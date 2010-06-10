<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<f:subview id="BankStatus">
    <h3>Bank status</h3>

	<h:messages errorClass="formError" infoClass="green" globalOnly="true"/>

<h:form id="form-bank-status">

		<fieldset>
		<table>
		<tr>
        	<td>Huidige status van de bank:</td>
			<td><h:outputText styleClass="bankStatus" value="#{BankAccessBean.bankStatus}"/></td>
       </tr>
		<tr>
			<td colspan="2">
		   <h:selectOneRadio value="#{BankStatusBean.status}" border="1" layout= "pageDirection"> 
		   <f:selectItem itemValue =	"open" itemLabel = 		"ABC Bank is open" />
		   <f:selectItem itemValue =	"closed" itemLabel = 	"ABC Bank is closed" />
		   </h:selectOneRadio>
			</td>
        </tr>
		<tr>
       		<td colspan="2"><h:outputLabel styleClass="formError" value="* Selecteer een van de stati en klik daarna op de 'OK' button"></h:outputLabel></td>
		</tr>
		 <tr>
		   <td colspan="2"><h:commandButton action="#{BankStatusBean.commit}" value="OK" /></td>
         </tr>
  		</table>
        </fieldset>
  		
</h:form>
</f:subview>