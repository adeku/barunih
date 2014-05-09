<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/" />
<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
</c:if>

<div class="print-layout">
	<div class="print-header">
		<h1>Kategori</h1>
	</div>
	<table class="table-helper table">
		<colgroup>
			<col style="width: 250px"></col>
			<col style="width: auto"></col>
			<col style="width: 60px;"></col>
			<col style="width: 70px"></col>
			<col style="width: 50px"></col>
			<col style="width: 50px"></col>
		</colgroup>
		<thead>
			<tr>
				<td>KATEGORI</td>
				<td>DESKRIPSI</td>
				<td align="right">ITEM</td>
				<td align="right">Berat (kg)</td>
				<td align="right">STOK</td>
				<td>status</td>
			</tr>
		</thead>
		<tbody id="tableValuePrint"></tbody>
		
		
	</table>
</div>
<div class="content notsw">
	<div
		class="main-content<c:if test="${!empty actionview}"> withright</c:if>">
		<!-- heading tittle -->
		<div class="header-info">
			<c:if test="${empty action&&empty actionview}">
				<div class="header-attr">
					<a href="javascript:void(0);"
						class="btn netral print skip-check-dirty"><i
						class="icon-grey icon-print"></i>CETAK</a>
					<c:if test="${canCreate}">
						<a href="${baseURL}inventory/categories/create" id="newData"
							class="btn positive"><i class="icon-plus icon-white"></i>Buat baru</a>
					</c:if>
				</div>
			</c:if>
			<h1>
				KATEGORI
				<c:if test="${action=='create'}">baru </c:if>
				<c:if test="${action=='update'}">ralat </c:if>
			</h1>
		</div>
		<c:if test="${empty actionview && actionview=='detail'}">
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
		</c:if>
		<!-- header info -->

		<!-- table -->
		<c:if test="${empty action}">
			<c:if test="${actionview=='detail'}">

				<table>
					<colgroup>
						<col style="width: 90px"></col>
						<col style="width: auto"></col>
						<col style=""></col>
						<col style="width: 100px"></col>
						<col style="width: 100px"></col>
						<col style="width: 100px"></col>
					</colgroup>
					<thead>
						<tr>
							<td>SKU</td>
							<td>ITEM</td>
							<td align="right">KATEGORI</td>
							<td align="right">Berat (kg)</td>
							<td align="right">STOK</td>
							<td>status</td>
						</tr>
					</thead>
					<tbody id="tableValue">
					</tbody>
				</table>

			</c:if>
			<c:if test="${actionview!='detail'}">
				<table>
					<colgroup>
						<col style="width: 250px"></col>
						<col style="width: auto"></col>
						<col style="width: 60px;"></col>
						<c:if test="${canDelete||canUpdate}">
							<col style="width: 60px;"></col>
						</c:if>
					</colgroup>
					<thead>
						<tr>
							<td>KATEGORI#</td>
							<td>DESKRIPSI</td>
							<td align="right">ITEM</td>
							<c:if test="${canDelete||canUpdate}">
								<td>&nbsp;</td>
							</c:if>
						</tr>
					</thead>
					<tbody id="tableValue">

					</tbody>
				</table>
			</c:if>
			<div class="btn_loadmore_area" id="btnPAgeNext"></div>
			<c:if test="${actionview=='detail'}">
				<div class="rightheader">
					<div class="btn-area">
						<c:if test="${canUpdate}">
							<a href="${baseURL}inventory/categories/${id}/edit"
								class="btn netral"><i class="icon-pencil icon-grey"></i>ralat</a>
						</c:if>
						<a href=javascript:void()" class="btn netral print"><i class="icon-print icon-grey"></i>PRINT</a>
						<a href="${baseURL}inventory/categories" class="btn netral close"><i
							class="icon-cancel icon-grey"></i>KEMBALI</a>
					</div>
					<div class="form">
						<div class="field field-data">
							<label>Nama:</label>
							<p class="fr">${dataDetail.name}</p>
						</div>
						<div class="field field-data">
							<label>Deskripsi:</label>
							<p class="fr">${dataDetail.description}</p>
						</div>
						<div class="field field-data">
							<label>Induk Kategori:</label>
							<p class="fr">
								<c:if test="${dataDetail.subcategory==null}">-</c:if>
								<c:if test="${dataDetail.subcategory!=null}">${dataDetail.subcategoryName}</c:if>
							</p>
						</div>
					</div>


				</div>
			</c:if>
		</c:if>

		<c:if test="${action=='create'||action=='update'}">
			<div class="content-form">
				<form:form modelAttribute="formIN" method="POST">
					<div class="field">
						<label>Nama:</label>
						<form:input path="name" cssClass="focus-tab long validation" data-validation="required"
							target-tab="#description" />
					</div>
					<div class="field">
						<label>Deskripsi:</label>
						<form:textarea path="description" cssClass="focus-tab validation" data-validation="required"
							target-tab="#subCatregory" target-type="select" />
					</div>
					<div class="field">
						<label>Induk Kategori:</label>
						<div class="field-content fl">
							<form:select class="select focus-tab" data-validation="required" path="subCatregory"
								items="${subCAtegoryOption}" target-tab="#btnSimpan"
								target-type="button" />
						</div>
					</div>
					<br />
					<br />
					<div class="field">
						<br /> <label>&nbsp;</label>
						<button class="btn positive focus-tab" id="btnSimpan"
							target-tab="#cancel" target-type="button">
							<i class="icon-file icon-white"></i>SIMPAN
						</button>
						<a href="${baseURL}inventory/categories" id="cancel"
							class="btn netral focus-tab" target-tab="#name"><i
							class="icon-cancel icon-grey"></i>KEMBALI</a>
					</div>
				</form:form>
			</div>
		</c:if>

	</div>
</div>
<form id="frList" method="post"></form>
<div style="display: none">
	<div class="popup alert" id="popup" style="width: 400px !important">
		<div class="head_title">
			INFORMASI<a href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		<div class="popup-holder">
			<p>Apakah kategori ini akan dihapus?</p>
			<br /> <a href="" class="btn positive" id="btYesHapus1" delID="">Hapus</a>
			<a href="" class="btn netral btNotHapus">Batal</a>
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
			<p>Apakah kategori ini akan dihapus?</p>
			<br /> <a href="" class="btn positive" id="btYesHapus1" delID="">Hapus</a>
			<a href="" class="btn netral btNotHapus">Batal</a>
		</div>
	</div>
</div>

<c:if test="${actionview=='detail'}">
	<div style="display: none">
													<div class="popup alert" id="datalogDetailHere"
														style="width: 820px !important">
														<div class="head_title"><span id="dDATaProduk">Prohex isi</span><a href="javascript:void(0)" class="fr close-popup"><i
															class="icon-blue icon-close"></i></a>
															<div class="clear"></div>
														</div>
														<div class="popup-holder">
														<div id="detailImage" class="part1 fl"></div>
															<div class="part2 fl"><div class="field">
																<label>Merk  </label>
																<label id="pBrandName" class="fr">brand name</label>
															</div>
															<div class="field">
																<label>SKU</label>
																<label id="pSKU" class="fr">SKU1</label>
															</div>
															<div class="field">
																<label>Kategori </label>
																<label id="pcategoryID" class="fr">category 1</label>
															</div>
															<div class="field">
																<label>Description</label>
																<br/>
																<label id="pdescriptionID">Description 122</label>
															</div>
														</div>
														<div class="part3 fl"><div class="field">
															<label>MSRP</label>
															<label id="pmsRP" class="fr">Description 122</label>
														</div>
														<div id="pPriceList"> </div>
													</div>
													<div class="part4 fl"><div class="field">
														<label>On-Hand</label>
														<label id="ponhandID" class="fr">category 1</label>
													</div>
													<div class="field">
														<label>Siap Pesan</label>
														<label id="pSiapPesan" class="fr">category 1</label>
													</div>
													<div id="pWarehouseList"> </div>
												</div>
												<div class="clear"></div>
											</div>
										</div>
									</div>
</c:if>
