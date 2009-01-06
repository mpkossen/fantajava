package efg.jpa.bank;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import efg.jpa.bank.db.Account;
import efg.jpa.bank.db.Database;
import efg.jpa.bank.db.Status;
import efg.jpa.bank.db.Transaction;
import java.util.Collection;
import java.util.HashSet;

class TransactionManager implements Runnable
{
  /*****************************************************************************
   * Static - Attributes
   ****************************************************************************/
  private static EntityManager      entityManager      = Database.getEntityManager("TransactionManager");
  private static TransactionManager transactionManager = new TransactionManager();

  static TransactionManager getTransactionManager()
  {
    return transactionManager;
  }
  private Thread       transactionThread = null;
  // Simple Queue Implementation
  private final Object queueLock         = new Object();
  private final int    bufsize           = 128;
  private Collection[] queue             = new Collection[bufsize];
  private int          front             = 0;
  private int          back              = -1;
  private int          count             = 0;

  private TransactionManager()
  {
    System.out.println("TransactionManager()");
  }

  /**
   * AccountOffices append HashSets with transactions to the end of the queue.
   * <br>
   * If the queue is full AccountOffices must wait for empty space. <br>
   * If necessary the TransactionManager is activated, <br>
   * and status in AccountManager is set.
   * 
   * @param hs
   */
  void add(HashSet<Transaction> hs)
  {
    System.out.println("TransactionManager.add(" + hs.size() + ")");
    synchronized (queueLock)
    {
      while (count == bufsize)
      {
        // System.out.println("TransactionManager.add(): the queue is full,
        // waiting for space");
        sleep(1000);
      }
      back++;
      if (back == bufsize) back = 0;
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
    {
    }
  }

  private void setIdle(boolean idle)
  {
    // System.out.println("TransactionManager.setIdle("+idle+")");
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
    // System.out.println("\n\n\nTransactionManager.run(): started ");
    while (true)
    {
      HashSet<Transaction> transactions = null;
      synchronized (queueLock)
      {
        if (count == 0)
        {
          transactionThread = null;
          setIdle(true);
          // System.err.println("TransactionManager.run(): stopped ");
          return;
        }
        transactions = (HashSet<Transaction>) queue[front];
        // System.out.println("transactions.size()="+transactions.size());
        front++;
        if (front == bufsize) front = 0;
        count--;
      }
      // insert transactions in database
      String sync = null;
      for (Transaction transaction : transactions)
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
            String from = transaction.getFrom();
            String to = transaction.getTo();
            Account fromAccount = entityManager.find(Account.class, from);
            if (fromAccount == null)
            {
              tx.setRollbackOnly();
            }
            else
            {
              Account toAccount = entityManager.find(Account.class, to);
              if (toAccount == null)
              {
                tx.setRollbackOnly();
              }
              else
              {
                if (sync == null)
                {
                  sync = from.equals("100000") ? to : from;
                }
                double amount = transaction.getAmount();
                System.out.println("TransactionManager: " + from + " " + to + " " + amount);
                
                fromAccount.setBalance(fromAccount.getBalance() - amount);
                toAccount.setBalance(toAccount.getBalance() + amount);
                Status status = entityManager.find(Status.class, "0");
                String number = "" + (1 + Long.parseLong(status.getMaxTransaction()));
                status.setMaxTransaction(number);
                transaction.setId(number);
                transaction.setTransferTime("" + System.currentTimeMillis());
                transaction.setFromAccount(fromAccount);
                transaction.setToAccount(toAccount);
                entityManager.persist(transaction);
              }
            }
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
      } // for
      // System.out.println("status-down: "+ sync);
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
          Account s = entityManager.find(Account.class, sync);
          s.setLock(s.getLock() - 1);
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
    } // while
  } // run

  @Override
  protected void finalize() throws Throwable
  {
    // System.out.println("TransactionManager.finalize()");
  }

  @Override
  public String toString()
  {
    return "[TransactionManager]";
  }
}