<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Author</title>
</head>
<body>

<h1>View Author</h1>

<table border='1px'>

<thead>
<tr>
    <th>id</th><th>firstName</th><th>lastName</th><th>books</th>
</tr>
</thead>

<tbody>
<tr>
	<td>${author.id}</td>
	<td>${author.firstName}</td>
	<td>${author.lastName}</td>
	<td>${author.books}</td>
</tr>
</tbody>
</table>

</body>
</html>