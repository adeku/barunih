<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:url var="baseURL" value="/purchase/bills"/><c:url var="URL" value="/"/><c:if test="${URL=='//'}"><c:set var="baseURL" value="/purchase/bills"/><c:set var="URL" value="/"/></c:if><c:forEach items="${data}" var="bill" varStatus="status">
						<tr>							
							<td class="number">
								<a href="${baseURL}/${bill.id}/detail" class="upfont">${bill.refNumber}</a>
							</td>
							<td>
								<a href="${URL}contacts/suppliers/${bill.supplierId}/detail">${bill.supplierName}</a><br>
								<span>${bill.supplierName}</span>
							</td>
							
							<td align="right" class="number price">
								${bill.totalItem}
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
									<c:when test="${bill.status == 'Hold'}">
										<div class="status hold">
											${bill.status}
										</div>
									</c:when>
									<c:when test="${bill.status == 'Completed'}">
										<div class="status done">
											${bill.status}
										</div>
									</c:when>
									<c:when test="${bill.status == 'Cancelled'}">
										<div class="status cancel">
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
						</tr>
					</c:forEach>