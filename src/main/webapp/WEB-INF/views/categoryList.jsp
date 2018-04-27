<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Category list</title>
</head>
<body>

<h1>Category list</h1>

<table border='1px'>
<thead>
<tr>
    <th>id</th><th>name</th><th>description</th><th>books</th>
</tr>
</thead>
<tbody>

<c:forEach items='${categoryItems}' var='category'>
<tr>
<td><a href='${pageContext.request.contextPath}/deleteCategory/${category.id}/confirm'>Delete</a>
	<a href='${pageContext.request.contextPath}/updateCategory/${category.id}'>Update</a><br>
	<a href='${pageContext.request.contextPath}/viewCategory/${category.id}'>View</a></td>
	<td>${category.name}</td>
	<td>${category.description}</td>
	<td>${category.books}</td>
</tr>
</c:forEach>

</tbody>
</table>

</body>
</html>