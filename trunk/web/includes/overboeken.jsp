<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%> 
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>   
<h:panelGroup id="panel-overboeken" layout="block" rendered="#{OverboekenB.renderOverboeken}"> 
<h1>Overboeken</h1> 
<h:form id="form-overboeking"> 
<table class="tabel" cellpadding="0" cellspacing="0" width="410"> <tr> 
<td>Bedrag(&euro;)</td> <td> 
<h:inputText  id="input-bedrag"  value="#{OverboekenB.amount}"  required="true"  valueChangeListener="#{OverboekenB.changeAmount}"/> </td> </tr> <tr> <td>Van rekening</td> 
<td><h:outputText value="#{OverboekenB.rekeningNummer}" /> </td> </tr> 
<tr> <td>Naar rekening</td> 
<td> <h:inputText  id="input-naar-rekening"  value="#{OverboekenB.number}"  required="true" valueChangeListener="#{OverboekenB.changeNumber}"/>  
</td> </tr> </table> 
<h:commandButton id="cmd-overboeken" type="submit" styleClass="knop" value="versturen >" actionListener="#{OverboekenB.doOverboeking}" /> 
</h:form> 
</h:panelGroup> 