package project7.abcbank.jaas;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import project7.abcbank.beans.BankAccessBean;


public class MyCallbackHandler implements CallbackHandler
{
	private BankAccessBean bankAccessBean = null;

	public MyCallbackHandler(BankAccessBean bankAccessBean)
	{
		super();
		System.out.println("MyCallbackHandler.MyCallbackHandler()");
		this.bankAccessBean = bankAccessBean;
	}

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException
	{
		for (Callback callback : callbacks)
		{
			if (callback instanceof BankAccessBeanCallback)
			{
				BankAccessBeanCallback babc = (BankAccessBeanCallback) callback;
				babc.setBankAccessBean(bankAccessBean);
			}
			else
			{
				System.out.println("Unsupported callback: " + callback.getClass().getName());
				throw new UnsupportedCallbackException(callback);
			}
		}
	}
}
