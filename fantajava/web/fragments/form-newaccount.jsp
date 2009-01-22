<%-- 
    Document   : form-newaccount
    Created on : 22-jan-2009, 8:51:06
    Author     : Vincent
--%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:subview id="subview-newaccount">
    <h1>Nieuw Account aanmaken</h1>
	<h:form id="form-newaccount">
		<ul>
			<li><label>Gebruikersnaam:</label><h:inputText id="username" styleClass="formInput" value="#{AccountManagerB.newaccount.newName}" required="true" valueChangeListener="#{AccountManagerB.setNewName}"/></li>
			<li><label>Limiet:</label><h:inputText id="limit" styleClass="formInput" value="#{AccountManagerB.newaccount.newLimit}" required="true" valueChangeListener="#{AccountManagerB.setNewLimit}"/></li>
			<li><label>Pincode:</label><h:inputSecret id="pincode" styleClass="formInput" value="#{AccountManagerB.newaccount.newPincode}" required="true" valueChangeListener="#{AccountManagerB.setNewPincode}"/></li>
			<li><input type="reset" name="reset" value="Wissen" /><h:commandButton id="createNewAccount" type="submit" value="createNewAccount" actionListener="#{AccountManagerB.newAccount}"/></li>
		</ul>
	</h:form>
</f:subview>
