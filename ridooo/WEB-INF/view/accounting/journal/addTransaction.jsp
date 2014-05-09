<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:url var="addTransaction" value="/accounting/journals/addTransaction" />
	<form:form modelAttribute="transaction" method="POST" action="${addTransaction}">
		${message}
		<strong>Journal</strong><br />
		<form:label path="journalNote">Note:</form:label>
			<form:input path="journalNote"/>
		<form:label path="journalType">Type:</form:label>
			<form:select path="journalType" multiple="false" size="1">
				<c:if test="${!empty jTypes }">
					<form:options items="${jTypes}"/>
				</c:if>
			</form:select>
		<form:label path="journalAmount">Amount:</form:label>
			<form:input path="journalAmount"/>
			<br /><br />
			
		<strong>Posting</strong><br />
		<form:label path="accountId1">Description:</form:label>
			<form:select path="accountId1" multiple="false" size="1">
				<form:options items="${accounts}"/>
			</form:select>
		<form:label path="postingAmount1">Amount:</form:label>
			<form:input path="postingAmount1" />
			<br/><br/>
		<form:label path="accountId2">Description:</form:label>
			<form:select path="accountId2" multiple="false" size="1">
				<form:options items="${accounts}"/>
			</form:select>
		<form:label path="postingAmount2">Amount:</form:label>
			<form:input path="postingAmount2" />
			<br/><br/>
		
		<form:button name="action" value="${save}">Save</form:button>
			<br/><br/>
	</form:form>

</body>
</html>