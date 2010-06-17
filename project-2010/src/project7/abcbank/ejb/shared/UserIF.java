package project7.abcbank.ejb.shared;

public interface UserIF
{
	public String getUsername();

	public void setUsername(String username) throws Exception;

	public String getPassword();

	public void setPassword(String password) throws Exception;

	public long getSalt();

	public void setSalt(long salt) throws Exception;
}
