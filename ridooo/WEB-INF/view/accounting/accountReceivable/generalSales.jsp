<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="getCustomers" value="/finance/getcustomers" />
<c:url var="receivable" value="/finance/receivable" />
<c:url var="apiFinance" value="/api/companies/1/finance" />
<c:url var="img" value="/img" />
<script>
	var arr = [];
	var row = 0;
	var count = 0;
	var obj;
	
	function getCustomers() {
		$.ajax({
				type : "GET",
				url : "${getCustomers}"
			})
			.done(
				function(resPonseText) {
					obj = jQuery.parseJSON(resPonseText);
					var data = obj.data;
// 					console.log(data);
						for ( var i = 0, len = data.length; i < len; i++) {
							arr.push({id:data[i].id, name:data[i].name});
// 							ids.push(data[i].id);
						}
					return arr;
				});
	}
	
	function getCustomerDetails(customerId, customerName) {
		$.ajax({
				type : "POST",
				url : "${receivable}/"+customerId+"/getdetails"
			})
			.done(
				function(resPonseText) {
					obj = jQuery.parseJSON(resPonseText);
					var data = obj.data;
// 					console.log(data);
					$.each(data, function(index, value) {
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
// 					console.log(data);
					invoiceList = $('.extended ul.item-list');
					invoiceList.empty();
					if(data.length != 0){
						$.each(data, function(index, value) {
// 							console.log('value:'+value);
							$('.extended ul.item-list').append('<li><span class="btn btn-mini" id="'+value.id+'"><i class="icon-plus"></i></span><div class="item"><a href="#">Invoice '+value.id+
								'</a><p>'+value.due_date+'</p></div><span class="number">'+value.amount+'</span><div class="clear"></div></li>');
							
						});
						$('span.number').currency();
						
					}
					else{
						$('.extended ul.item-list').append('<li>No Pending Invoice</li>');
					}
					
					sb.resize();
					
				});
	}
	
	function getInvoiceDetails(invoiceId) {
		$.ajax({
				type : "POST",
				url : "${receivable}/transaction/"+invoiceId+"/getdetails"
			})
			.done(
				function(resPonseText) {
					obj = jQuery.parseJSON(resPonseText);
					var data = obj.data;
// 					console.log(data);
// 					ids = data;
					$.each(data,function(key,value){
						console.log(key+":"+value);
// 						$.each(value,function(index,val){
// 							console.log("val:"+val);
							$('#invoice_form tbody').prepend('<tr class="'+invoiceId+'"><td><div class="remove-element">&times;</div><h4><a href="#">Invoice '+invoiceId+'</a></h4><span>'+value.accountName+'</span>'+
								'<input type="hidden" id="titles'+count+'" name="titles['+count+']" value="Invoice '+invoiceId+'">'+
								'<input type="hidden" id="invoiceIds'+count+'" name="invoiceIds['+count+']" value="'+invoiceId+'"><input type="hidden" id="accountIds'+count+'" name="accountIds['+count+']" value="'+value.accountId+'"></td>'+
								'<td class="align-right"><span class="number quantity">'+value.qty+'</span>'+
								'<input type="hidden" id="quantities'+count+'" name="quantities['+count+']" value="'+value.qty+'"></td>'+
								'<td><span class="amount number price">'+value.unitPrice+'</span>'+
								'<input type="hidden" id="unitPrices'+count+'" name="unitPrices['+count+']" value="'+value.unitPrice+'"></td>'+
								'<td><span class="amount totalCount">'+value.amount+'</span>'+
								'<input type="hidden" class="totalCount" id="amounts'+count+'" name="amounts['+count+']" value="'+value.amount+'"></td></tr>');
							count++;
// 						});
						
						$('input.totalCount').trigger('change');
						/* var totalAmount = $('input.totalCount').sumValues();
						$('div.detail h2').html(totalAmount);
						$('tfoot span.total').html(totalAmount);
						$('input#totalAmount').val(totalAmount); */
						
					});
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
	
	function api(){
		$.ajax({
			type : "POST",
			url : "${apiFinance}/generalsales",
			
			data : $('form').serialize()
		}).done(function(resPonseText) {
			alert(resPonseText);
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
			
			$('.extended').children().css('display', 'block');
			$('.empty-state').hide();
			$('#invoice_form').show();
			$('.customer-details').show();
			$('.pending-invoices').show();
			
			//sb.remove();
			//core.scrollbar();
		};
		
		core.pills_autocomplete({
			data: '${getCustomers}',
			hintText: "Customer Name",
			onAdd: function(){
				callback();
			},
			target_element: '#customerId',
		});
// 		core.pills_autocomplete("${getCustomers}", "Customer Name", callback, '#customerId', void(0));
// 		core.typeahead(arr);

		core.morra_autocomplete({
				url: false, // required for ajax
				data: [ // required for data on local
					{ id: 0, name: "Beli Buku" }, { id: 1, name: "Beli Seragam" }, { id: 2, name: "Uang SPP" }, { id: 3, name: "Uang SSP" }
				],
				target_id: 'mac1', // required
				onAdd: function(){ // on add callback
// 					console.log(row);
// 					$('input[name="quantities"]:enabled').attr('id', count);
					$('input[name="itemQuantities"]:enabled').attr('name','itemQuantities['+row+']');
// 					$('input[name="unitPrices"]:enabled').parent().next().children().first().next().attr('id', count);
					$('input[name="itemPrices"]:enabled').parents('td').next().find('input').attr('name','itemAmounts['+row+']');
// 					$('input[name="unitPrices"]:enabled').attr('id', count);
					$('input[name="itemPrices"]:enabled').attr('name','itemPrices['+row+']');
// 					$('input[name="amounts"]:enabled').attr('id', count);
					
					$('input[name="itemQuantities['+row+']"]').focus();
					
					row++;
					
				},
				onResult: function(){ // on get resuls callback
				
				},
				onDelete: function(){
					$('input.totalCount').trigger('change');
				},
				must_same: true,
				min_char: 3, // min search char
				// if you want additional helper
				// type = "select" -> for render select box
				// type = "input" -> for render input box
				helper: {
					element: [
						{
							info: { element_id: 'select1', element_name: 'select1', element_class: 'input-small', element_type: 'select', element_label: 'select1' },
							data:
								${incomeOptions}
							
						}
					]
				},
			});
		
		$('button#pay').on('click',function(){	
			$('form#oForm').submit();
		});
		
		$('body').on('click','ul.item-list li span.btn-mini',function(){
//				console.log("disable:"+$(this).parent().attr('disabled'));
			if(!$(this).hasClass('disabled')){
				var invoiceId = $(this).attr('id');
				$(this).addClass('disabled');
				getInvoiceDetails(invoiceId);
			}
		});
		
		$('.count').listenForChange();
		$('body').on('keyup','.count', function() {
// 			console.log("keyup");
			$('.count').trigger('change');
		});
		$('body').on('change','.count', function() {
// 			console.log("change");
			var thisClass = $(this).attr('class');
			if($(this).hasClass('quantity')){
				var quantity = $(this).val();
				var price = $(this).parents('td').next().find('input').val();
				var result = quantity * price;
// 				console.log('q:'+quantity);
// 				console.log('p:'+price);
// 				console.log('r:'+result);
				$(this).parents('td').next().next().find('span').html(result);
				$(this).parents('td').next().next().find('span').currency();
				$(this).parents('td').next().next().find('input').val(result);
			}
			else{
				var quantity = $(this).parents('td').prev().find('input').val();
				var price = $(this).val();
				var result = quantity * price;
// 				console.log('q:'+quantity);
// 				console.log('p:'+price);
// 				console.log('r:'+result);
				$(this).parents('td').next().find('span').html(result);
				$(this).parents('td').next().find('span').currency();
				$(this).parents('td').next().find('input').val(result);
			}
			
			$('input.totalCount').trigger('change');
// 			$('span.totalCount').html(result);
// 			$('input.totalCount').val(result);
			
		});
		$('body').on('change','input.totalCount',function(){
// 		$('input.totalCount').change(function(){
			console.log('change total');
			var total = $('input.totalCount').sumValues();
			$('span.total').html(total);
			$('div.detail h2').html(total);
			$('input#totalAmount').val(total);
			$('#totalAmount').change();
			
			$('span.total').currency();
			$('div.detail h2').currency();
			console.log('finish');
		});
		
		$('body').on('click','.remove-element',function(){
			var rmClass = $(this).parents('tr').attr('class');
			$('tr.'+rmClass).remove();
			$('.btn-mini#'+rmClass).removeClass('disabled');
			
			$('input.totalCount').trigger('change');
		});
		
		$('body').on('click','.mac-close',function(){
			$('input.totalCount').trigger('change');
		});
		
		$('#totalAmount').change(function(){
// 			alert($('#totalAmount').val());
			if($(this).val() == "0"){
				$('#pay').addClass('disabled');
				$('#pay').attr('disabled','');
			}
			else{
				$('#pay').removeClass('disabled');
				$('#pay').removeAttr('disabled');
			}
		});
		
		$('#token-input-customerId').keydown(function(e) {
			var code = e.keyCode || e.which;
		    if (code == '9') {
				$('#mac1').focus();
		    }
		 });
		
		$('.table').on('keyup', '#mac1',function(e){
		    var code = e.keyCode || e.which;
		    if (code == '27') {
				$('#pay').focus();
		    }
		});
		
		/* $('.content-header').hide();
		$('.item-list').hide(); */
		
		/* if ajax
		core.typeahead(function(){
			$.ajax({
				success: function(data){
					return data;
				}
			});
		});
		*/
		
// 		$('form').submit(function(e){
// 			e.preventDefault();
// 			api();
// 		});
	});
</script>
<div class="content">
			
			<!-- GRID CONTAINER -->
			<div class="grid-container">
			
				<!-- START : CONTENT AREA -->
				<div class="grid1 last extended no-close">
					<div class="grid-content">
						<div class="empty-state person small"><h3>No Customer</h3></div>
						
						<div class="content-header customer-details">
							<%-- <img class="image avatar avatar-mini" src="${img}/no-person.png">
							<h3>Adi Saputra</h3>
							<p class="subtitle">Class 2</p>
							<div class="clear"></div> --%>
						</div>
						
						<div class="pending-invoices">
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
					
						<div class="content-header">
							<div class="helper summary">
								<div class="detail border">
									<h2>0</h2>
									<p>Total (Rp.)</p>
								</div>
								<button class="btn btn-positive btn-huge disabled" id="pay" disabled>PAY</button>
							</div>
							
							<!-- <div class="helper">
								<a href="receipt.html" class="btn btn-large btn-positive fr">Pay</a>
								<div class="box-amount">
									<div class="box-title">Total (Rp.)</div>
									<div class="box-content"><span class="amount">150.000</span></div>
								</div>
							</div> -->
						
							<h1>General Sales</h1>
							<p class="subtitle">Record general sales transactions</p>
						</div>
						
<!-- 						<form id="customer_form"> -->
						<c:url var="postForm" value="/finance/generalsales" />
						<form:form modelAttribute="oForm" method="POST" action="${postForm}" class="validate">
<%-- 							<cform:input label="Customer Name" path="customerId" size="small" clazz="pills-autocomplete pills-autocomplete-form" append="<i class='icon-pencil'></i>"/> --%>
							<input type="hidden" name="roleId" value="1" />
							<div class="control-group">
								<label class="control-label" for="token-input-customerId">Customer Name</label>
								<div class="controls pills-autocomplete-field">
										<div class="input-append">
											<input id="customerId" name="customerId" type="text" class="input-small pills-autocomplete pills-autocomplete-form" /><span class="add-on"><i class="icon-pencil"></i></span>
										</div>
								</div>
							</div>
<!-- 						</form> -->
							<hr />
							
							<div class="empty-state person"><h2>Start with customer profile</h2></div>
							
	<%-- 						<form method="POST"> --%>
							<table id="invoice_form" class="item-list table table-striped">
							
								<colgroup>
									<col/>
									<col class="w50"/>
									<col class="w150"/>
									<col class="w150"/>
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
										<td><span class="amount total">0</span><form:hidden path="totalAmount" /></td>
									</tr>
								</tfoot>
								
								
								<tbody>
									<tr>
										<td class="no-padding">
											<input id="mac1" type="text" class="input-small morra_autocomplete" />
										</td>
										<td class="no-padding"><input name="itemQuantities" type="text" class="amount number count quantity" disabled/></td>
										<td class="no-padding"><input name="itemPrices" type="text" class="amount number count price" disabled/></td>
<!-- 									<td class="no-padding"><input type="text" class="amount totalCount" /><input name="amounts" type="hidden" class="totalCount" /></td> -->
										<td class="no-padding"><span class="amount totalCount"></span><input name="itemAmounts" type="hidden" class="totalCount" /></td>
									</tr>
									<!--
									<tr>
										<td>
											<h4><a href="#">Invoice 1234</a></h4>
											<span>This is description</span>
										</td>
										<td><span class="number">1</span></td>
										<td><span class="number">150.000</span></td>
										<td><span class="amount">150.000</span></td>
									</tr>
									-->
								</tbody>
								
							</table>
							<%-- <table id="invoice_form"  class="item-list table table-striped">
								<colgroup>
									<col/>
									<col class="w100"/>
									<col class="w200"/>
									<col class="w200"/>
								</colgroup>
								
								<thead>
									<tr>
										<th>Item</th>
										<th>Qty</th>
										<th>Unit Price</th>
										<th>Amount (Rp.)</th>
									</tr>
								</thead>
								
								<tbody>
									<tr>
										<td>
											<input type="text" class="input-small pills-autocomplete" />
										</td>
										<td><input type="text" class="number" /></td>
										<td><input type="text" class="number" /></td>
										<td><input type="text" class="amount price" /></td>
									</tr>
									<!-- <tr>
										 <td>
											<h4><a href="#">Invoice 1234</a></h4>
											<span>This is description</span>
										</td>
										<td><span class="number">1</span></td>
										<td><span class="number">150.000</span></td>
										<td><span class="amount">150.000</span></td>
									</tr> -->
								</tbody>
								
								<tfoot>
									<tr>
										<td colspan="2"></td>
										<td><h4>Total (Rp.)</h4></td>
										<td><span class="amount total">150.000</span></td>
									</tr>
								</tfoot>
							</table> --%>
						</form:form>
					</div>
					<!-- END : CONTENT AREA -->
				</div>
			</div>
			<div class="clear"></div>
			
		</div>