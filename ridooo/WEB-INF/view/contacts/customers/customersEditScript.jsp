<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('#btnSimpan').on('click',function(e){
			e.preventDefault();
			$('#formIN').submit();
		  });
		$("body").keydown(function(e){
			if(e.keyCode == 112){
				e.preventDefault();				
			    $('#btnSimpan').trigger('click');
			}
		});
		$('#namaToko').focus();
	});
</script>