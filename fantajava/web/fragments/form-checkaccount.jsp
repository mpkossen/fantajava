<%-- 
    Document   : form-checkaccount
    Created on : 24-jan-2009, 17:57:57
    Author     : Bami
--%>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:subview id="subview-checkaccount">
    Hieronder kunt u een account bekijken.
    <h:form onclick="Refresh">
    <h:commandButton value="Open" action="#{AccountManagerB.openAction}">
    </h:commandButton>
    <h:commandButton value="Sluit" action="#{AccountManagerB.sluitAction}">
    </h:commandButton>
    </h:form>
</f:subview>
