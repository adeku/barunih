<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:forEach items="${data}" var="salesOrder" varStatus="status">
<c:url var="baseURL" value="/sales/sales-orders"/>
<c:url var="packinglist_url" value="/sales/packing-list/"/>
<c:url var="URL" value="/"/>

<c:if test="${URL=='//'}">
	<c:set var="URL" value="/"/>
	<c:set var="baseURL" value="/sales/sales-orders"/>
	<c:set var="packinglist_url" value="/sales/packing-list/"/>
</c:if>

	<tr>
	<c:if test="${canUpdate||canDelete}">
		<td class="borderright" align="center">
			<input name="SOCheckboxes[${offset + status.index}]" type="checkbox" value="${salesOrder.id}" data-status="${salesOrder.status}">
		</td>
		</c:if>
		<td class="number">
			<a href="${baseURL}/${salesOrder.id}/detail" class="upfont">${salesOrder.refNumber}</a>
			<c:if test="${salesOrder.retail}">
				<i class="icon-retail"></i>
			</c:if>
		</td>
		<td>
			<h6><a href="${URL}contacts/customers/${salesOrder.customerId}/detail">${salesOrder.customerName}</a></h6>
			<span>${salesOrder.customerCity}</span>
		</td>
		<td>${salesOrder.deliveryDate}</td>
		<td align="right" class="number price">
			${salesOrder.totalItem}
		</td>
		<td align="right" class="number borderright price">
			${salesOrder.totalAmount}
		</td>
		<td>
			${salesOrder.trxDate}<br>
			<span>${salesOrder.trxTime}</span>
		</td>
		<td>
			<c:choose>
				<c:when test="${salesOrder.status == 'Proses'}">
					<div class="status in-process">
						${salesOrder.status}
					</div>
				</c:when>
				<c:when test="${salesOrder.status == 'Tahan'}">
					<div class="status hold">
						${salesOrder.status}
					</div>
				</c:when>
				<c:when test="${salesOrder.status == 'Aktif'}">
					<div class="status active">
						${salesOrder.status}
					</div>
				</c:when>
				<c:when test="${salesOrder.status == 'Kemas'}">
					<div class="status packed">
						${salesOrder.status}
					</div>
				</c:when>
				<c:when test="${salesOrder.status == 'Selesai'}">
					<div class="status completed">
						${salesOrder.status}
					</div>
				</c:when>
				<c:when test="${salesOrder.status == 'Batal'}">
					<div class="status cancelled">
						${salesOrder.status}
					</div>
				</c:when>
				<c:otherwise>
					<div class="status hold">
						${salesOrder.status}
					</div>
				</c:otherwise>
			</c:choose>
		</td>
		<c:if test="${canUpdate||canDelete}">
		<td>
			<c:if test="${salesOrder.status != 'Batal'}">
				<div class="btn-group icon fr">
					<div class="btn">
						<i class="icon-dropdown"></i>
					</div>
					<ul class="rg" style="display: none;">
						<c:if test="${salesOrder.status == 'Aktif'&&canUpdate}">
							<li><a href="${baseURL}/${salesOrder.id}/edit">Ralat</a></li>
						</c:if>
						
						<c:if test="${((salesOrder.status == 'Aktif' && !salesOrder.retail) || salesOrder.status == 'Kemas')&&canUpdate}">
							<li><a href="${URL}sales/invoice/create-form/process-invoice/${salesOrder.id}">Proses</a></li>
						</c:if>
						<c:if test="${salesOrder.status == 'Aktif' && salesOrder.retail && salesOrder.statusCode == 1 && canUpdate}">
							<li><a class="processTO" href="#" data-id="${salesOrder.id}">Proses Ke Packing</a></li>
						</c:if>
						<c:if test="${salesOrder.status == 'Aktif' && salesOrder.retail && salesOrder.statusCode == 2 && canUpdate}">
							<li><a href="${URL}sales/packing-list/${salesOrder.id}" data-id="${salesOrder.id}" class="processTO">Proses Ke Packing</a></li>
						</c:if>
						<c:choose>
							<c:when test="${salesOrder.status == 'Tunda' && canUpdate}">
								<li><a href="${baseURL}/${salesOrder.id}/change-status/active">Aktifkan</a></li>
							</c:when>
							<c:when test="${salesOrder.status == 'Aktif' && canUpdate}">
								<li><a href="${baseURL}/${salesOrder.id}/change-status/hold">Tahan</a></li>
							</c:when>
						</c:choose>
						<c:if test="${salesOrder.packinglist}">
								
								<li><a href="${packinglist_url}global/${salesOrder.id}" >Packing List Global</a></li>
						</c:if>
						<c:if test="${salesOrder.status != 'Batal' && canDelete}">
							<li><a href="#" class="cancel" data-id="${salesOrder.id}">Batal</a></li>
						</c:if>
					</ul>
				</div>
			</c:if>
		</td>
		</c:if>
	</tr>
</c:forEach>