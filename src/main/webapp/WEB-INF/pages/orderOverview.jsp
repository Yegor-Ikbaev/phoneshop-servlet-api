<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.checkout.Order" scope="request"/>


<tags:master pageTitle="Order overview">
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
        <c:forEach var="cartItem" items="${order.cartItems}">
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
                <fmt:formatNumber value="${order.priceOfProducts}" type="currency" currencySymbol="USD"/>
            </td>
        </tr>
    </table>

    <p>
        <span>First name: ${order.contactDetails.firstName}</span>
    </p>
    <p>
        <span>Last name: ${order.contactDetails.lastName}</span>
    </p>
    <p>
        <span>Phone: ${order.contactDetails.phone}</span>
    </p>


    <p>
        <span>Delivery mode: ${order.deliveryDetails.mode}</span>
    </p>
    <p>
        <span>Delivery price: ${order.deliveryDetails.price}</span>
    </p>
    <p>
        <span>Delivery address: ${order.deliveryDetails.address}</span>
    </p>
    <p>
        <span>Delivery date: ${order.deliveryDetails.date.description}</span>
    </p>

    <p>
        <span>Total price: ${order.totalPrice}</span>
    </p>
</tags:master>