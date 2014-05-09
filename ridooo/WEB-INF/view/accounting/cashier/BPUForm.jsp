<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<c:url var="checkout" value="/finance/cashier/checkout" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">

		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
				<div class="content-header">
					<span class="fr"> <a class="btn"
						href="/finance/cashier/transactions/create">Create Transaction</a>
					</span>

					<h1>Cashier Checkout Report</h1>
					<p class="subtitle">Generate cashier checkout report</p>

				</div>

				<c:choose>
					<c:when test="${!empty transactionList}">
						<form class="form-horizontal" method="post" action="${checkout}">
							<div class="control-group">
								<label class="control-label" for="input01">Cashier Name</label>
								<div class="controls">
									<a href="#"><c:out value="${cashierName}" /></a>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="input01">Date and Time</label>
								<div class="controls">
									<c:out value="${dateAndTime}" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="prependedInput">Total
									Amount</label>
								<div class="controls">
									<div class="input-prepend">
										<span class="add-on">Rp</span><input class="input-small"
											id="prependedInput" size="20" type="text">
									</div>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="textarea">Note</label>
								<div class="controls">
									<textarea class="input-xxsmall" id="textarea" rows="3"></textarea>
								</div>
							</div>
						</form>





						<form:form modelAttribute="bpuForm" method="POST"
							action="${checkout}" class="form-horizontal">
							${message}
							
								<table class="table table-striped">
								<colgroup>
									<col />
									<col />
									<col class="amount">
									<col class="w50">
								</colgroup>
								<thead>
									<tr>
										<th>ID</th>
										<th>DATE</th>
										<th>REFNUMBER</th>
										<th>AMOUNT</th>
									</tr>
								</thead>
								<c:set var="totalAmount" value="0" />
								<tbody>
									<c:forEach items="${transactionList}" var="transaction"
										varStatus="itemsRow">

										<tr>
											<td><form:label path="transactionIds[${itemsRow.index}]">
													<c:out value="${transaction.id}" />
												</form:label> <form:hidden path="transactionIds[${itemsRow.index}]"
													value="${transaction.id}" /></td>
											<td><c:out value="${transaction.trxDate}" /></td>

											<td><c:out value="${transaction.refNumber}" /></td>

											<td><span class="amount"><c:out
														value="${transaction.amount}" /></span></td>

											<c:set var="totalAmount"
												value="${totalAmount + transaction.amount}" />
									</c:forEach>

								</tbody>
							</table>

							<div class="form-actions">
								<form:button name="action" value="save" class="btn btn-positive">Checkout</form:button>

							</div>


						</form:form>
					</c:when>
					<c:otherwise>
						<div class="empty-state person">
							<h2>No Report Available</h2>
						</div>
						<!-- Nothing to checkout! -->
						<br />
						<c:url var="backToCashier"
							value="/finance/cashier/transactions/create" />
						<a href="${backToCashier}">Back to cashier menu</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<!-- END : CONTENT AREA -->

	</div>
	<div class="clear"></div>
</div>

<%-- <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BPU Form</title>
</head>
<body>
	<c:choose>
		<c:when test="${!empty transactionList}">
			<c:url var="addTransaction" value="/accounting/cashier/checkoutBPU" />
			<form:form modelAttribute="bpuForm" method="POST" action="${addTransaction}">
				${message}
				<strong>BPU Form</strong><br />
				
					<table>
						<tr>
							<th>Trans#</th>
							<th>Date</th>
							<th>RefNumber</th>
							<th>Amount</th>
						</tr>
						<c:set var="totalAmount" value="0" />
						<c:forEach items="${transactionList}" var="transaction" varStatus="itemsRow">
							<tr>
								<td><form:label path="transactionIds[${itemsRow.index}]"><c:out value="${transaction.id}" /></form:label>
									<form:hidden path="transactionIds[${itemsRow.index}]" value="${transaction.id}"/>
								</td>
								<td><c:out value="${transaction.trxDate}" /></td>
								<td><c:out value="${transaction.refNumber}" /></td>
								<td><c:out value="${transaction.amount}" /></td>
								<c:set var="totalAmount" value="${totalAmount + transaction.amount}" />
						</c:forEach>
					</table>
				
				<form:label path="cashierName">Cashier Name : <c:out value="${cashierName}" /> </form:label>
					<form:hidden path="cashierName" value="${cashierName}"/>
					<br />
				<form:label path="dateAndTime">Date and Time : <c:out value="${dateAndTime}" /> </form:label>
					<form:hidden path="dateAndTime" value="${dateAndTime}"/>
					<br />
				<form:label path="totalAmount">Total Amount : <c:out value="${totalAmount}" /> </form:label>
					<form:hidden path="totalAmount" value="${totalAmount}"/>
					<br />
				<form:label path="note">Note:</form:label>
					<form:textarea path="note"/>
				
				<form:button name="action" value="${save}">Save</form:button>
					<br/><br/>
			</form:form>
		</c:when>
		<c:otherwise>
			Nothing to checkout! <br />
			<a href="../../accounting">Back to accounting menu</a>
		</c:otherwise>
	</c:choose>
</body>
</html> --%>