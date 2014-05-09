<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/sales/sales-orders"/>
<c:url var="URL" value="/"/>
<c:if test="${URL=='//'}">
<c:set var="URL" value="/"/>
<c:set var="baseURL" value="/sales/sales-orders"/>
</c:if>
<div class="print-layout">
	<div class="print-header">
		<h1>NOTA PESAN</h1>
	</div>
	<table class="table-helper table">
		<colgroup>
	
			<col style="width:60px"></col>
			<col style="width:auto;"></col>
			<col style="width:70px"></col>
			<col style="width:30px"></col>
			<col style="width:80px"></col>
			<col style="width:70px"></col>
			<col style="width:50px"></col>
			
		</colgroup>
			<thead>
				<tr>
					<td>SO#</td>
					<td>PELANGGAN</td>
					<td>TGL KIRIM</td>
					<td align="right">ITEM</td>
					<td align="right">JUMLAH</td>
					<td>TGL</td>
					<td align="center">STATUS</td>
					
				</tr>
			</thead>
		<tbody id="tableValuePrint"></tbody>
		</table>
	</div>
	<div class="content notsw">
		<form:form modelAttribute="form" method="POST" action="${baseURL}/change-status">
			<div class="main-content">
				
				<!-- heading title -->
				<div class="header-info">
					<div class="header-attr">
					<c:if test="${canUpdate||canDelete}">
						<div class="btn-group" id="tools" >
							<a class="btn disabled" href="javascript:void(0);">Select item(s)</a>
							<div class="btn disabled" style="border-left: 0px;">
								<i class="icon-dropdown icon-grey disabled"></i>
							</div>
							<ul style="display: none;">
								<li><button id="make_process">Proses</button></li>
								<c:if test="${canUpdate}">
								<li><form:button name="button" value="hold">Tunda</form:button></li>
								<li><form:button name="button" value="paid">Tandai Selesai</form:button></li>
								</c:if>
								<c:if test="${canDelete}">
								<li><form:button name="button" value="cancel" class="cancel">Batal</form:button></li>
								</c:if>
								<li style="display:none;"><a>Tidak ada tools</a></li>
							</ul>
						</div>
						</c:if>
						<a href="javascript:void(0);" class="btn netral print"><i class="icon-grey icon-print"></i>CETAK</a>
						<c:if test="${canCreate}">
						<a href="${baseURL}/create" class="btn positive" id="create-button"><i class="icon-plus icon-white"></i>BUAT BARU</a>
						</c:if>
					</div>
					<h1>nota pesan</h1>
				</div>
				<div class="pill-area">
					<p class="label">Total</p>
					<p class="content-pill">${totalData}</p>
				</div>
				<c:if test="${!empty searchParameter.indexSearch}">
					<div class="pill-area">
						<p class="label">Kata Kunci</p>
						<p class="content-pill">${searchParameter.indexSearch}</p>
					</div>
				</c:if>
				<c:if test="${!empty searchParameter.startDate}">
					<div class="pill-area">
						<p class="label">Tgl</p>
						<p class="content-pill">${searchParameter.startDate} - ${searchParameter.endDate}</p>
					</div>
				</c:if>
				<c:if test="${!empty searchParameter.customerName}">
					<div class="pill-area">
						<p class="label">Pelanggan</p>
						<p class="content-pill">${searchParameter.customerName}</p>
					</div>
				</c:if>
				<c:if test="${!empty searchParameter.statusName}">
					<div class="pill-area">
						<p class="label">Status</p>
						<p class="content-pill">${searchParameter.statusName}</p>
					</div>
				</c:if>
				<c:if test="${!empty searchParameter.salesPersonName}">
					<div class="pill-area">
						<p class="label">Sales Person</p>
						<p class="content-pill">${searchParameter.salesPersonName}</p>
					</div>
				</c:if>
				<c:if test="${!empty searchParameter.retail}">
					<div class="pill-area">
						<p class="label">Retail</p>
						<p class="content-pill">${searchParameter.retail}</p>
					</div>
				</c:if>
				<!-- header info -->
				
				<!-- table -->
				<table>
					<colgroup>
					<c:if test="${canUpdate||canDelete}">
					<col style="width:40px"></col>
					</c:if>
					<col style="width:100px"></col>
					<col style="width:auto;min-width:500px;"></col>
					<col style="width:70px"></col>
					<col style="width:60px"></col>
					<col style="width:140px"></col>
					<col style="width:90px"></col>
					<col style="width:70px"></col>
					<c:if test="${canUpdate||canDelete}">
					<col style="width:50px"></col>
					</c:if>
					</colgroup>
					<thead>
						<tr>
						<c:if test="${canUpdate||canDelete}">
							<td align="center"><input class="check_all" type="checkbox"></td>
							</c:if>
							<td>SO#</td>
							<td>PELANGGAN</td>
							<td>TGL KIRIM</td>
							<td align="right">ITEM</td>
							<td align="right">JUMLAH</td>
							<td>TGL</td>
							<td>STATUS</td>
							<c:if test="${canUpdate||canDelete}">
							<td>&nbsp;</td>
							</c:if>
						</tr>
					</thead>
					<tbody id="tableValue">
					</tbody>
				</table>
				
				<div class="btn_loadmore_area" id="btnPAgeNext"></div>
			</div>
			<div class="clear"></div>
			<div style="display: none">
				<div class="popup alert" id="error"  >
					<div class="head_title">ERROR<a href="javascript:void(0)" class="fr close-popup"><i
						class="icon-blue icon-close"></i></a>
						<div class="clear"></div>
					</div>
					<div class="popup-holder">
						<p id="error-message">Pilih satu jenis sales order, retail atau grosir saja. Tidak bisa digabung.</p>
						<br/> <a id="error-button" href="javascript:void(0)" class="btn positive close-popup">OK</a>
						<div class="clear"></div>
					</div>
				</div>
				<div class="popup alert" id="retail-process" style="width: 400px !important">
					<div class="head_title">Retail Process<a href="javascript:void(0)" class="fr close-popup"><i
						class="icon-blue icon-close"></i></a>
						<div class="clear"></div>
					</div>
					<div class="popup-holder">
						<!-- 				<p><span id="checkedRetail"></span> Nota pesan terpilih. Siapkan surat pindah untuk barang-barang yang tidak tersedia di gudang retail?</p> -->
						<p><span id="checkedRetail"></span> Nota pesan terpilih. Barang hanya tersedia sebagian. Apakah Anda ingin melanjutkan?</p>
						<br/> <a href="#" class="btn positive" id="createTO">Lanjutkan</a> <a
						href="#" class="btn netral btNotHapus close-popup">Batal</a>
					</div>
				</div>
				<div class="popup alert" id="wholesale-process" style="width: 400px !important">
					<div class="head_title">Wholesale Process<a href="javascript:void(0)" class="fr close-popup"><i
						class="icon-blue icon-close"></i></a>
						<div class="clear"></div>
					</div>
					<div class="popup-holder">
						<p><span id="checkedWholesale"></span> Nota pesan terpilih. Buat nota jual dan surat jalan?</p>
						<br/> <a href="#" class="btn positive" id="make_invoices">Lanjutkan</a> <a
						href="#" class="btn netral btNotHapus close-popup">Batal</a>
					</div>
				</div>
				
				<div class="popup alert" id="cancel-alert" style="width: 300px !important">
					<div class="head_title">Batal<a href="javascript:void(0)" class="fr close-popup"><i
						class="icon-blue icon-close"></i></a>
						<div class="clear"></div>
					</div>
					<div class="popup-holder">
						<p>Apakah Anda yakin ingin membatalkan transaksi ini?</p>
						<br/> <a href="#" class="btn positive cancel-button">Ya</a> <a
						href="#" class="btn netral btNotHapus close-popup">Tidak</a>
					</div>
				</div>
				
				<div class="popup alert" id="cancel-form-alert" style="width: 400px !important">
					<div class="head_title">Batal<a href="javascript:void(0)" class="fr close-popup"><i
						class="icon-blue icon-close"></i></a>
						<div class="clear"></div>
					</div>
					<div class="popup-holder">
						<p>Apakah Anda yakin ingin membatalkan transaksi-transaksi ini?</p>
						<br/> <a href="#" class="btn positive cancel-form-button">Ya</a> <a
						href="#" class="btn netral btNotHapus close-popup">Tidak</a>
						<div class="clear"></div>
					</div>
				</div>
			</div>
		</form:form>
	</div>