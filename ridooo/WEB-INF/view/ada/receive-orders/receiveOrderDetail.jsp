<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/purchase/receive-orders/"/>
<c:url var="URL" value="/"/>

<c:if test="${URL=='//'}">
	<c:set var="URL" value="/"/>
	<c:set var="baseURL" value="/purchase/receive-orders/"/>
</c:if>

<!-- PRINT START -->
<div class="print-layout">
	<div class="print-header">
		<div class="fl text-right">
			<h2>SURAT TERIMA</h2>
			<h1>${form.refNumber}</h1>
<%-- 			<div class="date">${form.printDate}</div> --%>
		</div>
		<div class="fl">
			<span>SUPPLIER</span>
			<h3>${form.supplierName}</h3>
			<p>${form.supplierCity}</p>
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
				<label>INDUK DOKUMEN</label>
				<p>${form.parentRefNumber}</p>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<table class="invoice_table print-box table">
		<colgroup>
			<col style="width: 30px; text-align: right;"></col>
			<col style="width: 60px"></col>
			<col style="width: 60px"></col>
			<col style="width: auto"></col>
			<col style="width: 85px"></col>
			<col style="width: 75px"></col>
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
			<c:forEach items="${form.transactionDetails}" var="detail" varStatus="status">
			<tr id="first" height="20">
				<td height="20" align="right" class="borderright price"><p class="number">${detail.quantity}</p></td>
				<td height="20"><p class='number'>${detail.unit.trim() == "1" ? "Biji" : "Box [".concat(detail.unit).trim().concat("]")}</p></td>
				<td height="20">${detail.sku}</td>
				<td height="20">${detail.itemName}</td>
				<td height="20" align="right" class="number">${detail.warehouseName}</td>
				<td height="20" align="right" class="number">${detail.shelfName}</td>
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
			
			<h1>surat terima (${form.refNumber})</h1>
				
		</div>
		<!-- header info -->
	
		<!-- table -->
		<table class="invoice_table notsw">
			<colgroup>
				<col class="one_col" style="width: 60px; text-align: right;"></col>
				<col class="sec_col" style="width: 70px"></col>
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
					<td>GUDANG</td>
					<td>RAK</td>
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
						<td height="20" class="number">${detail.warehouseName}</td>
						<td height="20" class="number">${detail.shelfName}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="rightheader">
		<div class="btn-area">
			<a ${  form.status == -1 || form.status == 1 ? "" : "href='".concat(baseURL).concat(form.id).concat("/edit'") } class="btn ${ form.status == -1 || form.status == 1 ? 'disabled' : 'netral'}"><i class="icon-file icon-grey"></i>RALAT</a>
			<a href="javascript:void(0);" class="btn netral print"><i class="icon-grey icon-print"></i>CETAK</a>
			<a ${  form.status == -1 ? "" : "href='".concat(baseURL).concat(form.id).concat("/edit-status/cancel'") }  class="btn close ${form.status == -1 ? 'disabled' : 'negative cancel' }" data-id="${form.id}"><i class="icon-close icon-white"></i>BATAL</a>
		</div>
		<div class="totalbar ">
			<span class="total-weight">
					${form.totalWeight}
			</span>
			<small>kg</small>
			<form:hidden path="totalWeight" />
			<span class="total-item">
					${form.totalItem}
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
						<input  class="medium text-right border-bottom" value="${form.supplierName}" readonly/>
						<textarea class="long address">${form.supplierAddress}</textarea>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="form">
					<div class="inputholder">
						<label>Vendor:</label>
						<input class="medium text-right border-bottom" value="${form.supplierAddress}" readonly/>
						<textarea class="long address" readonly>${form.supplierAddress}</textarea>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="clear"></div>
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