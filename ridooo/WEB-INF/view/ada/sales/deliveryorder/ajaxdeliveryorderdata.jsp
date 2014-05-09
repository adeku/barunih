<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:url var="base_url" value="/"/><c:if test="${base_url=='//'}"><c:set var="base_url" value="/"/></c:if><c:forEach items="${data}" var="salesOrder">
					<tr id="first">
					<c:if test="${canUpdate}">
						<td class="borderright" align="center">
							<input type="checkbox" name="id_do[]" value="${salesOrder.id}">
						</td>
						</c:if>
						<td class="number">
							<a href="${base_url}sales/deliveryorder/detail/${salesOrder.id}" class="upfont">${salesOrder.refNumber}</a>
						</td>
						<td>
							<h6><a href="${base_url}contacts/customers/${salesOrder.customerId}/detail">${salesOrder.customerName}</a></h6>
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
							<c:choose>
								<c:when test="${salesOrder.status == 'Tahan'}">
									<div class="status hold">
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
<%-- 							<div class="status ${salesOrder.status.equals('Batal') ? 'cancel' : (salesOrder.status.equals('Terkirim') ? 'done' : 'hold')} }"> --%>
<%-- 								&nbsp;&nbsp;${salesOrder.status}&nbsp;&nbsp; --%>
<!-- 							</div> -->
						</td>
						<c:if test="${canUpdate}">
						<td>
							<c:if test="${!salesOrder.status.toLowerCase().equals('batal')}">
								<div class="btn-group icon fr">
									<div class="btn">
										<i class="icon-dropdown"></i>
									</div>
	
									<ul class="rg" style="display: none;">
										
										<c:if test="${!salesOrder.status.toLowerCase().equals('selesai')}">
											<li><a href="${base_url}sales/deliveryorder/edit-form/${salesOrder.id}">Ralat</a></li>
											<li><a href="${base_url}sales/deliveryorder/edit/status/${salesOrder.id}/1">Terkirim</a></li>
										</c:if>
										<li><a href="${base_url}sales/deliveryorder/edit/status/${salesOrder.id}/-1" class="cancel">Batal</a></li>
									</ul>
								</div>
							</c:if>
						</td>
						</c:if>
					</tr>
				</c:forEach>