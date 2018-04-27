<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Constraint violations</title>
</head>
<body>
<h1>Validation errors</h1>

<h3>Book errors:</h3>
<c:forEach var='error' items='${bookViolations}'>
${error.propertyPath} :  ${error.invalidValue} : ${error.message} <br>
</c:forEach>

<h3>Author errors:</h3>
<c:forEach var='error' items='${authorViolations}'>
${error.propertyPath} :  ${error.invalidValue} : ${error.message} <br>
</c:forEach>

<h3>Publisher errors:</h3>
<c:forEach var='error' items='${publisherViolations}'>
${error.propertyPath} :  ${error.invalidValue} : ${error.message} <br>
</c:forEach>

</body>
</html>