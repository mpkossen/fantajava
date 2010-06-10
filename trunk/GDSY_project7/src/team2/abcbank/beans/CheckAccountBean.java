package team2.abcbank.beans;

import javax.faces.application.FacesMessage;

import team2.abcbank.ejb.shared.AccountData;
import team2.abcbank.ejb.shared.BankException;

public class CheckAccountBean extends CommonBean
{
	private long accountnumber;
	private String pincode;
	private String name;
	private String balance, limit;
	private TransactionTableEntry[] transactions;
	private boolean checkingAccount;

	/**
	 * Retrieves information about the account and its transactions
	 * 
	 * @author Reinier Kip
	 * @throws BankException
	 */
	public void check() throws BankException
	{
		System.out.println("CheckAccountBean.check()");
		try
		{
			AccountData data = accountManager.getAccount(accountnumber);
			setName(data.getUsername());
			setBalance(CommonBean.formatAmountInCents(data.getBalance()));
			setLimit(CommonBean.formatAmountInCents(data.getLimit()));
			transactions = AccountOfficeBean.convertToTransactionTableEntry(accountManager.getTransactions(accountnumber));
			checkingAccount = true;
		}
		catch (BankException e)
		{
			addMessage(FacesMessage.SEVERITY_ERROR, "Fout bij het ophalen van account: " + e.getMessage());
			checkingAccount = false;
		}
	}

	public long getAccountnumber()
	{
		return accountnumber;
	}

	public void setAccountnumber(long accountnumber)
	{
		this.accountnumber = accountnumber;
	}

	public String getPincode()
	{
		return pincode;
	}

	public void setPincode(String pincode)
	{
		this.pincode = pincode;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getBalance()
	{
		return balance;
	}

	public void setBalance(String balance)
	{
		this.balance = balance;
	}

	public String getLimit()
	{
		return limit;
	}

	public void setLimit(String limit)
	{
		this.limit = limit;
	}

	public boolean isCheckingAccount()
	{
		return checkingAccount;
	}

	public void setCheckingAccount(boolean checkingAccount)
	{
		this.checkingAccount = checkingAccount;
	}

	public TransactionTableEntry[] getTransactions()
	{
		return transactions;
	}
}
