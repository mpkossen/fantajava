package abcbank.jaas;

import javax.security.auth.callback.PasswordCallback;

public class MyPasswordCallback
extends PasswordCallback
{
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
    System.out.print("MyPasswordCallback.setPassword("+new String(password)+")");
    super.setPassword(password);
  }

}
