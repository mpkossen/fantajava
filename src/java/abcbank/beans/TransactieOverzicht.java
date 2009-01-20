/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package abcbank.beans;

import efg.jpa.bank.AccountOffice;

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
	public String soort = "foo";
	public String bedrag;
	
	public TransactieOverzicht(AccountOffice ao)
	{
		soort = "foo";

		this.ao = ao;
		//transacties = ao.getPendingTransacties();
		transacties =  new String[1][2];
		transacties[0][0] = "foo";
		transacties[0][1] = "bar";
		/*for(int i = 0; i < transacties.length; i++)
		{
			String soort = transacties[i][1];
			String bedrag = transacties[i][2];
		}*/
	}
}
