package abcbank.jaas;

import javax.security.auth.callback.PasswordCallback;

public class MyPasswordCallBack extends PasswordCallback
{
	private static final long serialVersionUID = 1L;

	public MyPasswordCallBack(String prompt, boolean echoOn)
	{
		super(prompt, echoOn);
		System.out.println("MyPasswordCallback(" + prompt + ", " + echoOn + ")");
	}

	@Override
	public void clearPassword()
	{
		System.out.println("MyPasswordCallback.clearPassword()");
		super.clearPassword();
	}

	@Override
	public char[] getPassword()
	{
		System.out.println("MyPasswordCallback.getPassword()");
		return super.getPassword();
	}

	@Override
	public String getPrompt()
	{
		System.out.println("MyPasswordCallback.getPrompt()");
		return super.getPrompt();
	}

	@Override
	public boolean isEchoOn()
	{
		System.out.println("MyPasswordCallback.isEchoOn()");
		return super.isEchoOn();
	}

	@Override
	public void setPassword(char[] password)
	{
		System.out.print("MyPasswordCallback.setPassword(" + new String(password) + ")");
		super.setPassword(password);
	}
}
