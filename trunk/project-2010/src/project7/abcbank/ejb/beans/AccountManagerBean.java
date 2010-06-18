package project7.abcbank.ejb.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import project7.abcbank.ejb.entities.Account;
import project7.abcbank.ejb.entities.Status;
import project7.abcbank.ejb.entities.Transaction;
import project7.abcbank.ejb.shared.AccountData;
import project7.abcbank.ejb.shared.AccountIF;
import project7.abcbank.ejb.shared.AccountManagerIF;
import project7.abcbank.ejb.shared.BankException;
import project7.abcbank.ejb.shared.TransactionData;


@Stateless(name = "AccountManagerBean")
@Remote(AccountManagerIF.class)
public class AccountManagerBean implements AccountManagerIF, Serializable
{
	@PersistenceContext(unitName = "ABC-BANK")
	EntityManager em = null;

	@Override
	public AccountData getAccount(long accountNumber) throws BankException
	{
		System.out.println("getAccount: " + accountNumber);
		AccountIF a = Account.getByAccountNumber(em, accountNumber);
		if (a == null)
			throw new BankException("could not find an account with this number");
		AccountData ad = new AccountData();
		ad.setAccountNumber(a.getAccountNumber());
		ad.setBalance(a.getBalance());
		ad.setLimit(a.getLimit());
		ad.setUsername(a.getUsername());
		return ad;
	}

	@Override
	public TransactionData[] getTransactions(long accountNumber) throws BankException
	{
		System.out.println("getTransactions: " + accountNumber);
		Account a = Account.getByAccountNumber(em, accountNumber);
		if (a == null)
			throw new BankException("could not find an account with this number");
		Query query = em.createQuery("FROM Transaction WHERE (fromAccount = ?1 OR toAccount = ?1)").setParameter(1, a);
		List list = query.getResultList();
		ArrayList<TransactionData> tdList = new ArrayList<TransactionData>(list.size());
		for (Object obj : list)
		{
			Transaction t = (Transaction) obj;
			TransactionData td = new TransactionData();
			td.setAmount(t.getAmount());
			td.setFromAccount(t.getFromAccount() == null ? -1 : t.getFromAccount().getAccountNumber());
			td.setToAccount(t.getToAccount() == null ? -1 : t.getToAccount().getAccountNumber());
			td.setTransactionCreatedTime(t.getTransactionCreatedTime());
			td.setTransactionFinishedTime(t.getTransactionFinishedTime());
			tdList.add(td);
		}
		return tdList.toArray(new TransactionData[0]);
	}

	@Override
	public void newAccountManager(String username, String password) throws BankException
	{
		System.out.println("new Account Manager: " + username + " with password: " + password);
		if (username == null || password == null)
			throw new BankException("no null values please");
		Account old = Account.getByUsername(em, username);
		if (old != null)
			throw new BankException("user already exists in the database");
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		account.setIsManager(true);
		account.setBalance(0);
		account.setSalt(0);
		account.setLimit(0);
		em.persist(account); // Update de Database.
	}

	@Override
	public AccountData newAccountOffice(long limitInCents, String username, String password) throws BankException
	{
		System.out.println("newAccountOffice: " + username + " with password " + password + " and limit " + limitInCents);
		if (username == null || password == null)
			throw new BankException("no null values please");
		if (limitInCents < 0)
			throw new BankException("limitInCents must be positive");
		Account old = Account.getByUsername(em, username);
		if (old != null)
			throw new BankException("user already exists in the database");
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		account.setIsManager(false); // geen AccountManager dus false
		account.setBalance(0);
		account.setSalt(0);
		account.setLimit(limitInCents);
		em.persist(account); // Database updaten.
		AccountData ad = new AccountData();
		ad.setAccountNumber(account.getAccountNumber());
		return ad;
	}

	@Override
	public void setOpen(boolean bankIsOpen) throws BankException
	{
		System.out.println("setOpen: " + bankIsOpen);
		Status status = Status.getSingleStatus(em);
		status.setBankIsOpen(bankIsOpen);
		em.merge(status);
	}
}