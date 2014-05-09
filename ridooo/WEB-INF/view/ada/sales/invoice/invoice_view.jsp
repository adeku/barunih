<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="base_url" value="/" />

<c:if test="${base_url=='//'}">
<c:set var="base_url" value="/" />
</c:if>
<div class="print-layout">
	<div class="print-header">
		<h1>NOTA JUAL</h1>
	</div>
	<table class="table-helper table">
			<colgroup>
				
				<col style="width:111px"></col>
				<col style="width:auto;"></col>
				<col style="width:70px"></col>
				<col style="width:60px"></col>
				<col style="width:140px"></col>
				<col style="width:90px"></col>
				<col style="width:70px"></col>
				
			</colgroup>
			<thead>
				<tr>
				
					<td>INV#</td>
					<td>PELANGGAN</td>
					<td>TGL KIRIM</td>
					<td align="right">ITEM</td>
					<td align="right">JUMLAH</td>
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
				<div class="btn-group"  id="tools" href="javascript:void(0);">
				<c:if test="${canUpdate}">
					<a class="btn disabled" href="javascript:void(0)" >Select item(s)</a>
					<div class="btn disabled">
						<i class="icon-dropdown icon-grey disabled"></i>
					</div>
				</c:if>

					<ul style="display: none;">
					<c:if test="${canUpdate}">
						<li class="tunda"><a href="javascript:void(0)" class="hold_all" data-status="2">Tunda</a></li>
						<li class="bayar"><a href="javascript:void(0)" class="paid_all" data-status="1">Tandai telah Dibayar</a></li>
						</c:if>
						<li class="print"><a href="javascript:print();" class="print">Print</a></li>
						<c:if test="${canUpdate}">
						<li class="batal"><a href="javascript:void(0);" class="cancel cancel_all" data-status="-1">Batalkan</a></li>
						</c:if>
					</ul>
				</div>
				<a href="javascript:void(0);" class="btn netral print"><i class="icon-grey icon-print"></i>CETAK</a>
				<c:if test="${canCreate}">
				<a href="" class="btn positive " id="inline"><i	class="icon-plus"></i>BUAT BARU</a>
				</c:if>
			</div>
			<h1>nota jual</h1>
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
		
		<!-- header info -->
		<!-- table -->
		<form class="invoice_action" method="POST" >
		<table>
			<colgroup>
			<c:if test="${canUpdate}">
				<col style="width:40px"></col>
				</c:if>
				<col style="width:111px"></col>
				<col style="width:auto;min-width:500px;"></col>
				<col style="width:70px"></col>
				<col style="width:60px"></col>
				<col style="width:140px"></col>
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
					<td>INV#</td>
					<td>PELANGGAN</td>
					<td>TGL KIRIM</td>
					<td align="right">ITEM</td>
					<td align="right">JUMLAH</td>
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
			<div class="head_title">PILIH SUMBER NOTA<a href="javascript:void(0);" class="fr close-popup"><i
					class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Sumber nota bisa berasal dari transaksi penjualan yang sudah
					ada atau membuat nota baru yang tidak berhubungan dengan transaksi
					penjualan.</p>

				<p>Silahkan pilih sumber nota?</p>
				<br /> 
					<a href="${base_url}sales/invoice/create-form"  class="btn positive" id="create" >BUAT BARU</a>
					<a href="${base_url}sales/invoice/create-fake-form"  class="fr">BUAT DATA DUMMY</a>
					<a href="${base_url}sales/sales-orders" class="fr"  style="margin-right:15px" >DARI SALES ORDER</a>
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