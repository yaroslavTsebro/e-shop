<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
      <input type="submit" value="<fmt:message key="login.button.confirm"/>">
    </form>
    <div><fmt:message key="login.text.already"/>
      <a href="/controller?command=registerCommand"/><fmt:message key="login.text.url.text"/></a></div>
  </div>
</div>
<style>
  <%@include file='../../../style/admin.css' %>
</style>
</body>
</html>
