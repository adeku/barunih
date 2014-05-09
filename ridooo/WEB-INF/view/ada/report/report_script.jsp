<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/sales/sales-orders"/>
<c:url var="URL" value="/"/>
<c:if test="${URL=='//'}">
<c:set var="URL" value="/"/>
<c:set var="baseURL" value="/sales/sales-orders"/>
</c:if>
<script type="text/javascript" >
	$(document).ready(function(e){
		$( "#dateFrom" ).datepicker( "setDate", "${dateFrom}" );
		$( "#dateTo" ).datepicker( "setDate", "${dateTo}" );
	});
</script>