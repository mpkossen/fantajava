package team2.abcbank.beans;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class EvenMoreCommonBean
{
	/**
	 * Can return null!! Only for session/application scoped beans!! Make sure that #{beanname} instanceof beanname!!
	 */
	public Object getBean(String bean)
	{
		try
		{
			FacesContext fc = getFacesContext();
			return fc.getApplication().evaluateExpressionGet(fc, "#{" + bean + "}", Class.forName(getClass().getPackage().getName() + "." + bean));
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public FacesContext getFacesContext()
	{
		return FacesContext.getCurrentInstance();
	}

	public ExternalContext getExternalContext()
	{
		return getFacesContext().getExternalContext();
	}

	public void addMessage(Severity severity, String message)
	{
		getFacesContext().addMessage(null, new FacesMessage(severity, message, message));
	}
}
