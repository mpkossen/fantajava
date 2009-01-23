<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<div id="menu">
<h:form>
<h:commandLink actionListener="#{TransactionB.setViewStorten}" value="Storten" styleClass="menu_item"/>
<h:commandLink actionListener="#{TransactionB.setViewOpnemen}" value="Opnemen" styleClass="menu_item" />
<h:commandLink actionListener="#{TransactionB.setViewOverboeken}" value="Overboeken" styleClass="menu_item" />
</h:form>
</div>
