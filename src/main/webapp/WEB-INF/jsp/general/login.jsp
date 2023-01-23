<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <c:set var="title" value="category.title"/>
  <%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<div class="container">
  <div class="wrapper">
    <form action="/controller" method="post" name="loginForm">
      <input type="hidden" name="command" value="loginCommand"/>
      <label for="email"><fmt:message key="login.text.email"/></label>
      <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
             id="email" name="email" placeholder="<fmt:message key="login.placeholder.email"/>" required>
      <label for="password"><fmt:message key="login.text.password"/></label>
      <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
             title="${field.required}" id="password" name="password" placeholder="<fmt:message key="login.placeholder.password"/>" required>
      <input type="submit" value="<fmt:message key="login.button.confirm"/>">
    </form>
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
