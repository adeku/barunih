<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="URL" value="/return-orders" />
<!-- PRINT START -->
<div class="print-layout">
	<div class="print-header">
		<div class="fl text-right">
			<h2>NOTA RETUR</h2>
			<h1>${form.refNumber}</h1>
<%-- 			<div class="date">${form.printDate}</div> --%>
		</div>
		<div class="fl">
			<span>SUPPLIER</span>
			<h3>${form.customerName}</h3>
			<p>${form.customerCity}</p>
		</div>
		<div class="fl">
			<span>GUDANG</span>
			<h3>${form.warehouseName}</h3>
			<p>${form.warehouseAddress}</p>
		</div>

		<div class="fr">
			<div class="fieldprint">
				<label>KODE BONGKAR</label>
				<p>${form.refNumber}</p>
			</div>
			<div class="fieldprint">
				<label>JUMLAH</label>
				<p>${form.totalItem}</p>
			</div>
			<div class="fieldprint">
				<label>BERAT TOTAL (KG)</label>
				<p>${form.totalWeight}</p>
			</div>
			<div class="fieldprint">
				<label>GRAND TOTAL (IDR)</label>
				<p class="price">${form.total}</p>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<table class="invoice_table print-box table">
		<colgroup>
			<col class="one_col" style="width: 60px; text-align: right;"></col>
			<col class="sec_col" style="width: 84px"></col>
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
			<c:forEach items="${form.transactionDetails}" var="detail" varStatus="status">
			<tr id="first" height="20">
				<td height="20" align="right" class="borderright"><p class="number price">${detail.quantity}</p></td>
				<td height="20"><p class="number">${detail.unit.trim() == "1" ? "Biji" : "Box [".concat(detail.unit).trim().concat("]")}</p></td>
				<td height="20">${detail.sku}</td>
				<td height="20">${detail.itemName}</td>
				<td height="20" align="right" class="number price">${detail.price}</td>
				<td height="20" align="right" class="number price">${detail.subtotal}</td>
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
			<h1>nota retur (${form.refNumber})</h1>
		</div>
		<!-- header info -->
	
		<!-- table -->
		<table class="invoice_table notsw">
			<colgroup>
				<col class="one_col" style="width: 60px; text-align: right;"></col>
				<col class="sec_col" style="width: 84px"></col>
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
				<c:forEach items="${form.transactionDetails}" var="detail" varStatus="status">
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
			<a href="${URL}/${id}/edit" class="btn netral"><i class="icon-grey icon-pencil"></i>RALAT</a>
			<a href="javascript:void(0);" class="btn netral print"><i
					class="icon-grey icon-print"></i>CETAK</a>
			<a href="${URL}/${id}/change-status/cancel" class="btn negative cancel"><i class="icon-close icon-white"></i>BATAL</a>
		</div>
		<div class="totalbar "><span class="total-weight">${form.totalWeight}</span><small>kg</small> <span class="total-item">${form.totalItem}</span><small>items</small>
			<br>
			<div class="fl">
				TOTAL<br> <span>IDR</span>
			</div>
			<div class="fr">
				<span class="price">${form.total}</span>
			</div>
			<div class="clear"></div>
		</div>
		<input type="hidden" name="total-weight" value="0"> <input
			type="hidden" name="total-item" value="0"> <input
			type="hidden" name="total-price" value="0">
		<div class="form">
			<div class="field">
				<label>Kode Bongkar:</label> <input class="fr normal text-right"
					type="text text-center" name="salesorderLabel"
					value="${form.refNumber }" disabled><input
					class="fr normal text-right" type="hidden" name="salesorder" value="0" readonly/>
			</div>
			<div class="clear"></div>
			<div class="inputholder">
				<label>Pelanggan:</label> <input placeholder="Select customer..."
					name="customers" id="customers" type="text"
					class=" medium text-right border-bottom" value="${form.customerName}" readonly/>
				<textarea class="long" name="status" readonly>${form.customerAddress}</textarea>
			</div>
			
		</div>
	</div>
	<div style="display: none">
		<div class="popup alert" id="cancel-alert">
			<div class="head_title">Batal<a href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Apakah Anda yakin ingin membatalkan transaksi ini?</p>
				<br/> <a href="#" class="btn positive cancel-button">Ya</a> <a
				href="#" class="btn netral btNotHapus close-popup">Tidak</a>
				<div class="clear"></div>
			</div>
		</div>
		<div class="popup alert" id="cancel-form-alert" style="width: 400px !important">
			<div class="head_title">Batal<a href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Apakah Anda yakin ingin membatalkan transaksi-transaksi ini?</p>
				<br/> <a href="#" class="btn positive cancel-form-button">Ya</a> <a
				href="#" class="btn netral btNotHapus close-popup">Tidak</a>
				<div class="clear"></div>
			</div>
		</div>
	</div>
</div>