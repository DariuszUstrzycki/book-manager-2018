<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Category form</title>
</head>
<body>

<h1>Category form</h1>

<form:form action='${pageContext.request.contextPath}/saveCategory' method='POST' modelAttribute='category'>
<form:hidden placeholder='id' path="id"/>
<form:input placeholder='name' path="name"/>
<form:errors path='name' cssClass="error" element='div'></form:errors>
<form:input placeholder='description' path="description"/>
<form:errors path='description' cssClass="error" element='div'></form:errors>
<input type='submit' value='Save'>
</form:form>

</body>
</html>