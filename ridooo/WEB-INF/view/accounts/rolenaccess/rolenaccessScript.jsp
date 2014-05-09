<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/>

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/"/>
</c:if>
<script type="text/javascript">
	$(document).ready(function() {
		$('#btnSave').on('click',function(e){
			e.preventDefault();
			$('#formIN').submit();
		  });
		$('#buatBaru').on('click',function(e){
			e.preventDefault();
			$.colorbox({
				inline : true,
				height:174,
				href : "#addRole",
				onComplete: function() {
					$('#roleName').focus();
				}
			});
		});
		
		$('#btSavePosition').on('click',function(e){
			var canSave = true;
			msg = '';
			if (canSave&&$('#roleName').val().length<1) {
				canSave = false;
				msg = 'Tulis nama peran';
			}
			if (canSave) {
				$('#fr').submit();
			} else {
				$('#errorMessage').text(msg);
			}
		});
		
		$('#btDeletePeran').on('click',function(e){	
			e.preventDefault();
			$.colorbox({
				inline : true,
				height:174,
				href : "#askDelete"
			});
		});
		$('.btNotHapus1').on('click',function(e){	
			e.preventDefault();
			$.colorbox.close();
		});
		$('#btYesHapus1').on('click',function(e){
			e.preventDefault();
			$('#formDelete').attr("action", "${baseURL}accounts/rolenaccess/${positionSelected}/deleted");
			$('#formDelete').submit();
		});
		
		$("body").keydown(function(e){
			if(e.keyCode == 116){
				e.preventDefault();				
				$('#buatBaru').trigger('click');
			} else if(e.keyCode == 112){
				e.preventDefault();		
				if ($('#addRole').parent().is(':visible')){
					$('#btSavePosition').trigger('click');
				}
			}
		});
	});
</script>