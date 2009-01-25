<%-- 
    Document   : BankManager
    Created on : 22-jan-2009, 14:33:00
    Author     : Bami
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>   
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
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
	    <div class="sidebar">
		
		<jsp:include page="/fragments/transaction_submenu.jsp" flush="true"></jsp:include>
	    </div>
	    <div class="body">
		
		<f:subview id="subview-choice">
		    <h:panelGrid id="opnemen" columns="1" rendered="#{TransactionB.getRenderOpnemen}" >
			<jsp:include page="/fragments/transaction_opnemen.jsp" flush="true" />
		    </h:panelGrid>
		    <h:panelGrid id="overboeken" columns="1" rendered="#{TransactionB.getRenderOverboeken}" >
			<jsp:include page="/fragments/transaction_overboeken.jsp" flush="true" />
		    </h:panelGrid>
		    <h:panelGrid id="storten" columns="1" rendered="#{TransactionB.getRenderStorten}" >
			<jsp:include page="/fragments/transaction_storten.jsp" flush="true" />
		    </h:panelGrid>
		    <h:panelGrid id="overzicht" columns="1" rendered="#{TransactionB.getRenderOverzicht}" >
			<jsp:include page="/fragments/overview-transactions.jsp" flush="true" />
		    </h:panelGrid>
		    
		</f:subview>
		
	    </div>
	</body>
    </html>
</f:view>