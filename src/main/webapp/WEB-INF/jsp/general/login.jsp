<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty sessionScope.lang}">
  <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
</c:if>
<c:if test="${not empty sessionScope.lang}">
  <script src='https://www.google.com/recaptcha/api.js?hl=${sessionScope.lang}'></script>
</c:if>
<html>
  <c:set var="title" value="login.title"/>
  <%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
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
      <div class="g-recaptcha" id="rcaptcha" data-sitekey="6LfBxiwkAAAAANXv9zbsFWpqeOsPzrpVD-v8_UOb">
      </div>
      <input type="submit" value="<fmt:message key="login.button.confirm"/>">
    </form>
    <div><fmt:message key="login.text.already"/>
      <a href="/controller?command=registerPage"/><fmt:message key="login.text.url.text"/></a></div>
  </div>
</div>
<style>
  <%@include file='../../../style/login.css' %>
</style>
</body>
</html>
