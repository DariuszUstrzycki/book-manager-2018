<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View book</title>
</head>
<body>
<h1>View Book</h1>

<table border='1px'>

<thead>
<tr>
    <th>id</th><th>title</th><th>isbn</th><th>rating</th>
    <th>description</th><th>publisher</th><th>authors</th>
</tr>
</thead>

<tbody>
<tr>
	<td>${book.id}</td>
	<td>${book.title}</td>
	<td>${book.isbn}</td>
	<td>${book.rating}</td>
	<td>${book.description}</td>
	<td>${book.publisher.name}</td>
	<td>${book.authors}</td>
</tr>
</tbody>

</table>

</body>
</html>