package beans;

import efg.jpa.bank.AccountManager;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


public class AccountManagerBean extends CommonBean
{
	private static final long serialVersionUID = 1L;
	private static int ID = 0;

	private static int getId ()
	{
		return ID++;
	}
	private int id = getId();
        private AccountManager accountManager = null;
	private HttpSession session = null;


	public AccountManagerBean ()
	{
		System.out.println("(" + id + ")LibraryManagerBean()");
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if ( session != null )
		{
			setException("Id=" + id + ", sessionId=" + session.getId());
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
		return efg.jpa.bank.AccountManager.getStatus(); 
	}

/**
 * Geeft de gegevens van een rekening (account). Deze methode returnt eigenlijk
 * de waarden van account.getDetails().
 * @param number	het nummer van de account
 * @return	details van een rekening (naam, number, balance, limit, pincode en salt)
 */
	public String[] getAccount (String number)
	{
		String[] ret = accountManager.getAccount(number);
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
		String[][] ret = accountManager.getTransactions(number);	// For compile reasons. To be removed later.
		return ret;
	}

/**
 * De status wordt gezet met boolean b (true/false). Als true dan is de stat
 * &= ~Status.CLOSED, anders is stat |= Status.CLOSED. Status.CLOSED is een
 * static public final int met waarde 1.
 * @param b	of de bank open gezet moet worden of niet.
 * @return	een getStatus() = een string met 4 mogelijke statussen.
 */
	public synchronized String setOpen (boolean b)
	{
		return accountManager.setOpen(b);
	}

/**
 * Maakt een nieuwe rekening aan.
 * @param newLimit		limiet van de rekening.
 * @param newName		naam van de eigenaar.
 * @param newPincode	pincode van de rekening.
 * @return				het nummer van de nieuwe aangemaakte rekening.
 */
	public String newAccount (double newLimit, String newName, String newPincode)
	{
           if(newLimit != 0 && !newName.equals(null) && !newPincode.equals(null)){
                try{
                accountManager.newAccount(newLimit, newName, newPincode);
                }
                catch(Exception e) {
                    System.out.println("newAccount exception: " + e.getMessage());
                }
           }
           else{
            return "Mislukt";
           }
           return "";
	}

	@Override
	protected void finalize () throws Throwable
	{
		System.err.println("(" + id + ")AccountOfficeBean.finalize()");
		super.finalize();
	}

}
