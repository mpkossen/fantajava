   <%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%> 
   <%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>   
   
   <h:panelGroup id="panel-opnemen" layout="block" rendered="#{TransactionB.renderOpnemen}"> 
   <h1>Opnemen</h1> <h:form id="form-opnemen"> 
   <table class="tabel" cellpadding="0" cellspacing="0" width="410"> 
   <tr> <td>Bedrag(&euro;)</td> <td> 
   <h:inputText  id="input-bedrag"  value="#{TransactionB.amount}"  required="true"  valueChangeListener="#{TransactionB.setAmount}"/> </td> </tr> 
   <tr> <td>Van rekening</td> 
   <td><h:outputText value="#{TransactionB.rekeningNummer}" /> </td> </tr> 
   </table> <h:commandButton id="cmd-opnemen" type="submit" styleClass="knop" value="versturen >" actionListener="#{TransactionB.doTransaction}" /> 
   </h:form> 
   </h:panelGroup>  
   
   
   ---
   
   <h:panelGroup id="panel-storten" layout="block" rendered="#{TransactionB.renderStorten}"> 
<h1>Storten</h1> 
<h:form id="form-storten"> 
<table class="tabel" cellpadding="0" cellspacing="0" width="410"> 
<tr> <td>Bedrag(&euro;)</td> 
<td> <h:inputText  id="input-bedrag"  value="#{transactionB.amount}"  required="true"  valueChangeListener="#{TransactionB.changeAmount}"/> </td> 
</tr> <tr> 
<td>Op rekening</td> 
<td><h:outputText value="#{TransactionB.rekeningNummer}" /> </td> 
</tr> </table> 
<h:commandButton id="cmd-opnemen" type="submit" styleClass="knop" value="versturen >" actionListener="#{TransactionB.doStorten}" /> 
</h:form> 
</h:panelGroup> 


---

   
   <h:panelGroup id="panel-overboeken" layout="block" rendered="#{TransactionB.renderOverboeken}"> 
<h1>Overboeken</h1> 
<h:form id="form-overboeking"> 
<table class="tabel" cellpadding="0" cellspacing="0" width="410"> <tr> 
<td>Bedrag(&euro;)</td> <td> 
<h:inputText  id="input-bedrag"  value="#{TransactionB.amount}"  required="true"  valueChangeListener="#{TransactionB.changeAmount}"/> </td> </tr> <tr> <td>Van rekening</td> 
<td><h:outputText value="#{TransactionB.rekeningNummer}" /> </td> </tr> 
<tr> <td>Naar rekening</td> 
<td> <h:inputText  id="input-naar-rekening"  value="#{TransactionB.number}"  required="true" valueChangeListener="#{TransactionB.changeNumber}"/>  
</td> </tr> </table> 
<h:commandButton id="cmd-overboeken" type="submit" styleClass="knop" value="versturen >" actionListener="#{TransactionB.doOverboeking}" /> 
</h:form> 
</h:panelGroup> 