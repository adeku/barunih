<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- CONTENT -->
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">

			<div class="grid-content vrs-invenprodux vrs-invenitem">
				<div class="content-header">
				<c:if test="${isAdmin}">
					<div class="helper">
					<c:url var="manageWarehousePAge" value="/accounts/companies" />
						<a href="${manageWarehousePAge}" class="btn fr">Manage Warehouses</a>
					</div>
				</c:if>
					<h1>Warehouses</h1>
					<p class="subtitle">The followings are locations that carry
						inventory</p>
				</div>

				<br />
				<c:choose>
					<c:when test="${empty warehouseList}">
						<div class="empty-state book">
							<h2>Warehouse empty</h2>
						</div>
					</c:when>

					<c:otherwise>
						<table class="table table-striped">
							<colgroup>
								<col>
								<col class="w100">
								<col class="w100">
							</colgroup>
							<thead>
								<tr>
									<th>Warehouses</th>
									<th class="align-left">&nbsp;</th>
									<th class="align-right">&nbsp;</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${warehouseList}" var="warehouseList1" varStatus="status">
									<tr class="vrs-invenitem-tradd">
										<td>
											<h4>
												<a href="#" class="vrs-invensto-tra">${warehouseList1.wareHouseName}</a>
											</h4> <span>${warehouseList1.street1} &mdash;
												${warehouseList1.city}</span>
										</td>
										<td class="align-left"><c:if
												test="${warehouseList1.status==0}">
												<span class="label label-important">Disable</span></td>
										</c:if>
										<c:if test="${warehouseList1.status==1}">
											<span class="label label-success">Enable</span>
										</c:if>

										<td class="align-right">
											<div class="btn-group dropdown fr">
												<a href="#" class="btn show-extend">Edit</a>
												<button class="btn btn-65 dropdown-toggle"
													data-toggle="dropdown">
													<span class="caret"></span>
												</button>
												<ul class="dropdown-menu pull-left">
													<c:if test="${warehouseList1.status==0}">
														<li><a href="javascript:void(0)" onClick="enableWarehouseinTabInventory(1,'warehouse_${warehouseList1.wareHouseID}')">Enable</a></li>
													</c:if>
													<c:if test="${warehouseList1.status==1}">
														<li>
															<a class="${warehouseList1.delPopUp}" href="javascript:void(0)">Disable</a>
															<input name="idData" type="hidden" 
															 value="warehouse_${warehouseList1.wareHouseID}">
														</li>														
													</c:if>
													<li><a class="${warehouseList1.delPopUp}x" href="javascript:void(0)">Remove</a><input name="idData" type="hidden" 
															 value="warehouse_${warehouseList1.wareHouseID}"></li>													
												</ul>												
											</div>
											
										</td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
				${pagination}
			</div>



		</div>
		<div class="clear"></div>
	</div>
</div>


<div id="popup_companies1" style="display: none;">
		<div class="candidate vrs-invenprcls-modal" style="width: 450px">
			<form class="form-horizontal" name="formTransferOrder"
				id="formTransferOrder" method="POST" onSubmit="return createTransferORderInventorytab()">
				<input type="hidden" name="origin">
				<p>By deleting a warehouse, all active inventories will be
					transfered to another warehouse.</p>

				<fieldset class="vrs-comp-popupset">
					<label>Please select a destination warehouse</label> <select
						class="input-medium" name="wareHouseDestination"
						id="wareHouseDestination">
					</select>
				</fieldset>

				<fieldset class="vrs-comp-popupset">
					<label>Please provide a reason</label>
					<textarea name="reason" rows="3"></textarea>
				</fieldset>

				<div class="form-actions vrs-comp-popupsubmit">
					<button type="submit" class="btn btn-negative">Disable</button>
					<button type="reset" class="btn" onClick="$.colorbox.close();">Cancel</button>
				</div>
			</form>
		</div>
	</div>

	<div id="popup_companies2" style="display: none;">
		<div class="candidate vrs-invenprcls-modal" style="width: 450px">
			<form class="form-horizontal" name="formTransferOrder2"
				id="formTransferOrder2" method="POST" onSubmit="return disableWarehouseInventorytab()">
				<input type="hidden" name="origin2">
				<fieldset class="vrs-comp-popupset">
					<label>No active inventory found. Please provide a reason</label>
					<textarea name="reason2" rows="3"></textarea>
				</fieldset>

				<div class="form-actions vrs-comp-popupsubmit">
					<button type="submit" class="btn btn-negative">Disable</button>
					<button type="reset" class="btn" onClick="$.colorbox.close();">Cancel</button>
				</div>
			</form>
		</div>
	</div>

	<div id="popup_companies3" style="display: none;">
		<div class="candidate vrs-invenprcls-modal" style="width: 450px">
			<form class="form-horizontal" method="POST">
				<p>You have no other locations that can carry inventory.</p>

				<p>
					<c:url var="manageWarehouse" value="/inventory/warehouses" />
					<a href="${manageWarehouse}">Manage Warehouse</a>
				</p>

				<div class="form-actions vrs-comp-popupsubmit">
					<button type="reset" class="btn" onClick="$.colorbox.close();">Cancel</button>
				</div>
			</form>
		</div>
	</div>
	
	
	<div id="popup_companies1x" style="display: none;">
		<div class="candidate vrs-invenprcls-modal" style="width: 450px">
			<form class="form-horizontal" name="formTransferOrderx"
				id="formTransferOrderx" method="POST" onSubmit="return createTransferORderForDELeteInventorytab()">
				<input type="hidden" name="originx">
				<p>By deleting a warehouse, all active inventories will be
					transfered to another warehouse.</p>

				<fieldset class="vrs-comp-popupset">
					<label>Please select a destination warehouse</label> <select
						class="input-medium" name="wareHouseDestinationx"
						id="wareHouseDestinationx">
					</select>
				</fieldset>

				<fieldset class="vrs-comp-popupset">
					<label>Please provide a reason</label>
					<textarea name="reasonx" rows="3"></textarea>
				</fieldset>

				<div class="form-actions vrs-comp-popupsubmit">
					<button type="submit" class="btn btn-negative">Hapus</button>
					<button type="reset" class="btn" onClick="$.colorbox.close();">Batal</button>
				</div>
			</form>
		</div>
	</div>

	<div id="popup_companies2x" style="display: none;">
		<div class="candidate vrs-invenprcls-modal" style="width: 450px">
			<form class="form-horizontal" name="formTransferOrder2x"
				id="formTransferOrder2x" method="POST" onSubmit="return removeWarehouseInventorytab()">
				<input type="hidden" name="origin2x">
				<fieldset class="vrs-comp-popupset">
					<label>No active inventory found. Please provide a reason</label>
					<textarea name="reason2x" id="reason2x" rows="3"></textarea>
				</fieldset>

				<div class="form-actions vrs-comp-popupsubmit">
					<button type="submit" class="btn btn-negative">Hapus</button>
					<button type="reset" class="btn" onClick="$.colorbox.close();">Batal</button>
				</div>
			</form>
		</div>
	</div>
	
<script>
	$(document)
			.ready(
					function() {
						var click = false;
						$('table a.btn-white').hover(function() {
							var child = $(this).find('.custom-icon');

							if (child.hasClass('check')) {
								p_name = 'check';
								c_name = 'red-cross';
							} else if (child.hasClass('gray-cross')) {
								p_name = 'gray-cross';
								c_name = 'check';
							}
							child.toggleClass(p_name);
							child.toggleClass(c_name);
						}, function() {
							if (click == false) {
								var child = $(this).find('.custom-icon');
								child.toggleClass(c_name);
								child.toggleClass(p_name);
							} else {
								click = false;
							}
						});

						$('table a.btn-white').click(
								function(e) {
									click = true;
									var hid = $(this).parent().find(
											'input[type="hidden"]');
									var child = $(this).find('.custom-icon');

									child.removeClass(c_name);
									if (hid.val() == 0) {
										hid.val(1);
										child.addClass('check');
										doChangeWarehousestatus(hid.val(), hid
												.attr("name"));
									} else {
										hid.val(0);
										child.addClass('gray-cross');

									}
								});

						$('.delete_popup1').click(
										function(e) {
											var hid = $(this).parent().find('input[type="hidden"]');
												$.ajax(
																{
																	type : "POST",
																	url : site_url
																			+ 'accounts/companies/list-warehouse-destination',
																	data : {
																		'company' : hid.val()
																	}
																})
														.done(
																function(
																		resPonseText) {
																	var obj = jQuery
																			.parseJSON(resPonseText);
																	document.formTransferOrder.origin.value = hid.val();
																	var dataRow = obj.wareHouseList;
																	var selectWarehosue = $('#wareHouseDestination');
																	selectWarehosue
																			.empty();
																	for (i = 0; i < dataRow.length; i++) {
																		selectWarehosue
																				.append('<option value="'+dataRow[i].id+'">'
																						+ dataRow[i].val
																						+ '</option>');
																	}
																	$(this)
																			.popup(
																					{
																						width : "480px",
																						title : "DISABLE WAREHOUSE",
																						html : $(
																								'#popup_companies1')
																								.html()
																					});
																});
										});

						$('.delete_popup2').click(
										function(e) {
											var hid = $(this).parent().find('input[type="hidden"]');
												document.formTransferOrder2.origin2.value = hid.val();
												$(this)
														.popup(
																{
																	width : "480px",
																	title : "DISABLE WAREHOUSE",
																	html : $(
																			'#popup_companies2')
																			.html()
																});
										});

						$('.delete_popup3').click(
										function(e) {
											var hid = $(this).parent().find('input[type="hidden"]');
												e.preventDefault();
												$(this)
														.popup(
																{
																	width : "480px",
																	title : "DISABLE WAREHOUSE",
																	html : $(
																			'#popup_companies3')
																			.html()
																});
										});
						
						
						
						
						$('.delete_popup1x').click(
								function(e) {
									var hid = $(this).parent().find('input[type="hidden"]');
										$.ajax(
														{
															type : "POST",
															url : site_url
																	+ 'accounts/companies/list-warehouse-destination',
															data : {
																'company' : hid.val()
															}
														})
												.done(
														function(
																resPonseText) {
															var obj = jQuery
																	.parseJSON(resPonseText);
															document.formTransferOrderx.originx.value = hid.val();
															var dataRow = obj.wareHouseList;
															var selectWarehosue = $('#wareHouseDestinationx');
															selectWarehosue
																	.empty();
															for (i = 0; i < dataRow.length; i++) {
																selectWarehosue
																		.append('<option value="'+dataRow[i].id+'">'
																				+ dataRow[i].val
																				+ '</option>');
															}
															$(this)
																	.popup(
																			{
																				width : "480px",
																				title : "DELETE WAREHOUSE",
																				html : $(
																						'#popup_companies1x')
																						.html()
																			});
														});
								});

				$('.delete_popup2x').click(
								function(e) {
									var hid = $(this).parent().find('input[type="hidden"]');
										document.formTransferOrder2x.origin2x.value = hid.val();
										$(this)
												.popup(
														{
															width : "480px",
															title : "DELETE WAREHOUSE",
															html : $(
																	'#popup_companies2x')
																	.html()
														});
								});

				$('.delete_popup3x').click(
								function(e) {
									var hid = $(this).parent().find('input[type="hidden"]');
										e.preventDefault();
										$(this)
												.popup(
														{
															width : "480px",
															title : "DELETE WAREHOUSE",
															html : $(
																	'#popup_companies3')
																	.html()
														});
								});
						
						
						
					});
</script>
