package abcbank.jaas;

import javax.security.auth.callback.NameCallback;

public class MyNameCallBack extends NameCallback
{
	private static final long serialVersionUID = 1L;

	public MyNameCallBack(String prompt)
	{
		super(prompt);
		System.out.println("MyNameCallback(" + prompt + ")");
	}

	public MyNameCallBack(String prompt, String defaultName)
	{
		super(prompt, defaultName);
		System.out.println("MyNameCallback(" + prompt + ", " + defaultName + ")");
	}

	@Override
	public String getDefaultName()
	{
		System.out.println("MyNameCallback.getDefaultName()");
		return super.getDefaultName();
	}

	@Override
	public String getName()
	{
		System.out.println("MyNameCallback.getName()");
		return super.getName();
	}

	@Override
	public String getPrompt()
	{
		System.out.println("MyNameCallback.getPrompt()");
		return super.getPrompt();
	}

	@Override
	public void setName(String name)
	{
		System.out.println("MyNameCallback.setName(" + name + ")");
		super.setName(name);
	}
}
