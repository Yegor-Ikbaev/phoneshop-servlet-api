<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>


<tags:master pageTitle="CheckoutPage">
    <table>
        <thead>
        <tr>
            <td>
                Image
            </td>
            <td>
                Description
            </td>
            <td>
                Quantity
            </td>
            <td>
                Price
            </td>
        </tr>
        </thead>
        <c:forEach var="cartItem" items="${cart.cartItems}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
                </td>
                <td>
                    <c:url value="/products/${cartItem.product.id}" var="productUrl"/>
                    <a href="${productUrl}">${cartItem.product.description}</a>
                </td>
                <td>
                    ${cartItem.quantity}
                </td>
                <td class="price">
                    <fmt:formatNumber value="${cartItem.product.price}" type="currency"
                                      currencySymbol="${cartItem.product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3" style="text-align: right">
                Price of products:
            </td>
            <td>
                <fmt:formatNumber value="${cart.totalPrice}" type="currency" currencySymbol="USD"/>
            </td>
        </tr>
    </table>

    <form method="post">
        <p>
            <label for="firstName">First name</label>
            <input id="firstName" name="firstName" value="${firstName}"/>
        </p>
        <c:if test="${not empty firstNameError}">
            <p>
                <span style="color: red">${firstNameError}</span>
            </p>
        </c:if>
        <p>
            <label for="lastName">Last name</label>
            <input id="lastName" name="lastName" value="${lastName}"/>
        </p>
        <c:if test="${not empty lastNameError}">
            <p>
                <span style="color: red">${lastNameError}</span>
            </p>
        </c:if>
        <p>
            <label for="phone">Phone</label>
            <input id="phone" name="phone" value="${phone}"/>
        </p>
        <c:if test="${not empty phoneError}">
            <p>
                <span style="color: red">${phoneError}</span>
            </p>
        </c:if>

        <p>
            <label for="deliveryMode">Delivery mode</label>
            <select id="deliveryMode" name="deliveryMode">
                <c:forEach var="deliveryMode" items="${deliveryModes}">
                    <option value="${deliveryMode.price}">${deliveryMode.description}</option>
                </c:forEach>
            </select>
            <label id="priceOfDelivery" name="priceOfDelivery">Price:</label>
            <script type="text/javascript">
                document.getElementById("deliveryMode").addEventListener("change", function () {
                    document.getElementById("priceOfDelivery").innerHTML = "Price: " + this.value;
                });
            </script>
        </p>

        <p>
            <label for="date">Delivery date</label>
            <select id="date" name="date">
                <c:forEach var="date" items="${dates}">
                    <option>${date.description}</option>
                </c:forEach>
            </select>
        </p>
        <p>
            <label for="address">Address</label>
            <input id="address" name="address" value="${address}"/>
        </p>
        <c:if test="${not empty addressError}">
            <p>
                <span style="color: red">${addressError}</span>
            </p>
        </c:if>

        <p>
            <label for="paymentMethod">Payment method</label>
            <select id="paymentMethod" name="paymentMethod">
                <c:forEach var="paymentMethod" items="${paymentMethods}">
                    <option>${paymentMethod.description}</option>
                </c:forEach>
            </select>
        </p>

        <p>
            <c:url value="/checkout" var="checkoutPage"/>
            <button formaction="${checkoutPage}">Place</button>
        </p>
    </form>

</tags:master>