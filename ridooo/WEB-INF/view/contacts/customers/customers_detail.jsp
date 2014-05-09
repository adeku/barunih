<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/" />

<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
</c:if>

<!-- PRINT START -->
<div class="print-layout" >

	<div class="print-header">
		<div class="fl text-right">
			<h2>${pageTitle}</h2>
			<h1>${formIN.namaToko}</h1>
			<p>${formIN.alamat}, ${formIN.kota}, ${formIN.propinsi}</p>
		</div>

		<div class="fl">
			<span>TIPE</span>
			<h3 >${formIN.type}</h3>
			<span>SALES PERSON</span>
			<h3 >${dataDetail.salespersonName}</h3>
		</div>

		<div class="fl">
			<span>NPWP</span>
			<h3>${formIN.npwp}</h3>
			<span>FAX</span>
			<h3>${formIN.fax}</h3>
		</div>

		<div class="fr">
			<span>TELEPON</span>
			<h3>${formIN.telepon}</h3>
			<span>HP</span>
			<h3>${formIN.hp}</h3>
		</div>
		<div class="clear"></div>
	</div>

	<table class="invoice_table print-box table">
		<colgroup>
			<col style="width: 110px"></col>
			<col style="width: auto;"></col>
			<c:if test="${pageTitle.toLowerCase().equals('pelanggan')}">
				<col style="width: 80px"></col>
			</c:if>
			<col style="width: 80px"></col>
			<col style="width: 140px;"></col>
			<col style="width: 90px"></col>
			<col style="width: 70px"></col>
		</colgroup>
		<thead>
			<tr>
				<td>INV#</td>
				<td>PELANGGAN</td>
				<c:if test="${pageTitle.toLowerCase().equals('pelanggan')}">
					<td>TGL KIRIM</td>
				</c:if>
				<td align="right">ITEM</td>
				<td align="right">JUMLAH</td>
				<td>TGL</td>
				<td>STATUS</td>
			</tr>
		</thead>
		<tbody id="tableValuePrint" class="detail_print"></tbody>
	</table>
</div>
<!-- PRINT END -->

<div class="content notsw">

	<div class="main-content withright">
		<div class="header-info">
			<h1>${pageTitle}</h1>
		</div>
		<c:if test="${serviceName=='customers'&&isViewInvoice}">
			<table>
				<colgroup>
					<col style="width: 110px"></col>
					<col style="width: auto;"></col>
					<col style="width: 80px"></col>
					<col style="width: 80px"></col>
					<col style="width: 140px;"></col>
					<col style="width: 90px"></col>
					<col style="width: 70px"></col>
				</colgroup>
				<thead>
					<tr>
						<td>INV#</td>
						<td>PELANGGAN</td>
						<td>TGL KIRIM</td>
						<td align="right">ITEM</td>
						<td align="right">JUMLAH</td>
						<td>TGL</td>
						<td>STATUS</td>
					</tr>
				</thead>
				<tbody id="tableValue"></tbody>
			</table>
			<div class="btn_loadmore_area" id="btnPAgeNext"></div>
		</c:if>

		<c:if test="${serviceName=='customers'&&!isViewInvoice}">
			<div class="empty-state">
				<i class="empty-icon entry"></i>
				<h4>ANDA TIDAK MEMILIKI AKSES DI HALAMAN Nota Jual</h4>
			</div>
		</c:if>

		<c:if test="${serviceName=='suppliers'&&viewPurchaseBill}">
			<table>
				<colgroup>
					<col style="width: 100px"></col>
					<col style="width: auto; min-width: 500px;"></col>
					<col style="width: 60px"></col>
					<col style="width: auto; min-width: 120px"></col>
					<col style="width: 90px"></col>
					<col style="width: 70px"></col>
				</colgroup>
				<thead>
					<tr>
						<td>BILL#</td>
						<td>PELANGGAN</td>
						<td align="right">ITEM</td>
						<td align="right">JUMLAH</td>
						<td>TGL</td>
						<td>STATUS</td>
					</tr>
				</thead>
				<tbody id="tableValue"></tbody>
			</table>
			<div class="btn_loadmore_area" id="btnPAgeNext"></div>
		</c:if>

		<c:if test="${serviceName=='suppliers'&&!viewPurchaseBill}">
			<div class="empty-state">
				<i class="empty-icon entry"></i>
				<h4>ANDA TIDAK MEMILIKI AKSES DI HALAMAN Nota Pesan</h4>
			</div>
		</c:if>

		<c:if test="${serviceName=='couriers'&&isDeliveryOrder}">
			<table>
				<colgroup>
					<col style="width: 100px"></col>
					<col style="width: auto; min-width: 500px;"></col>
					<col style="width: 60px"></col>
					<col style="width: 90px"></col>
					<col style="width: 70px"></col>
				</colgroup>
				<thead>
					<tr>
						<td>DO#</td>
						<td>PELANGGAN</td>
						<td align="right">ITEM</td>
						<td>TGL</td>
						<td>STATUS</td>
					</tr>
				</thead>
				<tbody id="tableValue"></tbody>
			</table>
			<div class="btn_loadmore_area" id="btnPAgeNext"></div>
		</c:if>

		<c:if test="${serviceName=='couriers'&&!isDeliveryOrder}">
			<div class="empty-state">
				<i class="empty-icon entry"></i>
				<h4>ANDA TIDAK MEMILIKI AKSES DI HALAMAN Nota Pesan</h4>
			</div>
		</c:if>


		<div class="rightheader">
			<div class="btn-area">
				<c:if test="${canUpdate}">
					<a href="${baseURL}contacts/${serviceName}/${idPErson}/edit"
						class="btn netral"><i class="icon-file  icon-grey"></i>ralat</a>
				</c:if>
				<a href="javascript:void();" class="btn netral print"><i class="icon-print icon-grey"></i>PRINT</a>
				<a href="${baseURL}contacts/${serviceName}" class="btn netral close"><i
					class="icon-cancel icon-grey"></i>KEMBALI</a>
			</div>
			<form:form modelAttribute="formIN" method="POST">
				<h3>
					<c:if test="${action=='create'}">add</c:if>
					<c:if test="${action=='update'}">edit</c:if>
				</h3>
				<div class="form">
					<div class="field  field-data">
						<label>NAMA TOKO:</label>
						<p class="fr">${formIN.namaToko}</p>
						<%-- 						<form:input readonly="true" path="namaToko" cssClass="fr"/> --%>
					</div>
					<div class="field  field-data">
						<label>TIPE:</label>
						<p class="fr">${formIN.type}</p>
						<!-- 						<div style="display:none" class="field-content fr"> -->
						<%-- 							<form:select cssClass="select focus-tab" path="type" items="${typeOption}"/> --%>
						<!-- 						</div> -->
					</div>

					<div class="field  field-data">
						<label>KONTAK:</label>
						<p class="fr">${formIN.kontak}</p>
						<%-- 						<form:input readonly="true" path="kontak" class="fr"/> --%>
					</div>
					<div class="field field-data">
						<label class="">ALAMAT</label>
						<p class="fr">${formIN.alamat}</p>
						<%-- 						<form:textarea readonly="true" path="alamat" class=""/> --%>
					</div>
					<div class="field field-data">
						<label>KOTA:</label>
						<p class="fr">${formIN.kota}</p>
						<%-- 						<form:input  readonly="true" path="kota" class="fr medium"/> --%>
					</div>
					<div class="field field-data">
						<label>PROVINSI:</label>
						<p class="fr">${formIN.propinsi}</p>
						<%-- 						<form:input  readonly="true" path="propinsi" class="fr medium"/> --%>
					</div>
					<div class="field field-data">
						<label>TELEPON:</label>
						<p class="fr">${formIN.telepon}</p>
						<%-- 						<form:input  readonly="true" path="telepon" class="fr medium"/> --%>
					</div>
					<div class="field field-data">
						<label>HP:</label>
						<p class="fr">${formIN.hp}</p>
						<%-- 						<form:input readonly="true" path="hp" class="fr medium"/> --%>
					</div>
					<div class="field field-data">
						<label>FAX:</label>
						<p class="fr">${formIN.fax}</p>
						<%-- 						<form:input readonly="true" path="fax" class="fr medium"/> --%>
					</div>
					<div class="field field-data">
						<label>NPWP:</label>
						<p class="fr">${formIN.npwp}</p>
						<%-- 						<form:input readonly="true" path="npwp" class="fr long"/> --%>
					</div>
					<div class="field field-data">
						<label>SALESPERSON:</label>
						<p class="fr">${dataDetail.salespersonName}</p>
						<!-- 						<div style="display:none" class="field-content fr"> -->
						<%-- 							<form:select  cssClass="select focus-tab" path="salesperson" items="${salesOption}"/> --%>
						<!-- 						</div> -->
					</div>
				</div>
			</form:form>
		</div>


		<c:if test="${!empty action}">
			<div class="rightheader">
				<div class="btn-area">

					<h3>Detail</h3>
					<div class="field">
						<label>NAMA TOKO:</label> ${dataDetail.nama_toko}
					</div>
					<div class="field">
						<label>TIPE:</label> ${dataDetail.type}
					</div>
					<div class="field">
						<label>KONTAK:</label> ${dataDetail.kontak}
					</div>
					<div class="field">
						<label class="">ALAMAT</label>${dataDetail.alamat}
					</div>
					<div class="field">
						<label>KOTA:</label>${dataDetail.kota}
					</div>
					<div class="field">
						<label>PROPINSI:</label>${dataDetail.propinsi}
					</div>
					<div class="field">
						<label>TELEPON:</label>${dataDetail.telepon}
					</div>
					<div class="field">
						<label>HP:</label>${dataDetail.hp}
					</div>
					<div class="field">
						<label>FAX:</label>${dataDetail.fax}
					</div>
					<div class="field">
						<label>NPWP:</label>${dataDetail.npwp}
					</div>
					<div class="field">
						<label>SALESPERSON:</label>${dataDetail.salespersonName}
					</div>
				</div>
			</div>
		</c:if>

	</div>
</div>