<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/>
<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/"/>
</c:if>

<script type="text/javascript">
var urlLoadingImage = '${baseURL}';
<c:if test="${pageHere.equalsIgnoreCase(\"detail\")}">
var urlAjax = '${baseURL}inventory/catalogs/ajax-data-catalogs-inwarehouse/${id}';
</c:if>
<c:if test="${pageHere.equalsIgnoreCase(\"kodebongkar\")}">
var urlAjax = '${baseURL}inventory/catalogs/ajax-data-catalogs-inwarehouse-kodebongkar/${id}';
</c:if>
var nop = ${limitDataPage};
</script>
<script src="${baseURL}js/pagescroll1.js" type="text/javascript"></script>