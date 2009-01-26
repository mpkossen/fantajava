<%--
    Document   : TransactieOverzicht
    Created on : 19-Jan-2009, 13:38:48
    Author     : mistermartin75
--%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<f:subview id="overview-transactions">
    <h:panelGroup binding="#{AccountOfficeB.dynamicDataTableGroup}" />
</f:subview>
