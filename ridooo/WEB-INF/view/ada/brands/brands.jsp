<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/" />

<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
</c:if>

<div class="print-layout">
	<div class="print-header">
		<h1>Merk</h1>
	</div>
	<table class="table-helper table">
		<colgroup>
			<col style="width: auto"></col>
			<col style="width: auto"></col>
			<col style="width: 90px"></col>
			<c:if test="${actionview == 'detail'}">
				<col style="width: 120px"></col>
				<col style="width: 90px"></col>
				<col style="width: 90px"></col>
			</c:if>
		</colgroup>
		<thead>
			<tr>
				<c:if test="${actionview !='detail'}">
					<td>merk</td>
					<td>deskripsi</td>
					<td>tgl perubahan</td>
				</c:if>

				<c:if test="${actionview == 'detail'}">
					<td>sku</td>
					<td>item</td>
					<td align="right" class="text-right">kategori</td>
					<td class="text-right">berat</td>
					<td class="text-right">stok</td>
					<td>status</td>
				</c:if>
			</tr>
		</thead>
		<tbody id="tableValuePrint"></tbody>
	</table>
</div>
<div class="content notsw">
	<div
		class="main-content<c:if test="${!empty actionview}"> withright</c:if>">
		<div class="header-info">
			<c:if test="${empty action && empty actionview}">
				<div class="header-attr">
					<c:if test="${canDelete}">
						<div class="btn-group" id="tools">
							<a class="btn netral disabled" href="">Select item(s)</a>
							<div class="btn disabled">
								<i class="icon-dropdown icon-grey disabled"></i>
							</div>
							<ul style="display: none">
								<li><a href="" class="cancel" id="btnHapusSelected">Hapus</a>
								</li>
							</ul>
						</div>
					</c:if>
					<a href="javascript:void(0);"
						class="btn netral print skip-check-dirty"><i
						class="icon-grey icon-print"></i>CETAK</a>
					<c:if test="${canCreate}">
						<a href="${baseURL}inventory/brands/create" class="btn positive"
							id="newData"><i class="icon-plus"></i>Buat baru</a>
					</c:if>
				</div>
			</c:if>
			<h1>
				merk
				<c:if test="${action=='create'}">baru </c:if>
			</h1>
		</div>
		<c:if test="${actionview!='detail' && empty action}">
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
		<c:if test="${!empty searchParameter.searchStartDate && !empty searchParameter.searchEndDate}">
			<div class="pill-area">
				<p class="label">Tgl</p>
				<p class="content-pill">${searchParameter.searchStartDate} - ${searchParameter.searchEndDate}</p>
			</div>
		</c:if>
		</c:if>
		
		<c:if test="${empty action}">
			<form id="dataList" method="post">
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
							<c:if test="${canDelete}">
								<col style="width: 10px"></col>
								<col style="width: 300px"></col>
							</c:if>
							<c:if test="${!canDelete}">
								<col style="width: 310px"></col>
							</c:if>
							<col style="width: auto"></col>
							<c:if test="${canDelete||canUpdate}">
								<col style="width: 150px"></col>
							</c:if>
						</colgroup>
						<thead>
							<tr>
								<c:if test="${canDelete}">
									<td align="center"><input type="checkbox"
										class="check_all"></td>
								</c:if>
								<td>merk#</td>
								<td>deskripsi</td>
								<td>tgl perubahan</td>
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
			</form>
			<c:if test="${actionview=='detail'}">
				<div class="rightheader">

					<div class="btn-area">
						<c:if test="${canUpdate}">
							<a href="${baseURL}inventory/brands/${id}/edit"
								class="btn netral"><i class="icon-pencil icon-grey"></i>ralat</a>
						</c:if>
						<a href="javascript:void()" class="btn netral print"><i
							class="icon-print icon-grey"></i>CETAK</a> <a
							href="${baseURL}inventory/brands" class="btn netral close"><i
							class="icon-cancel icon-grey"></i>KEMBALI</a>
					</div>
					<div class="form">
						<div class="field field-data">
							<label>Nama Merk:</label>
							<p class="fr">${dateDetail.name}</p>
						</div>
						<div class="field field-data">
							<label>Deskripsi:</label>
							<p class="fr">${dateDetail.content}</p>
						</div>
					</div>
				</div>
			</c:if>

		</c:if>
		<c:if test="${action=='create'||action=='update'}">
			<div class="content-form">
				<form:form modelAttribute="formIN" method="POST">
					<div class="field">
						<label>Nama Merk:</label>
						<form:input path="title" class="focus-tab long validation"
							data-validation="required" target-tab="#content" />
					</div>
					<div class="field">
						<label>Deskripsi:</label>
						<form:textarea path="content" target-type="button"
							target-tab="#btnSimpan" class="focus-tab validation"
							data-validation="required" />
					</div>
					<div class="field">
						<label>&nbsp;</label>
						<button target-type="button" target-tab="#btBack" id="btnSimpan"
							class="btn positive focus-tab">
							<i class="icon-file icon-white"></i>SIMPAN
						</button>
						<a href="${baseURL}inventory/brands" id="btBack"
							class="btn netral focus-tab" target-tab="#title"><i
							class="icon-cancel icon-grey"></i>KEMBALI</a>
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
				<br /> <a href="" class="btn positive btNotHapus">OK</a>
			</div>
		</div>
	</div>

	<div style="display: none">
		<div class="popup alert" id="popupTAhan"
			style="width: 400px !important">
			<div class="head_title">
				INFORMASI<a href="javascript:void(0)" class="fr close-popup"><i
					class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>PILIH DATA YANG AKAN DI TAHAN</p>
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
				<p>Apakah merk ini akan dihapus?</p>
				<br /> <a href="" class="btn positive" id="btYesHapus">Hapus</a> <a
					href="" class="btn netral btNotHapus">Batal</a>
			</div>
		</div>
	</div>
	<div style="display: none">
		<div class="popup alert" id="popup_cannotDeleted" style="width: 400px !important">
			<div class="head_title">
				INFORMASI<a href="javascript:void(0)" class="fr close-popup"><i
					class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Merk ini tidak bisa dihapus karena dipakai Katalog lain</p>
				<br /><a
					href="" class="btn netral btNotHapus">OK</a>
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
				<p>Apakah merk ini akan dihapus?</p>
				<br /> <a href="" class="btn positive" id="btYesHapus1" delID="">Hapus</a>
				<a href="" class="btn netral btNotHapus1">Batal</a>
			</div>
		</div>
	</div>
</div>

<form id="frList" method="post">
	<input type="hidden" name="id"><input type="hidden"
		name="status">
</form>

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
<form id="frList" method="post"><input type="hidden" name="id"><input type="hidden" name="status"></form>