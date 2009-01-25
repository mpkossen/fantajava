/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

/**
 *
 * @author Bami
 */


import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import efg.jpa.bank.AccountManager;
/* Deze bean regelt de login shit. De Bank.jsp vraagt aan deze bean welke subview hij moet presenten.
 * 
 * */
public class BankBean {      
        private ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	
	public BankBean() {
	    System.out.println("BankBean();");
	}
	
        public boolean getDisplayManager()
        {
		System.out.println("BankBean.getDisplayManager()");
                return ec.isUserInRole("beheerders");
        }
       
        public boolean getDisplayOffice()
        {
               System.out.println("BankBean.getDisplayOffice()");
                return ec.isUserInRole("klanten");
        }
	
	public boolean getClosedBankWarning() {
	    System.out.println("BankBean.getClosedBankWarning()");
	    return (AccountManager.getStatus().equals(AccountManager.cb) || AccountManager.getStatus().equals(AccountManager.ci));
	}
	
	public boolean getBusyBankWarning() {
	    System.out.println("BankBean.getBusyBankWarning()");
	    return (AccountManager.getStatus().equals(AccountManager.ob) || AccountManager.getStatus().equals(AccountManager.cb));
	}
	
}


