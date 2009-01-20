package abc.bank.beans;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import efg.jpa.bank.BankException;

import abc.bank.jaas.MyPrincipal;

public class OverboekenBean
		extends OfficeBean
{
	public void doOverboeking(ActionEvent ae)
	{
		System.out.println("OverboekenBean - doOverboeking()");
		try
		{
			System.out.println("overboeking - " + number + ", " + amount);
			((MyPrincipal) ex.getUserPrincipal()).getAccountOffice().transfer(number, amount);
			number = "";
			amount = 0.0;
		}
		catch (BankException e)
		{
			System.err.println("OverboekenBean - doOverboeking() - BankException: "
					+ e.getMessage());
		}
	}
	
	public void changeNumber(ValueChangeEvent vce)
	{
		System.out.println("LibraryManagerBean.changeNewBook("
				+ vce.getOldValue() + ", " + vce.getNewValue() + ")");
	}
}
