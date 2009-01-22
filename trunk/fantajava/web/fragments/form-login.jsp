<%-- 
    Document   : Login
    Created on : 21-jan-2009, 23:48:58
    Author     : Bami
--%>
<f:subview id="subview-login">
<h1>Login</h1>
<form action="j_security_check" method="post" action="j_security_check">
    <dl>
	<dt><label for="j_username">Gebruikersnaam</label></dt>
	<dd><input type="text" name="j_username" /></dd>
    </dl>
    <dl>
	<dt><label for="j_password">Wachtwoord</label></dt>
	<dd><input type="password" name="j_password" /></dd>
    </dl>
<input type="SUBMIT" value="Login" /></form>
</f:subview>