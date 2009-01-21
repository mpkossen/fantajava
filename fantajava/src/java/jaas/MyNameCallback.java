package jaas;

import javax.security.auth.callback.NameCallback;

public class MyNameCallback
extends NameCallback
{
  public MyNameCallback(String prompt)
  {
    super(prompt);
    System.out.println("MyNameCallback("+prompt+")");
  }

  public MyNameCallback(String prompt, String defaultName)
  {
    super(prompt, defaultName);
    System.out.println("MyNameCallback("+prompt+", "+defaultName+")");
  }

  public String getDefaultName()
  {
    System.out.println("MyNameCallback.getDefaultName()");
    return super.getDefaultName();
  }

  public String getName()
  {
    System.out.println("MyNameCallback.getName()");
    return super.getName();
  }

  public String getPrompt()
  {
    System.out.println("MyNameCallback.getPrompt()");
    return super.getPrompt();
  }

  public void setName(String name)
  {
    System.out.println("MyNameCallback.setName("+name+")");
    super.setName(name);
  }

}