package project7.abcbank.ejb.beans;

import java.util.Calendar;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import project7.abcbank.ejb.entities.*;
import project7.abcbank.ejb.shared.*;


@MessageDriven(activationConfig =
{
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = TransactionManager.QUEUE_NAME)
})
public class TransactionManager implements MessageListener
{
	public final static String QUEUE_NAME = "queue/transactionQueue";

	@PersistenceContext(name = "ABC-BANK")
	private EntityManager em;

	@Resource
	private SessionContext sc;

	@Override
	public void onMessage(Message message)
	{
		TransactionData[] batch = null;
		try
		// Try to convert the message to a transactionbatch
		{
			ObjectMessage messageObject = (ObjectMessage) message;
			Object messageData = messageObject.getObject();
			batch = (TransactionData[]) messageData;
		}
		catch (Exception e)
		{
			// If a transaction didn't complete, we are to make it "go away" but not before we print.
			System.out.println("Bad transactionbatch, ignoring.");
			System.out.println("Exception:\n" + e.toString());
			return;
		}
		doTransactionBatch(batch);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void doTransactionBatch(TransactionData[] batch)
	{
		try
		{
			for (TransactionData data : batch)
			{
				if (!transactionIsValid(data))
					throw new BankException("recieved an invalid transactiondata object");
				doTransaction(data);
			}
		}
		catch (BankException e)
		{
			sc.setRollbackOnly();
			System.out.println("BankException encountered, performing a rollback of entire batch.");
			System.out.println("Exception:\n" + e.toString());
			return;
		}
	}

	/**
	 * This method checks whether the transaction is sane
	 */
	private boolean transactionIsValid(TransactionData td)
	{
		return (td.getFromAccount() >= 0 || td.getToAccount() >= 0) && td.getFromAccount() != td.getToAccount() && td.getAmount() > 0 && td.getTransactionCreatedTime() != null;
	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	private void doTransaction(TransactionData td) throws BankException
	{
		// Create a new Transaction entity and copy some values
		Transaction transaction = new Transaction();
		transaction.setAmount(td.getAmount());
		transaction.setTransactionCreatedTime(td.getTransactionCreatedTime());

		// Retrieve the bans status
		Status status = Status.getSingleStatus(em);

		// Transfer
		if (td.getFromAccount() >= 0 && td.getToAccount() >= 0)
		{
			Account fromAccount = Account.getByAccountNumber(em, td.getFromAccount());
			Account toAccount = Account.getByAccountNumber(em, td.getToAccount());
			if (fromAccount == null || toAccount == null)
				throw new BankException("Invalid accountnumber supplied");

			// Check for limit
			if (-fromAccount.getLimit() > (fromAccount.getBalance() - td.getAmount()))
				throw new BankException("Insufficient funds");

			fromAccount.setBalance(fromAccount.getBalance() - td.getAmount());
			toAccount.setBalance(toAccount.getBalance() + td.getAmount());

			// Update the new transaction entity
			transaction.setFromAccount(fromAccount);
			transaction.setToAccount(toAccount);

			// Merge modified entities
			em.merge(fromAccount);
			em.merge(toAccount);
		}
		// Withdrawal
		else if (td.getFromAccount() >= 0)
		{
			Account fromAccount = Account.getByAccountNumber(em, td.getFromAccount());
			if (fromAccount == null)
				throw new BankException("Invalid accountnumber supplied");

			// Check for limit
			if (-fromAccount.getLimit() > (fromAccount.getBalance() - td.getAmount()))
				throw new BankException("Insufficient funds");

			fromAccount.setBalance(fromAccount.getBalance() - td.getAmount());

			// Update the banks balance
			status.setBalance(status.getBalance() - td.getAmount());

			// Update the new transaction entity
			transaction.setFromAccount(fromAccount);
			transaction.setToAccount(null);

			// Merge modified entities
			em.merge(fromAccount);
		}
		// Deposit
		else if (td.getToAccount() >= 0)
		{
			Account toAccount = Account.getByAccountNumber(em, td.getToAccount());
			if (toAccount == null)
				throw new BankException("Invalid accountnumber supplied");

			toAccount.setBalance(toAccount.getBalance() + td.getAmount());

			// Update the banks balance
			status.setBalance(status.getBalance() + td.getAmount());

			// Update the new transaction entity
			transaction.setFromAccount(null);
			transaction.setToAccount(toAccount);

			// Merge modified entities
			em.merge(toAccount);
		}
		// Something unexpected
		else
			throw new BankException("Invalid transaction data somehow passed our tests");

		// Setting finished time
		transaction.setTransactionFinishedTime(Calendar.getInstance());

		// Update the bank's balance
		em.merge(status);

		// Persisting the Transaction
		em.persist(transaction);

		if (td.getFromAccount() >= 0 && td.getToAccount() >= 0)
			System.out.println("MDB: transferred " + td.getAmount() + " from " + td.getFromAccount() + " to " + td.getToAccount());
		else if (td.getFromAccount() >= 0)
			System.out.println("MDB: withdrew " + td.getAmount() + " from " + td.getFromAccount());
		else if (td.getToAccount() >= 0)
			System.out.println("MDB: deposited " + td.getAmount() + " to " + td.getToAccount());
	}
}
