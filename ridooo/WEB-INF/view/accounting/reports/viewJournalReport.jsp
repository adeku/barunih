<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>Journal Report</title>
</head>
<body>
	<c:if test="${!empty journalPosts}">
		<table>
			<tr>
				<th>Trans#</th>
				<th>Type</th>
				<th>Date</th>
				<th>RefNumber</th>
				<th>Name</th>
				<th>Note</th>
				<th>Account</th>
				<th>Debit</th>
				<th>Credit</th>
			</tr>
			<c:set var="debit" value="0"/>
			<c:set var="totalDebit" value="0"/>
			<c:set var="credit" value="0"/>
			<c:set var="totalCredit" value="0"/>
			<c:set var="transactionId" value="0"/>
			<c:forEach items="${journalPosts}" var="post" varStatus="status">
				<tr>
					<c:choose>
						<c:when test="${post.transactionId != transactionId}">
							<c:set var="transactionId" value="${post.transactionId}" />
							<td><c:out value="${post.transactionId}"/></td>
							<td><c:out value="${post.type}"/></td>
							<td><c:out value="${post.date}"/></td>
							<td><c:out value="${post.refNumber}"/></td>
							<td><c:out value="${post.name}"/></td>
							<td><c:out value="${post.note}"/></td>
						</c:when>
						<c:otherwise>
							<td></td> <td></td><td></td><td></td><td></td><td></td>
						</c:otherwise>
					</c:choose>
<!-- 					<td> -->
<%-- 						<c:if test="${journalPosts.transactionId != transactionId}"> --%>
							
							
<%-- 						</c:if> --%>
<!-- 					</td> -->
					
					<td><c:out value="${post.accountCode}"/>-<c:out value="${post.accountName}"/></td>
					<td>
						<c:if test="${post.debit != 0}">
							<c:out value="${post.debit}"/>
							<c:set var="debit" value="${debit + post.debit}"/>
						</c:if>
					</td>
					<td>
						<c:if test="${post.credit != 0}">
							<c:out value="${post.credit}"/>
							<c:set var="credit" value="${credit + post.credit}"/>
						</c:if>
					</td>
<%-- 					<c:choose> --%>
<%-- 						<c:when test="${post.debit != 0}"> --%>
<%-- 							<td><c:out value="${post.debit}"/></td> --%>
							
<!-- 							<td></td> -->
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<!-- 							<td></td> -->
<%-- 							<td><c:out value="${post.amount}"/></td> --%>
							
<%-- 						</c:otherwise> --%>
<%-- 					</c:choose> --%>
					<c:if test="${journalPosts[status.index+1].transactionId != transactionId}">
						</tr>
						<tr>
							<td colspan="7"></td>
							<td><c:out value="${debit}"/></td>
							<td><c:out value="${credit}"/></td>
							<c:set var="totalDebit" value="${totalDebit + debit}"/>
							<c:set var="totalCredit" value="${totalCredit + credit}"/>
							<c:set var="debit" value="0"/>
							<c:set var="credit" value="0"/>
					</c:if>
					
<%-- 					<c:set var="balance" value="${transaction + transaction.debit - transaction.credit}"/> --%>
<%-- 					<td><fmt:formatNumber value="${transaction}" type="currency" currencyCode="IDR" /></td> --%>
<%-- 					<td><c:out value="${balance}"/></td> --%>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="7">Total</td>
				<td><c:out value="${totalDebit}"/></td>
				<td><c:out value="${totalCredit}"/></td>
			</tr>
		</table>
	</c:if>
	
	<a href="../../../accounting">Back to Accounting Menu</a>
</body>
</html>