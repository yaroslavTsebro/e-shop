<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<c:set var="title" value="admin.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
    <div class="container">
        <div class="sending-container">
            <c:forEach items="${requestScope.sendingList}" var="sending">
                <div class="item-wrapper">
                    <a class="item panel" href="/controller?command=viewCurrentSending&id=${sending.id}">
                        <div class="item">
                            <div class="item-header">
                                <div class="header-item-left">
                                    <fmt:message key="admin.sending.code"/> ${sending.id}
                                </div>
                                <div class="header-item-right red">
                                        ${sending.condition}
                                </div>
                            </div>
                            <div class="item-body">
                                <div class="body-item-address">
                                    <fmt:message key="admin.sending.address"/> ${sending.address}
                                </div>
                            </div>
                            <div class="item-footer">
                                <fmt:message key="admin.sending.start.date"/> ${sending.startDate}
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
<style>
    <%@include file='../../../style/header.css' %>
<%--    <%@include file='../../../style/admin_panel.css' %>--%>
    <%@include file='../../../style/sendings.css' %>
</style>
</body>
</html>
