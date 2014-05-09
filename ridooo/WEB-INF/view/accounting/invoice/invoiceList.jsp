<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">
	
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
				<div class="content-header">
					<h1>Invoices</h1>
					<p class="subtitle">Showing all invoices for all customers<p>
				</div>
			
				<c:if test="${!empty invoices}">
					<table>
						<tr>
						<th>Transaction ID</th>
						<th>To</th>
						<th>Total Amount</th>
						</tr>					
						<c:set var="count" value="0" scope="page" />
					
						<c:forEach items="${invoices}" var="invoice">
							<tr>
								<%--<td><c:out value="${invoice.partNumber}"/></td> --%>
								<td><c:out value="${invoice.refId}"/></td>
								<td>SMP1</td>	
								<td><c:out value="${invoice.amount}"/></td>
							</tr>
						</c:forEach>
					</table>
				</c:if> 
		
			</div>
		</div>
	</div>
</div>