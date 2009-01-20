<%-- 
    Document   : NieuweAccount
    Created on : 19-jan-2009, 21:46:41
    Author     : Bami
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="includes/header.html" />
<div class="topdiv"><br />
<jsp:include page="includes/sidebar.jsp" >
    <jsp:param name="type" value="manager" />
</jsp:include>



<div id="content">

<form action="klant_overzicht.html">

<table>
<tr><td>
Gebruikersnaam:</td><td><input type="text"/></td></tr>
<tr><td>
Wachtwoord:</td><td> <input type="password"/> </td></tr>
</table>

<input type="submit" value="Log in!"/>
</form>
</div>
</div>


<jsp:include page="includes/footer.html" />
