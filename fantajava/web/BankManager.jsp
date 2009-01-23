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
        <jsp:include page="/fragments/menu-manager.jsp" flush="true"></jsp:include>
        </div>
<div class="body">
    <h:panelGrid id="newAccount" columns="1"
	rendered="#{BankManagerB.displayNewAccount}">
	 <jsp:include page="/fragments/form-newaccount.jsp" flush="true"></jsp:include>
    </h:panelGrid>
    <h:panelGrid id="checkAccount" columns="1"
	rendered="#{BankManagerB.displayCheckAccount}">
	<jsp:include page="/fragments/form-checkaccount.jsp" flush="true"></jsp:include>
    </h:panelGrid>
    <h:panelGrid id="bankStatus" columns="1"
	rendered="#{BankManagerB.displayBankStatus}">
	<jsp:include page="/fragments/form-bankStatus.jsp" flush="true"></jsp:include>
    </h:panelGrid>
</div>
    </body>
</html>
</f:view>