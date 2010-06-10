package team2.abcbank.ejb.shared;

import team2.abcbank.ejb.entities.Account;

/**
 * Entity!
 * 
 * @author ejvos
 */
public interface AccountIF extends UserIF
{
	public long getAccountNumber();

	public void setAccountNumber(long number) throws BankException;

	public long getBalance();

	public void setBalance(long balance) throws BankException;

	public long getLimit();

	public void setLimit(long limit) throws BankException;

	public boolean getIsManager();

	public void setIsManager(boolean isManager);
}
