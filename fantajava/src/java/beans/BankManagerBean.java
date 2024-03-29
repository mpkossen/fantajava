/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Bami
 */
public class BankManagerBean {
// placeholder variablen, na inloggen worden die ingevuld
    protected String usrname;
    protected String password;
    private AccountManagerBean amb;
    int paginatype; // zet op welke pagina hij moet komen na een error
    boolean[] displayPages = {
	false, // boolean voor newAccountPagina
	false, // boolean voor checkAccountPagina
	false // boolean voor setBankStatusPagina
    };

    public BankManagerBean() {
	System.out.println("BankManagerBean()");
	HttpSession hs = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	if (hs == null) {
	    // als er geen sessie is, fuck dit, log uit    
	    logUit(-1);
	}
	
    }

    private void logUit(int i) {
    }
    
    /**
     * Setters voor true en false van de verschillende pagina's
     */
    public void newAccountPage(ActionEvent ae) {
        System.out.println("BankManagerBean.newAccountPage()");
	naarPagina(0);
    }

    public void checkAccountPage(ActionEvent ae) {
        System.out.println("BankManagerBean.checkAccountPage()");
	naarPagina(1);
    }

    public void bankStatusPage(ActionEvent ae) {
        System.out.println("BankManagerBean.bankStatusPage()");
	naarPagina(2);
    }

    private void naarPagina(int i) {
        System.out.println("BankManagerBean.naarPagina("+i+")");
	displayPages = new boolean[]{false, false, false};
	if (i >= 0 && i <= displayPages.length) {
	    displayPages[i] = true;
	}
    }

    /**
     * Getters voor de status van de boolean
     */
    public boolean getDisplayNewAccount() {
        System.out.println("BankManagerBean.getDisplayNewAccount()");
	return displayPages[0];
    }

    public boolean getDisplayCheckAccount() {
        System.out.println("BankManagerBean.getDisplayCheckAccount()");
	return displayPages[1];
    }

    public boolean getDisplayBankStatus() {
        System.out.println("BankManagerBean.getDisplayBankStatus()");
	return displayPages[2];
    }

    public String getBeheerderNaam() {
        System.out.println("BankManagerBean.getBeheerderNaam()");
	// lololol grote subobject boom
	return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName().toString();
    }
    
    	@Override
	protected void finalize () throws Throwable
	{
		System.err.println("BankManagerBean.finalize()");
		super.finalize();
	}
}

