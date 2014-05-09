<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="geItemsOption"
	value="/inventory/stock-adjustments/inventory-options" />
<c:url var="getOnhand" value="/inventory/stock-adjustments/getonhand" />
<c:url var="editData" value="/inventory/stock-adjustments/delData" />
<script>
	$(document)
			.ready(
					function() {
						core.pills_autocomplete({
							data : '${geItemsOption}',
							onAdd : function(a) {
								$.ajax({
									type : "POST",
									url : '${getOnhand}',
									data : {
										idTable : $('#txItem').val()
									}
								}).done(function(resPonseText) {
									$('#countOnHand').html(resPonseText);
								});

							},
							target_element : '#txItem'
						});

						$('.delete_popup')
								.click(
										function() {
											var tmp = $(this);
											$(this)
													.popup(
															{
																title : 'Confirm to Delete',
																hideClose : true,
																html : '<form id="deleteModel" method="POST">'
																		+ '<input type="hidden" name="idTable" value="'
																		+ tmp
																				.attr('id')
																		+ '">'
																		+ '<p>Are you sure to delete this item?</p><div class="control-group">'
																		+ '<div class="controls">'
																		+ '<textarea class="input-full"  id="reasonDelete" name="reasonDelete" placeholder="Reasons" id="textarea" rows="3"></textarea>'
																		+ '</div></div>'
																		+ '<div class="form-actions">'
																		+ '<button type="submit" class="btn btn-negative">Delete</button>'
																		+ '<button type="reset" class="btn" onClick="$.colorbox.close();">Cancel</button>'
																		+ '</div></form>',
																height : '250px'
															});
										});

					});
</script>
<!-- CONTENT -->
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content vrs-invenprodux vrs-invenitem vrs-invensto">
				<div class="content-header">
					<h1>Stock Adjustment</h1>
					<p class="subtitle">Manage stock item in every warehouse</p>
				</div>
				<c:if test="${success&&showMessage}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Well done!</strong> ${alertMessage}
					</div>
				</c:if>
				<c:if test="${!success&&showMessage}">
					<div class="alert alert-error">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>${alertMessage}</strong> ${alertMessage2}
					</div>
				</c:if>
				<form:form modelAttribute="stockadjustmentviewform" method="POST"
					class="vrs-invensto-head">
					<form:hidden path="idTable" />

					<div class="vrs-invensto-headup">
						<div class="field fl vrs-invensto-head-vendor">
							<label>Item</label>
							<div class="control pills-autocomplete-field">
								<div class="input-append">
									<form:input
										class="pills-autocomplete pills-autocomplete-form required"
										path="txItem" />
									<span class="add-on"><i class="icon-pencil"></i></span>
								</div>
							</div>
						</div>
						<div class="field fr vrs-invensto-head-whs">
							<label>Warehouse</label>
							<form:select path="warehouseId" items="${warehouseOptions}" />
						</div>
						<br class="clear" />
					</div>
					<div class="vrs-invensto-headdw">
						<div class="field fl vrs-invensto-head-oh">
							<label>On Hand</label> <strong class="vrs-f18" id="countOnHand">0</strong>
						</div>
						<div class="field fl vrs-invensto-head-adj">
							<label>Adjustment</label>
							<div class="control">
								<div class="input-append">
									<form:input path="txAdjustment" class="input-small" />
								</div>
							</div>
						</div>
						<div class="field fl vrs-invensto-head-rea">
							<label>Reason</label>
							<div class="control">
								<div class="input-append">
									<form:input path="txReason" />
								</div>
							</div>
						</div>
						<div class="field fr">
							<label>&nbsp; </label>
							<form:button path="btSave" class="btn btn-positive">Submit</form:button>
						</div>
						<br class="clear" />
					</div>
				</form:form>


				<table class="table table-striped">

					<colgroup>
						<col>
						<col class="w150">
						<col class="w100">
						<col class="w150">
						<col class="w100">
						<col class="w100">
						<col class="actions">
					</colgroup>

					<thead>
						<tr>
							<th>Item</th>
							<th class="align-left">Date</th>
							<th class="align-left">User</th>
							<th class="align-right">Stock Adjustment</th>
							<th class="align-right">Adj. Stock</th>
							<th class="align-left">Reason</th>
							<th class="align-right">&nbsp;</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${viewDAtaAdjustStock}"
							var="viewDAtaAdjustStock1">
							<tr class="vrs-invenitem-tradd">
								<td>
									<h4>${viewDAtaAdjustStock1.productTitle}</h4> <span><a
										href="#" class="alt-link">${viewDAtaAdjustStock1.productBrand}</a>
										&mdash; ${viewDAtaAdjustStock1.productSKU}</span>
								</td>
								<td class="align-left">${viewDAtaAdjustStock1.dateIN}</td>
								<td class="align-left"><a class="alt-link">${viewDAtaAdjustStock1.userInput}</a></td>
								<td class="align-right vmid"><span class="amount">${viewDAtaAdjustStock1.stokAdjustment}</span></td>
								<td class="align-right vmid"><span class="amount">${viewDAtaAdjustStock1.adjStock}</span></td>
								<td class="align-left vmid">${viewDAtaAdjustStock1.reason}</td>
								<td class="align-right">
									<div class="btn-group dropdown fr">
										<a href="#" class="btn"
											onClick="prepareEditStockAdjustment('${viewDAtaAdjustStock1.idORderDetail}')">Edit</a>
										<button class="btn btn-65 dropdown-toggle"
											data-toggle="dropdown">
											<span class="caret"></span>
										</button>
										<ul class="dropdown-menu pull-left">
											<li><a class="delete_popup" href="javascript:void(0)"
												style="color: #333"
												id="${viewDAtaAdjustStock1.idORderDetail}">Delete</a></li>
										</ul>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				${pagination}
			</div>

		</div>
		<div class="clear"></div>
	</div>
</div>
