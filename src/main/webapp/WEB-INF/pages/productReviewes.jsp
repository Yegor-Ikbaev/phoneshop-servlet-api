<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
<form method="post">
    <table>
        <thead>
        <tr>
            <td>
                Product
            </td>
            <td>
                Username
            </td>
            <td>
                Rating
            </td>
            <td>
                Text
            </td>
            <td>
                Approve
            </td>
        </tr>
        </thead>
        <c:forEach var="review" items="${reviewes}">
            <tr>
                <td>
                        ${review.product.description}
                </td>
                <td>
                        ${review.userName}
                </td>
                <td>
                        ${review.rating}
                </td>
                <td>
                        ${review.text}
                </td>
                <td>
                    <c:url value="/reviewes/approve/${review.id}" var="approveUrl"/>
                    <button formaction="${approveUrl}">Approve</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>
</tags:master>