package jaas;

import efg.jpa.bank.AccountManager;
import efg.jpa.bank.AccountOffice;
import java.security.Principal;

public class MyPrincipal
	implements Principal, java.io.Serializable {

    private static final long serialVersionUID = 1L;
    protected String name = null;
    protected AccountOffice ao = null;
    protected AccountManager am = null;
    private Object userObject;

    public MyPrincipal(String newName) {
	//System.out.println("MyPrincipal("+newName+")");
	if (newName == null) {
	    throw new NullPointerException("illegal null input");
	}
	name = newName;
    }

    public MyPrincipal(String newName, Object uObject) {
	this(newName);
	userObject = uObject;
    }

    public String getName() {
	//System.out.println("MyPrincipal("+name+").getName()");
	return name;
    }

    public Object getUserObject() {
	return userObject;
    }

    public boolean equals(Object o) {
	boolean ret = false;
	if (o != null) {
	    if (o instanceof MyPrincipal) {
		MyPrincipal other = (MyPrincipal) o;
		if (this.name.equals(other.name)) {
		    ret = true;
		}
	    }
	}
	//System.out.print("MyPrincipal("+name+").equals("+o+"): "+ret);
	return ret;
    }

    public int hashCode() {
	//System.out.println("MyPrincipal("+name+").hashCode()");
	return name.hashCode();
    }

    public String toString() {
	return name;
    }

    public AccountManager getAccountManager() {
	System.out.println("MyPricipal.getAccountManager() " + am);
	return am;
    }

    public AccountOffice getAccountOffice() {
	System.out.println("MyPricipal.getAccountOffice() " + ao);
	return ao;
    }
}