package project7.abcbank.ejb.shared;

public interface AccountManagerIF
{
	/**
	 * @throws BankException when account doesn't exist
	 */
	public AccountData getAccount(long accountNumber) throws BankException;

	/**
	 * @throws BankException when account doesn't exist
	 */
	public TransactionData[] getTransactions(long accountNumber) throws BankException;

	/**
	 * @throws BankException when username == null || password == null
	 * @throws BankException when username already exists
	 * @throws BankException when limitInCents < 0
	 */
	public AccountData newAccountOffice(long limitInCents, String username, String password) throws BankException;

	/**
	 * @throws BankException when username == null || password == null
	 * @throws BankException when username already exists
	 */
	public void newAccountManager(String username, String password) throws BankException;

	/**
	 * @throws BankException when bankIsOpen == true and bank is already open
	 * @throws BankException when bankIsOpen == false and bank is already closed
	 */
	public void setOpen(boolean bankIsOpen) throws BankException;
}
