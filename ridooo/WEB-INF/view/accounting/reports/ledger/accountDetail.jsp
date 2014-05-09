<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url var="reconcile" value="/finance/generalledger/${accountId}/view/" />
<script>

	$(document).ready(function() {
		if($('#optionDate').val()!=5)
			$('#date-range').hide(0);
	});
	
	function reconcileTransaction(transactionId) {
		$.ajax({
				type : "POST",
				url : "${reconcile}/"+transactionId+"/reconcile",
				beforeSend: function() {
					core.loading('main');
				}
			})
			.done(
				function() {
					core.loading.destroy();
					var change = $('span.reconcile#'+transactionId)
					change.removeClass('reconcile');
					change.addClass('unreconcile');
					change.addClass('confirm');
				});
	}
	
	function unreconcileTransaction(transactionId) {
		$.ajax({
				type : "POST",
				url : "${reconcile}/"+transactionId+"/unreconcile",
				beforeSend: function() {
					core.loading('main');
				}
			})
			.done(
				function() {
					core.loading.destroy();
					var change = $('span.unreconcile#'+transactionId)
					change.removeClass('unreconcile');
					change.addClass('reconcile');
					change.removeClass('confirm');
				});
	}
	
	$(document).ready(function(){
		<c:if test="${oForm.accountCategory == 'Bank'}">
			$('#select_all').click(function(){
				$(this).parents('table').find('td input[type="checkbox"]').trigger('click');
			});
		</c:if>
		$('body').on('click','.reconcile',function(){
			var id = $(this).attr('id');
			reconcileTransaction(id);
		});
		$('body').on('click','.unreconcile',function(){
			var id = $(this).attr('id');
			unreconcileTransaction(id);
		});
		
	});
	function filterByDate() {
		if (document.form.dateFrom.value.length > 0
				&& document.form.dateTo.value.length>0) {
			document.form.submit();
		}
	}
	
	function showCustomDate() {
		if ($('#optionDate').val()==5){
			$('#date-range').fadeIn(400);
		}else{
			$('#date-range').fadeOut(400);
			filterByDate() ;
		}
	}
</script>

<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
	
		<div class="grid-full">
			<div class="grid-content">
				<c:url var="postForm" value="/finance/generalledger/${accountId}/view" />
				<form:form modelAttribute="oForm" method="POST" action="${postForm}" class="validate">
					<div class="content-header">
						<%-- <div class="helper border summary">
							<c:choose>
								<c:when test="${!empty oForm.totalBillAmount}">
									<h2>${oForm.totalBillAmount}</h2>
								</c:when>
								<c:otherwise>
									<h2>0</h2>
								</c:otherwise>
							</c:choose>
							
							<p>Total (Rp.)</p>
							<form:button name="save" value="add" class="btn btn-positive btn-huge">SAVE</form:button>
						</div> --%>
						
						<h1>General Ledger</h1>
						<p class="subtitle">${oForm.accountName} - ${oForm.accountNumber}</p>
						
					</div>
					
					${viewTimeLoad}
					
					<%-- <div class="field fl view">
						<label>Account Number</label>
						<div class="input-append">
							${oForm.accountNumber}
							<form:input path="refNumber" class="input-very-small" />
<!-- 							<input type="text" class="input-very-small" /> -->
						</div>
					</div>
					
					<div class="field fl view">
						<label>Account Name</label>
						<div class="input-append">
							${oForm.accountName}
							<form:input path="refNumber" class="input-very-small" />
<!-- 							<input type="text" class="input-very-small" /> -->
						</div>
					</div>
						<div class="field fl"></div> --%>
						
					<div class="field fl view">
						<label>Starting Balance (Rp.)</label>
						<div class="input-append">
							<span class="ledger-amount amount">${oForm.startBalance}</span>
						</div>
					</div>
					<div class="field fl view">
						<label>Ending Balance (Rp.)</label>
						<div class="input-append">
							<span class="ledger-amount amount">${oForm.lastBalance}</span>
						</div>
					</div>
					
					
					<div class="fr ledger-button">
						<label>&nbsp;</label>
						<form:button name="action" value="generate" class="btn btn-positive btn-small">Generate Report</form:button>
					</div>
					
					<div class="field fr view-input">
						<label>School</label>
						<div class="input-append">
							<form:select path="companyId" class="input-very-small" items="${schoolOptions}" />
<!-- 								<p class="help-block">End Date</p> -->
						</div>
					</div>
					
					<div id="date-range">
						<div class="field fr view-input">
							<label>End Date</label>
							<div class="input-append">
								<form:input path="endDate" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span>
	<!-- 								<p class="help-block">End Date</p> -->
							</div>
						</div>
						<div class="field fr view-input">
							<label>Start Date</label>
							<div class="input-append">
								<form:input path="startDate" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span>
	<!-- 								<p class="help-block">Start Date</p> -->
							</div>
						</div>
					</div>
					<div class="field fr view-input">
							<label>Date Option</label>
							<div class="input-append">
								<select id="optionDate" name="optionDate" class="input-very-small datepicker" readonly="" onChange="showCustomDate();">
									<c:forEach items="${pdOption}" var="date">
										<c:choose>
										    <c:when test="${date[0].equalsIgnoreCase(dateOption)}">
												<option value="${date[0]}" selected>${date[1]}</option>
										    </c:when>
										    <c:otherwise>
												<option value="${date[0]}">${date[1]}</option>
										    </c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
					</div>
					<div class="field fr"></div>
					
					<table class="table table-striped">
						<colgroup>
							<col class="w100" />
							<col />
							<col class="amount" />
							<col class="amount" />
							<col class="amount" />
							<c:if test="${oForm.accountCategory == 'Bank'}">
								<col class="w20" />
							</c:if>
						</colgroup>
						
						<thead>
							<tr>
								
								<th>DATE</th>
								<th>TRANSCATION</th>
								<th class="align-right">DEBIT (Rp.)</th>
								<th class="align-right">CREDIT (Rp.)</th>
								<th class="align-right">BALANCE (Rp.)</th>
								<c:if test="${oForm.accountCategory == 'Bank'}">
<!-- 									<th><input type="checkbox" id="select_all"></th> -->
									<th></th>
								</c:if>
							</tr>
						</thead>
						
						<%-- <tfoot>
							<tr>
								<td></td>
								<td colspan="2" class="align-right"><h4>Last Balance (Rp.)</h4></td>
								<c:choose>
									<c:when test="${!empty oForm.lastBalance}">
										<td><span class="amount total">${oForm.lastBalance}</span></td>
									</c:when>
									<c:otherwise>
										<td><span class="amount total">0</span></td>
									</c:otherwise>
								</c:choose>
								
							</tr>
						</tfoot> --%>
						
						<tbody>
							<%-- <c:forEach items="${oForm.detailIds}" var="detailId" varStatus="status">
								<input type="hidden" name="detailIds[${status.index}]" value="${detailId}">
							</c:forEach> --%>
							<%-- <tr>
								<td>Start Balance</td>
								<td></td>
								<td>
									<span class="amount total">${oForm.startBalance}</span>
								</td>
								<td>
								</td>
							</tr> --%>
							<c:if test="${!empty oForm.balances}">
								<c:forEach items="${oForm.balances}" var="balance" varStatus="status">
									<tr>
										<td><div style=""><span>${oForm.trxDate[status.index]}</span></div></td>
										<td>
											<c:choose>
												<c:when test="${oForm.types[status.index] != 'Pay In' && oForm.types[status.index] != 'Pay Out'}">
													<h4>${oForm.types[status.index]} ${oForm.transactionIds[status.index]}</h4>
												</c:when>
												<c:otherwise>
													<h4>Payment ${oForm.transactionIds[status.index]}</h4>
												</c:otherwise>
											</c:choose>
<%-- 											<div class="mac-description" style=""><h4>${oForm.title[status.index]}</h4> --%>
												<%-- <div class="general" style="">
													<input type="hidden" name="helper_element_id_1[${status.index}]" value="${oForm.helper_element_id_1[status.index]}">
													<input type="hidden" name="helper_element_name_1[${status.index}]" value="${oForm.helper_element_name_1[status.index]}">
													<span class="mac-detail" data-id="0" name="helper_element_label_1[${status.index}]" href="javascript:void(0);">${oForm.helper_element_name_1[status.index]}</span> - 
													<input type="hidden" name="helper_element_id_2[${status.index}]" value="${oForm.helper_element_id_2[status.index]}">
													<input type="hidden" name="helper_element_name_2[${status.index}]" value="${oForm.helper_element_name_2[status.index]}">
													<span class="mac-detail" data-id="0" name="helper_element_label_2[${status.index}]" href="javascript:void(0);">${oForm.helper_element_name_2[status.index]}</span>
												</div> --%>
												<%-- <input type="hidden" name="autocomplete_id[${status.index}]" value="${oForm.autocomplete_id[status.index]}">
												<input type="hidden" name="autocomplete_name[${status.index}]" value="${oForm.autocomplete_name[status.index]}"> --%>
<!-- 											</div> -->
	<!-- 										<input type="hidden" id="morra_sautocomplete_1"> -->
										</td>
										
										<td>
											<div style="">
												<span class="amount">
													<c:if test="${!empty oForm.debit[status.index]}">
														${oForm.debit[status.index]}
													</c:if>
												</span>
											</div>
										</td>
										<td>
											<div style="">
												<span class="amount">
													<c:if test="${!empty oForm.credit[status.index]}">
														${oForm.credit[status.index]}
													</c:if>
												</span>
											</div>
										</td>
										<td>
											<span class="amount">
												<c:if test="${!empty balance}">
													${balance}
												</c:if>
											</span>
										</td>
										
										<c:if test="${oForm.accountCategory == 'Bank'}">
											<%-- <td>
												<c:choose>
													<c:when test="${oForm.status[status.index] == true}">
														<form:checkbox path="checked[${status.index}]" disabled="true" />
													</c:when>
													<c:otherwise>
														<form:checkbox path="checked[${status.index}]" />
													</c:otherwise>
												</c:choose>
											</td> --%>
											<td>
												<form:hidden path="transactionIds[${status.index}]" value="${oForm.transactionIds[status.index]}"/>
												<c:choose>
													<c:when test="${oForm.status[status.index] == true}">
<%-- 														<a href="javascript:void(0);" class="unreconcile" id="${oForm.transactionIds[status.index]}"> --%>
															<span class="unreconcile rounded-label label-reconcile confirm" id="${oForm.transactionIds[status.index]}">R</span>
<!-- 														</a> -->
													</c:when>
													<c:otherwise>
<%-- 														<a href="javascript:void(0);" class="reconcile" id="${oForm.transactionIds[status.index]}"> --%>
															<span class="reconcile rounded-label label-reconcile" id="${oForm.transactionIds[status.index]}">R</span>
<!-- 														</a> -->
													</c:otherwise>
												</c:choose>
											</td>
										</c:if>
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
				</form:form>
			</div>
			<!-- END : CONTENT AREA -->
		</div>
	</div>
	<div class="clear"></div>
	
</div>

