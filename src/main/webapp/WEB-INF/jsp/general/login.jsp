<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</div>
<style>
  <%@include file='../../../style/admin_style.css' %>
</style>
</body>
</html>
