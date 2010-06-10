<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css"/>
<script type="text/javascript" src="javascript.js"></script>

<title>ABC Bank - Applicatie</title>
</head>
<body class="oneColElsCtrHdr">
<div id="container">
<div id="header">
<center>ABC BANK - Applicatie</center>
<!-- end #header --></div>

<h:panelGroup rendered="#{facesContext.externalContext.userPrincipal != null}"><h:form id="logoutForm"><p>U bent ingelogd als: <h:outputText value="#{facesContext.externalContext.userPrincipal.name}" /> | <h:commandLink value="Uitloggen" action="#{BankAccessBean.logout}" onclick="return doBevestigen();" /></p></h:form></h:panelGroup>