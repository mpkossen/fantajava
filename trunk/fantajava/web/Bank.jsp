<%-- 
    Document   : Bank.jsp
    Created on : 22-jan-2009, 13:06:47
    Author     : Bami
--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<f:view> 
BankBean: <h:outputText  value="#{BankB}" /><br />
BankBean: <h:outputText  value="#{BankB.displayManager}" /><br />
<h:panelGrid id="panel-manager" columns="1"
	rendered="#{BankB.displayManager}">  
	<meta http-equiv="Refresh" content="1; URL=BankManager.faces" />
</h:panelGrid>

<h:panelGrid id="panel-office" columns="1"
	rendered="#{BankB.displayOffice}">
	<meta http-equiv="Refresh" content="1; URL=BankOffice.faces" />
</h:panelGrid>
</f:view>
