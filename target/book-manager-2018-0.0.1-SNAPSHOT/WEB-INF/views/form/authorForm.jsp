<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Author form</title>
</head>
<body>

<h1>Author form</h1>

<form:form action='${pageContext.request.contextPath}/save${entityName}' method='POST' modelAttribute='author'>

<form:input placeholder='first name' path="firstName"/>
<form:hidden path="id"/>
<form:errors path='firstName' cssClass="error"></form:errors>
<form:input placeholder='last name' path="lastName"/>
<form:errors path='lastName' cssClass="error"></form:errors>
<form:input  placeholder='year of birth' path="yearOfBirth"/>
<form:errors path='yearOfBirth' cssClass="error"></form:errors>
<hr>
<input type='submit' value='Save'>
</form:form>

</body>
</html>