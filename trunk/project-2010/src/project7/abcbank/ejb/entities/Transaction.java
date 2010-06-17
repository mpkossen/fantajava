package project7.abcbank.ejb.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.FetchType;

import project7.abcbank.ejb.shared.BankException;
import project7.abcbank.ejb.shared.TransactionIF;


/**
 * Each instance of this class represents a transaction between two accounts or on one account. If both relations are set it's a transaction between two accounts. If only one relation is set then it's
 * a transaction on a single account. If the fromAccount-relation is set it's a withdrawal. And if the toAccount-relation is set it's a deposit.
 * 
 */
@Entity
@Table(name = "transaction")
public class Transaction implements TransactionIF, Serializable
{
	private long transactionID = 0; // Unique key for each transaction
	private long amount = 0; // Amount of money in cents, must be positive.
	private Account fromAccount = null; // Account to subtract the amount from.
	private Account toAccount = null; // Account to add the amount to.
	private Calendar createdTime = null; // Time this transaction was created.
	private Calendar finishedTime = null; // Time this transaction was committed.

	@Override
	@Id
	@GeneratedValue
	@Column(name = "transactionID")
	public long getTransactionID()
	{
		return transactionID;
	}

	/**
	 * This method should only be called by the Persistence Engine. JPA uses the constructor without parameters and then uses all the set-Methods to fill in the data found in the database. Since the
	 * transactionID represents the primary key it should not be used by our own code.
	 * 
	 * @param transactionID the key with which can be referred to this transaction
	 */
	@Override
	public void setTransactionID(long transactionID)
	{
		this.transactionID = transactionID;
	}

	/**
	 * Returns the amount of money for this transaction
	 * 
	 * @return the amount to be transferred in cents
	 */
	@Override
	@Column(name = "amount")
	public long getAmount()
	{
		return amount;
	}

	/**
	 * The transaction-amount must be a positive number, else it would be possible to withdraw or deposit a negative number or even 'steal' money fro another account.
	 * 
	 * @param amount the amount to be transferred in cents.
	 */
	@Override
	public void setAmount(long amount) throws BankException
	{
		if (amount > 0)
			this.amount = amount;
		else
			throw new BankException("Transaction amount must be a positive number larger than zero.");
	}

	/**
	 * The account the amount is withdrawn from. If fromAccount is null the transaction represents a deposit.
	 * 
	 * @return the account to withdraw or transfer the money (amount) from
	 */
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	public Account getFromAccount()
	{
		return fromAccount;
	}

	/**
	 * Sets the FromAccount for this transaction. If the fromAccount equals null and toAccount doesn't equal null then it is considered a deposit.
	 * 
	 * @param fromAccount the account the amount should be subtracted from
	 */
	@Override
	public void setFromAccount(Account fromAccount) throws BankException
	{
		this.fromAccount = fromAccount;
	}

	/**
	 * The account the amount is deposited on. If toAccount is null the transaction represents a withdrawal.
	 * 
	 * @return the account to deposit or transfer the money (amount) to
	 */
	@Override
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	public Account getToAccount()
	{
		return toAccount;
	}

	/**
	 * Sets the ToAccount for this transaction. If the toAccount equals null and fromAccount doesn't equal null then it is considered a withdrawal.
	 * 
	 * @param toAccount the account the amount should be subtracted from
	 */
	@Override
	public void setToAccount(Account toAccount) throws BankException
	{
		this.toAccount = toAccount;
	}

	/**
	 * Returns the time this transaction was created. This shouldn't represent the time this object was created, but it should represent when the transaction with this primary key (transactionID) was
	 * saved to the transaction-queue.
	 * 
	 * @return the time this transaction was created
	 */
	@Override
	@Column(name = "createdTime")
	public Calendar getTransactionCreatedTime()
	{
		return createdTime;
	}

	/**
	 * This method should only be called by the Persistence Engine or during the initial creation of the transaction. Hence it is only possible to use this setter if the current value of createdTime
	 * is null.
	 * 
	 * JPA uses the constructor without parameters and then uses all the set-Methods to fill in the data found in the database.
	 * 
	 * @param transactionCreatedTime
	 */
	@Override
	public void setTransactionCreatedTime(Calendar transactionCreatedTime)
	{
		this.createdTime = transactionCreatedTime;
	}

	/**
	 * Returns the time this transaction was and executed and thus finished. This shouldn't represent the time this object was created, but it should represent when the transaction with this primary
	 * key (transactionID) was removed from the transaction-queue.
	 * 
	 * @return
	 */
	@Override
	@Column(name = "finishedTime")
	public Calendar getTransactionFinishedTime()
	{
		return finishedTime;
	}

	/**
	 * This method should only be called by the Persistence Engine or during the execution of the transaction. Hence it is only possible to use this setter if the current value of finishedTime is
	 * null.
	 * 
	 * JPA uses the constructor without parameters and then uses all the set-Methods to fill in the data found in the database.
	 * 
	 * @param transactionFinishedTime
	 */
	@Override
	public void setTransactionFinishedTime(Calendar transactionFinishedTime)
	{
		this.finishedTime = transactionFinishedTime;
	}
}
