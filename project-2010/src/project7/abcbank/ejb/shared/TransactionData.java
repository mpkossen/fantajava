package project7.abcbank.ejb.shared;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TransactionData implements Serializable
{
	private long toAccount = -1;
	private long fromAccount = -1;
	private long amount = -1;
	private Calendar transactionCreatedTime = null;
	private Calendar transactionFinishedTime = null;
	public static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

	public long getToAccount()
	{
		return toAccount;
	}

	public void setToAccount(long toAccount)
	{
		this.toAccount = toAccount;
	}

	public long getFromAccount()
	{
		return fromAccount;
	}

	public void setFromAccount(long fromAccount)
	{
		this.fromAccount = fromAccount;
	}

	public long getAmount()
	{
		return amount;
	}

	public void setAmount(long amount)
	{
		this.amount = amount;
	}

	public Calendar getTransactionCreatedTime()
	{
		return transactionCreatedTime;
	}

	public void setTransactionCreatedTime(Calendar transactionCreatedTime)
	{
		this.transactionCreatedTime = transactionCreatedTime;
	}

	public Calendar getTransactionFinishedTime()
	{
		return transactionFinishedTime;
	}

	public void setTransactionFinishedTime(Calendar transactionFinishedTime)
	{
		this.transactionFinishedTime = transactionFinishedTime;
	}

	public String toString()
	{
		String ret = "TransactionData:\n";
		if (fromAccount >= 0)
			ret += "From:\t" + fromAccount + "\n";
		else
			ret += "From:\tnull\n";
		if (toAccount >= 0)
			ret += "To:\t" + toAccount + "\n";
		else
			ret += "To:\tnull\n";
		ret += "Amount:\t" + amount + "\n";
		if (transactionCreatedTime != null)
			ret += "Create:\t" + dateFormat.format(transactionCreatedTime) + "\n";
		else
			ret += "Create:\tnull\n";
		if (transactionFinishedTime != null)
			ret += "Finish:\t" + dateFormat.format(transactionFinishedTime) + "\n";
		else
			ret += "Finish:\tnull\n";
		return ret;
	}
}
