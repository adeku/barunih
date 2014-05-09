<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
		<c:when test="${!empty sessionScope.u9988u}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu List</title>
</head>
<body>
	Menu List
	<br />
	<br />
	<br />
	<div>Check Timer</div>
	<div id="timeIn">Timer</div>
	<br />

	<c:choose>
		<c:when test="${!empty sessionScope.u9988u}">
		${sessionScope.u9988u} 
		<br />
			<br />
			<a href="../academic">Academic Service</a>
			<br />
			<br />
			<a href="../accounting">Accounting Service</a>
			<br />
			<br />
			<a href="../reg/312">Edit Profile</a>
			<br />
			<br />
			<a href="../companies/add">Add Company</a>
			<br />
			<br />
			<a href="../companies/addUser">Add Company's User</a>
			<br />
			<br />
			<a href="../requestMerge/1">request Merger</a>
			<br />
			<br />
			<a href="../companies/addSubCompany">Add Sub Company's</a>
			<br />
			<br />
			<a href="../addScholl/1">Add School</a>&nbsp;&nbsp;&nbsp;<a href="../editShool/1">Edit School</a>
			<br />
			<br />
			<a href="../logout">Logout</a>
		</c:when>
		<c:otherwise>
			<c:redirect url="/login/1" />
		</c:otherwise>
	</c:choose>
	</c:choose>



${viewTimeLoad}
	${viewJavaScriptFunction}
</body>
</html>