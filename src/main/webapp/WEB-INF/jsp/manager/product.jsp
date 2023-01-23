<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <c:set var="title" value="product.title"/>
    <%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
    <div class="container">
        <div class="wrapper">
            <form action="/product" method="post" name="productAddForm" enctype="multipart/form-data">
                <label for="name_ua"><fmt:message key="product.add.form.label.text.ua"/></label>
                <input maxlength="50" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="name_ua" name="name_ua" placeholder="<fmt:message key="product.add.form.placeholder.text.name.ua"/>" required>
                <label for="name_en"><fmt:message key="product.add.form.label.text.en"/></label>
                <input maxlength="50" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="name_en" name="name_en" placeholder="<fmt:message key="product.add.form.placeholder.text.name.en"/>" required>

                <label for="price"><fmt:message key="product.add.form.label.text.price"/></label>
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" min="0" id="price" name="price" placeholder="<fmt:message key="product.add.form.placeholder.text.price"/>" required>
                <label for="weight"><fmt:message key="product.add.form.label.text.weight"/></label>
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" min="0" id="weight" name="weight" placeholder="<fmt:message key="product.add.form.placeholder.text.weight"/>" required>
                <label for="count"><fmt:message key="product.add.form.label.text.count"/></label>
                <input type="number" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" min="0" id="count" name="count" placeholder="<fmt:message key="product.add.form.placeholder.text.count"/>" required>
                <label for="warranty"><fmt:message key="product.add.form.label.text.warranty"/></label>
                <input type="number" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" min="0" id="warranty" name="warranty" placeholder="<fmt:message key="product.add.form.placeholder.text.warranty"/>" required>

                <label for="title_ua"><fmt:message key="product.add.form.label.text.title.ua"/></label>
                <input maxlength="50" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="title_ua" name="title_ua" placeholder="<fmt:message key="product.add.form.placeholder.text.title.ua"/>" required>
                <label for="description_ua"><fmt:message key="product.add.form.label.text.description.ua"/></label>
                <input maxlength="500" class="textarea" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="description_ua" name="description_ua" placeholder="<fmt:message key="product.add.form.placeholder.text.description.ua"/>" required>

                <label for="title_en"><fmt:message key="product.add.form.label.text.title.en"/></label>
                <input maxlength="50" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="title_en" name="title_en" placeholder="<fmt:message key="product.add.form.placeholder.text.title.en"/>" required>
                <label for="description_en"><fmt:message key="product.add.form.label.text.description.en"/></label>
                <input maxlength="500" class="textarea" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="description_en" name="description_en" placeholder="<fmt:message key="product.add.form.placeholder.text.description.en"/>" required>

                <label><fmt:message key="product.add.form.placeholder.text.category"/></label>
                <select name="category_id">
                    <c:forEach items="${requestScope.categoryList}" var="category">
                        <option value="${category.id}" >UA: ${category.nameUa}<br> EN: ${category.nameEn}</option>
                    </c:forEach>
                </select>
                <label><fmt:message key="product.add.form.placeholder.text.company"/></label>
                <select name="company_id" >
                    <c:forEach items="${requestScope.companyList}" var="company">
                        <option value="${company.id}" >UA: ${company.nameUa}<br> EN: ${company.nameEn}</option>
                    </c:forEach>
                </select>
                <label><fmt:message key="product.add.label.photo"/></label>
                <div id="photos">
                    <br>
                    <input type="file" name="file"/>
                </div>
                <div class="wrapper">
                    <hr>
                </div>
                <label><fmt:message key="product.add.label.characteristic"/></label>
                <div id="characteristics">
                    <div id="characteristic">
                        <select name="characteristic_id" class="char_selector" >
                            <c:forEach items="${requestScope.characteristicList}" var="characteristic">
                                <option onclick="disableSelected()" value="${characteristic.id}" >UA: ${characteristic.nameUa}<br> EN: ${characteristic.nameEn}</option>
                            </c:forEach>
                            <input type="text" name="characteristic_value">
                            <div class="wrapper">
                                <hr>
                            </div>
                        </select>
                    </div>
                </div>
                <input type="submit" value="<fmt:message key="category.add.form.button"/>">
            </form>
            <button onclick="addPhotoFolder()">Add PhotoFolder</button>
            <button onclick="deletePhotoFolder()">Delete PhotoFolder</button>
            <br>
            <button onclick="addCharacteristic()">Add Characteristic</button>
            <button onclick="deleteCharacteristic()">Delete Characteristic</button>
        </div>
    </div>
<script src="../../../js/product.js"></script>
<style>
    <%@include file='../../../style/admin.css' %>
</style>
</body>
</html>
