<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<c:set var="title" value="admin.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
    <div class="container">
        <div class="panel-wrapper">
            <div class="panel">
                <a class="item panel-item" href="/controller?command=viewSending"><fmt:message key="admin.panel.sending"/></a>
                <a class="item panel-item" href="/controller?command=getSendReportPage"><fmt:message key="admin.panel.reports"/></a>
                <a class="item panel-item" href="/product"><fmt:message key="admin.panel.product"/></a>
                <a class="item panel-item" href="/controller?command=viewCategories"><fmt:message key="admin.panel.categories"/></a>
                <a class="item panel-item" href="/controller?command=viewCompanies"><fmt:message key="admin.panel.companies"/></a>
                <a class="item panel-item" href="/controller?command=viewCharacteristics"><fmt:message key="admin.panel.characteristics"/></a>
            </div>
        </div>
    </div>
    <style>
        <%@include file='../../../style/header.css' %>
        <%@include file='../../../style/admin_panel.css' %>
    </style>
</body>
</html>
