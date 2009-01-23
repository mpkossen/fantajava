<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<f:subview id="subview-choice">
	<jsp:include page="transaction_opnemen.jsp" flush="#{TransactionB.getRenderOpnemen}" />
	<jsp:include page="transaction_overboeken.jsp" flush="#{TransactionB.getRenderOverboeken}" />
	<jsp:include page="transaction_storten.jsp" flush="#{TransactionB.getRenderStorten}" />
</f:subview>