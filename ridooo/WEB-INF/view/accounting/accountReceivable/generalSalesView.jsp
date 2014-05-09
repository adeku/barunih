<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
	
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
				<div class="content-header">
					<div class="helper border summary">
						<c:choose>
							<c:when test="${!empty oForm.totalAmount}">
								<h2>${oForm.totalAmount}</h2>
							</c:when>
							<c:otherwise>
								<h2>0</h2>
							</c:otherwise>
						</c:choose>
						
						<p>Total (Rp.)</p>
					</div>
					
					<h1>General Sales ${transactionId}</h1>
					<p class="subtitle">Created on ${oForm.created} by ${oForm.createdByName}</p>
				</div>
				
				<div class="field fl view">
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
				
				<div class="field fl view">
					<label>Date</label>
					<div class="input-append">
						${oForm.trxDate}
					</div>
				</div>
				
				<div class="field fl view">
					<label>Cash Sales No.</label>
					<div class="input-append">
						${oForm.refNumber}
					</div>
				</div>
				
				<div class="field fl view">
					<label>Cashier</label>
					<div class="input-append">
						<c:url var="cashierProfile" value="/academic/staffs/${oForm.cashierId}/details" />
						<a href="${cashierProfile}">${oForm.cashierName}</a>
					</div>
				</div>
			
				<table class="table table-striped">
				
					<colgroup>
						<col/>
						<col class="amount"/>
						<col class="amount"/>
						<col class="w100 amount"/>
					</colgroup>
					
					<thead>
						<tr>
							<th>Item</th>
							<th class="align-right">Qty</th>
							<th class="align-right">Unit Price</th>
							<th class="align-right">Paid Amount (Rp.)</th>
						</tr>
					</thead>
					
					<tfoot>
						<tr>
							<td colspan="2"></td>
							<td><h4>Total (Rp.)</h4></td>
							<td><span class="amount total">0</span></td>
						</tr>
					</tfoot>
					
					
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
									<td><div style=""><span class="amount">${oForm.quantities[status.index]}</span></div></td>
									<td><div style=""><span class="amount">${oForm.unitPrices[status.index]}</span></div></td>
									<td><div style=""><span class="amount totalCount">${oForm.amounts[status.index]}</span></div></td>
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