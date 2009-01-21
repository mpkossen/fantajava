<%-- 
    Document   : index (login)
    Created on : 21-jan-2009, 12:38:09
    Author     : Bami
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>   
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
	    <h1>Login</h1>
	    <form action="j_security_check" method="post">
		<dl>
		    <dt><label for="j_username">Reknr</label></dt>
		    <dd><input type="text" name="j_username" /></dd>
		</dl>
		<dl>
		    <dt><label for="j_password">Pincode</label></dt>
		    <dd><input type="password" name="j_password" /></dd>
		</dl>
	    <input type="submit" value="Login" /></form>
	</div>
    </body>
</html>
