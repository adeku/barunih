<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:url var="baseURL" value="/"/><c:forEach items="${data}" var="data1">
						<tr id="first">
						<c:if test="${canDelete}">
							<td align="center" class="borderright">
							<c:if test="${data1.canDeleted}">
								<input name="idBrand[]" id="idBrand[]" type="checkbox" value="${data1.id}" />
								</c:if>
							</td>
						</c:if>
							<c:url var="dataDetail" value="/inventory/brands/${data1.id}/detail" />
							<c:if test="${baseURL=='//'}">
							<c:set var="dataDetail" value="/inventory/brands/${data1.id}/detail" />
							</c:if>
							<td><a href ="${dataDetail}">${data1.title}</a></td>
							<td>${data1.content}</td>
							<td>
								${data1.modifiedDate}<br>
								<span>${data1.modifiedTime}</span>
							</td>
<c:if test="${canDelete||canUpdate}">
							<td>
								<div class="btn-group icon fr">
									<div class="btn">
										<i class="icon-dropdown"></i>
									</div>
									<ul class="rg">
									<c:if test="${canUpdate}">
										<li>
											<c:url var="dataDetail" value="/inventory/brands/${data1.id}/edit" />
											<c:if test="${baseURL=='//'}">
											<c:set var="dataDetail" value="/inventory/brands/${data1.id}/edit" />
											</c:if>
											<a href="${dataDetail}">Ralat</a>
										</li>
									</c:if>
<c:if test="${canDelete}">
<li>
<a href="" class="cancel btnHapus1" candel="${data1.canDeleted}" id="${data1.id}">Hapus</a>
</li>
</c:if>
										
									</ul>
								</div>
							</td>
</c:if>
						</tr>
						</c:forEach>