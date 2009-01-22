/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import efg.jpa.bank.AccountOffice;
import efg.jpa.bank.AccountManager;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kalizec
 */
public class AccountManagerBean extends CommonBean
{
	private static final long serialVersionUID = 1L;
	private static int ID = 0;

	private static int getId ()
	{
		return ID++;
	}
	private int id = getId();
	private AccountOffice accountOffice = null;
        private AccountManager accountManager = null;
	private HttpSession session = null;

// <editor-fold desc="Constructors">
	public AccountManagerBean ()
	{
		System.out.println("(" + id + ")LibraryManagerBean()");
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if ( session != null )
		{
			setException("Id=" + id + ", sessionId=" + session.getId());
			accountOffice = (AccountOffice) session.getAttribute("accountOffice");
			if ( accountOffice != null )
			{
				return;
			}
                        accountManager = (AccountManager) session.getAttribute("accountManager");
                        if ( accountManager != null )
			{
				return;
			}
		}
		try
		{
			FacesContext.getCurrentInstance().getExternalContext().redirect("/onzeurlnaam/AccountOffice.faces");
		}
		catch ( Exception e )
		{
			System.out.println(e.getMessage());
		}
	}
// </editor-fold>

// <editor-fold desc="Callbacks">
	public void beforePhase (PhaseEvent phaseEvent)
	{
		System.out.println("(" + id + ")AccountOfficeBean.beforePhase(" + phaseEvent.getPhaseId() + ")");
	}

	public void afterPhase (PhaseEvent phaseEvent)
	{
		System.out.println("AccountOfficeBean.afterPhase(" + phaseEvent.getPhaseId() + ")");
	}
// </editor-fold>

// <editor-fold desc="Getters">
/**
 * Geeft de status van de bank weer.
 * @return een status (string). De statusen:
 * a.	open and busy
 * b.	closed and busy
 * c.	open and idle
 * d.	closed and idle
 */
	public static String getStatus ()
	{
		return ""; // For compile reasons. To be removed later.
	}

/**
 * Geeft de gegevens van een rekening (account). Deze methode returnt eigenlijk
 * de waarden van account.getDetails().
 * @param number	het nummer van de account
 * @return	details van een rekening (naam, number, balance, limit, pincode en salt)
 */
	public String[] getAccount (String number)
	{
		String[] ret = new String[1];	// For compile reasons. To be removed later.
		return ret;
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
		String[][] ret = new String[1][1];	// For compile reasons. To be removed later.
		return ret;
	}
// </editor-fold>

// <editor-fold desc="Setters">
/**
 * De status wordt gezet met boolean b (true/false). Als true dan is de stat
 * &= ~Status.CLOSED, anders is stat |= Status.CLOSED. Status.CLOSED is een
 * static public final int met waarde 1.
 * @param b	of de bank open gezet moet worden of niet.
 * @return	een getStatus() = een string met 4 mogelijke statussen.
 */
	public synchronized String setOpen (boolean b)
	{
		return ""; // For compile reasons. To be removed later.
	}
// </editor-fold>

// <editor-fold desc="ActionListener Methods">
/**
 * Maakt een nieuwe rekening aan.
 * @param newLimit		limiet van de rekening.
 * @param newName		naam van de eigenaar.
 * @param newPincode	pincode van de rekening.
 * @return				het nummer van de nieuwe aangemaakte rekening.
 */
	public void newAccount (double newLimit, String newName, String newPincode)
	{
            try{
            accountManager.newAccount(newLimit, newName, newPincode);
            }
            catch(Exception e) {
                System.out.println("newAccount exception: " + e.getMessage());
            } // For compile reasons. To be removed later.
	}
// </editor-fold>

// <editor-fold desc="Validators">
// </editor-fold>

// <editor-fold desc="ValueChangeListeners">
// </editor-fold>

// <editor-fold desc="Finalize">
	@Override
	protected void finalize () throws Throwable
	{
		System.err.println("(" + id + ")AccountOfficeBean.finalize()");
		super.finalize();
	}// </editor-fold>

}
