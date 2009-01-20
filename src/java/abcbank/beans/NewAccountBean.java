package abc.bank.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import abc.bank.jaas.MyPrincipal;

import efg.jpa.bank.AccountManager;

public class NewAccountBean extends BankManagerBean{
	private String error = "";
	private FacesContext ctx = FacesContext.getCurrentInstance();
	private String view = "subview-choice:subview-new-account:new-account-form:";
	private FacesMessage n = new FacesMessage("*");
	private String mesj = "";
	
	public NewAccountBean()
	{
		System.out.println("NewAccountBean()");
	}
	
	/*****************************************************************************
	 * ActionListener
	 ****************************************************************************/
	public void doNewAccount(ActionEvent ae)
	{
		System.out.println("NewAccountBean.doNewAccount()");
		if(limit.equals(""))
		{
			error+="<br>Limit is a required field!";
			ctx.addMessage(view+"input-new-limit", n);
		}
		else 
		{
			try
			{
				double nLimit = Double.parseDouble(limit);
			}
			catch(Exception e)
			{
				error+="<br>Limit must be numeric";
				ctx.addMessage(view+"input-new-limit", n);
			}
		}
		if(name.equals(""))
		{
			error+="<br>Name is a required field!";
			ctx.addMessage(view+"input-new-name", n);
		}
		else if(name.equals("beheerder"))
		{
			error+="<br>Disallow to use 'beheerder' for name!";
			ctx.addMessage(view+"input-new-name", n);
		}
		if(pass.equals("") || pass2.equals(""))
		{
			error+="<br>Password is a required field!";
			ctx.addMessage(view+"input-new-pass", n);
			ctx.addMessage(view+"input-check-pass", n);
		}
		else if(!pass.equals(pass2))
		{
			error+="<br>Password must be same!";
			ctx.addMessage(view+"input-new-pass", n);
			ctx.addMessage(view+"input-check-pass", n);
		}
		if(!error.equals(""))
		{
			setError(error);
		}
		else
		{
			double newLimit = Double.parseDouble(limit);
			try
			{
				MyPrincipal mp = (MyPrincipal) ex.getUserPrincipal();
				String s = mp.getAccountManager().newAccount(newLimit, name, pass);
				System.out.println("New account created with account number "+s);
				mesj = "New account created with "+s;
				clear();
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public String getMesj()//message om de nieuwe gemaakt accountnr te tonen
	{
		return mesj;
	}
	
	@Override
	protected void finalize() throws Throwable
	{
	    System.err.println("NewAccountBean.finalize()");
	    super.finalize();
	}
}
