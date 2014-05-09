<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/" />
<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
</c:if>

<c:if test="${empty action && empty actionview}">
	<div class="print-layout">
		<div class="print-header">
			<h1>${pageTitle}</h1>
		</div>
		<table class="table-helper table">
			<colgroup>
				<col style="width: 10px"></col>
				<col style="width: 10px"></col>
				<col style="width: 10px"></col>
				<col style="width: 10px"></col>
				<col style="width: 10px"></col>
			</colgroup>
			<thead>
				<tr>
					<td>nama/npwp</td>
					<td>alamat</td>
					<td>telepon</td>
					<td>hp</td>
					<td>fax</td>
				</tr>
			</thead>
			<tbody id="tableValuePrint"></tbody>
		</table>
	</div>
	<div class="content notsw">
		<div class="main-content <c:if test="${!empty actionview}"> withright</c:if>">
			<div class="header-info">

				<c:if test="${empty action&&empty actionview}">
					<div class="header-attr">
						<a href="javascript:void(0);"
							class="btn netral print skip-check-dirty"><i
							class="icon-grey icon-print"></i>CETAK</a> <a
							href="${baseURL}accounts/${serviceName}/create"
							class="btn positive" id="newData"><i class="icon-plus"></i>Buat
							baru</a>
					</div>
				</c:if>

				<h1>${pageTitle}</h1>
			</div>
			<div class="pill-area">
			<p class="label">Total</p>
			<p class="content-pill">${countPeople}</p>
		</div>
		<c:if test="${!empty searchParameter.index_search}">
		<div class="pill-area">
			<p class="label">Kata kunci</p>
			<p class="content-pill">${searchParameter.index_search}</p>
		</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchStartDate && !empty searchParameter.searchEndDate}">
			<div class="pill-area">
				<p class="label">Tgl</p>
				<p class="content-pill">${searchParameter.searchStartDate} - ${searchParameter.searchEndDate}</p>
			</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchStatus}">
		<div class="pill-area">
			<p class="label">Status</p>
			<p class="content-pill"><c:if test="${searchParameter.searchStatus==1}">Aktif</c:if><c:if test="${searchParameter.searchStatus==0}">Non Aktif</c:if></p>
		</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchCity}">
		<div class="pill-area">
			<p class="label">Kota</p>
			<p class="content-pill">${searchParameter.searchCity}</p>
		</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchProvince}">
		<div class="pill-area">
			<p class="label">Provinsi</p>
			<p class="content-pill">${searchParameter.searchProvince}</p>
		</div>
		</c:if>
			<table>
				<colgroup>
					<col style="width: 10px"></col>
					<col style="width: 10px"></col>
					<col style="width: 10px"></col>
					<col style="width: 10px"></col>
					<col style="width: 10px"></col>
					<col style="width: 10px"></col>
				</colgroup>
				<thead>
					<tr>
						<td>nama/npwp</td>
						<td>alamat</td>
						<td>telepon</td>
						<td>hp</td>
						<td>fax</td>
						<td>&nbsp;</td>
					</tr>
				</thead>
				<tbody id="tableValue">

				</tbody>
			</table>
			<div class="btn_loadmore_area" id="btnPAgeNext"></div>
</c:if>


<c:if test="${actionview=='detail'}">
	<div class="print-layout">
		<div class="print-header">
			<div class="fl text-right">
				<h1>${formIN.name}</h1>
				<h2>${formIN.npwp}</h2>
				<p class="date">${formIN.street}, ${formIN.city}, ${formIN.province}</p>
			</div>
			<div class="fl">
			</div>
			<div class="fr">
				<div class="fieldprint">
					<label>TELEPHONE</label>
					<p>${formIN.telephone.equals("") ? "-" : formIN.telephone}</p>
				</div>
				<div class="fieldprint">
					<label>HP</label>
					<p class="price">${formIN.hp}</p>
				</div>
				<div class="fieldprint">
					<label>FAX</label>
					<p>${formIN.fax.equals("") ? "-" : formIN.fax}</p>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<table class="table-helper table">
			<colgroup>
				<col style="width: 10px"></col>
				<col style="width: 10px"></col>
				<col style="width: 10px"></col>
				<col style="width: 10px"></col>
				<col style="width: 10px"></col>
				<col style="width: 10px"></col>
				<col style="width: 10px"></col>
			</colgroup>
			<thead>
				<tr>
					<td>SO#</td>
					<td>PELANGGAN</td>
					<td>TGL KIRIM</td>
					<td align="right">ITEM</td>
					<td align="right">JUMLAH</td>
					<td>TGL</td>
					<td>STATUS</td>
				</tr>
			</thead>
			<tbody id="tableValuePrint"></tbody>
		</table>
	</div>
	<div class="content notsw">
		<div
			class="main-content<c:if test="${!empty actionview}"> withright</c:if>">
			<div class="header-info">

				<c:if test="${empty action&&empty actionview}">
					<div class="header-attr">
						<a href="javascript:void(0);"
							class="btn netral print skip-check-dirty"><i
							class="icon-grey icon-print"></i>CETAK</a> <a
							href="${baseURL}accounts/${serviceName}/create"
							class="btn positive" id="newData"><i class="icon-plus"></i>Buat
							baru</a>
						<h3>
							<c:if test="${countPeople>0}">${countPeople} orang</c:if>
						</h3>
					</div>
				</c:if>

				<h1>${pageTitle}</h1>
			</div>
			<table>
				<colgroup>
					<col style="width: 100px"></col>
					<col style="width: auto;"></col>
					<col style="width: 120px"></col>
					<col style="width: 60px"></col>
					<col style="width: 140px"></col>
					<col style="width: 120px"></col>
					<col style="width: 70px"></col>
				</colgroup>
				<thead>
					<tr>
						<td>SO#</td>
						<td>PELANGGAN</td>
						<td>TGL KIRIM</td>
						<td align="right">ITEM</td>
						<td align="right">JUMLAH</td>
						<td>TGL</td>
						<td>STATUS</td>
					</tr>
				</thead>
				<tbody id="tableValue">
				</tbody>
			</table>
			<div class="btn_loadmore_area" id="btnPAgeNext"></div>
</c:if>

<c:if test="${actionview=='detail'}">
	

	<div class="rightheader">
		<div class="btn-area">

			<a href="${baseURL}accounts/${serviceName}/${idPErson}/edit"
				class="btn netral"><i class="icon-grey icon-pencil"></i>RALAT</a> <a
				href="javascript:void(0);" class="btn netral print"><i
				class="icon-grey icon-print"></i>CETAK</a> <a
				href="${baseURL}accounts/${serviceName}" class="btn netral close"><i
				class="icon-cancel icon-grey"></i>KEMBALI</a>
		</div>
		<div class="form">
			<div class="field">
				<form:form modelAttribute="formIN" method="POST">
					<div class="field field-data">
						<label>NAMA:</label>
						<form:input path="name" readonly="true" class="focus-tab long fr  validation" data-validation="required"
							target-tab="#street" />
					</div>
					<div class="field field-data">
						<label class="">ALAMAT</label>
						<form:textarea path="street" readonly="true" class="focus-tab  validation" data-validation="required"
							target-tab="#city" />
					</div>
					<div class="field field-data">
						<label>KOTA:</label>
						<form:input path="city" readonly="true"
							class="focus-tab medium fr  validation" data-validation="required" target-tab="#province" />
					</div>
					<div class="field field-data">
						<label>PROVINSI:</label>
						<form:input path="province" readonly="true"
							class="focus-tab medium fr  validation" data-validation="required" target-tab="#telephone" />
					</div>
					<div class="field field-data">
						<label>TELEPON:</label>
						<form:input path="telephone" readonly="true"
							class="focus-tab medium fr  validation" data-validation="required" target-tab="#hp" />
					</div>
					<div class="field field-data">
						<label>HP:</label>
						<form:input path="hp" readonly="true" class="focus-tab medium fr  validation" data-validation="required"
							target-tab="#fax" />
					</div>
					<div class="field field-data">
						<label>FAX:</label>
						<form:input path="fax" readonly="true" class="focus-tab medium fr  validation" data-validation="required"
							target-tab="#npwp" />
					</div>
					<div class="field field-data">
						<label>NPWP:</label>
						<form:input path="npwp" readonly="true"
							class="focus-tab medium fr  validation" data-validation="required" target-tab="#save-button"
							target-type="button" />
					</div>
				</form:form>
			</div>
		</div>
</c:if>

<c:if test="${action=='create'||action=='update'}">

<div class="content">
<div class="main-content <c:if test="${!empty actionview}"> withright</c:if>">

	<div class="header-info">
		<h1>${pageTitle}</h1>
	</div>
	<div class="content-form">
		<form:form modelAttribute="formIN" method="POST">
			<div class="field">
				<label>NAMA:</label>
				<form:input path="name" class="focus-tab long  validation" data-validation="required" target-tab="#street" />
			</div>
			<div class="field">
				<label class="">ALAMAT</label>
				<form:textarea path="street" class="focus-tab  validation" data-validation="required" target-tab="#city" />
			</div>
			<div class="field">
				<label>KOTA:</label>
				<form:input path="city" class="focus-tab medium validation" data-validation="required"
					target-tab="#province" />
			</div>
			<div class="field">
				<label>PROVINSI:</label>
				<form:input path="province" class="focus-tab medium validation" data-validation="required"
					target-tab="#telephone" />
			</div>
			<div class="field">
				<label>TELEPON:</label>
				<form:input path="telephone" class="focus-tab medium number-filter validation" data-validation="required"
					target-tab="#hp" />
			</div>
			<div class="field">
				<label>HP:</label>
				<form:input path="hp" class="focus-tab medium number-filter validation" data-validation="required" target-tab="#fax" />
			</div>
			<div class="field">
				<label>FAX:</label>
				<form:input path="fax" class="focus-tab medium number-filter validation" data-validation="required" target-tab="#npwp" />
			</div>
			<div class="field">
				<label>NPWP:</label>
				<form:input path="npwp" class="focus-tab medium number-filter  validation" data-validation="required"
					target-tab="#save-button" target-type="button" />
			</div>
			<div class="field">
				<label>&nbsp;</label>
				<button id="save-button" class="btn positive focus-tab"
					target-tab="#btCancel" target-type="button">
					<i class="icon-file icon-white"></i>SIMPAN
				</button>
				<a href="${baseURL}accounts/${serviceName}"
					class="btn netral focus-tab" id="btCancel" target-tab="#name"><i
					class="icon-cancel icon-grey"></i>KEMBALI</a>
			</div>
		</form:form>
	</div>
</c:if>
</div>
</div>

<div style="display: none">
	<div class="popup alert" id="askDelete1"
		style="width: 400px !important">
		<div class="head_title">
			INFORMASI<a href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		<div class="popup-holder">
			<p>Apakah Anda yakin ingin menghapus ${pageTitle.toLowerCase()}
				ini?</p>
			<br /> <a href="" class="btn positive" id="btYesHapus1" delID="">Hapus</a>
			<a href="" class="btn netral btNotHapus1">Batal</a>
		</div>
	</div>
</div>
