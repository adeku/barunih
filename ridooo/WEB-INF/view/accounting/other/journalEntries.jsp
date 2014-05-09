<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="addVendor" value="/people/create" />
<c:url var="getAccounts" value="/finance/getaccounts" />
<script>
	$(document).ready(function(){
		var count = 0;
		$('table#group').hide();
		
		$('body').on('keyup','.count', function() {
			var thisClass = $(this).attr('class');
			if($(this).val() == '' && $(this).hasClass('debit')){
				$(this).parents('td').next().find('input').removeAttr('disabled');
			}
			else if($(this).val() == '' && $(this).hasClass('credit')){
				$(this).parents('td').prev().find('input').removeAttr('disabled');
			}
			else if($(this).hasClass('debit')){
// 				$(this).parents('td').next().find('input').addClass('disabled');
				$(this).parents('td').next().find('input').attr('disabled','');
			}
			else if($(this).hasClass('credit')){
				$(this).parents('td').prev().find('input').attr('disabled','');
			}
			
			$('.count').trigger('change');
		});
		$('body').on('change','.count', function() {
			var thisClass = $(this).attr('class');
			
// 			if($(this).hasClass('debit')){
// 				var quantity = $(this).val();
// 				var price = $(this).parents('td').next().find('input').val();
// 				var discount = $(this).parents('td').next().next().find('input').val();
// 				var result = quantity * (price - discount);
// 				$(this).parents('td').next().next().next().find('span').html(result);
// 				$(this).parents('td').next().next().next().find('input').val(result);
// 			}
// 			else if($(this).hasClass('price')){
// 				var quantity = $(this).parents('td').prev().find('input').val();
// 				var price = $(this).val();
// 				var discount = $(this).parents('td').next().find('input').val();
// 				var result = quantity * (price - discount);
// 				$(this).parents('td').next().next().find('span').html(result);
// 				$(this).parents('td').next().next().find('input').next().val(result);
// 			}
// 			else{
// 				var quantity = $(this).parents('td').prev().prev().find('input').val();
// 				var price = $(this).parents('td').prev().find('input').val();
// 				var discount = $(this).val();
// 				var result = quantity * (price - discount);
// 				$(this).parents('td').next().find('span').html(result);
// 				$(this).parents('td').next().find('input').val(result);
// 			}
			
// 			$('input.totalCount').trigger('change');
// 			$('span.totalCount').html(result);
// 			$('input.totalCount').val(result);

			if($(this).hasClass('debit')){
				var total = $('input.count.debit').sumValues();
				$('#totalDebitAmount').val(total);
				$('.totalDebit').text(total);
				$('.totalDebit').currency();
			}
			else if($(this).hasClass('credit')){
				var total = $('input.count.credit').sumValues();
				$('#totalCreditAmount').val(total);
				$('.totalCredit').text(total);
				$('.totalCredit').currency();
			}
			
		});
// 		$('body').on('change','input.totalCount',function(){
// 			console.log('totalCount changed');
// 			var total = $('input.totalCount').sumValues();
// 			$('span.total').html(total);
// // 			$('div.detail h2').html(total);
// 			$('input#totalAmount').val(total);
			
// 			$('span.total').currency();
// // 			console.log();
// 		});
		
		core.morra_autocomplete({
			url: "${getAccounts}", // required for ajax
// 			data: [ // required for data on local
// 				{ id: 0, name: 'Invoice SPP' }, { id: 0, name: 'Invoice SSP' }, { id: 0, name: 'Beli Buku' }, { id: 0, name: 'Beli Seragam' }
// 			],
// 			data: ${accounts},
			target_id: 'mac1', // required
			onAdd: function(){ // on add callback
				console.log(count);
				$('input[name="debitAmounts"]:enabled').attr('name','debitAmounts['+count+']');
				$('input[name="creditAmounts"]:enabled').attr('name','creditAmounts['+count+']');
// 				$('input[name="debitAmounts"]:enabled').parents('td').next().find('input').attr('name','amounts['+count+']');
				
				count++;
			},
// 			onComplete: function(){ // on complete prepopulate callback
// //					var i = 0;
// 				<c:forEach items="${oForm.quantities}" var="quantity" varStatus="status">
// 					console.log("${status.index} ${oForm.amounts[status.index]}");
// 					$('input[name="quantities[${status.index}]"]').val('${quantity}');
// 					$('input[name="unitPrices[${status.index}]"]').val('${oForm.unitPrices[status.index]}');
// 					$('input[name="discounts[${status.index}]"]').val('${oForm.discounts[status.index]}');
// 					$('input[name="amounts[${status.index}]"]').val('${oForm.amounts[status.index]}');
// 					$('input[name="amounts[${status.index}]"]').prev().text('${oForm.amounts[status.index]}');
// 				</c:forEach>
// 			},
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
							${schoolOptions}
					},
				]
			},
// 			<c:if test="${!empty oForm.details}">
// 			prepopulate: 
// 				${oForm.details}
// 			</c:if>
		});
		
		$('body').on('click', '.token-input-dropdown a.add-more', function(e){
			e.preventDefault();
			window.location = "${addVendor}";
		});
		
		$('.submit').click(function(e){
			e.preventDefault();
			var debit = parseInt($('#totalDebitAmount').val());
			var credit = parseInt($('#totalCreditAmount').val());
			if($('#totalDebitAmount').val().length == 0 && $('#totalCreditAmount').val().length == 0){
				$('.content-header').after('<div class="alert alert-error">'+
						'<button type="button" class="close" data-dismiss="alert">&times;</button>'+
						'<strong>Error!</strong> Please insert amounts!'+
						'</div>');
			}
			else if(debit != credit){
				$('.content-header').after('<div class="alert alert-error">'+
						'<button type="button" class="close" data-dismiss="alert">&times;</button>'+
						'<strong>Error!</strong> Total amount not same!'+
						'</div>');
			}else if($("input[name^='helper_element_name_1'][value=]").length > 0){
				$('.content-header').after('<div class="alert alert-error">'+
						'<button type="button" class="close" data-dismiss="alert">&times;</button>'+
						'<strong>Error!</strong> Please choose company for all account!'+
						'</div>');
			}
			else{
				$('#journalEntriesForm').submit();
			}
		});
	});
</script>
<div class="bill content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<div class="grid-full">
			<div class="grid-content">
				<c:url var="postForm" value="/finance/journalentries" />
				<form:form modelAttribute="journalEntriesForm" method="POST" action="${postForm}" class="validate">
					<div class="content-header">
						<%-- <div class="helper border summary">
							<div class="detail">
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
							<button class="btn btn-positive btn-huge">SAVE</button>
						</div> --%>
						
								<h1>Journal Entries</h1>
								<p class="subtitle">Record manual journal entries</p>
					</div>
<%-- 					${message} --%>
					<c:if test="${!empty action}">
						<div class="alert alert-success">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Well done!</strong> You have successfully ${action} ${object}
						</div>
					</c:if>
					<c:if test="${!empty message}">
						<div class="alert alert-error">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Error!</strong> ${message}
						</div>
					</c:if>
					
					<table id="single" class="table table-striped">
						<colgroup>
							<col />
							<col class="amount" />
							<col class="amount" />
						<colgroup>
						
						<thead>
							<tr>
								<th>ACCOUNT</th>
								<th class="align-right">DEBIT (Rp.)</th>
								<th class="align-right">CREDIT (Rp.)</th>
							</tr>
						</thead>
						
						<tfoot>
							<tr>
								<td class="align-right"><h4>Total (Rp.)</h4></td>
								<td><span class="amount totalDebit">0</span><form:hidden path="totalDebitAmount"/></td>
								<td><span class="amount totalCredit">0</span><form:hidden path="totalCreditAmount"/></td>
							</tr>
						</tfoot>
						
						<tbody>
							<tr>
								<td class="no-padding"><input id="mac1" type="text" class="morra_autocomplete" /></td>
								<td class="no-padding"><input name="debitAmounts" type="text" class="amount count debit" disabled /></td>
								<td class="no-padding"><input name="creditAmounts" type="text" class="amount count credit" disabled /></td>
							</tr>
						</tbody>
					</table>
					
					<form:textarea path="title" placeholder="Memo" class="input-xxlarge" rows="4" />
					
					<div class="form-actions">
						<form:button name="action" value="${save}" class="btn btn-positive submit">Save</form:button>
						<c:url var="cancel" value="/finance/journalentries" />
						<a href="${cancel}" class="btn">Cancel</a>
					</div>
				</form:form>
			</div>
			<!-- END : CONTENT AREA -->
		</div>
	</div>
	<div class="clear"></div>
	
</div>