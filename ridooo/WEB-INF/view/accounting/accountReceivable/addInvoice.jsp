<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="addVendor" value="/people/create" />
<c:url var="getCustomers" value="/finance/getcustomers" />
<c:url var="financeCompanies" value="/finance/companies" />
<script>
	function getClasses(schoolId) {
		$.ajax({
				type : "POST",
				url : "${financeCompanies}/"+schoolId+"/getclasses"
			})
			.done(
				function(resPonseText) {
					data = jQuery.parseJSON(resPonseText);
	//					var data = obj.data;
					console.log(data);
					$('#classId').empty();
					$.each(data,function(key, value){
						
						$('#classId').append($('<option></option>').attr("value", key).text(value));
					});
				});
	}

	$(document).ready(function(){
		var count = 0;
		
		$('#group_link').click(function(){
			$('#oForm').toggle();
			$('#massForm').toggle();
		});
		$('#customer_link').click(function(){
			$('#group_link').trigger('click');
		});
		
		$('#companyId').change(function(){
			var schoolId = $(this).val();
			getClasses(schoolId);
		});
		
		
		$('body').on('keyup','.count', function() {
			$('.count').trigger('change');
		});
		$('body').on('change','.count', function() {
			var thisClass = $(this).attr('class');
			if($(this).hasClass('quantity')){
				var quantity = $(this).val();
				var price = $(this).parents('td').next().find('input').val();
				var discount = $(this).parents('td').next().next().find('input').val();
				var result = quantity * (price - discount);
				$(this).parents('td').next().next().next().find('span').html(result);
				$(this).parents('td').next().next().next().find('span').currency();
				$(this).parents('td').next().next().next().find('input').val(result);
			}
			else if($(this).hasClass('price')){
				var quantity = $(this).parents('td').prev().find('input').val();
				var price = $(this).val();
				var discount = $(this).parents('td').next().find('input').val();
				var result = quantity * (price - discount);
				$(this).parents('td').next().next().find('span').html(result);
				$(this).parents('td').next().next().find('span').currency();
				$(this).parents('td').next().next().find('input').next().val(result);
			}
			else{
				var quantity = $(this).parents('td').prev().prev().find('input').val();
				var price = $(this).parents('td').prev().find('input').val();
				var discount = $(this).val();
				var result = quantity * (price - discount);
				$(this).parents('td').next().find('span').html(result);
				$(this).parents('td').next().find('span').currency();
				$(this).parents('td').next().find('input').val(result);
			}
			
			$('input.totalCount').trigger('change');
// 			$('span.totalCount').html(result);
// 			$('input.totalCount').val(result);
			
		});
		$('body').on('change','input.totalCount',function(){
			console.log('totalCount changed');
			var total = $('input.totalCount').sumValues();
			$('span.total').html(total);
			$('div.detail h2').html(total);
			$('input#totalAmount').val(total);
			
			$('span.total').currency();
			$('div.detail h2').currency();
			console.log();
		});
		
// 		var data = [{id: 1, name: "Ganteng"}, {id: 2, name: "Dummy 2"}, {id: 3, name: "Dummy 3"}];

		<c:choose>
			<c:when test="${!empty oForm.totalAmount}">
				core.pills_autocomplete({
					data: '${getCustomers}',
					hintText: "Type Name",
					target_element: '#customerId',
					prePopulate: ${oForm.customer},
					add_more: true,
					add_more_text: 'Add More',
					onAdd: function(){
						$('#customerId').focus();
					},
					onReady: function(){
						$('#customerId').focus();
					}
				});
// 				core.pills_autocomplete("${getCustomers}", "Type Name", void(0), '#customerId', ${oForm.customer}, true, 'Add More');
			</c:when>
			<c:otherwise>
				core.pills_autocomplete({
					data: '${getCustomers}',
					hintText: "Type Name",
					target_element: '#customerId',
					add_more: true,
					add_more_text: 'Add More',
					onAdd: function(){
						$('#customerId').focus();
					},
					onReady: function(){
						$('#customerId').focus();
					}
				});
// 				core.pills_autocomplete("${getCustomers}", "Type Name", void(0), '#customerId', void(0), true, 'Add More');
			</c:otherwise>
		</c:choose>
// 		core.pills_autocomplete("${getCustomers}", "Type Name", void(0), '#customerId', void(0), true, 'Add More');
		
		core.morra_autocomplete({
			url: false, // required for ajax
			data: [ // required for data on local
				{ id: 0, name: 'Invoice SPP' }, { id: 0, name: 'Invoice SSP' }, { id: 0, name: 'Beli Buku' }, { id: 0, name: 'Beli Seragam' }
			],
			target_id: 'mac1', // required
			onAdd: function(){ // on add callback
				console.log(count);
// 				$('input[name="quantities"]:enabled').attr('id', count);
				$('input[name="quantities"]:enabled').attr('name','quantities['+count+']');
				$('input[name="quantities['+count+']"]').addClass('required');
// 				$('input[name="discounts"]:enabled').parent().next().children().first().next().attr('id', count);
				$('input[name="discounts"]:enabled').parents('td').next().find('input').attr('name','amounts['+count+']');
				$('input[name="amounts['+count+']"]').addClass('required');
// 				$('input[name="discounts"]:enabled').attr('id', count);
				$('input[name="discounts"]:enabled').attr('name','discounts['+count+']');
				$('input[name="discounts['+count+']"]').addClass('required');
				$('input[name="discounts['+count+']"]').addClass('discount');
// 				$('input[name="unitPrices"]:enabled').attr('id', count);
				$('input[name="unitPrices"]:enabled').attr('name','unitPrices['+count+']');
				$('input[name="unitPrices['+count+']"]').addClass('required');
// 				$('input[name="amounts"]:enabled').attr('id', count);

//				$('a[name="helper_element_label_1['+count+']"]').focus();
				$('input[name="quantities['+count+']"]').focus();
				
				count++;
				console.log("onAdd count"+count);
			},
			onComplete: function(){ // on complete prepopulate callback
//					var i = 0;
				<c:forEach items="${oForm.quantities}" var="quantity" varStatus="status">
					console.log("${status.index} ${oForm.amounts[status.index]} q:${quantity}");
					$('input[name="quantities[${status.index}]"]').val('${quantity}');
					$('input[name="quantities[${status.index}]').addClass('required');
					$('input[name="unitPrices[${status.index}]"]').val('${oForm.unitPrices[status.index]}');
					$('input[name="unitPrices[${status.index}]').addClass('required');
					$('input[name="discounts[${status.index}]"]').val('${oForm.discounts[status.index]}');
					$('input[name="discounts[${status.index}]').addClass('required');
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
							${incomeOptions}
					},
				]
			},
			<c:if test="${!empty oForm.details}">
			prepopulate: 
				${oForm.details}
			</c:if>
		});
		
		core.morra_autocomplete({
			url: false, // required for ajax
			data: [ // required for data on local
				{ id: 0, name: 'Invoice SPP' }, { id: 0, name: 'Invoice SSP' }, { id: 0, name: 'Beli Buku' }, { id: 0, name: 'Beli Seragam' }
			],
			target_id: 'mac2', // required
			onAdd: function(){ // on add callback
				console.log(count);
// 				$('input[name="quantities"]:enabled').attr('id', count);
				$('input[name="quantities"]:enabled').attr('name','quantities['+count+']');
				$('input[name="quantities['+count+']"]').addClass('required');
// 				$('input[name="discounts"]:enabled').parent().next().children().first().next().attr('id', count);
				$('input[name="discounts"]:enabled').parents('td').next().find('input').attr('name','amounts['+count+']');
				$('input[name="amounts['+count+']"]').addClass('required');
// 				$('input[name="discounts"]:enabled').attr('id', count);
				$('input[name="discounts"]:enabled').attr('name','discounts['+count+']');
				$('input[name="discounts['+count+']"]').addClass('required');
				$('input[name="discounts['+count+']"]').addClass('discount');
// 				$('input[name="unitPrices"]:enabled').attr('id', count);
				$('input[name="unitPrices"]:enabled').attr('name','unitPrices['+count+']');
				$('input[name="unitPrices['+count+']"]').addClass('required');
// 				$('input[name="amounts"]:enabled').attr('id', count);

				$('input[name="quantities['+count+']"]').focus();
				count++;
				console.log("onAdd mac2 count"+count);
			},
			onComplete: function(){ // on complete prepopulate callback
//					var i = 0;
				<c:forEach items="${oForm.quantities}" var="quantity" varStatus="status">
					console.log("${status.index} ${oForm.amounts[status.index]}");
					$('input[name="quantities[${status.index}]"]').val('${quantity}');
					$('input[name="quantities[${status.index}]').addClass('required');
					$('input[name="unitPrices[${status.index}]"]').val('${oForm.unitPrices[status.index]}');
					$('input[name="unitPrices[${status.index}]').addClass('required');
					$('input[name="discounts[${status.index}]"]').val('${oForm.discounts[status.index]}');
					$('input[name="discounts[${status.index}]').addClass('required');
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
			helper_text: "Assign Sub Company & Account",
			helper: {
				element: [
					{
						info: { element_id: 'select1', element_name: 'select1', element_class: 'input-small', element_type: 'select', element_label: 'select1' },
						data:
							${incomeOptions}
					},
				]
			},
		});
		
		$('body').on('click', '.token-input-dropdown a.add-more', function(e){
			e.preventDefault();
			window.location = "${addVendor}";
		});
		
		$('#token-input-customerId').keyup(function(e) {
// 		    console.log('keyup called');
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
		
		$('.table').on('keyup', '#mac2',function(e){
		    var code = e.keyCode || e.which;
		    if (code == '27') {
				$('#save').focus();
		    }
		});
	});
</script>
<div class="bill content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
	
		<div class="grid-full">
			<div class="grid-content">
				<c:choose>
					<c:when test="${!empty invoiceId}">
						<c:url var="postForm" value="/finance/invoices/${invoiceId}/edit" />
					</c:when>
					<c:otherwise>
						<c:url var="postForm" value="/finance/invoices/create" />
						<c:url var="postMassForm" value="/finance/invoices/masscreate" />
					</c:otherwise>
				</c:choose>
				<form:form modelAttribute="oForm" method="POST" action="${postForm}" id="oForm" class="validate">
					<div class="content-header">
						<div class="helper summary">
							<div class="detail  border">
								<c:choose>
									<c:when test="${!empty oForm.totalAmount}">
										<h2>${oForm.totalAmount}</h2>
									</c:when>
									<c:otherwise>
										<h2>0</h2>
									</c:otherwise>
								</c:choose>
								<p>TOTAL AMOUNT (Rp.)</p>
							</div>
<!-- 							<input class="btn btn-positive btn-huge" type="submit" value="SAVE"> -->
							<button id="save" class="btn btn-positive btn-huge" type="submit">SAVE</button>
						</div>
						
						<c:choose>
							<c:when test="${!empty invoiceId}">
								<h1>Invoice ${invoiceId}</h1>
								<p class="subtitle">Edit invoice for customer or group</p>
							</c:when>
							<c:otherwise>
								<h1>New Invoice</h1>
								<p class="subtitle">Create invoice for customer or group</p>
							</c:otherwise>
						</c:choose>
					</div>
<%-- 					${message} --%>
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
<%-- 					${incomeOptions} --%>
					<div class="field fl">
						<label>
							Customer
							<c:if test="${empty invoiceId}"> 
								(or <a href="#" id="group_link">Group</a>)
							</c:if>
						</label>
						<div class="controls pills-autocomplete-field">
							<div class="input-append">
								<form:input path="customerId" class="pills-autocomplete pills-autocomplete-form input-very-small required" /><span class="add-on"><i class="icon-pencil"></i></span>
<!-- 								<input type="text" id="vendor" class="pills-autocomplete pills-autocomplete-form input-very-small" /><span class="add-on"><i class="icon-pencil"></i></span> -->
							</div>
						</div>
					</div>
					
					<div class="field fl">
						<label>Invoice No.</label>
						<div class="input-append">
							<form:input path="refNumber" class="input-very-small" />
<!-- 							<input type="text" class="input-very-small" /> -->
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
						<label>Due Date</label>
						<div class="input-append">
							<form:input path="dueDate" class="datepicker input-very-small required" /><span class="add-on"><i class="icon-calendar"></i></span>
						</div>
					</div>
					
					<table id="single" class="table table-striped">
					<colgroup>
						<col />
						<col class="w50" />
						<col class="w150" />
						<col class="w150" />
						<col class="w150" />
					<colgroup>
					
					<thead>
						<tr>
							<th>ITEM</th>
							<th class="align-right">QTY</th>
							<th class="align-right">UNIT PRICE</th>
							<th class="align-right">DISCOUNT (Rp.)</th>
							<th class="align-right">AMOUNT (Rp.)</th>
						</tr>
					</thead>
					
					<tfoot>
						<tr>
							<td colspan="3"></td>
							<td><h4>Total (Rp.)</h4></td>
							<c:choose>
								<c:when test="${!empty oForm.totalAmount}">
									<td><span class="amount total">${oForm.totalAmount}</span><form:hidden path="totalAmount" value="${oForm.totalAmount}" /></td>
								</c:when>
								<c:otherwise>
									<td><span class="amount total">0</span><form:hidden path="totalAmount"/></td>
								</c:otherwise>
							</c:choose>
<%-- 							<td><span class="amount total">0</span><form:hidden path="totalAmount" /></td> --%>
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
									<td class="no-padding"><div style=""><input name="discounts[${status.index}]" type="text" class="amount count price" value="${oForm.discounts[status.index]}"></div></td>
									<td class="no-padding"><div style=""><span class="amount totalCount">${oForm.amounts[status.index]}</span>
									<input name="amounts[${status.index}]" type="hidden" class="totalCount" value="${oForm.amounts[status.index]}"></div></td>
								</tr>
							</c:forEach>
						</c:if> --%>
						<tr>
							<td class="no-padding"><input id="mac1" type="text" class="morra_autocomplete" /></td>
							<td class="no-padding"><input name="quantities" type="text" class="amount count quantity" disabled /></td>
							<td class="no-padding"><input name="unitPrices" type="text" class="amount count price" disabled /></td>
							<td class="no-padding"><input name="discounts" type="text" class="amount count" disabled /></td>
							<td><span class="amount totalCount"></span><input name="amounts" type="hidden" class="totalCount" /></td>
						</tr>
					</tbody>
					</table>
					
					<%-- <table id="group" class="table table-striped item-list">
					<colgroup>
						<col />
						<col class="amount" />
						<col class="amount" />
						<col class="amount" />
						<col class="amount" />
					<colgroup>
					
					<thead>
						<tr>
							<th>ITEM</th>
							<th class="align-right">AMOUNT (Rp.)</th>
						</tr>
					</thead>
					
					<tfoot>
						<tr>
							<td><h4>Total (Rp.)</h4></td>
							<td><span class="amount">150.000</span></td>
						</tr>
					</tfoot>
					
					<tbody>
						<tr>
							<td class="no-padding"><input id="mac2" type="text" class="morra_autocomplete" /></td>
							<td><span class="amount"></span></td>
						</tr>
					</tbody>
					</table> --%>
				</form:form>
				
				<c:if test="${empty invoiceId}"> 
					<form:form modelAttribute="massForm" method="POST" action="${postMassForm}" id="massForm" class="validate" style="display:none;">
						<div class="content-header">
							<div class="helper summary">
								<div class="detail border">
									<c:choose>
										<c:when test="${!empty oForm.totalAmount}">
											<h2>${oForm.totalAmount}</h2>
										</c:when>
										<c:otherwise>
											<h2>0</h2>
										</c:otherwise>
									</c:choose>
									<p>TOTAL AMOUNT (Rp.)</p>
								</div>
<!-- 								<input class="btn btn-positive btn-huge" type="submit" value="SAVE"> -->
								<button class="btn btn-positive btn-huge" type="submit" form="massForm">SAVE</button>
							</div>
							
							<c:choose>
								<c:when test="${!empty invoiceId}">
									<h1>Invoice ${invoiceId}</h1>
									<p class="subtitle">Edit invoice for customer or group</p>
								</c:when>
								<c:otherwise>
									<h1>New Invoice</h1>
									<p class="subtitle">Create invoice for customer or group</p>
								</c:otherwise>
							</c:choose>
						</div>
						<c:if test="${!empty message}">
							<div class="alert alert-error">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								<strong>Error!</strong> ${message}
							</div>
						</c:if>
<%-- 						${incomeOptions} --%>
						<div class="field fl">
							<label>School (or <a href="#" id="customer_link">Customer</a>)</label>
							<div class="controls">
								<form:select path="companyId" class="input-small required margin-right-zero" items="${companyOptions}" />
	<!-- 								<input type="text" id="vendor" class="pills-autocomplete pills-autocomplete-form input-very-small" /><span class="add-on"><i class="icon-pencil"></i></span> -->
							</div>
						</div>
						
						<div class="field fl">
							<label>Class</label>
							<div class="controls">
								<form:select path="classId" class="input-small required margin-right-zero" items="${classOptions}" />
<!-- 								<input type="text" id="vendor" class="pills-autocomplete pills-autocomplete-form input-very-small" /><span class="add-on"><i class="icon-pencil"></i></span> -->
							</div>
						</div>
						
						<div class="field fl">
							<label>Invoice No.</label>
							<div class="input-append">
								<form:input path="refNumber" class="input-very-small" />
	<!-- 							<input type="text" class="input-very-small" /> -->
							</div>
						</div>
						
						<div class="field fl">
							<label>Date</label>
							<div class="input-append">
								<form:input path="massTrxDate" class="datepicker input-very-small required" /><span class="add-on"><i class="icon-calendar"></i></span>
	<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
							</div>
						</div>
						
						<div class="field fl">
							<label>Due Date</label>
							<div class="input-append">
								<form:input path="massDueDate" class="datepicker input-very-small required" /><span class="add-on"><i class="icon-calendar"></i></span>
	<!-- 							<input type="text" class="datepicker input-very-small" /><span class="add-on"><i class="icon-calendar"></i></span> -->
							</div>
						</div>
						
						<table id="single" class="table table-striped">
							<colgroup>
								<col />
								<col class="w50" />
								<col class="w150" />
								<col class="w150" />
								<col class="w150" />
							<colgroup>
							
							<thead>
								<tr>
									<th>ITEM</th>
									<th class="align-right">QTY</th>
									<th class="align-right">UNIT PRICE</th>
									<th class="align-right">DISCOUNT (Rp.)</th>
									<th class="align-right">AMOUNT (Rp.)</th>
								</tr>
							</thead>
							
							<tfoot>
								<tr>
									<td colspan="3"></td>
									<td><h4>Total (Rp.)</h4></td>
									<c:choose>
										<c:when test="${!empty oForm.totalAmount}">
											<td><span class="amount total">${oForm.totalAmount}</span><form:hidden path="totalAmount" value="${oForm.totalAmount}" /></td>
										</c:when>
										<c:otherwise>
											<td><span class="amount total">0</span><form:hidden path="totalAmount"/></td>
										</c:otherwise>
									</c:choose>
		<%-- 							<td><span class="amount total">0</span><form:hidden path="totalAmount" /></td> --%>
								</tr>
							</tfoot>
							
							<tbody>
								<c:forEach items="${oForm.detailIds}" var="detailId" varStatus="status">
									<input type="hidden" name="detailIds[${status.index}]" value="${detailId}">
								</c:forEach>
								<tr>
									<td class="no-padding"><input id="mac2" type="text" class="morra_autocomplete" /></td>
									<td class="no-padding"><input name="quantities" type="text" class="amount count quantity" disabled /></td>
									<td class="no-padding"><input name="unitPrices" type="text" class="amount count price" disabled /></td>
									<td class="no-padding"><input name="discounts" type="text" class="amount count" disabled /></td>
									<td><span class="amount totalCount"></span><input name="amounts" type="hidden" class="totalCount" /></td>
								</tr>
							</tbody>
						</table>
						
					</form:form>
				</c:if>
				
				
			</div>
			<!-- END : CONTENT AREA -->
		</div>
	</div>
	<div class="clear"></div>
	
</div>