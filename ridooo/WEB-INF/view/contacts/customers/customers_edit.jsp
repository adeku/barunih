<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/"/><c:if test="${baseURL=='//'}"><c:set var="baseURL" value="/"/></c:if>
<div class="content">		
<div class="main-content">
	<div class="header-info">
		<h1>${pageTitle}</h1>
	</div>
<div class="content-form">
<form:form modelAttribute="formIN" method="POST">	
<div class="field">
	<label>NAMA TOKO:</label>
	<form:input path="namaToko" class="focus-tab long validation" data-validation="required" target-tab="#type" target-type="select"/>
</div>
<div class="field">
	<label>TIPE:</label>
	<div class="field-content">
		<form:select class="select focus-tab  validation" data-validation="required" path="type" items="${typeOption}" target-tab="#kontak"/>
	</div>
</div>
<div class="field">
	<label>KONTAK:</label>
	<form:input path="kontak" class="focus-tab medium" target-tab="#alamat"/>
</div>
<div class="field">
	<label class="">ALAMAT</label>
	<form:textarea path="alamat" class="focus-tab" target-tab="#kota"/>
</div>
<div class="field">
	<label>KOTA:</label>
	<form:input path="kota" class="focus-tab medium  validation" data-validation="required" target-tab="#propinsi"/>
</div>
<div class="field">
	<label>PROVINSI:</label>
	<form:input path="propinsi" class="focus-tab medium" target-tab="#telepon"/>
</div>
<div class="field">
	<label>TELEPON:</label>
	<form:input path="telepon" class="focus-tab medium" target-tab="#hp"/>
</div>
<div class="field">
	<label>HP:</label>
	<form:input path="hp" class="focus-tab medium number-filter" target-tab="#fax"/>
</div>
<div class="field">
	<label>FAX:</label>
	<form:input path="fax" class="focus-tab medium number-filter" target-tab="#npwp"/>
</div>
<div class="field">
	<label>NPWP:</label>
	<form:input path="npwp"  class="focus-tab medium number-filter" target-tab="#salesperson" target-type="select"/>
</div>
<div class="field">
	<label>SALESPERSON:</label>
	<div class="field-content">
	<form:select class="select focus-tab" path="salesperson" items="${salesOption}" target-tab="#btnSimpan" target-type="button"/>
	</div>
</div>

<div class="field">
	<label>&nbsp;</label>
			<button class="btn positive focus-tab mr10"  id="btnSimpan" target-tab="#btCancel" target-type="button"><i class="icon-file icon-white"></i>SIMPAN</button>
			<a href="${baseURL}contacts/${serviceName}" class="btn netral focus-tab" target-tab="#namaToko" id="btCancel"><i class="icon-cancel icon-grey"></i>KEMBALI</a>
			
		</div>
</form:form> 
</div>           
</div>
</div>