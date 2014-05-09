<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/"/>
<c:set var="onServer" value="0"/>

<c:if test="${baseURL=='//'}">
<c:set var="onServer" value="1"/>
</c:if>

<c:choose>
	<c:when test="${action == 'edit' }">
		<c:url var="formURL" value="/purchase/receive-orders/${id}/edit" />
		<c:if test="${onServer=='1'}">
			<c:set var="formURL" value="/purchase/receive-orders/${id}/edit" />
		</c:if>
	</c:when>
	<c:otherwise>
		<c:url var="formURL" value="/purchase/receive-orders/create" />
		<c:if test="${onServer=='1'}">
			<c:set var="formURL" value="/purchase/receive-orders/${id}/edit" />
			<c:if test="${onServer=='1'}">
				<c:set var="formURL" value="/purchase/receive-orders/create" />
			</c:if>
		</c:if>
	</c:otherwise>
</c:choose>

<div class="content">
	<form:form modelAttribute="form" method="POST" action="${formURL}">
		<div class="main-content withright">
			<!-- heading tittle -->
			<c:choose>
				<c:when test="${action == 'edit' }">
					<h1>surat terima (${form.refNumber})</h1>
				</c:when>
				<c:otherwise>
					<h1>surat terima baru</h1>
				</c:otherwise>
			</c:choose>
			<!-- header info -->
			
			<!-- table -->
			<table class="invoice_table" style="${empty form.transactionDetails ? 'display:none' : '' }">
				<colgroup>
					<col style="width:60px;text-align:right;"></col>
					<col style="width:60px"></col>
					<col style="width:120px"></col>
					<col style="width:auto"></col>
					<col style="width:160px"></col>
					<col style="width:170px"></col>
				</colgroup>
				<thead>
					<tr>
						<td align="right">QTY</td>
						<td>UNIT</td>
						<td>SKU</td>
						<td>ITEM</td>
						<td align="right">GUDANG</td>
						<td align="right">RAK</td>
					</tr>
				</thead>
				<tbody>
				
				<c:forEach items="${form.transactionDetails}" var="detail" varStatus="pointer">
					<tr id="">
						<form:hidden path="transactionDetails[${pointer.index}].detailId"/>
						<form:hidden path="transactionDetails[${pointer.index}].documentId"/>
						<form:hidden path="transactionDetails[${pointer.index}].itemId"/>
						<form:hidden path="transactionDetails[${pointer.index}].quantity"/>
						<form:hidden path="transactionDetails[${pointer.index}].unit"/>
						<form:hidden path="transactionDetails[${pointer.index}].sku"/>
						<td align="right" class="borderright">
							<p class="number">${detail.quantity }</p>
						</td>
						<td class="item-container">
							${detail.unit}
						</td>
						<td>
							${detail.sku}
						</td>
						<td>
							${detail.itemName}
						</td>
						<td  >
							<div class="field-content fr">
								<select name="transactionDetails[${pointer.index}].warehouseId" class="warehouse select validation" data-validation="required" >
									<c:forEach items="${warehouseOption}" var="warehouse">
										<c:choose>
											<c:when test="${warehouse.key == detail.warehouseId}">
												<option value="${warehouse.key}" selected>${warehouse.value}</option>
											</c:when>
											<c:otherwise>
												<option value="${warehouse.key}">${warehouse.value}</option>
											</c:otherwise>
										</c:choose>
										
									</c:forEach>
								</select>
							</div>
						</td>
						<td >
							<div class="field-content fr">
								<select name="transactionDetails[${pointer.index}].shelfId" class="shelf select validation" data-validation="required" >
										<c:forEach items="${detail.shelves}" var="shelf">
											<c:choose>
												<c:when test="${shelf.key == detail.shelfId}">
													<option value="${shelf.key}" selected>${shelf.value}</option>
												</c:when>
												<c:otherwise>
													<option value="${shelf.key}">${shelf.value}</option>
												</c:otherwise>
											</c:choose>
											
										</c:forEach>
								</select>
							</div>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			
			<div class="empty-state"
				style="${empty form.transactionDetails ? '' : 'display:none' }">
				<i class="empty-icon entry"></i>
				<h4>Silakan mulai dengan mengisi data supplier di sisi kanan.</h4>
			</div>
		</div>
		<div class="rightheader">
			<div class="btn-area">
				<button class="btn positive"><i class="icon-file icon-white"></i>SIMPAN</button>
				<a href="" class="btn netral"><i class="icon-grey icon-print"></i>CETAK</a>
			</div>
			<div class="totalbar ">
				<span class="total-weight">
					<c:choose>
						<c:when test="${action.equals('edit')}">
							${form.totalWeight}
						</c:when>
						<c:otherwise>
							0
						</c:otherwise>
					</c:choose>
				</span>
				<small>kg</small>
				<form:hidden path="totalWeight" />
				<span class="total-item">
					<c:choose>
						<c:when test="${action.equals('edit')}">
							${form.totalItem}
						</c:when>
						<c:otherwise>
							0
						</c:otherwise>
					</c:choose>
				</span>
				<form:hidden path="totalItem" />
				<small>items</small>
				<div class="clear"></div>
			</div>
			
			<div class="clear"></div>
			<c:choose>
				<c:when test="${form.document == 'vin_return_orders'}">
					<div class="form">
						<div class="inputholder">
							<label>Pelanggan:</label>
							<form:input path="supplierId" id="customer" cssClass="medium text-right border-bottom validation" data-validation="required"  placeholder="Select customer..." />
							<textarea class="long address"></textarea>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="form">
						<div class="inputholder">
							<label>Vendor:</label>
							<form:input path="supplierId" id="supplier" cssClass="medium text-right border-bottom validation" data-validation="required"  placeholder="Select supplier..." />
							<textarea class="long address"></textarea>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			
		</div>
		<div class="clear"></div>
	</form:form>
	<div style="display: none">
		<div class="popup alert" id="popup">
			<div class="head_title">
				PERHATIAN<a class="fr"><i class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Anda harus memilih supplier terlebih dahulu. Input supplier
					berada pada sidebar sebelah kanan!!!</p>
				<br /> <a href="javascript:void(0)" class="btn positive" id="supplier-goto" style="margin-left: 51px;">PILIH SUPPLIER</a>
			</div>
		</div>
	</div>
</div>