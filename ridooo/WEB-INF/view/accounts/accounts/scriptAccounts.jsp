<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/><c:if test="${baseURL=='//'}"><c:set var="baseURL" value="/"/></c:if>
<c:if test="${!empty limitDataPage}">
<script type="text/javascript">
var urlAjax = '${baseURL}accounts/user-accounts/ajax-data-useraccounts';
var nop = ${limitDataPage};
</script>
<script src="${baseURL}js/pagescroll.js" type="text/javascript"></script>
</c:if>	
<script type="text/javascript">
function personAutoComplete() {
	core.ui.autocomplete({
		data : "${baseURL}accounts/staff/ajax-auto-complete",
		<c:if test="${!empty formIN.idPerson && !empty staffName}">
		prePopulate : [{id:"${formIN.idPerson}",name:"${staffName}"}],
		</c:if>
		hintText : 'Silahkan ketikkan nama Staf...',
		onDelete : null,
		onAdd :null,
		onResult : null,
		onReady : function(){
			$("#token-input-idPerson").focus();
			$("#token-input-idPerson").parents("ul").css("background", "#ffffff");
			$("#token-input-idPerson").css("border", "0");
		},
		minChars : 1,
		width 	: 200,
	});
}
$(document).ready(function() {
	personAutoComplete();
	$('#ShowAddRole').on('click',function(e){
		e.preventDefault();			
		$('#selectRoleType').hide();
		$('#newRoleType').val('1');
		$('#RoleTypeAdd').fadeIn('slow');			
	});
	
	$('.btNotHapus1').on('click',function(e){
		e.preventDefault();
		$.colorbox.close();
	});
	
	$('#ShowselectRole').on('click',function(e){
		e.preventDefault();			
		$('#RoleTypeAdd').hide();
		$('#newRoleType').val('0');
		$('#selectRoleType').fadeIn('slow');			
	});
	
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
		$('#formList').attr("action", "${baseURL}accounts/user-accounts/delete/"+$('#btYesHapus1').attr('delID'));
		$('#formList').submit();
	 });
	
	<c:if test="${!empty limitDataPage}">
	$("body").keydown(function(e){
		if(e.keyCode == 116){
			e.preventDefault();
		     window.location.href = $('#newData').attr('href');
		}
	});
	</c:if>
	
	<c:if test="${!empty action}">
	$("body").keydown(function(e){
		if(e.keyCode == 112){
			e.preventDefault();				
		    $('#btnSimpan').trigger('click');
		}
	});
	<c:if test="${idAccount>2||empty idAccount}">
	$('#idPerson').focus();
	</c:if>
	<c:if test="${idAccount==2}">
	$('#personEmail').focus();
	</c:if>
	</c:if>
});
</script>