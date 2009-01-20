package abcbank.jaas;

import java.security.Principal;

import efg.jpa.bank.*;

public class MyPrincipal implements Principal, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	protected String name = null;
	protected AccountOffice ao = null;
	protected AccountManager am = null;

	public MyPrincipal(String newName) {
		System.out.println("MyPrincipal(" + newName + ")");
		if (newName == null)
			throw new NullPointerException("illegal null input");
		name = newName;
	}

	public MyPrincipal(String newName, AccountManager am) {
		System.out.println("MyPrincipal(" + newName + ", " + am + ")");
		if (newName == null)
			throw new NullPointerException("illegal null input");
		name = newName;
		this.am = am;
	}

	public MyPrincipal(String newName, AccountOffice ao) {
		System.out.println("MyPrincipal(" + newName + ", " + ao + ")");
		if (newName == null)
			throw new NullPointerException("illegal null input");
		name = newName;
		this.ao = ao;
	}

	@Override
	public boolean equals(Object o) {
		System.out.println("MyPrincipal.equals()");
		boolean ret = false;
		if (o != null) {
			if (o instanceof MyPrincipal) {
				MyPrincipal other = (MyPrincipal) o;
				if (this.name.equals(other.name))
					ret = true;
			}
		}
		System.out.print("MyPrincipal(" + name + ").equals(" + o + "): " + ret);
		return ret;
	}

	public String getName() {
		System.out.println("MyPrincipal(" + name + ").getName()");
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

	@Override
	public int hashCode() {
		System.out.println("MyPrincipal(" + name + ").hashCode()");
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}
}