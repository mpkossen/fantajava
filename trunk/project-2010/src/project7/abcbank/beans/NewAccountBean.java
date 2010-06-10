package project7.abcbank.beans;

import javax.faces.application.FacesMessage;

import project7.abcbank.ejb.shared.AccountData;
import project7.abcbank.ejb.shared.BankException;


public class NewAccountBean extends CommonBean
{
	private String newName, newPincode;
	private String newLimit;

	public void create()
	{
		try
		{
			long bedrag = CommonBean.getAmountInCents(newLimit);
			if (bedrag < 0)
				throw new BankException("Het limiet moet hoger zijn dan of gelijk zijn aan 0");
			AccountData accountData = accountManager.newAccountOffice(bedrag, newName, newPincode);
			addMessage(FacesMessage.SEVERITY_INFO, "Het aanmaken van uw account is geslaagd. Het nummer is: " + accountData.getAccountNumber() + ".");
		}
		catch (BankException e)
		{
			addMessage(FacesMessage.SEVERITY_ERROR, "Er is een fout opgetreden: " + e.getMessage());
		}
	}

	public void setNewName(String newName)
	{
		System.out.println("NewAccountBean.setName()");
		this.newName = newName;
	}

	public void setNewPincode(String newPincode)
	{
		System.out.println("NewAccountBean.setPincode()");
		this.newPincode = newPincode;
	}

	public void setNewLimit(String newLimit)
	{
		System.out.println("NewAccountBean.setLimit()");
		this.newLimit = newLimit;
	}

	public String getNewName()
	{
		System.out.println("NewAccountBean.getName()");
		return newName;
	}

	public String getNewLimit()
	{

		System.out.println("NewAccountBean.getLimit()");
		return newLimit;
	}

	public String getNewPincode()
	{
		System.out.println("NewAccountBean.getPincode()");
		return newPincode;
	}
}
