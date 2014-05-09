<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><c:url var="baseURL" value="/return-orders"/><c:url var="URL" value="/"/><c:if test="${URL=='//'}"><c:set var="baseURL" value="/return-orders"/><c:set var="URL" value="/"/></c:if><c:forEach items="${data}" var="returnOrder" varStatus="status">
						<tr>
							<c:if test="${canUpdate}">
							<td class="borderright" align="center">
								<input name="RTCheckboxes[${status.index}]" type="checkbox" value="${returnOrder.id}">
							</td>
							</c:if>
							<td class="number">
								<a href="${baseURL}/${returnOrder.id}/detail" class="upfont">${returnOrder.refNumber}</a>
							</td>
							<td>
								<a href="${URL}contacts/customers/${returnOrder.customerId}/detail">${returnOrder.customerName}</a><br>
								<span>${returnOrder.customerCity}</span>
							</td>
							
							<td align="right" class="number price">
								${returnOrder.totalItem}
							</td>
							<td align="right" class="number borderright price">
								${returnOrder.totalAmount}
							</td>
							<td>
								${returnOrder.trxDate}<br>
								<span>${returnOrder.trxTime}</span>
							</td>
							<td>
								<c:choose>
									<c:when test="${returnOrder.status == 'Tahan'}">
										<div class="status hold">
											${returnOrder.status}
										</div>
									</c:when>
									<c:when test="${returnOrder.status == 'Aktif'}">
										<div class="status active">
											${returnOrder.status}
										</div>
									</c:when>
									<c:when test="${returnOrder.status == 'Selesai'}">
										<div class="status completed">
											${returnOrder.status}
										</div>
									</c:when>
									<c:when test="${returnOrder.status == 'Batal'}">
										<div class="status cancelled">
											${returnOrder.status}
										</div>
									</c:when>
									<c:otherwise>
										<div class="status hold">
											${returnOrder.status}
										</div>
									</c:otherwise>
								</c:choose>
							</td>
							<c:if test="${canUpdate}">
							<td>
								<c:if test="${returnOrder.status != 'Batal'}">
									<div class="btn-group icon fr">
										<div class="btn">
											<i class="icon-dropdown"></i>
										</div>
										<ul class="rg" style="display: none;">
											<c:if test="${returnOrder.editable}">
												<li><a href="${baseURL}/${returnOrder.id}/edit">Ralat</a></li>
											</c:if>
	<%-- 										<c:choose> --%>
	<%-- 											<c:when test="${returnOrder.status == 'Hold' || returnOrder.status == 'Draft'}"> --%>
	<%-- 												<li><a href="${baseURL}/${returnOrder.id}/change-status/active">Active</a></li> --%>
	<%-- 											</c:when> --%>
	<%-- 											<c:when test="${returnOrder.status == 'Active'}"> --%>
	<%-- 												<li><a href="${baseURL}/${returnOrder.id}/change-status/hold">Hold</a></li> --%>
	<%-- 											</c:when> --%>
	<%-- 											<c:otherwise> --%>
	<%-- 												<li><a href="${baseURL}/${returnOrder.id}/change-status/hold">Hold</a></li> --%>
	<%-- 											</c:otherwise> --%>
	<%-- 										</c:choose> --%>
											<li><a href="${baseURL}/${returnOrder.id}/change-status/cancel" class="cancel">Batal</a></li>
										</ul>
									</div>
								</c:if>
							</td>
							</c:if>
						</tr>
					</c:forEach>