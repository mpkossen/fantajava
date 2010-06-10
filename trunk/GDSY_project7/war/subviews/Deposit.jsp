<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<f:subview id="Deposit">
	<h3>Geld storten</h3>
    
	<h:messages errorClass="formError" infoClass="green" globalOnly="true"/>

	<h:form id="geldStorten">
		<fieldset>
		<table>
			<tr>
				<td>Bedrag</td>
				<td><h:inputText id="bedrag" value="#{DepositBean.bedrag}" validator="#{DepositBean.bedragInputValidator}" required="true" /></td>
                <td><h:message for="bedrag" /></td>
			</tr>
			<tr>
           		<td colspan="3"><h:outputLabel styleClass="formError" value="* Alle velden zijn verplicht"></h:outputLabel></td>
			</tr>
			<tr>
	            <td></td>
				<td><h:commandButton action="#{DepositBean.geldStorten}" value="Geld storten" /><h:commandButton value="Reset" type="reset"/></td>
	            <td></td>
			</tr>
		</table>
		</fieldset>
	</h:form>
    
		<jsp:include page="../parts/TransactionTable.jsp" />
 </f:subview>