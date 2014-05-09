<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="base_url" value="/" />
<c:if test="${base_url=='//'}">
<c:set var="base_url" value="/" />
</c:if>

<div class="print-layout">
	<div class="print-header">
		<h1>SURAT JALAN</h1>
	</div>
	<table class="table-helper table">
			<colgroup>
				<col style="width:100px"></col>
				<col style="width:auto;min-width:500px;"></col>
<%-- 				<col style="width:70px"></col> --%>
				<col style="width:60px"></col>
<%-- 				<col style="width:auto;min-width:120px"></col> --%>
				<col style="width:90px"></col>
				<col style="width:70px"></col>
				
			</colgroup>
			<thead>
				<tr>
					<td>INV#</td>
					<td>PELANGGAN</td>
					<td align="right">ITEM</td>
					<td>TGL</td>
					<td align="center">STATUS</td>
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
			<c:if test="${canUpdate}">
				<div class="btn-group" id="tools">
					<a class="btn netral disabled" href="">Select item(s)</a>
					<div class="btn disabled" style="border-left: 0px;">
						<i class="icon-dropdown icon-grey disabled"></i>
					</div>
					<ul style="display: none;">
						<li><a href="javascript:void(0)" class="paid_all skip-check-dirty">Terkirim</a></li>
						<li><a href="" class="cancel cancel_all skip-check-dirty">Batal</a></li>
					</ul>
				</div>
				</c:if>
				<a href="javascript:void(0);" class="btn netral print skip-check-dirty"><i class="icon-grey icon-print"></i>CETAK</a>
				<c:if test="${canCreate}">
				<a href="${base_url}sales/deliveryorder/create-form" class="btn positive skip-check-dirty"  id="inline"><i class="icon-plus"></i>BUAT BARU</a>
				</c:if>
			</div>
			<h1>Surat Jalan</h1>
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
		<c:if test="${!empty searchParameter.destinationCustomerName}">
			<div class="pill-area">
				<p class="label">Tujuan</p>
				<p class="content-pill">${searchParameter.destinationCustomerName}</p>
			</div>
		</c:if>
		<!-- header info -->
		<!-- table -->
		
		<form class="deliveryorder_action" method="POST" >
		<table>
			<colgroup>
			<c:if test="${canUpdate}">
				<col style="width:40px"></col>
				</c:if>
				<col style="width:100px"></col>
				<col style="width:auto;min-width:500px;"></col>
<%-- 				<col style="width:70px"></col> --%>
				<col style="width:60px"></col>
<%-- 				<col style="width:auto;min-width:120px"></col> --%>
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
					<td>DO#</td>
					<td>PELANGGAN</td>
<!-- 					<td>TGL KIRIM</td> -->
					<td align="right">ITEM</td>
<!-- 					<td align="right">Courier</td> -->
					<td>TGL</td>
					<td>STATUS</td>
					<c:if test="${canUpdate}">
					<td>&nbsp;</td>
					</c:if>
				</tr>
			</thead>
			<tbody  id="tableValue"></tbody>
		</table>
		<div class="btn_loadmore_area" id="btnPAgeNext"></div>
		</form>
	</div>
	<div class="clear"></div>
	<div style="display: none">
		<div class="popup alert" id="popup" style="width: 500px !important">
			<div class="head_title">PILIH AKSI<a href="javascript:void(0);" class="fr close-popup"><i
					class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Surat jalan baru untuk fake data</p>
				<%-- <a href="${base_url}sales/deliveryorder/create-form" class="btn positive">BUAT
				BARU</a>  --%>
				<br />
				<a href="${base_url}sales/deliveryorder/create-fake-form" class="btn positive" id="create">BUAT FAKE DATA</a>
				<div class="clear"></div>
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