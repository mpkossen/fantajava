package efg.jpa.bank;

public interface AccountOfficeIF
{
  String[] getDetails(); 
  String[][] getPendingTransacties();  
  String transfer( String number, double amount )throws BankException;   
  void sync(); 
  void close();
}
