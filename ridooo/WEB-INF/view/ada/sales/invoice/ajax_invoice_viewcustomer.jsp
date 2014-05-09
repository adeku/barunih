<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:url var="base_url" value="/" /><c:if test="${base_url=='//'}"><c:set var="base_url" value="/" /></c:if><c:forEach items="${data}" var="invoice">
					<tr id="first">
						<td class="number">
							<a href="${base_url}sales/invoice/detail/${invoice.id}" class="upfont">${invoice.refNumber}</a>
						</td>
						<td>
							<a href="${base_url}contacts/customers/${invoice.customerId}/detail">${invoice.customerName}</a><br>
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
							<div class="status ${invoice.status.equals('Batal') ? 'cancel' : (invoice.status.equals('Lunas') ? 'done' : 'hold')} }">
								&nbsp;&nbsp;${invoice.status}&nbsp;&nbsp;
							</div>
						</td>
					</tr>
				</c:forEach>