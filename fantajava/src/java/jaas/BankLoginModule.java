package jaas;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import efg.jpa.bank.AccountManager;
import efg.jpa.bank.AccountOffice;

import efg.jpa.bank.util.MD5;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class BankLoginModule extends beans.CommonBean implements LoginModule {
    // initial state
    private Subject subject = null;
    private Set<Principal> principals = null;
    private CallbackHandler callbackHandler = null;
    private Map sharedState = null;
    private Map options = null;    // the authentication status
    private boolean succeeded = false;
    private boolean commitSucceeded = false;    // principal, roles, callerPrincipal
    private final MyPrincipal principal = new MyPrincipal("BankLoginModule");
    private MyGroup roles = null;
    private MyGroup callerPrincipal = null;
    /*************************************************************************\
    initialize
    \*************************************************************************/
    public void initialize(Subject newSubject, CallbackHandler newCallbackHandler, Map newSharedState, Map newOptions) {
	System.out.println("BankLoginModule.initialize(" + newSubject + ", " + newCallbackHandler + ", " + newSharedState + ", " + newOptions + ")");
	subject = newSubject;
	principals = subject.getPrincipals();
	callbackHandler = newCallbackHandler;
	sharedState = newSharedState;
	options = newOptions;
    }

    /*************************************************************************\
    login
    \*************************************************************************/
    public boolean login()
	    throws LoginException {
	System.out.println("BankLoginModule.login()");
	boolean ret = false;
	// als de bank busy is, laat maar, vergeet dit helemaal
	if (!AccountManager.getStatus().equals(AccountManager.cb) || !AccountManager.getStatus().equals(AccountManager.ob)) {

	    MyNameCallback nc = new MyNameCallback("User name: ", "guest");
	    MyPasswordCallback pc = new MyPasswordCallback("Password: ", false);
	    Callback callbacks[] = {nc, pc};
	    try {
		callbackHandler.handle(callbacks);
	    } catch (IOException e) {
		System.out.println(e);
		LoginException le = new LoginException("Failed to get username/password");
		le.initCause(e);
		throw le;
	    } catch (UnsupportedCallbackException e) {
		System.out.println(e);
		LoginException le = new LoginException("CallbackHandler does not support: " + e.getCallback());
		le.initCause(e);
		throw le;
	    }
	    String username = nc.getName();
	    char[] pwd = pc.getPassword();
	    String password = new String(pwd.clone());
	    pc.clearPassword();
	    System.out.println("username=" + username);
	    System.out.println("password=" + password);

	    String salt = "" + System.currentTimeMillis();
	    password = MD5.encode(MD5.hash(password + salt));
	    try {
		InitialContext ctx = new InitialContext();

		System.out.println("InitialContext=" + ctx);
		AccountManager office = new AccountManager(username, password, salt);
		System.out.println(office.toString());
		roles = new MyGroup("Roles");
		roles.addMember(new MyPrincipal("beheerders", office));
		callerPrincipal = new MyGroup("CallerPrincipal");
		callerPrincipal.addMember(new MyPrincipal(username, office));
		ret = succeeded = true;
	    } catch (Exception e) {
		System.out.println("LoginError: " + e);
		// als de bank gesloten is, sla dit dan maar over
		System.err.println("Bank gesloten, login automatisch gefaald");
		if (!AccountManager.getStatus().equals(AccountManager.ci) || !AccountManager.getStatus().equals(AccountManager.cb)) {
		    try {
			System.out.println("Try as office");
			InitialContext ctx = new InitialContext();
			AccountOffice office = new AccountOffice(username, password, salt);
			roles = new MyGroup("Roles");
			roles.addMember(new MyPrincipal("klanten", office));
			callerPrincipal = new MyGroup("CallerPrincipal");
			callerPrincipal.addMember(new MyPrincipal(username, office));
			ret = succeeded = true;
		    } catch (Exception e1) {
			System.out.println("LoginError: " + e1);
		    }
		}
	    }

	} else {
	    System.err.println("Bank is busy! Geen login mogelijk.");
	}
	System.out.println("BankLoginModule.login(): " + ret);
	return ret;
    }

    /*************************************************************************\
    commit
    \*************************************************************************/
    public boolean commit()
	    throws LoginException {
	boolean ret = false;
	if (succeeded) {
	    principals.add(principal);
	    principals.add(roles);
	    principals.add(callerPrincipal);
	    ret = commitSucceeded = true;
	}
	System.out.println("BankLoginModule.commit(): " + ret);
	return ret;
    }

    /*************************************************************************\
    abort
    \*************************************************************************/
    public boolean abort()
	    throws LoginException {
	boolean ret = true;
	succeeded = false;
	if (commitSucceeded) {
	    ret = ret && logout();
	    commitSucceeded = false;
	}
	System.out.println("BankLoginModule.abort(): " + ret + " "+succeeded);
	return ret;
    }

    /*************************************************************************\
    logout
    \*************************************************************************/
    public boolean logout()
	    throws LoginException {
	boolean ret = principals.remove(principal);
	ret = ret && principals.remove(roles);
	ret = ret && principals.remove(callerPrincipal);
	System.out.println("BankLoginModule.logout(): " + ret);
	return ret;
    }

    public static Context getInitialContext() throws NamingException {
	Properties p = new Properties();
	p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
	p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
	p.put(Context.PROVIDER_URL, "jnp://localhost:1099");
	return new InitialContext(p);
    }
}