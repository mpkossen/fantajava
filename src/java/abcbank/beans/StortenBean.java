package abcbank.beans;

import javax.faces.event.ActionEvent;

import abc.bank.jaas.MyPrincipal;
import efg.jpa.bank.BankException;

public class StortenBean
		extends OfficeBean
{	
	public void doStorten(ActionEvent ae)
	{
		System.out.println("StortenBean - doStorten()");
		try
		{
			System.out.println("storten - " + number + ", " + amount);
			((MyPrincipal) ex.getUserPrincipal()).getAccountOffice().transfer(null, amount);
			number = "";
			amount = 0.0;
		}
		catch (BankException e)
		{
			System.err.println("OverboekenBean - doOverboeking() - BankException: "
					+ e.getMessage());
		}
	}
}
