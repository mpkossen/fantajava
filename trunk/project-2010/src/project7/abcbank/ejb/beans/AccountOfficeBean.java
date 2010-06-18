package project7.abcbank.ejb.beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import project7.abcbank.ejb.entities.Account;
import project7.abcbank.ejb.shared.AccountData;
import project7.abcbank.ejb.shared.AccountIF;
import project7.abcbank.ejb.shared.AccountOfficeIF;
import project7.abcbank.ejb.shared.BankException;
import project7.abcbank.ejb.shared.TransactionData;

@Stateful(name = "AccountOfficeBean")
@Remote(AccountOfficeIF.class)
public class AccountOfficeBean implements AccountOfficeIF, Serializable
{
	@PersistenceContext(name = "ABC-BANK")
	private EntityManager em;

	@Resource(mappedName = "ConnectionFactory")
	private QueueConnectionFactory jmsQueueFactory;

	@Resource(mappedName = TransactionManager.QUEUE_NAME)
	private Queue jmsTransactionQueue;

	private Vector<TransactionData> pendingTransactionQueue = new Vector<TransactionData>();

	@Override
	public void deposit(long accountNumber, long amountInCents) throws BankException
	{
		System.out.println("deposit: " + accountNumber + " " + amountInCents);
		if (amountInCents <= 0)
			throw new BankException("Requested amount should be more than 0.");

		AccountIF account = Account.getByAccountNumber(em, accountNumber);
		if (account == null)
			throw new BankException("account does not exist");

		TransactionData data = new TransactionData();
		data.setAmount(amountInCents);
		data.setFromAccount(-1);
		data.setToAccount(account.getAccountNumber());
		data.setTransactionCreatedTime(Calendar.getInstance());

		pendingTransactionQueue.add(data);
	}

	@Override
	public AccountData getDetails(long accountNumber) throws BankException
	{
		System.out.println("getDetails: " + accountNumber);
		AccountIF account = Account.getByAccountNumber(em, accountNumber);
		if (account == null)
			throw new BankException("account does not exist");

		AccountData data = new AccountData();
		data.setAccountNumber(account.getAccountNumber());
		data.setBalance(account.getBalance());
		data.setLimit(account.getLimit());
		data.setUsername(account.getUsername());

		return data;
	}

	@Override
	public TransactionData[] getPendingTransactions(long accountNumber) throws BankException
	{
		System.out.println("getPendingTransactions: " + accountNumber);
		return pendingTransactionQueue.toArray(new TransactionData[0]);
	}

	@Override
	public void sync(long accountNumber) throws BankException
	{
		System.out.println("sync: " + accountNumber);
		try
		{
			QueueConnection connection = jmsQueueFactory.createQueueConnection();
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueSender sender = session.createSender(jmsTransactionQueue);

			ObjectMessage message;

			message = session.createObjectMessage();
			message.setObject(pendingTransactionQueue.toArray(new TransactionData[0]));
			sender.send(message);

			pendingTransactionQueue.clear();
		}
		catch (JMSException e)
		{
			throw new BankException(e.getMessage());
		}
	}

	@Override
	public void transfer(long fromAccountNumber, long toAccountNumber, long amountInCents) throws BankException
	{
		System.out.println("transfer " + amountInCents + " from " + fromAccountNumber + " to " + toAccountNumber);
		if (amountInCents <= 0)
			throw new BankException("Request amount should be more than 0.");
		if (fromAccountNumber == toAccountNumber)
			throw new BankException("source account can't equal target account");

		AccountIF from = Account.getByAccountNumber(em, fromAccountNumber);
		AccountIF to = Account.getByAccountNumber(em, toAccountNumber);

		if (from == null)
			throw new BankException("source account does not exist");
		if (to == null)
			throw new BankException("target account does not exist");
		if (getNewBalance(from) - amountInCents < -from.getLimit())
			throw new BankException("Requested amount would produce a sub-limit outcome.");

		TransactionData data = new TransactionData();
		data.setAmount(amountInCents);
		data.setFromAccount(from.getAccountNumber());
		data.setToAccount(to.getAccountNumber());
		data.setTransactionCreatedTime(Calendar.getInstance());

		pendingTransactionQueue.add(data);
	}

	@Override
	public void withdraw(long accountNumber, long amountInCents) throws BankException
	{
		System.out.println("withdraw " + amountInCents + " from " + accountNumber);
		if (amountInCents <= 0)
			throw new BankException("Request amount should be more than 0.");
		AccountIF account = Account.getByAccountNumber(em, accountNumber);
		if (account == null)
			throw new BankException("account does not exist");

		if (getNewBalance(account) - amountInCents < -account.getLimit())
			throw new BankException("Requested amount would produce a sub-limit outcome.");

		TransactionData data = new TransactionData();
		data.setAmount(amountInCents);
		data.setFromAccount(account.getAccountNumber());
		data.setToAccount(-1);
		data.setTransactionCreatedTime(Calendar.getInstance());

		pendingTransactionQueue.add(data);
	}

	/**
	 * Calculates the balance after the pending transaction would have been executed
	 * 
	 * @return The balance after the pending transaction would have been executed
	 */
	private long getNewBalance(AccountIF account) throws BankException
	{
		System.out.println("getNewBalance " + account);
		long newBalance = account.getBalance();
		Iterator<TransactionData> i = pendingTransactionQueue.iterator();
		TransactionData data;
		while (i.hasNext())
		{
			data = i.next();
			if (data.getFromAccount() == -1)
				newBalance += data.getAmount();
			else
				newBalance -= data.getAmount();
		}
		return newBalance;
	}
}