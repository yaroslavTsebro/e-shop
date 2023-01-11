<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <c:set var="title" value="product.title"/>
    <%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
    <div class="container">
        <div class="wrapper">
            <form action="/product?command=add" method="post" name="customInputForm">
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="name_ua" name="name_ua" placeholder="<fmt:message key="category.add.form.placeholder.text.en"/>" required>
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="name_en" name="name_en" placeholder="<fmt:message key="category.add.form.placeholder.text.en"/>" required>
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="price" name="price" placeholder="<fmt:message key="category.add.form.placeholder.text.en"/>" required>
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="weight" name="weight" placeholder="<fmt:message key="category.add.form.placeholder.text.en"/>" required>
                <input type="submit" value="<fmt:message key="category.add.form.button"/>">
            </form>
        </div>
    </div>
</body>
</html>
