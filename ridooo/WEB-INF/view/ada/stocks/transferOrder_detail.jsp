<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
</c:if>
<!-- PRINT START -->
<div class="print-layout">
	<div class="print-header">

		<div class="fl text-right">
			<h2>SURAT PINDAH</h2>
			<h1>${dataBefore.refNumber}</h1>
			<div class="date">${dataBefore.printDate}</div>
		</div>

		<div class="fl">
			<span>GUDANG ASAL</span>
			<h3>${dataBefore.sourceWarehouseName}</h3>
			<p>${dataBefore.sourceWarehouseAddress}</p>
		</div>
		<div class="fl">
			<span>GUDANG TUJUAN</span>
			<h3>${dataBefore.destinationeWarehouseName}</h3>
			<p>${dataBefore.destinationeWarehouseAddress}</p>
		</div>

		<div class="fr">
			<div class="fieldprint">
				<label>JUMLAH</label>
				<p>${dataBefore.totalItem}</p>
			</div>
			<div class="fieldprint">
				<label>BERAT TOTAL (KG)</label>
				<p>${dataBefore.totalWeight}</p>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<table class="invoice_table print-box table">
		<colgroup>
			<col class="one_col" style="width: 40px; text-align: right;"></col>
			<col class="sec_col" style="width: 84px"></col>
			<col class="third_col" style="width: 90px"></col>
			<col class="four_col" style="width: auto"></col>
			<col class="five_col" style="width: 140px"></col>
			<col class="six_col" style="width: 170px"></col>
		</colgroup>
		<thead>
			<tr>
				<td align="center">QTY</td>
				<td>UNIT</td>
				<td>SKU</td>
				<td>ITEM</td>
				<td>RAK ASAL</td>
				<td>RAK TUJUAN</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${form.transactionDetails}" var="detail" varStatus="status">
			<tr id="first" height="20">
				<td height="20" align="right" class="borderright"><p class="number price">${detail.quantity}</p></td>
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
<!-- PRINT END -->
	<div class="content notsw">
	<form id="formIN" name="formIN" method="POST">
		<div class="main-content withright">
			<!-- heading tittle -->
			<h1>surat pindah (${dataBefore.refNumber})</h1>
			<!-- header info -->
			<!-- table -->
			<table class="invoice_table">
				<colgroup>
					<col style="width: 40px; text-align: right;">
					<col style="width: 90px">
					<col style="width: 180px">
					<col style="width: auto">
					<col style="width: 180px">
					<col style="width: 180px">
				</colgroup>
				<thead>
					<tr>
						<td align="center">QTY</td>
						<td>UNIT</td>
						<td>SKU</td>
						<td>ITEM</td>
						<td>RAK ASAL</td>
						<td>RAK TUJUAN</td>
					</tr>
				</thead>
				<tbody></tbody>
				<tfoot>
				</tfoot>
			</table>

		</div>
		<div class="rightheader">
			<div class="btn-area">
			<c:if test="${dataBefore.statusCode==1}">
				<a href="${baseURL}inventory/transfer-order/${id}/edit" class="btn netral"><i class="icon-grey icon-pencil"></i>RALAT</a>
			</c:if>
				<a href="javascript:void(0);" class="btn netral print"><i class="icon-grey icon-print"></i>CETAK</a>
				<a href="${baseURL}inventory/transfer-order" class="btn close ${data.statusCode == -1 ? 'disabled' : 'negative cancel' }" data-id="${id}"><i class="icon-close ${data.statusCode == -1 ? 'icon-grey' : 'icon-white' }"></i>BATAL</a>
			</div>
			<div class="totalbar ">
				<span  >${dataBefore.totalWeight}</span> <small>kg</small> <span
					 >${dataBefore.totalItem}</span> <small>items</small>
			</div>
			<div class="form">
				<div class="inputholder">
					<label>Tgl dibuat:</label> <input class="medium"
						value="${dataBefore.dateCreated}" type="text" readonly="readonly">
				</div>
				<div class="inputholder">
					<label>Tgl proses:</label> <input class="medium"
						value="${dataBefore.dateModified}" type="text" readonly="readonly">
				</div>
				<div class="inputholder">
					<label>Asal:</label> <input class="medium text-right border-bottom"
						type="text" name="sourceWarehouse1" id="sourceWarehouse1">
					<textarea class="long" id="addressAsal" readonly>${dataBefore.sourceWarehouseDescription}</textarea>
				</div>
				<div class="inputholder">
					<label>Tujuan:</label> <input
						class="medium text-right border-bottom" type="text"
						name="destinationeWarehouse1" id="destinationeWarehouse1">
					<textarea class="long" id="addressDestination" readonly>${dataBefore.destinationWarehouseDescription}</textarea>
				</div>
			</div>
		</div>

		<div style="display: none">
			<div class="popup alert" id="popup">
				<div class="head_title">
					PERHATIAN<a class="fr"><i class="icon-blue icon-close"></i></a>
					<div class="clear"></div>
				</div>
				<div class="popup-holder">
					<p>Anda harus memilih customer terlebih dahulu. Input customer
						berada pada sidebar sebelah kanan!!!</p>
					<br /> <a href="javascript:void(0)" class="btn positive">PILIH
						CUSTOMER</a>
				</div>
			</div>
		</div>
		<div style="display: none">
			<div class="popup alert" id="noWArehouseSource">
				<div class="head_title">
					PERHATIAN<a class="fr"><i class="icon-blue icon-close"></i></a>
					<div class="clear"></div>
				</div>
				<div class="popup-holder">
					<p>Anda harus memilih wrehouse terlebih dahulu. Input warehouse
						berada pada sidebar sebelah kanan!!!</p>
					<br /> <a href="" class="btn positive">OK</a>
				</div>
			</div>
		</div>
	
	</form>
</div>