<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<div id="sidebar">



<h:form>
<h2>Overzicht</h2>
<h:commandLink actionListener="#{TransactionB.setViewOverzicht}" value="Overzicht" styleClass="transaction_submenu"/>

<h2>Doe Transactie</h2>
<h:commandLink actionListener="#{TransactionB.setViewStorten}" value="Storten" styleClass="transaction_submenu"/>
<h:commandLink actionListener="#{TransactionB.setViewOpnemen}" value="Opnemen" styleClass="transaction_submenu" />
<h:commandLink actionListener="#{TransactionB.setViewOverboeken}" value="Overboeken" styleClass="transaction_submenu" />
</h:form>
</div>
