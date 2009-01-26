/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import efg.jpa.bank.AccountOffice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.component.html.HtmlColumn;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.servlet.http.HttpSession;
import javax.el.ValueExpression;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;

/**
 *
 * @author mistermartin75
 */
public class AccountOfficeBean extends CommonBean
{
    private static List<List<String>> transacties;
    private static String[] dynamicHeaders;
    private HtmlPanelGroup dynamicDataTableGroup;
	private AccountOffice accountOffice;
	private static int ID = 0;
	private HttpSession session = null;
	private String id;

	private static int getId ()
	{
		return ID++;
	}

	public AccountOfficeBean()
	{
        System.out.println("AccountOfficeBean()");
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if ( session != null )
		{
			//setException("Id=" + id + ", sessionId=" + session.getId());
			accountOffice = (AccountOffice) session.getAttribute("accountOffice");
			if ( accountOffice != null )
			{
				loadTransacties();
				return;
			}
		}
		try
		{
			FacesContext.getCurrentInstance().getExternalContext().redirect("AccountOffice.faces");
		}
		catch ( Exception e )
		{
			System.out.println(e.getMessage());
		}
	}

    private void loadTransacties()
	{
		dynamicHeaders = new String[] {"ID", "Name"};
		transacties = new ArrayList<List<String>>();

		String[][] oldtransacties = accountOffice.getPendingTransacties();

		for(int i = 0; i < oldtransacties.length; i++)
		{
			transacties.add(Arrays.asList(new String[] { oldtransacties[i][i], oldtransacties[i][i+1] }));
		}
    }

    private void populateDynamicDataTable() {

        // Create <h:dataTable value="#{AccountOfficeB.transacties}" var="transacties">.
        HtmlDataTable dynamicDataTable = new HtmlDataTable();
        dynamicDataTable.setValueExpression("value",
            createValueExpression("#{AccountOfficeBean.transacties}", List.class));
        dynamicDataTable.setVar("transacties");

        for (int i = 0; i < transacties.get(0).size(); i++) {

            // Create <h:column>.
            HtmlColumn column = new HtmlColumn();
            dynamicDataTable.getChildren().add(column);

            // Create <h:outputText value="dynamicHeaders[i]"> for <f:facet name="header"> of column.
            HtmlOutputText header = new HtmlOutputText();
            header.setValue(dynamicHeaders[i]);
            column.setHeader(header);

            // Create <h:outputText value="#{transacties[" + i + "]}"> for the body of column.
            HtmlOutputText output = new HtmlOutputText();
            output.setValueExpression("value",
                createValueExpression("#{transacties[" + i + "]}", String.class));
            column.getChildren().add(output);
        }

        // Add the datatable to <h:panelGroup binding="#{AccountOfficeB.dynamicDataTableGroup}">.
        dynamicDataTableGroup = new HtmlPanelGroup();
        dynamicDataTableGroup.getChildren().add(dynamicDataTable);
    }

    public HtmlPanelGroup getDynamicDataTableGroup()
	{
        // This will be called once in the first RESTORE VIEW phase.
        if (dynamicDataTableGroup == null)
		{
            loadTransacties();
            populateDynamicDataTable();
        }

        return dynamicDataTableGroup;
    }

    public List<List<String>> getTransacties()
	{
        return transacties;
    }

    public void setDynamicDataTableGroup(HtmlPanelGroup dynamicDataTableGroup) {
        this.dynamicDataTableGroup = dynamicDataTableGroup;
    }

	private ValueExpression createValueExpression(String valueExpression, Class<?> valueType)
	{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getExpressionFactory().createValueExpression(
            facesContext.getELContext(), valueExpression, valueType);
    }
	
	@Override
	protected void finalize () throws Throwable
	{
		System.err.println("AccountOfficeBean.finalize()");
		super.finalize();
	}
}
