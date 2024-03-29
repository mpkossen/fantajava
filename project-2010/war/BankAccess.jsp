<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:view>
	<jsp:include page="parts/Header.jsp" />
	<h2>Login</h2>

	<h:messages errorClass="formError" infoClass="green" globalOnly="true"/>

	<h:form id="loginForm">
		<fieldset>
        
		<h:panelGrid columns="3">
			<h:outputText value="Status" />
			<h:outputText styleClass="bankStatus" value="#{BankAccessBean.bankStatus}" />
			<h:outputText value="" />

			<h:outputText value="Account" />
			<h:inputText id="username" value="#{BankAccessBean.username}" required="true"/>
			<h:message styleClass="formError" for="username" />

			<h:outputText  value="Wachtwoord" />
			<h:inputSecret id="password" value="#{BankAccessBean.password}" required="true" />
			<h:message styleClass="formError" for="password" />

			<h:outputText value="" />
			<h:panelGroup>
				<h:commandButton value="Login" action="#{BankAccessBean.login}"/>
	            <h:commandButton value="Reset" type="reset"/>
			</h:panelGroup>
		</h:panelGrid>
		</fieldset>
	</h:form>
	<jsp:include page="parts/Footer.jsp" />
</f:view>