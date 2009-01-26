<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<div id="sidebar">



<h:form>
<h3>Overzicht</h3>
<h:commandButton actionListener="#{TransactionB.setViewOverzicht}" value="Overzicht"/>

<h3>Doe Transactie</h3>
<h:commandButton actionListener="#{TransactionB.setViewStorten}" value="Storten"/>
<h:commandButton actionListener="#{TransactionB.setViewOpnemen}" value="Opnemen"/>
<h:commandButton actionListener="#{TransactionB.setViewOverboeken}" value="Overboeken" />
</h:form>
</div>
