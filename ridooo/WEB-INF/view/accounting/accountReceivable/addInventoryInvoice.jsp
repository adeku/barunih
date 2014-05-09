<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="getCustomers" value="/finance/getcustomers" />
<c:url var="getSalespersons" value="/people/getsalespersons" />
<c:url var="getAddresses" value="/people/getaddresses" />
<c:url var="getItems" value="/inventory/getitems" />
<c:url var="items" value="/inventory/items" />
<c:url var="people" value="/people" />

<script>
	$(document).ready(function(){
		var countData = 0;
		var data = [{id: 1, name: "Ganteng"}, {id: 2, name: "Dummy 2"}, {id: 3, name: "Dummy 3"}];
		
		$('#popup_addaddr').click(function(e){
			e.preventDefault();
			if($('#customerId').val() == ""){
				alert('Please input customer name first.');
			}
			else{
				$('#roleId').val($('#customerId').val());
				$(this).popup({
					width: "480px",
					title: "Add Address",
					html: $('#popup_addaddrpop').html()
				});
			}
		});
		
		$('body').on('submit','#addressForm',function(e){
			e.preventDefault();
			
			$.ajax({
				type : "POST",
				url : "${people}/addaddress",
				data : $('#addressForm').serialize()
			})
			.done(
				function(resPonseText) {
					data = jQuery.parseJSON(resPonseText);
// 					var data = obj.data;
					console.log(data);
					if(data.message == ""){
						$.colorbox.close();
						
						var select = $('#addressId');
						if(select.prop) {
						  var options = select.prop('options');
						}
						else {
						  var options = select.attr('options');
						}
						 
						options[options.length] = new Option(data.name, data.id);
						select.val(data.id);
					}
					else{
						$('.control-group').eq(0).before('<div class="alert alert-error">'+
							'<button type="button" class="close" data-dismiss="alert">&times;</button>'+
							data.message+'</div>');
						$.colorbox.resize();
// 						alert('Error! '+data.message);
					}
					
			});
		});
		
		$('body').on('click','#addressForm .alert .close',function(){
			$.colorbox.resize();
		});

		$('#billIncluded1').click(function () {
            if($("#billIncluded1").is(':checked')){
            	$("#vrs-irbill-delorderspan").show();
				$("#vrs-irbill-calspan").show();
				$("#vrs-irbill-helpspan").show();
				$("#vrs-irbill-table").find(".no-view").removeClass("no-view");
				$("#vrs-irbill-table").find(".w0").removeClass("w0").addClass("w150");
				$("#vrs-irbill-help").hide();
				$("#vrs-irbill-delorder").hide();
				$("#vrs-invenitemv-tfoot").show();
			}
			else{
				$("#vrs-irbill-delorderspan").hide();
				$("#vrs-irbill-calspan").hide();
				$("#vrs-irbill-helpspan").hide();
				$("#vrs-irbill-table th:nth-last-child(1)").addClass("no-view");
				$("#vrs-irbill-table th:nth-last-child(2)").addClass("no-view");
				$("#vrs-irbill-table tr td:nth-last-child(1)").addClass("no-view");
				$("#vrs-irbill-table tr td:nth-last-child(2)").addClass("no-view");
				$("#vrs-irbill-table").find(".w150").removeClass("w150").addClass("w0");
				$("#vrs-irbill-help").show();
				$("#vrs-irbill-delorder").show();
				$("#vrs-invenitemv-tfoot").hide();
			}
        });
		
		core.pills_autocomplete({
			data: '${getCustomers}',
			hintText: "Customer Name",
			target_element: '#customerId',
			onAdd: function(){
				$.ajax({
					type : "GET",
					url : "${getAddresses}",
					data : {
						roleId: $('#customerId').val()
					}
				})
				.done(
					function(resPonseText) {
						data = jQuery.parseJSON(resPonseText);
//	 					var data = obj.data;
						console.log(data);
						var select = $('#addressId');
						$('option', select).remove();
						$.each(data, function(index, value){
							if(select.prop) {
							  var options = select.prop('options');
							}
							else {
							  var options = select.attr('options');
							}
							 
							options[options.length] = new Option(value.name, value.id);

						});
				});
			},
			add_more: true,
			add_more_text: 'Add More',
		});
		
		core.pills_autocomplete({
			data: '${getSalespersons}',
			hintText: "Sales Name",
			onAdd: function(){
			},
			target_element: '#salespersonId',
			add_more: true,
			add_more_text: 'Add New'
		});
		
		core.morra_autocomplete({
			url: '${getItems}', // required for ajax
// 			data: [ // required for data on local
// 				{ id: 1, name: 'Mouse' }, { id: 2, name: 'Laptop' }
// 			],
			target_id: 'mac1', // required
			onAdd: function(){ // on add callback
				$('.mac-detail').unbind('click');
				$('.vrs-invenitem-cross').css('display','block');
				
				$('input[name="quantities"]:enabled').attr('name','quantities['+countData+']');
				$('input[name="quantities['+countData+']"]').addClass('required');
				if($("#billIncluded1").is(':checked')){
					$('input[name="unitPrices"]:enabled').parents('td').next().find('input').attr('name','amounts['+countData+']');
					$('input[name="amounts['+countData+']"]').addClass('required');
					$('input[name="unitPrices"]:enabled').attr('name','unitPrices['+countData+']');
					$('input[name="unitPrices['+countData+']"]').addClass('required');
				}
				
				$.ajax({
					type : "POST",
					url : "${items}/"+$('input[name="autocomplete_id['+countData+']"]').val()+"/getitemdetail"
				})
				.done(
					function(resPonseText) {
						data = jQuery.parseJSON(resPonseText);
//	 					var data = obj.data;
						console.log(data);
						console.log(data.brand);
						console.log(data.sku);
						
						console.log(countData);
						$('a[name="helper_element_label_1['+countData+']"]').text(data.brand);
						$('a[name="helper_element_label_2['+countData+']"]').text(data.sku);
						
						countData++;
				});
			},
			onResult: function(){ // on get resuls callback
				
			},
			onComplete: function(){
				$('.mac-detail').unbind('click');
			},
			must_same: true,
			min_char: 3, // min search char
			// if you want additional helper
			// type = "select" -> for render select box
			// type = "input" -> for render input box
			helper: {
				element: [
					{
						info: { element_id: 'select1', element_name: 'select1', element_class: 'input-small', element_type: 'select', element_label: '<i>loading</i>' },
						data:[
							{ id: 1, name: 'data1' }, { id: 2, name: 'data2' }
						]
					},
					{
						info: { element_id: 'select2', element_name: 'select2', element_class: 'input-small', element_type: 'select', element_label: '<i>loading</i>' },
						data:[
							{ id: 1, name: 'data1' }, { id: 2, name: 'data2' }
						]
					},
					//{
					//	info: { element_id: 'input1', element_name: 'input1', element_class: 'input-small', element_type: 'input', element_label: 'input1' },
					//	data:[
					//		{ value: 'data1' }
					//	]
					//}
				]
			},
// 			prepopulate: [
// 				{
// 					main: {
// 						id: 1,
// 						name: 'Mouse'
// 					},
// 					element: [
// 						{
// 							id: 1, name: 'Toshiba'
// 						},
// 						{
// 							id: 2, name: '88769'
// 						},
// 					]
// 				},
// 				{
// 					main: {
// 						id: 2,
// 						name: 'Laptop'
// 					},
// 					element: [
// 						{
// 							id: 1, name: 'Samsung'
// 						},
// 						{
// 							id: 2, name: '32846'
// 						},
// 					]
// 				}
// 			]
		});
		
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
			console.log('change total')
			var total = $('input.totalCount').sumValues();
			$('span.total').html(total);
			$('div.detail h2').html(total);
			$('input#totalAmount').val(total);
			
			$('span.total').currency();
			$('div.detail h2').currency();
			console.log('finish');
		});
		
		$('.token-input-list input[type="text"]').eq(1).focus(function(){
			$("body").find(".token-input-dropdown").eq(1).find('.show-extend').removeClass('show-extend');
		});
		
		$("body").find(".token-input-dropdown").eq(1).on('click','.add-more',function(){
			$('#vrs-irbill-delorderspan').hide();
			$('#vrs-irbill-delorder').show();
			$('#deliveryOrderNo').trigger('focus');
		});
		
	});
</script>
<div class="content">
			
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		
		<!-- START : CONTENT AREA -->
		<div id="popup_addaddrpop" style="display: none;">
			<div class="candidate vrs-invenprcls-modal" style="width: 450px">
				<form:form modelAttribute="addressForm" class="form-horizontal" method="POST">
					<form:hidden path="roleId" />
	    			<div class="control-group">
	    				<label class="control-label">Name</label>
	    				<div class="controls">
	  						<div class="input-append">
	  							<form:input path="name" class="input-small" />
<!-- 	  							<input name="name" class="input-small" size="16" type="text"> -->
	  						</div>
	    				</div>
	    			</div>
	    			
	    			<div class="control-group">
	    				<label class="control-label">Street 1</label>
	    				<div class="controls">
	  						<div class="input-append">
	  							<form:textarea path="street1" class="input-medium" />
<!-- 	  							<textarea name="street1" class="input-medium"></textarea> -->
	  						</div>
	    				</div>
	    			</div>
	    			
	    			<div class="control-group">
	    				<label class="control-label">Street 2</label>
	    				<div class="controls">
	  						<div class="input-append">
	  							<form:textarea path="street2" class="input-medium" />
<!-- 	  							<textarea name="street2" class="input-medium"></textarea> -->
	  						</div>
	    				</div>
	    			</div>
	    			
	    			<div class="control-group">
	    				<label class="control-label">District</label>
	    				<div class="controls">
	  						<div class="input-append">
	  							<form:input path="district" class="input-small" />
<!-- 	  							<input name="district" class="input-small" size="16" type="text"> -->
	  						</div>
	    				</div>
	    			</div>
	    			
	    			<div class="control-group">
	    				<label class="control-label">City</label>
	    				<div class="controls">
	  						<div class="input-append">
	  							<form:input path="city" class="input-small" />
<!-- 	  							<input name="city" class="input-small" size="16" type="text"> -->
	  						</div>
	    				</div>
	    			</div>
	    			
	    			<div class="control-group">
	    				<label class="control-label">Province</label>
	    				<div class="controls">
	  						<div class="input-append">
	  							<form:input path="province" class="input-small" />
<!-- 	  							<input name="province" class="input-small" size="16" type="text"> -->
	  						</div>
	    				</div>
	    			</div>
	    			
	    			<div class="control-group">
	    				<label class="control-label">Postal Code</label>
	    				<div class="controls">
	  						<div class="input-append">
	  							<form:input path="postalCode" class="input-small" />
<!-- 	  							<input name="postalCode" class="input-small" size="16" type="text"> -->
	  						</div>
	    				</div>
	    			</div>
	    			
	    			<div class="control-group">
	    				<label class="control-label">Country</label>
	    				<div class="controls">
	  						<div class="input-append">
	  							<form:input path="country" class="input-small" />
<!-- 	  							<input name="country" class="input-small" size="16" type="text"> -->
	  						</div>
	    				</div>
	    			</div>
	    			
	    			<div class="control-group">
	    				<label class="control-label">Latitude</label>
	    				<div class="controls">
	  						<div class="input-append">
	  							<form:input path="latitude" class="input-small" />
<!-- 	  							<input name="latitude" class="input-small" size="16" type="text"> -->
	  						</div>
	    				</div>
	    			</div>
	    			
	    			<div class="control-group">
	    				<label class="control-label">Longitude</label>
	    				<div class="controls">
	  						<div class="input-append">
	  							<form:input path="longitude" class="input-small" />
<!-- 	  							<input name="longitude" class="input-small" size="16" type="text"> -->
	  						</div>
	    				</div>
	    			</div>
	    			
	    			<!-- <div class="control-group">
	    				<label class="control-label">Default Price</label>
	    				<div class="input-append controls">
	  						<input type="text" class="input-mini url" ><span class="add-on">% Off</span>
	    				</div>
	    			</div>
	    			
	    			<div class="control-group">
	    				<label class="control-label">From</label>
	    				<div class="controls">
	  						<select class="input-small">
								<option>MSRP</option>
								<option>VIP</option>
							</select>
	    				</div>
	    			</div> -->
	    			
	    			<div class="form-actions">
	    				<form:button type="submit" class="btn btn-positive">Create</form:button>
<!-- 	    				<button type="submit" class="btn btn-positive">Create</button> -->
	    				<button type="reset" class="btn">Cancel</button>
	    			</div>
	    		</form:form>
			</div>
		</div>
		<div class="grid-full">
			<div class="grid-content vrs-invenitem">
				<c:url var="postForm" value="/finance/invoices/create/inventory" />
				<form:form modelAttribute="oForm" method="POST" action="${postForm}" class="validate">
<%-- 				<formform id="itemReceiptForm" action="/verse/inventory/item-receipts" method="POST"> --%>
					<div class="content-header">
 						<div id="vrs-irbill-helpspan" class="helper summary">
							<div class="detail border">
 								<h2>0</h2>
 								<p>TOTAL AMOUNT (Rp.)</p>
 							</div>
 							<form:button class="btn btn-positive btn-huge">SAVE</form:button>
<!--  							<button class="btn btn-positive btn-huge">SAVE</button> -->
 						</div>
 						
 						<h1>New Invoice</h1>
 						
 						<div class="field fl">
 							<div class="input-prepend fl">
 								<span class="add-on">No.</span><form:input path="refNumber" class="input-mini" />
<!--  								<input id="refNumber" name="refNumber" class="input-mini" type="text" value=""> -->
 							</div>
 						</div>
						
						<div class="field fl">
							<div class="input-append">
								<form:input path="trxDate" class="datepicker" /><span class="add-on"><i class="icon-calendar"></i></span>
<!-- 								<input type="text" class="input-very-small datepicker" readonly=""><span class="add-on"><i class="icon-calendar"></i></span> -->
							</div>
						</div>
						
  					</div>
  					
  					<c:if test="${!empty message}">
						<div class="alert alert-error">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Error!</strong> ${message}
						</div>
					</c:if>
				
					<br/>
					
					<div class="field fl">
						<label>Customer</label>
						<div class="controls pills-autocomplete-field">
							<div class="input-append">
								<form:input path="customerId" class="pills-autocomplete pills-autocomplete-form input-very-small required" /><span class="add-on"><i class="icon-pencil"></i></span>
	<!-- 							<input id="trxVendor" name="trxVendor" class="input-small" type="text" value=""><span class="add-on"><i class="icon-pencil"></i></span> -->
							</div>
						</div>
					</div>
					<div class="field fl">
						<label>Ship to <a href="#" id="popup_addaddr">(Add)</a>:</label>
						<form:select path="addressId" class="input-medium" />
						<!-- <select class="input-small">
  								<option>something</option>
  								<option>2</option>
  								<option>3</option>
  								<option>4</option>
  								<option>5</option>
  							</select> -->
					</div>
					
					<%-- <div class="field fr">
						<label>Payment Term</label>
						<form:select path="paymentTerm" items="${warehouseOptions}" class="input-small" />
					</div> --%>
					
					<div class="field fr">
						<label>Due Date</label>
						<div class="input-append">
							<form:input path="dueDate" class="datepicker" /><span class="add-on"><i class="icon-calendar"></i></span>
						</div>
					</div>
					
					<div class="field fr">
						<label>Salesperson</label>
						<div class="controls pills-autocomplete-field">
							<div class="input-append">
								<form:input path="salespersonId" class="pills-autocomplete pills-autocomplete-form input-very-small required" /><span class="add-on"><i class="icon-pencil"></i></span>
	<!-- 							<input id="trxVendor" name="trxVendor" class="input-small" type="text" value=""><span class="add-on"><i class="icon-pencil"></i></span> -->
							</div>
						</div>
					</div>
					
					<div class="field fr">
						<label>Warehouse</label>
						<form:select path="companyId" items="${warehouseOptions}" class="input-small" />
					</div>
					
					<table id="vrs-irbill-table" class="table table-striped">
						
						<colgroup>
							<col>
							<col class="score">
							<col class="w150">
							<col class="w150">
						</colgroup>
						
						<thead>
							<tr>
								<th>Item</th>
								<th class="align-right">QTY</th>
								<th class="align-right">Unit Price</th>
								<th class="align-right">Amount (Rp.)</th>
							</tr>
						</thead>
						
						<tbody>
							<tr class="vrs-invenitem-tradd">
	     						<td class="no-padding">
	     							<input id="mac1" type="text" class="morra_autocomplete morra_autocomplete_focus" autocomplete="off">
	     							<div class="autocomplete-label" style="display: block;"><i class="icon-plus"></i><p>Add Item</p></div>
	     							<input type="hidden" id="morra_autocomplete_mac1">
	     						</td>
	     						<td class="no-padding">
	     							<form:input path="quantities" class="amount count quantity" disabled="true" />
<!-- 	     							<input class="amount count price required" type="text" autocomplete="off"/> -->
	     						</td>
								<td class="no-padding">
									<form:input path="unitPrices" class="amount count price" disabled="true" />
<!-- 									<input class="amount count price required" type="text" autocomplete="off"/> -->
								</td>
								<td class="no-padding">
									<span class="amount totalCount"></span>
									<form:hidden path="amounts" class="totalCount" />
<!-- 									<input name="amounts" type="hidden" class="totalCount" /> -->
								</td>
	      					</tr>
						</tbody>
						
						<tfoot id="vrs-invenitemv-tfoot">
							<tr class="vrs-invenitemv-tr vrs-invenitemv-total">
								<td class="align-right" colspan="3"><span>Total (Rp.)</span></td>
								<td class="align-right">
									<span class="amount total">0</span><form:hidden path="totalAmount" />
								</td>
       						</tr>
						</tfoot>
					</table>
				</form:form>
				
				
				
			</div>
			<!-- END : CONTENT AREA -->
		</div>
	</div>
	<div class="clear"></div>
	
</div>