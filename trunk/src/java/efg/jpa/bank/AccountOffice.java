package efg.jpa.bank;

import java.util.HashSet;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import efg.jpa.bank.db.Account;
import efg.jpa.bank.db.Database;
import efg.jpa.bank.db.Status;
import efg.jpa.bank.db.Transaction;
import efg.jpa.bank.util.MD5;

/**
 * @author EGerlofsma
 */
public final class AccountOffice implements AccountOfficeIF
{
  /*****************************************************************************
   * Attributes
   ****************************************************************************/
  private boolean              closed        = false;
  private Account              account       = null;
  private HashSet<Transaction> transactions  = new HashSet<Transaction>(101);
  private double               actualBalance = 0D;
  private EntityManager        entityManager = Database.getEntityManager("AccountOffice");

  /*****************************************************************************
   * Construction
   ****************************************************************************/
  public AccountOffice(String newNumber, String newPincode, String newSalt) throws BankException
  {
    //System.out.println("AccountOffice(" + newNumber + "," + newPincode + "," + newSalt + ")");
    synchronized (entityManager)
    {
      EntityTransaction tx = entityManager.getTransaction();
      try
      {
        if (tx.isActive())
        {
          throw new BankException("Transaction is active.");
        }
        tx.begin();
        Status status = entityManager.find(Status.class, "0");
        int stat = Integer.parseInt(status.getBank());
        if ((stat & Status.CLOSED) == Status.CLOSED) throw new BankException("Bank is closed.");
        account = entityManager.find(Account.class, newNumber);
        if (account == null) throw new BankException("no account");
        int lock = account.getLock();
        if (lock != 0) throw new BankException("Account lock(" + lock + ")");
        String salt = account.getSalt();
        if (newSalt.compareTo(salt) <= 0) throw new BankException("Invalid salt");
        byte[] dws = MD5.hash(account.getPincode() + newSalt);
        // System.out.println(MD5.encode(dws));
        if (!newPincode.equals(MD5.encode(dws))) throw new BankException("Invalid password: " + account.getName());
        account.setSalt(newSalt);
        actualBalance = account.getBalance();
      }
      finally
      {
        if (!tx.getRollbackOnly())
        {
          try
          {
            tx.commit();
          }
          catch (RollbackException re)
          {
            try
            {
              tx.rollback();
            }
            catch (PersistenceException pe)
            {
              throw new BankException("commit: " + pe.getMessage());
            }
          }
        }
        else
        {
          try
          {
            tx.rollback();
          }
          catch (PersistenceException pe)
          {
            throw new BankException("rollback: " + pe.getMessage());
          }
        }
      }
    }
  }

  /*****************************************************************************
   * customer - getDetails()
   ****************************************************************************/
  public String[] getDetails()
  {
    //System.out.println("AccountOffice.getDetails()");
    if (closed) return null;
    return account.details();
  }

  /*****************************************************************************
   * customer - getPendingTransactions()
   ****************************************************************************/
  public String[][] getPendingTransacties()
  {
    //System.out.println("AccountOffice.getPendingTransacties()");
    if (closed) return null;
    String[][] ret = new String[transactions.size()][];
    int i = 0;
    for (Transaction transaction : transactions)
    {
      ret[i++] = new String[]
      {
          transaction.getId(), transaction.getFrom(), transaction.getTo(), "" + transaction.getAmount(), transaction.getTransactionTime(), transaction.getTransferTime()
      };
    }
    return ret;
  }

  /**
   * Myprincipal holds the myAccount and is doing a transfer MyPrincipal is
   * creating a Transaction entity
   * 
   * transfer(null, 100.0) - this is a deposit - money comes from 100000 -
   * Transaction(100000, myAccount, 100.0)
   * 
   * transfer(null, -100.0) - this is a withdrawal - money goes to 100000 -
   * Transaction(myAccount, 100000, 100.0)
   * 
   * transfer(toAccount, 100.0) - this is a transfer to another account - money
   * goes from myAccount to 100002 - Transaction(myAccount, toAccount, 100.0)
   */
  /*****************************************************************************
   * customer - transfer()
   ****************************************************************************/
  public String transfer(String number, double amount) throws BankException
  {
    //System.out.println(account.getNumber() + " AccountOffice.transfer(" + number + ", " + amount + ")");
    boolean ret = false;
    if (closed) return "";
    String t0 = "" + System.currentTimeMillis();
    if (number == null)
    {
      if (amount > 0D)
      {
        // System.out.println("storting: "+amount);
        ret = transactions.add(new Transaction(null, "100000", account.getNumber(), amount, t0, "0"));
        actualBalance += amount;
      }
      else
      {
        // System.out.println("opname: "+amount);
        if ((-amount > actualBalance + account.getLimit())) throw new BankException("invalid amount");
        ret = transactions.add(new Transaction(null, account.getNumber(), "100000", -amount, t0, "0"));
        actualBalance += amount;
      }
    }
    else
    {
      // System.out.println("overboeking: "+number+", "+amount);
      if (account.getNumber().equals(number)) throw new BankException("to account = from account");
      if (amount <= 0D) throw new BankException("invalid amount: " + amount);
      if (amount > actualBalance + account.getLimit()) throw new BankException("balance + limit < amount: " + amount);
      ret = transactions.add(new Transaction(null, account.getNumber(), number, amount, t0, "0"));
      actualBalance -= amount;
    }
    return ret ? "oke" : "not oke";
  }

  /**
   * Synchroniseer de rekening met de database. Alle pending transactions van
   * dit moment worden gestuurd naar de TransactionManager. De
   * TransactionManager voltooid de overboekingen en update de rekeningen in de
   * database. Deze bankactie wordt in de achtergrond uitgevooerd en kan enige
   * tijd duren. sync() zal daarop niet wachten.
   */
  /*****************************************************************************
   * sync()
   ****************************************************************************/
  public void sync()
  {
    //System.out.println("AccountOffice.sync()");
    if (closed) return;
    if (transactions.size() > 0)
    {
      synchronized (entityManager)
      {
        EntityTransaction tx = entityManager.getTransaction();
        try
        {
          if (tx.isActive())
          {
            System.err.println("Transaction is active.");
          }
          tx.begin();
          Account a = entityManager.find(Account.class, account.getNumber());
          a.setLock(a.getLock() + 1);
        }
        finally
        {
          if (!tx.getRollbackOnly())
          {
            try
            {
              tx.commit();      
              TransactionManager tm = TransactionManager.getTransactionManager();    
              tm.add(transactions);
              transactions = new HashSet<Transaction>();
              
            }
            catch (RollbackException re)
            {
              try
              {
                tx.rollback();
              }
              catch (PersistenceException pe)
              {
                System.err.println("commit: " + pe.getMessage());
              }
            }
          }
          else
          {
            try
            {
              tx.rollback();
            }
            catch (PersistenceException pe)
            {
              System.err.println("rollback: " + pe.getMessage());
            }
          }
        }
      }
    }
  }

  /*****************************************************************************
   * close()
   ****************************************************************************/
  public void close()
  {
    //System.out.println("AccountOffice.close()");
    closed = true;
  }

  /*****************************************************************************
   * finalize(), toString()
   ****************************************************************************/
  @Override
  protected void finalize() throws Throwable
  {
    //System.out.println("AccountOffice[" + account.getNumber() + "].finalize()");
    close();
  }

  @Override
  public String toString()
  {
    return account.getName();
  }
}









