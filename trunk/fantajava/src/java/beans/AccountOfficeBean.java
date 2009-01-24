/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import efg.jpa.bank.AccountOffice;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mistermartin75
 */
public class AccountOfficeBean extends CommonBean
{
	private String id;
	private AccountOffice accountOffice;
	private static int ID = 0;
	private HttpSession session = null;

	private static int getId ()
	{
		return ID++;
	}

	public AccountOfficeBean()
	{
            System.out.println("AccountOfficebean()");
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if ( session != null )
		{
			setException("Id=" + id + ", sessionId=" + session.getId());
			accountOffice = (AccountOffice) session.getAttribute("accountOffice");
			if ( accountOffice != null )
			{
				return;
			}
		}
		try
		{
			FacesContext.getCurrentInstance().getExternalContext().redirect("AccountOffice.faces");
		}
		catch ( Exception e )
		{
			System.out.println(e.getMessage());
		}
	}

/**
 * Geeft alle transacties van de rekening number.
 * @param number	het nummer van de account
 * @return	een String[][]. De eerste array bevat nummers vanaf 0 tot en met het
 * aantal van transacties. De tweede array bevat de informatie van een
 * transactie (id, from, to, amount, transactiontime, transfertime)
 */
	public String[][] getTransactions (String number)
	{
            System.out.println("AccountOfficebean.getTransactions()");
		String[][] ret = accountOffice.getPendingTransacties();
		return ret;
	}
}
