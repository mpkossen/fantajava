<%-- 
    Document   : TransactieOverzicht in HTML5!
    Created on : 19-Jan-2009, 13:38:48
    Author     : mistermartin75
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:include page="includes/header.html" />
<div class="topdiv"><br />
<jsp:include page="includes/sidebar.jsp" >
    <jsp:param name="type" value="manager" />
</jsp:include>
            <div id="content">
                <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td>Soort transactie</td>
                        <td>Bedrag</td>
                    </tr>
                    /*
                     * Hier komt een loop voor de pending transactie's
                     */
                    <tr>
                        <td>${TransactieOverzicht.soort}</td>
                        <td>${TransactieOverzicht.bedrag}</td>
                    </tr>
                </table>
            </div>
</div>
<jsp:include page="includes/footer.html" />
