<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<c:set var="title" value="category.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
    <div class="container">
        <div class="wrapper">
            <form action="category" method="post" name="customInputForm">
                <label for="name_ua">name_ua</label>
                <input type="text" id="name_ua" name="name_ua" placeholder="Category name_ua" required>
                <label for="name_en">name_en</label>
                <input type="text" id="name_en" name="name_en" placeholder="Category name_en" required>
                <input type="submit" value="Submit">
            </form>
        </div>
        <div class="wrapper">
            <hr>
        </div>
        <div class="wrapper">
            <table id="result-table">
                <tr>
                    <th>ID</th>
                    <th>name_ua</th>
                    <th>name_en</th>
                </tr>
                <c:forEach items="${requestScope.categoryList}" var="category">
                    <tr>
                        <td>${category.id}</td>
                        <td>${category.nameUa}</td>
                        <td>${category.nameEn}</td>
                    </tr>
                    <tr>
                        <td>${category.id}</td>
                        <td>${category.nameUa}</td>
                        <td>${category.nameEn}</td>
                    </tr>
                    <tr>
                        <td>${category.id}</td>
                        <td>${category.nameUa}</td>
                        <td>${category.nameEn}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        </div>
</body>
</html>
