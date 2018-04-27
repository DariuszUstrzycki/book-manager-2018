<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@	taglib	prefix="form" uri="http://www.springframework.org/tags/form"	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PersonDetailsForm</title>
</head>
<body>

<h1>Personal details form</h1>
<form:form   method='POST' modelAttribute='personDTO' > 
<form:password placeholder='form:password' path="password"/>
<form:input placeholder='login' path="login"/>
<form:input placeholder='email' path="email"/>
<form:input placeholder='first name' path="firstName"/>
<form:input placeholder='last name' path="lastName"/>
<hr>

<!-- 

Male: <form:radiobutton placeholder='gender' path="gender" value='M'/>
Female: <form:radiobutton placeholder='gender' path="gender" value='F'/>
Other: <form:radiobutton placeholder='gender' path="gender" value='O'/><br>
 -->

Gender (radiobuttons items="${genderItems}")<br>
<form:radiobuttons items="${genderItems}" path="gender" />
<hr>

<form:textarea rows="2" cols="50" path="notes" placeholder='notes(textarea)' /><br>
<hr>

Mailing list (checkbox - nie ma value podanej?!)
<form:checkbox path="mailingList" placeholder='mailing list'/><br>
<hr>

Hobbies (checkboxES; items="d{hobbyItems} itemLabel='name'	itemValue='id'): 
    <form:checkboxes items="${hobbyItems}" path="hobbies"
    itemLabel="name"	itemValue="id"/>
<hr>

Country (select items="d{countryItems} lub rozbudowane o form:option(s)): 
	<form:select path="country"> 
	<form:option value="-"	label="--Please	Select--"/>
	<form:options items="${countryItems}"/>
</form:select>
<hr>

Programming skills (select items="d{progSkillsItems} multiple="true"):
  	<form:select items="${progSkillsItems}" path="programmingSkills"  multiple="true"/>
<hr>

<input type="submit" value="save">	


</form:form>


</body>
</html>