   <%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%> 
   <%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>   
   
   <h:panelGroup id="panel-opnemen" layout="block" rendered="#{OpnemenB.renderOpnemen}"> 
   <h1>Opnemen</h1> <h:form id="form-opnemen"> 
   <table class="tabel" cellpadding="0" cellspacing="0" width="410"> 
   <tr> <td>Bedrag(&euro;)</td> <td> 
   <h:inputText  id="input-bedrag"  value="#{OpnemenB.amount}"  required="true"  valueChangeListener="#{OpnemenB.changeAmount}"/> </td> </tr> 
   <tr> <td>Van rekening</td> 
   <td><h:outputText value="#{OpnemenB.rekeningNummer}" /> </td> </tr> 
   </table> <h:commandButton id="cmd-opnemen" type="submit" styleClass="knop" value="versturen >" actionListener="#{OpnemenB.doOpnemen}" /> 
   </h:form> 
   </h:panelGroup>  