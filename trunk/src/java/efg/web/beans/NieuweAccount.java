/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package efg.web.beans;

import efg.bank.AccountManager;

/**
 *
 * @author Bami
 */
public class NieuweAccount {
    // variable declaratie
    AccountManager accMan;
    
    public NieuweAccount(AccountManager am) {
	accMan = am;
    }
    
    public boolean voegNieuweAccountToe() {
	return false;
    }
	
}
