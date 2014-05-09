<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:if
	test="${!empty data}">
	<c:url var="baseURL" value="/" />
	<c:forEach items="${data}" var="data1">
		<tr>
			<c:if test="${canUpdate||canDelete}">
				<td align="center" class="borderright"><input
					name="idCatalogItem[]" id="idCatalogItem[]" type="checkbox"
					value="${data1.id_catalog_item}" /></td>
			</c:if>
			<td><a href="#" id="${data1.id_catalog_item}"
				class="showDetailCatalog">${data1.sku}</a></td>
			<td>${data1.title}</td>
			<td align="right" class="borderright">${data1.category}</td>
			<td align="right" class="number borderright price">${data1.retailPrice}</td>
			<td align="right" class="number">${data1.weight}</td>
			<td align="right" class="number text-right">${data1.qtyinRetail}</td>
			<td align="right" class="number text-right">${data1.qtyinWholeSale}</td>
			<td>
			<c:if test="${data1.status_items==1}">
					<div class="status hold active">Aktif</div>
				</c:if> <c:if test="${data1.status_items==0}">
					<div class="status hold">Tahan</div>
				</c:if></td>
			<c:if test="${canUpdate||canDelete}">
				<td>
					<div class="btn-group icon fr">
						<div class="btn">
							<i class="icon-dropdown"></i>
						</div>
						<ul class="rg">
							<c:if test="${canUpdate}">
								<li><c:url var="dataDetail"
										value="/inventory/catalogs/${data1.id_catalog_item}/edit" />
									<c:if test="${baseURL=='//'}">
										<c:set var="dataDetail"
											value="/inventory/catalogs/${data1.id_catalog_item}/edit" />
									</c:if> <a href="${dataDetail}">Ralat</a></li>
								<!-- 						<li><a href="">Ralat Stok</a></li> -->

								<li><c:if test="${data1.status_items==1}">
										<a href="" class="btnTahan1" id="${data1.id_catalog_item}">Tahan</a>
									</c:if> <c:if test="${data1.status_items==0}">
										<a href="" class="btnAktif1" id="${data1.id_catalog_item}">Aktifkan</a>
									</c:if></li>
							</c:if>
							<c:if test="${canDelete}">
								<li><a href="" class="cancel btnHapus1"
									id="${data1.id_catalog_item}">Hapus</a></li>
							</c:if>
						</ul>
					</div>
				</td>
			</c:if>
		</tr>
	</c:forEach>
</c:if>