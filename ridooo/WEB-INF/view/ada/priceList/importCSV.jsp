<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- CONTENT -->

<script>
function download(filename, text) {
    var pom = document.createElement('a');
    pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    pom.setAttribute('download', filename);
    pom.click();
}

	$(document).ready(function() {
		$('#exporr').click(function(e) {
			e.preventDefault();
			<c:url var="exportCSV" value="/inventory/pricelist/csv-export" />	
			window.open('${exportCSV}','Get Data', 800, 600);
		});
	});
</script>
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
				<div class="content-header">					
					<h2>Import price list from CSV file</h2>
				</div>
				<form method="POST" action="import-cvs-do" enctype="multipart/form-data">
				Filenya : <input type="file" name="fl">
				<br>
				<button type="submit">Import</button>
				</form>
				<hr>
				<br><br>
				<button type="button" id="exporr">Export</button>
				
		</div>
		<div class="clear"></div>
	</div>
</div>
