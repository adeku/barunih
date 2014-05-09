<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:forEach items="${data}" var="data1">
<c:url var="baseURL" value="/"/>
	<tr>
	<c:url var="urlDetail" value="/contacts/${serviceName}/${data1.id}/detail" />
	<c:if test="${baseURL=='//'}">
	<c:set var="urlDetail" value="/contacts/${serviceName}/${data1.id}/detail" />
	</c:if>
			<td><a href="${urlDetail}">${data1.nama}</a><br/><span>${data1.npwp}</span></td>
			<td>${data1.kontak}</td>
			<td>${data1.alamat}<br/><span>${data1.kota}<c:if test="${data1.kota.length()>0&&data1.province.length()>0}">, </c:if> ${data1.province}</span></td>
			<td>${data1.telepon}</td>
			<td>${data1.hp}</td>
			<td>${data1.fax}</td>
			<td>${data1.salespersonName}</td>
			<c:if test="${canUpdate||canDelete}">
			<td>
				<div class="btn-group icon fr">
									<div class="btn">
										<i class="icon-dropdown"></i>
									</div>
									<ul class="rg">
									<c:if test="${canUpdate}">
										<li>
										<c:url var="urlDetail" value="/contacts/${serviceName}/${data1.id}/edit" />
										<c:if test="${baseURL=='//'}">
										<c:set var="urlDetail" value="/contacts/${serviceName}/${data1.id}/edit" />
										</c:if>
											<a href="${urlDetail}">Ralat</a>
										</li>
									</c:if>
<c:if test="${canDelete}">
<li>
<a href="" class="cancel btnHapus1" id="${data1.id}">Hapus</a>
</li>
</c:if>
										
									</ul>
								</div>
			</td>
			</c:if>
		</tr>
</c:forEach>