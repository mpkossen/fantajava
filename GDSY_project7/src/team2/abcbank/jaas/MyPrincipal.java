package team2.abcbank.jaas;

import java.security.Principal;

public class MyPrincipal implements Principal
{
	protected String name = null;

	public MyPrincipal(String newName)
	{
		if (newName == null)
			throw new IllegalArgumentException("newName cannot be null");
		name = newName;
	}

	public String getName()
	{
		return name;
	}

	public boolean equals(Object o)
	{
		boolean ret = false;
		if (o != null)
		{
			if (o instanceof MyPrincipal)
			{
				MyPrincipal other = (MyPrincipal) o;
				if (name.equals(other.name))
					ret = true;
			}
		}
		return ret;
	}

	public int hashCode()
	{
		return name.hashCode();
	}

	public String toString()
	{
		return name;
	}
}
