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
    <div class=marquee-wrapper>
        <p class=marquee>
            Магазин TechnoGrad - якісно та швидко
        </p>
    </div>
    <div class="dropdownCategory">
        <button class="dropbtn">Категорії</button>
        <div class="dropdown-content">
            <button onclick="selectByParam(`category` ,-1)" class="slider-item-link" type="button">Нічого</button>
            <c:forEach items="${requestScope.categoryList}" var="category">
                    <button onclick="selectByParam(`category` ,${category.id})" class="slider-item-link" type="button">${category.nameUa}</button>
            </c:forEach>
        </div>
    </div>
    <div class="dropdownCompany">
        <button class="dropbtn">Компанії</button>
        <div class="dropdown-content">
            <button onclick="selectByParam(`company` ,-1)" class="slider-item-link" type="button">Нічого</button>
            <c:forEach items="${requestScope.companyList}" var="company">
                    <button onclick="selectByParam(`company` ,${company.id})" class="slider-item-link" type="button">${company.nameUa}</button>
            </c:forEach>
        </div>
    </div>
    <div class="dropdownSort">
        <button class="dropbtn">Сортування</button>
        <div class="dropdown-content">
            <button onclick="selectByParam(`sortBy` ,-1)" class="slider-item-link" type="button">Нічого</button>
            <button onclick="selectByParam(`sortBy` ,`priceAsc`)" class="slider-item-link" type="button">Від дешевих до дорогих</button>
            <button onclick="selectByParam(`sortBy` ,`priceDesc`)" class="slider-item-link" type="button">Від дорогих до дешевих</button>
            <button onclick="selectByParam(`sortBy` ,`nameAsc`)" class="slider-item-link" type="button">A-Я</button>
            <button onclick="selectByParam(`sortBy` ,`nameDesc`)" class="slider-item-link" type="button">Я-А</button>
            <button onclick="selectByParam(`sortBy` ,`avaliable`)" class="slider-item-link" type="button">Є у наявності</button>
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
                            <a href='<c:url value="/controller?command=viewCategories" />'><c:url value="/controller?command=viewCategories" /></a>${product.nameUa}
                        </div>
                        <div class="element-price">•  •  •  ${product.price}  •  •  •</div>
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
    <%@include file='../../../style/menu.css' %>
</style>

</body>
</html>
