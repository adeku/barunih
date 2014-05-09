<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:url var="baseURL" value="/sales/sales-orders"/><c:url var="packinglist_url" value="/sales/packing-list/"/><c:url var="URL" value="/"/><c:if test="${URL=='//'}"><c:set var="URL" value="/"/><c:set var="baseURL" value="/sales/sales-orders"/><c:set var="packinglist_url" value="/sales/packing-list/"/></c:if><c:forEach items="${data}" var="salesOrder" varStatus="status">
	<tr>		
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
				<c:when test="${salesOrder.status == 'Selesai'}">
					<div class="status done">
						${salesOrder.status}
					</div>
				</c:when>
				<c:when test="${salesOrder.status == 'Batal'}">
					<div class="status cancel">
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
	</tr>
</c:forEach>