<%-- 
    Document   : TransactieOverzicht in HTML5!
    Created on : 19-Jan-2009, 13:38:48
    Author     : mistermartin75
--%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="includes/header.html" />
<div class="topdiv"><br />
<jsp:include page="includes/sidebar.jsp" >
    <jsp:param name="type" value="gebruiker" />
</jsp:include>
            <div id="content">
                    <h:form>
                        <h:dataTable bgcolor="#F1F1F1" border="10" cellpadding="5" cellspacing="3" dir="LTR" first="0" frame="hsides" id="dt1" rows="4"
                            rules="all" summary="This is a JSF code to create dataTable." value="#{TransactieOverzicht.transacties}" var="item" width="50%">
                            <f:facet name="header">
                                <h:outputText value="This is 'dataTable' demo"/>
                            </f:facet>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="soort"/>
                                </f:facet>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="name"/>
                                </f:facet>
                            </h:column>
                            <f:facet name="footer">
                                <h:outputText value="The End"/>
                            </f:facet>
                        </h:dataTable>
                        <br/>
                        <br/>
                    </h:form>
            </div>
</div>
<jsp:include page="includes/footer.html" />
