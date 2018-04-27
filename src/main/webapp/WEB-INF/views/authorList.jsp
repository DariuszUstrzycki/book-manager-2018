<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Author list</title>
</head>
<body>

<h1>Author list</h1>

<table border='1px'>

<thead>
<tr>
    <th>id</th><th>firstName</th><th>lastName</th><th>books</th>
</tr>
</thead>

<tbody>

<c:forEach items='${authorItems}' var='author'>
<tr>
<td><a href='${pageContext.request.contextPath}/deleteAuthor/${author.id}/confirm'>Delete</a>
	<a href='${pageContext.request.contextPath}/updateAuthor/${author.id}'>Update</a>
	<a href='${pageContext.request.contextPath}/viewAuthor/${author.id}'>View</a></td>
	<td>${author.firstName}</td>
	<td>${author.lastName}</td>
	<td>${author.books}</td>
</tr>
</c:forEach>

</tbody>
</table>



</body>
</html>