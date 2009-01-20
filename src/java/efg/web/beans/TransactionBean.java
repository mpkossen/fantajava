package java.efg.web.beans;

import javax.faces.event.ActionEvent; 

public class TransactionBean extends CommonBean

{

	public boolean validate() throws Exception {
		System.out.println("TransactionBean.validate()");

	}

	public void doOpnemen(ActionEvent ae) {
		System.out.println("OverboekenBean - doOverboeking()");
		try {
			System.out.println("Opnemen - " + number + ", " + amount);
			((MyPrincipal) ex.getUserPrincipal()).getAccountOffice().transfer(
					null, -amount);
			number = "";
			amount = 0.0;
		} catch (BankException e) {
			System.err
					.println("OverboekenBean - doOverboeking() - BankException: "
							+ e.getMessage());
		}
	}

	public void doOverboeking(ActionEvent ae) {
		System.out.println("OverboekenBean - doOverboeking()");
		try {
			System.out.println("overboeking - " + number + ", " + amount);
			((MyPrincipal) ex.getUserPrincipal()).getAccountOffice().transfer(
					number, amount);
			number = "";
			amount = 0.0;
		} catch (BankException e) {
			System.err
					.println("OverboekenBean - doOverboeking() - BankException: "
							+ e.getMessage());
		}
	}

	public void changeNumber(ValueChangeEvent vce) {
		System.out.println("LibraryManagerBean.changeNewBook("
				+ vce.getOldValue() + ", " + vce.getNewValue() + ")");
	}

	public void doStorten(ActionEvent ae) {
		System.out.println("StortenBean - doStorten()");
		try {
			System.out.println("storten - " + number + ", " + amount);
			((MyPrincipal) ex.getUserPrincipal()).getAccountOffice().transfer(
					null, amount);
			number = "";
			amount = 0.0;
		} catch (BankException e) {
			System.err
					.println("OverboekenBean - doOverboeking() - BankException: "
							+ e.getMessage());
		}
	}
}
