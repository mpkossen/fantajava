package abcbank.jaas;

import java.security.Principal;

public class MyPrincipal
implements Principal, java.io.Serializable
{
  protected String name = null;

  public MyPrincipal(String newName)
  {
    //System.out.println("MyPrincipal("+newName+")");
    if (newName == null)
      throw new NullPointerException("illegal null input");
    name = newName;
  }

  public String getName()
  {
    //System.out.println("MyPrincipal("+name+").getName()");
    return name;
  }

  public boolean equals(Object o)
  {
    boolean ret = false;
    if (o != null)
    {
      if (o instanceof MyPrincipal)
      {
        MyPrincipal other = (MyPrincipal)o;
        if (this.name.equals(other.name)) ret = true;
      }
    }
    //System.out.print("MyPrincipal("+name+").equals("+o+"): "+ret);
    return ret;
  }

  public int hashCode()
  {
    //System.out.println("MyPrincipal("+name+").hashCode()");
    return name.hashCode();
  }

  public String toString()
  {
    return name;
  }

}
