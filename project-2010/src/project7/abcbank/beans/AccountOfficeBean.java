package project7.abcbank.beans;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import project7.abcbank.ejb.shared.BankException;
import project7.abcbank.ejb.shared.TransactionData;


public class AccountOfficeBean extends CommonBean
{
	private String subview = "Withdraw"; // default subview

	public void setSubview(ActionEvent event)
	{
		this.subview = (String) event.getComponent().getAttributes().get("to");
	}

	public String getSubview()
	{
		return "subviews/" + subview + ".jsp";
	}

	public String getBalance()
	{
		System.out.println("AccountOfficeBean.getBalance()");
		try
		{
			return CommonBean.formatAmountInCents(accountOffice.getDetails(accountNumber).getBalance());
		}
		catch (BankException e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public TransactionTableEntry[] getPendingTransactions()
	{
		System.out.println("AccountOfficeBean.getPendingTransactions()");
		try
		{
			return convertToTransactionTableEntry(accountOffice.getPendingTransactions(accountNumber));
		}
		catch (BankException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * beetje lelijk, maargoed dat is JSF
	 */
	public static TransactionTableEntry[] convertToTransactionTableEntry(TransactionData[] data)
	{
		TransactionTableEntry[] ret = new TransactionTableEntry[data.length];
		for (int i = 0; i < data.length; i++)
		{
			TransactionTableEntry e = new TransactionTableEntry();
			e.from = "" + (data[i].getFromAccount() < 0 ? "storting" : data[i].getFromAccount());
			e.to = "" + (data[i].getToAccount() < 0 ? "opname" : data[i].getToAccount());
			e.amount = CommonBean.formatAmountInCents(data[i].getAmount());
			e.dateCreated = CommonBean.formatCalendar(data[i].getTransactionCreatedTime());
			e.dateFinished = data[i].getTransactionFinishedTime() == null ? null : CommonBean.formatCalendar(data[i].getTransactionFinishedTime());
			ret[i] = e;
		}
		return ret;
	}

	public boolean getHasPendingTransactions()
	{
		TransactionData[] pt = null;
		try
		{
			pt = accountOffice.getPendingTransactions(accountNumber);
		}
		catch (BankException e)
		{
			e.printStackTrace();
		}
		return pt != null && pt.length > 0;
	}

	public void sync() throws BankException
	{
		System.out.println("AccountOfficeBean.sync()");
		accountOffice.sync(accountNumber);
		addMessage(FacesMessage.SEVERITY_INFO, "De transacties zijn afgehandeld. Er staan geen pending transacties meer.");
	}
}
