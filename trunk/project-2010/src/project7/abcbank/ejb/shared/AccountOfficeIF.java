package project7.abcbank.ejb.shared;

public interface AccountOfficeIF
{
	public AccountData getDetails(long accountNumber) throws BankException;

	public TransactionData[] getPendingTransactions(long accountNumber) throws BankException;

	/**
	 * @throws BankException when toAccountNumber does not exist
	 * @throws BankException when amountInCents <= 0
	 * @throws BankException when limit has been reached
	 */
	public void transfer(long fromAccountNumber, long toAccountNumber, long amountInCents) throws BankException;

	/**
	 * @throws BankException when amountInCents <= 0
	 * @throws BankException when limit has been reached
	 */
	public void withdraw(long accountNumber, long amountInCents) throws BankException;

	/**
	 * @throws BankException when amountInCents <= 0
	 */
	public void deposit(long accountNumber, long amountInCents) throws BankException;

	/**
	 * send all pending transactions to the TransactionManager
	 * 
	 * @throws BankException when failing to submit the transaction to the queue
	 */
	public void sync(long accountNumber) throws BankException;
}
