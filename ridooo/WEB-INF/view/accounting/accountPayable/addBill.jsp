<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="bills" value="/finance/bills" />
<c:url var="apiBills" value="/api/companies/1/finance/bills" />
<c:url var="getVendors" value="/finance/getvendors" />
<c:url var="addVendor" value="/people/vendors/create" />
<script type="text/javascript">
	var data = [];
	
	function getVendorDetails(vendorId, vendorName) {
		$.ajax({
				type : "POST",
				url : "${payableUrl}/"+vendorId+"/getvendordetails"
			})
			.done(
				function(resPonseText) {
					data = jQuery.parseJSON(resPonseText);
// 					var data = obj.data;
					console.log(data);
					$('.extended .content-header').html('<img class="image avatar avatar-mini" src="${img}/no-person.png"><h3>'+$(".token-input-token p").text()+'</h3><p class="subtitle"></p><div class="clear"></div>');
					if(data.city !== undefined && data.province !== undefined){
						$('.extended .content-header p.subtitle').html(data.city +", "+ data.province);
					}
					else if(data.city !== undefined){
						$('.extended .content-header p.subtitle').html(data.city);
					}
					else if(data.province !== undefined){
						$('.extended .content-header p.subtitle').html(data.province);
					}
					
				});
	}
	
	function getTransaction(str) {
		var selection = $("#categoryTransaction");
		$
				.ajax(
						{
							type : "POST",
							url : "${pageContext.request.contextPath}/finance/accountOption/1/"
									+ str,
							beforeSend : function() {
								selection.empty();
							}
						}).done(
						function(resPonseText) {
							var obj = jQuery.parseJSON(resPonseText);
							var dataThis = obj.data;
							for (i = 0; i < dataThis.length; i++) {
								selection.append($('<option></option>').val(
										dataThis[i].value)
										.html(dataThis[i].key));
							}
						});
	}
	function getAccounts(element) {
		var expenseAccount = element.parent().next().children();
		$.ajax({
			type : "GET",
			url : "${bills}/"+element.val()+"/getaccounts",
			beforeSend : function() {
				expenseAccount.empty();
			}
		}).done(function(resPonseText) {
			var obj = jQuery.parseJSON(resPonseText);
			data = obj.data;
			console.log(data);
			console.log(expenseAccount);
			
			for (var i = 0; i < data.length; i++) {
				expenseAccount.append($('<option></option>').val(
						data[i].id)
						.html(data[i].name));
			}
		});
	}
	
	function api(){
		$.ajax({
			type : "POST",
			url : "${apiBills}/${billId}/edit",
			
			data : $('form').serialize()
		}).done(function(resPonseText) {
			alert(resPonseText);
		});
	}
	
	// 		$('input[value=Liabilities]').attr('checked','checked');
	// 		getTransaction('Liabilities');
	$(document).ready(function() {
		var count = 0;
		var callback = function(){
// 			var vendorId = $('#vendorId').val();
// 			var vendorName = $('.token-input-token p').text();
// 			getVendorDetails(vendorId, vendorName);
// 			getInvoices(vendorId);
			
// 			$('.extended').children().css('display', 'block');
// 			$('.empty-state').css('display', 'none');
// 			$('#invoice_form').css('opacity', '1');
			
// 			//sb.remove();
// 			console.log("removed");
// 			//core.scrollbar();
		};
		
		$('input.totalCount').trigger('change');
		
			$('body').on('click', '.token-input-dropdown a.add-more', function(e){
				e.preventDefault();
				window.open("${addVendor}","_blank");
			});
		
		<c:choose>
			<c:when test="${!empty billId}">
				core.pills_autocomplete({
					data: '${getVendors}',
					hintText: "Vendor Name",
					onAdd: function(){
						callback();
					},
					target_element: '#vendorId',
					prePopulate: ${oForm.vendor},
					add_more: true,
					add_more_text: 'Add Vendors',
				});
// 				core.pills_autocomplete("${getVendors}", "Vendor Name", callback, '#vendorId', ${oForm.vendor}, true, 'Add Vendors');
			</c:when>
			<c:otherwise>
				core.pills_autocomplete({
					data: '${getVendors}',
					hintText: "Vendor Name",
					onAdd: function(){
						callback();
					},
					target_element: '#vendorId',
					add_more: true,
					add_more_text: 'Add Vendors',
				});
// 				core.pills_autocomplete("${getVendors}", "Vendor Name", callback, '#vendorId', void(0), true, 'Add Vendors');
			</c:otherwise>
		</c:choose>
		
// 		var data = [{id: 1, name: "Ganteng"}, {id: 2, name: "Dummy 2"}, {id: 3, name: "Dummy 3"}];
// 		core.pills_autocomplete(data, "This is hint", void(0), void(0), true, 'Add Vendors', '#vendorId');
		
		core.morra_autocomplete({
				url: false, // required for ajax
				data: [ // required for data on local
					{ id: 0, name: 'Pajak Bumi dan Bangunan' }, { id: 0, name: 'Pajak Usaha' }, { id: 0, name: 'Gaji Pegawai' }
				],
				target_id: 'mac1', // required
				onAdd: function(){ // on add callback
					console.log(count);
// 					$('input[name="quantities"]:enabled').attr('id', count);
					$('input[name="quantities"]:enabled').attr('name','quantities['+count+']');
					$('input[name="quantities['+count+']"]').addClass('required');
// 					$('input[name="unitPrices"]:enabled').parent().next().children().first().next().attr('id', count);
					$('input[name="unitPrices"]:enabled').parents('td').next().find('input').attr('name','amounts['+count+']');
					$('input[name="amounts['+count+']"]').addClass('required');
// 					$('input[name="unitPrices"]:enabled').attr('id', count);
					$('input[name="unitPrices"]:enabled').attr('name','unitPrices['+count+']');
					$('input[name="unitPrices['+count+']"]').addClass('required');
// 					$('input[name="amounts"]:enabled').attr('id', count);

					$('input[name="quantities['+count+']"]').focus();
					count++;
					
					
				},
				onComplete: function(){ // on complete prepopulate callback
// 					var i = 0;
					<c:forEach items="${oForm.quantities}" var="quantity" varStatus="status">
						console.log("${status.index} ${oForm.amounts[status.index]}");
						$('input[name="quantities[${status.index}]"]').val('${quantity}');
						$('input[name="quantities[${status.index}]').addClass('required');
						$('input[name="unitPrices[${status.index}]"]').val('${oForm.unitPrices[status.index]}');
						$('input[name="unitPrices[${status.index}]').addClass('required');
						$('input[name="amounts[${status.index}]"]').val('${oForm.amounts[status.index]}');
						$('input[name="amounts[${status.index}]"]').prev().text('${oForm.amounts[status.index]}');
						$('input[name="amounts[${status.index}]"]').prev().currency();
					</c:forEach>
				},
				onDelete: function(){
					$('input.totalCount').trigger('change');
				},
				must_same: false,
				min_char: 3, // min search char
				// if you want additional helper
				// type = "select" -> for render select box
				// type = "input" -> for render input box
				helper: {
					element: [
						{
							info: { element_id: 'select1', element_name: 'select1', element_class: 'input-small', element_type: 'select', element_label: 'select1' },
							data:
								${schoolOptions}
							
						},
						{
							info: { element_id: 'select2', element_name: 'select2', element_class: 'input-small', element_type: 'select', element_label: 'select2' },
							data:
								${expenseOptions}
						},
					]
				},
				<c:if test="${!empty oForm.details}">
				prepopulate: 
					${oForm.details}
				</c:if>
			});
		
// 		$('.total').keyup(function() {
// 			$('span#total').html($('input.total').sumValues());
// 		});
// 		$('.total').focusout(function() {
// 			$('span#total').html($('input.total').sumValues());
// 			$('#TotalBillAmount').val($('span#total').html());
// 		});
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
			console.log('change total')
			var total = $('input.totalCount').sumValues();
			$('span.total').html(total);
			$('div.detail h2').html(total);
			$('input#totalBillAmount').val(total);
			
			$('span.total').currency();
			$('div.detail h2').currency();
			console.log('finish');
		});
		
// 		$('#trxDate').keyup(function(e) {
// 		    var code = e.keyCode || e.which;
// 		    if (code == '9') {
// 				$('#token-input-vendorId').focus();
// 		    }
// 		 });
		
		$('#token-input-vendorId').keydown(function(e) {
			var code = e.keyCode || e.which;
		    if (code == '9') {
				$('#refNumber').focus();
		    }
		 });
		
		$('.table').on('keyup', '#mac1',function(e){
// 			console.log('keyup called'+e.keyCode);
		    var code = e.keyCode || e.which;
		    if (code == '27') {
// 		    	alert('masuk');
				$('#save').focus();
		    }
		});
		
		$('#trxDate').focus();
		
		
// 		$('form').submit(function(e){
// 			e.preventDefault();
// 			api();
// 		});

	});
</script>

<div class="bill content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<%-- <div class="grid1 last extended">
			<div class="grid-content">
				<h4>Add Vendors</h4>
				<p class="subtitle">Form instruction goes here, it can be more than 1 line</p>
				<c:url var="postForm" value="/finance/addvendor" />
				<form:form modelAttribute="vendorForm" method="POST" action="${postForm}" class="validate">
					<div class="field">
						<label>Name</label>
						<form:input path="name" />
<!-- 						<input type="text" /> -->
					</div>
					
					<div class="field">
						<label>Address</label>
						<form:textarea path="address" />
<!-- 						<textarea class="input-full"></textarea> -->
					</div>
					
					<div class="field">
						<label>Phone</label>
						<form:input path="phone" />
<!-- 						<input type="text" /> -->
						<p class="help-block">031-123-4567</p>
					</div>
					
					<div class="field">
						<label>Fax</label>
						<form:input path="fax" />
<!-- 						<input type="text" /> -->
						<p class="help-block">031-123-4567</p>
					</div>
					
					<div class="field">
						<label>Email</label>
						<form:input path="email" />
<!-- 						<input type="text" /> -->
					</div>
					
					<div class="form-actions">
						<button type="submit" class="btn btn-small btn-positive">Save changes</button>
						<button type="reset" class="btn btn-small btn-close">Cancel</button>
					</div>
				</form:form>
			</div>
		</div> --%>
	
		<div class="grid-full">
			<div class="grid-content">
				<c:choose>
					<c:when test="${!empty billId}">
						<c:url var="postForm" value="/finance/bills/${billId}/edit" />
					</c:when>
					<c:otherwise>
						<c:url var="postForm" value="/finance/bills/create" />
					</c:otherwise>
				</c:choose>
				
				<form:form modelAttribute="oForm" method="POST" action="${postForm}" class="validate">
					<input type="hidden" name="roleId" value="1" />
					<div class="content-header border-bottom-zero">
						<div class="helper summary">
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
							<form:button name="save" value="add" class="btn btn-positive btn-huge">SAVE</form:button>
						</div>
						<c:choose>
							<c:when test="${!empty billId}">
								<h1>Bill ${billId}</h1>
<!-- 								<p class="subtitle">Edit bill from vendor</p> -->
							</c:when>
							<c:otherwise>
								<h1>New Bill</h1>
<!-- 								<p class="subtitle">Create bill from vendor</p> -->
							</c:otherwise>
						</c:choose>
						<div class="field fl">
							<div class="input-append">
<%-- 								<form:input path="trxDate" class="datepicker input-very-small required" /><span class="add-on"><i class="icon-calendar"></i></span> --%>
								<form:input path="trxDate" class="datepicker" /><span class="add-on"><i class="icon-calendar"></i></span>
							</div>
						</div>
					</div>
					
					<c:if test="${!empty message}">
						<div class="alert alert-error">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Error!</strong> ${message}
						</div>
					</c:if>
					
					<c:if test="${!empty warningMessages}">
						<c:choose>
							<c:when test="${warningMessagesSize == 1}">
								<div class="alert alert-block alert-block-pad">
									<button type="button" class="close" data-dismiss="alert">&times;</button>
									<strong>Warning!</strong> ${warningMessages[0]}
								</div>
							</c:when>
							<c:otherwise>
								<div class="alert alert-block">
									<button type="button" class="close" data-dismiss="alert">&times;</button>
									<h4 class="alert-heading">Warning!</h4>
									<p>
										<ul>
											<c:forEach items="${warningMessages}" var="warningMessage">
												<li>${warningMessage}</li>
											</c:forEach>
										</ul>
									</p>
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
					
					<div class="field fl">
						<label>Vendor</label>
						<div class="controls pills-autocomplete-field">
							<div class="input-append">
								<form:input path="vendorId" class="pills-autocomplete pills-autocomplete-form input-very-small required" /><span class="add-on"><i class="icon-pencil"></i></span>
<!-- 								<input type="text" id="vendor" class="pills-autocomplete pills-autocomplete-form input-very-small" /><span class="add-on"><i class="icon-pencil"></i></span> -->
							</div>
						</div>
					</div>
					
					<div class="field fl">
						<label>Bill No.</label>
						<div class="input-append">
							<form:input path="refNumber" class="input-very-small" />
<!-- 							<input type="text" class="input-very-small" /> -->
						</div>
					</div>
					
					<%-- <div class="field fl">
						<label>Date</label>
						<div class="input-append">
							<form:input path="trxDate" class="datepicker input-very-small required" /><span class="add-on"><i class="icon-calendar"></i></span>
<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
						</div>
					</div> --%>
					
					<%-- <div class="field fl">
						<label>Total Amount</label>
						<div class="input-prepend">
							<span class="add-on">Rp.</span><form:input path="TotalBillAmount" class="input-very-small" />
<!-- 							<span class="add-on">Rp.</span><input type="text" class="input-very-small" /> -->
						</div>
					</div> --%>
					
					<div class="field fl">
						<label>Due Date</label>
						<div class="input-append">
<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
							<form:input path="dueDate" class="datepicker input-very-small required" /><span class="add-on"><i class="icon-calendar"></i></span>
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
					
					<tfoot>
						<tr>
							<td colspan="2"></td>
							<td class="align-right"><h4>Total (Rp.)</h4></td>
							<c:choose>
								<c:when test="${!empty oForm.totalBillAmount}">
									<td><span class="amount total">${oForm.totalBillAmount}</span><form:hidden path="totalBillAmount" value="${oForm.totalBillAmount}" /></td>
								</c:when>
								<c:otherwise>
									<td><span class="amount total">0</span><form:hidden path="totalBillAmount"/></td>
								</c:otherwise>
							</c:choose>
							
						</tr>
					</tfoot>
					
					<tbody>
						<c:forEach items="${oForm.detailIds}" var="detailId" varStatus="status">
							<input type="hidden" name="detailIds[${status.index}]" value="${detailId}">
						</c:forEach>
						<%-- <c:if test="${!empty oForm.helper_element_id_1}">
							<c:forEach items="${oForm.helper_element_id_1}" var="schoolId" varStatus="status">
								<tr>
									<td class="no-padding">
										<div class="mac-description" style=""><h4>${oForm.autocomplete_name[status.index]}</h4>
											<div class="general" style="">
												<input type="hidden" name="helper_element_id_1[${status.index}]" value="${oForm.helper_element_id_1[status.index]}">
												<input type="hidden" name="helper_element_name_1[${status.index}]" value="${oForm.helper_element_name_1[status.index]}">
												<a class="mac-detail" data-id="0" name="helper_element_label_1[${status.index}]" href="javascript:void(0);">${oForm.helper_element_name_1[status.index]}</a> - 
												<input type="hidden" name="helper_element_id_2[${status.index}]" value="${oForm.helper_element_id_2[status.index]}">
												<input type="hidden" name="helper_element_name_2[${status.index}]" value="${oForm.helper_element_name_2[status.index]}">
												<a class="mac-detail" data-id="0" name="helper_element_label_2[${status.index}]" href="javascript:void(0);">${oForm.helper_element_name_2[status.index]}</a>
											</div>
											<div class="mac-actions" style="">
												<a href="javascript:void(0);" data-id="0" class="mac-detail"><i class="icon-list-alt"></i></a>
												<a href="javascript:void(0);" class="mac-close">×</a>
											</div>
											<input type="hidden" name="autocomplete_id[${status.index}]" value="${oForm.autocomplete_id[status.index]}">
											<input type="hidden" name="autocomplete_name[${status.index}]" value="${oForm.autocomplete_name[status.index]}">
										</div>
										<input type="hidden" id="morra_autocomplete_1">
									</td>
									<td class="no-padding"><div style=""><input name="quantities[${status.index}]" type="text" class="amount count quantity" value="${oForm.quantities[status.index]}"></div></td>
									<td class="no-padding"><div style=""><input name="unitPrices[${status.index}]" type="text" class="amount count price" value="${oForm.unitPrices[status.index]}"></div></td>
									<td class="no-padding"><div style=""><span class="amount totalCount">${oForm.amounts[status.index]}</span>
									<input name="amounts[${status.index}]" type="hidden" class="totalCount" value="${oForm.amounts[status.index]}"></div></td>
								</tr>
							</c:forEach>
						</c:if> --%>
						<tr>
							<td class="no-padding"><input id="mac1" type="text" class="morra_autocomplete" /></td>
							<td class="no-padding"><input name="quantities" type="text" class="amount count quantity" disabled /></td>
							<td class="no-padding"><input name="unitPrices" type="text" class="amount count price" disabled /></td>
							<td class="no-padding"><span class="amount totalCount"></span><input name="amounts" type="hidden" class="totalCount" /></td>
						</tr>
					</tbody>
					</table>
				</form:form>
			</div>
			<!-- END : CONTENT AREA -->
		</div>
	</div>
	<div class="clear"></div>
	
</div>

