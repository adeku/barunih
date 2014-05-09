<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/" />

<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
</c:if>
<div class="print-layout">
	<div class="print-header">
		<h1>surat pindah</h1>
	</div>
	<table class="table-helper table">
		<colgroup>
			<col style="width: auto">
			<col style="width: auto">
			<col style="width: auto">
			<col style="width: auto">
			<col style="width: auto">
			<col style="width: auto">
			<col style="width: 0px">
		</colgroup>
		<thead>
			<tr>
				<td>to#</td>
				<td>asal</td>
				<td>tujuan</td>
				<td>item</td>
				<td>tgl</td>
				<td style="padding-left:20px !important">status</td>
				<td ></td>
			</tr>
		</thead>
		<tbody id="tableValuePrint"></tbody>
	</table>
</div>
<div class="content notsw">
	<div class="main-content <c:if test="${!empty action}">withright</c:if>">
		<!-- heading tittle -->
		<div class="header-info">
			<c:if test="${empty action&&(canCreate||canUpdate||canDelete)}">
				<div class="header-attr">
					<c:if test="${canUpdate||canDelete}">
						<div class="btn-group" id="tools">
							<a class="btn netral disabled" href="">Select item(s)</a>
							<div class="btn disabled">
								<i class="icon-dropdown icon-grey disabled"></i>
							</div>
							<ul>
								<c:if test="${canUpdate}">
									<li><a href="" id="btnSelesaiSelected">Selesai</a></li>
								</c:if>
								<c:if test="${canDelete}">
									<li><a href="" class="cancel" id="btnHapusSelected">Batal</a>
								</c:if>
								</li>
							</ul>
						</div>
						<a href="javascript:void(0);"
							class="btn netral print skip-check-dirty"><i
							class="icon-grey icon-print"></i>CETAK</a>
					</c:if>
					<c:if test="${canCreate}">
						<a href="${baseURL}inventory/transfer-order/create" id="newData"
							class="btn positive"><i class="icon-plus"></i>Buat baru</a>
					</c:if>
					
				</div>
			</c:if>
			<c:if test="${!empty action}">
				<div class="header-attr">
					<div class="post-status">Published</div>
				</div>
			</c:if>
			<h1>surat pindah</h1>
		</div>
		<div class="pill-area">
			<p class="label">Total</p>
			<p class="content-pill">${count}</p>
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
				<p class="content-pill"><c:if test="${searchParameter.searchStatus==0}">Batal</c:if><c:if test="${searchParameter.searchStatus==1}">Aktif</c:if><c:if test="${searchParameter.searchStatus==2}">Selesai</c:if></p>
			</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchSku}">
			<div class="pill-area">
				<p class="label">SKU</p>
				<p class="content-pill">${searchParameter.searchSku}</p>
			</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchItemName}">
			<div class="pill-area">
				<p class="label">Barang</p>
				<p class="content-pill">${searchParameter.searchItemName}</p>
			</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchSource}">
			<div class="pill-area">
				<p class="label">Asal</p>
				<p class="content-pill">${searchParameter.sourceWarehouseName}</p>
			</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchDestination}">
			<div class="pill-area">
				<p class="label">Tujuan</p>
				<p class="content-pill">${searchParameter.destinationWarehouseName}</p>
			</div>
		</c:if>
		<!-- header info -->

		<!-- table -->
		<c:if test="${empty action}">
			<form id="dataList">
				<table>
					<colgroup>
						<c:if test="${canDelete||canUpdate}">
							<col style="width: 5%"></col>
							<col style="width: 12%"></col>
						</c:if>
						<c:if test="${!canDelete||!canUpdate}">
							<col style="width: 18%"></col>
						</c:if>
						<col style="width: 25%"></col>
						<col style="width: 25%"></col>
						<col style="width: 12%"></col>
						<col style="width: 10%"></col>
						<c:if test="${canDelete||canUpdate}">
							<col style="width: 8%"></col>
							<col style="width: 5%"></col>
						</c:if>
						<c:if test="${!canDelete||!canUpdate}">
							<col style="width: 13%"></col>
						</c:if>
					</colgroup>
					<thead>
						<tr>
							<c:if test="${canDelete||canUpdate}">
								<td align="center"><input type="checkbox" class="check_all"></td>
							</c:if>
							<td>to#</td>
							<td>asal</td>
							<td>tujuan</td>
							<td>item</td>
							<td>tgl</td>
							<td align="left">status</td>
							<c:if test="${canDelete||canUpdate}">
								<td>&nbsp</td>
							</c:if>
						</tr>
					</thead>
					<tbody id="tableValue"></tbody>
				</table>
				<div class="btn_loadmore_area" id="btnPAgeNext"></div>
			</form>
		</c:if>
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
				<p>Apakah surat pindah ini akan dibatalkan?</p>
				<br /> <a href="" class="btn positive" id="btYesHapus1" delID="">Ya</a>
				<a href="" class="btn netral btNotHapus1">Tidak</a>
			</div>
		</div>
	</div>

	<div style="display: none">
		<div class="popup alert" id="popupSelesai"
			style="width: 400px !important">
			<div class="head_title">
				INFORMASI<a href="javascript:void(0)" class="fr close-popup"><i
					class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>PILIH DATA YANG AKAN DI SELESAIKAN</p>
				<br /> <a href="" class="btn positive btNotHapus">OK</a>
			</div>
		</div>
	</div>

</div>
<div style="display: none">
	<div class="popup alert" id="infoCannotDelete"
		style="width: 400px !important">
		<div class="head_title">
			INFORMASI<a href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		<div class="popup-holder">
			<p>PILIH DATA YANG AKAN DI HAPUS</p>
			<br /> <a href="" class="btn positive btNotHapus">OK</a>
		</div>
	</div>
</div>
<div style="display: none">
	<div class="popup alert" id="popup" style="width: 400px !important">
		<div class="head_title">
			INFORMASI<a href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		<div class="popup-holder">
			<p>Apakah surat pindah ini akan dibatalkan?</p>
			<br /> <a href="" class="btn positive" id="btYesHapus">Ya</a> <a
				href="" class="btn netral btNotHapus">Tidak</a>
		</div>
	</div>
</div>
