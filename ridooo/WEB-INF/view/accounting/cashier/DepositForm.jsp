<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<c:url var="checkout" value="/finance/cashier/checkout" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url var="getBankAccount" value="/finance/getbankaccounts" />
<script type="text/javascript">
var total = 0;
$(document).ready(function(){
// 	var data = [{id: 1, name: "Ganteng"}, {id: 2, name: "Dummy 2"}, {id: 3, name: "Dummy 3"}];
	core.pills_autocomplete({
		data: '${getBankAccount}',
		hintText: "Bank Name",
		target_element: '#bankAccountId',
	});
// 	core.pills_autocomplete("${getBankAccount}", "Bank Name", void(0), '#bankAccountId', void(0));
	
	var flag = true;
	
// 	$('#select_all').click(function(){		
// 		total = 0;
		
// 		$(this).parents('table').find('td input[type="checkbox"]').trigger('click');
// 		if(flag == true){
			
// 			$(this).parents('table').find('td input[type="checkbox"]').attr('checked', 'checked');
// 			flag = false;
// 		}else{
// 			console.log('asd');
// 			flag = true;
// 			total = 0;
// 			$(this).parents('table').find('td input[type="checkbox"]').removeAttr('checked');
// 			$('.detail h2').text(total);
// 			$('#totalAmount').val(total);
// 		}
		
		
// 	});
	
	
// 	$('.check').click(function(){
// 		total = 0;
// 		for(i = 0;i < $('.check').length;i++){
// 			console.log($('#checked'+i).val());
// 			if($('#checked'+i+'1').attr('checked') == "checked"){
// 				total += parseInt($('.amount#'+i).text());
// 				console.log('count:'+total);
// 			}
// 		}
// 		$('.detail h2').text(total);
// 		$('#totalAmount').val(total);
// 		console.log(total);
// 	});
	
// 	$('.check').click(function(){
// 		if(flag == true){
// 			total = total + parseInt($(this).parents('tr').find('.amount').text());
	
// 			$('.detail h2').text(total);
// 			$('#totalAmount').val(total);
// 		}
// 		else if($(this).attr('checked') == "checked"){
// 			console.log("total before:"+total)
// 			total = total + parseInt($(this).parents('tr').find('.amount').text());
// 			console.log("total after:"+total)
// 			$('.detail h2').text(total);
// 			$('#totalAmount').val(total);
// 		}
// 		else{
// 			console.log("total before:"+total)
// 			total = total + parseInt($(this).parents('tr').find('.amount').text());
// 			console.log("total after:"+total)
// 			$('.detail h2').text(total);
// 			$('#totalAmount').val(total);
// 		}
			
// 	});
	
	var selectflag = false;
	
	$('#select_all').click(function(){
		total = 0;
		if(selectflag == true){
			selectflag = false;
			$(this).parents('table').find('td input[type="checkbox"]').removeAttr('checked');
// 			total = 0;
			
		}
		else{
			selectflag = true;
			$(this).parents('table').find('td input[type="checkbox"]').attr('checked', 'checked');
// 			total = 0;
			for(i = 0;i < $('.check').length;i++){
// 	 			console.log($('#checked'+i).val());
//  				total += parseInt($('.amount#'+i).text());
//  				console.log('count:'+total);
				total = $('.amount2').sumValues();
	 		}
// 			$('.detail h2').text(total);
// 			$('#totalAmount').val(total);
		}
		$('.detail h2').text(total);
		$('.detail h2').currency();
		$('#totalAmount').val(total);
	});
	
	$('.check').click(function(){
		console.log(total);
		if(selectflag == true){
			selectflag = false;
			$('#select_all').removeAttr('checked');
			
			total = total - parseInt($(this).parents('tr').find('.amount2').val());
	
// 			$('.detail h2').text(total);
// 			$('#totalAmount').val(total);
		}
		else{
			if($(this).attr('checked') == "checked"){
// 				console.log("total before:"+total)
				total = total + parseInt($(this).parents('tr').find('.amount2').val());
// 				console.log("total after:"+total)
// 				$('.detail h2').text(total);
// 				$('#totalAmount').val(total);
			}
			else{
// 				console.log("total before:"+total)
				total = total - parseInt($(this).parents('tr').find('.amount2').val());
// 				console.log("total after:"+total)
// 				$('.detail h2').text(total);
// 				$('#totalAmount').val(total);
			}
		}
		$('.detail h2').text(total);
		$('.detail h2').currency();
		$('#totalAmount').val(total);
	});
});
	
</script>
<!-- CONTENT -->
		<div class="content">
			
			<!-- GRID CONTAINER -->
			<div class="grid-container">
			
				<!-- START : CONTENT AREA -->				
				<div class="grid-full">
					<div class="grid-content">
						<c:url var="deposit" value="/finance/cashier/deposits/create" />
						<form:form modelAttribute="bpuForm" method="POST" action="${deposit}" class="">
							<div class="content-header">
								<div class="helper summary">
									<div class="detail border">
										<h2>0</h2>
										<form:hidden path="totalAmount"/>
										<p>TOTAL (Rp.)</p>
									</div>
									<c:choose>
										<c:when test="${!empty bpuForm.transactionIds}">
											<form:button name="save" value="add" class="btn btn-positive btn-huge">SAVE</form:button>
										</c:when>
										<c:otherwise>
											<a href="javascript:void(0);" class="btn btn-positive btn-huge disabled">SAVE</a>
										</c:otherwise>
									</c:choose>
								</div>
							
								<h1>Make Deposit</h1>
								<p class="subtitle">Deposit funds from cashiers into bank account</p>
							</div>
							
							<c:choose>
								<c:when test="${empty bpuForm.transactionIds}">
									<div class="empty-state person">
										<h2>No transactions available to deposit</h2>
									</div>
								</c:when>
						
								<c:otherwise>
						
									<div class="field fl">
										<label>Deposit to</label>
										<div class="controls pills-autocomplete-field">
											<div class="input-append">
												<form:input path="bankAccountId" class="pills-autocomplete pills-autocomplete-form input-small" /><span class="add-on"><i class="icon-pencil"></i></span>
		<!-- 										<input type="text" id="vendor" class="pills-autocomplete pills-autocomplete-form input-small" /><span class="add-on"><i class="icon-pencil"></i></span> -->
											</div>
										</div>
									</div>
									
									<div class="field fl">
										<label>Date</label>
										<div class="input-append">
											<form:input path="trxDate" class="datepicker input-small" /><span class="add-on"><i class="icon-calendar"></i></span>
		<!-- 									<input type="text" class="datepicker input-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
										</div>
									</div>
						
									<table class="table table-striped">
										
										<colgroup>
											<col class="w20" />
											<col />
											<col class="w150"/>
											<col class="amount"/>
										</colgroup>
										
										<thead>
											<tr>
												<th><input type="checkbox" id="select_all" /></th>
												<th>Item</th>
												<th>Payment Method</th>
												<th class="align-right">Amount (Rp.)</th>
											</tr>
										</thead>
										
										<tbody>
											<c:forEach items="${bpuForm.transactionIds}" var="transactionId" varStatus="itemsRow">
												<tr>
														<td>
			<%-- 												${itemsRow.index} --%>
															<form:checkbox path="checked[${itemsRow.index}]" class="check" />
			<%-- 												<input type="checkbox" id="checked${itemsRow.index}" class="check" value="123" /> --%>
														</td>
														<td>
															<form:hidden path="transactionIds[${itemsRow.index}]" value="${transactionId}"/>
															<h4><a href="#">Payment <c:out value="${transactionId}" /></a></h4>
															<span><c:out value="${bpuForm.customerNames[itemsRow.index]}"/></span>
														</td>
														<td>
															<label><c:out value="${bpuForm.paymentMethods[itemsRow.index]}"/></label>
															<span>
																<c:choose>
																	<c:when test="${!empty bpuForm.paymentAccountNames[itemsRow.index]}">
																		<c:out value="${bpuForm.paymentAccountNames[itemsRow.index]} - ${bpuForm.paymentRefNumbers[itemsRow.index]}"/>
																	</c:when>
																	<c:otherwise>
																		<c:out value="${bpuForm.paymentRefNumbers[itemsRow.index]}"/>
																	</c:otherwise>
																</c:choose>
															</span>
														</td>
														<td>
															<span class="amount" id="${itemsRow.index}">
																<c:out value="${bpuForm.amounts[itemsRow.index]}"/>
															</span>
															<form:hidden path="amounts[${itemsRow.index}]" class="amount2" value="${bpuForm.amounts[itemsRow.index]}"/>
<%-- 															<input type="hidden" class="amount2" value="${bpuForm.amounts[itemsRow.index]}" /> --%>
														</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:otherwise>
							</c:choose>
						</form:form>
					</div>
					<!-- END : CONTENT AREA -->
				</div>
			</div>
			<div class="clear"></div>
			
		</div>
<%-- <div class="content">
	
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
			<div class="content-header">
				<div class="helper border summary">
					<div class="detail">
						<h2>897.023.000</h2>
						<p>TOTAL AMOUNT (Rp.)</p>
					</div>
					<button class="btn btn-positive btn-huge">SAVE</button>
				</div>
			
				<h1>Cashier Checkout Report</h1>
				<p class="subtitle">Generate cashier checkout report</p>
			
			</div>
				
				<c:choose>
					<c:when test="${!empty transactionList}">
					<form class="form-horizontal" method="post" action="${checkout}">
						<div class="control-group">
								<label class="control-label" for="input01">Cashier Name</label>
								<div class="controls">
									<a href="#"><c:out value="${cashierName}" /></a>
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label" for="input01">Date and Time</label>
								<div class="controls">
									<c:out value="${dateAndTime}" />
								</div>
							</div>
							
							<div class="control-group">
									<label class="control-label" for="prependedInput">Total Amount</label>
									<div class="controls">
										<div class="input-prepend">
											<span class="add-on">Rp</span><input class="input-small" id="prependedInput" size="20" type="text">
										</div>	
									</div>
							</div>
							
							<div class="control-group">
									<label class="control-label" for="textarea">Note</label>
									<div class="controls">
											<textarea class="input-xxsmall" id="textarea" rows="3"></textarea>
									</div>
								</div>
					</form>
					
					
					
		
						 
						<form:form modelAttribute="bpuForm" method="POST" action="${checkout}" class="form-horizontal">
							${message}
							
							<div class="field fl">
								<label>Deposit to</label>
								<div class="controls pills-autocomplete-field">
									<div class="input-append">
										<form:input path="bankAccountId" class="pills-autocomplete pills-autocomplete-form input-very-small" /><span class="add-on"><i class="icon-pencil"></i></span>
		<!-- 								<input type="text" id="vendor" class="pills-autocomplete pills-autocomplete-form input-very-small" /><span class="add-on"><i class="icon-pencil"></i></span> -->
									</div>
								</div>
							</div>
							
							<table class="table table-striped">
								<colgroup>
									<col/>
									<col/>
									<col class="amount" >
									<col class="w50" >
								</colgroup>
									<thead>
									<tr>
										<th>ID</th>
										<th>DATE</th>
										<th>REFNUMBER</th>
										<th>AMOUNT</th>
									</tr>
									</thead>
									<c:set var="totalAmount" value="0" />
									<tbody>
									<c:forEach items="${transactionList}" var="transaction" varStatus="itemsRow">
									
										<tr>
											<td><form:label path="transactionIds[${itemsRow.index}]"><c:out value="${transaction.id}" /></form:label>
												<form:hidden path="transactionIds[${itemsRow.index}]" value="${transaction.id}"/>
											</td>
											<td><c:out value="${transaction.trxDate}" /></td>
											
											<td><c:out value="${transaction.refNumber}" /></td>
											
											<td><span class="amount"><c:out value="${transaction.amount}"/></span></td>
											
											<c:set var="totalAmount" value="${totalAmount + transaction.amount}" />
									</c:forEach>
									
									</tbody>
								</table>
							
							<div class="form-actions">	
								<form:button name="action" value="save" class="btn btn-positive">Checkout</form:button>
								
							</div>
							
					
						</form:form>
					</c:when>
					<c:otherwise>
					<div class="empty-state person">
						<h2>No Report Available</h2>
					</div>
						<!-- Nothing to checkout! --> <br />
						<c:url var="backToCashier" value="/finance/cashier/transactions/create" />
						<a href="${backToCashier}">Back to cashier menu</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<!-- END : CONTENT AREA -->
		
	</div>
	<div class="clear"></div>
</div> --%>
