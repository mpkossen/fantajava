package efg.jpa.bank;

import efg.jpa.bank.db.Database;
import efg.jpa.bank.util.MD5;

public class Main
{
  public static void main(String[] args)
  {
    System.out.println("Main.main()");
    int len = args.length;
    int test = -1;
    if (len != 1)
    {
      exit();
    }
    else
    {
      if (args[0].equals("create"))
      {
        Database.createDatabase();
        return;
      }
      try
      {
        test = Integer.parseInt(args[0]);
        if (test < 0)
        {
          exit();
        }
      }
      catch (Exception e)
      {
        exit();
      }
    }
    
    
    try
    {
      switch (test)
      {
        case  1: doTest1(); break;
        case  2: doTest2(); break;
        case  3: doTest3(90, 10); break;
        case  4: doTest4(); break;
        default: exit();
      }
      
      waitForIdle();
      System.gc();
      System.runFinalization();
      System.out.println("Main: ready");
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
  }

  private static void exit()
  {
    System.out.println("usage: Main create|1|2|3|4");
    System.exit(1);
  }

  /*****************************************************************************
   * doTest1()
   ****************************************************************************/
  private static void doTest1()
  {
    System.out.println("\n\n\nMain.doTest1(): check account(100011)");
    try
    {
      AccountManager manager = getAccountManager("100000", "geheim");
      String[] details = manager.getAccount("100011");
      System.out.println("name   =" + details[0]);
      System.out.println("number =" + details[1]);
      System.out.println("balance=" + details[2]);
      System.out.println("limit  =" + details[3]);
      System.out.println("pincode=" + details[4]);
      System.out.println("salt   =" + details[5]);
      System.out.println();
      String[][] transactions = manager.getTransactions("100011");
      if (transactions != null)
      {
        for (int i = 0; i < transactions.length; i++)
        {
          for (int j = 0; j < transactions[i].length; j++)
            System.out.print(transactions[i][j] + " ");
          System.out.println();
        }
      }
      else
      {
        System.out.println("no transactions");
      }
    }
    catch (Exception e)
    {
      System.out.println("Main.doTest1(): " + e);
    }
  }

  /*****************************************************************************
   * doTest2()
   ****************************************************************************/
  private static void doTest2() throws BankException
  {
    System.out.println("\n\n\nMain.doTest2(): 1 storting, 1 opname, 1 overboeking");
    AccountOffice account1 = getAccountOffice("100010", "test100010");
    account1.transfer(null, 50.0);
    account1.transfer("100011", 40.0);
    account1.sync();
    account1.close();
    AccountOffice account2 = getAccountOffice("100011", "test100011");
    account2.transfer(null, -30.0);
    account2.sync();
    account2.close();
  }

  /*****************************************************************************
   * doTest3() n - threads m - transfers
   ****************************************************************************/
  private static void doTest3(final int n, final int m)
  {
    System.out.println("Main.doTest3(): OverboekenTest: " + n + " klanten doen elk gelijktijdig " + m + " overboekingen.");
    Thread[] o = new Thread[n];
    for (int i = 0; i < n; i++)
    {
      o[i] = new Thread("1000" + (10 + i)) // n threads
      {
        @Override
        public void run()
        {
          try
          {
            AccountOffice account1 = getAccountOffice(getName(), "test" + getName());
            for (int j = 0; j < m; j++) // m overboekingen
            {
              String account2 = null;
              do
              {
                account2 = "" + (100010 + (int) (n * Math.random()));
              }
              while (account1.getDetails()[1].equals(account2));
              account1.transfer(account2, 0.01);
            }
            account1.sync();
          }
          catch (Exception e)
          {
            System.err.println("Main.doTest3(): " + getName() + ", " + e.getMessage());
            return;
          }
        }
      };
      o[i].start();
    }
    System.out.println("Main.doTest3(): waiting for finishing threads");
    for (int i = 0; i < n; i++)
    {
      try
      {
        o[i].join();
      }
      catch (Exception e)
      {
        System.out.println("Main.doTest3(): " + e.getMessage());
        return;
      }
    }
    waitForIdle();
    System.out.print("Main.doCheck1(): check()  ");
    double som = 0.0;
    try
    {
      for (int i = 0; i < n; i++)
      {
        String name = "" + (i + 100010);
        AccountOffice r = getAccountOffice(name, "test" + name);
        String[] s = r.getDetails();
        som += Double.parseDouble(s[2]);
      }
      System.out.println("Banksaldo is: " + ((int) (som * 100D + 0.5)) / 100D);
    }
    catch (BankException e)
    {
      System.out.println("Main.doTest3(): " + e.getMessage());
      return;
    }
  }

  /*****************************************************************************
   * doTest4()
   ****************************************************************************/
  private static void doTest4()
  {
    System.out.println("\nMain.doTest4(): test wrong account number");
    try
    {
      getAccountOffice("200000", "test200000");
    }
    catch (BankException e)
    {
      System.out.println("wrong account detected");
      return;
    }
  }

  /*****************************************************************************
   * getters
   ****************************************************************************/
  private static AccountManager getAccountManager(String newAccount, String newPincode) throws BankException
  {
    System.out.println("Main.getAccountManager(" + newAccount + ", " + newPincode + ")");
    String salt = "" + System.currentTimeMillis();
    String pincode = MD5.encode(MD5.hash(newPincode + salt));
    return new AccountManager(newAccount, pincode, salt);
  }

  static AccountOffice getAccountOffice(String newAccount, String newPincode) throws BankException
  {
    // System.out.println("Main.getAccountOffice(" + newAccount + ", " + newPincode + ")");
    String salt = "" + System.currentTimeMillis();
    String pincode = MD5.encode(MD5.hash(newPincode + salt));
    return new AccountOffice(newAccount, pincode, salt);
  }

  /*****************************************************************************
   * waitForIdle
   ****************************************************************************/
  private static void waitForIdle()
  {
    System.out.println("waitForIdle()");
    while (AccountManager.getStatus().endsWith("busy"))
    {
      System.out.print(".");
      try
      {
        Thread.sleep(1000);
      }
      catch (InterruptedException ie)
      {
      }
    }
  }
}