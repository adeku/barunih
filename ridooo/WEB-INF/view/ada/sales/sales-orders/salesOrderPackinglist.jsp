<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="packinglist_url" value="/sales/packing-list/"/>
<c:url var="URL" value="/"/>

<c:if test="${URL=='//'}">
<c:set var="packinglist_url" value="/sales/packing-list/"/>
</c:if>

<div class="popup alert" id="popup" style="width: 696PX !important">
	<div class="head_title">
		PILIH NOTA PESAN
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
					<td>SO#</td>
					<td>PELANGGAN</td>
					<td>TGL KIRIM</td>
					<td align="right">JUMLAH</td>
					<td>TGL</td>
					<td>&nbsp;</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${data}" var="salesOrder">
					<tr id="first">
						<td class="number">
							<a href="" class="upfont">${salesOrder.refNumber}</a>
						</td>
						<td>
							<h6><a href="">${salesOrder.customerName}</a></h6>
							<span>${salesOrder.customerCity}</span>
						</td>
						<td>${salesOrder.deliveryDate}</td>
						<td align="right" class="number borderright">
							${salesOrder.totalAmount}
						</td>
						<td>
							${salesOrder.trxDate}<br>
							<span>${salesOrder.trxTime}</span>
						</td>
						<td>
							<div class="btn-group icon fr">
								<a href="${packinglist_url}${salesOrder.id}" class="btn positive" id="pl_so">PILIH</a>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br />&nbsp;&nbsp;<a id="button-back" href="" class="btn negative">KEMBALI</a>
		<br />
		<br />
		<br />
	</div>
	</div>
</div>