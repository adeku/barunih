<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/><c:if test="${baseURL=='//'}"><c:set var="baseURL" value="/"/></c:if>
<c:if test="${!empty limitDataPage}">
<script type="text/javascript">
var urlAjax = '${baseURL}accounts/user-accounts/ajax-data-useraccounts-inroleaccess/${positionSelected}';
var nop = ${limitDataPage};
var urlLoadingImage = '${baseURL}';
</script>
<script src="${baseURL}js/pagescroll1.js" type="text/javascript"></script>
</c:if>	