/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package abcbank.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


import efg.jpa.bank.AccountManager;


/**
 *
 * @author Bami
 */
public class NieuweAccountBoon extends CommonBean{
    // variable declaratie
    private AccountManager accMan;
    private String ErrorMessage = "";
    private FacesContext Context = FacesContext.getCurrentInstance();
    
    public NieuweAccountBoon(AccountManager am) {
	System.out.println("NieuweAccountBoon("+am+");");
	accMan = am;
	// temp hack, word afgevangen met exception als er ook daadwerkelijk iemand is ingelogd.
	if (accMan == null) {
	    System.out.println("AccountManager null: tijdelijke gebruiker instellen");
	    String username = ""+""+100000;
	    String password = "geheim";
	    String salt = "" + System.currentTimeMillis();
	    try {
		accMan = new AccountManager(username, efg.jpa.bank.util.MD5.encode(efg.jpa.bank.util.MD5.hash(password+salt)),salt);
	    }
	    catch(Exception e) {
		System.out.println(e);
	    }
	}
    }
    
    public void doeNieuweAccount(ActionEvent AE ) {
	System.out.println("doeNieuweAccount()"); 
	if(limiet.equals("")) {
	    
	}
    }
	
}
