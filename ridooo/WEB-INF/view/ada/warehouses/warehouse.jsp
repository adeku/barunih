<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="baseURL" value="/" />
<c:set var="onServer" value="0" />

<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
	<c:set var="onServer" value="1" />
</c:if>
<div class="print-layout">
	<div class="print-header">
		<h1>Gudang</h1>
	</div>
	<table class="table-helper table">
		<colgroup>
			<col style="width: auto"></col>
			<col style="width: auto"></col>
			<col style="width: auto"></col>
			<col style="width: auto"></col>
		</colgroup>
		<thead>
			<tr>
				<td>Gudang</td>
				<td>Alamat</td>
				<td>Telephone</td>
				<td>Jumlah barang</td>
			</tr>
		</thead>
		<tbody  id="tableValuePrint"></tbody>
	</table>
</div>
<div class="content notsw">
	<div class="main-content  withright">
		<div class="header-info">
			<div class="header-attr">
				<a href="javascript:void(0);" class="btn netral print skip-check-dirty"><i class="icon-grey icon-print"></i>CETAK</a>
				<a href="" class="btn positive" id="btnAddWarehouse"><i
					class="icon-plus"></i>Buat baru</a>
					
			</div>
			<h1>
				<c:if test="${action=='create'}">ADD </c:if>
				gudang
			</h1>
			<c:if test="${!empty searchParameter.searchStatus &&searchParameter.searchStatus==1}"> Aktif</c:if>
			<c:if test="${!empty searchParameter.searchStatus && searchParameter.searchStatus==0}">Non Aktif</c:if>
		</div>
		<div class="pill-area">
			<p class="label">Total</p>
			<p class="content-pill">${countData}</p>
		</div>
		<c:if test="${!empty searchParameter.index_search}">
		<div class="pill-area">
			<p class="label">Kata kunci</p>
			<p class="content-pill">${searchParameter.index_search}</p>
		</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchStatus}">
		<div class="pill-area">
			<p class="label">Status</p>
			<p class="content-pill"><c:if test="${searchParameter.searchStatus==1}">Aktif</c:if><c:if test="${searchParameter.searchStatus==0}">Non Aktif</c:if></p>
		</div>
		</c:if>
		<table>
			<colgroup>
				<col style="width: 130px"></col>
				<col style="width: auto"></col>
				<col style="width: 140px"></col>
				<col style="width: 130px"></col>
				<col style="width: 50px"></col>
			</colgroup>
			<thead>
				<tr>
					<td>Gudang#</td>
					<td>Alamat</td>
					<td>Telephone</td>
					<td>Jumlah barang</td>
					<td>&nbsp;</td>
				</tr>
			</thead>
			<tbody id="tableValue"></tbody>
		</table>
		<div class="btn_loadmore_area" id="btnPAgeNext"></div>
	</div>
	<div class="rightheader">
		<div class="form">
			<h3 id="RakNameWarehouse">Rak</h3>
			<table>
				<colgroup>
					<col style="width: auto"></col>
					<col style="width: 71px"></col>
					<col style="width: 48px"></col>
				</colgroup>
				<thead>
					<tr>
						<td>rak</td>
						<td>jumlah</td>
						<td></td>
					</tr>
				</thead>
				<tbody id="tableValueRak"></tbody>
			</table>
			<div class="btn_loadmore_area" id="btnPAgeNextRak"></div>
		</div>
	</div>
</div>

<div style="display: none">
	<div class="popup alert" id="addWArehouse"
		style="width: 300px !important">
		<div class="head_title">
			<span id="infoTitle">Tambah warehouse</span><a
				href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		<div class="field " id="msgWaregouseErr"></div>
		<div class="popup-holder">
			<c:url var="addWArehouse" value="/warehouses/addwarehouse" />
			<c:if test="${onServer=='1'}">
				<c:set var="addWArehouse" value="/warehouses/addwarehouse" />
			</c:if>
			<form:form action="${addWArehouse}" modelAttribute="frAddWarehouse">
				<form:hidden path="warehouseID" />
				<form:hidden path="addressID" />
				<div class="form">
					
					<div class="field">
						<label>Nama </label>
						<form:input path="name" class="fr" />
					</div>
					<div class="field">
						<label>Alamat </label>
						<form:input path="street" class="fr" />
					</div>
					<div class="field">
						<label>Telepon </label>
						<form:input path="telephone" class="fr number-filter" />
					</div>
					<div class="field">
						<label>Kota </label>
						<form:input path="city" class="fr" />
					</div>
					<div class="field">
						<label>Provinsi </label>
						<form:input path="province" class="fr focus-tab"
							target-tab="#type" target-type="select" />
					</div>
					<div class="field">
						<label class="fl mr10">Tipe Gudang</label>
						<div id="selectType fr">
							<form:select class="select fr focus-tab" path="type"
								target-tab="#btSave" target-type="button"
								items="${type_warehouse_option}" />
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</form:form>
			<div class="field">
				<label>&nbsp;</label><button class="btn positive focus-tab skip-check-dirty"
					target-tab="#btCanceLWArehouse" target-type="button" id="btSave">Simpan</button>
				<a href="" class="btn netral btNotHapus focus-tab"
					id="btCanceLWArehouse" target-tab="#name">Batal</a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>

<div style="display: none">
	<div class="popup alert" id="addRakk" style="width: 300px !important">
		<div class="head_title">
			<span id="infoTitleRak">Tambah warehouse</span><a
				href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		<div class="popup-holder">
			<c:url var="addurlRakk" value="/warehouses/addrak" />
			<c:if test="${onServer=='1'}">
				<c:set var="addurlRakk" value="/warehouses/addrak" />
			</c:if>
			<form:form action="${addurlRakk}" modelAttribute="frRakk">
				<form:hidden path="wareHouseID" />
				<form:hidden path="idRakk" />
				<div class="form">
					<div class="field" style="background-color: red; color: white;"
						id="rakmsgWaregouseErr"></div>
					<div class="field">
						<label>Nama RAK</label>
						<form:input path="RAkname" class="fr focus-tab"
							target-tab="#btSaveRak" target-type="button" />
					</div>
				</div>
			</form:form>
			<button class="btn positive focus-tab" id="btSaveRak"
				target-tab="#btCancelRak" target-type="button">Simpan</button> <a href=""
				class="btn netral btNotHapus focus-tab" id="btCancelRak"
				target-tab="#RAkname">Batal</a>
		</div>
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
			<p>Apakah Anda yakin ingin menghapus gudang ini?</p>
			<br /> <a href="" class="btn positive" id="btYesHapus1" delID="">Hapus</a>
			<a href="" class="btn netral btNotHapus1">Batal</a>
		</div>
	</div>
</div>

<div style="display: none">
	<div class="popup alert" id="askDelete1Rak"
		style="width: 400px !important">
		<div class="head_title">
			INFORMASI<a href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		<div class="popup-holder">
			<p>Apakah Anda yakin ingin menghapus rak ini?</p>
			<br /> <a href="" class="btn positive" id="btYesHapus1Rak" delID="">Hapus</a>
			<a href="" class="btn netral btNotHapus1">Batal</a>
		</div>
	</div>
</div>
<form id="frList" method="post"><input type="hidden" name="id"><input type="hidden" name="status"></form>