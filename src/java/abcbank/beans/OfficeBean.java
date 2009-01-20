package abcbank.beans;

import java.util.HashSet;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import abc.bank.jaas.MyPrincipal;

import efg.jpa.bank.AccountOffice;
import efg.jpa.bank.BankException;

public class OfficeBean
		extends CommonBean
{
	protected ExternalContext	ex		= FacesContext.getCurrentInstance().getExternalContext();
	protected double			amount;
	protected String			number	= null;
	
	public double getAmount()
	{
		return amount;
	}
	
	public String getNumber()
	{
		return number;
	}
	
	public void setAmount(double newAmount)
	{
		amount = newAmount;
	}
	
	public void setNumber(String newNumber)
	{
		number = newNumber;
	}
	
	private static boolean[]	render	= {false, false, false };
	
	public boolean getRenderStorten()
	{
		return render[0];
	}
	
	public boolean getRenderOpnemen()
	{
		return render[1];
	}
	
	public boolean getRenderOverboeken()
	{
		return render[2];
	}
	
	public void storten(ActionEvent ae)
	{
		reset(0);
	}
	
	public void opnemen(ActionEvent ae)
	{
		reset(1);
	}
	
	public void overboeken(ActionEvent ae)
	{
		reset(2);
	}
	
	private void reset(int i)
	{
		render = new boolean[] {false, false, false };
		if (i < 0) { return; }
		render[i] = true;
	}
	
	public boolean getRenderTransactions()
	{
		MyPrincipal mp = (MyPrincipal) ex.getUserPrincipal();
		return mp.getAccountOffice().getPendingTransacties().length > 0;
	}
	
	public void doOpslaan(ActionEvent ae)
	{
		MyPrincipal mp = (MyPrincipal) ex.getUserPrincipal();
		mp.getAccountOffice().sync();
	}
	
	public void changeAmount(ValueChangeEvent vce)
	{
		System.out.println("LibraryManagerBean.changeNewBook("
				+ vce.getOldValue() + ", " + vce.getNewValue() + ")");
	}
	
	public String getName()
	{
		return ((MyPrincipal)ex.getUserPrincipal()).getAccountOffice().getDetails()[0];
	}
	
	public String getRekeningNummer()
	{
		return ex.getRemoteUser();
	}
	
	public Transactie[] getTransacties()
	{
		Transactie[] a = new Transactie[((MyPrincipal)ex.getUserPrincipal()).getAccountOffice().getPendingTransacties().length];
		int i = 0;
		
		for(String[] b: ((MyPrincipal)ex.getUserPrincipal()).getAccountOffice().getPendingTransacties())
		{
			a[i] = new Transactie(b[0],b[1],b[2],b[3],b[4],b[5]);
			i++;
		}
		
		return a;
	}
	
	public class Transactie
	{
		private String id;
		private String from;
		private String to;
		private String amount;
		private String transactionTime;
		private String transferTime;
		
		public Transactie(String id,String from,String to, String amount, String transactionTime,String transferTime)
		{
			this.id = id;
			this.from = from;
			this.to = to;
			this.amount = amount;
			this.transactionTime = transactionTime;
			this.transferTime = transferTime;
		}
		
		public String getId(){return id;}
		public String getFrom(){return from;}
		public String getTo(){return to;}
		public String getAmount(){return amount;}
		public String getTransactionTime(){return transactionTime;}
		public String getTransferTime(){return transferTime;}
		
	}
	
	public String getBalans()
	{
		return ((MyPrincipal)ex.getUserPrincipal()).getAccountOffice().getDetails()[2];
	}
}
