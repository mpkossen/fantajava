<%-- 
    Document   : TransactieOverzicht in HTML5!
    Created on : 19-Jan-2009, 13:38:48
    Author     : mistermartin75
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transactieoverzicht</title>
    </head>
    <body>
        <h1>Hello World! This is your Transactieoverzicht, no idea what that means, really, but it should display some really fancy numbers. The fact that you're seeing this means it isn't working (yet). Please call you system administrator, Arno Becker, to fix the problem.</h1>
        <div id="content_container">
            <div id="sidebar">
                sidebar
            </div>

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
    </body>
</html>
