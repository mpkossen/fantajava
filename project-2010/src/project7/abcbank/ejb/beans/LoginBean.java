package project7.abcbank.ejb.beans;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import project7.abcbank.ejb.entities.Account;
import project7.abcbank.ejb.entities.Status;
import project7.abcbank.ejb.shared.*;


@Stateless(name = "LoginBean")
@Remote(LoginBeanIF.class)
public class LoginBean implements LoginBeanIF, Serializable
{
	@Resource(mappedName = "ConnectionFactory")
	private QueueConnectionFactory queueFactory;

	@Resource(mappedName = TransactionManager.QUEUE_NAME)
	private Queue transactionQueue;

	@PersistenceContext(name = "ABC-BANK")
	private EntityManager em;

	@Resource
	private SessionContext sc;

	@Override
//	@PermitAll
	public boolean getBankIsOpen() throws BankException
	{
//		System.out.println("######" + sc.getCallerPrincipal());
		try
		{
			Status status = Status.getSingleStatus(em);

			// DEBUG-intermezzo: initialiseer database indien leeg
			if (status == null)
			{
				try
				{
					Status newStatus = new Status();
					newStatus.setBalance(0);
					newStatus.setBankIsOpen(true);
					em.persist(newStatus);

					Account account = new Account();
					account.setIsManager(true);
					account.setBalance(0);
					account.setLimit(0);
					account.setUsername("a");
					account.setPassword("a");
					account.setSalt(1);
					em.persist(account);

					Account account2 = new Account();
					account2.setIsManager(false);
					account2.setBalance(0);
					account2.setLimit(0);
					account2.setUsername("b");
					account2.setPassword("b");
					account2.setSalt(1);
					em.persist(account2);

					em.flush();
				}
				catch (BankException e)
				{
					e.printStackTrace();
				}
				status = Status.getSingleStatus(em);
			}
			// einde DEBUG-intermezzo

			return status.getBankIsOpen();
		}
		catch (Exception e)
		{
			throw new BankException("an unknown exception occured");
		}
	}

	@Override
//	@PermitAll
	public boolean getTransactionManagerIsIdle() throws BankException
	{
		try
		{
			QueueConnection connection = queueFactory.createQueueConnection();
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueBrowser browser = session.createBrowser(transactionQueue);
			connection.start();
			boolean isIdle = !browser.getEnumeration().hasMoreElements();
			session.close();
			connection.stop();
			return isIdle;
		}
		catch (JMSException e)
		{
			e.printStackTrace();
			throw new BankException("queue connection error");
		}
	}

	@Override
//	@PermitAll
	public AccountData login(String username, String password, long salt) throws BankException
	{
		Account account = Account.getByUsername(em, username);
		Status status = Status.getSingleStatus(em);
		if (account == null)
			throw new BankException("No such account");
		long oldSalt = account.getSalt();
		if (salt <= oldSalt)
			throw new BankException("Invalid salt");
		byte[] dws = MD5.hash(account.getPassword() + salt);
		if (!password.equals(MD5.encode(dws)))
			throw new BankException("Invalid password");
		if (!account.getIsManager() && status != null && !status.getBankIsOpen())
			throw new BankException("Bank is closed");
		account.setSalt(salt);
		em.merge(account);
		AccountData ad = new AccountData();
		ad.setAccountNumber(account.getAccountNumber());
		ad.setIsManager(account.getIsManager());
		return ad;
	}
}
