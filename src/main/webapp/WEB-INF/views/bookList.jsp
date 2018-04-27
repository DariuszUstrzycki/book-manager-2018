<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>${entityName} list</h1>


<form action='${pageContext.request.contextPath}/listBooks' method='GET'>
  Books with rating at least:
  <input type="number" name="min" min="1">
  <input type="submit">
</form>

<h2>${msg}</h2>

<table border='1px'>

<thead>
<tr>
    <th>id</th><th>title</th><th>isbn</th><th>rating</th>
    <th>description</th><th>category</th><th>publisher</th><th>authors</th>
</tr>
</thead>

<tbody>

<c:forEach items='${bookItems}' var='book'>
<tr>
<td><a href='${pageContext.request.contextPath}/delete${entityName}/${book.id}/confirm'>Delete</a>
	<a href='${pageContext.request.contextPath}/update${entityName}/${book.id}'>Update</a>
	<a href='${pageContext.request.contextPath}/view${entityName}/${book.id}'>View</a></td>
	<td>${book.title}</td>
	<td>${book.isbn}</td>
	<td>${book.rating}</td>
	<td>${book.description}</td>
	<td>${book.category.name}</td>
	<td>${book.publisher.name}</td>
	<td>${book.authors}</td>
</tr>
</c:forEach>

</tbody>
</table>


</body>
</html>