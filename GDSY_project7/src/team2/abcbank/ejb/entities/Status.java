package team2.abcbank.ejb.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import team2.abcbank.ejb.shared.StatusIF;

@Entity
@Table(name = "status")
public class Status implements StatusIF, Serializable
{
	public static Status getSingleStatus(EntityManager em)
	{
		Query query = em.createQuery("FROM Status s");
		List list = query.getResultList();
		if (list.size() < 1)
			return null;
		return (Status) list.get(0);
	}

	private long balance = 0; // The balance of the bank. Basically the amount of money (in cents) in the bank.
	private boolean bankIsOpen; // True or False depending on whether the bank is open or not. True represents open.
	private int id = 0;

	@Id
	@GeneratedValue
	@Column(name = "statusID")
	@Override
	public int getStatusID()
	{
		return id;
	}

	@Override
	public void setStatusID(int id)
	{
		this.id = id;
	}

	/**
	 * Returns the current balance of the bank.
	 * 
	 * @return the balance of the bank in cents
	 */
	@Override
	@Column(name = "balance")
	public long getBalance()
	{
		return balance;
	}

	/**
	 * Sets the balance of the bank.
	 * 
	 * @param balance the balance of the bank in cents
	 */
	@Override
	public void setBalance(long balance)
	{
		this.balance = balance;
	}

	/**
	 * Returns the status of the bank. True represents open, false represents closed.
	 * 
	 * @return the status of the bank
	 */
	@Override
	@Column(name = "bankIsOpen")
	public boolean getBankIsOpen()
	{
		return bankIsOpen;
	}

	/**
	 * Sets the status of the bank. True represents open, false represents closed.
	 * 
	 * @param status the status of the bank
	 */
	@Override
	public void setBankIsOpen(boolean status)
	{
		this.bankIsOpen = status;
	}
}
