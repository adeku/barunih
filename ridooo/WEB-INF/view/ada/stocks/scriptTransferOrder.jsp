<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:url var="ajaxData"
	value="/inventory/transfer-order/ajax-data-transfer-order" />
<c:url var="jsFile" value="/js/pagescroll.js" />

<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
	<c:set var="ajaxData"
		value="/inventory/transfer-order/ajax-data-transfer-order" />
	<c:set var="jsFile" value="/js/pagescroll.js" />
</c:if>


<c:if test="${!empty limitDataPage}">
	<script type="text/javascript">
var urlAjax = '${ajaxData}';
var nop = ${limitDataPage};
</script>
	<script src="${jsFile}" type="text/javascript"></script>
</c:if>

<script type="text/javascript">
function changeStatus1(id,stat) {
$.get("${baseURL}inventory/transfer-order/"+id+"/change-status-1data?status="+stat,function(result) { location.reload(); });
}

function changeStatusAll(stat) {
	$.post("${baseURL}inventory/transfer-order/change-status-datas/"+stat,$("#dataList").serialize(),function(result) { location.reload(); });
}

	$(document).ready(function() {		
		$("input.check_all").change(function(e){
			var is_checked = $(this).is(':checked');
			$(this).parents("table").find("tbody input[type=checkbox]").attr("checked", is_checked);
		});	
		
		$('#btYesHapus1').on('click',function(e){
			e.preventDefault();			
			$.colorbox.close();
			changeStatus1($(this).attr('delID'),0);
		 });
		
		$('.btNotHapus1').on('click',function(e){
			e.preventDefault();
			$.colorbox.close();
		 });
		
		$('#btnSelesaiSelected').on('click',function(e){
				e.preventDefault();
				$(this).blur();
				if ($('input[name="idDAta[]"]:checked').length>0) {
					changeStatusAll(2);
				} else {
					$.colorbox({
						inline : true,
						height:180,
						href : "#popupSelesai"
					});
				}
		 });
		
		$('.btNotHapus').on('click',function(e){
			e.preventDefault();
			$.colorbox.close();
		 });
		
		$('#btnHapusSelected').on('click',function(e){
			e.preventDefault();
			$(this).blur();
			if ($('input[name="idDAta[]"]:checked').length>0) {
				$.colorbox({
					inline : true,
					height:164,
					href : "#popup"
				});
			} else {
				$.colorbox({
					inline : true,
					height:164,
					href : "#infoCannotDelete"
				});
			}
		 });
		
		$('#btYesHapus').on('click',function(e){
			e.preventDefault();			
			$.colorbox.close();
			changeStatusAll(0);
		 });
		
		$('#tableValue').bind('click', function(event) {
			var elementHere = event.target;
			if (elementHere.getAttribute('class')!=null) {
			if (elementHere.getAttribute('class').indexOf("btnHapus1")!=-1) {
				event.preventDefault();
				elementHere.blur();
				$.colorbox({
					inline : true,
					height:164,
					href : "#askDelete1"
				});
				$('#btYesHapus1').attr('delID', elementHere.getAttribute('id'));				
			}
		}
		 });
		
		<c:if test="${!empty limitDataPage&&canCreate}">
		$("body").keydown(function(e){
			if(e.keyCode == 116){
				e.preventDefault();				
			     window.location.href = $('#newData').attr('href');
			}
		});
		</c:if>	
		

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