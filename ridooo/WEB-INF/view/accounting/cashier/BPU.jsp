<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url var="imageCiputra" value="/img/logo-splash.png"></c:url>

<script>
	$(document).ready(function(){
		$('span.number').currency();
		$('td.total_amount').currency();
		$('span.price').currency();
	});
</script>

<!-- CONTENT -->
		<div class="content">
			
			<!-- GRID CONTAINER -->
			<div class="grid-container">
				
				<!-- START : CONTENT AREA -->
				<div class="grid-full">
					<div class="grid-content">
						<div class="content-header no-border">
							<div class="helper">
								<c:url var="makeDeposit" value="/finance/cashier/deposits/create" />
								<a href="${makeDeposit}" class="btn fr">Make Deposit</a>
							</div>
							<h1>Deposit</h1>
    						<p class="subtitle">Showing all deposit information</p>
						</div>
						<br class="clear" />
						
						<table class="table table-striped">
							<colgroup>
								<col>
								<col class="w100">
								<col class="w150">
							</colgroup>
							<thead>
								<tr>
									<th>Transaction</th>
									<th class="align-left">TRX DATE</th>
									<th class="align-right">AMOUNT (Rp.)</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${transactions}" var="transaction" varStatus="status">
									<tr>
	        							<td>
	        								<c:url var="viewDeposit" value="/finance/cashier/deposits/${transaction.id}/view"></c:url>
											<h4><a href="${viewDeposit}" class="vrs-invensto-tra">Deposit ${transaction.id}</a></h4>
											<span>${bankNames[status.index]}</span>
										</td>
										<td class="align-left">${trxDates[status.index]}</td>
										<td class="align-right">
											<span class="amount">${transaction.amount}</span>
										</td>
	        						</tr>
        						</c:forEach>
								<!-- <tr>
        							<td>
										<h4><a href="#" class="vrs-invensto-tra">Deposit 1234</a></h4>
										<span>Bank bing-bung</span>
									</td>
									<td class="align-left">11/09/2012</td>
									<td class="align-right">
										<span class="amount">150.000</span>
									</td>
        						</tr>
								<tr>
        							<td>
										<h4><a href="#" class="vrs-invensto-tra">Deposit 1234</a></h4>
										<span>Bank bing-bung</span>
									</td>
									<td class="align-left">11/09/2012</td>
									<td class="align-right">
										<span class="amount">150.000</span>
									</td>
        						</tr>
								<tr>
        							<td>
										<h4><a href="#" class="vrs-invensto-tra">Deposit 1234</a></h4>
										<span>Bank bing-bung</span>
									</td>
									<td class="align-left">11/09/2012</td>
									<td class="align-right">
										<span class="amount">150.000</span>
									</td>
        						</tr> -->
							</tbody>
						</table>
						
					</div>
					<!-- END : CONTENT AREA -->
				</div>
			</div>
			<div class="clear"></div>
			
		</div>