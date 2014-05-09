<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:forEach items="${data}" var="data1">
						<tr>
							<td>${data1.pelapor}</td>
							<td>${data1.warehouse}<br/><span>${data1.rakName}</span></td>
							<td>${data1.tgl}<br/><span>${data1.jam}</span></td>
							<td>${data1.sku}</td>
							<td>${data1.item}</td>
							<td>${data1.firstStock}</td>
							<td>${data1.sisa}</td>
							<td>${data1.lastStock}</td>
							
							<td>${data1.notes}</td>
						</tr>
						</c:forEach>