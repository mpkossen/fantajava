package efg.bank.db;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * The object representation of a database table entry, inclusive all the
 * relationships.
 * 
 * @author Eric Gerlofsma
 */
public class Status extends Database
{
  static public final int CLOSED = 1; // Account offices are closed
  static public final int IDLE = 2; // TransactionManager is idle
  private static final String tablename = "Status";
  private static final String[] fieldname =
  {
      "Status_ID", "Status_Bank", "Status_Accounts", "Status_Transactions"
  };
  private static HashMap<String, Status> cache = new HashMap<String, Status>(101);

  public static Status findByPrimaryKey(String newID) throws SQLException
  {
    // System.out.println("Status.findByPrimaryKey(" + newID + ")");
    Status status = cache.get(newID);
    if (status == null) status = new Status(newID);
    return status;
  }

  /**
   * Create a database table
   */
  public static void createTable()
  {
    // System.out.println("Status.createTable(" + tablename + ")");
    String query = "CREATE TABLE " + tablename;
    query += ("( " + fieldname[0] + " VARCHAR(32)");
    for (int i = 1; i < fieldname.length; i++)
      query += (" , " + fieldname[i] + " VARCHAR(32)");
    query += (", PRIMARY KEY(" + fieldname[0] + ") )");
    try
    {
      //int n = 
      executeUpdate(query);
    }
    catch (SQLException sqle)
    {
      System.err.println("Status.update(): " + sqle);
    }
    // System.out.println("Status.createTable(): table created");
  }

  public static void deleteAll()
  {
    // System.out.println("Status.deleteAll()");
    String query = "DELETE FROM " + tablename;
    try
    {
      //int n = 
      executeUpdate(query);
    }
    catch (SQLException sqle)
    {
      System.err.println("Status.deleteAll(): " + sqle);
    }
  }
  /**
   * private attributes
   */
  private String id = "0";
  private String bank = "" + (CLOSED + IDLE);
  private String maxAccount = "99999";
  private String maxTransaction = "999999";

  public Status() throws SQLException
  {
    // System.out.println("Status()");
    synchronized (Database.lock)
    {
      setConnection(true);
      insert();
      cache.put(id, this);
      closeConnection();
    }
  }

  Status(String newID) throws SQLException
  {
    // System.out.println("Transaction(" + newID + ")");
    id = newID;
    select();
    cache.put(id, this);
  }

  public String getBank()
  {
    // System.out.println("Status.getBank()");
    return bank;
  }

  public void setBank(String newBank) throws SQLException
  {
    // System.out.println("Status.setBank(" + newBank + ")");
    bank = newBank;
    update();
  }

  String getMaxAccount()
  {
    // System.out.println("Status.getMaxAccount()");
    return maxAccount;
  }

  void setMaxAccount(String newMaxAccount) throws SQLException
  {
    // System.out.println("Status.setMaxAccount(" + newMaxAccount + ")");
    maxAccount = newMaxAccount;
    update();
  }

  String getMaxTransaction()
  {
    // System.out.println("Status.getMaxTransaction()");
    return maxTransaction;
  }

  void setMaxTransaction(String newMaxTransaction) throws SQLException
  {
    // System.out.println("Status.setMaxTransaction(" + newMaxTransaction +
    // ")");
    maxTransaction = newMaxTransaction;
    update();
  }

  String[] getDetails()
  {
    // System.out.println("Status.getDetails()");
    String[] ret = new String[fieldname.length];
    ret[0] = id;
    ret[1] = bank;
    ret[2] = maxAccount;
    ret[3] = maxTransaction;
    return ret;
  }

  /**
   * delete
   */
  @SuppressWarnings("unused")
  private void delete() throws SQLException
  {
    // System.out.println("Status.delete()");
    String query = "DELETE FROM " + tablename;
    query += (" WHERE " + fieldname[0] + " = '" + id + "'");
    int n = executeUpdate(query);
    Status status = cache.remove(id);
  }

  /**
   * update
   */
  private void update() throws SQLException
  {
    // System.out.println("Status.update()");
    String query = "UPDATE " + tablename + " SET ";
    query += ("  " + fieldname[1] + " = '" + bank + "'");
    query += (", " + fieldname[2] + " = '" + maxAccount + "'");
    query += (", " + fieldname[3] + " = '" + maxTransaction + "'");
    query += (" WHERE " + fieldname[0] + " = '" + id + "'");
    //int n = 
    executeUpdate(query);
  }

  /**
   * insert
   */
  private void insert() throws SQLException
  {
    // System.out.println("Status.insert()");
    String query = "INSERT INTO " + tablename + " VALUES (";
    query += ("'" + id + "'");
    query += (", '" + bank + "'");
    query += (", '" + maxAccount + "'");
    query += (", '" + maxTransaction + "'");
    query += (")");
    //int n = 
    executeUpdate(query);
  }

  /**
   * select
   */
  private void select() throws SQLException
  {
    // System.out.println("Status.select()");
    String query = "SELECT * FROM " + tablename + " WHERE ";
    query += (fieldname[0] + " = " + "'" + id + "'");
    String[][] entry = executeQuery(query, 4);
    id = entry[0][0];
    bank = entry[0][1];
    maxAccount = entry[0][2];
    maxTransaction = entry[0][3];
  }

  @Override
  protected void finalize() throws Throwable
  {
  // System.out.println("Status.finalize");
  }

  @Override
  public String toString()
  {
    return "[Status: " + id + "]";
  }
}