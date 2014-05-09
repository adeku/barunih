<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:url var="baseURL" value="/"/><c:if test="${baseURL=='//'}"><c:set var="baseURL" value="/"/></c:if><c:forEach items="${data}" var="data1"><tr id="first">
							<td>${data1.name}</td>
							<td>${data1.email}</td>
							<td><a href="${baseURL}accounts/rolenaccess/${data1.tiperole}/selected">${data1.tiperole}</a></td>
							<td>
							<div class="btn-group icon fr">
									<div class="btn">
										<i class="icon-dropdown"></i>
									</div>
									<ul class="rg">
										<li>											
											<a href="${baseURL}accounts/user-accounts/edit/${data1.id}">Ralat</a>
										</li>
<c:if test="${data1.id>2}">										
										<li>
											<a href="" class="cancel btnHapus1" id="${data1.id}">Hapus</a>
										</li>
</c:if>										
									</ul>
								</div>
							</td>
						</tr>
						</c:forEach>