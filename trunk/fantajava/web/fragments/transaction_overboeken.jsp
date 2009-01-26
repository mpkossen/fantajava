<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:subview id="transaction_overboeken">
   <h:panelGroup id="panel-overboeken" layout="block"> 
	<h1>Overboeken</h1> 
	<h:form id="form-overboeking"> 
	<table class="tabel" cellpadding="0" cellspacing="0" width="410"> <tr> 
	<td>Bedrag(&euro;)</td> <td> 
	<h:inputText  id="input-bedrag"  value="#{TransactionB.amount}"  required="true"  valueChangeListener="#{TransactionB.changeAmount}"/> </td> </tr>
	<tr> <td>Naar rekening</td> 
	<td> <h:inputText  id="input-naar-rekening"  value="#{TransactionB.tegenrekening}"  required="true" valueChangeListener="#{TransactionB.changeTegenrekening}"/>  
	</td> </tr> </table> 
	<h:commandButton id="cmd-overboeken" type="submit" styleClass="knop" value="Boek Over!" actionListener="#{TransactionB.doOverboeking}" /> 
	</h:form> 
	</h:panelGroup>
</f:subview>