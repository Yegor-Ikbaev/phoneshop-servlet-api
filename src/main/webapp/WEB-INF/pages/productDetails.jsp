<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>Description</td>
        <td class="">Price</td>
        <td>Stock</td>
      </tr>
    </thead>
    <tr>
      	<td><img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"></td>
        <td>${product.description}</td>
        <td class="price">
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
        <td>${product.stock}</td>
      </tr>
  </table>

    <p>
        <c:url value="/products/${product.id}" var="thisPage"/>
        <form method="post" action="${thisPage}">
            <c:if test="${not empty param.success}">
                <span style="color: green">${param.success}</span>
                <br>
            </c:if>
            <span>Quantity: </span>
            <input name="quantity" value="${empty param.quantity ? 1 : param.quantity}" style="text-align: right"/>
            <button type="submit">Add</button>
            <c:if test="${not empty param.exception}">
                <br>
                <span style="color: red">${param.exception}</span>
            </c:if>
        </form>
    </p>

    <tags:recentlyViewed products="${recentlyViewedProducts}"/>

</tags:master>