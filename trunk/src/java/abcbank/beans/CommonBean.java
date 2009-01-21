package abcbank.beans;

import javax.persistence.EntityManager;
import abcbank.jaas.BankLoginModule;
import efg.jpa.bank.db.Database;
import efg.jpa.bank.db.Status;

public class CommonBean
{
	private static final long serialVersionUID = 1L;
	protected boolean isClosed = false;
	private static String title = "ABC Bank";
	private static String error = "";
	private static String statuss = "";
	private static String exception = "";
	private static String footer = "ABC Bank 2008 - 2009";
	private static final EntityManager entityManager = Database.getEntityManager("AccountManager");
	public static String ob  = "ABC- Bank is open, you can log in to do business.";
	public static String cb  = "ABC- Bank is temporary closed, only bank managers can log in to open the ABC Bank.";

	public CommonBean()
	{
		System.out.println("CommonBean()");
	}
	
	public String getStatuss()
	{
		System.out.println("CommonBean.getStatus()");
		Status bankstatus = entityManager.find(Status.class, "0");
		switch (Integer.parseInt(bankstatus.getBank()))
	    {
	        case 0:
	        	statuss = ob; 
	        	isClosed = false;
	        	break;
	        case 1:
	        	statuss = cb;
	        	isClosed = true;
	        	break;
	        case 2:
	        	statuss = ob; 
	        	isClosed = false;
	        	break;
	        case 3:
	        	statuss = cb;
	        	isClosed = true;
	        	break;
	    }
	     return statuss;    
	}
	
	public String getTitle()
	{
		System.out.println("CommonBean.getTitle()");
		return title;
	}

	public String getError()
	{
		System.out.println("CommonBean.getMessage()");
		return error;
	}

	public String getException()
	{
		System.out.println("CommonBean.getException()");
		return exception;
	}

	public String getFooter()
	{
		/*
		//To Format the date
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat d = new SimpleDateFormat("yyyy");
        footer = d.format(cal.getTime());
        */
		System.out.println("CommonBean.getFooter()");
		return footer;
	}
	
	public void setTitle(String newTitle)
	{
		System.out.println("CommonBean.setTitle(" + newTitle + ")");
		title = newTitle;
	}

	public void setError(String newError)
	{
		System.out.println("CommonBean.setError(" + newError + ")");
		error = newError;
	}

	public void setException(String newException)
	{
		System.out.println("CommonBean.setException(" + newException + ")");
		exception = newException;
	}

	public void setFooter(String newFooter)
	{
		System.out.println("CommonBean.setFooter(" + newFooter + ")");
		footer = newFooter;
	}
	
	public boolean getRenderError()
	{
		return error != "";
	}

	@Override
	protected void finalize() throws Throwable
	{
		System.err.println("CommonBean.finalize()");
		super.finalize();
	}
}