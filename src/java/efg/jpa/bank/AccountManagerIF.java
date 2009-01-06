package efg.jpa.bank;

public interface AccountManagerIF
{
   String[] getAccount(String number);
   String[][] getTransactions(String number);
   String newAccount(double newLimit, String newName, String newPincode) throws BankException;
   String setOpen(boolean b);
}
