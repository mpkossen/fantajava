<%-- 
    Document   : Bank.jsp
    Created on : 22-jan-2009, 13:06:47
    Author     : Bami
--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<f:view>
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>ABC-BANK</title>
	    <link href="css/style.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
	    <div class="topdiv"></div>
	    <div class="sidebar"></div>
	    
	    <div class="body">
		Inloggen gelukt, U word over enkele ogenblikken naar de juiste pagina geleid.
		<h:panelGrid id="panel-manager" columns="1"
			     rendered="#{BankB.displayManager}">  
		    <meta http-equiv="Refresh" content="1; URL=BankManager.faces" />
		</h:panelGrid>
		
		<h:panelGrid id="panel-office" columns="1"
			     rendered="#{BankB.displayOffice}">
		    <meta http-equiv="Refresh" content="1; URL=BankOffice.faces" />
		</h:panelGrid>
	    </div>
	</body>
    </html>
</f:view>
