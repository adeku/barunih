<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:if test="${!empty data}"><c:url var="baseURL" value="/"/><c:forEach items="${data}" var="data1">
		<tr>
			<td><a href="#" id="${data1.id_catalog_item}" class="showDetailCatalog">${data1.sku}</a></td>
			<td>${data1.title}</td>
			<td align="right" class="borderright">${data1.category}</td>
			<td align="right" class="number">${data1.weight}</td>
			<td align="right" class="number">${data1.qtyonhand}</td>
			<td>
				<c:if test="${data1.status_items==1}"><div class="status hold active">Aktif</div></c:if>
				<c:if test="${data1.status_items==0}"><div class="status hold">Tahan</div></c:if>
			</td>
		</tr>
	</c:forEach>
</c:if>