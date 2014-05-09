<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="bills" value="/finance/bills"></c:url>
<c:url var="getVendors" value="/finance/getvendors" />
<c:url var="addVendor" value="/people/create" />

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
							<h3>Invoice No. ${billId}</h3>
							<p>${oForm.trxDate}</p>
						</div>
						<div class="clear"></div>
					</div>

					<div class="print-content">
						<div class="customer-info">
							<h4>Vendor:</h4>
							<p>
								${oForm.vendorName}  (ID: ${oForm.vendorId})
								<br />
								${oForm.vendorAddr}
							</p>
						</div>
						<div class="price-info">
							<div class="price-field">
								<label>Total Amount</label>
								<span class="price">${oForm.totalBillAmount}</span>
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
					<c:url var="payBill" value="/finance/payments-issued/create/${billId}" />
					<form:form modelAttribute="oForm" action="${payBill}">
						<div class="helper summary summary2">
							<div class="detail border">
								<c:choose>
									<c:when test="${!empty oForm.totalBillAmount}">
										<h2>${oForm.totalBillAmount}</h2>
									</c:when>
									<c:otherwise>
										<h2>0</h2>
									</c:otherwise>
								</c:choose>
								
								<p>Total (Rp.)</p>
							</div>
							
							<form:hidden path="vendorId" value="${oForm.vendorId}" />
							<form:hidden path="vendorName" value="${oForm.vendorName}" />
							<c:choose>
								<c:when test="${oForm.totalRemaining != '0'}">
									<button class="btn btn-positive btn-huge">PAY</button>
								</c:when>
								<c:otherwise>
									<button class="btn btn-positive btn-huge disabled" disabled>PAY</button>
								</c:otherwise>
							</c:choose>
								
						</div>
					</form:form>
					
					<%-- <div class="helper summary summary2">
						<div class="detail border">
							<c:choose>
								<c:when test="${!empty oForm.totalBillAmount}">
									<h2>${oForm.totalAmount}</h2>
								</c:when>
								<c:otherwise>
									<h2>0</h2>
								</c:otherwise>
							</c:choose>
							
							<p>Total (Rp.)</p>
						</div>
						<button class="btn print btn-huge">PRINT</button>
					</div> --%>
					
					<h1>Bill ${billId}</h1>
					<p class="subtitle">Last edited on ${oForm.lastModified} by ${oForm.modifiedByName}</p>
				</div>
				
				<div class="field fl view no-print">
					<label>Vendor</label>
					<div class="controls pills-autocomplete-field">
						<div class="input-append">
							<c:url var="vendorUrl" value="/people/vendors" />
							<a href="${vendorUrl}/${oForm.vendorId}/profile">${oForm.vendorName}</a>
<%-- 								<form:input path="vendorId" class="pills-autocomplete pills-autocomplete-form input-very-small" /><span class="add-on"><i class="icon-pencil"></i></span> --%>
<!-- 								<input type="text" id="vendor" class="pills-autocomplete pills-autocomplete-form input-very-small" /><span class="add-on"><i class="icon-pencil"></i></span> -->
						</div>
					</div>
				</div>
				
				<div class="field fl view no-print">
					<label>Bill No.</label>
					<div class="input-append">
						${oForm.refNumber}
<%-- 							<form:input path="refNumber" class="input-very-small" /> --%>
<!-- 							<input type="text" class="input-very-small" /> -->
					</div>
				</div>
				
				<div class="field fl view no-print">
					<label>Date</label>
					<div class="input-append">
						${oForm.trxDate}
<%-- 							<form:input path="trxDate" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> --%>
<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
					</div>
				</div>
				
				<div class="field fl view no-print">
					<label>Due Date</label>
					<div class="input-append">
						${oForm.dueDate}
<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
<%-- 							<form:input path="dueDate" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> --%>
					</div>
				</div>
				
				<table class="table table-striped">
				<colgroup>
					<col />
					<col class="w100" />
					<col class="w100" />
					<col class="w100" />
				<colgroup>
				
				<thead>
					<tr>
						<th>ITEM</th>
						<th class="align-right">QTY</th>
						<th class="align-right">UNIT PRICE</th>
						<th class="align-right">AMOUNT (Rp.)</th>
					</tr>
				</thead>
				
				<%-- <tfoot>
					<tr>
						<td colspan="2"></td>
						<td><h4>Total (Rp.)</h4></td>
						<c:choose>
							<c:when test="${!empty oForm.totalBillAmount}">
								<td><span class="amount total">${oForm.totalBillAmount}</span><form:hidden path="totalBillAmount" value="${oForm.totalBillAmount}" /></td>
							</c:when>
							<c:otherwise>
								<td><span class="amount total">0</span><form:hidden path="totalBillAmount"/></td>
							</c:otherwise>
						</c:choose>
						
					</tr>
				</tfoot> --%>
				
				<tbody>
					<%-- <c:forEach items="${oForm.detailIds}" var="detailId" varStatus="status">
						<input type="hidden" name="detailIds[${status.index}]" value="${detailId}">
					</c:forEach> --%>
					<c:if test="${!empty oForm.helper_element_id_1}">
						<c:forEach items="${oForm.helper_element_id_1}" var="schoolId" varStatus="status">
							<tr>
								<td class="no-padding">
									<div class="mac-description" style=""><h4>${oForm.autocomplete_name[status.index]}</h4>
										<div class="general" style="">
											<%-- <input type="hidden" name="helper_element_id_1[${status.index}]" value="${oForm.helper_element_id_1[status.index]}">
											<input type="hidden" name="helper_element_name_1[${status.index}]" value="${oForm.helper_element_name_1[status.index]}"> --%>
											<span class="mac-detail" data-id="0" name="helper_element_label_1[${status.index}]" href="javascript:void(0);">${oForm.helper_element_name_1[status.index]}</span> - 
											<%-- <input type="hidden" name="helper_element_id_2[${status.index}]" value="${oForm.helper_element_id_2[status.index]}">
											<input type="hidden" name="helper_element_name_2[${status.index}]" value="${oForm.helper_element_name_2[status.index]}"> --%>
											<span class="mac-detail" data-id="0" name="helper_element_label_2[${status.index}]" href="javascript:void(0);">${oForm.helper_element_name_2[status.index]}</span>
										</div>
										<!-- <div class="mac-actions" style="">
											<a href="javascript:void(0);" data-id="0" class="mac-detail"><i class="icon-list-alt"></i></a>
											<a href="javascript:void(0);" class="mac-close">×</a>
										</div> -->
										<%-- <input type="hidden" name="autocomplete_id[${status.index}]" value="${oForm.autocomplete_id[status.index]}">
										<input type="hidden" name="autocomplete_name[${status.index}]" value="${oForm.autocomplete_name[status.index]}"> --%>
									</div>
<!-- 										<input type="hidden" id="morra_autocomplete_1"> -->
								</td>
								<td class="no-padding"><div style=""><span class="amount">${oForm.quantities[status.index]}</span></div></td>
								<td class="no-padding"><div style=""><span class="amount">${oForm.unitPrices[status.index]}</span></div></td>
								<td class="no-padding"><div style=""><span class="amount totalCount">${oForm.amounts[status.index]}</span></div></td>
<%-- 									<input name="amounts[${status.index}]" type="hidden" class="totalCount" value="${oForm.amounts[status.index]}"> --%>
							</tr>
						</c:forEach>
					</c:if>
					<!-- <tr>
						<td class="no-padding"><input id="1" type="text" class="morra_autocomplete" /></td>
						<td class="no-padding"><input name="quantities" type="text" class="amount count quantity" disabled /></td>
						<td class="no-padding"><input name="unitPrices" type="text" class="amount count price" disabled /></td>
						<td class="no-padding"><span class="amount totalCount"></span><input name="amounts" type="hidden" class="totalCount" /></td>
					</tr> -->
				</tbody>
				</table>
			</div>
			<!-- END : CONTENT AREA -->
		</div>
	</div>
	<div class="clear"></div>
	
</div>

