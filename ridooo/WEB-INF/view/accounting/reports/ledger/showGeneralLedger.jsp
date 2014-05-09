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
<title>General Ledger</title>
</head>
<body>
	<c:url var="showGeneralLedger" value="/accounting/reports/ledgers/showGeneralLedger" />
	<form:form modelAttribute="account" method="POST" action="${showGeneralLedger}">
		<form:label path="id">Description:</form:label>
			<form:select path="id" multiple="false" size="1">
				<form:options items="${accounts}"/>
			</form:select>
		<form:button name="action" value="show">Show General Ledger</form:button>
	</form:form>
	
	<c:if test="${!empty postings}">
		<table>
			<tr>
				<th>Type</th>
				<th>Date</th>
				<th>Ref Number</th>	
<!-- 				<th>Party Id</th>				 -->
<!-- 				<th>Name</th>		 -->
				<th>Debit</th>
				<th>Kredit</th>
				<th>Balance</th>
			</tr>
			<c:set var="balance" value="0"/>
			<c:forEach items="${postings}" var="posting">
				<tr>
					<td><c:out value="${posting.type}"/></td>
					<td><c:out value="${posting.date}"/></td>
					<td><c:out value="${posting.code}"/></td>
<%-- 					<td><c:out value="${posting.partyId}"/></td>					 --%>
					<c:choose>
						<c:when test="${posting.debit != 0}">
							<td><fmt:formatNumber value="${posting.debit}" minFractionDigits="2" maxFractionDigits="2" /></td>
						</c:when>
						<c:otherwise>
							<td></td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${posting.credit != 0}">
							<td><fmt:formatNumber value="${posting.credit}" minFractionDigits="2" maxFractionDigits="2" /></td>
						</c:when>
						<c:otherwise>
							<td></td>
						</c:otherwise>
					</c:choose>
					
					<c:set var="balance" value="${balance + posting.debit - posting.credit}"/>
					<td><fmt:formatNumber value="${balance}" type="currency" currencyCode="IDR" /></td>
<%-- 					<td><c:out value="${balance}"/></td> --%>
				</tr>
			</c:forEach>			
		</table>
	</c:if>
</body>
</html>