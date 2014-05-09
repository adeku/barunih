<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- PRINT START -->
<div class="print-layout">
	<div class="print-header">
		<div class="fl">
			<div class="fl nonota">${data.refNumber}</div>
			<div class="fl">
				<h1>NOTA JUAL</h1>
				<p>${data.trxDatePrintFormat}<br /> No. PO: ${data.customerPO}</p>
			</div>
			<div class="clear"></div>
		</div>
		<div class="fr">
			<span>CUSTOMER:</span>
			<p>${empty data.customerName ? "Belum Ada Nama" : data.customerName}</p>
			<div class="address">${data.customerCity}</div>
		</div>
	</div>
	<div class="clear"></div>
	<table class="invoice_table print-box table">
		<colgroup>
			<col style="width: 30px; text-align: right;"></col>
			<col style="width: 30px"></col>
			<col style="width: 60px"></col>
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
			<c:forEach items="${data.invoiceDetails}" var="detail" varStatus="status">
			<tr id="first" height="20">
				<td height="20" align="right" class="borderright"><p class="number price">${detail.qty}</p></td>
				<td height="20"><span>${detail.unit}</span></td>
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
		<c:forEach begin="1" end="${data.lastBox}" varStatus="loop">
			<c:if test="${loop.index != 1}">
				<br>
			</c:if>
			<h3>BOX ${loop.index}</h3>
		    <table class="invoice_table notsw ${loop.index}">
				<colgroup>
					<col class="one_col" style="width: 60px; text-align: right;"></col>
					<col class="sec_col" style="width: 60px"></col>
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
						<fmt:parseNumber var="unit" type="number" value="${detail.unit}" />
						<c:if test="${unit == loop.index}">
							<tr id="first" height="20">
								<td height="20" align="right" class="borderright"><p
										class="number price">${detail.qty}</p></td>
								<td height="20">Biji</td>
								<td height="20">${detail.sku}</td>
								<td height="20">${detail.item.vinCatalogs.title}</td>
								<td height="20" align="right" class="number price">${detail.price}</td>
								<td height="20" align="right" class="number price">${detail.price * detail.qty}</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</c:forEach>
	</div>

	<div class="rightheader">
		<div class="btn-area">
			<a href="" class="btn netral"><i class="icon-grey icon-pencil"></i>RALAT</a>
			<a href="javascript:void(0);" class="btn netral print"><i class="icon-grey icon-print"></i>CETAK</a>
			<a href="${invoice_view_url}" class="btn negative close"><i class="icon-close icon-white"></i>BATAL</a>
		</div>
		<div class="totalbar "><span class="total-weight">${detail.total_weight}</span><small>kg</small> <span class="total-item">${detail.total_item}</span><small>items</small>
			<br>
			<div class="fl">
				TOTAL<br> <span>IDR</span>
			</div>
			<div class="fr">
				<span class="total-price">${detail.total_price}</span>
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
				<label>PO Pelanggan:</label> <input class="fr normal text-right" type="text"
					name="customerpo" value="${data.customerPO }" readonly/>
			</div>

			<div class="field">
				<label>Tgl Kirim:</label> <input class="fr normal text-right" type="text"
					name="customerpo" value="${data.deliveryDate }" readonly/>
			</div>

			<div class="field">
				<label>Sales Person:</label> <input class="fr normal text-right" type="text"
					name="customerpo" value="${data.salesPerson}" readonly/>
			</div>

			<div class="field">
				<label>Daftar Harga:</label>
				<input id="priceList"  type="text" value="${data.priceList}" class="fr semi-medium text-right" readonly> 
			</div>

		</div>
	</div>
</div>