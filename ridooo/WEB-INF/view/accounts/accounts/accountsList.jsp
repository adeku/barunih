<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib
	uri="http://www.springframework.org/tags/form" prefix="form"%><c:url
	var="baseURL" value="/" />
<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
</c:if>
<div class="print-layout">
	<div class="print-header">
		<h1>${pageTitle}</h1>
	</div>
	<table class="table-helper table">
		<colgroup>
			<col style="width: auto"></col>
			<col style="width: auto"></col>
			<col style="width: 200px"></col>
		</colgroup>
		<thead>
			<tr>
				<td>Nama</td>
				<td>Email</td>
				<td>Tipe akses</td>
			</tr>
		</thead>
		<tbody id="tableValuePrint"></tbody>
	</table>
</div>
<div class="content notsw">
	<div
		class="main-content<c:if test="${action=='detail'}"> withright</c:if>">
		<div class="header-info">

			<c:if test="${empty action}">
				<div class="header-attr">
					<a href="javascript:void(0);"
						class="btn netral print skip-check-dirty"><i
						class="icon-grey icon-print"></i>CETAK</a> <a
						href="${baseURL}accounts/user-accounts/create"
						class="btn positive" id="newData"><i class="icon-plus"></i>Buat
						baru</a>
				</div>
				<form id="formList" method="post"></form>
			</c:if>
			<h1>${pageTitle}</h1>
		</div>
		<c:if test="${empty action}">
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
		</c:if>

		<c:if test="${empty action}">
			<table>
				<colgroup>
					<col style="width: auto"></col>
					<col style="width: auto"></col>
					<col style="width: 200px"></col>
					<col style="width: 200px"></col>
				</colgroup>
				<thead>
					<tr>
						<td>Nama</td>
						<td>Email</td>
						<td>Tipe akses</td>
						<td>&nbsp;</td>
					</tr>
				</thead>
				<tbody id="tableValue"></tbody>
			</table>
			<div class="btn_loadmore_area" id="btnPAgeNext"></div>
		</c:if>

		<c:if test="${action=='detail'}">
			<table>
				<colgroup>
					<col style="width: 100px"></col>
					<col style="width: auto; min-width: 500px;"></col>
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
		</c:if>

		<div class="btn_loadmore_area" id="btnPAgeNext"></div>
		<c:if test="${action=='detail'}">

			<div class="rightheader">
				<div class="btn-area">
					<a href="${baseURL}accounts/user-accounts/edit/${idPErson}"
						class="btn positive"><i class="icon-file"></i>ralat</a> <a
						href="${baseURL}accounts/user-accounts" class="btn netral close"><i
						class="icon-cancel icon-grey"></i>KEMBALI</a>
				</div>
				<div class="form">
					<div class="field">
						<form:form modelAttribute="formIN" method="POST">
							<div class="field">
								<label>NAMA:</label>
								<form:input path="name" class="focus-tab long fr validation"
									data-validation="required" target-tab="#street" />
							</div>
							<div class="field">
								<label class="">ALAMAT</label>
								<form:textarea path="street" class="focus-tab fr validation"
									data-validation="required" target-tab="#city" />
							</div>
							<div class="field">
								<label>KOTA:</label>
								<form:input path="city" class="focus-tab medium fr validation"
									data-validation="required" target-tab="#province" />
							</div>
							<div class="field">
								<label>PROVINSI:</label>
								<form:input path="province"
									class="focus-tab medium fr validation"
									data-validation="required" target-tab="#telephone" />
							</div>
							<div class="field">
								<label>TELEPON:</label>
								<form:input path="telephone"
									class="focus-tab medium fr validation"
									data-validation="required" target-tab="#hp" />
							</div>
							<div class="field">
								<label>HP:</label>
								<form:input path="hp" class="focus-tab medium fr validation"
									data-validation="required" target-tab="#fax" />
							</div>
							<div class="field">
								<label>FAX:</label>
								<form:input path="fax" class="focus-tab medium fr validation"
									data-validation="required" target-tab="#npwp" />
							</div>
							<div class="field">
								<label>NPWP:</label>
								<form:input path="npwp" class="focus-tab medium fr"
									target-tab="#btnSimpan" target-type="button" />
							</div>
						</form:form>
					</div>
				</div>
		</c:if>

		<c:if test="${action=='create'||action=='update'}">
			<div class="content-form">
				<form:form modelAttribute="formIN" method="POST">
					<form:hidden path="newRoleType" />
					<c:if test="${idAccount>2||empty idAccount}">
						<div class="field">
							<label>NAMA STAFF:</label>
							<div class="fl" style="background: #ffffff">
								<div class="autocomplete-container">
									<c:if test="${action=='create'}">
										<form:input path="idPerson"
											class="focus-tab autocomplete validation"
											data-validation="required" target-type="select"
											target-tab="#roleType" />
									</c:if>
									<c:if test="${action=='update'}">
										<form:input path="idPerson"
											class="focus-tab validation validation"
											data-validation="required" target-type="select"
											target-tab="#roleType" readonly="true" />
									</c:if>
								</div>
							</div>
							<div class="clear"></div>
						</div>


					<div class="field" id="selectRoleType"
						<c:if test="${formIN.newRoleType=='1'}">style="display:none"</c:if>>
						<label>Tipe akses:</label>
						<div class="field-content">
							<form:select class="select focus-tab" path="roleType"
								items="${roleTypeOption}" target-tab="#personEmail" />
						</div>
					</div>

					</c:if>
					<div class="clear"></div>
					<div class="field">
						<label>Email :</label>
						<form:input path="personEmail" class="input-small focus-tab validation"
							data-validation="required,email" target-tab="#password1" value="" />
					</div>

					<div class="field">
						<label>Password:</label>
						<form:password path="password1" class="input-small focus-tab validation" style="padding-right:6px"
							data-validation="required" target-tab="#password2"
							autocomplete="off" value="" />
					</div>

					<div class="field">
						<label>Re Password:</label>
						<form:password path="password2" class="input-small focus-tab validation"  style="padding-right:6px"
							data-validation="required,same_with,min" data-validation-min="8"
							data-validation-same="#password1" target-type="button"
							target-tab="#save-button" />
					</div>

					<div class="field">
						<label>&nbsp;</label>
						<button id="save-button" class="btn positive focus-tab skip-check"
							target-tab="#btCancel" target-type="button">
							<i class="icon-file icon-white"></i>SIMPAN
						</button>
						<a href="${baseURL}accounts/user-accounts"
							class="btn netral focus-tab" id="btCancel" target-tab="#idPerson"><i
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
			<p>Apakah Anda yakin akan menghapus akun ini?</p>
			<br /> <a href="" class="btn positive" id="btYesHapus1" delID="">Hapus</a>
			<a href="" class="btn netral btNotHapus1">Batal</a>
		</div>
	</div>
</div>
