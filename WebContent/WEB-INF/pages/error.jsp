<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出了點錯誤</title>
</head>
<body>
	請將下列訊息回覆給作者fallicemoon@gmail.com<br><br>
	http status:<%=response.getStatus() %><br>
	${pageContext.exception}<br>
	<c:forEach items="${pageContext.exception.stackTrace}" var="stackTrace">
		${stackTrace}<br>
	</c:forEach>
	

</body>
</html>