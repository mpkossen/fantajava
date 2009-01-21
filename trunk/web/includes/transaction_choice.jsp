<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<f:subview id="subview-choice">
	<jsp:include page="transaction_opnemen.jsp" flush="true" />
	<jsp:include page="transaction_overboeken.jsp" flush="false" />
	<jsp:include page="transaction_storten.jsp" flush="false" />
</f:subview>