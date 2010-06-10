package project7.abcbank.jaas;

import javax.security.auth.callback.Callback;

import project7.abcbank.beans.BankAccessBean;


public class BankAccessBeanCallback implements Callback
{
	private BankAccessBean bab = null;

	public BankAccessBean getBankAccessBean()
	{
		return bab;
	}

	public void setBankAccessBean(BankAccessBean bab)
	{
		this.bab = bab;
	}
}
