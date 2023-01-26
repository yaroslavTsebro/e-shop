<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<c:set var="title" value="product.page.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
    <div class="container">
        <div class="slideshow-container">
            <c:forEach items="${requestScope.product.photos}" var="photo" varStatus="loop">
                    <div class=" mySlides fade">
                        <div class="numbertext">${loop.count} / ${requestScope.product.photos.size()}</div>
                        <img src="../../../static/images/${photo.name}" style="width:100%">
                        <c:if test="${sessionScope.lang == 'ua'}">
                            <div class="text">${requestScope.product.nameUa}</div>
                        </c:if>
                        <c:if test="${sessionScope.lang == 'en' || empty sessionScope.lang}">
                            <div class="text">${requestScope.product.nameEn}</div>
                        </c:if>

                    </div>
            </c:forEach>
            <a class="prev" onclick="plusSlides(-1)">❮</a>
            <a class="next" onclick="plusSlides(1)">❯</a>
        </div>
        <div class="product-data">
            <div class="row">
                <div class="column">
                    <div><fmt:message key="product.name"/></div>
                </div>
                <div class="column">
                    <c:if test="${sessionScope.lang == 'ua'}">
                        <div>${requestScope.product.nameUa}</div>
                    </c:if>
                    <c:if test="${sessionScope.lang == 'en' || empty sessionScope.lang}">
                        <div>${requestScope.product.nameEn}</div>
                    </c:if>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div><fmt:message key="product.price"/></div>
                </div>
                <div class="column">
                    <div>${requestScope.product.price} <fmt:message key="product.price.measure"/></div>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div><fmt:message key="product.count"/></div>
                </div>
                <div class="column">
                    <div>${requestScope.product.count} <fmt:message key="product.count.measure"/></div>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div><fmt:message key="product.weight"/></div>
                </div>
                <div class="column">
                    <div>${requestScope.product.weight} <fmt:message key="product.weight.measure"/></div>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div><fmt:message key="product.warranty"/></div>
                </div>
                <div class="column">
                    <div>${requestScope.product.warranty} <fmt:message key="product.warranty.measure"/></div>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div><fmt:message key="product.category"/></div>
                </div>
                <div class="column">
                    <c:if test="${sessionScope.lang == 'ua'}">
                        <a href='<c:url value="/controller?command=viewMenu&category=${requestScope.product.category.id}" />'>
                            <c:url value="${requestScope.product.category.nameUa}" /></a>
                    </c:if>
                    <c:if test="${sessionScope.lang == 'en' || empty sessionScope.lang}">
                        <a href='<c:url value="/controller?command=viewMenu&category=${requestScope.product.category.id}" />'>
                            <c:url value="${requestScope.product.category.nameEn}" /></a>
                    </c:if>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div><fmt:message key="product.company"/></div>
                </div>
                <div class="column">
                    <c:if test="${sessionScope.lang == 'ua'}">
                        <a href='<c:url value="/controller?command=viewMenu&company=${requestScope.product.company.id}" />'>
                            <c:url value="${requestScope.product.company.nameUa}" /></a>
                    </c:if>
                    <c:if test="${sessionScope.lang == 'en' || empty sessionScope.lang}">
                        <a href='<c:url value="/controller?command=viewMenu&company=${requestScope.product.company.id}" />'>
                            <c:url value="${requestScope.product.company.nameEn}" /></a>
                    </c:if>
                </div>
            </div>
            <div>
                <fmt:message key="product.characteristic"/>
            </div>
            <c:forEach items="${requestScope.product.productCharacteristics}" var="characteristic">
                <div class="row">
                    <div class="column">
                        <c:if test="${sessionScope.lang == 'ua'}">
                            <div>${characteristic.compatibility.characteristic.nameUa}: </div>
                        </c:if>
                        <c:if test="${sessionScope.lang == 'en' || empty sessionScope.lang}">
                            <div>${characteristic.compatibility.characteristic.nameEn}: </div>
                        </c:if>
                    </div>
                    <div class="column">
                        <div>${characteristic.value}</div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="product-data">
            <c:if test="${sessionScope.lang == 'ua'}">
                <div class="title">
                        ${requestScope.product.titleUa}
                </div>
                <div class="description">
                        ${requestScope.product.descriptionUa}
                </div>
            </c:if>
            <c:if test="${sessionScope.lang == 'en' || empty sessionScope.lang}">
                <div class="title">
                        ${requestScope.product.titleEn}
                </div>
                <div class="description">
                        ${requestScope.product.descriptionEn}
                </div>
            </c:if>
        </div>
    </div>
    <style>
        <%@include file='../../../style/product_page.css' %>
    </style>
<script>
    let slideIndex = 1;
    showSlides(slideIndex);

    function plusSlides(n) {
        showSlides(slideIndex += n);
    }

    function showSlides(n) {
        let i;
        let slides = document.getElementsByClassName("mySlides");
        if (n > slides.length) {slideIndex = 1}
        if (n < 1) {slideIndex = slides.length}
        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        slides[slideIndex-1].style.display = "block";

    }
</script>
</body>
</html>
