<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<c:set var="title" value="menu.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%--    <%@ include file="/WEB-INF/jspf/part/header.jspf" %>--%>
    <a href='<c:url value="/controller?command=viewCategories" />'><c:url value="/controller?command=viewCategories" /></a> <br/>
    <a href='<c:url value="/product" />'><c:url value="/product" /></a>  <br/>
    <a href='<c:url value="/controller?command=viewCompanies" />'><c:url value="/controller?command=viewCompanies" /></a>  <br/>
    <a href='<c:url value="/controller?command=viewCharacteristics" />'><c:url value="/controller?command=viewCharacteristics" /></a>  <br/>
    <form action="/controller" method="get" name="getAllProducts">
        <input class="hidden" type="hidden" name="command" value="searchCategories"/>
        <input type="search" name="pattern" placeholder="Search...">
        <input type="submit" value="Search"/>
    </form>
</body>
</html>
