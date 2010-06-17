package project7.abcbank.ejb.shared;

import java.util.Calendar;

import project7.abcbank.ejb.entities.Account;

public interface TransactionIF
{
	public long getTransactionID();

	public void setTransactionID(long transactionID);

	public Account getToAccount();

	public void setToAccount(Account toAccount) throws BankException;

	public Account getFromAccount();

	public void setFromAccount(Account fromAccount) throws BankException;

	public long getAmount();

	public void setAmount(long amount) throws BankException;

	public Calendar getTransactionCreatedTime();

	public void setTransactionCreatedTime(Calendar transactionCreatedTime);

	public Calendar getTransactionFinishedTime();

	public void setTransactionFinishedTime(Calendar transactionFinishedTime);

}
