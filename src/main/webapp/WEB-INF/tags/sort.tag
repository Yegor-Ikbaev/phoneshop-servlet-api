<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@attribute name="search" required="false" %>
<%@attribute name="sortBy" required="true" %>

<a href="products?search=${search}&sortBy=${sortBy}&order=asc">&uarr;</a>
<a href="products?search=${search}&sortBy=${sortBy}&order=desc">&darr;</a>
