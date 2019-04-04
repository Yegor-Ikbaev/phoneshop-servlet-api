<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>

<tags:master pageTitle="Cart">
    <c:if test="${paramValues.success ne null}">
        <p>
        <span style="color: green">Successfully updated</span>
        </p>
    </c:if>
    <c:if test="${success eq false}">
        <p>
            <span style="color: red">Updating error</span>
        </p>
    </c:if>

    <form method="post">
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
                <td>
                    Action
                </td>
            </tr>
            </thead>
            <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="iterator">
                <tr>
                    <td>
                        <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
                    </td>
                    <td>
                        <c:url value="/products/${cartItem.product.id}" var="productId"/>
                        <input name="productId" type="hidden" value="${cartItem.product.id}"/>
                        <a href="${productId}">${cartItem.product.description}</a>
                    </td>
                    <td>
                        <input name="quantity" style="text-align: right" value="${errors[iterator.index] eq null
                                                                                  ? cartItem.quantity
                                                                                  : quantities[iterator.index]}"/>
                        <c:if test="${errors[iterator.index] ne null}">
                            <br>
                            <span style="color: red">${errors[iterator.index]}</span>
                        </c:if>
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${cartItem.product.price}" type="currency" currencySymbol="${cartItem.product.currency.symbol}"/>
                    </td>
                    <td>
                        <c:url value="/cart/delete/${cartItem.product.id}" var="deleteURL"/>
                        <button formaction="${deleteURL}">Delete</button>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="3" style="text-align: right">
                    Total price:
                </td>
                <td>
                    <fmt:formatNumber value="${cart.totalPrice}" type="currency" currencySymbol="$"/>
                </td>
            </tr>
        </table>

        <p>
            <c:url value="/cart" var="updateURL"/>
            <button formaction="${updateURL}">Update</button>
        </p>

    </form>

</tags:master>