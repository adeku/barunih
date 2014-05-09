<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/purchase/receive-orders"/>
<c:url var="URL" value="/"/>

<c:if test="${URL=='//'}">
<c:set var="baseURL" value="/purchase/receive-orders"/>
<c:set var="URL" value="/"/>
</c:if>

<div class="print-layout">
	<div class="print-header">
		<h1>Surat Terima</h1>
	</div>
	<table class="table-helper table">
				<colgroup>
					<col style="width:100px"></col>
					<col style="width:auto;min-width:620px;"></col>
					<col style="width:100px"></col>
					<col style="width:60px"></col>
					<col style="width:90px"></col>
					<col style="width:90px"></col>
					<col style="width:70px"></col>
				</colgroup>
				<thead>
					<tr>
						<td>RO#</td>
						<td>VENDOR/PELANGGAN</td>
						<td>REF NUMBER</td>
						<td align="right">ITEM</td>
						<td align="right">BERAT</td>
						<td>TGL</td>
						<td align="center">STATUS</td>
					</tr>
				</thead>
			<tbody  id="tableValuePrint"></tbody>
		</table>
</div>
<div class="content notsw">
	<div class="main-content">
		<form:form modelAttribute="form" method="POST" action="${baseURL}/change-status">
			<!-- heading title -->
			<div class="header-info">
				<div class="header-attr">
				<c:if test="${canUpdate}">
					<div class="btn-group" id="tools">
						<a class="btn netral disabled" href="">Select item(s)</a>
						<div class="btn disabled">
							<i class="icon-dropdown icon-grey disabled"></i>
						</div>
						<ul style="display: none;">
							<li><form:button name="button" class="cancel" value="cancel">Batal</form:button></li>
						</ul>
					</div>
					</c:if>
					<a href="javascript:void(0);" class="btn netral print skip-check-dirty"><i class="icon-grey icon-print"></i>CETAK</a>
					
<%-- 					<a href="${baseURL}/create" class="btn positive"><i class="icon-plus icon-white"></i>NEW</a> --%>
				</div>
				<h1>surat terima</h1>
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
			<c:if test="${!empty searchParameter.statusName}">
				<div class="pill-area">
					<p class="label">Status</p>
					<p class="content-pill">${searchParameter.statusName}</p>
				</div>
			</c:if>
			<c:if test="${!empty searchParameter.vendorCustomerName}">
				<div class="pill-area">
					<p class="label">Vendor</p>
					<p class="content-pill">${searchParameter.vendorCustomerName}</p>
				</div>
			</c:if>
			<!-- header info -->
	
			<!-- table -->
			<table>
				<colgroup>
				<c:if test="${canUpdate}">
					<col style="width:40px"></col>
					</c:if>
					<col style="width:100px"></col>
					<col style="width:auto;min-width:620px;"></col>
					<col style="width:100px"></col>
					<col style="width:60px"></col>
					<col style="width:90px"></col>
					<col style="width:90px"></col>
					<col style="width:70px"></col>
					<c:if test="${canUpdate}">
					<col style="width:50px"></col>
					</c:if>
				</colgroup>
				<thead>
					<tr>
					<c:if test="${canUpdate}">
						<td align="center"><input class="check_all" type="checkbox"></td>
						</c:if>
						<td>RO#</td>
						<td>VENDOR/PELANGGAN</td>
						<td>REF NUMBER</td>
						<td align="right">ITEM</td>
						<td align="right">BERAT</td>
						<td>TGL</td>
						<td>STATUS</td>
						<c:if test="${canUpdate}">
						<td>&nbsp;</td>
						</c:if>
					</tr>
				</thead>
				<tbody id="tableValue">
					
				</tbody>
			</table>
			<div class="btn_loadmore_area" id="btnPAgeNext"></div>
		</form:form>
	</div>
	<div class="clear"></</div>
	
	<div style="display: none">
		<div class="popup alert" id="popup" style="width: 400px !important">
			<div class="head_title">
				PILIH SUMBER NOTA<a href="" class="fr"><i
					class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Sumber nota bisa berasal dari transaksi penjualan yang sudah
					ada atau membuat nota baru yang tidak berhubungan dengan transaksi
					penjualan.</p>

				<p>Silahkan pilih sumber nota?</p>
				<br /> <a href="javascript:create_from_list()" class="btn positive" id="inv_so">DARI SALES ORDER</a> 
				<c:if test="${canCreate}">
				<a href="${base_url}sales/invoice/create-form" class="btn positive">BUAT BARU</a>
					</c:if>
					 <a href="" class="btn netral">CANCEL</a>
			</div>
		</div>
		<div class="popup alert" id="cancel-alert">
			<div class="head_title">Batal<a href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Apakah Anda yakin ingin membatalkan transaksi ini?</p>
				<br/> <a href="#" class="btn positive cancel-button">Ya</a> <a
				href="#" class="btn netral btNotHapus close-popup">Tidak</a>
				<div class="clear"></div>
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
</div>