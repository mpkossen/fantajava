package team2.abcbank.ejb.shared;

/**
 * Entity!
 * 
 * @author ejvos
 */
public interface StatusIF
{
	public int getStatusID();

	public void setStatusID(int id);

	public boolean getBankIsOpen();

	public void setBankIsOpen(boolean status);

	public long getBalance();

	public void setBalance(long balance);
}
