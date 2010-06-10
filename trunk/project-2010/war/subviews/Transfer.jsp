<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<f:subview id="transfer">
	<h3>Geld overboeken</h3>
    
	<h:messages errorClass="formError" infoClass="green" globalOnly="true"/>
    
	<h:form id="geldOverboeken">
		<fieldset>
		<table>
			<tr>
				<td>AccountNr naar</td>
				<td><h:inputText id="reknrNaar" value="#{TransferBean.reknrNaar}" required="true" /></td>
				<td><h:message styleClass="formError" for="reknrNaar" /></td>
			</tr>
			<tr>
				<td>Bedrag</td>
				<td><h:inputText id="bedrag" value="#{TransferBean.bedrag}" validator="#{TransferBean.bedragInputValidator}" required="true" /></td>
				<td><h:message styleClass="formError" for="bedrag" /></td>
			</tr>
			<tr>
           		<td colspan="3"><h:outputLabel styleClass="formError" value="* Alle velden zijn verplicht"></h:outputLabel></td>
			</tr>
			<tr>
           		<td></td>
				<td><h:commandButton action="#{TransferBean.overboeken}" value="Geld overboeken" /><h:commandButton value="Reset" type="reset"/></td>
           		<td></td>
			</tr>
		</table>
		</fieldset>
	</h:form>
    
    <jsp:include page="../parts/TransactionTable.jsp" />

</f:subview>