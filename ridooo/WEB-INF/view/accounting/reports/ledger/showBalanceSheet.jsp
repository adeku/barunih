<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
	table,th,td{
		border:2px solid;
	}
</style>
<title>Balance Sheet</title>
</head>
<body>
	<c:url var="showBalanceSheet" value="/accounting/reports/balances/showBalanceSheet" />
	<form:form modelAttribute="account" method="POST" action="${showBalanceSheet}">
		<form:label path="category">Category:</form:label>
			<form:select path="category" multiple="false" size="1">
				<form:options items="${categoryOptions}"/>
			</form:select>
		<form:button name="action" value="show">Show Balance Sheet</form:button>
	</form:form>
	
	<c:if test="${!empty postings}">
		<table>
			<tr>
				<th>Ref Number</th>				
				<th>Name</th>		
				<th>Amount</th>
										
			</tr>
			<c:set var="total" value="0"/>
			<c:forEach items="${postings}" var="posting">
				<tr>					
					<td><c:out value="${posting.code}"/></td>
					<td><c:out value="${posting.name}"/></td>	
								
					<c:choose>
						<c:when test="${posting.balance != 0}">
							<td><fmt:formatNumber value="${posting.balance}" minFractionDigits="2" maxFractionDigits="2" /></td>
						</c:when>
						<c:otherwise>
							<td></td>
						</c:otherwise>
					</c:choose>
					<c:set var="total" value="${total + posting.balance}"/>
					
					<%-- <c:set var="balance" value="${balance + posting.amount}"/> --%>
					
<%-- 					<td><c:out value="${balance}"/></td> --%>
				</tr>
			</c:forEach>
			<td></td> <td></td>			
					<td><fmt:formatNumber value="${total}" type="currency" currencyCode="IDR" /></td>					
		</table>		
	</c:if>
</body>
</html>