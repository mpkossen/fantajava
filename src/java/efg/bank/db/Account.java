package efg.bank.db;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * The object representation of a database table entry, inclusive all the
 * relationships.
 * 
 * @author Eric Gerlofsma
 */
public class Account extends Database
{
  /**
   * tablename and fieldnames
   */
  static final String tablename = "Accounts";
  private static final String[] fieldname = {"Account_Number",
      "Account_Balance", "Account_Limit", "Account_Name", "Account_Pincode",
      "Account_Salt"};

  /**
   * cache
   */
  private static HashMap<String, Account> cache = new HashMap<String, Account>(101);

  /**
   * Find an entry by primary key. <br>
   * First by looking up the cache, <br>
   * second by looking up the database. <br>
   * SQLException is thrown in case the entry is not present. <br>
   * Used by the session beans.
   * 
   * @param newNumber
   * @return
   * @throws SQLException
   */
  public static Account findByPrimaryKey(String newNumber) throws SQLException
  {
    //System.out.println("Account.findByPrimaryKey(" + newNumber + ")");
    Account account = cache.get(newNumber); // first look in the cache
    if (account == null)
      account = new Account(newNumber); // read from database
    return account;
  }

  /**
   * createTable()
   */
  public static void createTable() throws SQLException
  {
    //System.out.println("Account.createTable(" + tablename + ")");
    String query = "CREATE TABLE " + tablename;
    query += ("( " + fieldname[0] + " VARCHAR(32)");
    query += (", " + fieldname[1] + " VARCHAR(32)");
    query += (", " + fieldname[2] + " VARCHAR(32)");
    query += (", " + fieldname[3] + " VARCHAR(32)");
    query += (", " + fieldname[4] + " VARCHAR(32)");
    query += (", " + fieldname[5] + " VARCHAR(32)");
    query += (", PRIMARY KEY ( " + fieldname[0] + " )");
    query += (")");
    //int n = 
    executeUpdate(query);
    //System.out.println("Account.createTable(): table created " + n);
  }

  /**
   * deleteAll()
   */
  public static void deleteAll() throws SQLException
  {
    //System.out.println("Account.deleteAll()");
    String query = "DELETE FROM " + tablename;
    //int n = 
    executeUpdate(query);   
    //System.out.println("Account.deleteAll(): deleted " + n);

  }
  /**
   * private attributes
   */
  private String number = null;
  private double balance = 0.0;
  private double limit = 0.0;
  private String name = null;
  private String pincode = null;
  private String salt = null;
  private HashSet<Transaction> from_transactions = new HashSet<Transaction>();
  private HashSet<Transaction> to_transactions = new HashSet<Transaction>();
  
 
  /**
   * Insert a new entry in the database and put it in the cache. <br>
   * Used by the AccountManager.
   * 
   * @param newBalance
   * @param newLimit
   * @param newName
   * @param newPincode
   * @param newSalt
   */
  public Account(double newLimit, String newName, String newPincode) throws SQLException
  {
    //System.out.println("Acount(" + newBalance + ", " + newLimit + ", " +
    // newName + ", " + newPincode + ", " + newSalt + ")");
    synchronized (Database.lock)
    {
      setConnection(false);
      try
      { Status status = Status.findByPrimaryKey("0");

        number = "" + (1 + Long.parseLong(status.getMaxAccount()));
        balance = 0D;
        limit = newLimit;
        name = newName;
        pincode = newPincode;
        salt = "0";
        insert();
        cache.put(number, this); // new account created
        status.setMaxAccount(number);
        con.commit();
      }
      catch (SQLException sqle)
      {
    	System.err.println("Account(): " + sqle);
        try
        {
          con.rollback();
        }
        catch (SQLException sqle_1)
        {
          System.err.println("Account()" + sqle_1);
        }
        throw new SQLException(sqle.getMessage());
      }
      closeConnection();
    }
  }

  /**
   * Load an entry by primary key and put it in the cache. <br>
   * Used by findByPrimaryKey()
   * 
   * @param newNumber
   */
  private Account(String newNumber) throws SQLException
  {
    //System.out.println("Account(" + newNumber + ")");
    number = newNumber;
    select();
    cache.put(number, this); // read from database
  }

  public String getNumber()
  {
    //System.out.println("Account.getNumber()");
    return number;
  }

  public double getBalance()
  {
    //System.out.println("Account.getBalance()");
    return balance;
  }

  public void setBalance(double newBalance) throws SQLException
  {
    //System.out.println("Account.setBalance(" + newBalance
    // + ")");
    balance = newBalance;
    update();
  }

  public double getLimit()
  {
    //System.out.println("Account.getLimit()");
    return limit;
  }

  void setLimit(double newLimit) throws SQLException
  {
    //System.out.println("Account.setLimit(" + newLimit +
    // ")");
    limit = newLimit;
    update();
  }

  public String getName()
  {
    //System.out.println("Account.getName()");
    return name;
  }

  void setName(String newName) throws SQLException
  {
    //System.out.println("Account.setName(" + newName +
    // ")");
    name = newName;
    update();
  }

  public String getPincode()
  {
    //System.out.println("Account.getPincode()");
    return pincode;
  }

  void setPincode(String newPincode) throws SQLException
  {
    //System.out.println("Account.setPincode(" + newPincode
    // + ")");
    pincode = newPincode;
    update();
  }

  public String getSalt()
  {
    //System.out.println("Account.getSalt()");
    return salt;
  }

  public void setSalt(String newSalt) throws SQLException
  {
    //System.out.println("Account.setSalt(" + newSalt +
    // ")");
    salt = newSalt;
    update();
  }

  public @SuppressWarnings("unchecked") 
  Collection getTransactions() throws SQLException
  {
    //System.out.println("Account.getTransactions()");
    Transaction.findAllByAccount(this);
    HashSet<Transaction> ret = new HashSet<Transaction>();
    ret.addAll(from_transactions);
    ret.addAll(to_transactions);
    return ret;
  }

  public String[] getDetails()
  {
    //System.out.println("Account.getDetails()");
    String[] ret = new String[fieldname.length];
    ret[0] = "name    = "+name;
    ret[1] = "number  = "+number;
    ret[2] = "balance = "+ Math.round(100D * balance) / 100D;
    ret[3] = "limit   = " + limit;
    ret[4] = "pincode = "+pincode;
    ret[5] = "salt    = "+salt;
    return ret;
  }

  @SuppressWarnings("unused")
  private void delete() throws SQLException
  {
    //System.out.println("Accout.delete()");
    // remove from database
    String query = "DELETE FROM " + tablename;
    query += (" WHERE " + fieldname[0] + " = '" + number + "'");
    //int n = 
    executeUpdate(query);
    // remove from cache
    //Account account = (Account) 
    cache.remove(number); // removed from database
    // remove all the associated transactions
    Collection ts = getTransactions();
    Iterator iter = ts.iterator();
    while (iter.hasNext())
    {
      Transaction t = (Transaction) iter.next();
      t.delete();
    }
  }

  private void update() throws SQLException
  {
    //System.out.println("Account.update(): "+number);
    String query = "UPDATE " + tablename + " SET ";
    query += ("  " + fieldname[1] + " = '" + balance + "'");
    query += (", " + fieldname[2] + " = '" + limit + "'");
    query += (", " + fieldname[3] + " = '" + name + "'");
    query += (", " + fieldname[4] + " = '" + pincode + "'");
    query += (", " + fieldname[5] + " = '" + salt + "'");
    query += (" WHERE " + fieldname[0] + " = '" + number + "'");
    //int n = 
    executeUpdate(query);
  }

  private void insert() throws SQLException
  {
    //System.out.println("Acount.insert()");
    String query = "INSERT INTO " + tablename + " VALUES (";
    query += ("'" + number + "'");
    query += (", '" + balance + "'");
    query += (", '" + limit + "'");
    query += (", '" + name + "'");
    query += (", '" + pincode + "'");
    query += (", '" + salt + "'");
    query += (")");
    //int n = 
    executeUpdate(query);
  }

  /**
   * @param query
   * @return result string array
   */
  private void select() throws SQLException
  {
    //System.out.println("Account.select(): " + number);
    String query = "SELECT * FROM " + tablename + " WHERE ";
    query += (fieldname[0] + " = " + "'" + number + "'");
    String[][] entry = executeQuery(query, 6);
    if (entry.length == 1)
    {
      number = entry[0][0];
      balance = Double.parseDouble(entry[0][1]);
      limit = Double.parseDouble(entry[0][2]);
      name = entry[0][3];
      pincode = entry[0][4];
      salt = entry[0][5];
    }
    else
    {
      throw new SQLException("Account number does not exists!");
    }
  }

  /**
   * used by transaction for updating the cmr field
   * 
   * @param t
   */
  void add_from(Transaction t)
  {
    //System.out.println("Account.add_from(" + t + ")");
    from_transactions.add(t);
  }
  
  /**
   * used by transaction for updating the cmr field
   * 
   * @param t
   */
  void add_to(Transaction t)
  {
    //System.out.println("Account.add_to(" + t + ")");
    to_transactions.add(t);
  }

  @Override
  protected void finalize() throws Throwable
  {
    //System.out.println("Account.finalize");
  }

  @Override
  public String toString()
  {
    return "[Account: " + number + "]";
  }
}