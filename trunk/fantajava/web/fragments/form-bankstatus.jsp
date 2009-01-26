<%-- 
    Document   : form-bankstatus
    Created on : 24-jan-2009, 11:18:12
    Author     : Vincent
--%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:subview id="view-bankstatus">
    <h:form id="form-bankstatus" onsubmit="Refresh">
	De bank is nu: <h:outputText rendered="#{!BankB.closedBankWarning}" value="Open!" /><h:outputText rendered="#{BankB.closedBankWarning}" value="Gesloten" /><br />
	<h:commandButton rendered="#{BankB.closedBankWarning}" id="cmd-open-bank" value="Open" actionListener="#{AccountManagerB.openAction}" />
	<h:commandButton rendered="#{!BankB.closedBankWarning}"id="cmd-sluit-bank" value="Sluit" actionListener="#{AccountManagerB.sluitAction}" />
    </h:form>
</f:subview>