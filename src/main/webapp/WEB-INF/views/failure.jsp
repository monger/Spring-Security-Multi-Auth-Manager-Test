<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <head>
        <title><fmt:message key="page.failure.title"/></title>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
        <link href="/css/master.css" type="text/css" rel="stylesheet"/>
        <link href="/css/bad-things.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
        <section id="message-container">
            <p>
                <fmt:message key="page.failure.detail"/>
            </p>
            <p>
                <a href="/login"><fmt:message key="page.failure.try-again"/></a>
            </p>
        </section>
    </body>
</html>