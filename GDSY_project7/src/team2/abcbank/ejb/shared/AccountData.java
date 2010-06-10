package team2.abcbank.ejb.shared;

import java.io.Serializable;

/**
 * data object
 * 
 * @author ejvos
 */
public class AccountData implements Serializable
{
	private String username = null;
	private long accountNumber = 0L;
	private long balance = 0L;
	private long limit = 0L;
	private boolean isManager = false;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public long getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public long getBalance()
	{
		return balance;
	}

	public void setBalance(long balance)
	{
		this.balance = balance;
	}

	public long getLimit()
	{
		return limit;
	}

	public void setLimit(long limit)
	{
		this.limit = limit;
	}

	public void setIsManager(boolean isManager)
	{
		this.isManager = isManager;
	}

	public boolean getIsManager()
	{
		return isManager;
	}
}
