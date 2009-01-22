<%-- 
    Document   : form-newaccount
    Created on : 22-jan-2009, 8:51:06
    Author     : Vincent
--%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:subview id="subview-login">
    <h1>Login</h1>
	<h:form id="form-newaccount">
		<ul>
			<li><label>Gebruikersnaam:</label><h:inputText id="newusername" styleClass="formInput" value="#{AccountManagerB.newaccount.newName}" required="true" valueChangeListener="#{AccountManagerB.newaccount.setnewName}"/></li>
			<li><label>Limitatie:</label><h:inputText id="newlimit" styleClass="formInput" value="#{AccountManagerB.newaccount.newLimit}" required="true" valueChangeListener="#{AccountManagerB.newaccount.setnewLimit}"/></li>
			<li><label>PIN:</label><h:inputText id="newpincode" styleClass="formInput" value="#{AccountManagerB.newaccount.newPincode}" required="true" valueChangeListener="#{AccountManagerB.newaccount.setnewPincode}"/></li>
			<li><input type="reset" name="reset" value="Wissen" /><h:commandButton id="createNewAccount" type="submit" value="createNewAccount" actionListener="#{AccountManagerB.newAccount}"/></li>
		</ul>
	</h:form>
</f:subview>
