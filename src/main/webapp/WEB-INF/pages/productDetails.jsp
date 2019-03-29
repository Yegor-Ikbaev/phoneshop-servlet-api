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
        <c:url value="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}" var="imageUrl"/>
      	<td><img class="product-tile" src="${imageUrl}"></td>
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

    <c:if test="${not empty recentlyViewedProducts}">
        <p>
        <h3>Recently viewed products</h3>
        <table>
            <tr>
                <c:forEach var="product" items="${recentlyViewedProducts}">
                    <td class="price" style="text-align: center">
                        <c:url value="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}" var="imageUrl"/>
                        <img class="product-tile" src="${imageUrl}">
                        <br>
                        <c:url value="/products/${product.id}" var="productId"/>
                        <a href="${productId}">${product.description}</a>
                        <br>
                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                    </td>
                </c:forEach>
            </tr>
        </table>
    </p>
    </c:if>
</tags:master>