<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<c:set var="title" value="current.sending.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/part/header.jspf" %>
<div class="container">
  <div class="wrapper">
    <div class="data">
      <div class="intend-data">
        <div class=" intend name lastname">
          <fmt:message key="admin.sending.intend.id"/> ${requestScope.intend.id}
        </div>
        <div class="email">
          <fmt:message key="admin.sending.intend.start.date"/> ${requestScope.intend.startDate}
        </div>
        <div class="email">
          <fmt:message key="admin.sending.intend.end.date"/> ${requestScope.intend.endDate}
        </div>
        <div class="email">
          <fmt:message key="admin.sending.intend.employee.id"/> ${requestScope.intend.employeeId}
        </div>
        <div class="code">
          <fmt:message key="admin.sending.intend.address"/> ${requestScope.intend.address}
        </div>
        <c:if test="${requestScope.intend.condition.toString() == 'ACCEPTED'}">
          <div class="green">
            <fmt:message key="admin.sending.intend.condition"/> ${requestScope.intend.condition}
          </div>
        </c:if>
        <c:if test="${requestScope.intend.condition.toString() == 'CART'}">
          <div class="grey">
            <fmt:message key="admin.sending.intend.condition"/> ${requestScope.intend.condition}
          </div>
        </c:if>
        <c:if test="${requestScope.intend.condition.toString() == 'TURNED_BACK'}">
          <div class="red">
            <fmt:message key="admin.sending.intend.condition"/> ${requestScope.intend.condition}
          </div>
        </c:if>
        <c:if test="${requestScope.intend.condition.toString() == 'IN_WAY'}">
          <div class="blue">
            <fmt:message key="admin.sending.intend.condition"/> ${requestScope.intend.condition}
          </div>
        </c:if>
        <c:if test="${requestScope.intend.condition.toString() == 'NEW'}">
          <div class="blue">
            <fmt:message key="admin.sending.intend.condition"/> ${requestScope.intend.condition}
          </div>
        </c:if>
        <c:if test="${requestScope.intend.condition.toString() == 'DENIED'}">
          <div class="red">
            <fmt:message key="admin.sending.intend.condition"/> ${requestScope.intend.condition}
          </div>
        </c:if>
        <c:if test="${requestScope.intend.condition.toString() == 'COMPLETED'}">
          <div class="green">
            <fmt:message key="admin.sending.intend.condition"/> ${requestScope.intend.condition}
          </div>
        </c:if>
      </div>
    </div>
    <c:if test="${not empty requestScope.intend.listIntends}">
      <table id="result-table">
        <tr>
          <th><fmt:message key="cart.table.sequence"/></th>
          <th></th>
          <th><fmt:message key="cart.table.name"/></th>
          <th><fmt:message key="cart.table.price"/></th>
          <th><fmt:message key="cart.table.chose"/></th>
          <th><fmt:message key="cart.table.avail"/></th>
          <th><fmt:message key="cart.table.total.price"/></th>
        </tr>
        <c:forEach items="${requestScope.intend.listIntends}" var="item" varStatus="loop">
          <tr class="myBtn">
            <td>${loop.index + 1}</td>
            <td>
              <img src="../../../static/images/${item.product.photos[0].name}">
            </td>
            <c:if test="${sessionScope.lang == 'ua'}">
              <td>${item.product.nameUa}</td>
            </c:if>
            <c:if test="${sessionScope.lang == 'en' || empty sessionScope.lang}">
              <td>${item.product.nameEn}</td>
            </c:if>
            <td>${item.productPrice}</td>
            <td>${item.count}</td>
            <td>${item.product.count}</td>
            <td>${item.product.price * item.count}</td>
          </tr>
        </c:forEach>
      </table>
      <c:if test="${requestScope.intend.condition.toString() ne 'TURNED_BACK'}">
        <div style="margin: 20px 0 0 0">
          <fmt:message key="intend.change.condition"/>
        </div>
        <button class="turnBackButton" onclick="openModal()"><fmt:message key="admin.sending.intend.change.condition.turned.back"/></button>

        <div id="myModal" class="modal">
        <div class="modal-content">
          <div class="modal-header">
            <span class="close">&times;</span>
            <h2><fmt:message key="cart.change.count.title"/></h2>
          </div>
          <div class="modal-body">
            <form action="/controller" method="post" name="turnIntendBackForm">
              <input type="hidden" name="command" value="turnIntendBack"/>
              <input type="hidden" name="intend_id" value="${requestScope.intend.id}"/>
              <label for="reason"><fmt:message key="intend.change.condition.reason.label"/></label>
              <textarea name="reason" required id="reason"></textarea>
              <input type="submit" value="<fmt:message key="cart.modal.change.count.button"/>">
            </form>
          </div>
        </div>
        </div>
    </c:if>
      <c:if test="${requestScope.intend.condition.toString() == 'TURNED_BACK'}">
        <c:if test="${not empty requestScope.intendReturn.reason}">
          <div class="data">
            <div class="intend-data">
              <div class=" intend name lastname">
                <fmt:message key="intend.return.reason.text"/> ${requestScope.intendReturn.reason}
              </div>
            </div>
          </div>
        </c:if>
      </c:if>
    </c:if>
  </div>
</div>
<script>
  let modal = document.getElementById("myModal");
  let modal1 = document.getElementById("myModal1");
  let btn = document.getElementsByClassName('myBtn');
  let span = document.getElementsByClassName("close")[0];
  function openModal(){
    modal.style.display = "block";
  }

  function openModal1(id){
    modal1.style.display = "block";
    document.getElementById("updateIdTo").value = id

  }
  span.onclick = function() {
    modal.style.display = "none";
    modal1.style.display = "none";
  }
  window.onclick = function(event) {
    if (event.target === modal) {
      modal.style.display = "none";
    }
    if (event.target === modal1) {
      modal1.style.display = "none";
    }
  }
</script>
<style>
  <%@include file='../../../style/header.css' %>
  <%@include file='../../../style/cart.css' %>
  <%@include file='../../../style/current_sending.css' %>
  <%@include file='../../../style/current_intend.css' %>
</style>
</body>
</html>
