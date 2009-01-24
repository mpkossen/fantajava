<%-- 
    Document   : menu-manager
    Created on : 23-jan-2009, 11:39:57
    Author     : Vincent
--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<h:form id="menu-manager">
<h:commandButton id="cmd-new-acount" value="Nieuw Account" actionListener="#{BankManagerB.doNewAccountPage}"></h:commandButton>
<h:commandButton id="cmd-chk-acount" value="Check Account" actionListener="#{BankManagerB.doCheckAccountPage}"></h:commandButton>
<h:commandButton id="cmd-chk-status" value="Status Bank" actionListener="#{BankManagerB.doBankStatusPage}"></h:commandButton>
</h:form>