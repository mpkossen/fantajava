package beans;

import efg.jpa.bank.BankException;
import jaas.MyPrincipal;
import efg.jpa.bank.AccountOffice;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


public class TransactionBean {

	private ExternalContext ex;

	private MyPrincipal mp;

	private AccountOffice ao;

	private double amount;
	
	private String tegenrekening = null;
	
	private String view = "";

	public TransactionBean() {

		ex = FacesContext.getCurrentInstance().getExternalContext();
		mp = (MyPrincipal) ex.getUserPrincipal();
		ao = mp.getAccountOffice();
	}

	public void doTransaction() throws BankException {
		ao.transfer(tegenrekening, amount);
	}
	
	public void changeAmount(String amount) {	
		try {
			this.amount = Double.parseDouble(amount);
		}
		catch(Exception e) {
			System.out.println("Er ging iets fout met het bedrag!");
		}	
	}
	
	public void changeTegenrekening(String rekening) {	
		if(rekening.equals("")) {
			this.tegenrekening = null;
		}
		else {
			try {
				int i = Integer.parseInt(rekening);
				this.tegenrekening = rekening;	
			}
			catch(Exception e) {
				System.out.println("Er ging iets fout met de tegenrekening!");
			}
		}
	}
	
	
	//TODO: fixen!
	public void setView(String s) {
/*		if(s.equals("Storten")) {
			
		}
		else if(s.equals("Opnemen")) {
			
		}
		else if(s.equals("Overboeken")) {
			
		}*/
		
		view = s;
	}
}
