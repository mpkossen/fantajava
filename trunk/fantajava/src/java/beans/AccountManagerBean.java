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
        private boolean bankStatus;
        private double newLimit = 0;
        private String newName = "";
        private String newPincode = "";
        private AccountManager accountManager = null;
	private HttpSession session = null;


	public AccountManagerBean ()
	{
            System.out.println("AccountManagerBean()");
		System.out.println("(" + id + ")AccountManagerBean()");
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
		/*
		 * waarom willen we dit?
		try
		{
			FacesContext.getCurrentInstance().getExternalContext().redirect("URL=AccountOffice.faces");
		}
		catch ( Exception e )
		{
			System.out.println(e.getMessage());
		}*/
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
            System.out.println("AccountManagerBean().getStatus()");
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
            System.out.println("AccountManagerBean.getAccount()");
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
            System.out.println("Accountmanagerbean().getTransactions");
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
	public synchronized String setOpen ()
	{
            System.out.println("Accountmanagerbean.setOpen()");
		return accountManager.setOpen(bankStatus);
	}

 /**
  * Set status open:
  */
        public void openAction(){
            System.out.println("Accountmanagerbean.openAction()");
            bankStatus = true;
        }

  /**
  * Set status closed:
  */
        public void sluitAction(){
            System.out.println("Accountmanagerbean.sluitAction()");
            bankStatus = false;
        }

 /**
  * Setters voor de nieuwe account
  * @param limit    de nieuwe limitatie
  * @param name     de nieuwe naam
  * @param pincode  de nieuwe pincode
  */

        public void setNewLimit(double limit){
            System.out.println("Accountmanagerbean.setNewLimit()");
            newLimit = limit;
        }

        public void setNewName(String name){
            System.out.println("Accountmanagerbean.setNewName()");
            newName = name;
        }

        public void setNewPincode(String pincode){
            System.out.println("Accountmanagerbean.setNewPincode()");
            newPincode = pincode;
        }

 /**
  * Getters voor de nieuwe account
  * @param newLimit    de nieuwe limitatie
  * @param newName     de nieuwe naam
  * @param newPincode  de nieuwe pincode
  */

        public double getLimit(){
            System.out.println("Accountmanagerbean.getLimit()");
            return newLimit;
        }

        public String getName(){
            System.out.println("Accountmanagerbean.getName()");
            return newName;
        }

        public String getPincode() {
            System.out.println("Accountmanagerbean.getPincode()");
            return newPincode;
        }

/**
 * Maakt een nieuwe rekening aan.
 * @param newLimit		limiet van de rekening.
 * @param newName		naam van de eigenaar.
 * @param newPincode	pincode van de rekening.
 * @return				het nummer van de nieuwe aangemaakte rekening.
 */


        public String newAccount ()
	{
            System.out.println("AccountManagerBean.newAccount()");
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
		System.err.println("(" + id + ")AccountManagerBean.finalize()");
		super.finalize();
	}

}
