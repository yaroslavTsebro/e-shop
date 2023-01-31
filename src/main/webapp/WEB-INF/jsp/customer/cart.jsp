<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:set var="title" value="cart.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
    <%@ include file="/WEB-INF/jspf/part/header.jspf" %>
    <div class="wrapper">
        <c:if test="${empty sessionScope.user}">
            <div class="container">
                <div class="cart-trouble">
                    <a href="/controller?command=loginPage"><fmt:message key="cart.user.empty"/></a>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
            <c:if test="${empty requestScope.cart.listIntends}">
                <div class="container">
                    <div class="cart-trouble">
                        <fmt:message key="cart.empty"/>
                    </div>
                </div>
            </c:if>
            <c:if test="${not empty requestScope.cart.listIntends}">
                <div class="container">
                    <div class="wrapper">
                        <table id="result-table">
                            <tr>
                                <th><fmt:message key="cart.table.sequence"/></th>
                                <th></th>
                                <th><fmt:message key="cart.table.name"/></th>
                                <th><fmt:message key="cart.table.price"/></th>
                                <th><fmt:message key="cart.table.chose"/></th>
                                <th><fmt:message key="cart.table.avail"/></th>
                                <th><fmt:message key="cart.table.total.price"/></th>
                                <th></th>
                                <th></th>
                            </tr>
                            <c:forEach items="${requestScope.cart.listIntends}" var="item" varStatus="loop">
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
                                    <td>
                                            <form action="/controller" method="post" name="deleteFromCartForm">
                                                <input class="hidden" type="hidden" name="command" value="deleteFromCart"/>
                                                <input class="hidden" type="hidden" name="delete_li_by_id" value="${item.id}"/>
                                                <button type="submit">x</button>
                                            </form>
                                    </td>
                                    <td>
                                        <button class="updateButton" onclick="openModal(${item.id}, ${item.product.id}, ${item.count})">+</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <button class="registerIntend" onclick="openModal1(${item.id})"><fmt:message key="cart.register.pre.button"/></button>
                    </div>

                <div id="myModal" class="modal">
                    <div class="modal-content">
                        <div class="modal-header">
                            <span class="close">&times;</span>
                            <h2><fmt:message key="cart.change.count.title"/></h2>
                        </div>
                        <div class="modal-body">
                            <form action="/controller" method="post" name="updateProductCountInCart">
                                <input type="hidden" name="command" value="updateProductCountInCart"/>
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

                <div id="myModal1" class="modal">
                    <div class="modal-content">
                        <div class="modal-header">
                            <span class="close">&times;</span>
                            <h2><fmt:message key="cart.register.intend.title"/></h2>
                        </div>
                        <div class="modal-body">
                            <form action="/controller" method="post" name="registerIntendForm">
                                <input type="hidden" name="command" value="registerIntend"/>
                                <input id="register_by_id" type="hidden" name="register_by_id" value="${requestScope.cart.id}"/>
                                <label for="address"><fmt:message key="cart.address.label"/></label>
                                <input type="text" id="address" name="address" placeholder="<fmt:message key="cart.address.placeholder"/>" required>
                                <input type="submit" value="<fmt:message key="entity.update.category.submit"/>">
                            </form>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:if>
    </div>
    <script>
        let modal = document.getElementById("myModal");
        let modal1 = document.getElementById("myModal1");
        let btn = document.getElementsByClassName('myBtn');
        let span = document.getElementsByClassName("close")[0];
        function openModal(id, productId, count){
            modal.style.display = "block";
            document.getElementById("updateIdTo").value = id
            document.getElementById("updateProductIdTo").value = productId
            document.getElementById("updateProductCountTo").value = count

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
    </style>
</body>
</html>
