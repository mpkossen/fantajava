package abcbank.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.text.DateFormat;
import java.util.Date;
import abc.bank.jaas.MyPrincipal;

public class CheckAccountBean extends BankManagerBean{
	
	/*****************************************************************************
	 * Attribute
	 ****************************************************************************/
	private FacesContext ctx = FacesContext.getCurrentInstance();
	private String view = "subview-choice:subview-check-account:form-check-account:";
	private FacesMessage n = new FacesMessage("!");
	private String error="";
	
	/*****************************************************************************
	 * Constructor
	 ****************************************************************************/
	public CheckAccountBean()
	{
		System.out.println("CheckAccountBean()");
	}
	
	/*****************************************************************************
	 * ActionListener
	 ****************************************************************************/
	
	public void doCheckAccount(ActionEvent ae)
	{
		error="";
		System.out.println("CheckAccountBean.doCheckAccount()");
		if(accountnr.equals(""))
		{
			ctx.addMessage(view+"input-check-account", n);
			setError("Accountnr is a required field!");
		}
		else
		{
			try
			{
				int i = Integer.parseInt(accountnr);
				String[][] ret = null;
				MyPrincipal mp = (MyPrincipal)ex.getUserPrincipal();
				try
				{
					getTransactie();
					if(getTransactie().length<0)
					{
						error="No transaction found!";
					}
				}
				catch (Exception e) {
					// TODO: handle exception
					error="No such account!";
				}
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				error="Accountnr must be numeric!";
			}
			
			if(!error.equals(""))setError(error);
			
		}
	}
	
	public Transactie[] getTransactie()
	{
		System.out.println("CheckAccountBean.getTransactie()");
		try
		{
			Transactie[] a = new Transactie[(((MyPrincipal)ex.getUserPrincipal()).getAccountManager().getTransactions(accountnr)).length];
			int i = 0;
			for(String[] b: ((MyPrincipal)ex.getUserPrincipal()).getAccountManager().getTransactions(accountnr))
			{
//				String t1 = DateFormat.getInstance().format(b[4]+"L");
//				String t2 = DateFormat.getInstance().format(b[5]+"L");
//				System.out.println("Time: "+t1 + "  ---  " + t2);
//				a[i] = new Transactie(b[0],b[1],b[2],b[3],t1,t2);
				try{
					System.out.println("---------------------> "+DateFormat.getInstance().format(b[4]));
				}
				catch (Exception e) {
					// TODO: handle exception
				}
				
				a[i] = new Transactie(b[0],b[1],b[2],b[3],b[4],b[5]);
				i++;
			}
			return a;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Geen transactie");
			error="No transaction found!";
		}
		if(!error.equals(""))setError(error);
		
		return null;
	}
	
	public class Transactie
	{
		private String id;
		private String from;
		private String to;
		private String amount;
		private String transactionTime;
		private String transferTime;
		
		public Transactie(String id,String from,String to, String amount, String transactionTime,String transferTime)
		{
			this.id = id;
			this.from = from;
			this.to = to;
			this.amount = amount;
			this.transactionTime = transactionTime;
			this.transferTime = transferTime;
		}
		
		public String getId(){return id;}
		public String getFrom(){return from;}
		public String getTo(){return to;}
		public String getAmount(){return amount;}
		public String getTransactionTime()
		{
			try{
				transactionTime = DateFormat.getInstance().format(new Date(transactionTime));
				System.out.println("---------------------> "+transactionTime);
				return transactionTime;
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			
			return transactionTime;
		}
		public String getTransferTime()
		{
			try
			{
				transferTime = DateFormat.getInstance().format(new Date(transferTime));
				System.out.println("---------------------> "+transferTime);
				return transferTime;
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			return transferTime;
		}
		
	}
	
	protected void finalize() throws Throwable
	{
	    System.err.println("CheckAccountBean.finalize()");
	    super.finalize();
	}
}
