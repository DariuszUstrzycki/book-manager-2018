<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@	taglib	prefix="form" uri="http://www.springframework.org/tags/form"	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Person form - simple form</title>
</head>
<body>

<h1>Add a person</h1>

<h3>.../personFormMParam >> Add a person with @RequestParam</h3>
	<form method="POST">
		<input type="text" placeholder='login' name="login"> 
		<input type="password" placeholder='password'	name="password"> 
		<input type="text" placeholder='email' name="email"> 
		<input type="submit">
	</form>
<hr>
	<!-- action='personForm' -->
<h3>.../personFormModelAtt >>Add a person with @ModelAttribute</h3>
	<form:form method='POST' modelAttribute='person'>
		<form:input placeholder='login' path='login' /><br>
		 <form:errors path="login" cssClass="error"	/>  <!-- element="div" -->
		
		<form:password placeholder='password' path="password" /><br>
		<form:errors path="password" cssClass="error" /> 
		<form:input placeholder='email' path="email" /><br>
		<form:errors path="*" cssClass="error" />    <!-- <form:errors path="*" cssClass="error" /> -->
		<input type='submit' value='Add person'>
	</form:form>

</body>
</html>