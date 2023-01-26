<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty sessionScope.lang}">
  <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
</c:if>
<c:if test="${not empty sessionScope.lang}">
  <script src='https://www.google.com/recaptcha/api.js?hl=${sessionScope.lang}'></script>
</c:if>
<html>
<c:set var="title" value="register.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
<div class="container" style="margin: 150px auto;">
  <div class="wrapper">
    <form action="/controller" method="post" name="registerForm">
      <input type="hidden" name="command" value="registerCommand"/>

      <label for="name"><fmt:message key="register.text.name"/></label>
      <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
             id="name" name="name" placeholder="<fmt:message key="register.placeholder.name"/>" required>

      <label for="lastname"><fmt:message key="register.text.lastname"/></label>
      <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
             id="lastname" name="lastname" placeholder="<fmt:message key="register.placeholder.lastname"/>" required>

      <label for="email"><fmt:message key="register.text.email"/></label>
      <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
             id="email" name="email" placeholder="<fmt:message key="register.placeholder.email"/>" required>

      <label for="password"><fmt:message key="register.text.password"/></label>
      <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
             title="${field.required}" id="password" name="password" placeholder="<fmt:message key="register.placeholder.password"/>" required>

      <div class="g-recaptcha" id="rcaptcha" data-sitekey="6LfBxiwkAAAAANXv9zbsFWpqeOsPzrpVD-v8_UOb">
        
      </div>
      <input type="submit" value="<fmt:message key="register.button.confirm"/>">
    </form>
    <div><fmt:message key="register.text.already"/>
      <a href="/controller?command=loginPage"/><fmt:message key="register.text.url.text"/></a></div>
  </div>
</div>
<style>
  <%@include file='../../../style/login.css' %>
</style>
</body>
</html>
