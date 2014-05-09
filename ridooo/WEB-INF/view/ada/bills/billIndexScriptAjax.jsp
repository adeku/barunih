<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:url var="baseURL" value="/purchase/bills"/><c:url var="URL" value="/"/><c:if test="${URL=='//'}"><c:set var="baseURL" value="/purchase/bills"/><c:set var="URL" value="/"/></c:if><c:forEach items="${data}" var="bill" varStatus="status">
						<tr>
						<c:if test="${canUpdate}">
							<td class="borderright" align="center">
								<input name="BillCheckboxes[${status.index}]" type="checkbox" value="${bill.id}">
							</td>
							</c:if>
							<td class="number">
								<a href="${baseURL}/${bill.id}/detail" class="upfont">BILL-${bill.id}</a>
							</td>
							<td>
								<h6><a href="${URL}contacts/suppliers/${bill.supplierId}/detail">${bill.supplierName}</a></h6>
								<span>${bill.supplierName}</span>
							</td>
							
							<td align="right" class="number price">
								${bill.totalItem}
							</td>
							<td align="right">
								${bill.refNumber}
							</td>
							<td align="right" class="number borderright price">
								${bill.totalAmount}
							</td>
							<td>
								${bill.trxDate}<br>
								<span>${bill.trxTime}</span>
							</td>
							<td>
								<c:choose>
									<c:when test="${bill.status == 'Tahan'}">
										<div class="status hold">
											${bill.status}
										</div>
									</c:when>
									<c:when test="${bill.status == 'Aktif'}">
										<div class="status hold active">
											${bill.status}
										</div>
									</c:when>
									<c:when test="${bill.status == 'Selesai'}">
										<div class="status done completed">
											${bill.status}
										</div>
									</c:when>
									<c:when test="${bill.status == 'Batal'}">
										<div class="status cancel cancelled">
											${bill.status}
										</div>
									</c:when>
									<c:otherwise>
										<div class="status hold">
											${bill.status}
										</div>
									</c:otherwise>
								</c:choose>
							</td>
							<c:if test="${canUpdate}">
							<td>
								<c:if test="${bill.status != 'Batal'}">
									<div class="btn-group icon fr">
										<div class="btn">
											<i class="icon-dropdown"></i>
										</div>
										
										<ul class="rg" style="display: none;">
											<c:if test="${bill.status == 'Aktif'}">
												<li><a href="${baseURL}/${bill.id}/edit">Ralat</a></li>
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
											
											<li><a href="${baseURL}/${bill.id}/change-status/cancel" class="cancel" data-id="${bill.id}">Batal</a></li>
										</ul>
									</div>
								</c:if>
							</td>
							</c:if>
						</tr>
					</c:forEach>