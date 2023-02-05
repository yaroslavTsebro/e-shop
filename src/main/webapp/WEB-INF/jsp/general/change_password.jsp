<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:set var="title" value="login.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
<div class="container">
    <div class="wrapper">
        <form action="/controller" method="post" name="changePasswordCommandForm">
            <input type="hidden" name="command" value="changePasswordCommand"/>

            <label for="code"><fmt:message key="login.text.email"/></label>
            <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                   id="code" name="code" placeholder="<fmt:message key="login.placeholder.email"/>" required>

            <label for="email"><fmt:message key="login.text.email"/></label>
            <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                   id="email" name="email" placeholder="<fmt:message key="login.placeholder.email"/>" required>

            <label for="newPassword"><fmt:message key="login.text.email"/></label>
            <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                   id="newPassword" name="new_password" placeholder="<fmt:message key="login.placeholder.email"/>" required>

            <label for="repPassword"><fmt:message key="login.text.email"/></label>
            <input type="text" oninvalid="this.setCustomValidity('<fmt:message key="field.required"/>')"
                   id="repPassword" name="rep_password" placeholder="<fmt:message key="login.placeholder.email"/>" required>

            <input type="submit" value="<fmt:message key="login.button.code"/>">
        </form>
    </div>
</div>
<style>
    <%@include file='../../../style/header.css' %>
    <%@include file='../../../style/login.css' %>
</style>
</body>
</html>