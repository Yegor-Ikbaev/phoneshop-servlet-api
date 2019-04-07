<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<header>


		<c:url value="/" var="productList"/>
		<a href="${productList}">
			<c:url value="/images/logo.svg" var="imageUrl"/>
	      	<img src="${imageUrl}"/>
	      	PhoneShop
	    </a>
		<c:url value="/cart" var="cartUrl"/>
		<a href="${cartUrl}">
			Cart
		</a>
	</header>	