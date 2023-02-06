<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:set var="title" value="product.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
<div class="container">
    <div class="wrapper">
        <div class="first-pic">

        </div>
        <div class="daily-pic">
            <form action="/controller" method="post" name="sendReport">
                <input class="hidden" type="hidden" name="command" value="sendReport"/>
                <input type="date" name="daily"/>
                <input class="deleteButton" type="submit" value="Delete"/>
            </form>
        </div>
    </div>
</div>
<style>
    <%@include file='../../../style/header.css' %>
    <%@include file='../../../style/admin.css' %>
</style>
</body>
</html>
