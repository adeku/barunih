<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:url var="baseURL" value="/"/><c:if test="${baseURL=='//'}"><c:set var="baseURL" value="/"/></c:if><c:forEach items="${data}" var="data1"><tr id="first">
							<td>${data1.name}</td>
							<td>${data1.email}</td>
						</tr>
						</c:forEach>