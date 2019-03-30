<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Product List">
  <form action="">
  	<input name="search" value="${param.search}"/>
  	 <button type="submit">Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>
        	Image
        </td>
        <td>
	        Description
	        <tags:sort search="${param.search}" sortBy="description"/>
        </td>
        <td>
        	Price
	        <tags:sort search="${param.search}" sortBy="price"/>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td>
        <c:url value="/products/${product.id}" var="productId"/>
        <a href="${productId}">${product.description}</a>
        </td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>