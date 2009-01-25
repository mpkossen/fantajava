<%-- 
    Document   : form-bankstatus
    Created on : 24-jan-2009, 11:18:12
    Author     : Vincent
--%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:subview id="subview-bankstatus">
    Hieronder kunt u de status van de bank wijzigen.<br />
    De bank is nu:<h:outputText rendered="#{!BankB.closedBankWarning}" value="Open!" /><h:outputText rendered="#{BankB.closedBankWarning}" value="Gesloten" /><br />
    <h:form id="form-bankstatus">
    <h:commandButton id="cmd-open-bank" value="Open" actionListener="#{BankManagerB.openBank}" />
    <h:commandButton id="cmd-sluit-bank" value="Sluit" actionListener="#{BankManagerB.sluitBank}" />
    </h:form>
</f:subview>