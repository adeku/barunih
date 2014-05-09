<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:forEach
	items="${data}" var="data1">
	<c:url var="baseURL" value="/" />
	<tr>
		<c:if test="${canDelete||canUpdate}">
			<td align="center" class="borderright"><input name="idDAta[]"
				id="idDAta[]" type="checkbox" value="${data1.id}" /></td>
		</c:if>
		<c:url var="ediURL"
			value="/inventory/transfer-order/${data1.id}/detail" />
		<c:if test="${baseURL=='//'}">
			<c:set var="ediURL"
				value="/inventory/transfer-order/${data1.id}/detail" />
		</c:if>
		<td><a href="${ediURL}">${data1.refNumber}</a></td>
		<td>${data1.asal}<br />
		<span>${data1.sourceCity} ${data1.sourceProvince}</span></td>
		<td>${data1.tujuan}<br />
		<span>${data1.destinationCity} ${data1.destinationProvince}</span></td>
		<td>${data1.itemsCount}</td>
		<td>${data1.atDate}<br />
		<span>${data1.atTime}</span></td>
		<td>
		<c:if test="${data1.status==2}">
			<div class="status done completed">Selesai</div>
		</c:if>
		<c:if test="${data1.status==1}">
			<div class="status hold active">Aktif</div>
		</c:if> <c:if test="${data1.status==0}">
			<div class="status cancel cancelled">Batal</div>
		</c:if></td>
		<c:if test="${canDelete||canUpdate}">
			<td>
				<c:if test="${data1.status!=2 && data1.status != 0}">
					<div class="btn-group icon fr">
						<div class="btn">
							<i class="icon-dropdown"></i>
						</div>
						<ul class="rg">
							<c:if test="${canUpdate}">
								<li><c:url var="ediURL"
										value="/inventory/transfer-order/${data1.id}/edit" /> <c:if
										test="${baseURL=='//'}">
										<c:set var="ediURL"
											value="/inventory/transfer-order/${data1.id}/edit" />
									</c:if> <a href="${ediURL}">Ralat</a></li>

								<li><c:url var="ediURL"
										value="/inventory/transfer-order/${data1.id}/change-status-1data?status=2" />
									<c:if test="${baseURL=='//'}">
										<c:set var="ediURL"
											value="/inventory/transfer-order/${data1.id}/change-status-1data?status=2" />
									</c:if> <a href="${ediURL}">Selesai</a></li>
							</c:if>
							<c:if test="${canDelete}">
								<li><a href="" class="cancel btnHapus1" id="${data1.id}">Batal</a>
								</li>
							</c:if>

						</ul>
					</div>
				</c:if>
			</td>
		</c:if>
	</tr>
</c:forEach>
