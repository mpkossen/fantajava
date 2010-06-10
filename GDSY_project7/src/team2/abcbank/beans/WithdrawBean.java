package team2.abcbank.beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.validator.ValidatorException;

import team2.abcbank.ejb.shared.BankException;

public class WithdrawBean extends CommonBean
{
	private static int ID = 0;

	private static int getId()
	{
		return ID++;
	}

	private int id = getId();
	private String bedrag = null;

	/***************************************************************************
	 * Construction
	 **************************************************************************/
	public WithdrawBean()
	{
		System.out.println("(" + id + ")WithdrawBean()");
	}

	/***************************************************************************
	 * Getters
	 **************************************************************************/
	public String getBedrag()
	{
		System.out.println("WithdrawBean.getBedrag()");
		return bedrag;
	}

	/***************************************************************************
	 * setters
	 **************************************************************************/
	public void setBedrag(String bedrag)
	{
		System.out.println("WithdrawBean.setBedrag(String bedrag)");
		this.bedrag = bedrag;
	}

	/***************************************************************************
	 * call backs
	 **************************************************************************/
	public void beforePhase(PhaseEvent phaseEvent)
	{
		System.out.println("WithdrawBean.beforePhase(" + phaseEvent.getPhaseId() + ")");
	}

	public void afterPhase(PhaseEvent phaseEvent)
	{
		System.out.println("WithdrawBean.afterPhase(" + phaseEvent.getPhaseId() + ")");
	}

	/***************************************************************************
	 * Validators
	 **************************************************************************/
	public void bedragInputValidator(FacesContext ctx, UIComponent cmp, Object val)
	{
		System.out.println("WithdrawBean.BedragInputValidator(" + val + ")");
		try
		{
			if (CommonBean.getAmountInCents((String) val) <= 0)
				throw new ValidatorException(new FacesMessage("Het bedrag moet hoger zijn dan 0"));
		}
		catch (BankException e)
		{
			throw new ValidatorException(new FacesMessage(e.getMessage()));
		}
	}

	public void geldOpnemen()
	{
		System.out.println("WithdrawBean.geldOpnemen()");
		try
		{
			accountOffice.withdraw(accountNumber, CommonBean.getAmountInCents(bedrag));
			addMessage(FacesMessage.SEVERITY_INFO, "Uw transactie is naar de pending transacties gestuurd. Klik op 'Verwerk transacties' om de transactie te voltooien.");
			bedrag = null;
		}
		catch (BankException e)
		{
			addMessage(FacesMessage.SEVERITY_ERROR, "Er is een fout opgetreden: " + e.getMessage());
		}
	}
}
