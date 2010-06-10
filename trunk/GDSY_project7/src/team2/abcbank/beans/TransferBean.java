package team2.abcbank.beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.validator.ValidatorException;

import team2.abcbank.ejb.shared.BankException;

public class TransferBean extends CommonBean
{
	private static int ID = 0;

	private static int getId()
	{
		return ID++;
	}

	private int id = getId();
	private long reknrNaar = 0;
	private String bedrag = null;

	/*****************************************************************************
	 * Construction
	 ****************************************************************************/
	public TransferBean()
	{
		System.out.println("(" + id + ")TransferBean()");
	}

	/*****************************************************************************
	 * Getters
	 ****************************************************************************/
	public long getReknrNaar()
	{
		System.out.println("(" + id + ")TransferBean.getRekNrNaar()");
		return reknrNaar;
	}

	public String getBedrag()
	{
		System.out.println("TransferBean.getBedrag()");
		return bedrag;
	}

	/*****************************************************************************
	 * setters
	 ****************************************************************************/
	public void setReknrNaar(long reknr_naar)
	{
		System.out.println("(" + id + ")TransferBean.setRekNrNaar(String reknr_naar)");
		this.reknrNaar = reknr_naar;
	}

	public void setBedrag(String bedrag)
	{
		System.out.println("TransferBean.setBedrag(String bedrag)");
		this.bedrag = bedrag;
	}

	/*****************************************************************************
	 * call backs
	 ****************************************************************************/
	public void beforePhase(PhaseEvent phaseEvent)
	{
		System.out.println("TransferBean.beforePhase(" + phaseEvent.getPhaseId() + ")");
	}

	public void afterPhase(PhaseEvent phaseEvent)
	{
		System.out.println("TransferBean.afterPhase(" + phaseEvent.getPhaseId() + ")");
	}

	/*****************************************************************************
	 * command methods
	 ****************************************************************************/

	public void overboeken()
	{
		System.out.println("TransferBean.overboeken()");
		try
		{
			accountOffice.transfer(accountNumber, reknrNaar, CommonBean.getAmountInCents(bedrag));
			addMessage(FacesMessage.SEVERITY_INFO, "De transactie is in de wachtrij geplaatst. Druk op 'Verwerk transacties' om de transacties uit te voeren.");
			bedrag = null;
			reknrNaar = 0;
		}
		catch (BankException e)
		{
			addMessage(FacesMessage.SEVERITY_ERROR, "Er is een fout opgetreden: " + e.getMessage());
		}
	}

	/*****************************************************************************
	 * Validators
	 ****************************************************************************/
	public void bedragInputValidator(FacesContext ctx, UIComponent cmp, Object val)
	{
		System.out.println("TransferBean.BedragInputValidator(" + val + ")");
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
}
