package efg.jpa.bank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import efg.jpa.bank.db.Account;
import efg.jpa.bank.db.Database;
import efg.jpa.bank.db.Status;
import efg.jpa.bank.db.Transaction;
import efg.jpa.bank.util.MD5;

public final class AccountManager implements AccountManagerIF
{
  /*****************************************************************************
   * Static - Attributes
   ****************************************************************************/
  public static final String         ob            = "abc-bank is open and busy";
  public static final String         cb            = "abc-bank is closed and busy";
  public static final String         oi            = "abc-bank is open and idle";
  public static final String         ci            = "abc-bank is closed and idle";
  private static final EntityManager entityManager = Database.getEntityManager("AccountManager");

  /*****************************************************************************
   * static - getStatus() - createDatabase()
   ****************************************************************************/
  public static String getStatus()
  {
    //System.out.println("AccountManager.getStatus()");   
    
     Status status = entityManager.find(Status.class, "0");
      String ret = "";
      switch (Integer.parseInt(status.getBank()))
      {
        case 0:
          ret = ob; break;
        case 1:
          ret = cb; break;
        case 2:
          ret = oi; break;
        case 3:
          ret = ci; break;
      } 
      //System.out.println("AccountManager.getStatus()="+ret);
      return ret;    
  }

  /*****************************************************************************
   * Construction
   ****************************************************************************/
  public AccountManager(String newNumber, String newPincode, String newSalt) throws BankException
  {
    System.out.println("AccountManager(" + newNumber + "," + newPincode + "," + newSalt + ")");
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
        Account account = entityManager.find(Account.class, newNumber);
        if (account == null) throw new BankException("Wrong account");
        if (!account.getName().equals("beheerder")) throw new BankException("No Manager");
        String salt = account.getSalt();
        if (newSalt.compareTo(salt) <= 0) throw new BankException("Invalid salt");
        byte[] dws = MD5.hash(account.getPincode() + newSalt);
        if (!newPincode.equals(MD5.encode(dws))) throw new BankException("Invalid password");
        account.setSalt(newSalt); 
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
   * getAccount
   ****************************************************************************/
  public String[] getAccount(String number)
  {
    System.out.println("Accountmanager.getAccount(" + number + ")");
    Account account = entityManager.find(Account.class, number);     
    return account.details();
  }

  /*****************************************************************************
   * getTransactions
   ****************************************************************************/
  public String[][] getTransactions(String number)
  {
    System.out.println("Accountmanager.getTransactions(" + number + ")");

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
        Account account = entityManager.find(Account.class, number);
        entityManager.refresh(account);
        

    Collection<Transaction> c = account.getFromTransactions();
   if (c == null)
    {
      c = account.getToTransactions();
    }
    else
    {
      c.addAll(account.getToTransactions());
    }    
    System.out.println("c="+c);
    String[][] ret = null;
    if (c != null)
    {
      List<Transaction> arrayList = new ArrayList<Transaction>(c);
      Collections.sort(arrayList);
      ret = new String[arrayList.size()][];
      int i = 0;
      for (Transaction transaction : arrayList)
      {
        ret[i++] = new String[]
        {
              transaction.getId()
            , transaction.getFromAccount().getName()
            , transaction.getToAccount().getName()
            , "" + transaction.getAmount()
            , transaction.getTransactionTime()
            , transaction.getTransferTime()
        };
      }
    }
    return ret;  
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

  /*****************************************************************************
   * newAccount
   ****************************************************************************/
  public String newAccount(double newLimit, String newName, String newPincode) throws BankException
  {
    System.out.println("Accountmanager.newAccount(" + newLimit + ", " + newName + ", " + newPincode + ")");
    if (newName.equals("beheerder")) throw new BankException("wrong name");
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
        String number = null;
        Status status = entityManager.find(Status.class, "0");
        number = "" + (1 + Long.parseLong(status.getMaxAccount()));
        status.setMaxAccount(number);
        entityManager.persist(new Account(number, 0D, newLimit, newName, newPincode, "0"));
        return "Accountnumber is: " + number;
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
   * setOpen
   ****************************************************************************/
  public String setOpen(boolean b)
  {
    System.out.println("Accountmanager.setOpen(" + b + ")");
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
        Status status = entityManager.find(Status.class, "0");
        int stat = Integer.parseInt(status.getBank());
        if (b)
        {
          stat &= ~Status.CLOSED;
        }
        else
        {
          stat |= Status.CLOSED;
        }
        status.setBank("" + stat);
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
    return getStatus();
  }

  /*****************************************************************************
   * finalize(), toString()
   ****************************************************************************/
  @Override
  protected void finalize() throws Throwable
  {
    // DatabaseManager.removeEntityManager();
    System.out.println("AccountManager.finalize()");
  }

  @Override
  public String toString()
  {
    return "[AccountManager]";
  }
}