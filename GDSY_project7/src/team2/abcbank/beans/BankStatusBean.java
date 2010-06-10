package team2.abcbank.beans;

import javax.faces.application.FacesMessage;

import team2.abcbank.ejb.shared.BankException;

public class BankStatusBean extends CommonBean
{
	private String status;

	public void setStatus(String status)
	{
		System.out.println("BankStatusBean.setStatus()");
		this.status = status;
	}

	public String getStatus() throws BankException
	{
		System.out.println("BankStatusBean.getStatus()");
		return loginBean.getBankIsOpen() ? "open" : "closed";
	}

	public void commit() throws BankException
	{
		accountManager.setOpen(status.equals("open"));
		addMessage(FacesMessage.SEVERITY_INFO, "De status is gewijzigd.");
	}
}
