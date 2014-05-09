<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:url var="packinglist_create_url" value="/sales/packing-list/create/" />
<c:url var="packinglist_url" value="/sales/packing-list/" />
<c:url var="salesorder_url" value="/sales/sales-orders" />

<c:url var="baseURL" value="/sales/sales-orders"/>
<c:url var="URL" value="/"/>
<c:set var="onServer" value="0"/>

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/"/>
<c:set var="packinglist_create_url" value="/sales/packing-list/create/" />
<c:set var="packinglist_url" value="/sales/packing-list/" />
<c:set var="salesorder_url" value="/sales/sales-orders" />
</c:if>

<div class="content">
	<form method="POST" action="${packinglist_create_url}${id_so}/${box}">
		<div class="main-content withright">
			<!-- heading tittle -->
			<h1>create packing list</h1>
			<!-- header info -->
			<div class="header_info">
				<div class="fl" >
					<div class="packingname box_number" style="position:relative;z-index:9${box == 0 ? '' : ';border:2px solid #959595'}" box_number="0">
						<a href="${packinglist_url}${id_so}">&nbsp;${data.salesResult.refNumber}&nbsp;</a>
					</div>
					<div class="line"></div>
					<ul class="packnumber" style="position:relative;z-index:9">
						<c:forEach items="${data.usedItem}" var="box_item">
							<li class="box_number" style="${box != 0 && box == box_item.box_number ? ';border:2px solid #000000' : '' }" box_number="${box_item.box_number}"  ><a href="${packinglist_url}${id_so}/${box_item.box_number}" >${box_item.box_number}</a><input type="hidden" name="packnumber[]" value="${box_item.box_number}"></li>
						</c:forEach>
						<c:if test="${empty data.usedItem}">
							<li  class="box_number" box_number="1"><a href="${packinglist_url}${id_so}/1" >1</a><input type="hidden" name="packnumber[]" value="1"></li>
						</c:if>
						<li><a href="javascript:void(0);" id="add-pack">+</a></li>
					</ul>
				</div>
				<div class="fr">
					<span>Box
					<span id="box">${box == 0 ? "free" : box}</span>. <span id="item-left">12</span> Item.</span>
				</div>
				<div class="clear"></div>
			</div>
			<!-- table -->
			<table class="free-box" style='${box == 0 ? "" : "display:none"}'>
				<colgroup>
					<col style="width: 60px; text-align: right;"></col>
					<col style="width: 60px"></col>
					<col style="width: 90px"></col>
					<col style="width: auto"></col>
					<col style="width: 40px"></col>
				</colgroup>
				<thead>
					<tr>
						<td align="right">QTY</td>
						<td>UNIT</td>
						<td>SKU</td>
						<td>ITEM</td>
						<td>&nbsp;</td>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${data.exist == true}">
							<c:forEach items="${data.freeItem.details}" var="salesOrder">
								<tr id="first"	style="background-color: #F6F6F6; z-index: 1">
									<td align="right" class="borderright">
										<p class="number">${salesOrder.quantity}</p>
										<input type="hidden" name="qty[]" value="${salesOrder.quantity}">
										<input type="hidden" name="detail" value="${salesOrder.id}">
									</td>
									<td>
										<span>${salesOrder.unit == 1 ? "Biji" : salesOrder.unit}</span>
										<input type="hidden" name="unit[]" value="${salesOrder.unit}">
									</td>
									<td>${salesOrder.sku}
										<input type="hidden" name="sku[]" value="${salesOrder.sku}"></td>
									<td>${salesOrder.item.vinCatalogs.title}
										<input type="hidden" name="item[]" value="${salesOrder.item.id}"></td></td>
									<td>
										<div class="btn-group icon fr">
											<a  href="javascript:void();" class="btn" style="margin-right:0">
												<i class="icon-justify"></i>
											</a>
											<ul class="rg">
												<li><a href="javascript:void();" class="transfer-item" box="1">Pindah Box</a></li>
												<li><a href="javascript:void();" class="split-item" data-item="${salesOrder.quantity}">Split Item</a></li>
											</ul>
										</div>
									</td>
								</tr>
							</c:forEach>
						</c:when>

						<c:otherwise>
							<c:forEach items="${data.itemResult}" var="salesOrder">
								<tr id="first"	style="background-color: #F6F6F6; z-index: 1">
									<td align="right" class="borderright">
										<p class="number">${salesOrder.quantity}</p>
										<input type="hidden" name="qty[]" value="${salesOrder.quantity}">
									</td>
									<td>
										<span>${salesOrder.unit == 1 ? "Biji" : salesOrder.unit}</span>
										<input type="hidden" name="unit[]" value="${salesOrder.unit}">
									</td>
									<td>${salesOrder.sku}
										<input type="hidden" name="sku[]" value="${salesOrder.sku}"></td>
									<td>${salesOrder.itemName}
										<input type="hidden" name="item[]" value="${salesOrder.itemId}"></td></td>
									<td>
										<div class="btn-group icon fr">
											<a  href="javascript:void();" class="btn" style="margin-right:0">
												<i class="icon-justify"></i>
											</a>
											<ul class="rg">
												<li><a href="javascript:void();" class="transfer-item" box="1">Pindah Box</a></li>
												<li><a href="javascript:void();" class="split-item" data-item="${salesOrder.quantity}">Split Item</a></li>
											</ul>
										</div>
									</td>

								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>

					<c:if test="${data.exist == true && empty data.freeItem.details}">
							<tr id="first"  class="drag-helper">
								<td align="center" colspan="5"><p >Item sudah di pindah ke semua box</p></td>
							</tr>
					</c:if>
					<c:if test="${data.exist == false && empty data.itemResult}">
							<tr id="first"  class="drag-helper">
								<td align="center" colspan="5"><p >Tidak ada item yang dapat diproses silahkan pilih Nota Pesan yang lain</p></td>
							</tr>
					</c:if>
				</tbody>
			</table>
			<table class="current-box" style='${box != 0 ? "" : "display:none"}'>
				<colgroup>
					<col style="width: 60px; text-align: right;"></col>
					<col style="width: 60px"></col>
					<col style="width: 90px"></col>
					<col style="width: auto"></col>
					<col style="width: 40px"></col>
				</colgroup>
				<thead>
					<tr >
						<td align="right">QTY</td>
						<td>UNIT</td>
						<td>SKU</td>
						<td>ITEM</td>
						<td>&nbsp;</td>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${data.exist == true && !empty data.currentItem.details}">
							<c:forEach items="${data.currentItem.details}" var="salesOrder">
								<tr id="first"	style="background-color: #F6F6F6; z-index: 1">
									<td align="right" class="borderright">
										<p class="number">${salesOrder.quantity}</p>
										<input type="hidden" name="qty-${box}[]" value="${salesOrder.quantity}">
										<input type="hidden" name="detail" value="${salesOrder.id}">
									</td>
									<td>
										<span>${salesOrder.unit == 1 ? "Biji" : salesOrder.unit}</span>
										<input type="hidden" name="unit-${box}[]" value="${salesOrder.unit}">
									</td>
									<td>${salesOrder.sku}
										<input type="hidden" name="sku-${box}[]" value="${salesOrder.sku}"></td>
									<td>${salesOrder.item.vinCatalogs.title}
										<input type="hidden" name="item-${box}[]" value="${salesOrder.item.id}"></td></td>
									<td><a href="javascript:void();" class="freeup-item"><i class="icon-close"></i></a></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
								<tr id="first"  class="drag-helper">
									<td align="center" colspan="5"><p >Belum ada item di box ini</p></td>
								</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>

			<table class="helper-box" style="display:none">
			</table>
			<table class="helper-deleted" style="display:none">
			</table>
		</div>
		<div class="rightheader">
			<div class="btn-area">
				<button class="btn positive"><i class="icon-file  icon-white"></i>SIMPAN</button>
				<a href="${salesorder_url}" class="btn netral close"><i class="icon-cancel icon-grey"></i>KEMBALI</a>
			</div>
			<div class="totalbar ">
				${data.weight_count}<small>kg</small> ${data.unit_count}<small>items</small>
			</div>
			<div class="form">
				<div class="field">
					<label>Nota Pesan:</label>
					<input type="text" class="fr normal " value="${data.salesResult.refNumber}" readonly/>
				</div>
				<div class="clear"></div>
				<div class="inputholder">
					<label>Pelanggan:</label> <input type="text" class="medium text-right border-bottom" name="customerName" value="${data.customerName}" readonly><input type="hidden" name="customer" value="${data.customerId}">
					<textarea class="long" readonly> ${data.address}</textarea>
				</div>
			</div>
		</div>
	</form>
	<div class="clear"></div>
	<div style="display: none">
		<div class="popup alert" id="popup"  style="width: 225px !important">
			<div class="head_title">
				MASUKKAN QUANTITY <a href="javascript:void(0);" class="fr close-popup"><i
					class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
			
				<div class="field-content">
					<center>
						<input id="split-item" placeholder="Masukkan qty" type="text" class="input-small required text-center" minlength="5">
						<!-- <p class="help-block">Masukkan banyak item yang ining di split</p> -->
					</center>
				</div>
				<br />
				<center>
					<a href="javascript:void(0)" class="btn positive" id="change-item"  >Split Item</a>
				</center>
			</div>
		</div>
	</div>
</div>