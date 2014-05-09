<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url var="imageCiputra" value="/img/logo-splash.png"></c:url>
<script>
	$(document).ready(function(){
		$('span.number').currency();
		$('td.total_amount').currency();
		$('span.price').currency();
	});
</script>

<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
				<!-- PRINT START -->
				<div class="print-layout">
					<div class="print-head">
						<div class="print-title">
							<img alt="Ciputra Logo" src="${imageCiputra }" />
							<h1>${schoolName }</h1>
							<p>${schoolAddr }</p>
						</div>
						<div class="print-info">
							<h3>Payment No. ${transactionId}</h3>
							<p>${oForm.trxDate}</p>
						</div>
						<div class="clear"></div>
					</div>

					<div class="print-content">
						<div class="customer-info">
							<h4>Vendor:</h4>
							<p>
								${oForm.customerName}  (ID: ${oForm.customerId})
								<br />
								${oForm.customerAddr} 
							</p>
						</div>
						<div class="price-info">
							<div class="price-field">
								<label>Total Amount</label>
								<span class="price">${oForm.totalAmount}</span>
							</div>
							<div class="price-field">
								<label>Cashier</label>
								<span>${oForm.createdByName}</span>
							</div>
						</div>
						<div class="clear"></div>
					</div>
				</div>
				<!-- PRINT END -->
				
				<div class="content-header">
					<%-- <div class="helper border summary summary2">
						<c:choose>
							<c:when test="${!empty oForm.totalAmount}">
								<h2>${oForm.totalAmount}</h2>
							</c:when>
							<c:otherwise>
								<h2>0</h2>
							</c:otherwise>
						</c:choose>
						
						<p>Total (Rp.)</p>
					</div> --%>
					
					<div class="helper summary summary2">
						<div class="detail border">
						<c:choose>
							<c:when test="${!empty oForm.totalAmount}">
								<h2>${oForm.totalAmount}</h2>
							</c:when>
							<c:otherwise>
								<h2>0</h2>
							</c:otherwise>
						</c:choose>
						<p>TOTAL AMOUNT (Rp.)</p>
						</div>
						<button class="btn print btn-huge">PRINT</button>
					</div>
					
					<h1>Payment Received ${transactionId}</h1>
					<p class="subtitle">Last edited on ${oForm.lastModified} by ${oForm.modifiedByName}</p>
				</div>
				
				<div class="field fl view no-print">
					<label>Customer</label>
					<div class="controls pills-autocomplete-field">
						<div class="input-append">
							<c:choose>
								<c:when test="${oForm.studentId != 0}">
									<c:url var="studentProfile" value="/academic/students/${oForm.studentId}/details" />
									<a href="${studentProfile}">${oForm.customerName}</a>
								</c:when>
								<c:otherwise>
									${oForm.customerName}
								</c:otherwise>
							</c:choose>
							
						</div>
					</div>
				</div>
				
				<div class="field fl view no-print">
					<label>Date</label>
					<div class="input-append">
						${oForm.trxDate}
					</div>
				</div>
				
				<div class="field fl view no-print">
					<label>Payment Method</label>
					<div class="input-append">
						${oForm.paymentMethod}
					</div>
				</div>
				
				<c:if test="${oForm.paymentMethod != 'Cash'}">
					<c:if test="${oForm.paymentMethod == 'Check'}">	
						<div class="field fl view no-print">
							<label>Check No.</label>
							<div class="input-append">
								${oForm.checkNumber}
							</div>
						</div>
					</c:if>
					<div class="field fl view no-print">
						<label>Account</label>
						<div class="input-append">
							${oForm.accountName}
						</div>
					</div>
				</c:if>
				
			
				<table class="table table-striped">
				
					<colgroup>
						<col />
						<col class="w150" />
						<col class="w150" />
					<colgroup>
					
					<thead>
						<tr>
							<th>ITEM</th>
							<th class="align-right">AMOUNT</th>
							<th class="align-right">RECEIVE AMOUNT</th>
						</tr>
					</thead>
					
					<tfoot>
						<tr>
							<td></td>
							<td><h4>Total (Rp.)</h4></td>
							<td><span class="amount total">${oForm.totalAmount}</span></td>
						</tr>
					</tfoot>
					
					
					<tbody>
						<c:if test="${!empty oForm.paidAmounts}">
							<c:forEach items="${oForm.paidAmounts}" var="paidAmount" varStatus="status">
								<tr>
									<td>
										<h4>
											<c:choose>
												<c:when test="${!empty oForm.invoiceIds[status.index]}">
													<c:url var="invoiceView" value="/finance/invoices/${oForm.invoiceIds[status.index]}/view"></c:url>
													<a href="${invoiceView}">${oForm.invoiceTitles[status.index]}</a>
												</c:when>
												<c:otherwise>
													${oForm.invoiceTitles[status.index]}
												</c:otherwise>
											</c:choose>
										</h4>
										<span>${oForm.accountNames[status.index]}</span>
									</td>
									<td>
										<span class="amount">${oForm.amounts[status.index]}</span>
									</td>
									<td><span class="amount">${paidAmount}</span></td>
								</tr>
								
								
								<%-- <tr>
									<td class="no-padding">
										<div class="mac-description" style=""><h4>${oForm.autocomplete_name[status.index]}</h4>
											<div class="general" style="">
												<span class="mac-detail" data-id="0" name="helper_element_label_1[${status.index}]" href="javascript:void(0);">${oForm.helper_element_name_1[status.index]}</span> 
											</div>
										</div>
									</td>
									<td><div style=""><span class="amount">${oForm.quantities[status.index]}</span></div></td>
									<td><div style=""><span class="amount">${oForm.unitPrices[status.index]}</span></div></td>
									<td><div style=""><span class="amount totalCount">${oForm.amounts[status.index]}</span></div></td>
								</tr> --%>
							</c:forEach>
						</c:if>
					</tbody>
					
				</table>
			</div>
			<!-- END : CONTENT AREA -->
		</div>
	</div>
	<div class="clear"></div>
	
</div>