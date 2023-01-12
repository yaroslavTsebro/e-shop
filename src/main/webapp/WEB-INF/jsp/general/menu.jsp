<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<c:set var="title" value="menu.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
    <%@ include file="/WEB-INF/jspf/part/header.jspf" %>
    <a href='<c:url value="/category" />'><c:url value="/category" /></a>
    <a href='<c:url value="/product" />'><c:url value="/product" /></a>
</body>
</html>
