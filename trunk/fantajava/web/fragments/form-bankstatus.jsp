<%-- 
    Document   : form-bankstatus
    Created on : 24-jan-2009, 11:18:12
    Author     : Vincent
--%>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:subview id="subview-bankstatus">
    Hieronder kunt u de status van de bank wijzigen.
    <h:form onclick="Refresh">
    <h:commandButton value="Open" action="#{AccountManagerB.openAction}">
    </h:commandButton>
    <h:commandButton value="Sluit" action="#{AccountManagerB.sluitAction}">
    </h:commandButton>
    </h:form>
</f:subview>