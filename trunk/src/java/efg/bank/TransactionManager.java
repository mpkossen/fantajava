package efg.bank;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import efg.bank.db.Account;
import efg.bank.db.Database;
import efg.bank.db.Status;
import efg.bank.db.Transaction;

class TransactionManager implements Runnable
{
  private static TransactionManager transactionManager = new TransactionManager();

  static TransactionManager getTransactionManager()
  {
    return transactionManager;
  }
  private Thread transactionThread = null;
  // Simple Queue Implementation
  private final Object queueLock = new Object();
  private final int bufsize = 128;
  private HashSet[] queue = new HashSet[bufsize];
  private int front = 0; //points to the front
  private int back = -1; //points to the back
  private int count = 0; //

  /**
   * AccountOffices append HashSets with transactions to the end of the queue.
   * <br>
   * If the queue is full AccountOffices must wait for empty space. <br>
   * If necessary the TransactionManager is activated, <br>
   * and status in AccountManager is set.
   * 
   * @param hs
   */
  void add(HashSet hs)
  {
    //System.out.println("(TransactionManager.add(" + hs + ")");
    synchronized (queueLock)
    {
      while (count == bufsize)
      {
        //System.out.println("TransactionManager.add(): the queue is full, waiting for space");
        sleep(1000);
      }
      back++;
      if (back == bufsize)
        back = 0;
      queue[back] = hs;
      count++;
      if (transactionThread == null)
      {
        transactionThread = new Thread(this);
        transactionThread.start();
        setIdle(false);
      }
    }
  }

  private void sleep(int ms)
  {
    try
    {
      Thread.sleep(ms);
    }
    catch (InterruptedException ie)
    {}
  }

  private void setIdle(boolean idle)
  {
    try
    {
      Status status = Status.findByPrimaryKey("0");
      int stat = Integer.parseInt(status.getBank());
      if (idle)
      {
        stat |= Status.IDLE;
      }
      else
      {
        stat &= ~Status.IDLE;
      }
      status.setBank("" + stat);
    }
    catch (SQLException sqle)
    {
      System.err.println("TransactionManager.setIdle(): " + sqle);
    }
  }

  /**
   * Overboeken wordt op 3 manieren gebruikt: <br>
   * 1. storten op eigen rekening: <br>
   * boolean b = overboeken(null, 100.0); <br>
   * bedrag is positief <br>
   * new Tranactie(100000, nummer, bedrag); <br>
   * 2. opname van eigen rekening: <br>
   * boolean b = overboeken(null, -100.0); <br>
   * bedrag is negatief <br>
   * new Tranactie(nummer, 100000, -bedrag); <br>
   * 3. overboeking naar een andere rekening: <br>
   * boolean b = overboeken("1002", 100.0); <br>
   * bedrag moet positief zijn <br>
   * new Tranactie(nummer, "1002", bedrag);
   */
  public void run()
  {
    //System.out.println("TransactionManager.run(): started ");
    while (true)
    {
      HashSet transactions = null;
      synchronized (queueLock)
      {
        if (count == 0)
        {
          transactionThread = null;
          setIdle(true);
          //System.err.println("TransactionManager.run(): stopped ");
          return;
        }
        transactions = queue[front];
        front++;
        if (front == bufsize)
          front = 0;
        count--;
      }
      // insert transactions in database
      Iterator iter = transactions.iterator();
      while (iter.hasNext())
      {
        Transaction transaction = (Transaction) iter.next();
        String from = transaction.getFrom();
        String to = transaction.getTo();
        double amount = transaction.getAmount();
        synchronized (Database.lock)
        {
          Database.setConnection(false);
          try
          {
            if (!(from.equals("100000") || to.equals("100000")))
            {
              //System.err.println("TransactionManager: transfer");
              Account f = Account.findByPrimaryKey(from);
              Account t = Account.findByPrimaryKey(to);
              f.setBalance(f.getBalance() - amount);
              t.setBalance(t.getBalance() + amount);
              //System.err.println("TransactionManager: commit transfer");
            }
            if (from.equals("100000"))
            {
              //System.err.println("TransactionManager: deposit");
              Account t = Account.findByPrimaryKey(to);
              t.setBalance(t.getBalance() + amount);
              //System.err.println("TransactionManager: commit deposit");
            }
            if (to.equals("100000"))
            {
              //System.err.println("TransactionManager: withdrawal");
              Account f = Account.findByPrimaryKey(from);
              f.setBalance(f.getBalance() - amount);
              //System.err.println("TransactionManager: commit withdrawal");
            }
            transaction.setTransferTime("" + System.currentTimeMillis());
            Database.con.commit();
          }
          catch (SQLException sqle)
          {
            System.err.println("TransactionManager: tx-catch "+sqle);        
            try
            {
              transaction.setTransferTime(amount + ": " + from + " --> " + to + " ?");
              //System.out.println("rollback");
              Database.con.rollback();
            }
            catch (SQLException sqle_1)
            {
              System.err.println("TransactionManager: tx-catch "+sqle_1);           
            }
          }
          finally
          {
            Database.closeConnection();
          } // finally
        } // synchronized
      } // while
    } // while
  } // run
  
  @Override
  protected void finalize() throws Throwable
  {
    //System.out.println("TransactionManager.finalize()");
  }
  
  @Override
  public String toString()
  {
    return "[TransactionManager]";
  }
}