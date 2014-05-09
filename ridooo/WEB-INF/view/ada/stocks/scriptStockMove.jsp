<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/>

<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/"/>
</c:if>


<c:if test="${!empty limitDataPage}">
<script type="text/javascript">
var urlAjax = '${baseURL}inventory/stock-tomove/ajax-data-stockmove';
var nop = ${limitDataPage};
</script>
<script src="${baseURL}js/pagescroll.js" type="text/javascript"></script>
</c:if>	