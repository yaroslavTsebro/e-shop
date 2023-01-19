<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <c:set var="title" value="product.title"/>
    <%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
    <div class="container">
        <div class="wrapper">
            <form action="/product" method="post" name="customInputForm" enctype="multipart/form-data">
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="name_ua" name="name_ua" placeholder="<fmt:message key="product.add.form.placeholder.text.name.ua"/>" required>
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="name_en" name="name_en" placeholder="<fmt:message key="product.add.form.placeholder.text.name.en"/>" required>

                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" min="0" id="price" name="price" placeholder="<fmt:message key="product.add.form.placeholder.text.price"/>" required>
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" min="0" id="weight" name="weight" placeholder="<fmt:message key="product.add.form.placeholder.text.weight"/>" required>
                <input type="number" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" min="0" id="count" name="count" placeholder="<fmt:message key="product.add.form.placeholder.text.count"/>" required>
                <input type="number" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" min="0" id="warranty" name="warranty" placeholder="<fmt:message key="product.add.form.placeholder.text.warranty"/>" required>

                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="title_ua" name="title_ua" placeholder="<fmt:message key="product.add.form.placeholder.text.title.ua"/>" required>
                <input class="textarea" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="description_ua" name="description_ua" placeholder="<fmt:message key="product.add.form.placeholder.text.description.ua"/>" required>

                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="title_en" name="title_en" placeholder="<fmt:message key="product.add.form.placeholder.text.title.en"/>" required>
                <input class="textarea" type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="description_en" name="description_en" placeholder="<fmt:message key="product.add.form.placeholder.text.description.en"/>" required>

                <label><fmt:message key="product.add.form.placeholder.text.category"/></label>
                <select name="category_id">
                    <c:forEach items="${requestScope.categoryList}" var="category">
                        <option value="${category.id}" >UA: ${category.nameUa}<br> EN: ${category.nameEn}</option>
                    </c:forEach>
                </select>
                <label><fmt:message key="product.add.form.placeholder.text.company"/></label>
                <select name="company_id">
                    <c:forEach items="${requestScope.companyList}" var="company">
                        <option value="${company.id}" >UA: ${company.nameUa}<br> EN: ${company.nameEn}</option>
                    </c:forEach>
                </select>
                <div id="photos">
                    <input type="file" name="file"/>
                </div>
                <input type="submit" value="<fmt:message key="category.add.form.button"/>">
            </form>
            <button onclick="addPhotoFolder()">Add PhotoFolder</button>
            <button onclick="addCharacteristic()">Add Characteristic</button>
        </div>
    </div>
<script>
    let count = 0;
    function addPhotoFolder(){
        if(count <= 3){
            var tag = document.createElement("input");
            tag.type = "file";
            tag.name = "file";
            var element = document.getElementById("photos");
            element.appendChild(tag);
            count++;
        }
    }
    function addCharacteristic(){
        var tag = document.createElement("input");
        tag.type = "file";
        tag.name = "file";
        var element = document.getElementById("photos");
        element.appendChild(tag);
    }
</script>
<style>
    <%@include file='../../../style/admin_style.css' %>
</style>
</body>
</html>
