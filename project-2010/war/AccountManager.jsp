<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:view>
	<jsp:include page="parts/Header.jsp" />
	<h2>AccountManager</h2>
	<div id="Menu">
		<h:form id="nav">
			<h:commandLink actionListener="#{AccountManagerBean.setSubview}" value="Bekijk account">
				<f:attribute name="to" value="CheckAccount" />
			</h:commandLink> | 
			<h:commandLink actionListener="#{AccountManagerBean.setSubview}" value="Nieuwe account">
				<f:attribute name="to" value="NewAccount" />
			</h:commandLink> |
			<h:commandLink actionListener="#{AccountManagerBean.setSubview}" value="Bank status">
				<f:attribute name="to" value="BankStatus" />
			</h:commandLink>
		</h:form>
	<!-- end#Menu --></div>
	<jsp:include page="#{AccountManagerBean.subview}" />
	<jsp:include page="parts/Footer.jsp" />
</f:view>