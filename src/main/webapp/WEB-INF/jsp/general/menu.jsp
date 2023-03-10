<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<c:set var="title" value="menu.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
<div class="container">
    <div class="wrapper-top">
        <form action="/controller" method="get" name="viewMenu">
            <input class="hidden" type="hidden" name="command" value="viewMenu"/>
            <input type="search" class="search-input" id="search-input" name="searchQuery" placeholder="<fmt:message key="manager.add.search"/>">
        </form>
        <button onclick="selectByParam(`searchQuery` ,getValueFormSearch())" class="dropbtn" type="button">Search</button>
        <a href="/controller?command=viewMenu" class="dropbtn clear"><fmt:message key="header.menu.clear"/></a>
    </div>
    <div class=marquee-wrapper>
        <p class=marquee>
            <fmt:message key="menu.marquee.text"/>
        </p>
    </div>
    <div class="utilsContainer">
        <div class="dropdownCategory">
            <button class="dropbtn"><fmt:message key="menu.category.selector"/></button>
            <div class="dropdown-content">
                <button onclick="selectByParam(`category` ,-1)" class="slider-item-link" type="button"><fmt:message key="menu.selector.nothing"/></button>
                <c:forEach items="${requestScope.categoryList}" var="category">
                    <c:if test="${sessionScope.lang == 'ua'}">
                        <button onclick="selectByParam(`category` ,${category.id})" class="slider-item-link" type="button">${category.nameUa}</button>
                    </c:if>
                    <c:if test="${sessionScope.lang == 'en' || empty sessionScope.lang}">
                        <button onclick="selectByParam(`category` ,${category.id})" class="slider-item-link" type="button">${category.nameEn}</button>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div class="dropdownCompany">
            <button class="dropbtn"><fmt:message key="menu.company.selector"/></button>
            <div class="dropdown-content">
                <button onclick="selectByParam(`company` ,-1)" class="slider-item-link" type="button"><fmt:message key="menu.selector.nothing"/></button>
                <c:forEach items="${requestScope.companyList}" var="company">
                    <c:if test="${sessionScope.lang == 'ua'}">
                        <button onclick="selectByParam(`company` ,${company.id})" class="slider-item-link" type="button">${company.nameUa}</button>
                    </c:if>
                    <c:if test="${sessionScope.lang == 'en' || empty sessionScope.lang}">
                        <button onclick="selectByParam(`company` ,${company.id})" class="slider-item-link" type="button">${company.nameEn}</button>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div class="dropdownSort">
            <button class="dropbtn"><fmt:message key="menu.sort.selector"/></button>
            <div class="dropdown-content">
                <button onclick="selectByParam(`sortBy` ,-1)" class="slider-item-link" type="button"><fmt:message key="menu.selector.nothing"/></button>
                <button onclick="selectByParam(`sortBy` ,`priceAsc`)" class="slider-item-link" type="button"><fmt:message key="menu.sort.selector.price.asc"/></button>
                <button onclick="selectByParam(`sortBy` ,`priceDesc`)" class="slider-item-link" type="button"><fmt:message key="menu.sort.selector.price.desc"/></button>
                <button onclick="selectByParam(`sortBy` ,`nameAsc`)" class="slider-item-link" type="button"><fmt:message key="menu.sort.selector.alphabet.asc"/></button>
                <button onclick="selectByParam(`sortBy` ,`nameDesc`)" class="slider-item-link" type="button"><fmt:message key="menu.sort.selector.alphabet.desc"/></button>
                <button onclick="selectByParam(`sortBy` ,`avaliable`)" class="slider-item-link" type="button"><fmt:message key="menu.sort.selector.avaliable"/></button>
            </div>
        </div>
    </div>

        <div class="menu_grid-container">
            <c:forEach items="${requestScope.productList}" var="product">
                    <div class="menu_grid-element element">
                        <div class="element-image">
                            <c:forEach items="${product.photos}" var="photo">
                                <img src="../../../static/images/${photo.name}">
                            </c:forEach>
                        </div>
                        <div class="element-text">
                            <c:if test="${sessionScope.lang == 'ua'}">
                                <a href='<c:url value="/controller?command=viewProductPage&id=${product.id}" />'><c:url value="${product.nameUa}" /></a>
                            </c:if>
                            <c:if test="${sessionScope.lang == 'en' || empty sessionScope.lang}">
                                <a href='<c:url value="/controller?command=viewProductPage&id=${product.id}" />'><c:url value="${product.nameEn}" /></a>
                            </c:if>
                        </div>
                        <div class="element-price">???  ???  ???  ${product.price}  ???  ???  ???</div>
                    </div>
            </c:forEach>
        </div>
        <c:if test="${requestScope.nOfPages > 1}">
            <div class="center">
                <div class="pagination">
                    <c:if test="${requestScope.currentPage != 0}">
                        <button onclick="selectByParam(`currentPage` ,${requestScope.currentPage - 1})" type="button">???</button>
                    </c:if>
                        <button class="active" type="button">${requestScope.currentPage + 1}</button>
                    <c:if test="${requestScope.currentPage != requestScope.nOfPages -1}">
                        <button onclick="selectByParam(`currentPage` ,${requestScope.currentPage + 1})" type="button">???</button>
                    </c:if>
                </div>
            </div>
        </c:if>
    </div>
<script>

    function getValueFormSearch(){
        return document.getElementById('search-input').value;
    }
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


    var wrapper = document.querySelector('.marquee-wrapper'),
        marquee = document.querySelector('.marquee'),
        wrapperWidth = wrapper.offsetWidth,
        marqueeWidth = marquee.scrollWidth;

    document.querySelector('.marquee-wrapper').onload = function() {
        clearInterval(interval)
    }

    function move() {
        var currentTX = getComputedStyle(marquee).transform.split(',');
        if( currentTX[4] === undefined ) {
            currentTX = -1;
        } else {
            currentTX = parseFloat(currentTX[4]) - 1;
        }

        if(-currentTX >= marqueeWidth) {
            marquee.style.transform = 'translateX(' + wrapperWidth + 'px)';

        } else {
            marquee.style.transform = 'translateX(' + currentTX + 'px)';
        }
    }

    var interval = setInterval(move, 4);
</script>
<style>
    <%@include file='../../../style/header.css' %>
    <%@include file='../../../style/menu.css' %>
</style>

</body>
</html>
