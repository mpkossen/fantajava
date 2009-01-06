package efg.bank.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * @author Eric Gerlofsma
 */
public class Database
{
  private static final String DSN = "BANK"; // Data Source Name
  private static final String usr = "efg";
  private static final String pwd = "geheim";
  private static final String url = "jdbc:odbc:" + DSN;
  static public final Object lock = new Object(); // for synchronization only
  public static Connection con = null;
  static
  {
    try
    {
      //System.out.println("Database.static-initializer");
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    }
    catch (ClassNotFoundException cnfe)
    {
      System.err.println("Database: "+cnfe);
    }
  }

  Database()
  {
  //System.out.println("Database()");
  }

  public static void setConnection(boolean autoCommit)
  {
    //System.out.println("Database.setConnection("+autoCommit+")");
    try
    {
      if (con == null)
        con = DriverManager.getConnection(url, usr, pwd);
      con.setAutoCommit(autoCommit);
      //con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); //
      // this is the default
    }
    catch (SQLException sqle)
    {
      System.err.println("Database.setConnection(): " + sqle);
      closeConnection();
    }
  }

  public static void closeConnection()
  {
    //System.out.println("Database.closeConnection()");
    try
    {
      if (con != null)
      {
        con.close();
        con = null;
      }
    }
    catch (SQLException sqle)
    {
      System.err.println("Database.closeConnection(): " + sqle);
    }
  }

  /**
   * executeUpdate
   * 
   * @param query for INSERT, UPDATE, DELETE <br>
   *          or a statement that returns nothing.
   * @return either the row count for INSERT, UPDATE or DELETE statements, <br>
   *         or 0 for SQL statements that return nothing
   * @throws SQLException
   */
  static int executeUpdate(final String query) throws SQLException
  {
    //System.out.println("Database.executeUpdate(" + query + ")");
    synchronized (lock)
    {
      Statement stmt = null;
      while (true)
      {
        try
        {
          if (con == null)
            setConnection(true);
          stmt = con.createStatement();
          int n = stmt.executeUpdate(query);
          //System.out.println("Database.executeUpdate(): n="+ n);
          return n;
        }
        catch (SQLException sqle)
        {
          System.err.println("Database.excuteUpdate(query): " + sqle);
          if (query.startsWith("CREATE TABLE"))
            return -1; // table exists
          throw new SQLException(sqle.getMessage());
        }
        finally
        {
          //System.out.println("Database.executeUpdate(query): finally");
          if (stmt != null)
            stmt.close();
          if (con != null && con.getAutoCommit())
            closeConnection();
        }
      }
    }
  }

  /**
   * executeQuery
   * 
   * @param query
   * @param len
   * @return
   * @throws SQLException
   */
  static String[][] executeQuery(final String query, final int len)
      throws SQLException
  {
    //System.out.println("Database.executeQuery(" + query + ", " + len + ")");
    synchronized (lock)
    {
      Statement stmt = null;
      ResultSet rs = null;
      try
      {
        if (con == null)
          setConnection(true);
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        if (rs != null)
        {
          String[] entry = new String[len];
          Vector<String[]> vec = new Vector<String[]>();       	
          while (rs.next())
          {
            for (int i = 0; i < len; i++)
              entry[i] = rs.getString(i + 1);
            vec.add(entry.clone());
          }         
          String[][] ret = new String[vec.size()][len];
          for (int i = 0; i < vec.size(); i++)
            ret[i] = vec.elementAt(i);
          return ret;
        }
        return null;
      }
      finally
      {
        //System.out.println("Database.executeQuery(): finally");
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        if (con != null && con.getAutoCommit())
          closeConnection();
      }
    }
  }
}