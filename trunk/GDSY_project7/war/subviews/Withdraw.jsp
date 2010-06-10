<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<f:subview id="Withdraw">
	<h3>Geld opnemen</h3>

	<h:messages errorClass="formError" infoClass="green" globalOnly="true"/>

	<h:form id="geldOpnemen">
		<fieldset>
		<table>
			<tr>
				<td>Bedrag</td>
				<td><h:inputText id="bedrag" value="#{WithdrawBean.bedrag}"
					validator="#{WithdrawBean.bedragInputValidator}" required="true" /></td>
				<td><h:message styleClass="formError" for="bedrag" /></td>
			</tr>
			<tr>
           		<td colspan="3"><h:outputLabel styleClass="formError" value="* Alle velden zijn verplicht"></h:outputLabel></td>
			</tr>
			<tr>
	            <td></td>
				<td><h:commandButton action="#{WithdrawBean.geldOpnemen}" value="Geld opnemen" /><h:commandButton value="Reset" type="reset"/></td>
	            <td></td>
			</tr>
		</table>
		</fieldset>
	</h:form>
	<jsp:include page="../parts/TransactionTable.jsp" />
</f:subview>