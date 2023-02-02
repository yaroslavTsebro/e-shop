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
        <div class="user-data">
          <div class="user code">
            <fmt:message key="admin.sending.user.id"/> ${requestScope.user.id}
          </div>
          <div class="user name lastname">
            <fmt:message key="admin.sending.user.name.lastname"/> ${requestScope.user.name} ${requestScope.user.lastname}
          </div>
          <div class="user email">
            <fmt:message key="admin.sending.user.email"/> ${requestScope.user.email}
          </div>
          <div class="user localename">
            <fmt:message key="admin.sending.user.localename"/> ${requestScope.user.localeName}
          </div>
        </div>
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
        <c:if test="${requestScope.intend.condition.toString() ne 'TURNED_BACK'}">
          <th></th>
          <th></th>
        </c:if>
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
            <c:if test="${requestScope.intend.condition.toString() ne 'TURNED_BACK'}">
            <td>
              <form action="/controller" method="post" name="deleteFromCartForm">
                <input class="hidden" type="hidden" name="command" value="deleteFromCart"/>
                <input class="hidden" type="hidden" name="user_id" value="${requestScope.user.id}"/>
                <input class="hidden" type="hidden" name="delete_li_by_id" value="${item.id}"/>
                <button type="submit">x</button>
              </form>
            </td>
            <td>
              <button class="updateButton" onclick="openModal(${item.id}, ${item.product.id}, ${item.count})">+</button>
            </td>
            </c:if>
          </tr>
        </c:forEach>
      </table>
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
        <c:if test="${requestScope.intend.condition.toString() == 'ACCEPTED'}">
        <div class="modal-body">
          <form action="/controller"   method="post" name="changeConditionForm">
            <input type="hidden" name="command" value="changeCondition"/>
            <input type="hidden" name="old_conditional" value="${requestScope.intend.condition}"/>
            <input type="hidden" name="new_conditional" value="TURNED_BACK"/>
            <input type="hidden" name="intend_id" value="${requestScope.intend.id}"/>
            <input type="submit" class="red" value="<fmt:message key="admin.sending.intend.change.condition.turned.back"/>">
          </form>
        </div>
          <div class="modal-body">
            <form action="/controller"  method="post" name="changeConditionForm">
              <input type="hidden" name="command" value="changeCondition"/>
              <input type="hidden" name="old_conditional" value="${requestScope.intend.condition}"/>
              <input type="hidden" name="new_conditional" value="IN_WAY"/>
              <input type="hidden" name="intend_id" value="${requestScope.intend.id}"/>
              <input type="submit" class="blue" value="<fmt:message key="admin.sending.intend.change.condition.in.way"/>">
            </form>
          </div>
        </c:if>
        <c:if test="${requestScope.intend.condition.toString() == 'CART'}">
        </c:if>
        <c:if test="${requestScope.intend.condition.toString() == 'DENIED'}">
        </c:if>
        <c:if test="${requestScope.intend.condition.toString() == 'NEW'}">
          <div class="modal-body">
            <form action="/controller"  method="post" name="changeConditionForm">
              <input type="hidden" name="command" value="changeCondition"/>
              <input type="hidden" name="old_conditional" value="${requestScope.intend.condition}"/>
              <input type="hidden" name="new_conditional" value="ACCEPTED"/>
              <input type="hidden" name="intend_id" value="${requestScope.intend.id}"/>
              <input type="submit" class="green" value="<fmt:message key="admin.sending.intend.change.condition.accepted"/>">
            </form>
          </div>
          <div class="modal-body">
            <form action="/controller"  method="post" name="changeConditionForm">
              <input type="hidden" name="command" value="changeCondition"/>
              <input type="hidden" name="old_conditional" value="${requestScope.intend.condition}"/>
              <input type="hidden" name="new_conditional" value="DENIED"/>
              <input type="hidden" name="intend_id" value="${requestScope.intend.id}"/>
              <input type="submit" class="red" value="<fmt:message key="admin.sending.intend.change.condition.denied"/>">
            </form>
          </div>
        </c:if>
        <c:if test="${requestScope.intend.condition.toString() == 'IN_WAY'}">
          <div class="modal-body">
            <form action="/controller"  method="post" name="changeConditionForm">
              <input type="hidden" name="command" value="changeCondition"/>
              <input type="hidden" name="old_conditional" value="${requestScope.intend.condition}"/>
              <input type="hidden" name="new_conditional" value="COMPLETED"/>
              <input type="hidden" name="intend_id" value="${requestScope.intend.id}"/>
              <input type="submit" class="green" value="<fmt:message key="admin.sending.intend.change.condition.completed"/>">
            </form>
          </div>
          <div class="modal-body">
            <form action="/controller"  method="post" name="changeConditionForm">
              <input type="hidden" name="command" value="changeCondition"/>
              <input type="hidden" name="old_conditional" value="${requestScope.intend.condition}"/>
              <input type="hidden" name="new_conditional" value="TURNED_BACK"/>
              <input type="hidden" name="intend_id" value="${requestScope.intend.id}"/>
              <input type="submit" class="green" value="<fmt:message key="admin.sending.intend.change.condition.turned.back"/>">
            </form>
          </div>
        </c:if>
        <c:if test="${requestScope.intend.condition.toString() == 'COMPLETED'}">
          <div class="modal-body">
            <form action="/controller" method="post" name="changeConditionForm">
              <input type="hidden" name="command" value="changeCondition"/>
              <input type="hidden" name="old_conditional" value="${requestScope.intend.condition}"/>
              <input type="hidden" name="new_conditional" value="TURNED_BACK"/>
              <input type="hidden" name="intend_id" value="${requestScope.intend.id}"/>
              <input type="submit" class="red" value="<fmt:message key="admin.sending.intend.change.condition.turned.back"/>">
            </form>
          </div>
        </c:if>
        <div id="myModal" class="modal">
          <div class="modal-content">
            <div class="modal-header">
              <span class="close">&times;</span>
              <h2><fmt:message key="cart.change.count.title"/></h2>
            </div>
            <div class="modal-body">
              <form action="/controller" method="post" name="updateProductCountInIntendAsAdminForm">
                <input type="hidden" name="command" value="updateProductCountInIntendAsAdmin"/>
                <input type="hidden" name="intend_id" value="${requestScope.intend.id}"/>
                <input type="hidden" name="user_id" value="${requestScope.user.id}"/>
                <input id="updateIdTo" type="hidden" name="update_by_id" value=""/>
                <input id="updateProductIdTo" type="hidden" name="update_by_product_id" value=""/>
                <input id="updateProductCountTo" type="hidden" name="update_by_product_count" value=""/>
                <label for="updated_li_count"><fmt:message key="cart.modal.change.count.label"/></label>
                <input type="number" id="updated_li_count" name="updated_li_count" placeholder="
                                            <fmt:message key="cart.modal.change.count.placeholder"/>" required>
                <input type="submit" value="<fmt:message key="cart.modal.change.count.button"/>">
              </form>
            </div>
          </div>
        </div>
      </c:if>
      <c:if test="${empty requestScope.intend.listIntends && requestScope.intend.condition.toString() == 'CART'}">
        <div class="empty-cart">
          <fmt:message key="admin.sending.intend.cart.empty"/>
        </div>
      </c:if>
    </div>
  </div>
<script>
  let modal = document.getElementById("myModal");
  let btn = document.getElementsByClassName('myBtn');
  let span = document.getElementsByClassName("close")[0];
  function openModal(id, productId, count){
    modal.style.display = "block";
    document.getElementById("updateIdTo").value = id
    document.getElementById("updateProductIdTo").value = productId
    document.getElementById("updateProductCountTo").value = count

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
  <%@include file='../../../style/header.css' %>
  <%@include file='../../../style/cart.css' %>
  <%@include file='../../../style/current_sending.css' %>
</style>
</body>
</html>
