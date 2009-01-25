<%-- 
    Document   : form-bankstatus
    Created on : 24-jan-2009, 11:18:12
    Author     : Vincent
--%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

    <h:form id="form-bankstatus">
	<h:commandButton id="cmd-open-bank" value="Open" actionListener="#{AccountManagerB.openBank}" />
	<h:commandButton id="cmd-sluit-bank" value="Sluit" actionListener="#{AccountManagerB.sluitBank}" />
    	<h:commandButton id="cmd-new-account" value="Nieuw Account" actionListener="#{BankManagerB.newAccountPage}" />
	<h:commandButton id="cmd-chk-account" value="Check Account" actionListener="#{BankManagerB.checkAccountPage}" />
	<h:commandButton id="cmd-chk-status" value="Status Bank" actionListener="#{BankManagerB.bankStatusPage}" />
    </h:form>
