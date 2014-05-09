<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:set var="onServer" value="0" />

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/" />
<c:set var="onServer" value="1" />
</c:if>

<c:choose>
	<c:when test="${action.equals('create')}">
		<c:url var="invoice_create" value="/sales/invoice/create" />
		<c:if test="${onServer=='1'}">
		<c:set var="invoice_create" value="/sales/invoice/create" />
		</c:if>
		<c:set var="save_label" value="SIMPAN" />	
	</c:when>
	<c:otherwise>
		<c:url var="invoice_create" value="/sales/invoice/generate-fake-detail" />
		<c:if test="${onServer=='1'}">
		<c:set var="invoice_create" value="/sales/invoice/generate-fake-detail" />
		</c:if>
		<c:set var="save_label" value="GENERATE" />
	</c:otherwise>
</c:choose>
<c:url var="invoice_view_url" value="/sales/invoice" />
<c:if test="${onServer=='1'}">
	<c:set var="invoice_view_url" value="/sales/invoice" />
</c:if>

<div class="content">
	<form class="invoice" method="POST" action="${invoice_create}">
		<div class="main-content withright">
			<!-- heading tittle -->
			<div class="header-info">
				<h1>nota jual baru</h1>
			</div>
			<!-- header info -->
			<!-- table -->
			<input type="hidden" name="modified" value="1" />
			<table class="invoice_table" style="${!empty  inputold.customer && inputold.customer[0] != ''  ? '': 'display:none'}">
				<colgroup>
					<col style="width: 60px; text-align: right;">
					<col style="width:60px"></col>
					<col style="width:120px"></col>
					<col style="width:auto"></col>
					<col style="width:140px"></col>
					<col style="width:140px"></col>
					<col style="width:40px"></col>
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
					<c:forEach items="${inputold.quantity}" var="qty" varStatus="pointer">
						<tr id="first">
							<td align="right" class="borderright editable"><p class="number">
								<input value="${qty}"  type="text" name="quantity[]" class="short qty focus-tab number-filter text-right" target-type="select" target-tab=".unit" target-tr="true" >
								<input type="hidden" name="weight[]" class="weight"/>
								</p></td>
							<td class="item-container"><span> <select
									class="select unit focus-tab "  target-action="create_new_row()"  name="unit[]" >
										<option value="1">Biji</option>
										<option value="48">[48]</option>
										<option value="46">[46]</option>
										<option value="44">[44]</option>
								</select>
							</span></td>
							<td><div class="sku">${inputold.sku[pointer.index]}</div>
								<input type="hidden" name="sku[]" value="${inputold.sku[pointer.index]}" />
							</td>
							<td class="product-container editable" >
								<input id="product-${pointer.index}" placeholder="Type product.." name="itemId[]" type="text" 
								class="product_name autocomplete" value="[{id:${inputold.itemId[pointer.index]}, name:'<c:out value="${inputold.productname[pointer.index]}"/>'}]" />
								<input type="hidden" name="productname[]" id="productname" value="<c:out value="${inputold.productname[pointer.index]}"/>" />
							</td>
							<td align="right" class="number"><div class="price prices">${inputold.price[pointer.index]}</div>
								<input type="hidden" name="price[]" value="${inputold.price[pointer.index]}" /></td></td>
							<td align="right" class="number"><div class="subtotal price">${inputold.subtotal[pointer.index]}</div>
								<input type="hidden" name="subtotal[]" value="${inputold.subtotal[pointer.index]}" /></td>
							<td align="center"><a class="delete-row" href="#hapus"><i
									class="icon-close" ></i></a></td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="7"><a href="#" class="add_item">Tambah Barang</a></td>
					</tr>
				</tfoot>
			</table>

			<div class="empty-state" style="${!empty inputold.customer && inputold.customer[0] != ''  ? 'display:none': ''}">
				<i class="empty-icon entry"></i>
				<h4>TIDAK ADA ENTRI. SILAKAN ISI FORM DATA DI SIDEBAR KANAN TERLEBIH DULU.</h4>
			</div>
		</div>
		<div class="rightheader">
			<div class="btn-area">
				<button type="submit" class="btn positive" id="save-button">
					<i class="icon-file icon-white"></i>${save_label}
				</button>
				<a href="${invoice_view_url}" class="btn netral close"><i class="icon-file icon-grey"></i>KEMBALI</a>
			</div>
			<div class="totalbar "><span class="total-weight">${inputold.total_weight[0] != null ? inputold.total_weight[0] : 0}</span><small>kg</small> <span class="total-item">${inputold.total_item[0] != null ? inputold.total_item[0] : 0}</span><small>items</small>
				<br>
				<div class="fl">
					TOTAL<br> <span>IDR</span>
				</div>
				<div class="fr">
					<span class="total-price price">${inputold.total_price[0] != null ? inputold.total_price[0] : 0}</span>
				</div>
				<div class="clear"></div>
			</div>
			<input type="hidden" name="total_weight" value="${inputold.total_weight[0]}" class="validation" data-validation="number" >
			<input type="hidden" name="total_item" value="${inputold.total_item[0]}" class="validation" data-validation="number" />
			<input type="hidden" name="total_price" value="${inputold.total_price[0]}" class="validation" data-validation="number" />
			<input type="hidden" name="customer_name" id="customer_name" />
			<div class="form">
				<div class="field">
					<label>Nota Pesan:</label> <input class="fr normal"
						type="text text-center" name="salesorderLabel" value="Kosong"
						disabled><input class="fr normal" type="hidden"
						name="salesorder" value="0">
				</div>
				<div class="clear"></div>
				<div class="inputholder">
					<label>Pelanggan:</label> <input placeholder="Select customer..."
						name="customer" id="customer" type="text"
						class="medium text-right border-bottom focus-tab validation" target-tab="#customerPO" data-validation="required" />
						<input name="customerName" type="hidden" id="customerName" value="${inputold.customerName[0]}"/>
					<textarea class="long" name="address" id="address" readonly>${inputold.address[0]}</textarea>
				</div>
				<div class="field">
					<label>PO Pelanggan:</label>
					<input class="fr semi-medium focus-tab text-right" type="text" name="customerpo" id="customerPO" target-tab="#discount" value="${inputold.customerpo[0]}">
				</div>
				<div class="field">
					<label>Discount:</label>
					<input class="fr semi-medium text-right focus-tab" type="text" name="discount" id="discount" target-tab="#useppn" value="${inputold.discount[0] == null ? '0' : inputold.discount[0]}">
				</div>

				<div class="field">
					<div class="checkbox">
						<div class="checkbox-field">
							<input id="useppn" name="useppn" target-tab="#ppn" class="focus-tab" type="checkbox" value="false" >
							<label>PPN</label>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<div id="ppn-container" style="display:none">
					<div class="field">
						<label>PPN:</label>
						<input class="fr semi-medium focus-tab text-right" type="text" name="ppn" id="ppn" target-tab="#taxNumber" value="${inputold.ppn[0] == null ? '10%' : inputold.ppn[0]}" disabled>
					</div>
	 				<div class="field">
						<label>Faktur Pajak:</label>
						<input class="fr semi-medium focus-tab text-right validation" data-validation="required_after" data-validation-after="#ppn" type="text" name="taxNumber" id="taxNumber" target-tab="#shopcode" value="${inputold.taxNumber[0]}" disabled>
					</div>
				</div>
				
				<div class="field">
					<label>Kode Toko:</label>
					<input class="fr semi-medium focus-tab text-right" type="text" name="shopcode" id="shopcode" target-tab="#senddate" value="${inputold.shopcode[0]}">
				</div>
				<div class="field">
					<label>Tgl Kirim:</label>
					<div class="field-content fr">
						<div class="input-date input-append">
							<div class="input-icon">
								<i class="icon-calendar"></i>
							</div>
							<input type="text" class="datepicker focus-tab" target-type="select" target-tab="#salesperson"  style="width: 67px"
								name="senddate" id="senddate"/>
						</div>
					</div>
				</div>

				<div class="field">
					<label>Sales Person:</label>
					<div class="field-content fr">
						<select id="salesperson" class="select focus-tab" target-type="select" target-tab="#priceList"  name="salesperson">
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
						<select id="priceList" class="select focus-tab" target-action="showTable(true)"  name="priceList">
							<c:forEach items="${priceList}" var="priceList" varStatus="pointer">
								<option value="${priceList}" ${priceList.equals('Retail') ? 'Selected' : ''} >${priceList}</option>
							</c:forEach>
						</select>
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
				PERHATIAN <a href="javascript:void(0);" class="fr close-popup"><i
					class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Anda harus memilih customer terlebih dahulu. Input customer
					berada pada sidebar sebelah kanan!</p>
				<br /> <a href="javascript:void(0)" class="btn positive"
					id="customer-goto"  >PILIH CUSTOMER</a>
			</div>
		</div>
	</div>
</div>