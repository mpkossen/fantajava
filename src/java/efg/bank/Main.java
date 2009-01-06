package efg.bank;

import efg.bank.util.MD5;

public class Main {
public static void main(String[] args) throws Exception
  {
    AccountManager.clean();
    System.out.println("Main: ------------------------------------------------");
    System.out.println();
    
    String rek = "" + 100000;
    String salt1 = "" + System.currentTimeMillis();
    String wachtwoord1 = MD5.encode(MD5.hash("geheim" + salt1));
    AccountManager beheer1 = new AccountManager(rek, wachtwoord1, salt1);
    beheer1.setOpen(true);
    doTest0();
    beheer1.setOpen(false);
    System.out.println("Main: ------------------------------------------------");
    System.out.println();
 
    //
    // overboekentest
    //
    String salt2 = "" + System.currentTimeMillis();
    String wachtwoord2 = MD5.encode(MD5.hash("geheim" + salt2));
    AccountManager beheer2 = new AccountManager(rek, wachtwoord2, salt2);
    beheer2.setOpen(true);
    doTest1();
    beheer2.setOpen(false);
    System.out.println("Main: ------------------------------------------------");
    System.out.println();
 
    //
    // check accounts
    //
    String salt3 = "" + System.currentTimeMillis();
    String wachtwoord3 = MD5.encode(MD5.hash("geheim" + salt3));
    AccountManager beheer3 = new AccountManager(rek, wachtwoord3, salt3);
    beheer3.setOpen(true);
    System.out.println("Main: Waiting for idle TransactionManager.");
    while(true)
    {
      String stat = AccountManager.getStatus();
      // System.out.println(status+", "+ (status & AccountManager.IDLE));
      if (stat.endsWith("idle")) break;
      try { Thread.sleep(1000); }
      catch (InterruptedException ie) {}
    }
    System.out.println(doCheck());
    beheer3.setOpen(false);   
    System.out.println("Main: ------------------------------------------------");
    System.out.println();
    
    //
    // check transactions
    //
    System.out.println("Main: Transactions from and to 100001");
    String salt4 = "" + System.currentTimeMillis();
    String wachtwoord4 = MD5.encode(MD5.hash("geheim" + salt4));
    AccountManager beheer4 = new AccountManager(rek, wachtwoord4, salt4);
    String[][] transactions = beheer4.getTransactions("100001");
    for (int i=0; i<transactions.length; i++)
    {
    	System.out.print("Main: ");
      for (int j=0; j<transactions[i].length; j++)
    	System.out.print(transactions[i][j]+" ");
      System.out.println();
    }
    System.out.println("------------------------------------------------");
    System.out.println();  
    
    System.out.println("test wrong account number");
    String rek5 = "" + 100000;
    String salt5 = "" + System.currentTimeMillis();
    String wachtwoord5 = MD5.encode(MD5.hash("geheim" + salt5));
    //AccountManager beheer5 = 
    new AccountManager(rek5, wachtwoord5, salt5);
    beheer1.setOpen(true);
    String rek6 = "1";
    String salt6 = "" + System.currentTimeMillis();
    String wachtwoord6 = MD5.encode(MD5.hash("test" + rek6 + salt6));
    //AccountOffice rekening1 = 
    new AccountOffice(rek6, wachtwoord6, salt6);
    beheer1.setOpen(false);
    System.out.println("Main: ------------------------------------------------");
    System.out.println();
 
  
  }
	private static void doTest0() throws Exception {
		System.out.println("Main: doTest0(): tijd=" + System.currentTimeMillis()
				/ 1000);
		System.out.println("Main: 1 storting, 1 opname, 1 overboeking");
		String rek1 = "100001";
		String salt1 = "" + System.currentTimeMillis();
		String wachtwoord1 = MD5.encode(MD5.hash("test" + rek1 + salt1));
		AccountOffice rekening1 = new AccountOffice(rek1, wachtwoord1, salt1);
		String rek2 = "100002";
		String salt2 = "" + System.currentTimeMillis();
		String wachtwoord2 = MD5.encode(MD5.hash("test" + rek2 + salt2));
		AccountOffice rekening2 = new AccountOffice(rek2, wachtwoord2, salt2);
		rekening1.transfer(null, 100.0);
		rekening1.transfer(rek2, 100.0);
		rekening1.sync();
		rekening1.close();
		rekening2.transfer(null, -100.0);
		rekening2.sync();
		rekening2.close();
	}

	private static void doTest1() throws Exception {
		System.out.println("Main: doTest1(): tijd=" + System.currentTimeMillis()
				/ 1000);
		final int n = 10; // threads, must be the same as in Check
		final int m = 10; // overboekingen per thread
		System.out.println("Main: OverboekenTest: " + n
				+ " klanten doen elk gelijktijdig " + m + " overboekingen.");
		Thread[] o = new Thread[n];
		for (int i = 0; i < n; i++) {
			o[i] = new Thread("" + i) // n threads
			{
				@Override
        public void run() {
					String rek1 = "" + (Integer.parseInt(getName()) + 100001);
					String salt = "" + System.currentTimeMillis();
					String wachtwoord = MD5.encode(MD5.hash("test" + rek1 + salt));
					AccountOffice rekening = null;
					try {
						rekening = new AccountOffice(rek1, wachtwoord, salt);
					} catch (Exception e) {
						System.err.println("Main: "+e);
						System.exit(1);
					}
					for (int j = 1; j <= m; j++) // m overboekingen
					{
						String rek2 = null;
						do {
							rek2 = "" + (100001 + (int) (n * Math.random()));
						} while (rekening.getDetails()[0].equals(rek2));
						try {
							rekening.transfer(rek2, 0.01);
						} catch (Exception e) {
							System.err.println("Main: "+e);
							System.exit(1);
						}
					}
					//long t = System.currentTimeMillis();
					rekening.sync();
					//System.out.println(Thread.currentThread().getName() + ": " + t);
				}
			};
			o[i].start();
		}
		System.out.println("Main: waiting for finishing threads");
		for (int i = 0; i < n; i++) {
			o[i].join();
		}
		System.out.println("Main: overboekentest ready");
	}

	private static String doCheck() throws Exception {
		System.out.print("Main: doCheck()  ");
		double som = 0.0;
		int n = 10; // must be the same as in Test
		for (int i = 0; i < n; i++) {
			String rek1 = "" + (i + 100001);
			String salt = "" + System.currentTimeMillis();
			String wachtwoord = MD5.encode(MD5.hash("test" + rek1 + salt));
			AccountOffice r = new AccountOffice(rek1, wachtwoord, salt);
			String[] s = r.getDetails();
			som += Double.parseDouble(s[1]);
		}
		return "Banksaldo is: " + ((int) (som * 100D + 0.5)) / 100D;
	}
}