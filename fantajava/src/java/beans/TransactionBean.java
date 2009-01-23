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

	private static boolean[] render = { false, false, false, true };
	
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
		} catch (Exception e) {
			System.out.println("Er ging iets fout met het bedrag!");
		}
	}

	public void changeTegenrekening(String rekening) {
		if (rekening.equals("")) {
			this.tegenrekening = null;
		} else {
			try {
				int i = Integer.parseInt(rekening);
				this.tegenrekening = rekening;
			} catch (Exception e) {
				System.out.println("Er ging iets fout met de tegenrekening!");
			}
		}
	}

	public boolean getRenderStorten() {
		return render[0];
	}

	public boolean getRenderOpnemen() {
		return render[1];
	}

	public boolean getRenderOverboeken() {
		return render[2];
	}
	
	public boolean getRenderOverzicht() {
		return render[3];
	}
	
	public void setViewStorten() {
		render[0] = true;
		render[1] = false;
		render[2] = false;
		render[3] = false;
	}
	
	public void setViewOpnemen() {
		render[0] = false;
		render[1] = true;
		render[2] = false;
		render[3] = false;
	}
	
	public void setViewOverboeken() {
		render[0] = false;
		render[1] = false;
		render[2] = true;
		render[3] = false;
	}
	
	public void setViewOverzicht() {
		render[0] = false;
		render[1] = false;
		render[2] = false;
		render[3] = true;
	}
}
