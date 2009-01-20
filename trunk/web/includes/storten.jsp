<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%> 
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>   
<h:panelGroup id="panel-storten" layout="block" rendered="#{StortenB.renderStorten}"> 
<h1>Storten</h1> 
<h:form id="form-storten"> 
<table class="tabel" cellpadding="0" cellspacing="0" width="410"> 
<tr> <td>Bedrag(&euro;)</td> 
<td> <h:inputText  id="input-bedrag"  value="#{StortenB.amount}"  required="true"  valueChangeListener="#{StortenB.changeAmount}"/> </td> 
</tr> <tr> 
<td>Op rekening</td> 
<td><h:outputText value="#{StortenB.rekeningNummer}" /> </td> 
</tr> </table> 
<h:commandButton id="cmd-opnemen" type="submit" styleClass="knop" value="versturen >" actionListener="#{StortenB.doStorten}" /> 
</h:form> 
</h:panelGroup> 