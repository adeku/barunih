<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Budget</title>
</head>
<body> -->
	<c:url var="addBudget" value="/accounting/budgets/create" />
	<form:form modelAttribute="budget" method="POST" action="${addBudget}">
		<form:label path="accountId">Account Name</form:label>
			<form:select path="accountId" multiple="false" size="1">
				<form:options items="${accounts}"/>
			</form:select>
			<br /><br />
		<form:label path="amount">Amount</form:label>
			<form:input path="amount"/>
			<br /><br />
		<form:label path="">Monthly Amount</form:label>
		<br />
		<%-- <c:forEach var="i" begin="0" end="11" step="1" varStatus ="status">
			<form:label path="monthlyAmount[${i}]">Month <c:out value="${i+1}" /> </form:label>
				<form:input path="monthlyAmount[${i}]"/>
				<br />
		</c:forEach> --%>
			
		<form:button name="action" value="save">Save</form:button>
			<br/><br/>
	</form:form>
<!-- </body>
</html> -->