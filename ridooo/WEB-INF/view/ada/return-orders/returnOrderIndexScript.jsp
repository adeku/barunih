<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="URL" value="/"/>
<c:url var="ajaxData" value="/return-orders/ajax-data-return-orders"/>
<c:url var="jsFile" value="/js/pagescroll.js"/>

<c:if test="${URL=='//'}">
<c:set var="URL" value="/"/>
<c:set var="ajaxData" value="/return-orders/ajax-data-return-orders"/>
<c:set var="jsFile" value="/js/pagescroll.js"/>
</c:if>

<c:if test="${!empty limitDataPage}">
<script type="text/javascript">
var urlAjax = '${ajaxData}';
var nop = ${limitDataPage};

$(document).ready(function(e){
	
	$("table").on("change", "input:checkbox", function(e){
		if($("table input:checkbox:checked").length > 0){
			$("#tools").children("a").html("Tools");
			$("#tools").find("div.disabled, a.disabled").removeClass("disabled").addClass("netral");
			$("#tools").find("i.icon-dropdown").removeClass("disabled").addClass("icon-grey");
			$("#tools").find("div.netral").addClass("dropdown");
		}else{
			$("#tools").children("a").html("Select item(s)");
			$("#tools").find("div.netral, a.netral").removeClass("netral").addClass("disabled");
			$("#tools").find("i.icon-dropdown").addClass("disabled");
			$("#tools").find("div.disabled").removeClass("dropdown");
		}
	});
	
});
</script>
<script src="${jsFile}" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(e){

	$("body").on("click","a.cancel", function(e){
		e.preventDefault();
		$.colorbox({
			inline : true,
			href : "#cancel-alert",
//	 		height : 207,
		});
		$("#cancel-alert a.cancel-button").attr('href', $(this).attr("href"));
	});
	
	$("body").on("click","button.cancel", function(e){
		e.preventDefault();
		$.colorbox({
			inline : true,
			href : "#cancel-form-alert",
		});
		$("#cancel-form-alert a.cancel-form-button").attr('id', 'cancel-form');
	});

	$("body").on("click", "a#cancel-form", function(e){
		e.preventDefault();
		$('form').append('<input type="hidden" name="button" value="cancel" />');
		$('form').submit();
	});
});
</script>
</c:if>	