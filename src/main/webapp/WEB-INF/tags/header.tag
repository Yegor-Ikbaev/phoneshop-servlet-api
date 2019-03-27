<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<header>

		<c:url value="/images/logo.svg" var="imageUrl"/>
		<c:url value="/" var="productList"/>

		<a href="${productList}">
	      <img src="${imageUrl}"/>
	      PhoneShop
	    </a>

		<p>Welcome to Expert-Soft training!</p>
	</header>	