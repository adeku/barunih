<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:url var="base_url" value="/"/><c:if test="${base_url=='//'}"><c:set var="base_url" value="/"/></c:if><c:forEach items="${data}" var="salesOrder">
					<tr id="first">
						<td class="number">
							<a href="${base_url}sales/deliveryorder/detail/${salesOrder.id}" class="upfont">${salesOrder.refNumber}</a>
						</td>
						<td>
							<a href="${base_url}contacts/customers/${salesOrder.customerId}/detail">${salesOrder.customerName}</a><br>
							<span>${salesOrder.customerCity}</span>
						</td>
<%-- 						<td>${salesOrder.deliveryDate}</td> --%>
						<td align="right" class="number borderright price">
							${salesOrder.totalItem}
						</td>
<!-- 						<td align="right" class="number borderright price"> -->
<%-- 							${salesOrder.totalAmount} --%>
<!-- 						</td> -->
						<td>
							${salesOrder.trxDate}<br>
							<span>${salesOrder.trxTime}</span>
						</td>
						<td>
							<div class="status ${salesOrder.status.equals('Cancelled') ? 'cancel' : (salesOrder.status.equals('Published') ? 'done' : 'hold')} }">
								&nbsp;&nbsp;${salesOrder.status}&nbsp;&nbsp;
							</div>
						</td>
					</tr>
				</c:forEach>