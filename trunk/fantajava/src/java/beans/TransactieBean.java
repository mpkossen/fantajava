package abcbank.beans;

import efg.jpa.bank.AccountOffice;
import abcbank.jaas.MyPrincipal;

public class TransactieBean {

	private ExternalContext ex;

	private MyPrincipal mp;

	private AccountOffice ao;

	public String bedrag;

	public TransactieBean() {

		ex = FacesContext.getCurrentInstance().getExternalContext();
		mp = (MyPrincipal) ex.getUserPrincipal();
		ao = mp.getAccountOffice();

	}
	
	public void doTransaction(String tegenrekening, double bedrag) {
		if(tegenrekening.equals("")) {
			tegenrekening = null;
		}
		ao.transfer(tegenrekening, bedrag);
	}
	
}
