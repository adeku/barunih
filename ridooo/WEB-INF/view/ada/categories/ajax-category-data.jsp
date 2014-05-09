<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:forEach items="${data}" var="data1">
<c:url var="baseURL" value="/"/>
						<tr>
						<c:url var="dataDetail" value="/inventory/categories/${data1.id}/detail" />
						<c:if test="${baseURL=='//'}">
						<c:set var="dataDetail" value="/inventory/categories/${data1.id}/detail" />
						</c:if>
							<td><a href="${dataDetail}">${data1.name}</a></td>
							<td><p class="description">${data1.description}</p> </td>
							<td align="right"><p class="number">${data1.count}</p></td>
<c:if test="${canDelete||canUpdate}">							
							<td align="center">
								<div class="btn-group icon fr">
									<div class="btn">
										<i class="icon-dropdown"></i>
									</div>
									<ul class="rg">
									<c:if test="${canUpdate}">	
										<li>
										<c:url var="dataDetail" value="/inventory/categories/${data1.id}/edit" />
										<c:if test="${baseURL=='//'}">
										<c:set var="dataDetail" value="/inventory/categories/${data1.id}/edit" />
										</c:if>
											<a href="${dataDetail}">Ralat</a>
										</li>
</c:if>										
<c:if test="${canDelete}">	
										<li>
											<a href="" id="${data1.id}" class="cancel btHapus1">Hapus</a>
										</li>
</c:if>
									</ul>
								</div>
							</td>
</c:if>							
						</tr>
						</c:forEach>