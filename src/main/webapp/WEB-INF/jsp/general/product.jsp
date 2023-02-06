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
        <c:if test="${not empty sessionScope.user}">
                <c:if test="${sessionScope.user.post.toString() == 'CUSTOMER'}">
                    <div class="product-data white">
                    <form action="/controller" method="post" name="addToCartForm">
                        <input type="hidden" name="command" value="addToCart"/>
                        <input type="hidden" id="product_id" name="product_id" value="${requestScope.product.id}">
                        <input type="hidden" id="product_count" name="product_count" value="${requestScope.product.count}">
                        <input type="hidden" id="product_price" name="product_price" value="${requestScope.product.price}">
                        <label for="add_to_cart_count"><fmt:message key="cart.add.count.label"/></label>
                        <input type="number" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                               title="${field.required}" min="0" max="${requestScope.product.count}" id="add_to_cart_count" name="add_to_cart_count"
                               placeholder="<fmt:message key="cart.add.count.placeholder"/>" required>
                        <input type="submit" class="white-button" value="<fmt:message key="cart.add.button"/>">
                    </form>
                    </div>
                </c:if>
            <c:if test="${sessionScope.user.post.toString() == 'MANAGER'}">
                <button class=" product-data white updateButton" onclick="openModal()"><fmt:message key="entity.update"/></button>
            </c:if>
        </c:if>
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
    <c:if test="${sessionScope.user.post.toString() == 'MANAGER'}">
        <div id="myModal" class="modal">
            <form action="/controller" method="post" name="updateProductForm">
                <input type="hidden" name="command" value="updateProduct">
                <input type="hidden" name="product_update_id" value="${requestScope.product.id}">
                <label for="name_ua"><fmt:message key="product.add.form.label.text.ua"/></label>
                <input maxlength="50" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" value="${requestScope.product.nameUa}"
                       value="" id="name_ua" name="new_name_ua" placeholder="<fmt:message key="product.add.form.placeholder.text.name.ua"/>" required>
                <label for="name_en"><fmt:message key="product.add.form.label.text.en"/></label>
                <input maxlength="50" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}"  value="${requestScope.product.nameEn}"
                       id="name_en" name="new_name_en" placeholder="<fmt:message key="product.add.form.placeholder.text.name.en"/>" required>

                <label for="price"><fmt:message key="product.add.form.label.text.price"/></label>
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')" value="${requestScope.product.price}"
                       title="${field.required}" min="0" id="price" name="new_price" placeholder="<fmt:message key="product.add.form.placeholder.text.price"/>" required>
                <label for="weight"><fmt:message key="product.add.form.label.text.weight"/></label>
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')" value="${requestScope.product.weight}"
                       title="${field.required}" min="0" id="weight" name="new_weight" placeholder="<fmt:message key="product.add.form.placeholder.text.weight"/>" required>
                <label for="count"><fmt:message key="product.add.form.label.text.count"/></label>
                <input type="number" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')" value="${requestScope.product.count}"
                       title="${field.required}" min="0" id="count" name="new_count" placeholder="<fmt:message key="product.add.form.placeholder.text.count"/>" required>
                <label for="warranty"><fmt:message key="product.add.form.label.text.warranty"/></label>
                <input type="number" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')" value="${requestScope.product.warranty}"
                       title="${field.required}" min="0" id="warranty" name="new_warranty" placeholder="<fmt:message key="product.add.form.placeholder.text.warranty"/>" required>

                <label for="title_ua"><fmt:message key="product.add.form.label.text.title.ua"/></label>
                <input maxlength="50" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')" value="${requestScope.product.titleUa}"
                       title="${field.required}" id="title_ua" name="new_title_ua" placeholder="<fmt:message key="product.add.form.placeholder.text.title.ua"/>" required>
                <label for="description_ua"><fmt:message key="product.add.form.label.text.description.ua"/></label>
                <input maxlength="500" class="textarea" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')" value="${requestScope.product.descriptionUa}"
                       title="${field.required}" id="description_ua" name="new_description_ua" placeholder="<fmt:message key="product.add.form.placeholder.text.description.ua"/>" required>

                <label for="title_en"><fmt:message key="product.add.form.label.text.title.en"/></label>
                <input maxlength="50" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')" value="${requestScope.product.titleEn}"
                       title="${field.required}" id="title_en" name="new_title_en" placeholder="<fmt:message key="product.add.form.placeholder.text.title.en"/>" required>
                <label for="description_en"><fmt:message key="product.add.form.label.text.description.en"/></label>
                <input maxlength="500" class="textarea" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"  value="${requestScope.product.descriptionEn}"
                       title="${field.required}" id="description_en" name="new_description_en" placeholder="<fmt:message key="product.add.form.placeholder.text.description.en"/>" required>

                <label><fmt:message key="product.add.form.placeholder.text.category"/></label>
                <select name="new_category_id">
                    <c:forEach items="${requestScope.categoryList}" var="category">
                        <option value="${category.id}" >UA: ${category.nameUa}<br> EN: ${category.nameEn}</option>
                    </c:forEach>
                </select>
                <label><fmt:message key="product.add.form.placeholder.text.company"/></label>
                <select name="new_company_id" >
                    <c:forEach items="${requestScope.companyList}" var="company">
                        <option value="${company.id}" >UA: ${company.nameUa}<br> EN: ${company.nameEn}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="<fmt:message key="entity.update"/>">
            </form>
        </div>
    </c:if>
    </div>
</div>
    <style>
        <%@include file='../../../style/header.css' %>
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

        let modal = document.getElementById("myModal");
        let btn = document.getElementsByClassName('myBtn');
        let span = document.getElementsByClassName("close")[0];
        function openModal(){
        modal.style.display = "block";
    }
        span.onclick = function() {
        modal.style.display = "none";
    }
        window.onclick = function(event) {
        if (event.target === modal) {
        modal.style.display = "none";
    }
    }
        function chooseCountry(){
        document.getElementById("country_id").value = document.getElementById("chooseCountrySelect" + id).value
    }
    </script>
</body>
</html>
