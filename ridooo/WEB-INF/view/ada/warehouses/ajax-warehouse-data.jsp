<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:url var="baseURL" value="/"/><c:if test="${baseURL=='//'}"><c:set var="baseURL" value="/"/></c:if><c:forEach items="${data}" var="data1">
	<tr>
			<td id="name-${data1.id}"><a href="${baseURL}warehouses/${data1.id}/detail">${data1.name}</a><c:if test="${data1.type=='Gudang Retail'}"><i class="icon-retail"></i></c:if> </td>
			<td id="street-${data1.id}">${data1.street}<br><span>${data1.city}<c:if test="${data1.city.length()>0&&data1.province.length()>0}">, </c:if>${data1.province}</span></td>
			<td id="telp-${data1.id}">${data1.telephone}</td>
			<td id="qty-${data1.id}">${data1.qty}</td>
			<td><input type="hidden" name="city-${data1.id}" value="${data1.city}">
			<input type="hidden" name="addressID-${data1.id}" value="${data1.addressID}">
			<input type="hidden" name="province-${data1.id}" value="${data1.province}">
			<input type="hidden" name="type-${data1.id}" value="${data1.type}">
			<div class="btn-group icon fr">
				<div class="btn">
					<i class="icon-dropdown"></i>
				</div>
				<ul class="rg">
					<li>
						<a href="" class="editData1" id="${data1.id}">Ralat</a>
					</li>
					<li>
						<a href="" class="ralatRak" compID="${data1.id}" id="${data1.id}" warehousName="${data1.name}">Ralat rak</a>
					</li>
					<li>
						<a href="" class="tambahRak" compID="${data1.id}" id="${data1.id}" warehousName="${data1.name}">Tambah rak</a>
					</li>
					<li>
					<a href="" class="cancel btnHapus1" id="${data1.id}">Hapus</a>
					</li>
				</ul>
			</div>
			</td>
	</tr>
	</c:forEach>