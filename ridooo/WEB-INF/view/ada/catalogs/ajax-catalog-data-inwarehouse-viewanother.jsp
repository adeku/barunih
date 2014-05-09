<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:if test="${!empty data}"><c:url var="baseURL" value="/"/><c:forEach items="${data}" var="data1">
		<tr>
			<td>${data1.rak_name}</td>
			<td  class="borderright">${data1.sku}</td>
			<td align="left">
				${data1.title}@${data1.unit}<br>
				<span>${data1.brandName}</span></td>
			<td align="right" class="number">${data1.weight}</td>
			<td>
				<c:if test="${data1.status_items==1}"><div class="status hold active">Aktif</div></c:if>
				<c:if test="${data1.status_items==0}"><div class="status hold">Tahan</div></c:if>
			</td>
			<td align="right">${data1.qoh}@${data1.unit}<br/><span class="price prices">${data1.totalqoh}</span></td>
		</tr>
	</c:forEach>
</c:if>