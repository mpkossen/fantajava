package abcbank.jaas;

import java.util.Map;
import java.util.Set;
import java.security.Principal;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import efg.jpa.bank.AccountManager;
import efg.jpa.bank.AccountOffice;
import efg.jpa.bank.util.MD5;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import abcbank.beans.CommonBean;

//import efg.library.LibraryAdmin;
//import abc.bank.BankAdmin -- moet het worden

public class BankLoginModule extends CommonBean implements LoginModule 
{
	// private static LibraryAdmin libraryAdmin = new LibraryAdmin();

	// initial state
	//private Subject subject = null;
	private Set<Principal> principals = null;
	private CallbackHandler callbackHandler = null;
	private FacesContext ctx = FacesContext.getCurrentInstance();
	private String view = "subview-access:panel-access:";
	private FacesMessage t = new FacesMessage("welkom, je bent ingelogd");
	//private Map sharedState = null;
	//private Map options = null;

	// the authentication status
	private boolean succeeded = false;
	private boolean commitSucceeded = false;

	// roles, callerPrincipal
	private MyPrincipal principal = null;
	private MyGroup roles = null;
	private MyGroup callerPrincipal = null;

	public void initialize(Subject newSubject, 
				CallbackHandler newCallbackHandler, 
				Map newSharedState, 
				Map newOptions)
	{
		System.out.println("BankLoginModule.initialize()");
		//subject = newSubject;
		principals = newSubject.getPrincipals();
		System.out.println("principals=" + principals);
		callbackHandler = newCallbackHandler;
		//sharedState = newSharedState;
		//options = newOptions;
	}

	public boolean login() throws LoginException
	{
		System.out.println("BankLoginModule.login()");

		boolean ret = false;
		MyNameCallback nc = new MyNameCallback("User name: ", "guest");
		MyPasswordCallback pc = new MyPasswordCallback("Password: ", false);
		Callback callbacks[] = { nc, pc };
		try
		{
			callbackHandler.handle(callbacks);
		}
		catch (Exception e)
		{
			System.out.println(e);
			throw new LoginException(e.getMessage());
		}
		String username = nc.getName();
		String password = new String(pc.getPassword());
		pc.clearPassword();
		System.out.println("username=" + username);
		System.out.println("password=" + password);

		String salt = ""+System.currentTimeMillis();
		password = MD5.encode(MD5.hash(password+salt));
		
		try
		{
			AccountManager am = new AccountManager(username, password,salt);
			roles = new MyGroup("Roles");
			roles.addMember(new MyPrincipal("AccountManager"));
			callerPrincipal = new MyGroup("CallerPrincipal");
			callerPrincipal.addMember(new MyPrincipal(username,am));
			ret = succeeded = true;
			System.out.println("AccountManager ingelogd");
//			ctx.addMessage(view, t);
		}
		catch(Exception e)
		{
			System.out.println("FalseAccountManager: "+e);
			try
			{
				if(getStatuss().equals(ob))
				{
					AccountOffice ao = new AccountOffice(username, password, salt);
					roles = new MyGroup("Roles");
					roles.addMember(new MyPrincipal("OfficeManager"));
					callerPrincipal = new MyGroup("CallerPrincipal");
					callerPrincipal.addMember(new MyPrincipal(username, ao));
					ret = succeeded = true;
					System.out.println("AccountOffice ingelogd");
				}
				else
				{
					setError("ABC- Bank is closed, so you can’t log in to the system, please come back later.");
				}
			}
			catch(Exception e2)
			{
				System.out.println("FalseAccountOffice: "+e2);
//				ctx.addMessage(view+"panel-access", t);
			}
		}
		
		System.out.println("BankLoginModule.login(): " + ret);
		return ret;
	}

	public boolean commit()
	{
		boolean ret = false;
		if (succeeded)
		{
			principals.add(roles);
			principals.add(callerPrincipal);
			ret = commitSucceeded = true;
		}
		System.out.println("BankLoginModule.commit(): " + ret);
		return ret;
	}

	public boolean abort()
	{
		boolean ret = true;
		succeeded = false;
		if (commitSucceeded)
		{
			ret = ret && logout();
			commitSucceeded = false;
		}
		System.out.println("BankLoginModule.abort(): " + ret);
		return ret;
	}

	public boolean logout()
	{
		boolean ret = principals.remove(roles);
		ret = ret && principals.remove(callerPrincipal);
		System.out.println("BankLoginModule.logout(): " + ret);
		return ret;
	}
}
