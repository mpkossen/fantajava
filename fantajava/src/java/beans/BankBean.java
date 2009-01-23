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
public class BankBean
{      
        private ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
       
        public boolean getDisplayManager()
        {
		System.out.println("BankBean.getDisplayManager()");
                System.out.println(ec.isUserInRole("Manager"));
                return ec.isUserInRole("Manager");
        }
       
        public boolean getDisplayOffice()
        {
               System.out.println("BankBean.getDisplayOffice()");
                return ec.isUserInRole("Klant");
        }
}


