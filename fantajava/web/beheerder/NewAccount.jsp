<%-- 
    Document   : NewAccount
    Created on : 21-jan-2009, 17:49:42
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
	    <form id="form1" name="form1" method="post" action="">
		<label>Voornaam</label>
		
		<input type="text" name="textfield" id="textfield" />
		
		<br />
		<label>Achternaam</label>
		<input type="text" name="textfield" id="textfield" />
		
		<br />
		<label>Adres</label>
		<input type="text" name="textfield" id="textfield" />
		
		<br />
		
		<label>Postcode </label>
		<input type="text" name="textfield" id="textfield" />
		
		<br />
		<label>Woonplaats </label>
		<input type="text" name="textfield" id="textfield" />
		
		<br />
		<input type="submit" name="zoek" id="zoek" value="Maak aan!" />
		
		<br />
		
	    </form>
	    
	</div>
    </body>
</html>
