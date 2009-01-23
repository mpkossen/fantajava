<%-- 
    Document   : AccountOffice.jsp
    Created on : 23-Jan-2009, 10:01:16
    Author     : mistermartin75
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
	<div class="sidebar"></div>

	<div class="body">
	    <jsp:include page="/fragments/oversight-transactions.jsp" flush="true"></jsp:include>
	</div>
    </body>
</html>
</f:view>
