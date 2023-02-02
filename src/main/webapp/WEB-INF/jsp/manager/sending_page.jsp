<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<c:set var="title" value="admin.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
    <div class="container">

        <div class="dropdownFilter">
            <button class="dropbtn"><fmt:message key="menu.sort.selector"/></button>
            <div class="dropdown-content">
                <button onclick="selectByParam(`condition` ,-1)" class="slider-item-link" type="button"><fmt:message key="admin.sending.selector.all"/></button>
                <button onclick="selectByParam(`condition` ,`CART`)" class="slider-item-link" type="button"><fmt:message key="admin.sending.selector.cart"/></button>
                <button onclick="selectByParam(`condition` ,`NEW`)" class="slider-item-link" type="button"><fmt:message key="admin.sending.selector.new"/></button>
                <button onclick="selectByParam(`condition` ,`ACCEPTED`)" class="slider-item-link" type="button"><fmt:message key="admin.sending.selector.accepted"/></button>
                <button onclick="selectByParam(`condition` ,`DENIED`)" class="slider-item-link" type="button"><fmt:message key="admin.sending.selector.denied"/></button>
                <button onclick="selectByParam(`condition` ,`TURNED_BACK`)" class="slider-item-link" type="button"><fmt:message key="admin.sending.selector.turned.back"/></button>
                <button onclick="selectByParam(`condition` ,`IN_WAY`)" class="slider-item-link" type="button"><fmt:message key="admin.sending.selector.in.way"/></button>
                <button onclick="selectByParam(`condition` ,`COMPLETED`)" class="slider-item-link" type="button"><fmt:message key="admin.sending.selector.completed"/></button>
            </div>
        </div>

        <div class="sending-container">
            <c:forEach items="${requestScope.sendingList}" var="sending">
                <div class="item-wrapper">
                    <a class="item panel" href="/controller?command=viewCurrentSending&id=${sending.id}">
                        <div class="item">
                            <div class="item-header">
                                <div class="header-item-left">
                                    <fmt:message key="admin.sending.code"/> ${sending.id}
                                </div>
                                <c:if test="${sending.condition.toString() == 'ACCEPTED'}">
                                    <div class="header-item-right green">
                                            ${sending.condition}
                                    </div>
                                </c:if>
                                <c:if test="${sending.condition.toString() == 'CART'}">
                                    <div class="header-item-right grey">
                                            ${sending.condition}
                                    </div>
                                </c:if>
                                <c:if test="${sending.condition.toString() == 'TURNED_BACK'}">
                                    <div class="header-item-right red">
                                            ${sending.condition}
                                    </div>
                                </c:if>
                                <c:if test="${sending.condition.toString() == 'IN_WAY'}">
                                    <div class="header-item-right blue">
                                            ${sending.condition}
                                    </div>
                                </c:if>
                                <c:if test="${sending.condition.toString() == 'NEW'}">
                                    <div class="header-item-right blue">
                                            ${sending.condition}
                                    </div>
                                </c:if>
                                <c:if test="${sending.condition.toString() == 'DENIED'}">
                                    <div class="header-item-right red">
                                            ${sending.condition}
                                    </div>
                                </c:if>
                                <c:if test="${sending.condition.toString() == 'COMPLETED'}">
                                    <div class="header-item-right green">
                                            ${sending.condition}
                                    </div>
                                </c:if>
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
<script>

    function selectByParam(parameter ,id){
        let path = window.location.href.split('?')[0]
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        if(urlParams.has(parameter)){
            urlParams.set(parameter, id);
        } else{
            urlParams.append(parameter, id);
        }
        if(id == -1){
            urlParams.delete(parameter)
        }
        location.href= path + "?" + urlParams;
    }
</script>
<style>
    <%@include file='../../../style/header.css' %>
<%--    <%@include file='../../../style/admin_panel.css' %>--%>
    <%@include file='../../../style/sendings.css' %>
</style>
</body>
</html>
