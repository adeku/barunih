<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="getCustomers" value="/finance/getcustomers" />
<c:url var="receivable" value="/finance/receivable" />
<c:url var="img" value="/img" />
<script>
	var arr = [];
	var row = 0;
	var obj;
	
// 	function getCustomers() {
// 		$.ajax({
// 				type : "GET",
// 				url : "${getCustomers}"
// 			})
// 			.done(
// 				function(resPonseText) {
// 					obj = jQuery.parseJSON(resPonseText);
// 					var data = obj.data;
// 					console.log(data);
// 						for ( var i = 0, len = data.length; i < len; i++) {
// 							arr.push({id:data[i].id, name:data[i].name});
// // 							ids.push(data[i].id);
// 						}
// 					return arr;
// 				});
// 	}
	
	function getCustomerDetails(customerId, customerName) {
		$.ajax({
				type : "POST",
				url : "${receivable}/"+customerId+"/getdetails"
			})
			.done(
				function(resPonseText) {
					obj = jQuery.parseJSON(resPonseText);
					var data = obj.data;
					console.log(data);
					$.each(data, function(index, value) {
						customerData = $('.extended .content-header');
						customerData.empty();
						var header = '<img class="image avatar avatar-mini" style="vertical-align: top;" src="${img}/no-person.png"><div class="vrs-comheadside"><h3>'+customerName+'</h3>';
						
						if (value.status == 'student'){
							if(typeof value.className != 'undefined'){
								header += '<p class="subtitle">'+value.className+'</p></div>';
							}
							else{
								header += '<p class="subtitle">Not yet enrolled to any class</p></div>';
							}
						}
						else{
							header += '<p class="subtitle">Candidate</p></div>';
						}
						
						customerData.append(header);
					});
					
				});
	}
	
	function getInvoices(customerId) {
		$.ajax({
				type : "POST",
				url : "${receivable}/"+customerId+"/getinvoices"
			})
			.done(
				function(resPonseText) {
					obj = jQuery.parseJSON(resPonseText);
					var data = obj.data;
					console.log(data);
					invoiceList = $('.extended ul.item-list');
					invoiceList.empty();
					if(data.length != 0){
						$.each(data, function(index, value) {
// 							console.log('value:'+value);
							$('.extended ul.item-list').append('<li>'+
									'<span class="btn btn-mini" id="'+value.id+'"><i class="icon-plus"></i></span>'+
									'<div class="item"><a href="#">Invoice '+value.id+'</a><p class="due_date" id="'+value.id+'">'+value.due_date+'</p></div>'+
									'<span class="amount number">'+value.amount+'</span>'+
								'<div class="clear"></div></li>');
						});
						
						sb.resize();
					}
					else{
						$('.extended ul.item-list').append('<li>No Pending Invoice</li>');
					}
					
				});
	}
	
	function getInvoiceDetails(invoiceId, daysOverdue) {
		$.ajax({
				type : "POST",
				url : "${receivable}/transaction/"+invoiceId+"/getdetails"
			})
			.done(
				function(resPonseText) {
					obj = jQuery.parseJSON(resPonseText);
					var data = obj.data;
					var isSPPInvoice = obj.SPP;
					var isSSPInvoice = obj.SSP;
					var fineHasCreated = obj.fine;
					var fineDetailId = obj.fineDetailId;
					
					console.log(data);
// 					ids = data;
					
// 					$.each(data,function(key,value){
// 						console.log('key:'+key);
// 						$.each(value,function(index,val){
// 							if(fineDetailId != val.id){
// 								// NOT FINANCE CHARGE DETAIL
// 								$('#invoice_form tbody').append('<tr class="'+invoiceId+'"><td><div class="remove-element">&times;</div>'+
// 										'<h4><a href="#">Invoice '+invoiceId+'</a></h4><span>'+key+'</span>'+
// 										'<input type="hidden" id="fineInvoiceIds'+row+'" name="fineInvoiceIds['+row+']" value="0">'+
// 										'<input type="hidden" id="detailIds'+row+'" name="detailIds['+row+']" value="'+val.id+'">'+
// 										'<input type="hidden" id="invoiceIds'+row+'" name="invoiceIds['+row+']" value="'+invoiceId+'">'+
// 										'<input type="hidden" id="accountIds'+row+'" name="accountIds['+row+']" value="'+val.accountId+'">'+
// 									'</td>'+
// 									'<td><span class="amount price" id="amount'+invoiceId+'">'+val.amount+'</span>'+
// 										'<input type="hidden" id="amounts'+row+'" name="amounts['+row+']" value="'+val.amount+'">'+
// 									'</td>'+
// 									'<td class="no-padding"><input type="text" name="paidAmounts['+row+']" class="amount count required"></td>'+
// 								'</tr>');
// 							}
// 							else{
// 								$('#invoice_form tbody').append('<tr class="'+invoiceId+'"><td><div class="remove-element">&times;</div>'+
// 										'<h4><a href="#">Invoice '+invoiceId+' <span class="label label-important fine-label">Charge</span></a></h4><span>'+key+'</span>'+
// 										'<input type="hidden" id="fineInvoiceIds'+row+'" name="fineInvoiceIds['+row+']" value="0">'+
// 										'<input type="hidden" id="detailIds'+row+'" name="detailIds['+row+']" value="'+val.id+'">'+
// 										'<input type="hidden" id="invoiceIds'+row+'" name="invoiceIds['+row+']" value="'+invoiceId+'">'+
// 										'<input type="hidden" id="accountIds'+row+'" name="accountIds['+row+']" value="'+val.accountId+'">'+
// 									'</td>'+
// 									'<td><span class="amount price" id="amount'+invoiceId+'">'+val.amount+'</span>'+
// 										'<input type="hidden" id="amounts'+row+'" name="amounts['+row+']" value="'+val.amount+'">'+
// 									'</td>'+
// 									'<td class="no-padding"><input type="text" name="paidAmounts['+row+']" class="amount count required"></td>'+
// 								'</tr>');
// 							}
// 							row++;
// 						});
						
						/* var totalAmount = $('span.price').sumValues();
						$('div.detail h2').html(totalAmount);
						$('tfoot span.total').html(totalAmount+'<input type="hidden" id="totalAmount" name="totalAmount" value="'+totalAmount+'">'); */
// 					});
					console.log('fineDetailId:'+fineDetailId);
					$.each(data,function(index,value){
						console.log('index:'+index);
						
// 						$.each(value,function(key,val){
							if(fineDetailId != value.id){
								// NOT FINANCE CHARGE DETAIL
								console.log('NOT FINANCE CHARGE DETAIL');
								$('#invoice_form tbody').append('<tr class="'+invoiceId+'"><td><div class="remove-element">&times;</div>'+
										'<h4><a href="#">Invoice '+invoiceId+'</a></h4><span>'+value.accountName+'</span>'+
										'<input type="hidden" id="fineInvoiceIds'+row+'" name="fineInvoiceIds['+row+']" value="0">'+
										'<input type="hidden" id="detailIds'+row+'" name="detailIds['+row+']" value="'+value.id+'">'+
										'<input type="hidden" id="invoiceIds'+row+'" name="invoiceIds['+row+']" value="'+invoiceId+'">'+
										'<input type="hidden" id="accountIds'+row+'" name="accountIds['+row+']" value="'+value.accountId+'">'+
									'</td>'+
									'<td><span class="amount price" id="amount'+invoiceId+'">'+value.amount+'</span>'+
										'<input type="hidden" id="amounts'+row+'" name="amounts['+row+']" value="'+value.amount+'">'+
									'</td>'+
									'<td class="no-padding"><input type="text" id="paidAmounts'+row+'" name="paidAmounts['+row+']" class="amount count required"></td>'+
								'</tr>');
							}
							else{
								console.log('masuk finance');
								$('#invoice_form tbody').append('<tr class="'+invoiceId+'"><td><div class="remove-element">&times;</div>'+
										'<h4><a href="#">Invoice '+invoiceId+' <span class="label label-important fine-label">Charge</span></a></h4><span>'+value.accountName+'</span>'+
										'<input type="hidden" id="fineInvoiceIds'+row+'" name="fineInvoiceIds['+row+']" value="0">'+
										'<input type="hidden" id="detailIds'+row+'" name="detailIds['+row+']" value="'+value.id+'">'+
										'<input type="hidden" id="invoiceIds'+row+'" name="invoiceIds['+row+']" value="'+invoiceId+'">'+
										'<input type="hidden" id="accountIds'+row+'" name="accountIds['+row+']" value="'+value.accountId+'">'+
									'</td>'+
									'<td><span class="amount price" id="amount'+invoiceId+'">'+value.amount+'</span>'+
										'<input type="hidden" id="amounts'+row+'" name="amounts['+row+']" value="'+value.amount+'">'+
									'</td>'+
									'<td class="no-padding"><input type="text" id="paidAmounts'+row+'" name="paidAmounts['+row+']" class="amount count required"></td>'+
								'</tr>');
							}
							
							var rowq = row;
							
							$("body").on('submit', '#oForm', function() {
								if ($("#paidAmounts"+rowq).hasClass('errore')) {
									//alert('Nemu Error'); 
									$('#paidAmounts'+rowq).addClass('error');
									return false;
								  }
								else{
								  	if($('.table').hasClass('error')){
								  		return false;
								  	}
								  	else{
								  		//alert('Lanjut bang !');
										return true;
								  	}
								}
							});
							
							$("#oForm").on('keyup', '#paidAmounts'+row, function() {
							  
							  if( parseInt($(this).val()) <= parseInt($('#amounts'+rowq).val())){
// 								  console.log($('#paidAmounts'+rowq).val() + ' valid ' + $('#amounts'+rowq).val());
								  $('#paidAmounts'+rowq).removeClass('error errore');
								  $('.table').removeClass('error errore');
							  }
							  else{
// 								  console.log($('#paidAmounts'+rowq).val() + ' error ' + $('#amounts'+rowq).val());
								  $('#paidAmounts'+rowq).addClass('error errore');
								  $('.table').addClass('error errore');
							  }
							});
							
							
							row++;
// 						});
						
						/* var totalAmount = $('span.price').sumValues();
						$('div.detail h2').html(totalAmount);
						$('tfoot span.total').html(totalAmount+'<input type="hidden" id="totalAmount" name="totalAmount" value="'+totalAmount+'">'); */
					});
					if(daysOverdue != 0){
						if(isSPPInvoice || isSSPInvoice){
							var fineAccountId = obj.fineAccountId;
							var fineAccountName = obj.fineAccountName;
							var chargeAmount = obj.chargeAmount;
							if(!fineHasCreated && fineDetailId == 0){
								var fine = daysOverdue * chargeAmount;
								$('#invoice_form tbody').append('<tr class="'+invoiceId+'"><td><div class="remove-element">&times;</div>'+
										'<h4><a href="#">Invoice '+invoiceId+' <span class="label label-important fine-label">Charge</span></a></h4><span>'+fineAccountName+'</span>'+
											'<input type="hidden" id="fineInvoiceIds'+row+'" name="fineInvoiceIds['+row+']" value="'+invoiceId+'">'+
											'<input type="hidden" id="detailIds'+row+'" name="detailIds['+row+']" value="0">'+
											'<input type="hidden" id="invoiceIds'+row+'" name="invoiceIds['+row+']" value="0">'+
											'<input type="hidden" id="accountIds'+row+'" name="accountIds['+row+']" value="'+fineAccountId+'">'+
									'</td>'+
									'<td><span class="amount price" id="amount'+invoiceId+'">'+fine+'</span>'+
										'<input type="hidden" id="amounts'+row+'" name="amounts['+row+']" value="'+fine+'">'+
									'</td>'+
									'<td class="no-padding"><input type="text" id="paidAmounts'+row+'" name="paidAmounts['+row+']" class="amount count required"></td>'+
								'</tr>');
								
								var rowq = row;
								
								$("body").on('submit', '#oForm', function() {
									if ($("#paidAmounts"+rowq).hasClass('errore')) {
										//alert('Nemu Error'); 
										$('#paidAmounts'+rowq).addClass('error');
										return false;
									  }
									else{
									  	if($('.table').hasClass('error')){
									  		return false;
									  	}
									  	else{
									  		//alert('Lanjut bang !');
											return true;
									  	}
									}
								});
								
								$("#oForm").on('keyup', '#paidAmounts'+row, function() {
								  
								  if( parseInt($(this).val()) <= parseInt($('#amounts'+rowq).val())){
//	 								  console.log($('#paidAmounts'+rowq).val() + ' valid ' + $('#amounts'+rowq).val());
									  $('#paidAmounts'+rowq).removeClass('error errore');
									  $('.table').removeClass('error errore');
								  }
								  else{
//	 								  console.log($('#paidAmounts'+rowq).val() + ' error ' + $('#amounts'+rowq).val());
									  $('#paidAmounts'+rowq).addClass('error errore');
									  $('.table').addClass('error errore');
								  }
								});
								row++;
							}
							else if(fineHasCreated && fineDetailId != 0){
								var fineInDbDays = obj.fineDays;
								console.log('fineInDbDays:'+fineInDbDays);
// 								var fineDays = fine / 1000;
// 								console.log('fineDays:'+fineDays);
								var fineDays = daysOverdue - fineInDbDays;
								console.log('fineDays:'+fineDays);
								
								if(fineDays > 0){
									var fine = fineDays * chargeAmount;
									$('#invoice_form tbody').append('<tr class="'+invoiceId+'"><td><div class="remove-element">×</div>'+
	 										'<h4><a href="#">Invoice '+invoiceId+' <span class="label label-important fine-label">Charge</span></a></h4><span>'+fineAccountName+'</span>'+
	 											'<input type="hidden" id="fineInvoiceIds'+row+'" name="fineInvoiceIds['+row+']" value="'+invoiceId+'">'+
	 											'<input type="hidden" id="detailIds'+row+'" name="detailIds['+row+']" value="0">'+
	 											'<input type="hidden" id="invoiceIds'+row+'" name="invoiceIds['+row+']" value="0">'+
	 											'<input type="hidden" id="accountIds'+row+'" name="accountIds['+row+']" value="'+fineAccountId+'">'+
	 									'</td>'+
	 									'<td><span class="amount price" id="amount'+invoiceId+'">'+fine+'</span>'+
	 										'<input type="hidden" id="amounts'+row+'" name="amounts['+row+']" value="'+fine+'">'+
	 									'</td>'+
	 									'<td class="no-padding"><input type="text" id="paidAmounts'+row+'" name="paidAmounts['+row+']" class="amount count required"></td>'+
	 								'</tr>');
								}
								
								var rowq = row;
								
								$("body").on('submit', '#oForm', function() {
									if ($("#paidAmounts"+rowq).hasClass('errore')) {
										//alert('Nemu Error'); 
										$('#paidAmounts'+rowq).addClass('error');
										return false;
									  }
									else{
									  	if($('.table').hasClass('error')){
									  		return false;
									  	}
									  	else{
									  		//alert('Lanjut bang !');
											return true;
									  	}
									}
								});
								
								$("#oForm").on('keyup', '#paidAmounts'+row, function() {
								  
								  if( parseInt($(this).val()) <= parseInt($('#amounts'+rowq).val())){
//	 								  console.log($('#paidAmounts'+rowq).val() + ' valid ' + $('#amounts'+rowq).val());
									  $('#paidAmounts'+rowq).removeClass('error errore');
									  $('.table').removeClass('error errore');
								  }
								  else{
//	 								  console.log($('#paidAmounts'+rowq).val() + ' error ' + $('#amounts'+rowq).val());
									  $('#paidAmounts'+rowq).addClass('error errore');
									  $('.table').addClass('error errore');
								  }
								});
								row++;
							}
						}
					}
					$('span#amount'+invoiceId).currency();
					/* $.each(data, function(index, value) {
						customerData = $('.extended .content-header');
						customerData.empty();
						customerData.append('<img class="image avatar avatar-mini" src="${img}/no-person.png"><h3>'+customerName+'</h3>');
						
						if (value.status == 'student'){
							if(typeof value.className != 'undefined'){
								customerData.append('<p class="subtitle">'+value.className+'</p><div class="clear"></div>');
							}
							else{
								customerData.append('<p class="subtitle">Not yet enrolled to any class</p><div class="clear"></div>');
							}
						}
						else{
							customerData.append('<p class="subtitle">Candidate</p><div class="clear"></div>');
						}
					}); */
					
				});
	}
	
	$(document).ready(function(){
// 		getCustomers();
		var callback = function(){
// 			arr.length = 0;
			// insert here
			var customerId = $('#customerId').val();
			var customerName = $('.token-input-token p').text();
			getCustomerDetails(customerId, customerName);
			getInvoices(customerId);
			
			$('#right-side').show();
			$('.empty-state').hide();
			$('#invoice_form').show();
			
			//sb.remove();
			console.log("removed");
			//core.scrollbar();
		};
		
		$('body').on('keyup','.count',function(){
			var totalAmount = $('.count').sumValues();
			$('div.detail h2').html(totalAmount);
			$('tfoot span.total').html(totalAmount);
			$('input#totalAmount').val(totalAmount);
			
			$('span.total').currency();
			$('div.detail h2').currency();
		});
		
		core.pills_autocomplete({
			data: '${getCustomers}',
			hintText: "Type Name",
			target_element: '#customerId',
			onAdd: function(){
				callback();
			},
		});
// 		core.pills_autocomplete("${getCustomers}", "Start typing...", callback);
		
		$('#paymentMethod').change(function(){
			if($(this).val() == 'Cash'){
				$('#checkNumber').attr('disabled','disabled');
				$('#account').attr('disabled','disabled');
			}
			else if($(this).val() == 'Bank Transfer'){
				$('#checkNumber').attr('disabled','disabled');
				$('#account').removeAttr('disabled');
			}
			else{
				$('#checkNumber').removeAttr('disabled');
				$('#account').removeAttr('disabled');
			}
				
		});
		
		$('body').on('click','ul.item-list li span.btn-mini',function(){
//				console.log("disable:"+$(this).parent().attr('disabled'));
			if(!$(this).hasClass('disabled')){
				var invoiceId = $(this).attr('id');
				$(this).addClass('disabled');
				
				var dueDate = $(this).siblings('div.item').find('p').text();
				var fine = 0;
				var daysOverdue = 0;
				if(dueDate.indexOf('overdue') != -1){
// 					console.log(dueDate);
					var dueDateSplit = dueDate.split(' ');
					daysOverdue = dueDateSplit[0];
					daysOverdue = parseInt(daysOverdue);
// 					fine = daysOverdue * 1000;
// 					console.log(fine);
				}
				getInvoiceDetails(invoiceId, daysOverdue);
			}
		});
		
		$('body').on('click','.remove-element',function(){
			var rmClass = $(this).parents('tr').attr('class');
			$('tr.'+rmClass).remove();
			$('.btn-mini#'+rmClass).removeClass('disabled');
		});
		
		// HACK TO VALIDATE
		$('.validate').submit(function(){
			if(!$('.validate').validate().element('#vendorId')){
				$('#customerId').siblings('.token-input-list').addClass('error');
			}
		});
		
		jQuery.extend(jQuery.validator.messages, {
			required: ''
		});
		
		
	});
</script>
<div class="bill content">
			
	<!-- GRID CONTAINER -->
	<div class="grid-container">
	
		<!-- START : CONTENT AREA -->
		<div class="grid1 last extended no-close">
			<div class="grid-content">
				<div class="empty-state person small" style="position:relative;top:50%;"><h3>No Customer</h3></div>
				
				<div id="right-side" style="display:none">
					<div class="content-header">
						<%-- <img class="image avatar avatar-mini" src="${img}/no-person.png">
						<h3>Adi Saputra</h3>
						<p class="subtitle">Class 2</p>
						<div class="clear"></div> --%>
					</div>
					
					<h4>PENDING INVOICE</h4>
					
					<ul class="item-list">
						<!-- <li>
							<span class="btn btn-mini"><i class="icon-plus"></i></span>
							<div class="item">
								<a href="#">Invoice 1</a>
								<p>3 days overdue</p>
							</div>
							<span class="number">150.000</span>
							<div class="clear"></div>
						</li> -->
					</ul>
				</div>
			</div>
		</div>
		
		<div class="grid2">
			<div class="grid-content">
				<c:url var="postForm" value="/finance/payments-received/create" />
				<form:form modelAttribute="oForm" method="POST" action="${postForm}" class="validate">
					<div class="content-header">
						<div class="helper summary">
							<div class="detail border">
								<h2>0</h2>
								<p>TOTAL AMOUNT (Rp.)</p>
							</div>
							<form:button name="save" value="add" class="btn btn-positive btn-huge">ACCEPT</form:button>
		<!-- 							<button class="btn btn-positive btn-huge" id="pay">PAY</button> -->
						</div>
						<%-- <div class="helper">
									<form:button name="save" value="add" class="btn btn-positive fr">Save</form:button>
		<!-- 							<button class="btn btn-positive fr">Save</button> -->
								</div> --%>
						<h1>Receive Payment</h1>
						<p class="subtitle">Page title instruction goes here</p>
					</div>
					
					<c:if test="${!empty message}">
						<div class="alert alert-error">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Error!</strong> ${message}
						</div>
					</c:if>
					
					<div class="field fl">
						<label>Customer</label>
						<div class="controls pills-autocomplete-field">
							<div class="input-append">
								<form:input path="customerId" class="pills-autocomplete pills-autocomplete-form input-very-small required" /><span class="add-on"><i class="icon-pencil"></i></span>
		<!-- 								<input type="text" id="vendor" class="pills-autocomplete pills-autocomplete-form input-very-small" /><span class="add-on"><i class="icon-pencil"></i></span> -->
							</div>
						</div>
					</div>
					
					<div class="field fl">
						<label>Date</label>
						<div class="input-append">
							<form:input path="trxDate" class="datepicker input-very-small required" /><span class="add-on"><i class="icon-calendar"></i></span>
		<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
						</div>
					</div>
					
					<div class="field fl">
						<label>Payment Method</label>
						<div class="input-append">
		<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
							<form:select path="paymentMethod" class="input-very-small required" items="${paymentOptions}"/>
						</div>
					</div>
					
					<div class="field fl">
						<label>Check No.</label>
						<div class="input-append">
							<form:input path="checkNumber" class="input-very-small" disabled='true' />
		<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
						</div>
					</div>
					
					<div class="field fl">
						<label>Account</label>
						<div class="input-append">
		<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
							<form:select path="account" class="input-very-small" items="${accountOptions}" disabled='true' />
						</div>
					</div>
					
					<table id="invoice_form" class="table table-striped item-list" style="display:none">
					<colgroup>
						<col />
						<col class="amount" />
						<col class="amount" />
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
							<td><span class="amount total">0</span><form:hidden path="totalAmount" /></td>
						</tr>
					</tfoot>
					
					<tbody>
						<!-- <tr>
							<td class="no-padding"><input id="1" type="text" class="morra_autocomplete" /></td>
							<td class="no-padding"><input name="amounts" type="text" class="amount" disabled /></td>
							<td class="no-padding"><span class="amount totalCount"></span><input name="paidAmounts" type="hidden" class="totalCount" /></td>
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