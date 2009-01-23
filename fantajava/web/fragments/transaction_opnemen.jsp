<h:panelGroup id="panel-opnemen" layout="block"> 
   <h1>Opnemen</h1> 
   <h:form id="form-opnemen"> 
   <table class="tabel" cellpadding="0" cellspacing="0" width="410"> 
   <tr> <td>Bedrag(&euro;)</td> 
   <td> 
   <h:inputText  id="input-bedrag"  value="#{TransactionB.amount}"  required="true"  valueChangeListener="#{TransactionB.changeAmount}"/> </td> </tr> 
   </table> <h:commandButton id="cmd-opnemen" type="submit" styleClass="knop" value="Neem Op!" actionListener="#{TransactionB.doTransaction}" /> 
   </h:form> 
   </h:panelGroup>  
  