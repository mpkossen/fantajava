/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

/**
 *
 * @author Bami
 */

import java.security.Principal;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
/* Deze bean regelt de login shit. De Bank.jsp vraagt aan deze bean welke subview hij moet presenten.
 * 
 * */
public class BankBean// extends CommonBean
{      
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
}


