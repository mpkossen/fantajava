package beans;

import jaas.MyPrincipal;
import efg.jpa.bank.AccountOffice;


public class TransactieBean {

	private ExternalContext ex;

	private MyPrincipal mp;

	private AccountOffice ao;

	public double amount;
	
	public String tegenrekening = null;

	public TransactieBean() {

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
}
