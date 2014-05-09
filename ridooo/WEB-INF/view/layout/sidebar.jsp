<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:url var="customer_auto_complete" value="/sales/customer/auto_complete" />
<c:url var="supplier_auto_complete" value="/sales/supplier/auto_complete" />
<c:url var="customer_courier_auto_complete" value="/sales/customer_courier/auto_complete" />
<c:url var="supplier_customer_auto_complete" value="/sales/customer_supplier/auto_complete" />
<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
	<c:set var="customer_auto_complete" value="/sales/customer/auto_complete" />
	<c:set var="supplier_auto_complete" value="/sales/supplier/auto_complete" />
	<c:set var="customer_courier_auto_complete" value="/sales/customer_courier/auto_complete" />
</c:if>
<c:set var="active"
	value="${requestScope['javax.servlet.forward.servlet_path']}" />
<c:set var="activeMenu"
	value="${requestScope['javax.servlet.forward.servlet_path'].split('/')}" />
<div class="main-nav-layout sticky notsw">
	<div class="nav_area">
		<a class="logo box" href="${baseURL}"><img
			src="${baseURL}img/logo.png" alt=""></a>
		<div class="search notsw">
		<form class="skip_check" id="search_index_form" method="post">
			<a href="" class="adv-search"><input class="skip_check" type="text" id="index_search" name="index_search"
				placeholder="Search..." value="${searchParameter.index_search}" /></a> </form>
<!-- 			<a href="" class="adv-search">Advanced</a> -->
				
		</div>
		<div class="main-nav box">
			<ul>
				<li class="active"><a href="">PENJUALAN</a>
					<ul  style="${activeMenu[1].equals('sales') || activeMenu[3].equals('create-form') == true ? 'display:block' : ''}">
						<li><a href="${baseURL}sales/sales-orders"
							class="${active == '/sales/sales-orders' || activeMenu[2].equals('sales-orders') ? 'active' : ''}">nota
								pesan</a></li>
						<%-- <li><a href="${baseURL}sales/packing-list" class="${active == '/sales/packing-list' || activeMenu[2].equals('packing-list') ? 'active' : ''}">packing list</a></li> --%>
						<li><a href="${baseURL}sales/invoice"
							class="${active == '/sales/invoice' || activeMenu[2].equals('invoice') ? 'active' : ''}">nota
								jual</a></li>
						<c:set var="linkSelected" value="/inventory/brands" />
						<li><a href="${baseURL}sales/deliveryorder"
							class="${active == '/sales/deliveryorder' || activeMenu[2].equals('deliveryorder') ? 'active' : ''}">surat
								jalan</a></li>
					</ul>
				</li>
				<li><a href="">PEMBELIAN</a>
					<ul  style="${activeMenu[1].equals('purchase') == true ? 'display:block' : ''}">
						<li><a href="${baseURL}purchase/bills"
							class="${active == '/purchase/bills' || activeMenu[2].equals('bills') ? 'active' : ''}">nota
								beli</a></li>
						<li><a href="${baseURL}purchase/receive-orders"
							class="${active == '/purchase/receive-orders' || activeMenu[2].equals('receive-orders') ? 'active' : ''}">surat
								terima</a></li>
					</ul>
				</li>
				<li><a href="">PENGEMBALIAN</a>
					<ul style="${activeMenu[1].equals('return-orders') == true ? 'display:block' : ''}">
						<li><a href="${baseURL}return-orders" class="${active == '/return-orders' || activeMenu[1].equals('return-orders') ? 'active' : ''}">nota retur</a></li>
					</ul>
				</li>
				<li class="active"><a href="">STOK</a>
					<ul  style="${activeMenu[1].equals('inventory') == true ? 'display:block' : ''}">
						<c:set var="linkSelected" value="inventory/brands" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">merk</a></li>
						<c:set var="linkSelected" value="inventory/catalogs" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">katalog</a></li>
						<c:set var="linkSelected" value="inventory/categories" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">kategori</a></li>
						<c:set var="linkSelected" value="inventory/stock-movement" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">penyesuaian
								stok</a></li>
						<c:set var="linkSelected" value="inventory/stock-tomove" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">perpindahan stok</a></li>
						<c:set var="linkSelected" value="inventory/transfer-order" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">surat
								pindah</a></li>
						<c:set var="linkSelected" value="inventory/pricelist" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">daftar
								harga</a></li>
					</ul></li>
				
				<li class="active"><a href="">LAPORAN</a>
					<ul  style="${ active == '/reports' || activeMenu[2].equals('reports') ?  'display:block' : ''}">
						<li><a href="${baseURL}reports" class="${active == '/reports' || activeMenu[2].equals('reports') ? 'active' : ''}">REKAPITULASI</a></li>
					</ul>
				</li>
				<li class="active"><a href="">KONTAK</a>
					<ul  style="${activeMenu[1].equals('contacts') == true ? 'display:block' : ''}">
						<c:set var="linkSelected" value="contacts/customers" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">pelanggan</a></li>
						<c:set var="linkSelected" value="contacts/suppliers" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">vendor</a></li>
						<c:set var="linkSelected" value="contacts/couriers" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">ekspedisi</a></li>
					</ul>
				</li>
				<c:set var="linkSelected" value="warehouses" />
				<li>
					<a href="">GUDANG</a>
					<ul style="${activeMenu[1].equals('warehouses') == true ? 'display:block' : ''}">
						<li><a href="${baseURL}${linkSelected}" class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">daftar gudang</a></li>
					</ul>
				</li>
				<li><a href="">AKUN PENGGUNA</a>
					<ul style="${activeMenu[1].equals('accounts') == true ? 'display:block' : ''}">
						<c:set var="linkSelected" value="accounts/staff" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">pegawai</a></li>
						<c:set var="linkSelected" value="accounts/user-accounts" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">akun</a></li>
						<c:set var="linkSelected" value="accounts/rolenaccess" />
						<li><a href="${baseURL}${linkSelected}"
							class="${active.startsWith('/'.concat(linkSelected)) ? 'active' : ''}">peran
								& akses</a></li>
					</ul>	
				</li>
				<li>
					<a href="">ARCHIVED</a>
					<ul  style="${activeMenu[1].equals('archived') == true ? 'display:block' : ''}">
						<li><a href="${baseURL}archived" class="${active == '/archived' ? 'active' : ''}">Archived</a></li>
						<li><a href="${baseURL}archived/backup" class="${active == '/archived/backup' ? 'active' : ''}">Backup</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	<div class="arrow_back">
		<a href="" class="close_search"><img
			src="${baseURL}img/arrow-back.png" /></a>
	</div>
	<div class="user-identity">
		<%= session.getAttribute("UserName").toString() %> | 
		<%= session.getAttribute("UserPosition") %><a href="${baseURL}logout" class="fr">KELUAR</a>
	</div>
</div>
<div class="search-panel notsw">
	<!-- <div class="fl"><a href="" class="close_search"><img src="img/arrow-back.png" /></a></div> -->
	<div class="search_area">
		<a href="" class="fr close_search"><i class="icon-blue icon-close"></i></a>
		<div class="clear"></div>
		<h1>SEARCH</h1>
		<form class="skip_check" id="search_form">
			<div class="field">
				<c:choose>
					<c:when test="${!empty searchParameter.indexSearch}">
						<c:set var="searchKeyword" value="${searchParameter.indexSearch}" />
					</c:when>
					<c:when test="${!empty searchParameter.index_search}">
						<c:set var="searchKeyword" value="${searchParameter.index_search}" />
					</c:when>
				</c:choose>
				<input type="text" class="long" placeholder="Masukkan kata kunci..." name="index_search" value="${searchKeyword}"/>
			</div>
			<div class="field">
				<label class="cr-right fl">Kumpulan:</label>
				<div class="field-content fr">
					<select class="select" id="service" name="service">
						<option value="sales/sales-orders" ${active.startsWith('/'.concat('sales/sales-orders')) ? 'selected' : ''}>Nota Pesan</option>
						<option value="sales/invoice" ${active.startsWith('/'.concat('sales/invoice')) ? 'selected' : ''}>Nota Jual</option>
						<option value="sales/deliveryorder" ${active.startsWith('/'.concat('sales/deliveryorder')) ? 'selected' : ''}>Surat Jalan</option>
						<option value="purchase/bills" ${active.startsWith('/'.concat('purchase/bills')) ? 'selected' : ''}>Nota Beli</option>
						<option value="purchase/receive-orders" ${active.startsWith('/'.concat('purchase/receive-orders')) ? 'selected' : ''}>Surat Terima</option>
						<option value="return-orders" ${active.startsWith('/'.concat('return-orders')) ? 'selected' : ''}>Nota Retur</option>
						<c:set var="linkSelected" value="inventory/brands" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Merk</option>
						<c:set var="linkSelected" value="inventory/catalogs" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Katalog</option>
						<c:set var="linkSelected" value="inventory/categories" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Kategori</option>
						<c:set var="linkSelected" value="inventory/stock-movement" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Penyesuaian Stok</option>
						<c:set var="linkSelected" value="inventory/stock-tomove" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Perpindahan Stok</option>
						<c:set var="linkSelected" value="inventory/pricelist" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Daftar Harga</option>
						<c:set var="linkSelected" value="inventory/transfer-order" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Surat Pindah</option>
						<c:set var="linkSelected" value="contacts/customers" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Pelanggan</option>
						<c:set var="linkSelected" value="contacts/suppliers" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Vendor</option>
						<c:set var="linkSelected" value="contacts/couriers" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Ekspedisi</option>
						<c:set var="linkSelected" value="warehouses" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Daftar Gudang</option>
						<c:set var="linkSelected" value="accounts/staff" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Pegawai</option>
						<c:set var="linkSelected" value="accounts/user-accounts" />
						<option value="${linkSelected}" ${active.startsWith('/'.concat(linkSelected)) ? 'selected' : ''}>Akun</option>
					</select>
				</div>
				<div class="clear"></div>
			</div>
			<div class="head-filter" id="filter_form_search">FILTER</div>
			
			<div class="field" id="customerField">
				<label class="cr-right">Pelanggan:</label>
				<div class="clear"></div>
				<input type="text" class="long" name="searchCustomerId" id="searchCustomerId" value="${customerId}" />
			</div>
			<div class="field" id="dateField" style="display:${!activeMenu[2].equals('catalogs') && !activeMenu[2].equals('stock-movement') ? 'block' : 'none'}">
				<label class="cr-right">Periode:</label>
				<div class="clear"></div>
				<div class="field-content fl">
					<div class="input-date input-append">
						<div class="input-icon">
							<i class="icon-calendar"></i>
						</div>
						<input type="text" class="datepicker" name="searchStartDate" />
					</div>
				</div>
				<div class="field-content fr">
					<div class="input-date input-append">
						<div class="input-icon">
							<i class="icon-calendar"></i>
						</div>
						<input type="text" class="datepicker" name="searchEndDate" />
					</div>
				</div>
				<div class="clear"></div>
			</div>
			
			<div class="field" id="statusField" style="display:${!activeMenu[2].equals('catalogs') && !activeMenu[2].equals('pricelist') ? 'block' : 'none'}">
				<label class="cr-right fl">Status:</label>
				<div class="field-content fr">
					<select class="select" name="searchStatus" id="searchStatus">
						<option value="">Pilih Status</option>
						<c:forEach items="${statusOption}" var="option">
							<option value="${option.key}">${option.value}</option>
						</c:forEach>
<!-- 						<option>Aktif</option> -->
<!-- 						<option>Item 2</option> -->
<!-- 						<option>Item 3</option> -->
					</select>
				</div>
			</div>
			<div class="field" id="salesPersonField" style="display:${activeMenu[2].equals('sales-orders') || activeMenu[2].equals('invoice') ? 'block' : 'none'}">
				<label class="cr-right fl">Sales Person:</label>
				<div class="field-content fr">
					<select class="select" name="salesPerson">
						<option value="">Pilih Sales</option>
						<c:forEach items="${salesList}" var="sales">
							<option value="${sales.key}" ${searchParameter.salesPersonId == sales.key ? 'selected' : '' }>${sales.value}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="field" id="retailField" style="display:${active == '/sales/sales-orders' || active == '/sales/invoice' || active == '/return-orders' ? 'block' : 'none'}">
				<label class="cr-right fl">Retail:</label>
				<div class="field-content fr">
					<select class="select" name="retail">
						<option value="">Pilih Retail</option>
						<c:forEach items="${retailOption}" var="retail">
							<option value="${retail.key}" ${searchParameter.retail == retail.key ? 'selected' : '' }>${retail.value}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="field" id="skuField" style="display:'none'">
				<label class="cr-right">SKU:</label>
 				<div class="clear"></div>
 				<input type="text" class="long" name="searchSku" id="searchSku" value="${searchParameter.searchSku}"/>
 			</div>
 			<div class="field" id="itemField" style="display:'none'">
				<label class="cr-right">Barang:</label>
 				<div class="clear"></div>
 				<input type="text" class="long" name="searchItemName" id="searchItemName" value="${searchParameter.searchItemName}" />
 			</div>
 			
			
			<div class="field" id="sourceField">
				<label class="cr-right">Asal:</label>
				<div class="clear"></div>
				<input type="text" class="long" name="searchSource" id="searchSource"/>
			</div>
			
			<div class="field" id="destinationField">
				<label class="cr-right">Tujuan:</label>
				<div class="clear"></div>
				<input type="text" class="long" id="searchDestination" name="searchDestination" />
			</div>
			<div class="field" id="destinationCustomerField">
				<label class="cr-right">Tujuan:</label>
				<div class="clear"></div>
				<input type="text" class="long" id="destinationCustomer" name="destinationCustomer" />
			</div>
			
			<div class="field" id="vendorField">
				<label class="cr-right">Vendor:</label>
				<div class="clear"></div>
				<input type="text" class="long" id="searchVendorId" name="searchVendorId" />
			</div>
			
			<div class="field" id="vendorCustomerField">
				<label class="cr-right">Vendor:</label>
				<div class="clear"></div>
				<input type="text" class="long" id="searchVendorCustomerId" name="searchVendorCustomerId" />
			</div>
			
			<div class="field" id="refNumberField">
				<label class="cr-right">Kode Bongkar:</label>
				<div class="clear"></div>
				<input type="text" class="long" name="refNumber" value="${searchParameter.refNumber}" />
			</div>
			
			<div class="field" id="warehouseField">
				<label class="cr-right">Gudang:</label>
				<div class="clear"></div>
				<input type="text" class="long" name="searchWarehouse" id="searchWarehouse" />
			</div>
			
			<div class="field" id="stockField">
				<label class="cr-right">Stok:</label>
				<div class="clear"></div>
				<input type="text" class="long" name="searchStock" value="${searchParameter.searchStock}"/>
			</div>
			
			<div class="field" id="brandField">
				<label class="cr-right">Merk:</label>
				<div class="clear"></div>
				<input type="text" class="long" name="searchBrand" id="searchBrand" />
			</div>
			
			<div class="field" id="categoryField">
				<label class="cr-right">Kategori:</label>
				<div class="clear"></div>
				<input type="text" class="long" name="searchCategory" id="searchCategory" />
			</div>
			
			<div class="field" id="cityField">
				<label class="cr-right">Kota:</label>
				<div class="clear"></div>
				<input type="text" class="long" name="searchCity" id="searchCity" value="${searchParameter.searchCity}"/>
			</div>
			
			<div class="field" id="provinceField">
				<label class="cr-right">Provinsi:</label>
				<div class="clear"></div>
				<input type="text" class="long" name="searchProvince" id="searchProvince" value="${searchParameter.searchProvince}"/>
			</div>
			<div class="field">
				<div class="checkbox">
					<div class="checkbox-field">
						<input type="checkbox" name="data_archive" value="true"> <label>Archive Data</label>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="filter-end">
				<a href="${baseURL}${active.substring(1,active.length())}" id="search" class="btn positive">SEARCH</a>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">

	function changeService(){
		if($('#service').selectText() == "Nota Pesan"){
			$('#filter_form_search').show();
			$('#customerField').show();
			$('#dateField').show();
			$('#statusField').show();
			$('#salesPersonField').show();
			$('#retailField').show();
			$('#itemField').hide();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').hide();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').hide();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').hide();
		}
		else if($('#service').selectText() == "Nota Jual"){
			$('#filter_form_search').show();
			$('#customerField').show();
			$('#dateField').show();
			$('#statusField').show();
			$('#salesPersonField').show();
			$('#retailField').hide();
			$('#itemField').hide();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').hide();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').hide();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').hide();
		}
		else if($('#service').selectText() == "Surat Jalan"){
			$('#filter_form_search').show();
			$('#customerField').hide();
			$('#dateField').show();
			$('#statusField').show();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').hide();
			$('#destinationField').hide();
			$('#destinationCustomerField').show();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').hide();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').hide();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').hide();
		}
		else if($('#service').selectText() == "Surat Pindah"){
			$('#filter_form_search').show();
			$('#customerField').hide();
			$('#dateField').show();
			$('#statusField').show();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').show();
			$('#destinationField').show();
			$('#destinationCustomerField').hide();
			$('#sourceField').show();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').hide();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').show();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').show();
		}
		else if($('#service').selectText() == "Nota Beli"){
			$('#filter_form_search').show();
			$('#customerField').hide();
			$('#dateField').show();
			$('#statusField').show();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').hide();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').show();
			$('#vendorCustomerField').hide();
			$('#refNumberField').show();
			$('#warehouseField').hide();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').hide();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').hide();
		}
		else if($('#service').selectText() == "Surat Terima"){
			$('#filter_form_search').show();
			$('#customerField').hide();
			$('#dateField').show();
			$('#statusField').show();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').hide();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').show();
			$('#refNumberField').hide();
			$('#warehouseField').hide();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').hide();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').hide();
		}
		else if($('#service').selectText() == "Nota Retur"){
			$('#filter_form_search').show();
			$('#customerField').show();
			$('#dateField').show();
			$('#statusField').show();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').hide();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').hide();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').hide();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').hide();
		}
		else if($('#service').selectText() == "Penyesuaian Stok"){
			$('#filter_form_search').show();
			$('#customerField').hide();
			$('#dateField').show();
			$('#statusField').show();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').show();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').show();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').show();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').hide();
		}
		else if($('#service').selectText() == "Perpindahan Stok"){
			$('#filter_form_search').show();
			$('#customerField').hide();
			$('#dateField').show();
			$('#statusField').show();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').show();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').show();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').show();
			$('#cityField').hide();
			$('#provinceField').hide();
		}
		else if($('#service').selectText() == "Katalog"){
			$('#filter_form_search').show();
			$('#customerField').hide();
			$('#dateField').hide();
			$('#statusField').hide();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').show();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').show();
			$('#stockField').show();
			$('#brandField').show();
			$('#categoryField').show();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').show();
		}
		else if($('#service').selectText() == "Daftar Harga"){
			$('#filter_form_search').show();
			$('#customerField').hide();
			$('#dateField').hide();
			$('#statusField').hide();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').show();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').hide();
			$('#stockField').hide();
			$('#brandField').show();
			$('#categoryField').show();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').hide();
		}
		else if($('#service').selectText() == "Merk"){
			$('#filter_form_search').show();
			$('#customerField').hide();
			$('#dateField').show();
			$('#statusField').hide();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').hide();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').hide();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').hide();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').hide();
		}
		else if( $('#service').selectText() == "Kategori" || $('#service').selectText() == "Akun"){
			$('#filter_form_search').hide();
			$('#customerField').hide();
			$('#dateField').hide();
			$('#statusField').hide();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').hide();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').hide();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').hide();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').hide();
		}
		else if($('#service').selectText() == "Daftar Gudang"){
			$('#filter_form_search').show();
			$('#customerField').hide();
			$('#dateField').hide();
			$('#statusField').show();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').hide();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').hide();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').hide();
			$('#cityField').hide();
			$('#provinceField').hide();
			$('#skuField').hide();
		}
		else if($('#service').selectText() == "Pelanggan" || $('#service').selectText() == "Vendor" || $('#service').selectText() == "Ekspedisi"
			 || $('#service').selectText() == "Pegawai"){
			$('#filter_form_search').show();
			$('#customerField').hide();
			$('#dateField').show();
			$('#statusField').show();
			$('#salesPersonField').hide();
			$('#retailField').hide();
			$('#itemField').hide();
			$('#destinationField').hide();
			$('#destinationCustomerField').hide();
			$('#sourceField').hide();
			$('#vendorField').hide();
			$('#vendorCustomerField').hide();
			$('#refNumberField').hide();
			$('#warehouseField').hide();
			$('#stockField').hide();
			$('#brandField').hide();
			$('#categoryField').hide();
			$('#cityField').show();
			$('#provinceField').show();
			$('#skuField').hide();
		}
		
		
	}
	
	function changeStatusOption(){
		$.ajax({
			type : "GET",
			url : "${baseURL}"+$('#service').val()+"/get-status-option"
		})
		.done(
			function(responseText) {
				var data = jQuery.parseJSON(responseText);
				var target = $("#statusField").find(".field-content");
				target.html("");
				target.append('<select class="select" name="searchStatus" id="searchStatus"></select>');

				$('#searchStatus').html("<option value=''>Pilih Status</option>");
				
				$.each(data, function(key,value){
					$('#searchStatus').append('<option value="'+key+'">'+value+'</option>');
				});

				core.ui.dropdown();
				
				<c:if test="${searchParameter.searchStatus.length()>0}">
				$('#searchStatus').selectVal(${searchParameter.searchStatus});
				</c:if>
				
				<c:if test="${searchParameter.status.length()>0}">
				$('#searchStatus').selectVal(${searchParameter.status});
				</c:if>
				
		});
	}
	
	function customerSearchAutoComplete(prepopulateData) {
		core.ui.autocomplete({
			data : "${customer_auto_complete}",
			prePopulate : prepopulateData, // required
			hintText : 'Silahkan ketikkan nama dari pelanggan....',
// 			onDelete : function(){
// 				$('textarea.address').val('');
// 				$('#customer').val('');
// 				$("#customerName").val('');
// 			},
// 			onAdd : ,
			onResult : null,
			width : $("#customerField").width(),
			onReady : null,
			target_element : '#searchCustomerId',
			minChars : 1,
		});
	}
	
	function destinationSearchAutoComplete(prepopulateData) {
		core.ui.autocomplete({
			data : "${customer_courier_auto_complete}",
			prePopulate : prepopulateData, // required
			hintText : 'Silahkan ketikkan nama dari pelanggan / ekspedisi....',
// 			onDelete : function(){
// 				$('textarea.address').val('');
// 				$('#customer').val('');
// 				$("#customerName").val('');
// 			},
// 			onAdd : ,
			onResult : null,
			width : $("#destinationCustomer").width(),
			onReady : null,
			target_element : '#destinationCustomer',
			minChars : 1,
		});
	}
	function vendorSearchAutoComplete(prepopulateData) {
		core.ui.autocomplete({
			data : "${supplier_auto_complete}",
			prePopulate : prepopulateData, // required
			hintText : 'Silahkan ketikkan nama vendor....',
// 			onDelete : function(){
// 				$('textarea.address').val('');
// 				$('#customer').val('');
// 				$("#customerName").val('');
// 			},
// 			onAdd : ,
			onResult : null,
// 			left : $(".inputholder").offset().left,
			width : $("#vendorField").width(),
			onReady : null,
			target_element : '#searchVendorId',
			minChars : 1,
		});
	}
	function vendorCustomerSearchAutoComplete(prepopulateData) {
		core.ui.autocomplete({
			data : "${supplier_customer_auto_complete}",
			prePopulate : prepopulateData, // required
			hintText : 'Silahkan ketikkan nama vendor/pelanggan....',
// 			onDelete : function(){
// 				$('textarea.address').val('');
// 				$('#customer').val('');
// 				$("#customerName").val('');
// 			},
// 			onAdd : ,
			onResult : null,
// 			left : $(".inputholder").offset().left,
			width : $("#vendorCustomerField").width(),
			onReady : null,
			target_element : '#searchVendorCustomerId',
			minChars : 1,
		});
	}
	
	
	function autoCompleteForSourceWarehouse(prepopulateData) {
		core.ui.autocomplete({
			data : "${baseURL}warehouses/warehouse-autocomplete",
			prePopulate : prepopulateData,
			hintText : 'Silahkan ketikkan nama gudang....',
			onResult : null,
			width : $("#sourceField").width(),
			onReady : null,
			onAdd : function (data) {
				<c:if test="${searchParameter.searchDestination.length()>0}">
				autoCompleteForDestinationWarehouse([{id: ${searchParameter.searchDestination}, name:"${searchParameter.destinationWarehouseName}"}]);
				</c:if>
				<c:if test="${searchParameter.searchDestination.length()<1}">
					autoCompleteForDestinationWarehouse();
				</c:if>
			},
			target_element : '#searchSource',
			minChars : 1,
		});
	}
	
	function autoCompleteForDestinationWarehouse(prepopulateData) {
		try {
			 $("#searchDestination").parent().find(".token-input-list").remove();
			} catch (err) {}
			
			core.ui.autocomplete({			
				data : "${baseURL}warehouses/warehouse-autocomplete?sourcei="+$('#searchSource').val(),
				prePopulate : prepopulateData,
				hintText : 'Silahkan ketikkan nama dari product....',
				onResult : null,
				onReady : null,
				width : $("#destinationField").width(),
				target_element : '#searchDestination',
				minChars : 1
			},
			$('#searchDestination'));
	}
	
	$(document).ready(function(){
		$('#search').click(function(e){
			e.preventDefault();
			$('#search_form').attr('action', "${baseURL}"+$('#service').val());
 			$('#search_form').submit();
// 			window.location = "${baseURL}" + $('#service').val() + "?" + $('form').serialize();
		});
		
		$('#service').selectOnClick(function(){
			changeService();
			changeStatusOption();
		});
		
		
		<c:if test="${searchParameter.searchStartDate.length()>0}">
			$('input[name="searchStartDate"]').datepicker( "setDate", "${searchParameter.searchStartDate}" );
		</c:if>
		<c:if test="${searchParameter.startDate.length()>0}">
				$('input[name="searchStartDate"]').datepicker( "setDate", "${searchParameter.startDate}" );
		</c:if>
		<c:if test="${searchParameter.searchEndDate.length()>0}">
			$('input[name="searchEndDate"]').datepicker( "setDate", "${searchParameter.searchEndDate}" );
		</c:if>
		<c:if test="${searchParameter.endDate.length()>0}">
			$('input[name="searchEndDate"]').datepicker( "setDate", "${searchParameter.endDate}" );
		</c:if>
		<c:if test="${searchParameter.searchSource.length()>0}">
			autoCompleteForSourceWarehouse([{id: ${searchParameter.searchSource}, name:"${searchParameter.sourceWarehouseName}"}]);
		</c:if>
		<c:if test="${searchParameter.searchSource.length()<1}">
			autoCompleteForSourceWarehouse();
		</c:if>
		<c:if test="${searchParameter.searchDestination.length()>0}">
		autoCompleteForDestinationWarehouse([{id: ${searchParameter.searchDestination}, name:"${searchParameter.destinationWarehouseName}"}]);
		</c:if>
		<c:if test="${searchParameter.searchDestination.length()<1}">
			autoCompleteForDestinationWarehouse();
		</c:if>
		var prepopulateData = null;
		<c:if test="${searchParameter.searchWarehouse.length()>0}">
			prepopulateData = [{id: ${searchParameter.searchWarehouse}, name:"${searchParameter.WarehouseName}"}];
		</c:if>
		core.ui.autocomplete({
			data : "${baseURL}warehouses/warehouse-autocomplete",
			prePopulate : prepopulateData,
			hintText : 'Silahkan ketikkan nama gudang....',
			width : $("#warehouseField").width(),
			target_element : '#searchWarehouse',
			minChars : 1
		});
		
		prepopulateData = null;
		<c:if test="${searchParameter.searchBrand.length()>0}">
			prepopulateData = [{id: ${searchParameter.searchBrand}, name:"${searchParameter.searchBrandName}"}];
		</c:if>
		core.ui.autocomplete({
			data : "${baseURL}inventory/brands/autocomplete-brands",
			prePopulate : prepopulateData,
			hintText : 'Silahkan ketikkan nama merk....',
			width : $("#brandField").width(),
			target_element : '#searchBrand',
			minChars : 1
		});
		
		prepopulateData = null;
		<c:if test="${searchParameter.searchCategory.length()>0}">
			prepopulateData = [{id: ${searchParameter.searchCategory}, name:"${searchParameter.searchCategoryName}"}];
		</c:if>
		core.ui.autocomplete({
			data : "${baseURL}inventory/categories/autocomplete-category",
			prePopulate : prepopulateData,
			hintText : 'Silahkan ketikkan nama kategori....',
			width : $("#categoryField").width(),
			target_element : '#searchCategory',
			minChars : 1
		});
		
		prepopulateData = null;
		<c:if test="${searchParameter.searchCity.length()>0}">
			prepopulateData = [{id: '${searchParameter.searchCity}', name:"${searchParameter.searchCity}"}];
		</c:if>
		core.ui.autocomplete({
			data : "${baseURL}contacts/customers/autocomplete-city",
			prePopulate : prepopulateData,
			hintText : 'Silahkan ketikkan nama kategori....',
			width : $("#categoryField").width(),
			target_element : '#searchCity',
			minChars : 1
		});
		
		prepopulateData = null;
		<c:if test="${searchParameter.searchProvince.length()>0}">
			prepopulateData = [{id: '${searchParameter.searchProvince}', name:"${searchParameter.searchProvince}"}];
		</c:if>
		core.ui.autocomplete({
			data : "${baseURL}contacts/customers/autocomplete-province",
			prePopulate : prepopulateData,
			hintText : 'Silahkan ketikkan nama kategori....',
			width : $("#categoryField").width(),
			target_element : '#searchProvince',
			minChars : 1
		});

		<c:choose>
			<c:when test="${searchParameter.customerId.length()>0}">
				customerSearchAutoComplete([{id: "${searchParameter.customerId}", name:"${searchParameter.customerName}"}]);
			</c:when>
			<c:otherwise>
				customerSearchAutoComplete();
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${searchParameter.destination.length()>0}">
				destinationSearchAutoComplete([{id: "${searchParameter.destination}", name:"${searchParameter.destinationCustomerName}"}]);
			</c:when>
			<c:otherwise>
				destinationSearchAutoComplete();
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${searchParameter.vendorId.length()>0}">
				vendorSearchAutoComplete([{id: "${searchParameter.vendorId}", name:"${searchParameter.vendorName}"}]);
			</c:when>
			<c:otherwise>
				vendorSearchAutoComplete();
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${searchParameter.vendorCustomerId.length()>0}">
				vendorCustomerSearchAutoComplete([{id: "${searchParameter.vendorCustomerId}", name:"${searchParameter.vendorCustomerName}"}]);
			</c:when>
			<c:otherwise>
				vendorCustomerSearchAutoComplete();
			</c:otherwise>
		</c:choose>
		
		changeService();
		changeStatusOption();
	});

</script>
