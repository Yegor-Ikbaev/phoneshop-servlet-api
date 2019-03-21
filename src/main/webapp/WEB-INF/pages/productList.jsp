<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Product List">
<tags:header/>  

  <form action="">
  	<input name="search" value="${param.search}"/>
  	 <button type="submit">Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>
        Description
        <a href="products?search=${param.search}&sortBy=description&order=asc">&uarr;</a>
        <a href="products?search=${param.search}&sortBy=description&order=desc">&darr;</a>
        </td>
        <td class="">
        Price
        <a href="products?search=${param.search}&sortBy=price&order=asc">&uarr;</a>
        <a href="products?search=${param.search}&sortBy=price&order=desc">&darr;</a>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td>
        <a href="products/${product.id}">${product.description}</a>
        </td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
  <tags:footer/>  
</tags:master>