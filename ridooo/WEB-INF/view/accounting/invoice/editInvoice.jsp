<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Invoice Form</title>

</head>
<body>
	<strong>Account Receivable</strong>
	<br />
	<br />
	<c:url var="updateInvoice" value="/accounting/invoices/${id}/updateInvoice" />
	<form:form modelAttribute="addInvoiceForm" method="POST" action="${updateInvoice}">
	<c:if test="${!empty classes}">	
			<tr>
				<th>Send To :</th>								
			</tr>
			<tr>	
			<c:forEach items="${classes}" var="kelas">
				<tr>					
					<td><c:out value="${kelas.grade}"/></td>					
				</tr>
			</c:forEach>	
			</tr>	
	</c:if>
	<br />
	<br />
	<br />
	<form:label path="account1">Description Piutang:</form:label>
		<form:select path="account1" multiple="false" size="1">
			<form:options items="${accounts}"/>
		</form:select>
	<form:label path="amountDeb">Total Amount:</form:label>
		<form:input path="amountDeb" />
		<br/><br/>
	<form:label path="account2">Description Pendapatan:</form:label>
		<form:select path="account2" multiple="false" size="1">
			<form:options items="${accounts}"/>
		</form:select>
	<form:label path="amountCre">Total Amount:</form:label>
		<form:input path="amountCre" />
		<br/><br/>
	
	
		<%-- <form:label path="type">Type:</form:label>	
		<form:select path="type" multiple="false" size="1">
				<form:options items="${type}"/>
		</form:select>
		<br />
		<br />
		
		<form:label path="invoiceTo">Send To:</form:label>
		<form:input path="invoiceTo" />
		<br />
		<br />
		
		<form:label path="itemPurchase">Quantity:</form:label>
		<form:input path="itemPurchase" />
		<br />
		<br /> 
		
		<form:label path="amount">Total Amount:</form:label>
		<form:input path="amount" />
		<br />
		<br />
		
		<strong>Posting</strong><br />
		<form:label path="account1">Description:</form:label>
			<form:select path="account1" multiple="false" size="1">
				<form:options items="${accounts}"/>
			</form:select>
		<form:label path="amountDeb">Total Amount:</form:label>
			<form:input path="amountDeb" />
			<br/><br/>
		<form:label path="account2">Description:</form:label>
			<form:select path="account2" multiple="false" size="1">
				<form:options items="${accounts}"/>
			</form:select>
		<form:label path="amountCre">Total Amount:</form:label>
			<form:input path="amountCre" />
			<br/><br/> --%>
		
		<form:button name="action" value="${save}">Save</form:button>
	</form:form>
</body>
</html>