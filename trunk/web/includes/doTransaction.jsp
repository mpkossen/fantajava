<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

   <%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%> 
   <%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>   
   <%@page contentType="text/html" pageEncoding="UTF-8"%>
	<jsp:include page="includes/header.html" />
	

 <HTML>
  <HEAD>
   <TITLE>Transactie</TITLE>
   <LINK REL=stylesheet TYPE="text/css" HREF="resources/style.css">
  </HEAD>
  <BODY>
   <CENTER>

   <jsp:include page="includes/sidebar.jsp" >
    <jsp:param name="type" value="gebruiker" />
</jsp:include>

     <div id="content">
     <jsp:include page="/includes/transaction_choice.jsp"    flush="true"/>
     </div>



   </CENTER>
  </BODY> 
 </HTML>  

<jsp:include page="includes/footer.html" />


</f:view>
	
	
	