<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:url var="invoice_edit" value="/sales/invoice/edit" />
<c:url var="invoice_view_url" value="/sales/invoice" />

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/" />
<c:set var="invoice_edit" value="/sales/invoice/edit" />
<c:set var="invoice_view_url" value="/sales/invoice" />
</c:if>

<div class="content">
	<form class="invoice_detail" method="POST" action="${invoice_edit}/${data.id}">
		<div class="main-content withright">
			<!-- heading tittle -->
			<h1>nota jual (${data.refNumber})</h1>
			<!-- header info -->
			<!-- table -->
			<br />
			<br />
			<input type="hidden" name="modified" value="1" />
			<table class="invoice_table">
				<colgroup>
					<col style="width: 60px; text-align: right;">
					<col style="width: 117px">
					<col style="width: 90px">
					<col style="width: auto">
					<col style="width: auto">
					<col style="width: auto">
					<col style="width: 40px">
				</colgroup>
				<thead>
					<tr>
						<td align="right">QTY</td>
						<td>UNIT</td>
						<td>SKU</td>
						<td>ITEM</td>
						<td align="right">HARGA</td>
						<td align="right">SUBTOTAL</td>
						<td>&nbsp;</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${data.invoiceDetails}" var="detail"
						varStatus="status">
						<tr id="first">
							<td align="right" class="borderright editable"><p class="number">
									<input type="hidden" name="inv_detail_id[]"
										class="inv_detail_id" id="qty-" value="${detail.id}">
									<input type="text" name="quantity[]" class=" text-right qty short number-filter" id="qty-"
										value="${detail.qty}">
									<input type="hidden" name="weight[]" class="weight" value="${detail.item.weight}"/>
								</p></td>
							<td class="item-container editable"><span>
							<select class="select unit focus-tab " target-action="create_new_row()" name="unit[]"  >
								<c:if test="${detail.unit.trim() == '1'}">
									<option value="1">Biji</option>
								</c:if>
								<c:if test="${detail.unit.trim() != '1'}">
									
								<option value="undefined">Box [${detail.unit}]</option>
								</c:if>
							</select>
							</span></td>
							<td><div class="sku">${detail.sku}</div> <input
								type="hidden" name="sku[]" id="sku-${status.index}"
								value="${detail.sku}" /></td>
							<td class="product-container"><input placeholder="Type product.." name="itemId[]" id="product-${status.index}" type="text" class="product_name autocomplete"
								value="[{ id:${detail.item.id}, name:'${detail.item.vinCatalogs.title}',  pricing: [{price: '${detail.price}' }] , weight : '${detail.item.weight}' }]">
							</td>
							<td align="right" class="number"><div class="price prices">${detail.price}</div>
								<input type="hidden" name="price[]" id="price-${status.index}"
								value="${detail.price}" /></td>
							<td align="right" class="number"><div class="subtotal price">${detail.subtotal}</div>
								<input type="hidden" name="subtotal[]"
								id="subtotal-${status.index}" value="${detail.subtotal}" /></td>
							<td align="center"><a class="delete-row" href="#hapus"><i
									class="icon-close"></i></a></td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="7"><a href="javascript:void('Tambah Barang')"
							class="add_item">Tambah Barang [F5]</a></td>
					</tr>
				</tfoot>
			</table>
		</div>
		<div class="rightheader">
			<div class="btn-area">
				<button type="submit" class="btn positive">
					<i class="icon-file icon-white"></i>SIMPAN
				</button>
				<a href="${invoice_view_url}" class="btn netral close"><i class="icon-cancel icon-grey"  ></i>KEMBALI</a>
			</div>
			<div class="totalbar "><span class="total-weight">${data.totalWeight}</span><small>kg</small> <span class="total-item">${data.totalItem}</span><small>items</small>
				<br>
				<div class="fl">
					TOTAL<br> <span>IDR</span>
				</div>
				<div class="fr">
					<span class="total-price price">${data.totalAmount}</span>
				</div>
				<div class="clear"></div>
			</div>
			<input type="hidden" name="total-weight" value="0"> <input
				type="hidden" name="total-item" value="0"> <input
				type="hidden" name="total-price" value="0">
			<div class="form">
				<div class="field">
					<label>Nota Pesan:</label> <input class="fr normal disabled"
						type="text text-right" name="salesorderLabel" value="${data.soRefNumber }" disabled>
						<input class="fr normal" type="hidden" name="salesorder" value="0">
				</div>
				<div class="clear"></div>
				<div class="inputholder">
					<label>Pelanggan:</label> <input placeholder="Select customer..."
						name="customer" id="customer" type="text"
						class=" medium text-right border-bottom" />
					<textarea class="long " name="status" readonly>${data.customerAddress}</textarea>
				</div>
				<div class="field">
					<label>Customer PO:</label> <input class="fr semi-medium  text-right" type="text"
						name="customerpo" value="${data.customerPO }">
				</div>
				
				<div class="field">
					<label>Discount:</label>
					<input class="fr  semi-medium  text-right" type="text" name="discount" id="discount" target-tab="#ppn" value="${data.discount == null ? 0 : data.discount }">
				</div>
				
				<div class="field">
					<div class="checkbox">
						<div class="checkbox-field">
							<input id="useppn" name="useppn" target-tab="#ppn" class="focus-tab" type="checkbox" value="false" ${inputold.useppn[0] != null ? "checked" : ""} >
							<label>PPN</label>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<div id="ppn-container" style='${inputold.useppn[0] != null ? "" : "display:none"} '>
					<div class="field">
						<label>PPN:</label>
						<input class="fr  semi-medium  focus-tab text-right" type="text" name="ppn" id="ppn" target-tab="#taxNumber" value="${data.ppn}"  ${inputold.useppn[0] != null ? "" : "disabled"} >
					</div>
					<div class="field">
						<label>Faktur Pajak:</label>
						<input class="fr semi-medium focus-tab text-right" type="text" name="taxNumber" id="taxNumber" target-tab="#shopcode" value="${data.taxNumber}" ${inputold.useppn[0] != null ? "" : "disabled"} >
					</div>
				</div>
				
				<div class="field">
					<label>Kode Toko:</label>
					<input class="fr  semi-medium  focus-tab text-right" type="text" name="shopcode" id="shopcode" target-tab="#senddate" value="${data.shopcode}">
				</div>
				
				<div class="field">
					<label>Tgl Kirim:</label>
					<div class="field-content fr">
						<div class="input-date input-append">
							<div class="input-icon">
								<i class="icon-calendar"></i>
							</div>
							<input id="senddate" type="text" class="datepicker focus-tab" target-type="select" target-tab="#salesperson"  style="width: 67px"
								name="senddate" value="${data.deliveryDate }" />
						</div>
					</div>
				</div>
				<div class="field">
					<label>Sales person:</label>
					<div class="field-content fr">
						<select id="salesperson" class="select focus-tab " target-type="select" target-tab="#priceList"  name="salesperson">
							<c:forEach items="${salesList}" var="salesPerson">
								<option value="${salesPerson.key }">${salesPerson.value }</option>
							</c:forEach>
						</select>
						<div class="clear"></div>
					</div>
				</div>
				<div class="field">
					<label>Daftar Harga:</label>
					<div class="field-content fr">
						<input id="priceList"  type="text" value="${data.priceList}" class="semi-medium text-right" readonly> 
						<div class="clear"></div>
					</div>
				</div>
			</div>
		</div>
	</form>

	<div class="clear"></div>

	<div style="display: none">
		<div class="popup alert" id="popup">
			<div class="head_title">
				PERHATIAN<a class="fr"><i class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Anda harus memilih customer terlebih dahulu. Input customer
					berada pada sidebar sebelah kanan!!!</p>
				<br /> <a href="javascript:void(0)" class="btn positive"
					id="customer-goto" style="margin-left: 51px;">PILIH CUSTOMER</a>
			</div>
		</div>
	</div>
</div>