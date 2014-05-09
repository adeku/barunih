<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="content">
	
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
				<c:url var="checkout" value="/finance/cashier/checkout" />
				<h1>Create Transaction</h1>
				 	<p class="subtitle">text</p>
<%-- 				<a href="${checkout}">Checkout</a> --%>
				<h3>${message}</h3>
	<%-- 			${errors} --%>
				<c:url var="addTransaction" value="/finance/cashier/transactions/create" />
				<form:form modelAttribute="transaction" method="POST" action="${addTransaction}" class="form-horizontal">
					<cform:input label="Ref Number:" path="transactionRefNumber"/>
					<%-- <form:label path="transactionRefNumber">Ref Number:</form:label>
						<form:input path="transactionRefNumber"/><br /> --%>
						
					<cform:input label="Date:" path="transactionDate"/>
					<%-- <form:label path="transactionDate">Date:</form:label>
						<form:input path="transactionDate"/><br /> --%>
						
					<%-- <form:label path="transactionType">Type:</form:label>
						<form:select path="transactionType" multiple="false" size="1">
							<c:if test="${!empty jTypes }">
								<form:options items="${jTypes}"/>
							</c:if>
						</form:select> --%>
						
					<cform:select label="Account Name:" path="accountId" options="${accounts}"/>
					<%-- <form:label path="accountId">Account Name:</form:label>
						<form:select path="accountId" multiple="false" size="1">
							<form:options items="${accounts}"/>
						</form:select><br /> --%>
						
					<cform:input label="Amount:" path="transactionAmount"/>
					<%-- <form:label path="transactionAmount">Amount:</form:label>
						<form:input path="transactionAmount"/>
						<br /><br /> --%>
						
					<cform:input label="Note:" path="transactionNote"/>
					<%-- <form:label path="transactionNote">Note:</form:label>
						<form:textarea path="transactionNote"/> --%>
						
					<div class="form-actions">	
						<form:button name="action" value="save" class="btn btn-positive">Save</form:button>
						<c:url var="cancel" value="/finance/cashier/checkout" />
						<a href="${cancel}" class="btn cancel">Cancel</a>
					</div>
				</form:form>
			</div>
		</div>
		<!-- END : CONTENT AREA -->
		
	</div>
	<div class="clear"></div>
</div>

<%-- <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cashier Transaction Form</title>
</head>
<body>
	<c:url var="addTransaction" value="/finance/cashier/transactions/create" />
	<form:form modelAttribute="transaction" method="POST" action="${addTransaction}">
		${message}
		<strong>Transaction</strong><br />
		<form:label path="transactionRefNumber">Ref Number:</form:label>
			<form:input path="transactionRefNumber"/><br />
		<form:label path="transactionDate">Date:</form:label>
			<form:input path="transactionDate"/><br />
		<form:label path="transactionType">Type:</form:label>
			<form:select path="transactionType" multiple="false" size="1">
				<c:if test="${!empty jTypes }">
					<form:options items="${jTypes}"/>
				</c:if>
			</form:select>
		<form:label path="accountId">Account Name:</form:label>
			<form:select path="accountId" multiple="false" size="1">
				<form:options items="${accounts}"/>
			</form:select><br />
		<form:label path="transactionAmount">Amount:</form:label>
			<form:input path="transactionAmount"/>
			<br /><br />
		<form:label path="transactionNote">Note:</form:label>
			<form:textarea path="transactionNote"/>
				
		<form:button name="action" value="${save}">Save</form:button>
			<br/><br/>
	</form:form>
</body>
</html> --%>