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
					<div class="grid-content vrs-invendelorv">
						<!-- PRINT START -->
						<div class="print-layout">
							<div class="print-head">
								<div class="print-title">
									<img alt="Ciputra Logo" src="${imageCiputra }" />
									<h1>${schoolName }</h1>
									<p>${schoolAddr }</p>
								</div>
								<div class="print-info">
									<h3>Deposit no. ${transactionId}</h3>
									<p>${trxDate}</p>
								</div>
								<div class="clear"></div>
							</div>

							<div class="print-content">
								<div class="customer-info">
									<h4>Bank Name:</h4>
									<p>
										${bankName}
									</p>
								</div>
								<div class="price-info">
									<div class="price-field">
										<label>Total Amount</label>
										<span class="price">${totalAmount}</span>
									</div>
									<div class="price-field">
										<label>Cashier</label>
										<span>${cashierName}</span>
									</div>
								</div>
								<div class="clear"></div>
							</div>
						</div>
						<!-- PRINT END -->
						
						<div class="vrs-invenitemv-ch content-header">			
							<!-- <div class="helper">
								<a href="receipt.html" class="btn fr print"><i class="icon-print"></i>&nbsp; Print</a>
							</div> -->
							
							
							<div class="helper summary summary2">
								<div class="detail border">
									<c:choose>
										<c:when test="${!empty totalAmount}">
											<h2>${totalAmount}</h2>
										</c:when>
										<c:otherwise>
											<h2>0</h2>
										</c:otherwise>
									</c:choose>
									
									<p>Total (Rp.)</p>
								</div>
								<button class="btn print btn-huge">PRINT</button>
							</div>
							
							<h1>Deposit ${transactionId}</h1>
						</div>
						
						<div class="list list-horizontal span4 vrs-no-ml">
							<ul>
								<li>
									<p class="title">Deposit to</p>
									<p>${bankName}</p>
								</li>
								<li>
									<p class="title">Date</p>
									<p>${trxDate}</p>
								</li>
								<li>
									<p class="title">Cashier</p>
									<c:url var="staffUrl" value="/academic/staffs" />
									<p><a href="${staffUrl}/${cashierId}/details">${cashierName}</a></p>
								</li>
							</ul>
						</div>
						
						<table class="table table-striped">
							<colgroup>
								<col>
<%-- 								<col class="w150"> --%>
								<col class="w150">
							</colgroup>
							
							<thead>
								<tr>
									<th>Item</th>
<!-- 									<th>Payment Method</th> -->
									<th class="align-right">Amount (RP.)</th>
								</tr>
							</thead>
							
							<tbody>
								<c:forEach items="${details}" var="detail" varStatus="status">
									<tr>
										<td>
											<h4>${detail.title}</h4>
											<span>${partyNames[status.index]}</span>
										</td>
<%-- 	        							<td><label>${paymentMethods[status.index]}</label></td> --%>
										<td><span class="amount">${detail.unitPrice}</span></td>
	        						</tr>
        						</c:forEach>
							</tbody>
						</table>
						
					</div>
					<!-- END : CONTENT AREA -->
				</div>
			</div>
			<div class="clear"></div>
			
		</div>