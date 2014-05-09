<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/sales/sales-orders"/>
<c:url var="URL" value="/"/>

<c:if test="${URL=='//'}">
	<c:set var="URL" value="/"/>
	<c:set var="baseURL" value="/sales/sales-orders"/>
</c:if>

<c:set var="editUrl" value="${baseURL}/${id}/edit"/>

<c:if test="${data.statusCode == -1}">
	<c:set var="editUrl" value="javascript:void(0);"/>
</c:if>
<!-- PRINT START -->
<div class="print-layout">
	<div class="print-header">
		<div class="fl text-right">
			<h2>NOTA PESAN</h2>
			<h1>${data.refNumber}</h1>
			<div class="date">${data.printDate}</div>
		</div>
		<div class="fl">
			<span>PELANGGAN</span>
			<h3>${data.customerName}</h3>
			<p>${data.customerCity}</p>
		</div>
		<div class="fl">
			<div class="fieldprint">
				<label>SALES PERSON</label>
				<p>${data.salesPersonName.toUpperCase()}</p>
			</div>
			<div class="fieldprint">
				<label>PO PELANGGAN</label>
				<p>${data.customerPO.toUpperCase()}</p>
			</div>
		</div>
		<div class="fr">
			<div class="fieldprint">
				<label>JUMLAH</label>
				<p>${data.totalItem}</p>
			</div>
			<div class="fieldprint">
				<label>BERAT TOTAL (KG)</label>
				<p>${data.totalWeight}</p>
			</div>
			<div class="fieldprint">
				<label>GRAND TOTAL (IDR)</label>
				<p class="price">${data.total}</p>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	
	<table class="invoice_table print-box table">
		<colgroup>
			<col style="width: 30px; text-align: right;"></col>
			<col style="width: 92px"></col>
			<col style="width: 176px"></col>
			<col style="width: auto"></col>
			<col style="width: 65px"></col>
			<col style="width: 75px"></col>
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
			<c:forEach items="${data.transactionDetails}" var="detail" varStatus="status">
			<tr id="first" height="20">
				<td height="20" align="right" class="borderright"><p class="number price">${detail.quantity}</p></td>
				<td height="20"><p class="number price">${detail.unit.trim() == "1" ? "Biji" : "Box [".concat(detail.unit).trim().concat("]")}</p></td>
				<td height="20" class="">${detail.sku}</td>
				<td height="20" class="">${detail.itemName}</td>
				<td height="20" align="right" ><div class="number price">${detail.price}</div></td>
				<td height="20" align="right" ><div class="number price">${detail.subtotal}</div></td>
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
			<h1>nota PESAN (${data.refNumber})</h1>
		</div>
		<!-- header info -->
	
		<!-- table -->
		<table class="invoice_table notsw">
			<colgroup>
				<col class="one_col" style="width: 60px; text-align: right;"></col>
				<col class="sec_col" style="width: 92px"></col>
				<col class="third_col" style="width: 176px"></col>
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
				<c:forEach items="${data.transactionDetails}" var="detail" varStatus="status">
					<tr id="first" height="20">
						<td height="20" align="right" class="borderright"><p
								class="number price">${detail.quantity}</p></td>
						<td height="20">${detail.unit.trim() == "1" ? "Biji" : "Box <span class='number'>[".concat(detail.unit).trim().concat("]</span>")}</td>
						<td height="20">${detail.sku}</td>
						<td height="20">${detail.itemName}</td>
						<td height="20" align="right" class="number price">${detail.price}</td>
						<td height="20" align="right" class="number price">${detail.subtotal}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="rightheader">
		<div class="btn-area">
		<c:if test="${canUpdate}">
			<a href=" ${editUrl}" class="btn ${data.statusCode == 1 ? 'netral' : 'disabled' }"><i class="icon-grey icon-pencil"></i>RALAT</a>
		</c:if>
			<a href="javascript:void(0);" class="btn netral print"><i class="icon-grey icon-print"></i>CETAK</a>
			<a href="javascript:void(0);" class="btn close ${data.statusCode == -1 ? 'disabled' : 'negative cancel' }" data-id="${id}"><i class="icon-close ${data.statusCode == -1 ? 'icon-grey' : 'icon-white' }"></i>BATAL</a>
		</div>
		<div class="totalbar "><span class="total-weight">${data.totalWeight}</span><small>kg</small> <span class="total-item">${data.totalItem}</span><small>items</small>
			<br>
			<div class="fl">
				TOTAL<br> <span>IDR</span>
			</div>
			<div class="fr">
				<span class="total-price price">${data.total}</span>
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
					value="${data.refNumber }" disabled><input
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
				<label>PO Pelanggan:</label> <input class="fr input-mini text-right" type="text"
					name="customerpo" value="${data.customerPO }" readonly/>
			</div>
			
			<div class="field">
				<label>Tgl Kirim:</label> <input class="fr normal text-right" type="text"
					name="customerpo" value="${data.deliveryDate }" readonly/>
			</div>
			
			<div class="field">
				<label>Sales Person:</label> <input class="fr normal text-right" type="text"
					name="customerpo" value="${data.salesPersonName}" readonly/>
			</div>
			
			<div class="field">
				<label>Daftar Harga:</label>
				<input id="priceList"  type="text" value="${data.priceList}" class="fr normal text-right" readonly> 
			</div>
		</div>
	</div>
	<div style="display:none;">
		<div class="popup alert" id="cancel-alert" style="width: 400px !important">
			<div class="head_title">Batal<a href="javascript:void(0)" class="fr close-popup"><i
					class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Apakah Anda yakin ingin membatalkan transaksi ini?</p>
				<br/> <a href="#" class="btn positive cancel-button">Ya</a> <a
					href="#" class="btn netral btNotHapus close-popup">Tidak</a>
			</div>
		</div>
	</div>
</div>