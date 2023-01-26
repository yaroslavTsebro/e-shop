<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<c:set var="title" value="category.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
    <div class="container">
        <div class="wrapper">
            <form action="/controller" method="post" name="createCategoryForm">
                <input type="hidden" name="command" value="createCategory"/>
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
            <form action="/controller" method="get" name="searchCategoriesForm">
                <input class="hidden" type="hidden" name="command" value="searchCategories"/>
                <input type="search" name="pattern" placeholder="<fmt:message key="manager.add.search"/>">
                <input type="submit" value="<fmt:message key="manager.add.search.button"/>"/>
            </form>
        </div>
        <div class="wrapper">
            <table id="result-table">
                <tr>
                    <th><fmt:message key="entity.id"/></th>
                    <th><fmt:message key="entity.name.ua"/></th>
                    <th><fmt:message key="entity.name.en"/></th>
                    <th><fmt:message key="entity.delete"/></th>
                    <th><fmt:message key="entity.update"/></th>
                </tr>
                <c:forEach items="${requestScope.categoryList}" var="category">
                        <tr class="myBtn">
                            <td>${category.id}</td>
                            <td>${category.nameUa}</td>
                            <td>${category.nameEn}</td>
                            <td>
                                <form action="/controller" method="post" name="deleteCategoryForm">
                                    <input class="hidden" type="hidden" name="command" value="deleteCategory"/>
                                    <input class="hidden" type="hidden" name="delete_by_id" value="${category.id}"/>
                                    <input class="deleteButton" type="submit" value="<fmt:message key="entity.delete"/>"/>
                                </form>
                            </td>
                            <td>
                                <button class="updateButton" onclick="openModal(${category.id})"><fmt:message key="entity.update"/></button>
                                <input  class="hidden" id="updateIdFrom${category.id}" type="hidden" name="update_by_id" value="${category.id}"/>
                            </td>
                        </tr>
                </c:forEach>
            </table>
        </div>
        </div>
    <div id="myModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <span class="close">&times;</span>
                <h2><fmt:message key="entity.update.category.title"/></h2>
            </div>
            <div class="modal-body">
                <form action="/controller" method="post" name="updateCategoryForm">
                    <input type="hidden" name="command" value="updateCategory"/>
                    <input id="updateIdTo" type="hidden" name="update_by_id" value=""/>
                    <label for="updated_name_ua"><fmt:message key="entity.update.category.label.name.ua"/></label>
                    <input type="text" id="updated_name_ua" name="updated_name_ua" placeholder="<fmt:message key="entity.update.category.name.ua"/>" required>
                    <label for="updated_name_en"><fmt:message key="entity.update.category.label.name.en"/></label>
                    <input type="text" id="updated_name_en" name="updated_name_en" placeholder="<fmt:message key="entity.update.category.name.en"/>" required>
                    <input type="submit" value="<fmt:message key="entity.update.category.submit"/>">
                </form>
            </div>
        </div>
    </div>
    <script>
        let modal = document.getElementById("myModal");
        let btn = document.getElementsByClassName('myBtn');
        let span = document.getElementsByClassName("close")[0];
        function openModal(id){
            modal.style.display = "block";
            document.getElementById("updateIdTo").value = document.getElementById("updateIdFrom" + id).value
        }
        span.onclick = function() {
            modal.style.display = "none";
        }
        window.onclick = function(event) {
            if (event.target === modal) {
                modal.style.display = "none";
            }
        }
    </script>
    <style>
        <%@include file='../../../style/admin.css' %>
    </style>
</body>
</html>
