<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<c:set var="title" value="characteristic.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<div class="container">
  <div class="wrapper">
    <form action="/controller" method="post" name="createCharacteristicForm">
      <input type="hidden" name="command" value="createCharacteristic"/>
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
    <form action="/controller" method="get" name="searchCharacteristicForm">
      <input class="hidden" type="hidden" name="command" value="searchCharacteristics"/>
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
      <c:forEach items="${requestScope.characteristicList}" var="characteristic">
        <tr class="myBtn">
          <td>${characteristic.id}</td>
          <td>${characteristic.nameUa}</td>
          <td>${characteristic.nameEn}</td>
          <td>
            <form action="/controller" method="post" name="deleteCharacteristicForm">
              <input class="hidden" type="hidden" name="command" value="deleteCharacteristic"/>
              <input class="hidden" type="hidden" name="delete_by_id" value="${characteristic.id}"/>
              <input class="deleteButton" type="submit" value="<fmt:message key="entity.delete"/>"/>
            </form>
          </td>
          <td>
            <button class="updateButton" onclick="openModal(${characteristic.id})"><fmt:message key="entity.update"/></button>
            <input  class="hidden" id="updateIdFrom${characteristic.id}" type="hidden" name="update_by_id" value="${characteristic.id}"/>
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
      <h2><fmt:message key="entity.update.characteristic.title"/></h2>
    </div>
    <div class="modal-body">
      <form action="/controller" method="post" name="updateCharacteristicForm">
        <input type="hidden" name="command" value="updateCharacteristic"/>
        <input id="updateIdTo" type="hidden" name="update_by_id" value=""/>
        <label for="updated_name_ua"><fmt:message key="entity.update.characteristic.label.name.ua"/></label>
        <input type="text" id="updated_name_ua" name="updated_name_ua" placeholder="<fmt:message key="entity.update.characteristic.name.ua"/>" required>
        <label for="updated_name_en"><fmt:message key="entity.update.characteristic.label.name.en"/></label>
        <input type="text" id="updated_name_en" name="updated_name_en" placeholder="<fmt:message key="entity.update.characteristic.name.en"/>" required>
        <input type="submit" value="<fmt:message key="entity.update.characteristic.submit"/>">
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
