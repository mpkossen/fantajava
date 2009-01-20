package efg.web.beans;

import javax.faces.event.*;
import javax.faces.context.*;


public class TransactionBean extends CommonBean {

	protected ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
	protected double amount;
	protected String number = null;

	public boolean validate() throws Exception {
		System.out.println("TransactionBean.validate()");
	}

	public void doOpnemen(ActionEvent ae) {
		System.out.println("TransactionBean.doOverboeking()");
		try {
			System.out.println("Opnemen - " + number + ", " + amount);
			((MyPrincipal) ex.getUserPrincipal()).getAccountOffice().transfer(
					null, -amount);
			number = "";
			amount = 0.0;
		} catch (BankException e) {
			System.err
					.println("TransactionBean.doOverboeking() - BankException: "
							+ e.getMessage());
		}
	}

	public void doOverboeking(ActionEvent ae) {
		System.out.println("TransactionBean.doOverboeking()");
		try {
			System.out.println("overboeking - " + number + ", " + amount);
			((MyPrincipal) ex.getUserPrincipal()).getAccountOffice().transfer(
					number, amount);
			number = "";
			amount = 0.0;
		} catch (BankException e) {
			System.err
					.println("TransactionBean.doOverboeking() - BankException: "
							+ e.getMessage());
		}
	}

	public void changeNumber(ValueChangeEvent vce) {
		System.out.println("LibraryManagerBean.changeNewBook("
				+ vce.getOldValue() + ", " + vce.getNewValue() + ")");
	}

	public void doStorten(ActionEvent ae) {
		System.out.println("TransactionBean.doStorten()");
		try {
			System.out.println("storten - " + number + ", " + amount);
			((MyPrincipal) ex.getUserPrincipal()).getAccountOffice().transfer(
					null, amount);
			number = "";
			amount = 0.0;
		} catch (BankException e) {
			System.err
					.println("TransactionBean.doOverboeking() - BankException: "
							+ e.getMessage());
		}
	}
}
