/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package efg.web.beans;

import efg.bank.AccountOffice;

/**
 *
 * @author mistermartin75
 */

// arno edit: hee faggot je moet het wel in efg.hoewehetooknoemen.iets stoppen. Dit breekt waarschijnlijk vanalles.
// Done
public class TransactieOverzicht
{
	AccountOffice ao;
	private String[][] transacties;
	String soort, bedrag;
	
	public TransactieOverzicht(AccountOffice ao)
	{
		this.ao = ao;
		transacties = ao.getPendingTransacties();
		for(int i = 0; i < transacties.length; i++)
		{
			String soort = transacties[i][1];
			String bedrag = transacties[i][2];
			/*
			 * Bovenstaande moet op een of andere manier naar de JSP gaan
			 */
		}
	}
}
