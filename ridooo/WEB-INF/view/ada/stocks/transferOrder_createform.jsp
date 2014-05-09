<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
</c:if>
<form id="formIN" name="formIN" method="POST">
	<div class="content">
		<div class="main-content withright">
			<!-- heading tittle -->
			<h1>surat pindah<c:if test="${action == 'create'}"> baru</c:if></h1>
			<!-- header info -->
			<br />
			<br />
			<!-- table -->
			<table class="invoice_table" style="${empty dataBefore.catalogs ? 'display:none' : '' }">
				<colgroup>
					<col style="width: 60px; text-align: right;">
					<col style="width: 60px">
					<col style="width: 120px">
					<col style="width: auto">
					<col style="width: 200px">
					<col style="width: 200px">
					<col style="width: 40px">
				</colgroup>

				<thead>
					<tr>
						<td align="center">QTY</td>
						<td>UNIT</td>
						<td>SKU</td>
						<td>ITEM</td>
						<td align="right">RAK ASAL</td>
						<td align="right">RAK Tujuan</td>
						<td>&nbsp;</td>
					</tr>
				</thead>
				<tbody>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="7"><a href="javascript:void('Tambah Barang')"
							class="add_item">Tambah Barang</a></td>
					</tr>
				</tfoot>
			</table>
			<div class="empty-state" style="${empty dataBefore.catalogs ? '' : 'display:none' }">
				<i class="empty-icon entry"></i>
				<h4>Silakan mulai dengan mengisi data warehouse di sisi kanan.</h4>
			</div>
		</div>
		<div class="rightheader">
			<div class="btn-area">
				<button class="btn positive" id="btSave"><i class="icon-file icon-white"></i>SIMPAN</button> 
				<a href="${baseURL}inventory/transfer-order" id="cancel" class="btn netral focus-tab" target-tab="#name"><i class="icon-cancel icon-grey"></i>KEMBALI</a>
			</div>

			<div class="totalbar ">
				<span class="total-weight">0</span> <small>kg</small> <span
					class="total-item">0</span> <small>items</small>
			</div>
			<div class="form">
				<div class="field">
					<label>Tgl dibuat:</label>
					<c:set var="dateCreated1" value="${dataBefore.dateCreated}" />
					<c:if test="${empty dateCreated1}">
						<c:set var="dateCreated1" value="${dateCreated}" />
					</c:if>
					<input class="normal fr text-right validation" data-validation="required" value="${dateCreated1}" type="text" readonly="readonly">
				</div>
				
				<c:set var="dateCreated1" value="${dataBefore.dateModified}" />
				<div class="field">
					<label>Tgl proses:</label>
					<div class="field-content fr">
						<div class="input-date input-append">
							<div class="input-icon">
								<i class="icon-calendar"></i>
							</div>
							<input type="text" class="datepicker focus-tab validation" data-validation="required" target-tab="#sourceWarehouse"  style="width: 67px" name="prosesDate" id="prosesDate"/>
						</div>
					</div>
				</div>
				
				
				<div class="inputholder">
					<label>Asal:</label> <input
						class="medium text-right border-bottom focus-tab validation" data-validation="required" type="text"
						name="sourceWarehouse" id="sourceWarehouse"
						target-type="autocomplete" target-tab="#destinationeWarehouse" >
					<textarea class="long" id="addressAsal" readonly></textarea>
				</div>
				<div class="inputholder">
					<label>Tujuan:</label> <input
						class="medium text-right border-bottom focus-tab validation" data-validation="required" type="text"
						name="destinationeWarehouse" id="destinationeWarehouse"
						target-type="autocomplete" target-tab="#product">
					<textarea class="long" id="addressDestination" readonly></textarea>
				</div>
			</div>
		</div>



		<div style="display: none">
			<div class="popup alert" id="popup">
				<div class="head_title">
					PERHATIAN<a href="javascript:void(0)" class="fr close-popup"><i
						class="icon-blue icon-close"></i></a>
					<div class="clear"></div>
				</div>
				<div class="popup-holder">
					<p>Anda harus memilih customer terlebih dahulu. Input customer
						berada pada sidebar sebelah kanan!!!</p>
					<br /> <a href="javascript:void(0)" class="btn positive">PILIH
						CUSTOMER</a>
				</div>
			</div>
		</div>
		<div style="display: none">
			<div class="popup alert" id="noWArehouseSource">
				<div class="head_title">
					PERHATIAN<a href="javascript:void(0)" class="fr close-popup"><i
						class="icon-blue icon-close"></i></a>
					<div class="clear"></div>
				</div>
				<div class="popup-holder">
					<p>Anda harus memilih wrehouse terlebih dahulu. Input warehouse
						berada pada sidebar sebelah kanan!!!</p>
					<br /> <a href="" class="btn positive" id="btnOK">OK</a>
				</div>
			</div>
		</div>
	</div>
</form>