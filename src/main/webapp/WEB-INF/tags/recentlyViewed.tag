<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="products" required="true" type="java.util.ArrayList" %>

<c:if test="${not empty products}">
    <p>
    <h3>Recently viewed products</h3>
    <table>
        <tr>
            <c:forEach var="product" items="${products}">
                <td class="price" style="text-align: center">
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    <br>
                    <c:url value="/products/${product.id}" var="productUrl"/>
                    <a href="${productUrl}">${product.description}</a>
                    <br>
                    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </td>
            </c:forEach>
        </tr>
    </table>
    </p>
</c:if>