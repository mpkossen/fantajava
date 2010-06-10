<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<f:subview id="NewAccount">
	<h3>Nieuw account</h3>
    
	<h:messages errorClass="formError" infoClass="green" globalOnly="true"/>

	<h:form id="form-new-account">
    	<fieldset>
		<table>
		<tr>
			<td>Naam:</td>
			<td><h:inputText id="Naam" value="#{NewAccountBean.newName}" required = "true"/></td>
            <td><h:message styleClass="formError" for="Naam" /></td>
        </tr>
		
		 <tr>
			<td>Pincode:</td>
			<td><h:inputText id="Pincode" value="#{NewAccountBean.newPincode}" required = "true"/></td>
            <td><h:message styleClass="formError" for="Pincode" /></td>
         </tr>
		
		<tr>
		  	<td>Kredietlimiet:</td>
			<td><h:inputText id="Kredietlimiet" value="#{NewAccountBean.newLimit}" required = "true" /></td>
            <td><h:message styleClass="formError" for="Kredietlimiet" /></td>
		</tr>
		<tr>
       		<td colspan="3"><h:outputLabel styleClass="formError" value="* Alle velden zijn verplicht"></h:outputLabel></td>
		</tr>
        <tr>
    	    <td></td>
	        <td><h:commandButton action="#{NewAccountBean.create}" value="Maak aan" /><h:commandButton value="Reset" type="reset"/></td>
	        <td></td>
        </tr>
        </table>
        </fieldset>
	</h:form>
</f:subview>