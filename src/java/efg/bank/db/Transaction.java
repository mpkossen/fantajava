package efg.bank.db;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * The object representation of a database table entry, inclusive all the
 * relationships.
 * 
 * @author Eric Gerlofsma
 */
public class Transaction extends Database
{
  public static final String                  tablename = "Transactions";

  private static final String[]               fieldname =
                                                        {
      "Transaction_ID",
      "Transaction_From",
      "Transaction_To",
      "Transaction_Amount",
      "Transaction_CreationTime",
      "Transaction_TransferTime"
                                                        };

  private static HashMap<String, Transaction> cache     = new HashMap<String, Transaction>(101);

  static Transaction findByPrimaryKey(String newID) throws SQLException
  {
    // System.out.println("Transaction.findByPrimaryKey(" + newID + ")");
    Transaction transaction = cache.get(newID); // read the
    // cache
    // first
    if (transaction == null) transaction = new Transaction(newID); // read from database
    return transaction;
  }

  static void findAllByAccount(Account account) throws SQLException
  {
    //System.out.println("Transaction.findAllByAccount(" + account + ")");
    String key = "" + account.getNumber();
    String query = "Select * from " + tablename + " WHERE ";
    query += (fieldname[1] + " = " + "'" + key + "'");
    query += " OR   ";
    query += (fieldname[2] + " = " + "'" + key + "'");

    String[][] entry = executeQuery(query, 6);
    for (int i = 0; i < entry.length; i++)
    {
      String id = entry[i][0];
      // System.out.println("Transaction.findAllByAccount(): id="+id);
      Transaction transaction = cache.get(id);
      // read the cache first
      if (transaction == null)
      {
        String from = entry[i][1];
        String to = entry[i][2];
        double amount = Double.parseDouble(entry[i][3]);
        String transactionTime = entry[i][4];
        String transferTime = entry[i][5];
        transaction = new Transaction(id, from, to, amount, transactionTime, transferTime);
        cache.put(id, transaction); // read from database
      }
      if (transaction.getFrom().equals(account.getNumber()))
      {
        account.add_from(transaction);
      }
      else if (transaction.getTo().equals(account.getNumber()))
      {
        account.add_to(transaction);
      }
      else
      {
        System.err.println("Transaction.findAllByAccount(): error");
      }
    }
  }

  /**
   * Create a database table
   */
  public static void createTable()
  {
    // System.out.println("Transaction.createTable(" + tablename + ")");
    String query = "CREATE TABLE " + tablename;
    query += ("( " + fieldname[0] + " VARCHAR(32)");
    for (int i = 1; i < fieldname.length; i++)
      query += (" , " + fieldname[i] + " VARCHAR(32)");
    query += (", PRIMARY KEY(" + fieldname[0] + ")");
    query += (", FOREIGN KEY(" + fieldname[1] + ") REFERENCES " + Account.tablename);
    query += (", FOREIGN KEY(" + fieldname[2] + ") REFERENCES " + Account.tablename);
    query += " )";
    try
    {
      //int n = 
      executeUpdate(query);
    }
    catch (SQLException sqle)
    {
      System.err.println("Transaction.update(): " + sqle);
    }
    // System.out.println("Transaction.createTable(): table created");
  }

  public static void deleteAll()
  {
    // System.out.println("Transaction.deleteAll()");
    String query = "DELETE FROM " + tablename;
    try
    {
      //int n = 
      executeUpdate(query);
    }
    catch (SQLException sqle)
    {
      System.err.println("Transaction.deleteAll(): " + sqle);
    }
  }

  /**
   * private attributes
   */
  private String        id              = null;
  private String        from            = null;
  private String        to              = null;
  private double        amount          = 0D;
  private String        transactionTime = null;
  private String        transferTime    = "------------";
  private DecimalFormat df              = new DecimalFormat("000000000.00");

  public Transaction(String newFrom, String newTo, double newAmount) throws SQLException
  {
    // System.out.println("Transaction(" + newFrom + ", " + newTo + ", "+
    // newAmount + ")");
    synchronized (Database.lock)
    {
      setConnection(false);
      try
      {
        Status status = Status.findByPrimaryKey("0");
        id = "" + (1 + Long.parseLong(status.getMaxTransaction()));
        from = newFrom;
        to = newTo;
        amount = newAmount;
        transactionTime = "" + System.currentTimeMillis();
        insert();
        cache.put(id, this); // new transaction created
        status.setMaxTransaction(id);
        con.commit();
      }
      catch (SQLException sqle)
      {
        System.err.println("Transaction(): " + sqle);
        con.rollback();
      }
      closeConnection();
    }
  }

  private Transaction(String newID) throws SQLException
  {
    // System.out.println("Transaction(" + newID + ")");
    id = newID;
    select();
    cache.put(id, this); // read from database
  }

  private Transaction(String newID, String newFrom, String newTo, double newAmount, String newTransactionTime,
      String newTransferTime)
  {
    // System.out.println("Transaction(" +newID + ", " + newFrom + ", " +
    // newTo
    // + ", " + newAmount + ", " + newTransactionTime + ", "
    // + newTransferTime + ")");
    id = newID;
    from = newFrom;
    to = newTo;
    amount = newAmount;
    transactionTime = newTransactionTime;
    transferTime = newTransferTime;
  }

  public String getFrom()
  {
    // System.out.println("Transaction.getFrom()");
    return from;
  }

  void setFrom(String newFrom) throws SQLException
  {
    // System.out.println("Transaction.setFrom(" + newFrom +
    // ")");
    from = newFrom;
    update();
  }

  public String getTo()
  {
    // System.out.println("Transaction.getTo()");
    return to;
  }

  void setTo(String newTo) throws SQLException
  {
    // System.out.println("Transaction.setTo(" + newTo + ")");
    to = newTo;
    update();
  }

  public double getAmount()
  {
    // System.out.println("Transaction.getAmount()");
    return amount;
  }

  void setAmount(double newAmount) throws SQLException
  {
    // System.out.println("Transaction.setAmount(" + newAmount + ")");
    amount = newAmount;
    update();
  }

  String getTransactionTime()
  {
    // System.out.println("Transaction.getTransactionTime()");
    return transactionTime;
  }

  void setTransactionTime(String newTransactionTime) throws SQLException
  {
    // System.out.println("Transaction.setTransactionTime(" + newTransactionTime + ")");
    transactionTime = newTransactionTime;
    update();
  }

  String getTransferTime()
  {
    // System.out.println("Transaction.getTransferTime()");
    return transferTime;
  }

  public void setTransferTime(String newTransferTime) throws SQLException
  {
    // System.out.println("Transaction.setTransferTime(" + newTransferTime + ")");
    transferTime = newTransferTime;
    update();
  }

  public String[] getDetails()
  {
    String[] ret = new String[fieldname.length];
    ret[0] = id;
    ret[1] = getFrom();
    ret[2] = getTo();
    ret[3] = df.format(getAmount());
    ret[4] = getTransactionTime();
    ret[5] = getTransferTime();
    return ret;
  }

  /**
   * delete
   */
  void delete() throws SQLException
  {
    // System.out.println("Transaction.delete()");
    String query = "DELETE FROM " + tablename;
    query += (" WHERE " + fieldname[0] + " = '" + id + "'");
    //int n = 
    executeUpdate(query);
    //Transaction transaction = (Transaction) 
    cache.remove(id);
    // removed from database
  }

  /**
   * update
   */
  private void update() throws SQLException
  {
    // System.out.println("Transaction.update()");
    String query = "UPDATE " + tablename + " SET ";
    query += ("  " + fieldname[1] + " = '" + from + "'");
    query += (", " + fieldname[2] + " = '" + to + "'");
    query += (", " + fieldname[3] + " = '" + amount + "'");
    query += (", " + fieldname[4] + " = '" + transactionTime + "'");
    query += (", " + fieldname[5] + " = '" + transferTime + "'");
    query += (" WHERE " + fieldname[0] + " = '" + id + "'");
    //int n = 
    executeUpdate(query);
  }

  /**
   * insert
   */
  private void insert() throws SQLException
  {
    // System.out.println("Transaction.insert()");
    String query = "INSERT INTO " + tablename + " VALUES (";
    query += ("'" + id + "'");
    query += (", '" + from + "'");
    query += (", '" + to + "'");
    query += (", '" + amount + "'");
    query += (", '" + transactionTime + "'");
    query += (", '" + transferTime + "'");
    query += (")");
    //int n = 
    executeUpdate(query);
  }

  /**
   * select
   */
  private void select() throws SQLException
  {
    // System.out.println("Transaction.select()");
    String query = "SELECT * FROM " + tablename + " WHERE ";
    query += (fieldname[0] + " = " + "'" + id + "'");
    String[][] entry = executeQuery(query, 6);
    id = entry[0][0];
    from = entry[0][1];
    to = entry[0][2];
    amount = Double.parseDouble(entry[0][3]);
    transactionTime = entry[0][4];
    transferTime = entry[0][5];
  }

  @Override
  protected void finalize() throws Throwable
  {
    System.out.println("Transaction.finalize");
  }

  @Override
  public String toString()
  {
    return "[Transaction: " + id + "]";
  }
}