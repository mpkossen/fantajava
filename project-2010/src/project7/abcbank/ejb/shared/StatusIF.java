package project7.abcbank.ejb.shared;

public interface StatusIF
{
	public int getStatusID();

	public void setStatusID(int id);

	public boolean getBankIsOpen();

	public void setBankIsOpen(boolean status);

	public long getBalance();

	public void setBalance(long balance);
}
