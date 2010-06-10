package project7.abcbank.ejb.shared;

public interface LoginBeanIF
{
	/**
	 * @throws BankException when login failed
	 */
	public AccountData login(String username, String password, long salt) throws BankException;

	/**
	 * @returns whether the bank is closed by an accountmanager
	 * @throws BankException no status information available
	 */
	public boolean getBankIsOpen() throws BankException;

	/**
	 * @returns whether the transactionmanager is idle
	 * @throws BankException no status information available
	 */
	public boolean getTransactionManagerIsIdle() throws BankException;
}
