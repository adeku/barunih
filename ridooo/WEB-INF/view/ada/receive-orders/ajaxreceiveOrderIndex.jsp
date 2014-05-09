<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:url var="baseURL" value="/" /><c:if test="${baseURL=='//'}"><c:set var="baseURL" value="/" /></c:if><c:forEach items="${data}" var="receiveOrder" varStatus="status">
						<tr>
						<c:if test="${canUpdate}">
							<td class="borderright" align="center">
								<input name="RTCheckboxes[${status.index}]" type="checkbox" value="${receiveOrder.id}">
							</td>
							</c:if>
							<td class="number">
								<a href="${baseURL}purchase/receive-orders/${receiveOrder.id}/detail" class="upfont">${receiveOrder.roNumber}</a>
							</td>
							<td>
								<c:choose>
									<c:when test="${receiveOrder.supplierCity}">
										<h6><a href="${baseURL}contacts/suppliers/${receiveOrder.supplierId}/detail">${receiveOrder.supplierName}</a></h6>
									</c:when>
									<c:otherwise>
										<h6><a href="${baseURL}inventory/transfer-order/${receiveOrder.documentId}/detail">${receiveOrder.supplierName}</a></h6>
									</c:otherwise>
								</c:choose>
								<span>${receiveOrder.supplierCity}</span>
							</td>
							<td class="number">
								${receiveOrder.refNumber}
							</td>
							
							<td align="right" class="number price">
								${receiveOrder.totalItem}
							</td>
							
							<td align="left" class="text-right number borderright ">
								${receiveOrder.totalWeight} KG
							</td>
							
							<td>
								${receiveOrder.trxDate}<br>
								<span>${receiveOrder.trxTime}</span>
							</td>
							<td>
								<c:choose>
									<c:when test="${receiveOrder.status == 'Aktif'}">
										<div class="status hold active">
											${receiveOrder.status}
										</div>
									</c:when>
									<c:when test="${receiveOrder.status == 'Selesai'}">
										<div class="status done completed">
											${receiveOrder.status}
										</div>
									</c:when>
									<c:when test="${receiveOrder.status == 'Batal'}">
										<div class="status cancel cancelled">
											${receiveOrder.status}
										</div>
									</c:when>
									<c:otherwise>
										<div class="status hold">
											${receiveOrder.status}
										</div>
									</c:otherwise>
								</c:choose>
							</td>
							<c:if test="${canUpdate}">
							<td>
								<c:if test="${receiveOrder.status == 'Aktif'}">
									<div class="btn-group icon fr">
										<div class="btn">
											<i class="icon-dropdown"></i>
										</div>
										<ul class="rg" style="display: none;">
											
												<li><a href="${baseURL}purchase/receive-orders/${receiveOrder.id}/edit">Ralat</a></li>
												<li><a href="${baseURL}purchase/receive-orders/${receiveOrder.id}/edit-status/cancel" class="cancel">Batal</a></li>
											
											
	<%-- 										<c:choose> --%>
	<%-- 											<c:when test="${receiveOrder.status == 'Hold' || receiveOrder.status == 'Draft'}"> --%>
	<%-- 												<li><a href="${baseURL}/${receiveOrder.id}/change-status/active">Active</a></li> --%>
	<%-- 											</c:when> --%>
	<%-- 										</c:choose> --%>
											
										</ul>
									</div>
								</c:if>
							</td>
							</c:if>
						</tr>
					</c:forEach>