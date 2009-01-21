package jaas;

import javax.security.auth.callback.PasswordCallback;

public class MyPasswordCallback
extends PasswordCallback
{
  private static final long serialVersionUID = -6707193005751200799L;

  public MyPasswordCallback(String prompt, boolean echoOn)
  {
    super(prompt, echoOn);
    System.out.println("MyPasswordCallback("+prompt+", "+echoOn+")");
  }

  public void clearPassword()
  {
    System.out.println("MyPasswordCallback.clearPassword()");
    super.clearPassword();
  }

  public char[] getPassword()
  {
    System.out.println("MyPasswordCallback.getPassword()");
    return super.getPassword();
  }

  public String getPrompt()
  {
    System.out.println("MyPasswordCallback.getPrompt()");
    return super.getPrompt();
  }

  public boolean isEchoOn()
  {
    System.out.println("MyPasswordCallback.isEchoOn()");
    return super.isEchoOn();
  }

  public void setPassword(char[] password)
  {
    System.out.println("MyPasswordCallback.setPassword("+new String(password)+")");
    super.setPassword(password);
  }

}

