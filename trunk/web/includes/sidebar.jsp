<!-- 
    Document   : sidebar.jsp
    Created on : 19-jan-2009, 21:50:17
    Author     : Bami
-->
<div class="sidebar">
<% 
System.out.println("type:"+request.getParameter("type"));
if (request.getParameter("type").equals("manager")) { 
System.out.println("win:"+request.getParameter("type")); 
%>
Manager<br />
<a href="NieuweAccount.jsp">Nieuwe Account</a><br />
<a href="ZoekAccount.jsp">Zoek Account</a><br />
<a href="WijzigStatus.jsp">Wijzig status</a><br />
<a href="loguit.jsp">Loguit</a><br />
<% 
}
if (request.getParameter("type").equals("gebruiker")) { 
%>
Gebruiker<br />
<a href="Transactie.jsp">Doe transactie</a><br/>
<a href="TransactieOverzicht.html">Transactieoverzicht</a><br/>
<a href="loguit.jsp">Loguit</a><br/>
<%
}
%>
</div>
