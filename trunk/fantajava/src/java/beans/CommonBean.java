package beans;
import efg.jpa.bank.AccountManager;

public class CommonBean
{
	boolean bankbusy, bankclosed;
	private static String title = "ABC-BANK";
	
	public CommonBean() {
	    System.out.println("CommonBean()");
	    String status = AccountManager.getStatus();
	    System.out.println("accountmanager status = " + AccountManager.getStatus().toString());
	    bankbusy = (status.equals(AccountManager.ob) || status.equals(AccountManager.cb));
	    bankclosed = (status.equals(AccountManager.ci) || status.equals(AccountManager.cb));
	}
	
	public String getTitle() {
	    return title;
	}
    
	@Override
	protected void finalize () throws Throwable
	{
		System.err.println("CommonBean.finalize()");
		super.finalize();
	}
}
