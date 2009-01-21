   
   <%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%> 
   <%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>   
   <%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="includes/header.html" />
<div class="topdiv"><br />
<jsp:include page="includes/sidebar.jsp" >
    <jsp:param name="type" value="gebruiker" />
</jsp:include>
   
   
   
   
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
   
  
   ---
   
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


---

   
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
	
	
<jsp:include page="includes/footer.html" />
	