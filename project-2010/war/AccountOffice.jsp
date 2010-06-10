<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:view>
	<jsp:include page="parts/Header.jsp" />
	<h2>AccountOffice</h2>
    
	<h:outputText styleClass="accountOfficeSaldo" value="Uw huidig saldo: #{AccountOfficeBean.balance}" /> 
    
	<div id="Menu">
		<h:form id="nav">
			<h:commandLink actionListener="#{AccountOfficeBean.setSubview}" value="Geld opnemen">
				<f:attribute name="to" value="Withdraw" />
			</h:commandLink> | 
			<h:commandLink actionListener="#{AccountOfficeBean.setSubview}" value="Geld overboeken">
				<f:attribute name="to" value="Transfer" />
			</h:commandLink> | 
			<h:commandLink actionListener="#{AccountOfficeBean.setSubview}" value="Geld storten">
				<f:attribute name="to" value="Deposit" />
			</h:commandLink>
		</h:form>
	<!-- end#Menu --></div>
	<jsp:include page="#{AccountOfficeBean.subview}" />
	<jsp:include page="parts/Footer.jsp" />
</f:view>