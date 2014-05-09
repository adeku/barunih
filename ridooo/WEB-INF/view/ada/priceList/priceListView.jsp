<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="editPrice" value="/inventory/pricelist/do-edit-pricelist" />
<script>
	$(document).ready(function() {
		// PRICELIST POPUP
		$('#c_popup').click(function(e) {
			e.preventDefault();
			$(this).popup({
				title : 'Create new price list',
				width : "480px",
				html : $('#popup_pricelist').html()
			});
		});
		
		$('.toedit').click(function(e) {
			e.preventDefault();
			$(this).popup({
				title : 'Edit price list',
				width : "480px",
				html : $('#popupedit_pricelist').html()
			});
			
			$('#frEditPricelist [name="idDAta"]').val($(this).attr('id'));
			$('#frEditPricelist [name="txPriceName"]').val($(this).attr('titleProduct'));
			$('#frEditPricelist [name="msrp"]').val($(this).attr('msrp'));
			$('#frEditPricelist [name="txPrice"]').val($(this).attr('price'));
			$('#frEditPricelist [name="txSalePrice"]').val($(this).attr('saleprice'));
			
		});

		$('body').on('submit','#frEditPricelist',function(e){
			e.preventDefault();
			$.post( "${editPrice}", $(this).serialize() ,function( result ) {
				
			});	    
		  });
	});
</script>
<!-- POP UP PRICELIST MODAL -->
<div id="popup_pricelist" style="display: none;">
	<div class="candidate vrs-invenprcls-modal" style="width: 450px">
		<form class="form-horizontal" method="POST">
			<div class="control-group">
				<label class="control-label">Name</label>
				<div class="controls">
					<div class="input-append">
						<input name="txPriceName" class="input-medium" size="16"
							type="text">
					</div>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">Default Price</label>
				<div class="input-append controls">
					<input type="text" name="txPersent" onKeyUp="onlyPercent(this);"
						class="input-mini url"><span class="add-on">% Off</span>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">From</label>
				<div class="controls">
					<select class="input-small" name="selectFromPrice">
						<c:forEach items="${fromOption}" var="fromOption1">
							<option>${fromOption1.price_name}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-actions">
				<input type="submit" class="btn btn-positive" value="Create"/>
				<button type="reset" class="btn">Cancel</button>
			</div>
		</form>
	</div>
</div>
<!-- POP UP PRICELIST MODAL -->


<!-- POP UP PRICELIST for edit MODAL -->
<div id="popupedit_pricelist" style="display: none;">
	<div class="candidate vrs-invenprcls-modal" style="width: 450px">
		<form class="form-horizontal" id="frEditPricelist" name="frEditPricelist" method="POST">
		<input type="hidden" name="idDAta">
			<div class="control-group">
				<label class="control-label">Name</label>
				<div class="controls">
					<div class="input-append">
						<input name="txPriceName" class="input-medium" size="16"
							type="text">
					</div>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">M S R P</label>
				<div class="input-append controls">
					<input type="text" name="msrp" onKeyUp="onlyPercent(this);"
						class="input-mini url">
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label">Price</label>
				<div class="input-append controls">
					<input type="text" name="txPrice" onKeyUp="onlyPercent(this);"
						class="input-mini url">
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label">Sale Price</label>
				<div class="input-append controls">
					<input type="text" name="txSalePrice" onKeyUp="onlyPercent(this);"
						class="input-mini url">
				</div>
			</div>

			<div class="form-actions">
				<button type="submit" class="btn btn-positive">Create</button>
				<button type="reset" class="btn">Cancel</button>
			</div>
		</form>
	</div>
</div>
<!-- POP UP PRICELIST  for edit  MODAL -->



<!-- CONTENT -->
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content vrs-invenprcls">
				<form id="bpuForm" name="bpuForm" method="POST">
					<input type="hidden" name="priceListSelected" value=""> <input
						type="hidden" name="brandSelected" value="">
					<div class="content-header">
						<h1>PRICE LIST</h1>
					</div>

					<div class="vrs-invenprcls-sr">
					<c:url var="createCatalog" value="/inventory/pricelist/csv-import" />
					<a href="${createCatalog}" class="btn">Import CSV file</a>
						<div class="btn-group dropdown dropdown-large fr">
							<div class="vrs-invenprcls-srs search">
									<i class="icon-search"></i> <input id="searchProduct"
										name="searchProduct" class="input-small" type="text" 
										onBlur="searchProductinPriceList()"
										 value = "${searchTEXT}"
										placeholder="Search">
							</div>
						</div>
						<div class="btn-group dropdown dropdown-large fr">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								${buttonBrandsSelected}<span class="vrs-invenprcls-cr caret"></span>
							</button>							
							<ul class="vrs-invenprcls-ddr dropdown-menu">

								<c:forEach items="${BrandOptions}" var="BrandOptions1">
									<c:if test="${buttonBrandsSelected!=BrandOptions1.price_name}">
										<li><a href="javascript:void(0);"
											onClick="changeBrandsOption('${BrandOptions1.brand_name}')">${BrandOptions1.brand_name}</a></li>
									</c:if>
								</c:forEach>

							</ul>
						</div>
						<div class="btn-group dropdown dropdown-large fl">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								${buttonpriceListSelected}<span class="vrs-invenprcls-cr caret"></span>
							</button>
							<ul class="vrs-invenprcls-ddr dropdown-menu">

								<c:forEach items="${shoesPriceListOptions}"
									var="shoesPriceListOptions1">
									<c:if
										test="${buttonpriceListSelected!=shoesPriceListOptions1.price_name}">
										<li><a href="javascript:void(0);"
											onClick="changePriceListOption('${shoesPriceListOptions1.price_name}')">
												${shoesPriceListOptions1.price_name} </a></li>
									</c:if>
								</c:forEach>

								<li><a href="javascript:void(0);" id="c_popup"><i
										class="vrs-invenproduct-bluplus"></i>Create New Price List</a></li>
							</ul>
						</div>
					</div>

					<table class="table table-striped">

						<colgroup>
							<col>
							<col class="w150">
							<col class="w150">
						</colgroup>

						<thead>
							<tr>
								<th>Item</th>
								<th class="align-right">MSRP (Rp.)</th>
								<th class="align-right">${headerColumn2} (Rp.)</th>
							</tr>
						</thead>

						<tbody>

							<c:forEach items="${viewPriceList}" var="viewPriceList1">
								<tr>
									<td><span class="avatar fl"><img
											src="${viewPriceList1.imageProduct}" width="50" height="50"
											alt="Avatar"></span> <span class="avatar-pull">
											<h4>
												<a href="#" class="toedit" titleProduct="${viewPriceList1.titleProduct}" 
												msrp="${viewPriceList1.msrp}" 
												price="${viewPriceList1.price}" 
												saleprice="${viewPriceList1.saleprice}" 
												id="${viewPriceList1.id}">${viewPriceList1.titleProduct}</a>
											</h4> <span>${viewPriceList1.skuProduct}</span>
									</span></td>
									<td class="vrs-invenprcls-amts amount no-padding">${viewPriceList1.msrp}</td>
									<td class="vrs-invenprcls-amts amount no-padding">${viewPriceList1.price}</td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</form>
				${pagination}
			</div>
			<!-- END : CONTENT AREA -->
		</div>
		<div class="clear"></div>
	</div>
</div>
