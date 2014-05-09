<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="base_url" value="/" />
<c:if test="${base_url=='//'}">
<c:set var="base_url" value="/" />
</c:if>
<!-- PRINT START -->
<div class="print-layout" >

	<div class="print-header">
		<div class="fl text-right">
			<h2>SURAT JALAN</h2>
			<h1>${data.refNumber}</h1>
			<div class="date">${data.trxDatePrintFormat}</div>
		</div>

		<div class="fl">
			<span>PELANGGAN</span>
			<h3>${data.customerName}</h3>
			<p>${data.customerCity}</p>
			<div class="fieldprint">
				<label>INDUK DOKUMEN</label>
				<p
				>${data.soRefNumber}</p>
			</div>
			<%-- <p>${data.customerAddress}</p> --%>
		</div>

		<div class="fl">
			<span>EKSPEDISI</span>
			<h3>${data.courierName}</h3>
			<p>${data.courierCity}</p>
			<%-- <p>${data.courierAddress}</p> --%>
		</div>

		<div class="fr">
			<div class="fieldprint">
				<label>KODE TOKO</label>
				<p  >${data.shopcode.equals("") ? "-" : data.shopcode}</p>
			</div>
			<div class="fieldprint">
				<label>ASURANSI</label>
				<p>${data.assurance}</p>
			</div>
			<div class="fieldprint">
				<label>JUMLAH</label>
				<p class="price">${data.totalItem}</p>
			</div>
			<div class="fieldprint">
				<label>BERAT TOTAL (KG)</label>
				<p class="price">${data.totalWeight}</p>
			</div>
		</div>
		<div class="clear"></div>
	</div>

	<table class="invoice_table print-box table">
		<colgroup>
			<col style="width: 30px; text-align: right;"></col>
			<col style="width: 82px"></col>
			<col style="width: 60px"></col>
			<col style="width: auto"></col>
			<col style="width: 100px"></col>
			<col style="width: 60px"></col>
			<col style="width: 30px; text-align: center;"></col>
		</colgroup>
		<thead>
			<tr>
				<td align="right">QTY</td>
				<td>UNIT</td>
				<td>SKU</td>
				<td>ITEM</td>
				<td>NO DUS</td>
				<td>BERAT</td>
				<td align="right"></td>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${data.deliveryorderDetails}" var="detail" varStatus="status">
			<tr id="first" height="20">
				<td height="20" align="right" class="borderright price"><p class="number">${detail.qty}</p></td>
				<td height="20"><p class="number">${detail.unit.trim() == "1"  ? "BIJI" : "BOX [".concat(detail.unit.trim()).concat("]")}</p></td>
				<td height="20">${detail.sku.toUpperCase()}</td>
				<td height="20">${empty detail.item.vinCatalogs.title ? "Alat-alat keperluan kantor" : detail.item.vinCatalogs.title}</td>
				<td height="20">${detail.boxNumber}</td>
				<td height="20">${detail.item.weight}</td>
				<td height="20"><p style="font-size:20px;margin-bottom:0;margin-top:-2px;top:0;">&#9744;</p></td>
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
			<h1>SURAT JALAN (${data.refNumber})</h1>
		</div>
		<!-- header info -->
	
		<!-- table -->
		<table class="invoice_table notsw">
			<colgroup>
				<col class="one_col" style="width: 60px; text-align: right;"></col>
				<col class="sec_col" style="width: 82px"></col>
				<col class="third_col" style="width: 90px"></col>
				<col class="four_col" style="width: auto"></col>
				<col style="width: 125px"></col>
				<col style="width: 80px"></col>
			</colgroup>
			<thead>
				<tr>
					<td align="right">QTY ${data.status.trim()}</td>
					<td>UNIT</td>
					<td>SKU</td>
					<td>ITEM</td>
					<td>NO DUS</td>
					<td>BERAT</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${data.deliveryorderDetails}" var="detail" varStatus="status">
					<tr id="first" height="20">
						<td height="20" align="right" class="borderright"><p
								class="number price">${detail.qty}</p></td>
						<td height="20">${detail.unit.trim() == "1"  ? "Biji" : "Box <span class='number'>[".concat(detail.unit.trim()).concat("]</span>")}</td>
						<td height="20">${detail.sku.toUpperCase()}</td>
						<td height="20">${empty detail.item.vinCatalogs.title ? "Alat-alat keperluan kantor" : detail.item.vinCatalogs.title}</td>
						<td height="20">${detail.boxNumber}</td>
						<td height="20">${detail.item.weight}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div class="rightheader">
		<div class="rightheader">
			<div class="btn-area">
				<c:choose>
					<c:when test="${data.status.trim().toUpperCase().equals('BATAL')}">
						<a href="javascript:void(0);" class="btn disabled" disabled><i class="icon-grey icon-pencil"></i>RALAT</a>
						<a href="javascript:void(0);" class="btn netral print"><i class="icon-grey icon-print"></i>CETAK</a>
						<a href="javascript:void(0);" class="btn close disabled" disabled><i class="icon-close icon-grey"></i>BATAL</a>
					</c:when>
					<c:otherwise>
						<a href="${base_url}sales/deliveryorder/edit-form/${data.id}" class="btn netral " }><i class="icon-grey icon-pencil"></i>RALAT</a>
						<a href="javascript:void(0);" class="btn netral print"><i class="icon-grey icon-print"></i>CETAK</a>
						<a href="${base_url}sales/deliveryorder/edit/status/${data.id}/-1" class="btn negative close"><i class="icon-close icon-white"></i>BATAL</a>
					</c:otherwise>
				</c:choose>
				
			</div>
			<div class="totalbar ">
				<span class="total-weight">${data.totalWeight}</span><small>kg</small> <span class="total-item">${data.totalItem}</span><small>items</small>
				<br>
				<div class="clear"></div>
			</div>
			<input type="hidden" name="total-weight" value="${data.totalWeight}">
			<input type="hidden" name="total-item" value="${data.totalItem}">
			<div class="form">
				<div class="field">
					<label>Nota Jual:</label>
					<input class="fr normal  text-right"	type="text" name="salesorderLabel"	value="${data.refNumber }" disabled>
					<input	class="fr normal" type="hidden" name="salesorder" value="0">
				</div>
				<div class="field">
					<label>Kode Toko:</label>
					<input class="fr normal  text-right" type="text" name="salesorderLabel" value="${data.shopcode}" disabled>
					<input class="fr normal" type="hidden" name="salesorder" value="0">
				</div>
				<div class="clear"></div>
				<div class="inputholder">
					<label>Pelanggan:</label>
					<input  type="text" class="medium text-right border-bottom " value="${data.customerName}" radonly/>
					<textarea class="long" name="cust-address" id="cust-address" readonly>${data.customerAddress}</textarea>
				</div>
				<div class="clear"></div>
				<div class="inputholder">
					<label>Ekspedisi:</label> 
					<input  type="text" class="medium text-right border-bottom " value="${data.courierName}" readonly/>
					<textarea class="long"name="eks-address" id="eks-address" readonly>${data.courierAddress}</textarea>
				</div>
				<div class="field">
					<label>Biaya Asuransi:</label>
					<input class="fr semi-medium focus-tab text-right number prices number-filter" type="text" name="assurance" id="assurance" target-tab="#senddate" value="${data.assurance}" readonly>
				</div>
				<div class="field">
					<label>Tgl Kirim:</label>
					<div class="field-content fr">
						<div class="input-date input-append">
							<div class="input-icon">
								<i class="icon-calendar icon-blue"></i>
							</div>
							<input type="text"  style="width: 67px" name="senddate" value="${data.deliveryDate}" class="validation" readonly/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>