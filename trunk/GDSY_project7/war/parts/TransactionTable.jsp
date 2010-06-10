<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

	<h:panelGroup rendered="#{AccountOfficeBean.hasPendingTransactions}">
		<h4>Openstaande transacties</h4>
		<h:dataTable value="#{AccountOfficeBean.pendingTransactions}" var="t" cellpadding="1" cellspacing="1" border="1">
			<h:column>
				<f:facet name="header"><h:outputText value="Van" /></f:facet>
				<h:outputText value="#{t.from}" />
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Naar" /></f:facet>
				<h:outputText value="#{t.to}" />
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Bedrag" /></f:facet>
				<h:outputText value="#{t.amount}" />
			</h:column>
			<h:column>
				<f:facet name="header"><h:outputText value="Datum opdracht" /></f:facet>
				<h:outputText value="#{t.dateCreated}" />
			</h:column>
		</h:dataTable>
        
		<h:form id="sync">
			<h:commandButton action="#{AccountOfficeBean.sync}" value="Verwerk transacties" />
		</h:form>
	</h:panelGroup>