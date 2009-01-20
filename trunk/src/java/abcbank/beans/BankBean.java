package abcbank.beans;

import java.security.Principal;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class BankBean
{	
	private ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	
	public boolean getRenderManager()
	{
		System.out.println(ec.isUserInRole("AccountManager"));
		return ec.isUserInRole("AccountManager");
	}
	
	public boolean getRenderOffice()
	{
		
		return ec.isUserInRole("OfficeManager");
	}
}
