<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:forEach items="${data}" var="invoice">
<c:url var="base_url" value="/" />

<c:if test="${base_url=='//'}">
<c:set var="base_url" value="/" />
</c:if>
	<tr id="first">
	<c:if test="${canUpdate}">
		<td class="borderright" align="center">
			<input class="check" type="checkbox" name="id_inv[]" value="${invoice.id}" data-status="${invoice.statusCode}">
		</td>
		</c:if>
		<td class="number">
			<a href="${base_url}sales/invoice/detail/${invoice.id}" class="upfont">${invoice.refNumber}</a>
			<c:if test="${invoice.retail == true}">
				<i class="icon-retail"></i>
			</c:if>
		</td>
		<td>
			<h6><a href="${base_url}contacts/customers/${invoice.customerId}/detail">${invoice.customerName}</a></h6>
			<span>${invoice.customerCity}</span>
		</td>
		<td>${invoice.deliveryDate}</td>
		<td align="right" class="number price">
			${invoice.totalItem}
		</td>
		<td align="right" class="number borderright price">
			${invoice.totalAmount}
		</td>
		<td>
			${invoice.trxDate}<br>
			<span>${invoice.trxTime}</span>
		</td>
		<td>
			<c:choose>
				<c:when test="${invoice.status == 'Tahan'}">
					<div class="status hold">
						${invoice.status}
					</div>
				</c:when>
				<c:when test="${invoice.status == 'Aktif'}">
					<div class="status active">
						${invoice.status}
					</div>
				</c:when>
				<c:when test="${invoice.status == 'Lunas'}">
					<div class="status paid">
						${invoice.status}
					</div>
				</c:when>
				<c:when test="${invoice.status == 'Batal'}">
					<div class="status cancelled">
						${invoice.status}
					</div>
				</c:when>
				<c:otherwise>
					<div class="status hold">
						${invoice.status}
					</div>
				</c:otherwise>
			</c:choose>
		</td>
		<c:if test="${canUpdate}">
		<td>
			<c:if test="${invoice.statusCode > 0}">
				<div class="btn-group icon fr">
					<div class="btn">
						<i class="icon-dropdown"></i>
					</div>
					<ul class="rg" style="display: none;">
						<c:if test="${invoice.statusCode == 2}">
							<li><a href="${base_url}sales/invoice/edit-form/${invoice.id}">Ralat</a></li>
						</c:if>
						<c:if test="${invoice.statusCode > 1}">
							<li><a href="${base_url}sales/invoice/edit/status/${invoice.id}/1">Terbayarkan</a></li>
						</c:if>
						<c:if test="${invoice.statusCode == 0}">
							<li><a href="${base_url}sales/invoice/edit/status/${invoice.id}/2">Tahan</a></li>
						</c:if>
						<li><a href="${base_url}sales/invoice/edit/status/${invoice.id}/-1" class="cancel">Batalkan</a></li>
					</ul>
				</div>
			</c:if>
		</td>
		</c:if>
	</tr>
</c:forEach>