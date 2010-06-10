package team2.abcbank.jaas;

import java.security.Principal;
import java.security.acl.Group;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;

public class MyGroup extends MyPrincipal implements Group
{
	protected HashSet<Principal> members = new HashSet<Principal>();

	public MyGroup(String name)
	{
		super(name);
	}

	public boolean addMember(Principal user)
	{
		boolean ret = false;
		if (!isMember(user))
		{
			members.add(user);
			ret = true;
		}
		return ret;
	}

	public boolean isMember(Principal member)
	{
		boolean ret = members.contains(member);
		return ret;
	}

	public Enumeration<Principal> members()
	{
		return Collections.enumeration(members);
	}

	public boolean removeMember(Principal user)
	{
		boolean ret = false;
		if (isMember(user))
		{
			members.remove(user);
			ret = true;
		}
		return ret;
	}

	public boolean equals(Object o)
	{
		boolean ret = false;
		if (o != null)
		{
			if (o instanceof MyGroup)
			{
				MyGroup other = (MyGroup) o;
				if (name.equals(other.name))
				{
					if (members.equals(other.members))
						ret = true;
				}
			}
		}
		return ret;
	}

	public int hashCode()
	{
		return members.hashCode();
	}

	public String toString()
	{
		return super.toString() + ": " + members;
	}
}
