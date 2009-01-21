/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Bami
 */
public class BankManagerBean {
// placeholder variablen, na inloggen worden die ingevuld
    protected String usrname;
    protected String password;
    int paginatype; // zet op welke pagina hij moet komen na een error
    
     public BankManagerBean()
        {
                System.out.println("BankManagerBean()");
                HttpSession hs = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                if(hs == null) {
                    // als er geen sessie is, fuck dit, log uit    
		    logUit(-1);
		}
        }
     
     private void logUit(int i) {
	 
	}
     }
