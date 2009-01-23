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
    boolean[] displayPages = {
                false, // boolean voor newAccountPagina
                false, // boolean voor checkAccountPagina
                false // boolean voor setBankStatusPagina
    };


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
     /**
      * Setters voor true en false van de verschillende pagina's
      */

     public void setNewAccountPageTrue(){
         displayPages[1] = false;
         displayPages[2] = false;
         displayPages[0] = true;
     }

     public void setCheckAccountPageTrue(){
         displayPages[0] = false;
         displayPages[2] = false;
         displayPages[1] = true;
     }

     public void setBankStatusPageTrue(){
         displayPages[0] = false;
         displayPages[1] = false;
         displayPages[2] = true;
     }

     public void setNewAccountPageFalse(){
         displayPages[0] = false;
     }

     public void setCheckAccountPageFalse(){
         displayPages[1] = false;
     }

     public void setBankStatusPageFalse(){
         displayPages[2] = false;
     }

     /**
      * Getters voor de status van de boolean
      */

     public boolean getDisplayNewAccount() {
         return displayPages[0];
     }

     public boolean getDisplayCheckAccount() {
         return displayPages[1];
     }

     public boolean getDisplayBankStatus() {
         return displayPages[2];
     }

     }
