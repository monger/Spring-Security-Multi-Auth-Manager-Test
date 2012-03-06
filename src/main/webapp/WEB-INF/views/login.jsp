<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <head>
        <title><fmt:message key="page.login.title"/></title>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
        <link href="/css/master.css" type="text/css" rel="stylesheet"/>
        <link href="/css/login.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
        <form id="login-container" action="<c:url value="j_spring_security_check"/>" method="post">
            <h1><fmt:message key="page.login.title"/></h1>
            <table>
                <tbody>
                    <tr>
                        <td class="label"><label for="txtUsername"><fmt:message key="label.username"/></label></td>
                        <td class="input"><input id="txtUsername" type="text" name="j_username"/></td>
                    </tr>
                    <tr>
                        <td class="label"><label for="txtPassword"><fmt:message key="label.password"/></label></td>
                        <td class="input"><input id="txtPassword" type="password" name="j_password"/></td>
                    </tr>
                    <tr>
                        <td class="submit" colspan="2">
                            <button type="submit" id="btnSubmit"><fmt:message key="label.submit"/></button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
        <script type="text/javascript">
            document.getElementById("txtUsername").focus();
        </script>
    </body>
</html>
