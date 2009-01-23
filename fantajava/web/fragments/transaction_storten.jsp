   <h:panelGroup id="panel-storten" layout="block"> 
	<h1>Storten</h1> 
	<h:form id="form-storten"> 
	<table class="tabel" cellpadding="0" cellspacing="0" width="410"> 
	<tr> <td>Bedrag(&euro;)</td> 
	<td> <h:inputText  id="input-bedrag"  value="#{TransactionB.amount}"  required="true"  valueChangeListener="#{TransactionB.changeAmount}"/> </td> 
	</tr> </table> 
	<h:commandButton id="cmd-opnemen" type="submit" styleClass="knop" value="Stort!" actionListener="#{TransactionB.doStorten}" /> 
	</h:form> 
	</h:panelGroup> 
