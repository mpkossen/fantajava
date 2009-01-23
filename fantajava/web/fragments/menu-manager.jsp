<%-- 
    Document   : menu-manager
    Created on : 23-jan-2009, 11:39:57
    Author     : Vincent
--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<h:commandButton value="Nieuw Account" action="#{BankManagerB.setNewAccountPageTrue}"></h:commandButton>
<h:commandButton value="Check Account" action="#{BankManagerB.setCheckAccountPageTrue}"></h:commandButton>
<h:commandButton value="Status Bank" action="#{BankManagerB.setBankStatusPageTrue}"></h:commandButton>
