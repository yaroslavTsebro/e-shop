<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<c:set var="title" value="category.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
    <div class="container">
        <div class="wrapper">
            <form action="/category?command=add" method="post" name="customInputForm">
                <label for="name_ua"><fmt:message key="category.add.form.label.text.ua"/></label>
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       id="name_ua" name="name_ua" placeholder="<fmt:message key="category.add.form.placeholder.text.ua"/>" required>
                <label for="name_en"><fmt:message key="category.add.form.label.text.en"/></label>
                <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                       title="${field.required}" id="name_en" name="name_en" placeholder="<fmt:message key="category.add.form.placeholder.text.en"/>" required>
                <input type="submit" value="<fmt:message key="category.add.form.button"/>">
            </form>
        </div>
        <div class="wrapper">
            <hr>
        </div>
        <div class="wrapper">
            <table id="result-table">
                <tr>
                    <th><fmt:message key="entity.id"/></th>
                    <th><fmt:message key="entity.name.ua"/></th>
                    <th><fmt:message key="entity.name.en"/></th>
                </tr>
                <c:forEach items="${requestScope.categoryList}" var="category">
                        <tr class="myBtn" onclick="var id = ${category.id}, nameUa = ${category.nameUa}, nameEn = ${category.nameEn}">
                            <td>${category.id}</td>
                            <td>${category.nameUa}</td>
                            <td>${category.nameEn}</td>
                            <td>${category.nameEn}</td>
                        </tr>
                </c:forEach>
            </table>
        </div>
        </div>
    <div id="myModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <span class="close">&times;</span>
                <h2>Change category settings</h2>
            </div>
            <div class="modal-body">
                <form action="category" method="post" name="customInputForm1">
                    <label for="updated_name_ua">name_ua</label>
                    <input type="text" id="updated_name_ua" name="updated_name_en" placeholder="Category name_ua" required>
                    <label for="updated_name_en">name_en</label>
                    <input type="text" id="updated_name_en" name="updated_name_en" placeholder="Category name_en" required>
                    <input type="submit" value="Submit">
                </form>
            </div>
        </div>
    </div>
    <script>
        var modal = document.getElementById("myModal");
        var btn = document.getElementsByClassName('myBtn');
        var span = document.getElementsByClassName("close")[0];
        btn.onclick = function() {
            modal.style.display = "block";
        }
        span.onclick = function() {
            modal.style.display = "none";
        }
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    </script>
</body>
</html>
