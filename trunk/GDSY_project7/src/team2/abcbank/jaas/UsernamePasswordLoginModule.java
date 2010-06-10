package team2.abcbank.jaas;

import java.util.Map;
import java.util.Set;
import java.security.Principal;

import javax.security.auth.spi.LoginModule;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import team2.abcbank.ejb.shared.AccountData;
import team2.abcbank.ejb.shared.MD5;

public class UsernamePasswordLoginModule implements LoginModule
{
	private Set<Principal> subjectPrincipals = null;
	private CallbackHandler callbackHandler = null;

	// the authentication status
	private boolean loginSucceeded = false;
	private boolean commitSucceeded = false;

	// assigned roles in login phase
	private MyGroup roles = null;
	//private MyGroup callerPrincipal = null;

	public void initialize(Subject newSubject, CallbackHandler newCallbackHandler, Map newSharedState, Map newOptions)
	{
		// subject = newSubject; // the actual user
		subjectPrincipals = newSubject.getPrincipals();
		callbackHandler = newCallbackHandler;
		// sharedState = newSharedState; // shared info between JAAS modules
		// options = newOptions; // parameters passed via login-config.xml
	}

	/**
	 * Called on login
	 * 
	 * @return false if LoginModule failed, causing it to be IGNORED!!!
	 */
	public boolean login() throws LoginException
	{
		System.out.println("UsernamePasswordLoginModule.login()");
		try
		{
			NameCallback nc = new NameCallback("Username: ", "guest");
			PasswordCallback pc = new PasswordCallback("Password: ", false);
			BankAccessBeanCallback babc = new BankAccessBeanCallback();
			Callback callbacks[] =
			{
					nc, pc, babc
			};
			callbackHandler.handle(callbacks);
			String username = nc.getName();
			String password = new String(pc.getPassword());
			pc.clearPassword();
			long salt = System.currentTimeMillis();
			byte[] hash = MD5.hash(password + salt);
			String encoded = MD5.encode(hash);

			// Principal name "Roles" is hardcoded in JSP / JAAS / JSF
			roles = new MyGroup("Roles");
			//callerPrincipal = new MyGroup("CallerPrincipal");

			AccountData ad = babc.getBankAccessBean().getLoginBean().login(username, encoded, salt);
			roles.addMember(new MyPrincipal(ad.getIsManager() ? "AccountManager" : "AccountOffice"));
			babc.getBankAccessBean().setAccountNumber(ad.getAccountNumber());
			//callerPrincipal.addMember(new MyPrincipal("bork"));
			loginSucceeded = true;
		}
		catch (Throwable t) // catch anything that might disrupt the authentication process
		{
			t.printStackTrace();
			throw new LoginException(t.getMessage());
		}
		if (!loginSucceeded)
			throw new FailedLoginException("login failed");
		return true;
	}

	/**
	 * Called when ALL of the LoginModules succeeded. Really assign all principals here.
	 * 
	 * @return false when login or commit failed.
	 */
	public boolean commit() throws LoginException
	{
		try
		{
			if (loginSucceeded)
			{
				subjectPrincipals.add(roles);
				//subjectPrincipals.add(callerPrincipal);
				commitSucceeded = true;
			}
			return loginSucceeded && commitSucceeded;
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			throw new LoginException();
		}
	}

	/**
	 * Called when ONE of the LoginModules failed.
	 * 
	 * @return false if this LoginModule's own login and/or commit attempts failed, and true otherwise.
	 */
	public boolean abort() throws LoginException
	{
		try
		{
			boolean ret = loginSucceeded && commitSucceeded;
			logout();
			return ret;
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			throw new LoginException();
		}
	}

	/**
	 * Logging out, so remove assigned principals.
	 * 
	 * @return false causes the LoginModule to be ignored!!!
	 */
	public boolean logout() throws LoginException
	{
		System.out.println("UsernamePasswordLoginModule.logout()");
		try
		{
			commitSucceeded = false;
			loginSucceeded = false;
			//subjectPrincipals.remove(callerPrincipal);
			subjectPrincipals.remove(roles);
			return true;
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			throw new LoginException();
		}
	}
}
