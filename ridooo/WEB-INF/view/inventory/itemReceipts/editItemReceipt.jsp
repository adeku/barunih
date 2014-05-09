<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="getVendors" value="/finance/getvendors" />
<c:url var="getDeliveryOrderNo" value="/inventory/get-delivery-order-no" />
<c:url var="getItems" value="/inventory/getitems" />
<c:url var="items" value="/inventory/items" />
<c:url var="itemOrders" value="/inventory/item-orders" />

<script>
	var countData = 0;
	var now = ${countItems};
	function eventBillIncluded() {
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
	}
	function countTotal() {
		console.log('change total')
		var total = $('input.totalCount').sumValues();
		$('span.total').html(total);
		$('div.detail h2').html(total);
		$('input#totalBillAmount').val(total);
		
		$('span.total').currency();
		$('div.detail h2').currency();
		console.log('finish');
	}
	function getItemOrderDetails(){
		$.ajax({
			type : "POST",
			url : "${itemOrders}/"+$('#deliveryOrderNoAuto').val()+"/getorderdetail",
			beforeSend: core.loading('main')
		})
		.done(
			function(resPonseText) {
				data = jQuery.parseJSON(resPonseText);
//					var data = obj.data;
				console.log(data);

				var vendorName = "";
				var vendorId = -1;
				var warehouseId = -1;
				var date = "";
				
				$.each(data, function(index, value){
					console.log(value.id);
					console.log(value.brand);
					console.log(value.sku);
					console.log(value.quantity);
					$('#vrs-irbill-table tbody').prepend('<tr class="vrs-invenitem-tradd">'+
							'<td class="no-padding"><div class="mac-description" style="">'+
								'<h4>'+value.name+'</h4>'+
								'<div class="general">'+
									'<input type="hidden" name="helper_element_id_1['+now+']" value="1">'+
									'<input type="hidden" name="helper_element_name_1['+now+']" value="'+value.brand+'">'+
									'<a class="mac-detail" data-id="0" name="helper_element_label_1['+now+']" href="javascript:void(0);">'+value.brand+'</a>'+
									' - <input type="hidden" name="helper_element_id_2['+now+']" value="2">'+
									'<input type="hidden" name="helper_element_name_2['+now+']" value="'+value.sku+'">'+
									'<a class="mac-detail" data-id="0" name="helper_element_label_2['+now+']" href="javascript:void(0);">'+value.sku+'</a>'+
								'</div>'+
								'<div class="mac-actions" style="display: none;">'+
									'<a href="javascript:void(0);" data-id="0" class="mac-detail"><i class="icon-list-alt"></i></a>'+
									'<a href="javascript:void(0);" class="mac-close">×</a>'+
								'</div>'+
								'<input type="hidden" name="autocomplete_id['+now+']" value="'+value.id+'">'+
								'<input type="hidden" name="autocomplete_name['+now+']" value="'+value.name+'">'+
								'<input type="hidden" id="morra_autocomplete_mac1">'+
								'<input type="hidden" id="morra_autocomplete_mac1">'+
							'</td>'+
							'<td class="no-padding">'+
								'<input id="quantities'+now+'" name="quantities['+now+']" class="amount count quantity" type="text" autocomplete="off" value="'+value.quantity+'" readonly>'+
							'</td>'+
							'<td class="no-padding">'+
								'<input name="unitPrices['+now+']" class="amount count price" type="text" autocomplete="off" value="0">'+
							'</td>'+
							'<td class="no-padding">'+
								'<span class="amount totalCount">0</span>'+
								'<input type="hidden" name="amounts['+now+']"  value="0" class="totalCount"'+
							'</td>'+
						'</tr>');
					
					if(vendorName == ""){
						vendorName = value.vendorName;
					}
					if(vendorId == -1){
						vendorId = value.vendorId;
					}
					if(warehouseId == -1)
						warehouseId = value.warehouseId;
					if(date == "")
						date = value.date;
					now++;
				});
				$('.token-input-list').eq(0).html('<li class="token-input-token"><p>'+vendorName+'</p><span class="token-input-delete-token">×</span></li><li class="token-input-input-token"><input type="text" autocomplete="off" style="outline: none; width: 30px; display: none;" id="token-input-vendorId"><tester style="position: absolute; top: -9999px; left: -9999px; width: auto; font-size: 12px; font-family: Trebuchet-MS, Helvetica, Arial, sans-serif; font-weight: normal; letter-spacing: normal; white-space: nowrap;"></tester></li>');
				$('#vendorId').val(vendorId);
				$('#existedDeliveryOrderNo').val($('.token-input-list').eq(1).find('p').text());
				$('#trxDate').val(date);
				$('#warehouseId').val(warehouseId);
				core.loading.destroy();
		});
	}
	
	$(document).ready(function(){
		
		var data = [{id: 1, name: "Ganteng"}, {id: 2, name: "Dummy 2"}, {id: 3, name: "Dummy 3"}];

		$('#billIncluded1').click(function () {
			eventBillIncluded();
        });
		
		core.pills_autocomplete({
			data: '${getVendors}',
			hintText: "Vendor Name",
			onAdd: function(){
				callback();
			},
			target_element: '#vendorId',
 			prePopulate: [{"id":${detailItemREceipt.vendorID},"name":'${detailItemREceipt.vendorName}'}],
			add_more: true,
			add_more_text: 'Add Vendors',
		});
		
		core.pills_autocomplete({
			data: '${getDeliveryOrderNo}',
			hintText: "DO No.",
			onAdd: function(){
				getItemOrderDetails();
			},
			target_element: '#deliveryOrderNoAuto',
			minChars: 1,
 			prePopulate:  [{"id":${detailItemREceipt.DOID},"name":'${detailItemREceipt.DORefNumber}'}],
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
				var count = now - 1;
				$('.mac-detail').unbind('click');
				$('.vrs-invenitem-cross').css('display','block');
				
				$('input[name="quantities"]:enabled').attr('name','quantities['+count+']');
				$('input[name="quantities['+count+']"]').addClass('required');
				if($("#billIncluded1").is(':checked')){
					$('input[name="unitPrices"]:enabled').parents('td').next().find('input').attr('name','amounts['+count+']');
					$('input[name="amounts['+count+']"]').addClass('required');
					$('input[name="unitPrices"]:enabled').attr('name','unitPrices['+count+']');
					$('input[name="unitPrices['+count+']"]').addClass('required');
					$('input[name="unitPrices['+count+']"]').val(0);
					$('input[name="amounts['+count+']"]').val(0);
				}
				$('input[name="quantities['+count+']"]').val(0);
				$('input[name="quantities['+count+']"]').focus();
				
				$.ajax({
					type : "POST",
					url : "${items}/"+$('input[name="autocomplete_id['+count+']"]').val()+"/getitemdetail",
				})
				.done(
					function(resPonseText) {
						data = jQuery.parseJSON(resPonseText);
						console.log(data);
						console.log(data.brand);
						console.log(data.sku);
						
						console.log(count);
						$('a[name="helper_element_label_1['+count+']"]').text(data.brand);
						$('a[name="helper_element_label_2['+count+']"]').text(data.sku);
						
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
				]
			},
		});
		
		
			
		
		$('body').on('keyup','.count', function() {
			$('.count').trigger('change');
		});
		$('body').on('change','.count', function() {
			var thisClass = $(this).attr('class');
			if($(this).hasClass('quantity')){
				var quantity = $(this).val();
				var price = $(this).parents('td').next().find('input').val();
				var result = quantity * price;
				$(this).parents('td').next().next().find('span').html(result);
				$(this).parents('td').next().next().find('span').currency();
				$(this).parents('td').next().next().find('input').val(result);
			}
			else{
				var quantity = $(this).parents('td').prev().find('input').val();
				var price = $(this).val();
				var result = quantity * price;
				$(this).parents('td').next().find('span').html(result);
				$(this).parents('td').next().find('span').currency();
				$(this).parents('td').next().find('input').val(result);
			}
			
			$('input.totalCount').trigger('change');
			
		});
		$('body').on('change','input.totalCount',function(){
			countTotal();			
		});
		
		$('.token-input-list input[type="text"]').eq(1).focus(function(){
			$("body").find(".token-input-dropdown").eq(1).find('.show-extend').removeClass('show-extend');
		});
		
		$("body").find(".token-input-dropdown").eq(1).on('click','.add-more',function(){
			$('#vrs-irbill-delorderspan').hide();
			$('#vrs-irbill-delorder').show();
			$('#deliveryOrderNo').trigger('focus');
		});
		eventBillIncluded();
		countTotal();
	});
</script>
<div class="content">
			
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content vrs-invenitem">
				<c:url var="postForm" value="/inventory/item-receipts/${editID}/edit" />
				<form:form modelAttribute="itemReceiptForm" method="POST" action="${postForm}" class="validate">
					<div class="content-header">
 						<div id="vrs-irbill-help" class="helper">
 							<form:button class="btn btn-positive fr">Save</form:button>
 						</div>
 						
 						<div id="vrs-irbill-helpspan" class="helper summary" style="display:none;">
							<div class="detail border">
 								<h2>0</h2>
 								<p>TOTAL AMOUNT (Rp.)</p>
 							</div>
 							<form:button class="btn btn-positive btn-huge">SAVE</form:button>
<!--  							<button class="btn btn-positive btn-huge">SAVE</button> -->
 						</div>
 						
 						<h1>Edit Item Receipts</h1>
						
						<div class="field fl">
							
								<div class="input-append">
									<form:input path="trxDate" class="datepicker" value="${detailItemREceipt.trxDate}" /><span class="add-on"><i class="icon-calendar"></i></span>
								</div>
						</div>
						
						<div class="field fl">
							<label class="checkbox">
								<form:checkbox path="billIncluded" label="Bill included" class="vrs-billinclude" />
    						</label>
						</div>
  					</div>
				
					<br/>
					
					<c:if test="${!empty message}">
						<div class="alert alert-error">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Error!</strong> ${message}
						</div>
					</c:if>
					
					<div class="field fl">
						<label>Vendor</label>
						<div class="controls pills-autocomplete-field">
							<div class="input-append">
								<form:input path="vendorId" class="pills-autocomplete pills-autocomplete-form input-very-small required" /><span class="add-on"><i class="icon-pencil"></i></span>
	<!-- 							<input id="trxVendor" name="trxVendor" class="input-small" type="text" value=""><span class="add-on"><i class="icon-pencil"></i></span> -->
							</div>
						</div>
					</div>
					
					<div id="vrs-irbill-delorder" class="field fl">
						<label>DO No.</label>
						<div class="controls pills-autocomplete-field">
							
							<form:input path="deliveryOrderNo" class="input-very-small" />
						</div>
					</div>
					
					<div id="vrs-irbill-delorderspan" class="field fl" style="display:none;">
						<label>DO No.</label>
						<div class="control pills-autocomplete-field">
  								<div class="input-append">
  									<input id="deliveryOrderNoAuto" class="pills-autocomplete pills-autocomplete-form input-very-small" type="text"><span class="add-on"><i class="icon-pencil"></i></span>
  									<form:hidden path="existedDeliveryOrderNo"/>
  								</div>
						</div>
					</div>
					
					<div id="vrs-irbill-calspan" class="field fl" style="display:none;">
						<label>Due Date</label>
						<div class="control pills-autocomplete-field">
							<div class="input-append">
								<form:input path="dueDate" class="input-mini datepicker" /><span class="add-on"><i class="icon-calendar"></i></span>
<!-- 								<input type="text" class="input-mini datepicker" readonly=""><span class="add-on"><i class="icon-calendar"></i></span> -->
							</div>
						</div>
					</div>
					
					<div class="field fr">
						<label>Warehouse</label>
						<form:select path="warehouseId" items="${warehouseOptions}" class="input-small" />
					</div>
					
					
				
					<table id="vrs-irbill-table" class="table table-striped">
						
						<colgroup>
							<col>
							<col class="w100">
							<col class="w0">
							<col class="w0">
						</colgroup>
						
						<thead>
							<tr>
								<th>Item</th>
								<th class="align-right">QTY</th>
								<th class="align-right no-view">Unit Price</th>
								<th class="align-right no-view">Amount (Rp.)</th>
							</tr>
						</thead>
						
						<tbody>
						<c:forEach items="${itemList}" var="item1">
							<tr class="vrs-invenitem-tradd">
	     						<td class="no-padding"><div class="mac-description" style="">
								<h4>${item1.name}</h4>
								<div class="general">
									<input type="hidden" name="helper_element_id_1[${item1.number}]" value="1">
									<input type="hidden" name="helper_element_name_1[${item1.number}]" value="${item1.brand}">
									<a class="mac-detail" data-id="0" name="helper_element_label_1[${item1.number}]" href="javascript:void(0);">${item1.brand}</a>
									 - <input type="hidden" name="helper_element_id_2[${item1.number}]" value="2">
									<input type="hidden" name="helper_element_name_2[${item1.number}]" value="${item1.sku}">
									<a class="mac-detail" data-id="0" name="helper_element_label_2[${item1.number}]" href="javascript:void(0);">${item1.sku}</a>
								</div>
								<input type="hidden" name="autocomplete_id[${item1.number}]" value="${item1.id}">
								<input type="hidden" name="autocomplete_name[${item1.number}]" value="${item1.name}">
								<input type="hidden" id="morra_autocomplete_mac1">
								<input type="hidden" id="morra_autocomplete_mac1">
							</td>
	     						<td class="no-padding">
	     							<input name="quantities[${item1.number}]" class="amount count quantity" type="text" autocomplete="off" value="${item1.qty}">
	     						</td>
								<td class="no-padding no-view">
									<input name="unitPrices[${item1.number}]" class="amount count price" type="text" value="${item1.unitPrice}" autocomplete="off">
								</td>
								<td class="no-padding no-view">
									<span class="amount totalCount">${item1.total}</span>
									<form:hidden path="amounts" class="totalCount" value="${item1.total}" />
								</td>
	      					</tr>
	      					</c:forEach>
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
								<td class="no-padding no-view">
									<form:input path="unitPrices" class="amount count price" disabled="true" />
<!-- 									<input class="amount count price required" type="text" autocomplete="off"/> -->
								</td>
								<td class="no-padding no-view">
									<span class="amount totalCount">0</span>
									<form:hidden path="amounts" class="totalCount" />
								</td>
	      					</tr>
						</tbody>
						
						<tfoot id="vrs-invenitemv-tfoot">
							<tr class="vrs-invenitemv-tr vrs-invenitemv-total">
								<td class="align-right" colspan="3"><span>Total (Rp.)</span></td>
								<td class="align-right">
									<span class="amount total">0</span><form:hidden path="totalBillAmount" />
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