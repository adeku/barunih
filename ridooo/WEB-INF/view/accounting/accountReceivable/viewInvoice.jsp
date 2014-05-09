<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url var="imageCiputra" value="/img/ciputralogo.png"></c:url>

<script>
	$(document).ready(function(){
		$('span.number').currency();
		$('td.total_amount').currency();
		$('span.price').currency();
	});
</script>

<div class="bill content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
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
							<h3>Invoice No. ${invoiceId}</h3>
							<p>${oForm.trxDate}</p>
						</div>
						<div class="clear"></div>
					</div>

					<div class="print-content">
						<div class="customer-info">
							<h4>Customer:</h4>
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
					
					<c:choose>
						<c:when test="${oForm.transactionType == 'Invoice'}">
							<h1>Invoice ${invoiceId}</h1>
						</c:when>
						<c:otherwise>
							<h1>Cash Sales ${invoiceId}</h1>
						</c:otherwise>
					</c:choose>
					<p class="subtitle">Last edited on ${oForm.lastModified} by ${oForm.modifiedByName}</p>
				</div>
				
				<div class="field fl view no-print">
					<label>Customer</label>
					<div class="controls pills-autocomplete-field">
						<div class="input-append">
							<c:url var="customers" value="/people/customers"></c:url>
							<a href="${customers}/${oForm.customerId}/profile">${oForm.customerName}
						</div>
					</div>
				</div>
				
				<div class="field fl view no-print">
					<label>Invoice No.</label>
					<div class="input-append">
						${oForm.refNumber}
					</div>
				</div>
				
				<div class="field fl view no-print">
					<label>Date</label>
					<div class="input-append">
						${oForm.trxDate}
					</div>
				</div>
				
				<div class="field fl view no-print">
					<label>Due Date</label>
					<div class="input-append">
						${oForm.dueDate}
					</div>
				</div>
				
				<table id="single" class="table table-striped">
					<colgroup>
						<col />
						<col class="w50" />
						<col class="w150" />
						<col class="w150" />
						<col class="w150" />
					<colgroup>
					
					<thead>
						<tr>
							<th>ITEM</th>
							<th class="align-right">QTY</th>
							<th class="align-right">UNIT PRICE</th>
							<th class="align-right">DISCOUNT (Rp.)</th>
							<th class="align-right">AMOUNT (Rp.)</th>
						</tr>
					</thead>
					
					<tbody>
						<c:if test="${!empty oForm.helper_element_id_1}">
							<c:forEach items="${oForm.helper_element_id_1}" var="schoolId" varStatus="status">
								<tr>
									<td class="no-padding">
										<div class="mac-description" style=""><h4>${oForm.autocomplete_name[status.index]}</h4>
											<div class="general" style="">
												<span class="mac-detail" data-id="0" name="helper_element_label_1[${status.index}]" href="javascript:void(0);">${oForm.helper_element_name_1[status.index]}</span>
											</div>
										</div>
									</td>
									<td class="no-padding"><div style=""><span class="amount">${oForm.quantities[status.index]}</span></div></td>
									<td class="no-padding"><div style=""><span class="amount">${oForm.unitPrices[status.index]}</span></div></td>
									<td class="no-padding"><div style=""><span class="amount">${oForm.discounts[status.index]}</span></div></td>
									<td class="no-padding"><div style=""><span class="amount totalCount">${oForm.amounts[status.index]}</span></div></td>
								</tr>
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