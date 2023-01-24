<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<c:set var="title" value="menu.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
<div class="container">
    <a href='<c:url value="/controller?command=viewCategories" />'><c:url value="/controller?command=viewCategories" /></a> <br/>
    <a href='<c:url value="/controller?command=viewMenu" />'><c:url value="/controller?command=viewMenu" /></a>  <br/>
    <a href='<c:url value="/controller?command=viewCompanies" />'><c:url value="/controller?command=viewCompanies" /></a>  <br/>
    <a href='<c:url value="/controller?command=viewCharacteristics" />'><c:url value="/controller?command=viewCharacteristics" /></a>  <br/>

        <div class="menu_grid-container">
            <c:forEach items="${requestScope.productList}" var="product">
                    <div class="menu_grid-element element">
                        <div class="element-image">
                            <c:forEach items="${product.photos}" var="photo">
                                <img src="../../../static/images/${photo.name}">
                            </c:forEach>
                        </div>
                        <div class="element-text">
                            <a href='<c:url value="/controller?command=viewCategories" />'><c:url value="/controller?command=viewCategories" /></a>${product.nameUa}
                        </div>
                        <div class="element-price">•  •  •  ${product.price}  •  •  •</div>
                    </div>
            </c:forEach>
        </div>
    </div>
<style>
    <%@include file='../../../style/menu.css' %>
</style>
</body>
</html>
