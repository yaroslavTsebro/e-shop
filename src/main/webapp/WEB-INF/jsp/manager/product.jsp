<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <c:set var="title" value="product.title"/>
    <%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
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
                                <option value="${characteristic.id}" >UA: ${characteristic.nameUa}<br> EN: ${characteristic.nameEn}</option>
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
            <button onclick="addPhotoFolder()"><fmt:message key="manager.product.add.photo.folder"/></button>
            <button onclick="deletePhotoFolder()"><fmt:message key="manager.product.delete.photo.folder"/></button>
            <br>
            <button onclick="addCharacteristic()"><fmt:message key="manager.product.add.characteristic.folder"/></button>
            <button onclick="deleteCharacteristic()"><fmt:message key="manager.product.delete.characteristic.folder"/></button>
        </div>
    </div>
<script>
    function addPhotoFolder(){
        let root = document.getElementById("photos")
        var aTag = document.createElement("br");
        var file = document.createElement("input");
        file.name = 'file';
        file.type = 'file';
        root.appendChild(aTag);
        root.appendChild(file);
    }

    function deletePhotoFolder(){
        let root = document.getElementById("photos");
        root.removeChild(root.lastChild);
        root.removeChild(root.lastChild);
    }

    function addCharacteristic(){
        let node = document.getElementById("characteristic");
        let newElem = node.cloneNode(true);
        document.getElementById("characteristics").appendChild(newElem);
    }
    function deleteCharacteristic(){
        let root = document.getElementById("characteristics");
        root.removeChild(root.lastChild);
        root.removeChild(root.lastChild);
    }

</script>
<style>
    <%@include file='../../../style/header.css' %>
    <%@include file='../../../style/admin.css' %>
</style>
</body>
</html>
