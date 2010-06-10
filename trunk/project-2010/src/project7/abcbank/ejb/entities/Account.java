package project7.abcbank.ejb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import project7.abcbank.ejb.shared.AccountIF;
import project7.abcbank.ejb.shared.BankException;


import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "account")
public class Account implements AccountIF, Serializable
{
	public static Account getByUsername(EntityManager em, String username)
	{
		Query query = em.createQuery("FROM Account WHERE username = ?").setParameter(1, username);
		List list = query.getResultList();
		if (list.size() < 1)
			return null;
		return (Account) list.get(0);
	}

	/**
	 * guarantees you won't get an accountmanager
	 */
	public static Account getByAccountNumber(EntityManager em, long accountNumber)
	{
		Account a = em.find(Account.class, accountNumber);
		if (a != null && !a.getIsManager())
			return a;
		return null;
	}

	private long accountNumber = 0;
	private long balance = 0;
	private long limit = 0;
	private long salt = 0;
	private boolean isManager = false;
	private String username = "";
	private String password = "";

	@Column(name = "balance")
	public long getBalance()
	{
		return balance;
	}

	public void setBalance(long balance) throws BankException
	{
		this.balance = balance;
	}

	@Column(name = "manager")
	public boolean getIsManager()
	{
		return isManager;
	}

	public void setIsManager(boolean isManager)
	{
		this.isManager = isManager;
	}

	@Column(name = "limit")
	public long getLimit()
	{
		return limit;
	}

	public void setLimit(long limit) throws BankException
	{
		this.limit = limit;
	}

	@Column(name = "salt")
	public long getSalt()
	{
		return salt;
	}

	public void setSalt(long salt) throws BankException
	{
		this.salt = salt;
	}

	@Column(name = "username")
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username) throws BankException
	{
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Id
	@GeneratedValue
	@Column(name = "accountNumber")
	public long getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) throws BankException
	{
		this.accountNumber = accountNumber;
	}
}
