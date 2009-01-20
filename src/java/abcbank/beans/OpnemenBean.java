package abcbank.beans;

import javax.faces.event.ActionEvent;

import abc.bank.jaas.MyPrincipal;
import efg.jpa.bank.BankException;

public class OpnemenBean
		extends OfficeBean
{	
	public void doOpnemen(ActionEvent ae)
	{
		System.out.println("OverboekenBean - doOverboeking()");
		try
		{
			System.out.println("Opnemen - " + number + ", " + amount);
			((MyPrincipal) ex.getUserPrincipal()).getAccountOffice().transfer(null, -amount);
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
