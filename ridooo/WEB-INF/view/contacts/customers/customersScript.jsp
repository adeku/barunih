<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/>
<c:url var="dataAjax" value="/contacts/${serviceName}/ajax-data-customers"/>
<c:url var="jsFile" value="/js/pagescroll.js"/>

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/"/>
<c:set var="dataAjax" value="/contacts/${serviceName}/ajax-data-customers"/>
<c:set var="jsFile" value="/js/pagescroll.js"/>
</c:if>

<c:if test="${!empty limitDataPage}">
<script type="text/javascript">
var urlAjax = '${dataAjax}';
var nop = ${limitDataPage};
</script>

<script src="${jsFile}" type="text/javascript"></script>
</c:if>


<script type="text/javascript">
function changeStatus1(id,stat) {
	$('#frList').attr('action',"${baseURL}contacts/${serviceName}/"+id+"/change-status");
	$('#frList [name=status]').val(stat);
	$('#frList').submit();
}

	$(document).ready(function() {
		$('#btnSimpan').on('click',function(e){
			e.preventDefault();
			$('#formIN').submit();
		  });		
		$('#tableValue').bind('click', function(event) {
			var elementHere = event.target;
			if (elementHere.getAttribute('class')!=null) {
			if (elementHere.getAttribute('class').indexOf("btnHapus1")!=-1) {
				elementHere.blur();
				event.preventDefault();	
				$.colorbox({
					inline : true,
					height:161,
					href : "#askDelete1"
				});
				$('#btYesHapus1').attr('delID',elementHere.getAttribute('id'));
			}
		}
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
		
		<c:if test="${!empty limitDataPage&&canCreate}">
		$("body").keydown(function(e){
			if(e.keyCode == 116){
				e.preventDefault();				
			     window.location.href = $('#newData').attr('href');
			}
		});
		</c:if>	
		
		
	});
</script>