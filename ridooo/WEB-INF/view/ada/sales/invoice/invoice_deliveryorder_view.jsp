<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:url var="invoice_url" value="/sales/deliveryorder/create-form/from-sales-order/"/>
<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/" />
<c:set var="invoice_url" value="/sales/deliveryorder/create-form/from-sales-order/"/>
</c:if>

<div class="popup alert" id="popup" style="width: 696PX !important">
	<div class="head_title">
		PILIH NOTA JUAL<a href="" class="fr"><i class="icon-blue icon-close"></i></a>
		<div class="clear"></div>
	</div>
	<div class="popup-holder">
		<div class="main-content">
		<!-- table -->
		<table>
			<colgroup>
				<col style="width:auto"></col>
				<col style="width:auto"></col>
				<col style="width:auto;min-width:500px;"></col>
				<col style="width:70px"></col>
				<col style="width:60px"></col>
				<col style="width:auto;min-width:120px"></col>
				<col style="width:90px"></col>
				<col style="width:70px"></col>
				<col style="width:50px"></col>
			</colgroup>
			<thead>
				<tr>
					<td>INV#</td>
					<td>PELANGGAN</td>
					<td>TGL KIRIM</td>
					<td align="right">JUMLAH</td>
					<td>TGL</td>
					<td>&nbsp;</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${data}" var="invoice">
					<tr id="first">
						<td class="number">
							<a href="" class="upfont">${invoice.refNumber}</a>
						</td>
						<td>
							<a href="">${invoice.customerName}</a><br>
							<span>${invoice.customerCity}</span>
						</td>
						<td>${invoice.deliveryDate}</td>
						<td align="right" class="number borderright">
							${invoice.totalAmount}
						</td>
						<td>
							${invoice.trxDate}<br>
							<span>${invoice.trxTime}</span>
						</td>
						<td>
							<div class="btn-group icon fr">
								<a href="${invoice_url}${invoice.id}" class="btn positive" id="inv_so">PILIH</a>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br />
		<br />
		<br />
	</div>
	</div>
</div>