/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package maarten;

import efg.bank.AccountOffice;

/**
 *
 * @author mistermartin75
 */
public class TransactieOverzicht
{
	AccountOffice ao;
	private String[][] transacties;
	
	public TransactieOverzicht(AccountOffice ao)
	{
		this.ao = ao;
		transacties = ao.getPendingTransacties();
		for(int i = 0; i < transacties.length; i++)
		{
			// Display transacties[i][i]
			// Dit moet op een of andere manier naar een jsp gedumpt worden

			// Dit is denk ik niet nodig, maar ik weet niet hoe die array er uit gaat zien
			// for(int j = 0; j < transacties.length; j++)
		}
	}
}
