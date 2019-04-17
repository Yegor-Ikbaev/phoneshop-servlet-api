<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">

    <p>
        <jsp:include page="/cart/minicart"/>
        <c:if test="${not empty errorMessage}">
            <%@ include file="../fragments/miniCart.jsp" %>
        </c:if>
    </p>

    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td>Price</td>
            <td>Stock</td>
        </tr>
        </thead>
        <tr>
            <td><img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>${product.description}</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
            <td>${product.stock}</td>
        </tr>
    </table>


    <c:url value="/products/${product.id}" var="thisPage"/>
    <form method="post" action="${thisPage}">
        <p>
            <c:if test="${not empty param.success}">
                <span style="color: green">Successfully added</span>
                <br>
            </c:if>
            <span>Quantity: </span>
            <input name="quantity" value="${empty quantity ? 1 : quantity}" style="text-align: right"/>
            <button type="submit">Add</button>
            <c:if test="${not empty errorMessage}">
                <br>
                <span style="color: red">${errorMessage}</span>
            </c:if>
        </p>
    </form>

    <br>

    <form method="post">
        <c:if test="${not empty param.successReview}">
            <p>
                <span style="color: green">Successfully added</span>
            </p>
        </c:if>
        <c:if test="${not empty param.reviewErrorMessage}">
            <p>
                <span style="color: red">Input error: ${param.reviewErrorMessage}</span>
            </p>
        </c:if>
        <p>
            <label for="username">Username</label>
            <input id="username" name="username" value="${username}"/>
        </p>
        <p>
            <label for="rating">Rating</label>
            <input id="rating" name="rating" value="${rating}"/>
        </p>
        <p>
            <label for="text">Text</label>
            <input id="text" name="text" value="${textError}"/>
        </p>

        <p>
            <c:url value="/review/${product.id}" var="placeReviewUrl"/>
            <button formaction="${placeReviewUrl}">Place</button>
        </p>
    </form>

    <table>
        <thead>
        <tr>
            <td>
                Username
            </td>
            <td>
                Rating
            </td>
            <td>
                Text
            </td>
        </tr>
        </thead>
        <c:forEach var="review" items="${reviewes}">
            <tr>
                <td>
                        ${review.userName}
                </td>
                <td>
                        ${review.rating}
                </td>
                <td>
                        ${review.text}
                </td>
            </tr>
        </c:forEach>
    </table>

    <tags:recentlyViewed products="${recentlyViewedProducts}"/>

</tags:master>