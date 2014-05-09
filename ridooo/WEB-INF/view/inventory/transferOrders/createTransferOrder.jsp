<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="viewItemsList" value="/inventory/transfer-orders/get-items" />
<c:url var="getSKUAndBrands"
	value="/inventory/transfer-orders/get-sku-brands" />
<script>
	var countData = 0;
	$(document).ready(
			function() {
				core.ui_with_parameter();
				core.morra_autocomplete({
					url : '${viewItemsList}',
					target_id : 'mac1',
					onAdd : function() {
						countData = now - 1;
						$('.mac-detail').unbind('click');
						$('.vrs-invenitem-cross').css('display', 'block');
						$('input[name="quantities"]:enabled').attr('name','quantities['+countData+']');
						$('input[name="quantities['+countData+']"]').addClass('required');
						$('input[name="quantities['+countData+']"]').val(0);
						$('input[name="quantities['+countData+']"]').focus();
						$('input[name="quantities['+countData+']"]').select();
						if (now<2) $('#totalITem').html(now+' ITEM'); else $('#totalITem').html(now+' ITEMS');
						$.ajax(
								{
									type : "POST",
									url : '${getSKUAndBrands}',
									data : {
										idTable : $(
												'input[name="autocomplete_id['
														+ countData + ']"]')
												.val()
									}
								}).done(
								function(resPonseText) {
									var obj = jQuery.parseJSON(resPonseText);
									$(
											'a[name="helper_element_label_1['
													+ countData + ']"]').text(
											obj.sku);
									$(
											'a[name="helper_element_label_2['
													+ countData + ']"]').text(
											obj.brandName);
									countData++;
								});

					},
					onResult : function() { // on get resuls callback

					},
					onComplete : function() {
						$('.mac-detail').unbind('click');
					},
					must_same : false,
					min_char : 3,
					helper : {
						element : [ {
							info : {
								element_id : 'select1',
								element_name : 'select1',
								element_class : 'input-small',
								element_type : 'select',
								element_label : '<i>loading</i>'
							},
							data : [ {
								id : 1,
								name : 'data1'
							}, {
								id : 2,
								name : 'data2'
							} ]
						}, {
							info : {
								element_id : 'select2',
								element_name : 'select2',
								element_class : 'input-small',
								element_type : 'select',
								element_label : '<i>loading</i>'
							},
							data : [ {
								id : 1,
								name : 'data1'
							}, {
								id : 2,
								name : 'data2'
							} ]
						} ]
					}
				});
			});
</script>
<!-- CONTENT -->
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">

			<div class="grid-content vrs-invenitem">
				<form:form modelAttribute="formCreate" id="formCreate"
					name="formCreate" method="POST">
					<div class="content-header">
						<div class="helper">
							<a href="javascript:void(0)" class="btn btn-positive fr"
								onClick="document.formCreate.submit()">Save</a>
						</div>

						<h1>Transfer Order</h1>


						<div class="field fl">
							<div class="input-append">
								<form:input path="transferORderDate"
									class="input-very-small datepicker" readonly="true" />
								<span class="add-on"><i class="icon-calendar"></i></span>
							</div>
						</div>
					</div>

					<br />

					<div style="display: block; position: relative;">
						<div class="field fr">
							<label>Ship Date</label>
							<div class="input-append">
								<form:input path="shipDate" class="input-very-small datepicker"
									readonly="true" />
								<span class="add-on"><i class="icon-calendar"></i></span>
							</div>
						</div>

						<div class="field fl">
							<label>Origin</label>
							<form:select path="origin" class="input-medium"
								items="${originOptions}" />
						</div>

						<div class="field fl">
							<label>Destination</label>
							<form:select path="destination" class="input-medium"
								items="${destinationOptions}" />
						</div>
					</div>


					<table class="table table-striped">

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
							<tr class="vrs-invenitem-tradd">
								<td class="no-padding"><input id="mac1" type="text" class="morra_autocomplete morra_autocomplete_focus" autocomplete="off">
									<div class="autocomplete-label" style="display: block;">
										<i class="icon-plus"></i>
										<p>Add Item</p>
									</div>
									<input type="hidden" id="morra_autocomplete_mac1"/>
								</td>
								<td class="no-padding"><input name="quantities" class="amount count price required" type="text" autocomplete="off" disabled/></td>
								<td class="no-view"></td>
								<td class="no-view"></td>
							</tr>
						</tbody>
					</table>
				</form:form>

				<h4 class="vrs-inventror-sumitem">
					<em property="italic" id="totalITem">0 Item</em>
				</h4>

			</div>



		</div>
		<div class="clear"></div>
	</div>
</div>
