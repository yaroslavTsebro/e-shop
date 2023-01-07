<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <c:set var="title" value="Something"/>
</head>
<body>
    <a href='<c:url value="/category/add" />'><c:url value="/category/add" /></a>
</body>
</html>
