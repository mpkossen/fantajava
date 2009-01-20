package abcbank.jaas;

import java.security.Principal;
import java.security.acl.Group;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;

public class MyGroup extends MyPrincipal implements Group
{
	private static final long serialVersionUID = 1L;
	private HashSet<Principal> members = new HashSet<Principal>();

	public MyGroup(String name)
	{
		super(name);
		System.out.println("MyGroup(" + name + ")");
	}

	public boolean addMember(Principal user)
	{
//		System.out.println("MyGroup.addMember("+user+")");
		boolean ret = false;
		if (!isMember(user))
		{
			members.add(user);
			ret = true;
		}
		System.out.println("MyGroup.addMember(" + user + "): " + ret);
		return ret;
	}

	public boolean isMember(Principal member)
	{
		System.out.println("MyGroup.isMember("+member+")");
		boolean ret = members.contains(member);
		System.out.println("MyGroup(" + name + ").isMember(" + member + "): " + ret);
		return ret;
	}

	public Enumeration<Principal> members()
	{
		System.out.println("MyGroup(" + name + ").members()");
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
		System.out.println("MyGroup(" + name + ").removeMember(" + user + "): " + ret);
		return ret;
	}

	@Override
	public boolean equals(Object o)
	{
//		System.out.print("MyGroup.equals()");
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
		System.out.print("MyGroup(" + name + ").equals(" + o + "): " + ret);
		return ret;
	}

	@Override
	public int hashCode()
	{
//		System.out.println("MyGroup(" + name + ").hashCode()");
		return members.hashCode();
	}

	@Override
	public String toString()
	{
		return super.toString() + ": " + members;
	}
}
