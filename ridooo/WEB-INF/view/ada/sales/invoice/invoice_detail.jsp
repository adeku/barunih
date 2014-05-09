<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:url var="baseURL" value="/" />
<c:url var="editUrl" value="/sales/invoice/edit-form/${data.id}"/>

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/" />
<c:set var="editUrl" value="/sales/invoice/edit-form/${data.id}" />
</c:if>
<!-- PRINT START -->
<div class="print-layout">
	<div class="print-header">
		<div class="fl text-right">
			<h2>NOTA JUAL</h2>
			<h1>${data.refNumber}</h1>
			<div class="date">${data.trxDatePrintFormat}</div>
		</div>
		<div class="fl">
			<span>PELANGGAN</span>
			<h3>${data.customerName}</h3>
			<p>${data.customerCity}</p>
			<div class="fieldprint">
				<label>KODE TOKO</label>
				<p>${data.shopcode.equals("") ? "-" : data.shopcode}</p>
			</div>
		</div>
		<div class="fl">
			<div class="fieldprint">
				<label>JUMLAH</label>
				<p>${data.totalItem}</p>
			</div>
			<div class="fieldprint">
				<label>BERAT TOTAL (KG)</label>
				<p>${data.totalWeight}</p>
			</div>
			<div class="fieldprint">
				<label>SALES PERSON</label>
				<p>${data.salesPerson}</p>
			</div>
			<div class="fieldprint">
				<label>PO PELANGGAN</label>
				<p>${data.customerPO.toUpperCase()}</p>
			</div>
		</div>
		<div class="fr">
			<div class="fieldprint">
				<label>TOTAL (IDR)</label>
				<p class="price">${data.subTotal}</p>
			</div>
			<div class="fieldprint">
				<label>DISCOUNT</label>
				<p>${data.discount == null ? "-" : data.discount}</p>
			</div>
			<div class="fieldprint">
				<label>PPN (10%)</label>
				<p>${data.ppn == null ? "-" : data.total_ppn}</p>
			</div>
			<div class="fieldprint">
				<label>GRAND TOTAL (IDR)</label>
				<p class="price">${data.totalAmount}</p>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	
	<div class="clear"></div>
	<table class="invoice_table print-box table">
		<colgroup>
			<col style="width: 30px; text-align: right;"></col>
			<col style="width: 82px"></col>
			<col style="width: 60px"></col>
			<col style="width: auto"></col>
			<col style="width: 65px"></col>
			<col style="width: 75px"></col>
		</colgroup>
		<thead>
			<tr>
				<td align="right">QTYs</td>
				<td>UNIT</td>
				<td>SKU</td>
				<td>ITEM</td>
				<td align="right">HARGA</td>
				<td align="right">SUBTOTAL</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${data.invoiceDetails}" var="detail" varStatus="status">
			<tr id="first" height="20">
				<td height="20" align="right" class="borderright"><p class="number price">${detail.qty}</p></td>
				<td height="20"><p class="number price">${detail.unit.trim() == "1" ? "Biji" : "Box [".concat(detail.unit).trim().concat("]")}</p></td>
				<td height="20">${detail.sku}</td>
				<td height="20">${detail.item.vinCatalogs.title}</td>
				<td height="20" align="right" class="number price">${detail.price}<</td>
				<td height="20" align="right" class="number price">${detail.subtotal}<</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<!-- PRINT END -->

<div class="content notsw">
	<div class="main-content withright">
		<!-- heading tittle -->
		<div class="header-info">
			<div class="header-attr">
			</div>
			<h1>nota jual (${data.refNumber})</h1>
		</div>
		<!-- header info -->
	
		<!-- table -->
		<table class="invoice_table notsw">
			<colgroup>
				<col class="one_col" style="width: 60px; text-align: right;"></col>
				<col class="sec_col" style="width: 82px"></col>
				<col class="third_col" style="width: 90px"></col>
				<col class="four_col" style="width: auto"></col>
				<col class="five_col" style="width: 140px"></col>
				<col class="six_col" style="width: 170px"></col>
			</colgroup>
			<thead>
				<tr>
					<td align="right">QTY</td>
					<td>UNIT</td>
					<td>SKU</td>
					<td>ITEM</td>
					<td align="right">HARGA</td>
					<td align="right">SUBTOTAL</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${data.invoiceDetails}" var="detail" varStatus="status">
					<tr id="first" height="20">
						<td height="20" align="right" class="borderright"><p
								class="number price">${detail.qty}</p></td>
						<td height="20">${detail.unit.trim() == "1" ? "Biji" : "Box <span class='number'>[".concat(detail.unit).trim().concat("]</span>")}</td>
						<td height="20">${detail.sku}</td>
						<td height="20">${detail.item.vinCatalogs.title}</td>
						<td height="20" align="right" class="number price">${detail.price}</td>
						<td height="20" align="right" class="number price">${detail.subtotal}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="rightheader">
		<div class="btn-area">
			<a href="${editUrl}" class="btn netral"><i class="icon-grey icon-pencil"></i>RALAT</a>
			<a href="javascript:void(0);" class="btn netral print"><i class="icon-grey icon-print"></i>CETAK</a>
			<a href="${baseURL}sales/invoice/edit/status/${data.id}/-1" class="btn negative close"><i class="icon-close icon-white"></i>BATAL</a>
		</div>
		<div class="totalbar "><span class="total-weightss">${data.totalWeight}</span><small>kg</small> <span class="total-itemss">${data.totalItem}</span><small>items</small>
			<br>
			<div class="fl">
				TOTAL<br> <span>IDR</span>
			</div>
			<div class="fr">
				<span class="total-prices price">${data.totalAmount}</span>
			</div>
			<div class="clear"></div>
		</div>
		<input type="hidden" name="total-weight" value="0"> <input
			type="hidden" name="total-item" value="0"> <input
			type="hidden" name="total-price" value="0">
		<div class="form">
			<div class="field">
				<label>Nota Pesan:</label> <input class="fr normal text-right"
					type="text text-center" name="salesorderLabel"
					value="${data.soRefNumber}" disabled><input
					class="fr normal text-right" type="hidden" name="salesorder" value="0" readonly/>
			</div>

			<div class="clear"></div>

			<div class="inputholder">
				<label>Pelanggan:</label> <input placeholder="Select customer..."
					name="customers" id="customers" type="text"
					class=" medium text-right border-bottom" value="${data.customerName}" readonly/>
				<textarea class="long" name="status" readonly>${data.customerAddress}</textarea>
			</div>

			<div class="field">
				<label>PO Pelanggan:</label> <input class="fr semi-medium  text-right" type="text"
					name="customerpo" value="${data.customerPO }" readonly/>
			</div>

			<div class="field">
				<label>Discount:</label>
				<input class="fr semi-medium text-right" type="text" value="${data.discount}" readonly>
			</div>
			
			<div class="field">
				<label>PPN:</label>
				<input class="fr semi-medium  text-right" type="text" value="${data.ppn}" readonly>
			</div>
			
			<div class="field">
				<label>Kode Toko:</label>
				<input class="fr semi-medium  text-right" type="text" name="shopcode" id="shopcode"  value="${data.shopcode}" readonly>
			</div>
			
			<div class="field">
				<label>Tgl Kirim:</label> <input class="fr semi-medium  text-right" type="text"
					name="customerpo" value="${data.deliveryDate }" readonly/>
			</div>

			<div class="field">
				<label>Sales Person:</label> <input class="fr semi-medium  text-right" type="text" value="${data.salesPerson}" readonly/>
			</div>
			
			<div class="field">
				<label>Daftar Harga:</label>
				<input id="priceList"  type="text" value="${data.priceList}" class="fr semi-medium text-right" readonly> 
			</div>

		</div>
	</div>
</div>