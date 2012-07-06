<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>Message : ${message}</h1>
	&nbsp;&nbsp;&nbsp;
	<a href="<c:url value="/j_spring_security_logout" />"> Logout</a>
</body>
</html>