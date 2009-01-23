<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<div id="sidebar">


<h2>Transaction</h2>
<h:form>
<h:commandLink actionListener="#{TransactionB.setViewStorten}" value="Storten" styleClass="transaction_submenu"/>
<h:commandLink actionListener="#{TransactionB.setViewOpnemen}" value="Opnemen" styleClass="transaction_submenu" />
<h:commandLink actionListener="#{TransactionB.setViewOverboeken}" value="Overboeken" styleClass="transaction_submenu" />
</h:form>
</div>
