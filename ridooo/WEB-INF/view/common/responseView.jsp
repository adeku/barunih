<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><c:choose>
	<c:when test="${empty sessionScope.u9988u}">
		<script>
			window.location
					.replace("${pageContext.request.contextPath}/logout");
		</script>
	</c:when>
	<c:otherwise>${responseView}</c:otherwise>
</c:choose>
