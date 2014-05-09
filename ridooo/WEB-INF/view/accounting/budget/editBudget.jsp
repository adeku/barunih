<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript">
	$(document).ready(
			function() {
				var amount = $('#amount').val();
				var total = 0;

				$("#budget").validate({
					onfocusout : false,
					onkeyup : false,
				});

				jQuery.validator.addMethod("greaterThanZero",
						function(value, element, params) {
							total = 0;
							for (i = 0; i < 12; i++) {
								total = total
										+ parseInt($('#monthlyAmount' + i)
												.val());
							}

							console.log(total);

							return this.optional(element)
									|| (parseFloat(value) == total);
						}, "Amount is not equal with total of monthly amount");

				$('#amount').rules('add', {
					greaterThanZero : true
				});

				$('.monthlyAmount').keyup(
						function() {
							// 			alert(amount);
							total = 0;
							for (i = 0; i < 12; i++) {
								total = total
										+ parseInt($('#monthlyAmount' + i)
												.val());
								// 				console.log(total);
							}
							$('#amount').val(total);
						});
				$('.divide').click(function() {
					amount = $('#amount').val();
					var value = Math.floor(amount / 12);
					var totalCount=0;
					for (i = 0; i < 12; i++) {
						$('#monthlyAmount' + i).val(value);
						totalCount+=value;
					}
					 $('#amount').val(totalCount);
				});

			});
</script>

<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">

		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
				<div class="content-header">
					<c:choose>
						<c:when test="${diffaction == 'add' }">
							<h1>Add Budgets</h1>
							<p class="subtitle">Add budget to be allocated</p>
						</c:when>
						<c:otherwise>
							<h1>Edit Budgets</h1>
							<p class="subtitle">Edit budget to be allocated</p>
						</c:otherwise>
					</c:choose>
				</div>
				<h3>${message}</h3>
				<%-- 			${errors} --%>
				<c:url var="actBudget" value="${action}" />
				<form:form modelAttribute="budget" method="POST"
					action="${actBudget}" class="form-horizontal validate" commandName="budget">
					<%-- <form:label path="accountId">Account Name:</form:label>
						<form:select path="accountId" multiple="false" size="1">
							<form:options items="${accounts}"/>
						</form:select> --%>
					<spring:bind path="accountId">
						<cform:select label="Account Name" path="accountId"
							options="${accounts}" selected="${status.value}" />
					</spring:bind>

					<%-- <form:label path="amount">Amount:</form:label>
						<form:input path="amount"/> --%>

					<spring:bind path="amount">
						<cform:input label="Amount *" path="amount" value="${status.value}"
							required="true" number="true" help="Budget amount in Rupiah"
							append="<a href='#' class='divide'>Distribute Equally</a>" />
						<div class="controls">
							<form:errors path="amount" class="error" />
						</div>
					</spring:bind>

					<div class='control-group'>
						<form:label path="monthlyAmount[0]" class="control-label">Monthly Amount</form:label>
					</div>
					<c:forEach var="i" begin="0" end="11" step="1" varStatus="stat">
						<%-- <form:label path="monthlyAmount[${i}]">Month <c:out value="${i+1}" /> </form:label>
							<form:input path="monthlyAmount[${i}]"/> --%>
						<spring:bind path="monthlyAmount[${i}]">
							<cform:input label="Month ${i+1}" path="monthlyAmount[${i}]"
								array="true" value="${status.value}" clazz="monthlyAmount" required="false" />
						</spring:bind>
					</c:forEach>
					<%-- 				<cform:input path="amount" label="File" type="file" /> --%>
					<div class="form-actions">
						<form:button name="action" value="save" class="btn btn-positive">Save</form:button>
						<c:url var="cancelBudget" value=".." />
						<a href="${cancelBudget}" class="btn cancel">Cancel</a>
					</div>
				</form:form>
			</div>
		</div>
		<!-- END : CONTENT AREA -->

	</div>
	<div class="clear"></div>
</div>

<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Budget</title>
</head>
<body>
	<c:url var="editBudget" value="/accounting/budgets/${budgetId}/edit" />
	<form:form modelAttribute="budget" method="POST" action="${editBudget}">
		<form:label path="accountId">Account Name:</form:label>
			<form:select path="accountId" multiple="false" size="1">
				<form:options items="${accounts}"/>
			</form:select>
			<br /><br />
		<form:label path="amount">Amount:</form:label>
			<form:input path="amount"/>
			<br /><br />
		<form:label path="">Monthly Amount:</form:label>
		<br />
		<c:forEach var="i" begin="0" end="11" step="1" varStatus ="status">
			<form:label path="monthlyAmount[${i}]">Month <c:out value="${i+1}" /> </form:label>
				<form:input path="monthlyAmount[${i}]"/>
				<br />
		</c:forEach>
			
		<form:button name="action" value="save">Save</form:button>
			<br/><br/>
	</form:form>
</body>
</html> --%>