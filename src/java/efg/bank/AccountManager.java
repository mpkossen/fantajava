package efg.bank;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import efg.bank.db.Account;
import efg.bank.db.Status;
import efg.bank.db.Transaction;
import efg.bank.util.MD5;

public final class AccountManager
{

  /**
   *   maakt de database leeg,
   *   maakt een AccountManager met nummer/password: 100000/geheim
   *   maak 100 gebruikers aan met nummer/pincode: 100001/test100001 - 100100/test100100
   */
  public static void clean() throws SQLException
  {
    System.out.println("AcountManager.clean()");
    Status.createTable();
    Account.createTable();
    Transaction.createTable();
    Transaction.deleteAll();
    Account.deleteAll();
    Status.deleteAll();
    System.out.println("creating bank-status");
    new Status();
    System.out.println("creating account manager");
    new Account(0.0, "beheerder", "geheim");
    System.out.println("creating 100 accounts");
    for (int i = 100001; i < 100101; i++)
      new Account(500.0, "test" + i, "test" + i);
    System.out.println("created 100 accounts");
  }

  /**
   * De 4 mogelijke return strings zijn:
   * "open and busy"
   * "closed and busy"
   * "open and idle"
   * "closed and idle"
   */
  public static String getStatus()
  {
    //System.out.println("AccountManager.getStatus()");
    try
    {
      Status status = Status.findByPrimaryKey("0");
      int stat = Integer.parseInt(status.getBank());
      String ret = "abc-bank is ";
      switch (stat)
      {
        case 0:
          ret += "open and busy";
          break;
        case 1:
          ret += "closed and busy";
          break;
        case 2:
          ret += "open and idle";
          break;
        case 3:
          ret += "closed and idle";
          break;
      }
      return ret;
    }
    catch (SQLException sqle)
    {
      return "";
    }
  }

  public AccountManager(String newNumber, String newPincode, String newSalt) throws Exception
  {
    System.out.println("AccountManager(" + newNumber + "," + newPincode + "," + newSalt + ")");
    Account account = Account.findByPrimaryKey(newNumber);
    if (!account.getName().equals("beheerder")) throw new Exception("No Manager");
    String salt = account.getSalt();
    if (newSalt.compareTo(salt) <= 0) throw new Exception("Invalid salt");
    byte[] dws = MD5.hash(account.getPincode() + newSalt);
    if (!newPincode.equals(MD5.encode(dws))) throw new Exception("Invalid password");
    account.setSalt(newSalt);
  }

  public synchronized String setOpen(boolean b) throws SQLException
  {
    //System.out.println("AccountManager.setOpen(" + b + ")");
    Status status = Status.findByPrimaryKey("0");
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
    return getStatus();
  }

  public String[] getAccount(String number) throws SQLException
  {
    //System.out.println("AccountManager.getAccount(" + number + ")");
    Account account = Account.findByPrimaryKey(number);
    return account.getDetails();
  }

  public String[][] getTransactions(String number) throws SQLException
  {
    //System.out.println("AccountManager.getTransactions(" + number + ")");
    Account account = Account.findByPrimaryKey(number);
    Collection c = account.getTransactions();
    //System.out.println(c);
    Iterator iter = c.iterator();
    Vector<Transaction> vec = new Vector<Transaction>();
    while (iter.hasNext())
    {
      vec.add((Transaction) (iter.next()));
    }
    String[][] ret = new String[vec.size()][];
    for (int i = 0; i < ret.length; i++)
    {
      Transaction transaction = vec.elementAt(i);
      ret[i] = transaction.getDetails();
    }
    return ret;
  }

  public String newAccount(double newLimit, String newName, String newPincode) throws Exception, SQLException
  {
    //System.out.println("AccountManager.newAcount(" + newLimit + ", " + newName + ", " + newPincode + ")");
    if (newName.equals("beheerder")) throw new Exception("wrong name");
    Account account = new Account(newLimit, newName, newPincode);
    return account.getNumber();
  }

  @Override
  protected void finalize() throws Throwable
  {
    //System.out.println("AccountManager.finalize()");
    super.finalize();
  }

  @Override
  public String toString()
  {
    return "[AccountManager]";
  }
}