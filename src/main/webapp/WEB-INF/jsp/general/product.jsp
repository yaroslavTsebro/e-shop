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
                        <div class="text">${requestScope.product.nameUa}</div>
                    </div>
            </c:forEach>
            <a class="prev" onclick="plusSlides(-1)">❮</a>
            <a class="next" onclick="plusSlides(1)">❯</a>
        </div>
        <div class="product-data">
            <div class="row">
                <div class="column">
                    <div>Назва: </div>
                </div>
                <div class="column">
                    <div>${requestScope.product.nameUa}</div>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div>Ціна: </div>
                </div>
                <div class="column">
                    <div>${requestScope.product.price} грн.</div>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div>Кількість: </div>
                </div>
                <div class="column">
                    <div>${requestScope.product.count} шт.</div>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div>Вага: </div>
                </div>
                <div class="column">
                    <div>${requestScope.product.weight} гр.</div>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div>Гарантія: </div>
                </div>
                <div class="column">
                    <div>${requestScope.product.warranty} місяці</div>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div>Категорія: </div>
                </div>
                <div class="column">
                    <div>${requestScope.product.category.nameUa}</div>
                </div>
            </div>
            <div class="row">
                <div class="column">
                    <div>Компанія: </div>
                </div>
                <div class="column">
                    <div>${requestScope.product.company.nameUa}</div>
                </div>
            </div>
            <c:forEach items="${requestScope.product.productCharacteristics}" var="characteristic">
                <div class="row">
                    <div class="column">
                        <div>${characteristic.compatibility.characteristic.nameUa}: </div>
                    </div>
                    <div class="column">
                        <div>${characteristic.value}</div>
                    </div>
                </div>
            </c:forEach>
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
