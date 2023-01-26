<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<c:set var="title" value="cart.title"/>
<%@ include file="/WEB-INF/jspf/part/head.jspf" %>
<body>
    <%@ include file="/WEB-INF/jspf/part/header.jspf" %>
    <div class="wrapper">
        <c:if test="${empty sessionScope.user}">
            <div class="cart-trouble">
                <a href="/controller?command=loginPage"><fmt:message key="cart.user.empty"/></a>
            </div>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
            <c:if test="${empty requestScope.cart}">
                <div class="cart-trouble">
                    <fmt:message key="cart.empty"/>
                </div>
            </c:if>
            <c:if test="${not empty requestScope.cart}">
                <div class="container">
                    <div class="table-head">
                        <div class="table-row table-row-title">
                            <li class="item-cell">#</li>
                            <li class="item-cell"></li>
                            <li class="item-cell">Product Code</li>
                            <li class="item-cell">Product name</li>
                            <li class="item-cell">Price</li>
                            <li class="item-cell">Chose</li>
                            <li class="item-cell">Avail.</li>
                            <li class="item-cell">Total Price</li>
                        </div>
                    </div>
                    <c:forEach items="${requestScope.cart.listIntends}" var="item" varStatus="loop">
                        <div class="table-body">
                            <div class="table-row">
                                <li class="item-cell">${loop.index}</li>
                                <li class="item-cell">
                                    <img src="../../../static/images/${item.product.photos[0]}">
                                </li>
                                <li class="item-cell">${item.product.id}</li>
                                <c:if test="${sessionScope.lang == 'ua'}">
                                    <li class="item-cell">${item.product.nameUa}</li>
                                </c:if>
                                <c:if test="${sessionScope.lang == 'en' || empty sessionScope.lang}">
                                    <li class="item-cell">${item.product.nameEn}</li>
                                </c:if>
                                <li class="item-cell">${item.product.price}</li>
                                <li class="item-cell">${item.count}</li>
                                <li class="item-cell">${item.product.count}</li>
                                <li class="item-cell">${item.product.price * item.count}</li>
                                <li class="item-cell">
                                    <form action="/controller" method="post" name="deleteCategoryForm">
                                        <input class="hidden" type="hidden" name="command" value="deleteCategory"/>
                                        <input class="hidden" type="hidden" name="delete_by_id" value="${item.product.id}"/>
                                        <input class="deleteButton" type="submit" value="<fmt:message key="entity.delete"/>"/>
                                    </form>
                                </li>
                                <li class="item-cell">
                                    <button class="updateButton" onclick="openModal(${item.product.id})"><fmt:message key="cart.change.count"/></button>
                                    <input  class="hidden" id="updateIdFrom${item.product.id}" type="hidden" name="update_by_id" value="${item.product.id}"/>
                                </li>
                            </div>
                        </div>
                    </c:forEach>
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
                                <label for="updated_price_count"><fmt:message key="entity.update.category.label.name.ua"/></label>
                                <input type="number" id="updated_price_count" name="updated_price_count" placeholder="
                                            <fmt:message key="entity.update.category.name.ua"/>" required>
                                <input type="submit" value="<fmt:message key="entity.update.category.submit"/>">
                            </form>
                        </div>
                    </div>
                </div>

                <div id="myModal" class="modal">
                    <div class="modal-content">
                        <div class="modal-header">
                            <span class="close">&times;</span>
                            <h2><fmt:message key="cart.register.intend.title"/></h2>
                        </div>
                        <div class="modal-body">
                            <form action="/controller" method="post" name="registerIntend">
                                <input type="hidden" name="command" value="registerIntend"/>
                                <label for="updated_name_ua"><fmt:message key="cart.address.label"/></label>
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
