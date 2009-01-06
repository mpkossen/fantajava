package efg.bank;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import efg.bank.db.Account;
import efg.bank.db.Status;
import efg.bank.db.Transaction;
import efg.bank.util.MD5;

/**
 * @author EGerlofsma
 */
public final class AccountOffice
{
  private boolean              closed        = false;
  private Account              account       = null;
  private HashSet<Transaction> transactions  = new HashSet<Transaction>(101);
  private Status               status        = Status.findByPrimaryKey("0");
  private double               actualBalance = 0D;

  public AccountOffice(String newNumber, String newPincode, String newSalt) throws Exception
  {
    System.out.println("AccountOffice(" + newNumber + "," + newPincode + "," + newSalt + ")");
    int stat = Integer.parseInt(status.getBank());
    if ((stat & Status.CLOSED) == Status.CLOSED) throw new Exception("Bank is closed.");
    account = Account.findByPrimaryKey(newNumber);
    String salt = account.getSalt();
    if (newSalt.compareTo(salt) <= 0) throw new Exception("Invalid salt");
    byte[] dws = MD5.hash(account.getPincode() + newSalt);
    //System.out.println(MD5.encode(dws));
    if (!newPincode.equals(MD5.encode(dws))) throw new Exception("Invalid password");
    account.setSalt(newSalt);
    actualBalance = account.getBalance();
  }

  /**
   * Overboeken wordt op 3 manieren gebruikt: <br>
   * 1.<br>
   * storten op eigen rekening: <br>
   * boolean b = overboeken(null, 100.0); <br>
   * bedrag is positief <br>
   * new Tranactie(100000, nummer, bedrag); <br>
   * 2.<br>
   * opname van eigen rekening: <br>
   * boolean b = overboeken(null, -100.0); <br>
   * bedrag is negatief <br>
   * new Tranactie(nummer, 100000, -bedrag); <br>
   * 3.<br>
   * overboeking naar een andere rekening: <br>
   * boolean b = overboeken("1002", 100.0); <br>
   * bedrag is positief <br>
   * new Tranactie(nummer, "1002", bedrag); <br>
   */
public String transfer(String n, double bedrag) throws SQLException, AccountOfficeException
  {
    //System.out.println("AccountOffice.overboeken(" + n + ", " + bedrag + ")");
    boolean ret = false;
    if (!closed)
    {
      if (bedrag == 0D)
        throw new AccountOfficeException("zero amount");
      if (n == null)
      {
        if (bedrag > 0D) // storting, bedrag is positief
        {
          Transaction tx = new Transaction("100000", account.getNumber(), bedrag);
          actualBalance += bedrag;
          ret = transactions.add(tx);
        }
        else
        // opname, bedrag is negatief
        {
          if ((actualBalance + account.getLimit()) >= -bedrag)
          {
            Transaction tx = new Transaction(account.getNumber(), "100000", -bedrag);
            actualBalance += bedrag;
            ret = transactions.add(tx);
          }
          else
          {
            throw new AccountOfficeException("invalid amount");
          }
        }
      }
      else // overboeking
      {
        if (bedrag > 0D)
        {
          if (actualBalance + account.getLimit() >= bedrag)
          {
            if (!account.getNumber().equals(n))
            {
              Transaction tx = new Transaction(account.getNumber(), n, bedrag);
              actualBalance -= bedrag;
              ret = transactions.add(tx);
            }
            else
            {
              throw new AccountOfficeException("invalid account");
            }
          }
          else
          {
            throw new AccountOfficeException("invalid amount");
          }
        }
        else
        {
          throw new AccountOfficeException("negative amount");
        }
      }
    }
    return ret?"oke":"not oke";
  }
  /**
   * Synchroniseer de rekening met de database. Alle pending transactions van
   * dit moment worden gestuurd naar de TransactionManager. De
   * TransactionManager voltooid de overboekingen en update de rekeningen in de
   * database. Deze bankactie wordt in de achtergrond uitgevooerd en kan enige
   * tijd duren. sync() zal daarop niet wachten.
   */
  public void sync()
  {
    //System.out.println("AccountOffice.sync()");
    if (!closed)
    {
      if (transactions.size() > 0)
      {
        TransactionManager tm = TransactionManager.getTransactionManager();
        tm.add(transactions);
        transactions = new HashSet<Transaction>();
      }
    }
  }

  public String[][] getPendingTransacties()
  {
    //System.out.println("AccountOffice.getPendingTransactions()");
    String[][] ret = null;
    if (!closed)
    {
      ret = new String[transactions.size()][];
      Iterator iter = transactions.iterator();
      for (int i = 0; iter.hasNext(); i++)
      {
        Transaction t = (Transaction) iter.next();
        ret[i] = t.getDetails();
      }
    }
    return ret;
  }

  public void close()
  {
    //System.out.println("AccountOffice.close()");
    closed = true;
  }

  public String[] getDetails()
  {
    //System.out.println("Accountoffice.getDetails()");
    String[] ret = null;
    if (!closed)
    {
      ret = account.getDetails();
    }
    return ret;
  }

  @Override
  protected void finalize() throws Throwable
  {
    //System.out.println("AccountOffice.finalize()");
    super.finalize();
  }

  @Override
  public String toString()
  {
    return account.getName();
  }
}