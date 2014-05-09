<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/" />
<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/" />
</c:if>
<div class="print-layout">
	<div class="print-header">
		<h1>Katalog</h1>
	</div>
	<table class="table-helper table">
				<colgroup>
					<col style="width:80px">
					<col style="width:auto">
					<col style="width140px">
					<col style="width:150px">
					<col style="width:100px">
					<col style="width:100px">
					<col style="width:50px">
				</colgroup>
				<thead>
					<tr>
						<td>SKU</td>
						<td>ITEM</td>
						<td align="right">KATEGORI</td>
						<td align="right">HARGA RETAIL</td>
						<td align="right">Berat (kg)</td>
						<td align="right">STOK</td>
						<td align="center">status</td>
					</tr>
				</thead>
			<tbody  id="tableValuePrint"></tbody>
	</table>
</div>
<div class="content notsw">
	<div
		class="main-content<c:if test="${!empty actionview}"> withright</c:if>">
		<div class="header-info">
			<c:if test="${empty action}">
			<div class="header-attr">
			<c:if test="${canUpdate||canDelete}">
				<div class="btn-group" id="tools">
					<a class="btn netral disabled" href="">Select item(s)</a>
					<div class="btn disabled">
						<i class="icon-dropdown icon-grey disabled"></i>
					</div>
					<ul>
					<c:if test="${canUpdate}">
						<li><a href="" id="btnTahanSelected">Tahan</a></li>
					</c:if>
					<c:if test="${canDelete}">
						<li><a href="" class="cancel" id="btnHapusSelected">Hapus</a>
					</c:if>
						</li>
					</ul>
				</div>
				</c:if>
				<a href="javascript:void(0);" class="btn netral print skip-check-dirty"><i class="icon-grey icon-print"></i>CETAK</a>
				<c:if test="${canCreate}">
				<a href="${baseURL}inventory/catalogs/create" id="newData"
				class="btn positive"><i class="icon-plus icon-white"></i>Buat baru</a>
				</c:if>
			</div>
			</c:if>
			<h1>
				Katalog
				<c:if test="${action=='create'}">baru </c:if>
<%-- 				<c:if test="${action=='update'}">ralat </c:if> --%>
			</h1>
		</div>
		<c:if test="${empty action}">
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
		<c:if test="${!empty searchParameter.searchWarehouse}">
			<div class="pill-area">
				<p class="label">Gudang</p>
				<p class="content-pill">${searchParameter.WarehouseName}</p>
			</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchStock}">
			<div class="pill-area">
				<p class="label">Stok on Hand</p>
				<p class="content-pill">${searchParameter.searchStock}</p>
			</div>
		</c:if>
		
		<c:if test="${!empty searchParameter.searchBrand}">
			<div class="pill-area">
				<p class="label">Merk</p>
				<p class="content-pill">${searchParameter.searchBrandName}</p>
			</div>
		</c:if>
		
		<c:if test="${!empty searchParameter.searchCategory}">
			<div class="pill-area">
				<p class="label">Kategori</p>
				<p class="content-pill">${searchParameter.searchCategoryName}</p>
			</div>
		</c:if>
		</c:if>
		
		<c:if test="${empty action}">
		<form id="dataList" method="post">
			<table>
				<colgroup>
				<c:if test="${canUpdate||canDelete}">
					<col style="width:10px"></col>
					<col style="width:80px"></col>
				</c:if>
				<c:if test="${!(canUpdate||canDelete)}">
					<col style="width:90px"></col>
				</c:if>
				<col style="width:auto"></col>
				<col style="width140px"></col>
				<col style="width:150px"></col>
				<col style="width:100px"></col>
				<col style="width:100px"></col>
				<col style="width:150px"></col>
				<c:if test="${canUpdate||canDelete}">
					<col style="width:50px"></col>
					<col style="width:50px"></col>
				</c:if>
				<c:if test="${!(canUpdate||canDelete)}">
					<col style="width:100px"></col>
				</c:if>
				</colgroup>
				<thead>
					<tr>
					<c:if test="${canUpdate||canDelete}">
						<td align="center"><input type="checkbox" class="check_all"></td>
					</c:if>
						<td>SKU</td>
						<td>ITEM</td>
						<td align="right">KATEGORI</td>
						<td align="right">HARGA RETAIL</td>
						<td align="right">Berat (kg)</td>
						<td align="right">STOK Retail</td>
						<td align="right">STOK Wholesale</td>
						<td>status</td>
					<c:if test="${canUpdate||canDelete}">
						<td>&nbsp;</td>
					</c:if>
					</tr>
				</thead>
				<tbody id="tableValue">
				</tbody>
			</table>
			<div class="btn_loadmore_area" id="btnPAgeNext"></div>
		</form>
		<c:if test="${actionview=='detail'}">
		<div class="rightheader">
			<div class="btn-area">
				<h3>details</h3>
				<div class="field">
					<label class="">Nama Produk:</label> ${dateDetail.name}
				</div>
				<div class="field">
					<label class="">Merk:</label> ${dateDetail.brandName}
				</div>
				<div class="field">
					<label class="">Sku:</label> ${dateDetail.sku}
				</div>
				<div class="field">
					<label class="">Kategori:</label>
					<c:if test="${dateDetail.taxonomyName==null}">-</c:if>
					<c:if test="${dateDetail.taxonomyName!=null}">${dateDetail.taxonomyName}</c:if>
				</div>
				<div class="field">
					<label class="">Batas Bawah:</label>${dateDetail.qty_minimum}
				</div>
				<div class="field">
					<label class="">Berat:</label>${dateDetail.weight} KG
				</div>
				<div class="field">
					<label class="">Deskripsi:</label>${dateDetail.content}
				</div>
			</div>
		</div>
		</c:if>
		</c:if>
		<c:if test="${action=='create'||action=='update'}">
		<div class="content-form">
			<form:form modelAttribute="formIN" enctype="multipart/form-data" name="formIN" method="POST">
				<div class="field">
					<label>Nama Produk:</label>
					<form:input path="title" cssClass="focus-tab long validation" data-validation="required"
						target-tab="#brand_id" target-type="select" />
					</div>
					<div class="field" id="brandAdd"
						<c:if test="${!formIN.newBrand}"> style="display:none"</c:if>>
						<label>Merk:</label>
						<form:input path="brand"/>
							<a href="" class="btn positive" id="ShowselectBrand">SELECT
							BRAND</a>
						</div>
						<div class="field" id="brandSElect"
							<c:if test="${formIN.newBrand}"> style="display:none"</c:if>>
							<label>Merk:${dataEditting.brandId}</label>
							<div class="field-content fl">
								<form:select class="select focus-tab validation" data-validation="required" path="brand_id"
									items="${brandOption}" target-tab="#sku" />
								</div>
								&nbsp;&nbsp;<a href="" id="ShowAddBrand" class="btn netral">MERK BARU</a>
							</div>
							<div class="field">
								<label>SKU:</label>
								<form:input path="sku" cssClass="focus-tab validation" data-validation="required"
									target-tab="#kategori_id" target-type="select" />
								</div>
								<div class="field" id="addKAtegori"
									<c:if test="${!formIN.newKategori}"> style="display:none"</c:if>>
									<label>KATEGORI:</label>
									<form:input path="kategori" />
										<a href="" class="btn positive" id="ShowselectKAtegori"></i>Select
										Kategori</a>
									</div>
									<div class="field" id="SelectKAtegori"
										<c:if test="${formIN.newKategori}"> style="display:none"</c:if>>
										<label>KATEGORI:</label>
										<div class="field-content fl">
											<form:select class="select focus-tab validation" data-validation="required" target-tab="#qty_minimum"
												path="kategori_id" items="${kategoriOption}" />
											</div>
											&nbsp; &nbsp;<a href="" class="btn netral" id="ShowAddKategori">NEW
											KATEGORI</a>
										</div>
										<div class="field">
											<label>BATAS BAWAH:</label>
											<form:input path="qty_minimum" cssClass="number-filter focus-tab validation" data-validation="required" target-tab="#weight"/>
											</div>
											<div class="field">
												<label>BERAT:</label>
												<form:input path="weight" cssClass="number-filter focus-tab validation" data-validation="required,number" target-tab="#catalogPhoto" target-type="file"/>
													KG
												</div>
												<div class="field">
													<label>Photo:</label>
													<form:input type="file" path="catalogPhoto" cssClass="focus-tab"
														target-tab="#content" />
													</div>
												<div class="field">
													<label>DESKRIPSI:</label>
													<form:textarea path="content" class="long" cssClass="focus-tab validation" data-validation="required"
														target-tab="#btnSimpan" target-type="button" />
													</div>
													<div class="field">
														<label>&nbsp;</label> <button class="btn positive focus-tab"
														id="btnSimpan" target-tab="#cancel" target-type="button"><i class="icon-file icon-white"></i>SIMPAN</button>
														<a href="${baseURL}inventory/catalogs" id="cancel"
														class="btn netral focus-tab" target-tab="#title"><i class="icon-cancel icon-grey"></i>KEMBALI</a>
													</div>
													<form:hidden path="newBrand" />
														<form:hidden path="newKategori" />
														</form:form>
													</div>
													</c:if>
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
															<br />
															<a href="" class="btn positive btNotHapus">OK</a>
														</div>
													</div>
												</div>
												
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
									<div style="display: none">
										<div class="popup alert" id="popupTAhan" style="width: 400px !important">
											<div class="head_title">
												INFORMASI<a href="javascript:void(0)" class="fr close-popup"><i
												class="icon-blue icon-close"></i></a>
												<div class="clear"></div>
											</div>
											<div class="popup-holder">
												<p>PILIH DATA YANG AKAN DI TAHAN</p>
												<br />
												<a href="" class="btn positive btNotHapus">OK</a>
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
												<p>Apakah katalog ini akan dihapus?</p>
												<br /> <a href="" class="btn positive" id="btYesHapus">Hapus</a> <a
												href="" class="btn netral btNotHapus">Batal</a>
											</div>
										</div>
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
												<p>Apakah katalog ini akan dihapus?</p>
												<br /> <a href="" class="btn positive" id="btYesHapus1" delID="">Hapus</a>
												<a href="" class="btn netral btNotHapus1">Batal</a>
											</div>
										</div>
									</div>
								</div><form id="frList" method="post"><input type="hidden" name="id"><input type="hidden" name="status"></form>