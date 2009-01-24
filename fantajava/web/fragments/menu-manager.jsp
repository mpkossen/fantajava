<%-- 
    Document   : menu-manager
    Created on : 23-jan-2009, 11:39:57
    Author     : Vincent
--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<h:form onclick="Refresh">
<h:commandButton value="Nieuw Account" actionListener="#{BankManagerB.newAccountPage}"></h:commandButton>
<h:commandButton value="Check Account" actionListener="#{BankManagerB.checkAccountPage}"></h:commandButton>
<h:commandButton value="Status Bank" actionListener="#{BankManagerB.bankStatusPage}"></h:commandButton>
</h:form>