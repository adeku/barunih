<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:forEach items="${data}" var="data1">
						<tr>
							<td>
								${data1.pelapor}<br/>
								<span>${data1.tgl}</span>
							</td>
							<td>${data1.warehouse}<br/><span>${data1.rakName}</span></td>
							<td>
								${data1.item}<br/>
								<span>${data1.sku}</span>
							</td>
							<td>
								${data1.documentDescription}<br/>
								<c:if test="${data1.in_out}">Masuk</c:if><c:if test="${!data1.in_out}">Keluar</c:if>
							</td>
							<td>
								${data1.lastStock}<br/>
								${data1.notes.trim().equals('') || data1.notes == null ? '-' : data1.notes}
							</td>
						</tr>
						</c:forEach>