package team2.abcbank.beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.validator.ValidatorException;

import team2.abcbank.ejb.shared.AccountData;
import team2.abcbank.ejb.shared.BankException;

public class DepositBean extends CommonBean
{
	private static int ID = 0;

	private static int getId()
	{
		return ID++;
	}

	private int id = getId();
	private String bedrag = null;

	/*****************************************************************************
	 * Construction
	 ****************************************************************************/
	public DepositBean()
	{
		System.out.println("(" + id + ")DepositBean()");
	}

	/*****************************************************************************
	 * Getters
	 ****************************************************************************/
	public String getBedrag()
	{
		System.out.println("DepositBean.getBedrag()");
		return bedrag;
	}

	/*****************************************************************************
	 * setters
	 ****************************************************************************/
	public void setBedrag(String bedrag)
	{
		System.out.println("DepositBean.setBedrag(String bedrag)");
		this.bedrag = bedrag;
	}

	/*****************************************************************************
	 * call backs
	 ****************************************************************************/
	public void beforePhase(PhaseEvent phaseEvent)
	{
		System.out.println("DepositBean.beforePhase(" + phaseEvent.getPhaseId() + ")");
	}

	public void afterPhase(PhaseEvent phaseEvent)
	{
		System.out.println("DepositBean.afterPhase(" + phaseEvent.getPhaseId() + ")");
	}

	/*****************************************************************************
	 * Validators
	 ****************************************************************************/
	public void bedragInputValidator(FacesContext ctx, UIComponent cmp, Object val)
	{
		System.out.println("DepositBean.BedragInputValidator(" + val + ")");
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

	public void geldStorten()
	{
		System.out.println("DepositBean.geldStorten()");
		try
		{
			AccountData accountinformations = accountOffice.getDetails(accountNumber);
			accountOffice.deposit(accountNumber, CommonBean.getAmountInCents(bedrag)); // stort op eigen rekening.
			addMessage(FacesMessage.SEVERITY_INFO, "Uw transactie van AccountNr: " + accountinformations.getAccountNumber()
					+ " is naar de pending transactions gestuurd. Klik op 'Verwerk transacties' om de transactie te voltooien.");
		}
		catch (BankException e)
		{
			addMessage(FacesMessage.SEVERITY_ERROR, "Er is een fout opgetreden: " + e.getMessage());
		}
	}
}
