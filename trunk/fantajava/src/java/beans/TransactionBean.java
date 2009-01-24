package beans;

import efg.jpa.bank.BankException;
import jaas.MyPrincipal;
import efg.jpa.bank.AccountOffice;
import java.awt.event.ActionEvent;
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
            System.out.println("TransactionBean()");

		ex = FacesContext.getCurrentInstance().getExternalContext();
		mp = (MyPrincipal) ex.getUserPrincipal();
		ao = mp.getAccountOffice();
	}

	public void doTransaction() throws BankException {
            System.out.println("TransactionBean.doTransaction()");
		ao.transfer(tegenrekening, amount);
	}

	public void changeAmount(String amount) {
            System.out.println("TransactionBean.changeAmount()");
		try {
			this.amount = Double.parseDouble(amount);
		} catch (Exception e) {
			System.out.println("Er ging iets fout met het bedrag!");
		}
	}

	public void changeTegenrekening(String rekening) {
            System.out.println("TransactionBean.changeTegenrekening()");
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
            System.out.println("TransactionBean.getRenderStorten()");
		return render[0];
	}

	public boolean getRenderOpnemen() {
            System.out.println("TransactionBean.getRenderOpnemen()");
		return render[1];
	}

	public boolean getRenderOverboeken() {
            System.out.println("TransactionBean.getRenderOverboeken()");
		return render[2];
	}
	
	public boolean getRenderOverzicht() {
            System.out.println("TransactionBean.getRenderOverzicht()");
		return render[3];
	}

        public void setViewStorten(ActionEvent ae) {
        System.out.println("TransactionBean.setViewStorten()");
	naarPagina(0);
        }

        public void setViewOpnemen(ActionEvent ae) {
        System.out.println("TransactionBean.setViewOpnemen()");
	naarPagina(1);
        }

        public void setViewOverboeken(ActionEvent ae) {
        System.out.println("TransactionBean.setViewOverboeken()");
	naarPagina(2);
        }

        public void setViewOverzicht(ActionEvent ae) {
        System.out.println("TransactionBean.setViewOverzicht()");
	naarPagina(3);
        }

        private void naarPagina(int i) {
        System.out.println("BankManagerBean.naarPagina()");
	render = new boolean[]{false, false, false, false};
	if (i >= 0 && i <= render.length) {
	    render[i] = true;
	}
    }
}
