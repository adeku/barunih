<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:set var="onServer" value="0" />
<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
	<c:set var="onServer" value="1" />
</c:if>
<div class="print-layout">
	<div class="print-header">
		<h1>Daftar Harga</h1>
	</div>
	<table class="table-helper table">
			<colgroup>
				<col style="width: 20px">
				<col style="width: 180px">
				<col style="width: auto">
				<col style="width: 150px">
				<col style="width: 150px">
			</colgroup>
			<thead>
				<tr>
					<td style="width: 10px"></td>
					<td>sku</td>
					<td>item</td>
					<td class="text-right">msrp</td>
					<td class="text-right">mitra10 <input type="hidden" name="priceSelected" value="mitra10"></td>
				</tr>
			</thead>
			<tbody  id="tableValuePrint"></tbody>
	</table>
</div>
<div class="content notsw">
	<form id="frPriceSet" method="post">
		<div class="main-content">
			<div class="header-info">
				<div class="header-attr">

					<div class="btn-group">
						<a class="btn netral" href="">Tools</a>
						<div class="btn dropdown netral">
							<i class="icon-dropdown icon-grey"></i>
						</div>
						<ul>
							<c:if test="${canCreate||canUpdate}">
<!-- 								<li><a href="" id=btnSavePriceHere>Simpan</a></li> -->
							</c:if>
							<li><a href="" id="btnExport">Export daftar</a></li>
							<c:if test="${canCreate||canUpdate}">
								<li><a href="" id="btnImport">Update daftar</a></li>
							</c:if>
							<c:if
								test="${!(selectedOne.equalsIgnoreCase('wholesale')||selectedOne.equalsIgnoreCase('retail'))&&canDelete}">
								<li><a href="" class="cancel" id="btnHapusSelected">Hapus</a>
								</li>
							</c:if>
						</ul>
					</div>
					<c:if test="${empty priceList&&(canCreate||canUpdate)}">
						<a href="" class="btn positive" id="newPrice"><i
							class="icon-file"></i>buat baru</a>
					</c:if>
					<c:if test="${!empty priceList}">
						<div class="btn-group">
							<a class="btn netral" href="">${selectedOne}</a>
							<div class="btn dropdown netral">
								<i class="icon-dropdown icon-grey"></i>
							</div>
							<ul>
								<c:forEach items="${priceList}" var="price1">
									<c:if test="${selectedOne!=price1}">
										<li><a href="#" class="selectPriceType" pricetype="${price1}">${price1}</a></li>
									</c:if>
								</c:forEach>
								<c:if test="${canCreate}">
									<li><a href="#" id="newPrice1">Buat baru</a></li>
								</c:if>
							</ul>
						</div>
					</c:if>
					<a href="javascript:void(0);" class="btn netral print skip-check-dirty" data-print-limit="10"><i class="icon-grey icon-print"></i>CETAK</a>
				</div>
				<h1>DAFTAR HARGA</h1>
			</div>
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
		
		<c:if test="${!empty searchParameter.searchItemName}">
			<div class="pill-area">
				<p class="label">Barang</p>
				<p class="content-pill">${searchParameter.searchItemName}</p>
			</div>
		</c:if>
		<c:if test="${!empty searchParameter.searchBrand}">
			<div class="pill-area">
				<p class="label">Merk</p>
				<p class="content-pill">${searchParameter.searchBrandName}</p>
			</div>
		</c:if>
			<table>
				<colgroup>
					<col style="width: 20px"></col>
					<col style="width: 180px"></col>
					<col style="width: auto"></col>
					<c:if test="${isAdmin==1}">
					<col style="width: 150px"></col>
					</c:if>
					<col style="width: 150px"></col>
				</colgroup>
				<thead>
					<tr>
						<td></td>
						<td>sku</td>
						<td>item</td>
						<c:if test="${isAdmin==1}">
						<td class="text-right">msrp</td>
						</c:if>
						<c:if test="${priceListCount>0}">
							<td class="text-right">${selectedOne}<input type="hidden" name="priceSelected"
								value="${selectedOne}"></td>
						</c:if>
					</tr>
				</thead>
				<tbody id="tableValue"></tbody>
			</table>
			<div class="btn_loadmore_area" id="btnPAgeNext"></div>
		</div>
	</form>
</div>

<div style="display: none">
	<div class="popup alert" id="winDowsaddPrice"
		style="width: 400px !important">
		<div class="head_title">
			Tambah harga<a href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		<div class="popup-holder">
			<c:url var="addPrice" value="/inventory/pricelist/addprice" />
			<c:if test="${onServer=='1'}">
				<c:set var="addPrice" value="/inventory/pricelist/addprice" />
			</c:if>
			<form id="frAddPrice" action="${addPrice}" method="post">
				<div class="form">
					<div class="field" style="background-color: red; color: white;"
						id="msgWaregouseErr"></div>
					<div class="field">
						<label>Nama harga</label> <input type="text" name="namePrice"
							id="namePrice" class="fr">
					</div>
				</div>
			</form>
			<br /> <a href="" class="btn positive" id="btSavePrice">SAve</a> <a
				href="" class="btn netral btNotHapus">cancel</a>
		</div>
	</div>
</div>

<div style="display: none">
	<div class="popup alert" id="infoDeleted"
		style="width: 400px !important">
		<div class="head_title">
			INFORMASI<a href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		<div class="popup-holder">
			<p>Apakah harga ${selectedOne} akan dihapus?</p>
			<br />
			<br /> <a href="" class="btn positive" id="btYesHapus">Hapus</a> <a
				href="" class="btn netral btNotHapus">Batal</a>
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
	<div class="popup alert" id="layoutUpdateData" style="height: 600px !important">
		<div class="head_title">
			<span>Update daftar harga</span><a
				href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		
		<div class="popup-holder">
			<div class="empty-state upload-file" style="margin-top:0;cursor:pointer">
				<i class="icon-upload entry" style="font-size:14px;margin-top:12px;"></i>
				<h5>Klik / Drag file ke sini</h5>
			</div>
			<input type="file" id="import-file" style="display: none"  accept=".csv"/>
			<div class="clear"></div>
			<br />
			<div class="field">
				<label>&nbsp;</label>
				<a id="import-btn" class="btn positive focus-tab" href="">import</a> 
				<a target-tab="#name" id="btCanceLWArehouse" class="btn netral btNotHapus focus-tab" href="">batal</a>
			</div>
		</div>
	</div>
</div>

<div style="display: none">
	<div class="popup alert" id="layoutExportData" style="height:400px">
		<div class="head_title">
			<span>Export daftar harga</span><a
				href="javascript:void(0)" class="fr close-popup"><i
				class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		<div class="popup-holder">
			<div class="content-form export-form">
				<div class="field" style="background-color: red" id="alertError">

				</div>
				<div class="field fl"  >
					<label>Brand :</label> <br>
					<select id="brand" name="brand"  class="select "  >
<!-- 						<option value="" selected="selected">Pilih gudang</option> -->
						<c:forEach var="brand" items="${brands}">
							<option value="${brand.value_text}">${brand.value_text}</option>
						</c:forEach>
					</select>
				</div>
				<br />
				<br />	
				<div class="clear"></div>
				<div class="field fl" >
					<label>Stok :</label> <br>
					<select id="stock" name="stock" class="select" >
						<option value="1">Available</option>
						<option value="2">Not Available</option>
						<option value="3">Exclude Out Of Stock</option>
						<option value="4">Exclude Low Of Stock</option>
					</select>
				</div>
				<br />
				<br />	
				<div class="clear"></div>
				<div class="field fl"  >
					<label>Harga :</label> <br>
					<select id="group" name="group" class="select ">
						<c:forEach items="${priceList}" var="price1">
							<option value="${price1}">${price1}</option>
						</c:forEach>
					</select>
				</div>
				<br />
				<br />
				<br />
				<br />	
				<div class="clear"></div>
				<div class="field">
					<label>&nbsp;</label>
						<a id="export-btn" class="btn positive focus-tab skip-check-dirty" href="javascript:void();">export</a>
						<a target-tab="#name" class="btn netral btNotHapus focus-tab" href="">batal</a>
				</div>

			</div>
		</div>
	</div>
</div>