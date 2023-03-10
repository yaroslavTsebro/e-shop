<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
    <c:set var="title" value="company.title"/>
    <%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
<div class="container">
    <div class="wrapper">
        <form action="/controller" method="post" name="createCompanyForm">
            <input type="hidden" name="command" value="createCompany"/>
            <label for="name_ua"><fmt:message key="company.add.form.label.text.ua"/></label>
            <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                   id="name_ua" name="name_ua" placeholder="<fmt:message key="company.add.form.placeholder.text.ua"/>" required>
            <label for="name_en"><fmt:message key="company.add.form.label.text.en"/></label>
            <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                   title="${field.required}" id="name_en" name="name_en" placeholder="<fmt:message key="company.add.form.placeholder.text.en"/>" required>
            <label><fmt:message key="company.choose.country.form.button"/></label>
            <select name="country_id">
                <c:forEach items="${requestScope.countryList}" var="country">
                    <option value="${country.id}" >UA: ${country.nameUa}<br> EN: ${country.nameEn}</option>
                </c:forEach>
            </select>
            <input type="submit" value="<fmt:message key="company.add.form.button"/>">
        </form>
    </div>
    <div class="wrapper">
        <hr>
    </div>
    <div class="wrapper">
        <form action="/controller" method="get" name="searchCompaniesForm">
            <input class="hidden" type="hidden" name="command" value="searchCompanies"/>
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
                <th><fmt:message key="country.name.ua"/></th>
                <th><fmt:message key="country.name.en"/></th>
                <th><fmt:message key="entity.delete"/></th>
                <th><fmt:message key="entity.update"/></th>
            </tr>
            <c:forEach items="${requestScope.companyList}" var="company">
                <tr class="myBtn">
                    <td>${company.id}</td>
                    <td>${company.nameUa}</td>
                    <td>${company.nameEn}</td>
                    <td>${company.country.nameUa}</td>
                    <td>${company.country.nameEn}</td>
                    <td>
                        <form action="/controller" method="post" name="deleteCompanyForm">
                            <input class="hidden" type="hidden" name="command" value="deleteCompany"/>
                            <input class="hidden" type="hidden" name="delete_by_id" value="${company.id}"/>
                            <input class="deleteButton" type="submit" value="Delete"/>
                        </form>
                    </td>
                    <td>
                        <button class="updateButton" onclick="openModal(${company.id})"><fmt:message key="entity.update"/></button>
                        <input  class="hidden" id="updateIdFrom${company.id}" type="hidden" name="update_by_id" value="${company.id}"/>
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
            <h2><fmt:message key="entity.update.company.title"/></h2>
        </div>
        <div class="modal-body">
            <form action="/controller" method="post" name="updateCompanyForm">
                <input type="hidden" name="command" value="updateCompany"/>
                <input id="updateIdTo" type="hidden" name="update_by_id" value=""/>
                <label for="updated_name_ua"><fmt:message key="entity.update.company.label.name.ua"/></label>
                <input type="text" id="updated_name_ua" name="updated_name_ua" placeholder="<fmt:message key="entity.update.company.name.ua"/>" required>
                <label for="updated_name_en"><fmt:message key="entity.update.company.label.name.en"/></label>
                <input type="text" id="updated_name_en" name="updated_name_en" placeholder="<fmt:message key="entity.update.company.name.en"/>" required>
                <label><fmt:message key="company.choose.country.form.button"/></label>
                <select name="updated_country_id">
                    <c:forEach items="${requestScope.countryList}" var="country">
                        <option value="${country.id}" >UA: ${country.nameUa}<br> EN: ${country.nameEn}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="<fmt:message key="entity.update.company.submit"/>">
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
    function chooseCountry(){
        document.getElementById("country_id").value = document.getElementById("chooseCountrySelect" + id).value
    }
</script>
<style>
    <%@include file='../../../style/admin.css' %>
    <%@include file='../../../style/header.css' %>
</style>
</body>
</html>
