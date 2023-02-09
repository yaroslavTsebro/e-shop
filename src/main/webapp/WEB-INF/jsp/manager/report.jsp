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
            <div class="daily-pic">
                <form action="/controller" method="post" name="sendReport">
                    <input class="hidden" type="hidden" name="command" value="sendReport"/>
                    <input type="date" name="date1"/>
                    <input type="date"  name="date2"/>
                    <input class="deleteButton" type="submit" value="<fmt:message key="admin.get.report"/>"/>
                </form>
            </div>
        </div>
        <div class="daily-pic">
            <form action="/controller" method="post" name="sendReport">
                <input class="hidden" type="hidden" name="command" value="sendReport"/>
                <input id="date-hidden" type="hidden" name="date1"/>
                <input id="date-daily" type="date" onchange="document.getElementById('date-hidden').value = this.value" name="date2"/>
                <input class="deleteButton" type="submit" value="<fmt:message key="admin.get.daily.report"/>"/>
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
