<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:forEach items="${data}" var="data1">
<c:url var="baseURL" value="/"/>
	<tr>			
	<c:url var="urlDetail" value="/accounts/${serviceName}/${data1.id}/detail"/>
	<c:if test="${baseURL=='//'}">
	<c:set var="urlDetail" value="/accounts/${serviceName}/${data1.id}/detail"/>
	</c:if>			
			<td><a href="${urlDetail}">${data1.name}</a><br/><span>${data1.npwp}</span></td>
			<td>${data1.address}<br/><span>${data1.city}<c:if test="${data1.city.length()>0&&data1.province.length()>0}">, </c:if> ${data1.province}</span></td>
			<td>${data1.telephone}</td>
			<td>${data1.hp}</td>
			<td>${data1.fax}</td>
			<td>
				<div class="btn-group icon fr">
									<div class="btn">
										<i class="icon-dropdown"></i>
									</div>
									<ul class="rg">
										<li>
										<c:url var="urlDetail" value="/accounts/${serviceName}/${data1.id}/edit"/>
										<c:if test="${baseURL=='//'}">
										<c:set var="urlDetail" value="/accounts/${serviceName}/${data1.id}/edit"/>
										</c:if>
											<a href="${urlDetail}">Ralat</a>
										</li>
<li>
<a href="" class="cancel btnHapus1" id="${data1.id}">Hapus</a>
</li>
										
									</ul>
								</div>
			</td>
		</tr>
</c:forEach>