package team2.abcbank.jaas.captcha;

import java.util.Map;

import javax.security.auth.spi.LoginModule;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.jacc.PolicyContext;
import javax.servlet.http.HttpServletRequest;

import com.octo.captcha.service.image.ImageCaptchaService;

public class CaptchaLoginModule implements LoginModule
{
	private CallbackHandler callbackHandler = null;
	private boolean loginSucceeded = false;
	private boolean commitSucceeded = false;

	public void initialize(Subject newSubject, CallbackHandler newCallbackHandler, Map newSharedState, Map newOptions)
	{
		callbackHandler = newCallbackHandler;
	}

	/**
	 * Called on login
	 * 
	 * @return false if LoginModule failed, causing it to be IGNORED!!!
	 */
	public boolean login() throws LoginException
	{
		try
		{
			CaptchaCallback cc = new CaptchaCallback();
			Callback callbacks[] =
			{
				cc
			};
			callbackHandler.handle(callbacks);

			// this doesn't really make this PAM pluggable, but it's the only way
			// doc: http://blog.smart-java.nl/blog/index.php/2008/07/28/request-in-loginmodule/
			HttpServletRequest req = (HttpServletRequest) PolicyContext.getContext("javax.servlet.http.HttpServletRequest");
			if (req != null)
			{
				ImageCaptchaService ics = CaptchaServiceSingleton.getInstance();
				if (ics != null)
				{
					loginSucceeded = ics.validateResponseForID(req.getSession().getId(), cc.getCaptcha());
				}
			}
		}
		catch (Throwable t) // catch anything that might disrupt the authentication process
		{
			throw new LoginException(t.getMessage());
		}
		loginSucceeded = true; // DEBUG
		if (!loginSucceeded)
			throw new FailedLoginException("captcha incorrect");
		return true;
	}

	/**
	 * Called when ALL of the LoginModules succeeded. Really assign all principals here.
	 * 
	 * @return false when login or commit failed.
	 */
	public boolean commit() throws LoginException
	{
		commitSucceeded = loginSucceeded;
		return loginSucceeded && commitSucceeded;
	}

	/**
	 * Called when ONE of the LoginModules failed.
	 * 
	 * @return false if this LoginModule's own login and/or commit attempts failed, and true otherwise.
	 */
	public boolean abort() throws LoginException
	{
		boolean ret = loginSucceeded && commitSucceeded;
		logout();
		return ret;
	}

	/**
	 * Logging out, so remove assigned principals.
	 * 
	 * @return false causes the LoginModule to be ignored!!!
	 */
	public boolean logout() throws LoginException
	{
		System.out.println("CaptchaLoginModule.logout()");
		loginSucceeded = false;
		commitSucceeded = false;
		return true;
	}
}
