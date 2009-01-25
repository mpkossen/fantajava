<%-- 
    Document   : menu-manager
    Created on : 23-jan-2009, 11:39:57
    Author     : Vincent
--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:subview id="subview-menu-manager">
    <h:form id="form-menu-manager">
	<h:commandButton id="cmd-new-acount" value="Nieuw Account" actionListener="#{BankManagerB.newAccountPage}" />
	<h:commandButton id="cmd-chk-acount" value="Check Account" actionListener="#{BankManagerB.checkAccountPage}" />
	<h:commandButton id="cmd-chk-status" value="Status Bank" actionListener="#{BankManagerB.bankStatusPage}" />
    </h:form>
</f:subview>