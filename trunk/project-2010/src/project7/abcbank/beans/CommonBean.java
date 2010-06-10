package project7.abcbank.beans;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;

import project7.abcbank.ejb.shared.AccountManagerIF;
import project7.abcbank.ejb.shared.AccountOfficeIF;
import project7.abcbank.ejb.shared.BankException;
import project7.abcbank.ejb.shared.LoginBeanIF;


public class CommonBean extends EvenMoreCommonBean
{
	protected LoginBeanIF loginBean = null;
	protected AccountManagerIF accountManager = null;
	protected AccountOfficeIF accountOffice = null;
	protected long accountNumber = -1;

	public CommonBean()
	{
		BankAccessBean bab = (BankAccessBean) getBean("BankAccessBean");
		if (bab != null)
		{
			loginBean = bab.getLoginBean();
			accountManager = bab.getAccountManager();
			accountOffice = bab.getAccountOffice();
			accountNumber = bab.getAccountNumber();
		}
	}

	public static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
	public static NumberFormat numberFormat = new DecimalFormat("\u00A4 0.00");
	static
	{
		numberFormat.setCurrency(Currency.getInstance("EUR"));
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);
	}

	public static String formatCalendar(Calendar calendar)
	{
		return dateFormat.format(calendar.getTime());
	}

	public static String formatAmountInCents(long amountInCents)
	{
		// return numberFormat.format(amountInCents / 100); // we still get the double imprecision.... sigh
		return Currency.getInstance("EUR").getSymbol() + " " + penniesToString(amountInCents);
	}

	public static long getAmountInCents(String string) throws BankException
	{
		try
		{
			// BigDecimal bd = new BigDecimal(string);
			// bd = bd.setScale(2);
			// bd = bd.multiply(new BigDecimal(100));
			// return bd.longValueExact();
			return parseLongPennies(string);
		}
		catch (Exception e)
		{
			throw new BankException("ongeldig bedrag opgegeven");
		}
	}

	/**
	 * convert a String into long pennies. It ignores invalid chars, lead trail, embedded spaces. Dash is treated as a minus sign. 0 or 2 decimal places are permitted.
	 * 
	 * @param numStr String to be parsed.
	 * 
	 * @return long in pennies.
	 * 
	 * @exception NumberFormatException if the number is too big to fit in a long.
	 */
	public static long parseLongPennies(String numStr)
	{
		numStr = numStr.trim();
		// strip commas, spaces, + etc
		StringBuffer b = new StringBuffer(numStr.length());
		boolean negative = false;
		int decpl = -1;
		for (int i = 0; i < numStr.length(); i++)
		{
			char c = numStr.charAt(i);
			switch (c)
			{
			case '-':
				negative = true;
				break;
			case '.':
				if (decpl == -1)
				{
					decpl = 0;
				}
				else
				{
					throw new NumberFormatException("more than one decimal point");
				}
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				if (decpl != -1)
				{
					decpl++;
				}
				b.append(c);
				break;
			default:
				// ignore junk chars
				break;
			}
			// end switch
		}
		// end for
		if (numStr.length() != b.length())
		{
			numStr = b.toString();
		}
		if (numStr.length() == 0)
		{
			return 0;
		}
		long num = Long.parseLong(numStr);
		if (decpl == -1 || decpl == 0)
		{
			num *= 100;
		}
		else if (decpl == 2)
		{
			/* it is fine as is */
		}
		else
		{
			throw new NumberFormatException("wrong number of decimal places.");
		}
		if (negative)
		{
			num = -num;
		}
		return num;
	}

	// end parseLongPennies

	/**
	 * convert pennies to a string with a decorative decimal point.
	 * 
	 * @param long amount in pennies.
	 * @return amount with decorative decimal point, but no lead $.
	 */
	public final static String penniesToString(long pennies)
	{
		boolean negative;
		if (pennies < 0)
		{
			pennies = -pennies;
			negative = true;
		}
		else
		{
			negative = false;
		}
		String s = Long.toString(pennies);
		int len = s.length();
		switch (len)
		{
		case 1:
			s = "0.0" + s;
			break;
		case 2:
			s = "0." + s;
			break;
		default:
			s = s.substring(0, len - 2) + "." + s.substring(len - 2, len);
			break;
		} // end switch
		if (negative)
		{
			s = "-" + s;
		}
		return s;
	} // end penniesToString

	public static class TransactionTableEntry
	{
		public String from, to, amount, dateCreated, dateFinished;

		public String getFrom()
		{
			return from;
		}

		public String getTo()
		{
			return to;
		}

		public String getAmount()
		{
			return amount;
		}

		public String getDateCreated()
		{
			return dateCreated;
		}

		public String getDateFinished()
		{
			return dateFinished;
		}
	}
}
