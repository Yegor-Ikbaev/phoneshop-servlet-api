<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href="http://fonts.googleapis.com/css?family=Lobster+Two" rel="stylesheet" type="text/css">
    <c:url value="/styles/main.css" var="style"/>
  <link href="${style}" rel="stylesheet">
</head>
<body>
	<tags:header/>
	  <main>
	    <jsp:doBody/>
	  </main>
    <tags:footer/>
</body>
</html>