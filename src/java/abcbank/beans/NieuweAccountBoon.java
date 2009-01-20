/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package abcbank.beans;

import efg.jpa.bank.AccountManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bami
 */
public class NieuweAccountBoon extends CommonBean{
    // variable declaratie
    AccountManager accMan;
    
    public NieuweAccountBoon(AccountManager am) {
	accMan = am;
	if (accMan == null) {
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
    
    public boolean voegNieuweAccountToe(Double limit, String name, String pincode ) {
	try {
	    accMan.newAccount(limit, name, pincode);
	} catch (Exception ex) {
	    Logger.getLogger(NieuweAccountBoon.class.getName()).log(Level.SEVERE, null, ex);
	} 
	return false;
    }
	
}
