<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">

		<!-- START : CONTENT AREA -->
		<div class="grid2">
			<h1>File Library</h1>
			<p align="right">
				${test} <a href="classroom/create" class="btn"
					style="right: auto; color: #000000">Create New File</a>
			</p>
			<br />
			<c:if test="${!empty showData}">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Account ID</th>
							<th>Amount</th>
							<th>Monthly Amount</th>
							<th>Control</th>
						</tr>
					</thead>

					<c:forEach items="${showData}" var="showData">
						<tbody>
							<tr>
								<td><c:out value="${showData.accountName}" /></td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</c:if>			
			<p align="right">
				${test} <a href="classroom/create" class="btn"
					style="right: auto; color: #000000">Create New File</a>
			</p>
		</div>
		<div class="grid1 last">&nbsp;</div>
		<!-- END : CONTENT AREA -->

	</div>
	<div class="clear"></div>
</div>

<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Budget List</title>
</head>
<body>
	<c:if test="${!empty budgets}">
		<table>
			<tr>
				<th>Account ID</th>
				<th>Amount</th>
				<th>Monthly Amount</th>
				<th>Control</th>
			</tr>
	
			<c:forEach items="${budgets}" var="budget">
				<tr>
					<td><c:out value="${budget.accountName}"/></td>
					<td><c:out value="${budget.amount}"/></td>
					<td>
						<c:forEach items="${budget.monthlyAmount}" var="amount" varStatus="status">
						<c:out value="${status.index + 1}"/>:<c:out value="${amount}"/><br />
						</c:forEach>
					</td>
					<td><a href="budgets/${budget.id}/edit">Edit</a> <a href="budgets/${budget.id}/delete">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html> --%>