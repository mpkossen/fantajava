/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package abcbank.beans;

import efg.jpa.bank.AccountOffice;
import abcbank.jaas.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author mistermartin75
 */

public class TransactieOverzicht
{
	private ExternalContext ex;
	private MyPrincipal mp;
	private AccountOffice ao;
	
	
	private String[][] transacties;
	public String soort = "foo";
	public String bedrag;
	
	public TransactieOverzicht(AccountOffice ao)
	{
		
		ex = FacesContext.getCurrentInstance().getExternalContext();
		mp = (MyPrincipal) ex.getUserPrincipal();
		ao = mp.getAccountOffice();
		
		soort = "foo";

		this.ao = ao;
		transacties = ao.getPendingTransacties();
		//transacties =  new String[1][2];
		//transacties[0][0] = "foo";
		//transacties[0][1] = "bar";
	}
}
