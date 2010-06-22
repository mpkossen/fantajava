<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<f:subview id="CheckAccount">
	<h3>Check account</h3>

	<h:messages errorClass="formError" infoClass="green" globalOnly="true"/>
    
	<h:form id="form-check-account">
    <fieldset>
    <table>
		<tr>
			<td>Account number:</td>
			<td><h:inputText id="accountNumber" value="#{CheckAccountBean.accountnumber}" required="true" /></td>
			<td><h:message styleClass="formError" for="accountNumber" /></td>
		</tr>
		<tr>
       		<td colspan="3"><h:outputLabel styleClass="formError" value="* Alle velden zijn verplicht"></h:outputLabel></td>
		</tr>
		<tr>
			<td></td>
            <td><h:commandButton action="#{CheckAccountBean.check}" value="Check" /><h:commandButton value="Reset" type="reset"/></td>
			<td></td>
		</tr>
        </table>
        </fieldset>
	</h:form>
    
	<h:panelGroup rendered="#{CheckAccountBean.checkingAccount}">
		<span class="bold">Accountnaam:</span> <h:outputText value="#{CheckAccountBean.name}" />
		<span class="bold">Accountnummer:</span> <h:outputText value="#{CheckAccountBean.accountnumber}" />
		<span class="bold">Balans:</span><h:outputText value="#{CheckAccountBean.balance}" />
        <span class="bold">Limiet:</span><h:outputText value="#{CheckAccountBean.limit}" />
		<span class="bold">Transacties:</span>
		<h:dataTable value="#{CheckAccountBean.transactions}" var="t" cellpadding="1" cellspacing="1" styleClass="overview" border="1">
			<h:column>
				<f:facet name="header"><h:outputText value="Van" /></f:facet>
				<h:outputText value="#{t.from}" />
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Naar" /></f:facet>
				<h:outputText value="#{t.to}" />
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Bedrag" /></f:facet>
				<h:outputText value="#{t.amount}" />
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Datum opdracht" /></f:facet>
				<h:outputText value="#{t.dateCreated}" />
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Datum transactie" /></f:facet>
				<h:outputText value="#{t.dateFinished}" />
			</h:column>
		</h:dataTable>
		<!--<h:outputText value="#{CheckAccountBean.transactions}" />-->
	</h:panelGroup> 
</f:subview>