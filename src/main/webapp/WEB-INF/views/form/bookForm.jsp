<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Book form</title>
</head>
<body>

<h1>Book form</h1>

<form:form action='${pageContext.request.contextPath}/save${entityName}' method='POST' modelAttribute='book'>
<form:hidden path="id"/>
<form:input placeholder='isbn' path="isbn"/>
<form:errors path='isbn' cssClass='error'></form:errors>
<form:input placeholder='title' path="title"/>
<form:errors path='title' cssClass='error'></form:errors>
<form:input placeholder='rating from 1 to 10' path="rating"/>
<form:errors path='rating' cssClass='error'></form:errors>
<form:input placeholder='description' path="description"/>
<form:errors path='description' cssClass='error'></form:errors>
<form:input placeholder='pages(min 2)' path="pages"/>
<form:errors path='pages' cssClass='error'></form:errors>
<hr>
Category (path="category.id"):
<form:select path="category.id" items="${categoryItems}" itemLabel='name' itemValue='id' ></form:select>
<form:errors path='category.id' cssClass='error'></form:errors>
<hr>

<hr>
Publisher (path="publisher.id"):
<form:select path="publisher" items="${publisherItems}" itemLabel='name' itemValue='id' ></form:select>
<form:errors path='publisher' cssClass='error'></form:errors>
<hr>

Authors
<form:select path="authors" items="${authorItems}" itemLabel='lastName' itemValue='id' multiple="true" ></form:select>
<form:errors path='authors' cssClass='error'></form:errors>

<hr>
<input type='submit' value='Save'>
</form:form>


</body>
</html>