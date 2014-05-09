<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<c:url var="depositLink" value="/finance/cashier/deposit"/>
<c:url var="generalSales" value="/finance/generalsales"/>
<c:url var="regForm" value="/finance/registration"/>


<div class="content">
			
	<!-- GRID CONTAINER -->
	<div class="grid-container">
				
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
			<form>
					<c:set var="test" value="${0 }"/>
					<c:set var="count" value="${0 }"/>
					<c:forEach items="${vCashierDashboard}" var="vCash">
					 	<c:set var="totalTest" value="${totalTest + vCash.amount }"/>
					 	<c:set var="totalCount" value="${totalCount + vCash.countId }"/>
					</c:forEach>
					
				<div class="content-header no-border">
						<div class="helper border summary summary2 helper-cashier">
							<h2>${totalTest }</h2>
							<p>CASH ON HAND (Rp.)</p>
						</div>
						<c:forEach items="${vCashierDashboard}" var="cashierSet">
							<c:set var="test" value="${cashierSet.cashierName }"></c:set>
						</c:forEach>
						<img class="image avatar avatar-small" src="${pageContext.request.contextPath}/img/no-person.png" /> 
						<h1>${name }</h1>
							<p class="subtitle">Cashier Dashboard</p>
						<div class="clear"></div>
				</div>
				
				
				<span class="second-headercashiergreen" >Today's Transactions</span>
				<span class="second-headercashier">${totalCount }</span>				
				<span class="second-linkcashier"><a href="${generalSales }" class="alt-link">Transaction</a> </span>
				<span class="second-headercashier">${countReg }</span>
				<span class="second-linkcashier"><a href="${regForm }" class="alt-link">Registration Forms Sales</a></span>
				<span><a href="${depositLink }" class="btn btn-positive fr" style="margin-bottom: 15px">Checkout</a></span>
				
				<table class="table table-striped">
					<colgroup>
						<col>
						<col class="w200">
						<col class="w100">
					</colgroup>

					<thead>
						<tr>
							<th>Transaction</th>
							<th>Payment Method</th>
							<th class="align-text">Amount</th>
						</tr>
					</thead>
					
					<tbody id="tbodyData">

						<c:forEach items="${vCashierDashboard}" var="cashier">
							<c:url var="detailsStaff" value="/academic/staffs/${cashier.partyId}/details"></c:url>
							<c:url var="detailsTransaction" value="/finance/invoices/${cashier.id }/view"/>
							<tr id="trData${cashier.id}">
								<td><h4>										
										<a href="${detailsTransaction }">PAYMENT <c:out value="${cashier.id} " /></a>
									</h4>
									<span><a href="${detailsStaff }" class="alt-link">${cashier.cashierName }</a></span>
								</td>
								<td>
									<label class="second-linkcashier">${cashier.paymentMethod}</label>
									<span>${cashier.accName } <c:if test="${!empty cashier.paymentRefNo}"> - ${cashier.paymentRefNo }</c:if></span>
								</td>
								<td><span class="amount">${cashier.amount}</span></td>
																	
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</form>
			</div>
			<!-- END : CONTENT AREA -->
		</div>
	</div>
	<div class="clear"></div>
			
</div>