<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url var="payablePaymentUrl" value="/finance/payments-issued" />
<c:url var="apiPayablePaymentUrl" value="/api/companies/1/finance/payments-issued" />
<c:url var="payableUrl" value="/finance/payable" />
<c:url var="getVendors" value="/finance/getvendors" />
<c:url var="img" value="/img" />
<c:url var="addVendor" value="/people/vendors/create" />
<script>
	var data = [];
	var bills = [];
	var details = [];
	var length = 0;
	
	function getVendorDetails(vendorId, vendorName) {
		$.ajax({
				type : "POST",
				url : "${payableUrl}/"+vendorId+"/getvendordetails"
			})
			.done(
				function(resPonseText) {
					data = jQuery.parseJSON(resPonseText);
					console.log(data);
					$('.grid1 .content-header').html('<img class="image avatar avatar-mini" src="${img}/no-person.png"><h3>'+$(".token-input-token p").text()+'</h3><p class="subtitle"></p><div class="clear"></div>');
					if(data.city !== undefined && data.province !== undefined){
						$('.grid1 .content-header p.subtitle').html(data.city +", "+ data.province);
					}
					else if(data.city !== undefined){
						$('.grid1 .content-header p.subtitle').html(data.city);
					}
					else if(data.province !== undefined){
						$('.grid1 .content-header p.subtitle').html(data.province);
					}
					
				});
	}
	
	function getBills(vendorId) {
		$.ajax({
				type : "POST",
				url : "${payablePaymentUrl}/"+vendorId+"/getbills"
			})
			.done(
				function(resPonseText) {
					var obj = jQuery.parseJSON(resPonseText);
					var bills = obj.data;
					console.log(bills);
					$('ul.item-list').empty();
					
					if(bills.length != 0){
						$.each(bills, function(index, value) {
							console.log(index+"=>id : " + value.id);
							console.log(index+"=>due_date : " + value.due_date);
							if(value.due_date !== undefined){
								console.log("add bill into item-list");
								$('ul.item-list').append('<li><span class="btn btn-mini" id="'+value.id+'"><i class="icon-plus"></i></span><div class="item"><a href="#">Bills '+value.id+'</a><p>'+value.due_date+'</p></div>'+
									'<span class="number">'+value.amount+'</span><div class="clear"></div></li>');
							}
						});
						sb.resize();
					}
					else{
						$('.extended ul.item-list').append('<li>No Pending Bills</li>');
					}
					<c:if test="${!empty billId}">
						$('span#${billId}').click();
					</c:if>
				});
	}
	
	function getDetails(billId) {
		$.ajax({
				type : "POST",
				url : "${payablePaymentUrl}/"+billId+"/getdetails"
			})
			.done(
				function(resPonseText) {
					var obj = jQuery.parseJSON(resPonseText);
					details = obj.data;
					console.log(details);
					
					$.each(details,function(index,value){
						console.log(index+":"+value);
						$.each(value.category,function(i,val){
							console.log(val.accountId+":"+val.accountName+":"+val.remaining);
							$('table#bill_form tbody').append(
								'<tr class="'+billId+'">'+
									'<td><div class="remove-element">&times;</div>'+
										'<h4><a href="#">Bill '+billId+'</a><input name="billIds['+length+']" type="hidden" value="'+billId+'" /></h4>'+
										'<span>'+value.companyName+'</span><input name="companyIds['+length+']" type="hidden" value="'+value.companyId+'" /> &mdash; '+
										'<span>'+val.accountName+'</span><input name="accountIds['+length+']" type="hidden" value="'+val.accountId+'" />'+
										'<input name="detailIds['+length+']" type="hidden" value="'+val.detailIds+'" /></td>'+
									'<td><span class="amount" id="amount'+billId+'">'+val.remaining+'</span><input id="billAmounts'+length+'" name="billAmounts['+length+']" type="hidden" value="'+val.remaining+'" /></td>'+
								'<td class="no-padding"><input id="paidAmounts'+length+'" name="paidAmounts['+length+']" type="text" class="amount count" /></td></tr>');
							
							var rowq = length;
							
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
							
							$("#oForm").on('keyup', '#paidAmounts'+length, function() {
							  
							  if( parseInt($(this).val()) <= parseInt($('#billAmounts'+rowq).val())){
								  $('#paidAmounts'+rowq).removeClass('error errore');
								  $('.table').removeClass('error errore');
							  }
							  else{
								  $('#paidAmounts'+rowq).addClass('error errore');
								  $('.table').addClass('error errore');
							  }
							});
							
							length += 1;
						});
					});
					$('span#amount'+billId).currency();
				});
	}
	
	function api(){
		$.ajax({
			type : "POST",
			url : "${apiPayablePaymentUrl}/create",
			
			data : $('form').serialize()
		}).done(function(resPonseText) {
			alert(resPonseText);
		});
	}
	
	
	$(document).ready(function(){
		<c:forEach items="${vendorOptions}" var="vendor">
			data.push({id: <c:out value="${vendor.key}" />, name: '${vendor.value}'});
		</c:forEach>
		
		var callback = function(){
			// insert here
			var vendorId = $('#vendorId').val();
			getVendorDetails(vendorId);
			getBills(vendorId);
			
			$('.vendor-details').show();
			$('.pending-bills').show();
			$('table#bill_form').show();
			$('.empty-state').hide();
		};
		
		core.pills_autocomplete({
			data: '${getVendors}',
			hintText: "Vendor Name",
			onAdd: function(){
				callback();
			},
			target_element: '#vendorId',
			add_more: true,
			add_more_text: 'Add Vendors',
			<c:if test="${!empty vendorId}">
				prePopulate: [{"id":${vendorId},"name":"${vendorName}"}],
			</c:if>
		});
// 		core.pills_autocomplete("${getVendors}", "Start typing...", callback, '#vendorId', void(0), true, 'Add Vendors');
// 		core.pills_autocomplete("${getVendors}", "Start typing...", callback);
		
		$('body').on("click", 'ul.item-list span.btn-mini', function(event){
			if(!$(this).hasClass('disabled')){
				console.log($(this).attr('id'));
				var billId = $(this).attr('id');
				getDetails(billId);
				
				$(this).addClass('disabled');
				$('.extended').show();
			}
			
		});
		
		$('body').on('keyup','.count',function(){
			var totalAmount = $('.count').sumValues();
			$('div.detail h2').html(totalAmount);
			$('tfoot span.total').html(totalAmount);
			$('input#totalAmount').val(totalAmount);
			
			$('span.total').currency();
			$('div.detail h2').currency();
		});
		
		$('body').on('click','.remove-element',function(){
			var rmClass = $(this).parents('tr').attr('class');
			$('tr.'+rmClass).remove();
			$('.btn-mini#'+rmClass).removeClass('disabled');
		});
		
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
		
		// HACK TO VALIDATE
		$('.validate').submit(function(){
			if(!$('.validate').validate().element('#vendorId')){
				$('#vendorId').siblings('.token-input-list').addClass('error');
			}
		});
		
		jQuery.extend(jQuery.validator.messages, {
			required: ''
		});
		
		// clik event on auto complete add more button
		$('body').on('click', '.token-input-dropdown a.add-more', function(e){
				e.preventDefault();
				window.open("${addVendor}","_blank");
			});
		
		<c:if test="${!empty billId}">
			getVendorDetails(${vendorId}, "${vendorName}")
			getBills(${vendorId});
			
			$('.vendor-details').show();
			$('.pending-bills').show();
			$('table#bill_form').show();
			$('.empty-state').hide();
// 			getDetails(${billId});
		</c:if>
		
// 		$('form').submit(function(e){
// 			e.preventDefault();
// 			api();
// 		});
	});
</script>
<div class="bill content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<div class="grid1 last">
			<div class="grid-content">
			
				<div class="empty-state person small"><h3>No Vendor</h3></div>
				<div class="content-header vendor-details">
					<!-- <img class="image avatar avatar-mini" src="img/no-person.png">
					<h3>Adi Saputra</h3>
					<p class="subtitle">Class 2</p>
					<div class="clear"></div> -->
				</div>
				
				<div class="pending-bills">
					<h4>PENDING BILLS</h4>
					
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
				<c:url var="postForm" value="/finance/payments-issued/create" />
				<form:form modelAttribute="oForm" method="POST" action="${postForm}" class="validate">
					<input type="hidden" name="roleId" value="1" />
					<div class="content-header">
						<div class="helper  summary">
							<div class="detail border">
								<h2>0</h2>
								<p>TOTAL AMOUNT (Rp.)</p>
							</div>
							<form:button name="save" value="add" class="btn btn-positive btn-huge">PAY</form:button>
						</div>
						<h1>Issue Payment</h1>
						<p class="subtitle">Issue Payment for bills</p>
					</div>
					
					<div class="field fl">
						<label>Vendor</label>
						<div class="controls pills-autocomplete-field">
							<div class="input-append">
								<form:input path="vendorId" class="pills-autocomplete pills-autocomplete-form input-very-small required" /><span class="add-on"><i class="icon-pencil"></i></span>
<!-- 								<input type="text" id="vendorId" class="pills-autocomplete pills-autocomplete-form input-very-small" /><span class="add-on"><i class="icon-pencil"></i></span> -->
							</div>
						</div>
					</div>
					
					
					
					<div class="field fr">
						<label>Account</label>
						<div class="input-append">
		<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
							<form:select path="account" class="input-very-small" items="${accountOptions}" disabled="true" />
						</div>
					</div>
					
					
					<div class="field fr">
						<label>Date</label>
						<div class="input-append">
							<form:input path="trxDate" class="datepicker input-very-small required" /><span class="add-on"><i class="icon-calendar"></i></span>
<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
						</div>
					</div>
					
					<div class="field fr">
						<label>Check No.</label>
						<div class="input-append">
							<form:input path="checkNumber" class="input-very-small" disabled="true" />
<!-- 							<input type="text" class="input-very-small" /> -->
						</div>
					</div>
					
					
					<div class="field fr">
						<label>Payment Method</label>
						<div class="input-append">
		<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
							<form:select path="paymentMethod" class="input-very-small required" items="${paymentOptions}"/>
						</div>
					</div>
					
					<table id="bill_form" class="table table-striped item-list">
					<colgroup>
						<col />
						<col class="amount" />
						<col class="amount" />
					</colgroup>
					
					<thead>
						<tr>
							<th>ITEM</th>
							<th class="align-right">AMOUNT</th>
							<th class="align-right">PAY AMOUNT</th>
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
							<td class="no-padding"><input type="text" class="amount" disabled /></td>
							<td class="no-padding"><span class="amount"></span></td>
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
	
<%-- <div class="content">
			
			<!-- GRID CONTAINER -->
			<div class="grid-container">
				
				<!-- START : CONTENT AREA -->
				<div class="grid1 last extended no-close" style="display:none">
					<div class="empty-state no-vendor small"></div>
					<div class="content-header">
						<!-- <img class="image avatar avatar-mini" src="img/no-person.png">
						<h3>Adi Saputra</h3>
						<div class="clear"></div> -->
					</div>
					
					<h4>BILL</h4>
					
					<ul class="item-list"></ul>
				</div>
				
				<div class="grid2">
					<div class="grid-content">
						<h1>Make Payments</h1>
						<p class="subtitle">Record payments made to vendors</p>
						
						<c:url var="postForm" value="/finance/payable-payments/create" />
						<form:form modelAttribute="oForm" method="POST" action="${postForm}" class="form-horizontal validate">
							<cform:input label="Vendor" path="vendorId" size = "small" pillsAutocompleteForm="true" />
							<cform:input datepicker="true" label="Date *" path="trxDate"/>
							<cform:select label="Payment Method" path="paymentMethod" options="${methodOptions}" />
							<cform:input label="Check No." path="checkNo"/>
							<cform:select label="Account" path="account" options="${cashOptions}" clazz="cash" />
							<form:select path="account" items="${checkOptions}" style="display:none"></form:select>
							<cform:select label="" path="account" options="${checkOptions}" clazz="check" groupattr="style='display:none'"/>
							
							<c:choose>
								<c:when test="${!empty billId}">
									<cform:input label="Bill No" path="id" value="${billId}" disabled="true"/>
								</c:when>
								<c:otherwise>
									<cform:input label="Bill No" path="id" />
								</c:otherwise>
							</c:choose>
							<cform:input label="Total Amount *" path="totalAmount" prepend="Rp" number="true" value="${remaining}" required="true" />
							
							
							<div class="form-actions">
								<form:button name="save" value="add" class="btn btn-positive">Save</form:button>
								<c:url var="cancel" value=".." />
								<a href="${cancel}" class="btn cancel">Cancel</a>
							</div>
							
							<table id="invoice_form" class="item-list table table-striped">
							
								<colgroup>
									<col/>
									<col class="w100"/>
									<col class="w200"/>
									<col class="w200"/>
								</colgroup>
								
								<thead>
									<tr>
										<th>Item</th>
										<th></th>
										<th>Bill Amount</th>
										<th>Paid Amount</th>
									</tr>
								</thead>
								
								<tbody>
								</tbody>
								
								<tfoot>
									<tr>
										<td colspan="2"></td>
										<td><h4>Total (Rp.)</h4></td>
										<td>
											<span class="amount">0</span><form:hidden path="totalAmount"/><form:hidden path="totalBillAmount"/>
										</td>
									</tr>
								</tfoot>
							</table>
						</form:form>
					</div>
				</div>
				<!-- END : CONTENT AREA -->
				
			</div>
			<div class="clear"></div>
		</div> --%>
	
	
	<!-- <script type="text/javascript">
		function getTransaction(str) {
			var selection = $("#categoryTransaction");
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/finance/accountOption/1/" + str,
				beforeSend : function() {
					selection.empty();
				}
			}).done(
					function(resPonseText) {
						var obj = jQuery.parseJSON(resPonseText);
						var dataThis = obj.data;
						for (i = 0; i < dataThis.length; i++) {
							selection.append($('<option></option>').val(
									dataThis[i].value).html(dataThis[i].key));
						}
					});
		}
		$.fn.sumValues = function() {
			var sum = 0; 
			this.each(function() {
				if ( $(this).is(':input') ) {
					var val = $(this).val();
				} else {
					var val = $(this).text();
				}
				sum += parseFloat( ('0' + val).replace(/[^0-9-\.]/g, ''), 10 );
			});
			return sum;
		};
// 		$('input[value=Liabilities]').attr('checked','checked');
// 		getTransaction('Liabilities');
		$(document).ready(function(){
			$('.amount').keyup(function(){
						$('span#total').html( $('input.amount').sumValues() );
			});
			$('.amount').focusout(function(){
				$('span#total').html( $('input.amount').sumValues() );
				$('#TotalBillAmount').val($('span#total').html());
			});
// 			$('#TotalBillAmount').focusout(function(){
// 				$('span#total').html( $('input.amount').sumValues() );
// 			});
			
		});
	</script> -->