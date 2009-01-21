package abcbank.jaas;

import java.util.Map;
import java.util.Set;
import java.io.IOException;
import java.security.Principal;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class BankLoginModule
implements LoginModule
{
  // initial state
  private Subject subject = null;
  private Set principals = null;
  private CallbackHandler callbackHandler = null;
  private Map sharedState = null;
  private Map options = null;

  // the authentication status
  private boolean succeeded = false;
  private boolean commitSucceeded = false;

  // roles, callerPrincipal
  private MyGroup roles = null;
  private MyGroup callerPrincipal = null;

  /*************************************************************************\
   initialize
  \*************************************************************************/
  public void initialize( Subject newSubject
                        , CallbackHandler newCallbackHandler
                        , Map newSharedState
                        , Map newOptions
                        )
  {
    System.out.println("BankLoginModule.initialize()");
    subject = newSubject;
    principals = subject.getPrincipals();
    System.out.println("principals="+principals);
    callbackHandler = newCallbackHandler;
    sharedState = newSharedState;
    options = newOptions;
  }

  /*************************************************************************\
   login
  \*************************************************************************/
  public boolean login()
  throws LoginException
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
    catch(Exception e)
    {
      System.out.println(e);
      throw new LoginException(e.getMessage());
    }
    String username = nc.getName();
    String password = new String(pc.getPassword());
    pc.clearPassword();
    System.out.println("username="+username);
    System.out.println("password="+password);
    if (username.equals("eric") && password.equals("geheim"))
    {
      roles = new MyGroup("Roles");
      roles.addMember(new MyPrincipal("klant"));
      roles.addMember(new MyPrincipal("beheerder"));
      callerPrincipal = new MyGroup("CallerPrincipal");
      callerPrincipal.addMember(new MyPrincipal(username));
      ret = succeeded = true;
    }
    else if (username.equals("fred") && password.equals("geheim"))
    {
      roles = new MyGroup("Roles");
      roles.addMember(new MyPrincipal("klant"));
      callerPrincipal = new MyGroup("CallerPrincipal");
      callerPrincipal.addMember(new MyPrincipal(username));
      ret = succeeded = true;

    }
    System.out.println("BankLoginModule.login(): "+ret);
    return ret;
  }

  /*************************************************************************\
   commit
  \*************************************************************************/
  public boolean commit()
  throws LoginException
  {
    boolean ret = false;
    if (succeeded)
    {
      principals.add(roles);
      principals.add(callerPrincipal);
      ret = commitSucceeded = true;
    }
    System.out.println("BankLoginModule.commit(): "+ret);
    return ret;
  }

  /*************************************************************************\
   abort
  \*************************************************************************/
  public boolean abort()
  throws LoginException
  {
    boolean ret = true;
    succeeded = false;
    if (commitSucceeded)
    {
      ret = ret && logout();
      commitSucceeded = false;
    }
    System.out.println("BankLoginModule.abort(): "+ret);
    return ret;
  }

  /*************************************************************************\
   logout
  \*************************************************************************/
  public boolean logout()
  throws LoginException
  {
    boolean ret = principals.remove(roles);
    ret = ret && principals.remove(callerPrincipal);
    System.out.println("BankLoginModule.logout(): "+ret);
    return ret;
  }
}
