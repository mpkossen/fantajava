package project7.abcbank.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;

import org.jboss.security.SecurityAssociation;
import org.jboss.security.auth.callback.CallbackHandlerPolicyContextHandler;
import org.jboss.web.tomcat.security.login.WebAuthentication;

import project7.abcbank.ejb.shared.AccountManagerIF;
import project7.abcbank.ejb.shared.AccountOfficeIF;
import project7.abcbank.ejb.shared.BankException;
import project7.abcbank.ejb.shared.LoginBeanIF;
import project7.abcbank.jaas.MyCallbackHandler;


public class BankAccessBean extends EvenMoreCommonBean
{
	private String username = null;
	private String password = null;

	protected LoginBeanIF loginBean = null;
	protected AccountManagerIF accountManager = null;
	protected AccountOfficeIF accountOffice = null;
	protected long accountNumber = -1;

	public BankAccessBean()
	{
		try
		{
			Context ctx = new InitialContext();
			loginBean = (LoginBeanIF) ctx.lookup("java:comp/env/LoginBeanRef");
			accountManager = (AccountManagerIF) ctx.lookup("java:comp/env/AccountManagerRef");
			accountOffice = (AccountOfficeIF) ctx.lookup("java:comp/env/AccountOfficeRef");
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
	}

	public String getBankStatus()
	{
		System.out.println("BankAccessBean.getBankStatus()");
		String status = "database onbereikbaar";
		try
		{
			status = loginBean.getBankIsOpen() ? "bank is open" : "bank is closed";
			status += loginBean.getTransactionManagerIsIdle() ? " and idle" : " and busy";
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		return status;
	}

	public String logout()
	{
		System.out.println("BankAccessBean.logout()");
		
		HttpSession session = (HttpSession) getExternalContext().getSession(false);
		if (session != null)
		{
			session.invalidate();
		}
		return "logoutOutcome";
	}

	public String login()
	{
		System.out.println("BankAccessBean.login()");

		ExternalContext ec = getExternalContext();
		boolean error = false;
		// alleen maar inloggen als de user nog niet ingelogd is
		if (ec.getRemoteUser() == null)
		{
			try
			{
				// de callbackHandler die WebAuthentication opbouwt voor zijn username + credentials parameters
				// delegeert onbekende callbacks naar deze callbackHandler
				// zo kunnen we dus communiceren via de callbacks
				// doc: http://docs.jboss.org/jbossas/javadoc/4.0.5/security/org/jboss/security/auth/callback/CallbackHandlerPolicyContextHandler.html
				// doc: https://jira.jboss.org/jira/browse/JBAS-2345
				CallbackHandlerPolicyContextHandler.setCallbackHandler(new MyCallbackHandler(this));

				// doe de programmatic web logon:
				// doc: http://jboss.org/community/docs/DOC-12656
				// dit object doet zowel authentication als authorization
				WebAuthentication wa = new WebAuthentication();
				boolean success = wa.login(username, password);
				if (!success)
				{
					error = true;
					// gespecificeerd in:
					// https://jira.jboss.org/jira/browse/SECURITY-278?focusedCommentId=12433231#action_12433231
					Object o = SecurityAssociation.getContextInfo("org.jboss.security.exception");
					if (o instanceof LoginException)
					{
						LoginException le = (LoginException) o;
						addMessage(FacesMessage.SEVERITY_ERROR, "Login fout: " + le.getMessage());
					}
					else
					{
						addMessage(FacesMessage.SEVERITY_ERROR, "Systeem fout");
					}
				}
			}
			catch (Throwable t)
			{
				error = true;
				addMessage(FacesMessage.SEVERITY_ERROR, "Systeem fout");
				t.printStackTrace();
			}
		}

		// mogelijke outcomes voor redirection
		// zie JSF navigation rules in faces-config.xml
		if (error)
			return "loginError";
		if (ec.isUserInRole("AccountManager"))
			return "loggedInAsAccountManager";
		if (ec.isUserInRole("AccountOffice"))
			return "loggedInAsAccountOffice";

		// kom hier uit als JBoss iets geks doet:
		// de authenticatie is gelukt, maar de authorisatie is mislukt
		addMessage(FacesMessage.SEVERITY_ERROR, "Systeem fout");
		return "loginError";
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	
	/**
	 * for jaas mycallbackhandler
	 */
	public LoginBeanIF getLoginBean()
	{
		return loginBean;
	}

	public AccountManagerIF getAccountManager()
	{
		return accountManager;
	}

	public AccountOfficeIF getAccountOffice()
	{
		return accountOffice;
	}

	public long getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber)
	{
		this.accountNumber = accountNumber;
	}
}