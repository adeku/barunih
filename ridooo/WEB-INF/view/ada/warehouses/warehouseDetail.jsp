<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />

<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
</c:if>
<div class="print-layout">
	<div class="print-header">

		<div class="fl text-right">
			<h2>Gudang Detail</h2>
			<h1>ID-${id}</h1>
			<div class="date">${companyDetail.created_date}</div>
		</div>

		<div class="fl">
			<span>GUDANG</span>
			<h3>${companyDetail.name}</h3>
			<p>${companyDetail.street}</p>
		</div>
		<div class="fr">
			<div class="fieldprint">
				<label>Telepon:</label>
				<p class="fr">${companyDetail.telephone}</p>
			</div>
			<div class="fieldprint">
				<label>Type gudang:</label>
				<p class="fr">${companyDetail.type}</p>
			</div>
			<div class="fieldprint">
				<label>Kota:</label>
				<p class="fr">${companyDetail.city}</p>
			</div>
			<div class="fieldprint">
				<label>Provinsi:</label>
				<p class="fr">${companyDetail.province}</p>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<table class="table-helper table">
		<c:if test="${pageHere.equalsIgnoreCase(\"detail\")}">
			<colgroup>
				<col style="width: 120px"></col>
				<col style="width: 90px"></col>
				<col style="width: auto"></col>
				<col style="width: 150px"></col>
				<col style="width: 100px"></col>
				<col style="width: 125px"></col>
				<col style="width: 100px"></col>
			</colgroup>
			<thead>
				<tr>
					<td>Rak</td>
					<td>SKU</td>
					<td>ITEM</td>
					<td align="right">Berat (kg)</td>
					<td >status</td>
					<td class="text-right">Stok</td>
				</tr>
			</thead>
			</c:if>
			
			<c:if test="${pageHere.equalsIgnoreCase(\"kodebongkar\")}">
			<colgroup>
				<col style="width: 120px"></col>
				<col style="width: 120px"></col>
				<col style="width: 120px"></col>
				<col style="width: 90px"></col>
				<col style="width: auto"></col>
				<col style="width: 150px"></col>
				<col style="width: 100px"></col>
				<col style="width: 100px"></col>
			</colgroup>
			<thead>
				<tr>
					<td>Kode barang</td>
					<td>Dokumen</td>
					<td>Rak</td>
					<td>SKU</td>
					<td>ITEM</td>
					<td align="right">Berat (kg)</td>
					<td class="text-right">Stok</td>
				</tr>
			</thead>
			</c:if>
		<tbody id="tableValuePrint"></tbody>
	</table>
	<div class="clear"></div>
</div>
<div class="content notsw">
	<div class="main-content withright">
		<div class="header-info">
			<div class="header-attr">
			<c:if test="${pageHere.equalsIgnoreCase(\"detail\")}">
			<a href="${baseURL}warehouses/${id}/kode-detail" class="btn netral" id="newData">Kode Bongkar</a>
			</c:if>
			<c:if test="${pageHere.equalsIgnoreCase(\"kodebongkar\")}">
			<a href="${baseURL}warehouses/${id}/detail" class="btn netral" id="newData">Kembali</a>
			</c:if>
			</div>
			<h1>Gudang Detail 
			<c:if test="${pageHere.equalsIgnoreCase(\"kodebongkar\")}">
			Beserta kode bongkar
			</c:if>
			</h1>
		</div>

		<table>
		<c:if test="${pageHere.equalsIgnoreCase(\"detail\")}">
			<colgroup>
				<col style="width: 120px"></col>
				<col style="width: 90px"></col>
				<col style="width: auto"></col>
				<col style="width: 150px"></col>
				<col style="width: 100px"></col>
				<col style="width: 125px"></col>
				<col style="width: 100px"></col>
			</colgroup>
			<thead>
				<tr>
					<td>Rak</td>
					<td>SKU</td>
					<td>ITEM</td>
					<td align="right">Berat (kg)</td>
					<td >status</td>
					<td class="text-right">Stok</td>
				</tr>
			</thead>
			</c:if>
			
			<c:if test="${pageHere.equalsIgnoreCase(\"kodebongkar\")}">
			<colgroup>
				<col style="width: 120px"></col>
				<col style="width: 120px"></col>
				<col style="width: 120px"></col>
				<col style="width: 90px"></col>
				<col style="width: auto"></col>
				<col style="width: 150px"></col>
				<col style="width: 100px"></col>
				<col style="width: 100px"></col>
			</colgroup>
			<thead>
				<tr>
					<td>Kode barang</td>
					<td>Dokumen</td>
					<td>Rak</td>
					<td>SKU</td>
					<td>ITEM</td>
					<td align="right">Berat (kg)</td>
					<td class="text-right">Stok</td>
				</tr>
			</thead>
			</c:if>
			<tbody id="tableValue">
			</tbody>
		</table>

		<div class="btn_loadmore_area" id="btnPAgeNext"></div>

		<div class="rightheader">
			<div class="btn-area">
				<a href="${baseURL}warehouses" class="btn netral"><i class="icon-grey icon-file"></i>KEMBALI</a>
				<a href="javascript:void(0);" class="btn netral print"><i class="icon-grey icon-print"></i>CETAK</a>
			</div>
			<div class="form">
				<div class="field">
					<label>Gudang:</label>
					<p class="fr">${companyDetail.name}</p>
				</div>
				<div class="field">
					<label>Alamat:</label>
					<p class="fr">${companyDetail.street}</p>
				</div>
				<div class="field">
					<label>Telepon:</label>
					<p class="fr">${companyDetail.telephone}</p>
				</div>
				<div class="field">
					<label>Kota:</label>
					<p class="fr">${companyDetail.city}</p>
				</div>
				<div class="field">
					<label>Provinsi:</label>
					<p class="fr">${companyDetail.province}</p>
				</div>
				<div class="field">
					<label>Type gudang:</label>
					<p class="fr">${companyDetail.type}</p>
				</div>
			</div>
		</div>
	</div>
</div>