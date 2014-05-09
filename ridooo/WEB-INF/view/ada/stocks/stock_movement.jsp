<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/" />
<c:url var="addAction" value="/inventory/stock-movement/create" />
<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/"/>
<c:set var="addAction" value="/inventory/stock-movement/create" />
</c:if>
<div class="print-layout">
	<div class="print-header">
		<h1>Penyesuaian Stok</h1>
	</div>
	<table class="table-helper table">
			<colgroup>
				<col style="width: auto"></col>
				<col style="width: auto"></col>
				<col style="width: auto"></col>
				<col style="width: auto"></col>
				<col style="width: auto"></col>
				<col style="width: auto"></col>
				<col style="width: auto"></col>
				<col style="width: auto"></col>
				<col style="width: auto"></col>
			</colgroup>
			<thead>
				<tr>
					<td>pelapor</td>
					<td>Gudang</td>
					<td>tgl</td>
					<td>sku</td>
					<td>item</td>
					<td>awal</td>
					<td>selisih</td>
					<td>akhir</td>
					<td>catatan</td>
				</tr>
			</thead>
			<tbody  id="tableValuePrint"></tbody>
	</table>
</div>
<div class="content notsw">
	<div class="main-content">
		<!-- heading tittle -->
		<div class="header-info">
		
			<div class="header-attr">
				<a href="javascript:void(0);" class="btn netral print skip-check-dirty"><i class="icon-grey icon-print"></i>CETAK</a>
				<c:if test="${canCreate}">
				<a href="" class="btn positive" id="btnAdd"><i class="icon-plus"></i>Buat baru</a>
				</c:if>
			</div>
		
			<h1>penyesuaian stok</h1>
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
		<c:if test="${!empty searchParameter.searchStartDate && !empty searchParameter.searchEndDate}">
			<div class="pill-area">
				<p class="label">Tgl</p>
				<p class="content-pill">${searchParameter.searchStartDate} - ${searchParameter.searchEndDate}</p>
			</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchStatus}">
			<div class="pill-area">
				<p class="label">Status</p>
				<p class="content-pill"><c:if test="${searchParameter.searchStatus==1}">Available</c:if><c:if test="${searchParameter.searchStatus==2}">Not Available</c:if><c:if test="${searchParameter.searchStatus==3}">Exclude Out Of Stock</c:if><c:if test="${searchParameter.searchStatus==4}">Exclude Low Of Stock</c:if></p>
			</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchItemName}">
			<div class="pill-area">
				<p class="label">Barang</p>
				<p class="content-pill">${searchParameter.searchItemName}</p>
			</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchWarehouse}">
			<div class="pill-area">
				<p class="label">Gudang</p>
				<p class="content-pill">${searchParameter.WarehouseName}</p>
			</div>
		</c:if>
		<!-- header info -->

		<!-- table -->
		<table>
			<colgroup>
				<col style="width: 120px"></col>
				<col style="width: 150px"></col>
				<col style="width: 100px"></col>
				<col style="width: 130px"></col>
				<col style="width: auto"></col>
				<col style="width: 90px"></col>
				<col style="width: 90px"></col>
				<col style="width: 90px"></col>
				<col style="width: 150px"></col>
			</colgroup>
			<thead>
				<tr>
					<td>pelapor</td>
					<td>Gudang</td>
					<td>tgl</td>
					<td>sku</td>
					<td>item</td>
					<td>awal</td>
					<td>selisih</td>
					<td>akhir</td>
					<td>catatan</td>
				</tr>
			</thead>
			<tbody id="tableValue">

			</tbody>
		</table>
		<div class="btn_loadmore_area" id="btnPAgeNext"></div>
	</div>
</div>

<div style="display: none">
	<div class="popup alert" id="formAdd" style="width: 600px !important">
		<div class="head_title">
			<span id="titleForm"></span><a href="javascript:void(0)"
				class="fr close-popup"><i class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		<div class="popup-holder">			
			<form:form modelAttribute="formIN" action="${addAction}"
				method="POST">
				<div class="field" style="background-color: red" id="alertError">

				</div>
				<div class="field">
					<label>Nama Barang :</label>
					<div class="item_dropdown">
						<form:input path="catalog_item_id" cssClass="focus-tab validation" data-validation="required"
							target-tab="#warehouse" target-type="select" />
					</div>
				</div>

				<div class="field fl" style="margin-right: 20px;">
					<label>Gudang :</label> <br />
					<form:select class="select focus-tab" path="warehouse" target-tab="#rak" target-type="select"
						items="${warehouse_option}" />
				</div>
				<div class="field fl" style="margin-right: 20px;" id="elementRak">
					<label>Rak :</label> <br />
						<form:select class="select focus-tab" path="rak" items="${rakOptions}" target-tab="#group" target-type="select" />
				</div>
				<div class="clear"></div>
				<div class="field fl" style="margin-right: 20px;">
					<label>Group :</label> <br />
					<form:select class="select focus-tab" path="group" items="${groupOption}" target-tab="#qty"/>
				</div>
				<div class="field fl">
					<label>Penyesuaian:</label> <br />
					<form:input path="qty" class="fl focus-tab"
						style="width:30px; margin-right:10px;" target-tab="#notes"/>
					<form:input path="maxQty" class="fl" style="width:30px;"
						readonly="true" />
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="field">
					<label>Alasan :</label>
					<form:textarea path="notes" cssClass="focus-tab" target-tab="#btSAve" target-type="button" style="border: 1px solid #ccc;"/>
				</div>
			</form:form>
			<br /> <button id="btSAve" class="btn positive focus-tab" target-tab="#btCancel" target-type="button"  ><i class="icon-file icon-white" ></i>SIMPAN</button> 
			<a href="" class="btn netral focus-tab" id="btCancel"  target-tab="#catalog_item_id" target-type="autocomplete"><i class="icon-cancel icon-grey"></i>kembali</a>
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
			<p>Are you sure you want to delete this item from the stock
				movement?</p>
			<br /> <a href="" class="btn positive" id="btYesHapus1" delID="">Hapus</a>
			<a href="" class="btn netral btNotHapus1">Batal</a>
		</div>
	</div>
</div>
