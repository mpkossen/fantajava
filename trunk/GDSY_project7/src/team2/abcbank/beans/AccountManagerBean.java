package team2.abcbank.beans;

import javax.faces.event.ActionEvent;

public class AccountManagerBean extends CommonBean
{
	private String subview = "CheckAccount"; // default subview

	public void setSubview(ActionEvent event)
	{
		this.subview = (String) event.getComponent().getAttributes().get("to");
	}

	public String getSubview()
	{
		return "subviews/" + subview + ".jsp";
	}
}
