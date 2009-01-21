package beans;

import jaas.MyPrincipal;
import efg.jpa.bank.AccountOffice;


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

	public void doTransaction() {
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
