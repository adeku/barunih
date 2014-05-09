<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/"/><c:if test="${baseURL=='//'}"><c:set var="baseURL" value="/"/></c:if>
<div class="print-layout">
	<div class="print-header">
		<h1>${pageTitle}</h1>
	</div>
	<table class="table-helper table">
				<colgroup>
					<col style="width:100px"></col>
					<col style="width:auto;min-width:620px;"></col>
					<col style="width:100px"></col>
					<col style="width:60px"></col>
					<col style="width:90px"></col>
					<col style="width:70px"></col>
				</colgroup>
				<thead>
					<tr>
						<td>nama/npwp</td>
						<td>kontak</td>
						<td>alamat</td>
						<td>telepon</td>
						<td>hp</td>
						<td>fax</td>
						<td>salesperson</td>
					</tr>
				</thead>
			<tbody  id="tableValuePrint"></tbody>
		</table>
</div>
<div class="content notsw">
	<div class="main-content<c:if test="${!empty action}"> withright</c:if>">
		<div class="header-info">
			<c:if test="${empty action}">
			<div class="header-attr">
				<a href="javascript:void(0);" class="btn netral print skip-check-dirty"><i class="icon-grey icon-print"></i>CETAK</a>
				<c:if test="${canCreate}">
					<a href="${baseURL}contacts/${serviceName}/create" id="newData" class="btn positive"><i class="icon-plus"></i>Buat baru</a>
				</c:if>
			</div>
			</c:if>
			<h1><c:if test="${action=='create'}">Add </c:if>${pageTitle}</h1>
			
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
			<col style="width:120px"></col>
			<col style="width:150px"></col>
			<col style="width:auto"></col>
			<col style="width:120px"></col>
			<col style="width:120px"></col>
			<col style="width:120px"></col>
			<col style="width:130px"></col>
			<c:if test="${canUpdate||canDelete}">
				<col style="width:10px"></col>
				<col style="width:10px"></col>
			</c:if>
			<c:if test="${!(canUpdate||canDelete)}">
				<col style="width:20px"></col>
			</c:if>
			</colgroup>
			<thead>
				<tr>
					<td>nama/npwp</td>
					<td>kontak</td>
					<td>alamat</td>
					<td>telepon</td>
					<td>hp</td>
					<td>fax</td>
					<td>salesperson</td>
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
											</div>
											<div style="display: none">
												<div class="popup alert" id="askDelete1" style="width: 400px !important">
													<div class="head_title">INFORMASI<a href="javascript:void(0)" class="fr close-popup"><i
														class="icon-blue icon-close"></i></a>
														<div class="clear"></div>
													</div>
													<div class="popup-holder">
														<c:choose>
															<c:when test="${pageTitle == 'Daftar Pelanggan'}">
																<c:set var="title" value="pelanggan"></c:set>
															</c:when>
															<c:when test="${pageTitle == 'Daftar Vendor'}">
																<c:set var="title" value="vendor"></c:set>
															</c:when>
															<c:when test="${pageTitle == 'Daftar Ekspedisi'}">
																<c:set var="title" value="ekspedisi"></c:set>
															</c:when>
														</c:choose>
														<p>Apakah Anda yakin ingin menghapus ${title} ini?</p>
														<br/> <button class="btn positive" id="btYesHapus1" delID="">Hapus</button> <a
														href="" class="btn netral btNotHapus1">Batal</a>
													</div>
												</div>
											</div>
<form id="frList" method="post"><input type="hidden" name="status"></form>