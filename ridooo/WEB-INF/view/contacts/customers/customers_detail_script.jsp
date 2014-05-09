<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />

<c:if test="${serviceName=='customers'}">
	<c:url var="dataAjax" value="/sales/invoice/ajax-data-notajual-in-customer/${idPErson}"/>
</c:if>

<c:if test="${serviceName=='suppliers'}">
	<c:url var="dataAjax" value="/purchase/bills/ajax-data-purchasebill-suppliers/${idPErson}"/>
</c:if>

<c:if test="${serviceName=='couriers'}">
	<c:url var="dataAjax" value="/sales/deliveryorder/ajax-data-deliveryorder-courier/${idPErson}"/>
</c:if>


<c:url var="jsFile" value="/js/pagescroll1.js"/>



<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/" />
<c:if test="${serviceName=='customers'}">
	<c:set var="dataAjax" value="/sales/invoice/ajax-data-notajual-in-customer/${idPErson}"/>
</c:if>
<c:if test="${serviceName=='suppliers'}">
	<c:set var="dataAjax" value="/purchase/bills/ajax-data-purchasebill-suppliers/${idPErson}"/>
</c:if>

<c:if test="${serviceName=='couriers'}">
	<c:set var="dataAjax" value="/sales/deliveryorder/ajax-data-deliveryorder-courier/${idPErson}"/>
</c:if>
<c:set var="jsFile" value="/js/pagescroll1.js"/>
</c:if>

<c:if test="${!empty limitDataPage}">
<script type="text/javascript">
var urlAjax = '${dataAjax}';
var nop = ${limitDataPage};
var urlLoadingImage = '${baseURL}';
</script>
<script src="${jsFile}" type="text/javascript"></script>
</c:if>