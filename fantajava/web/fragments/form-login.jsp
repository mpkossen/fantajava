<%-- 
    Document   : Login
    Created on : 21-jan-2009, 23:48:58
    Author     : Bami
--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<f:subview id="form-login">
<h1>Login</h1>
<h3><h:outputText rendered="#{!BankB.closedBankWarning}" value="De ABC bank is open." /></h3>
<h:outputText rendered="#{BankB.busyBankWarning}" value="De ABC bank is overbezet, een moment geduld aub." />
<h:panelGrid columns="1" rendered="#{!BankB.busyBankWarning}" style="width:100%;">
<h:outputText rendered="#{BankB.closedBankWarning}" value="De ABC bank is gesloten. Alleen managers kunnen inloggen." />
<form action="j_security_check" method="post" action="j_security_check">
    <dl>
	<dt><label for="j_username">Gebruikersnaam</label></dt>
	<dd><input type="text" name="j_username" /></dd>
    </dl>
    <dl>
	<dt><label for="j_password">Wachtwoord</label></dt>
	<dd><input type="password" name="j_password" /></dd>
    </dl>
<input type="SUBMIT" value="Login" /></form>
</h:panelGrid>
</f:subview>