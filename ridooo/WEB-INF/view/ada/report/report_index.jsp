<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/sales/sales-orders"/>
<c:url var="URL" value="/"/>
<c:if test="${URL=='//'}">
<c:set var="URL" value="/"/>
<c:set var="baseURL" value="/sales/sales-orders"/>
</c:if>
<div class="print-layout">
	<div class="header-print">
		<div class="header-attr">
			<h2 class="fl">LAPORAN</h2>
			<p id="rightform_date"  class=" fr" >&nbsp;&mdash;&nbsp; ${dateTo}</p>
			<p id="rightform_date" class=" fr" >${dateFrom}</p>
			<div class="clear"></div>
		</div>
	</div>
	<!-- header info -->
	
	<!-- table -->
	<div class="tableofreport">
		<div class="fl">
			<div class="title">LAPORAN ASET</div>
			<div class="fl">
				<p>Jumlah Jenis Barang</p>
				<p>Jumlah Barang Kosong</p>
				<p>Total Aset</p>
				<p>Total Unit Barang</p>
				<c:forEach var="warehouse" items="${warehouse_report}">
					<p >${warehouse.name}</p>
				</c:forEach>
			</div>
			<div class="fr text-right">
				<p class="price">${catalog_jenis_count == null ? 0 : catalog_jenis_count}</p>
				<p class="price">${catalog_out_of_stock_jenis == null ? 0 : catalog_out_of_stock_jenis}</p>
				<p class="price">${catalog_asset_total == null ? 0 : catalog_asset_total}</p>
				<p class="price">${catalog_count_total == null ? 0 : catalog_count_total}</p>
				
				<c:forEach var="warehouse" items="${warehouse_report}">
					<p class="price">${warehouse.total}</p>
				</c:forEach>
			</div>
			<div class="clear"></div>
		</div>
		<div class="fl">
			<div class="title">LAPORAN JUAL BELI</div>
			<div class="fl">
				<p>Total Penjualan</p>
				<p>Belum Diproses</p>
				<p>Jumlah Unit Barang</p>
				<p>Peningkatan</p>
				<p>&nbsp;</p>
				<p>Total Pembelian</p>
				<p>Jumlah Unit Barang</p>
				<p>Peningkatan</p>
			</div>
			<div class="fr">
				<p class="price">${invoice_total_sales == null ? 0 : invoice_total_sales}</p>
				<p class="price">${invoice_total_pending_sales == null ? 0 : invoice_total_pending_sales}</p>
				<p class="price">${invoice_total_item == null ? invoice_total_item : invoice_total_item}</p>
				<p>+34%</p>
				<p>&nbsp;</p>
				<p  class="price">${bill_total_sales == null ? 0 : bill_total_sales}</p>
				<p  class="price">${bill_total_item == null ? 0 : bill_total_item}</p>
				<p >+23%</p>
			</div>
			<div class="clear"></div>
		</div>
		<div class="fl">
			<div class="title">HUTANG/PIUTANG</div>
			<div class="fl">
				<p>Total Hutang</p>
				<p>Bulan Ini</p>
				<p>30+</p>
				<p>60+</p>
				<p>&nbsp;</p>
				<p>Total Piutang</p>
				<p>Bulan Ini</p>
				<p>30+</p>
				<p>60+</p>
			</div>
			<div class="fr">
				<p class="price">${total_utang_all 	  == null ? 0 : total_utang_all}</p>
				<p class="price">${total_utang_at_month == null ? 0 : total_utang_at_month}</p>
				<p class="price">${total_utang_30_month == null ? 0 : total_utang_30_month}</p>
				<p class="price">${total_utang_60_month == null ? 0 : total_utang_60_month}</p>
				<p>&nbsp;</p>
				<p class="price">${total_piutang_all      == null ? 0 : total_piutang_all}</p>
				<p class="price">${total_piutang_at_month == null ? 0 : total_piutang_at_month}</p>
				<p class="price">${total_piutang_30_month == null ? 0 : total_piutang_30_month}</p>
				<p class="price">${total_piutang_60_month == null ? 0 : total_piutang_60_month}</p>
			</div>
			<div class="clear"></div>
		</div>
		<div class="fl">
			<div class="title">LAIN-LAIN</div>
			<div class="fl">
				<p>Jumlah Staff</p>
				<p>Jumlah Salesperson</p>
				<p>Penjualan per Orang</p>
				<p>Top Salesperson</p>
				<p>&nbsp;</p>
				<p>Jumlah Retur</p>
				<p>Jumlah Unit Retur</p>
			</div>
			<div class="fr">
				<p>${staff_total_count}</p>
				<p>${salesperson_total_count}</p>
				<p class="price-decimal">${salesperson_invoice_avg}</p>
				<p class="price">${top_salesperson}</p>
				<p>&nbsp;</p>
				<p class="price">${ret_total_sales}</p>
				<p class="price">${ret_total_item}</p>
			</div>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
	<table class=" tableprint"  style="page-break-before: always">
		<colgroup>
		<col style="width:140px"></col>
		<col style="width:auto"></col>
		<col style="width:90px;"></col>
		<col style="width:160px;"></col>
		<col style="width:90px"></col>
		<col style="width:100px;"></col>
		</colgroup>
		<thead>
			<tr>
				<td>NOTA JUAL</td>
				<td>PELANGGAN</td>
				<td align="right">ITEM</td>
				<td align="right">JUMLAH</td>
				<td>TGL</td>
				<td>STATUS</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="invoice" items="${invoice_list}">
			<tr style="page-break-inside:auto;page-break-after:auto;page-break-before:auto">
				<td class="number"><a class="upfont" href="">${invoice.refNumber}</a></td>
				<td>
					<a href="">${invoice.customerName}</a>
					<br>
					<span>${invoice.customerCity}</span>
				</td>
				<td class="number" align="right">${invoice.totalItem}</td>
				<td class="number price" align="right">${invoice.totalAmount}</td>
				<td>12/12/12</td>
				<td>
					<c:choose>
						<c:when test="${invoice.status == 'Tahan'}">
							<div class="status hold">
								${invoice.status}
							</div>
						</c:when>
						<c:when test="${invoice.status == 'Aktif'}">
							<div class="status hold active">
								${invoice.status}
							</div>
						</c:when>
						<c:when test="${invoice.status == 'Lunas'}">
							<div class="status done completed">
								${invoice.status}
							</div>
						</c:when>
						<c:when test="${invoice.status == 'Batal'}">
							<div class="status cancel cancelled">
								${invoice.status}
							</div>
						</c:when>
						<c:otherwise>
							<div class="status hold">
								${invoice.status}
							</div>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br/>
	<br/>
	<!-- bill section-->
	<table class=" tableprint" style="page-break-before:always">
		<colgroup>
		<col style="width:140px"></col>
		<col style="width:auto"></col>
		<col style="width:90px;"></col>
		<col style="width:160px;"></col>
		<col style="width:90px"></col>
		<col style="width:100px;"></col>
		</colgroup>
		<thead>
			<tr>
				<td>NOTA BELI</td>
				<td>PELANGGAN</td>
				<td align="right">ITEM</td>
				<td align="right">JUMLAH</td>
				<td>TGL</td>
				<td>STATUS</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="bill" items="${bill_list}">
			<tr style="page-break-inside:auto;page-break-after:auto;page-break-before:auto">
				<td class="number"><a class="upfont" href="">${bill.refNumber}</a></td>
				<td>
					<a href="">${bill.customerName}</a>
					<br>
					<span>${bill.customerCity}</span>
				</td>
				<td class="number" align="right">${bill.totalItem}</td>
				<td class="number price" align="right">${bill.totalAmount}</td>
				<td>12/12/12</td>
				<td>
					<c:choose>
						<c:when test="${bill.status == 'Tahan'}">
							<div class="status hold">
								${bill.status}
							</div>
						</c:when>
						<c:when test="${bill.status == 'Aktif'}">
							<div class="status hold active">
								${bill.status}
							</div>
						</c:when>
						<c:when test="${bill.status == 'Lunas'}">
							<div class="status done completed">
								${bill.status}
							</div>
						</c:when>
						<c:when test="${bill.status == 'Batal'}">
							<div class="status cancel cancelled">
								${bill.status}
							</div>
						</c:when>
						<c:otherwise>
							<div class="status hold">
								${bill.status}
							</div>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
<div class="content notsw">
	<!-- heading title -->
	<div class="main-content">
			<!-- heading tittle -->
		<form:form modelAttribute="form" method="get" action="">
			<div class="header-info">
				<div class="header-attr">
					<div class="input-date input-append mr10">
						<div class="input-icon">
							<i class="icon-calendar icon-grey"></i>
						</div>
						<input id="dateFrom" class="datepicker" type="text" name="dateFrom" mr="555" value="${dateFrom}">
					</div>
					<div class="input-date input-append">
						<div class="input-icon">
							<i class="icon-calendar icon-grey"></i>
						</div>
						<input id="dateTo" class="datepicker" type="text" name="dateTo" mr="555" value="${dateTo}">
					</div>
					<button href="" class="btn positive mr10">SEARCH</button>
					<a href="" class="btn netral print"><i class="icon-grey icon-print"></i>PRINT</a>
				</div>
				<h1>LAPORAN</h1>
			</div>
		</form:form>
		<!-- header info -->
		
		<!-- table -->
		<div class="tableofreport">
			<div class="fl">
				<div class="title">LAPORAN ASET</div>
				<div class="fl">
					<p>Jumlah Jenis Barang</p>
					<p>Jumlah Barang Kosong</p>
					<p>Total Aset</p>
					<p>Total Unit Barang</p>
					<c:forEach var="warehouse" items="${warehouse_report}">
						<p >${warehouse.name}</p>
					</c:forEach>
				</div>
				<div class="fr text-right">
					<p class="price">${catalog_jenis_count == null ? 0 : catalog_jenis_count}</p>
					<p class="price">${catalog_out_of_stock_jenis == null ? 0 : catalog_out_of_stock_jenis}</p>
					<p class="price">${catalog_asset_total == null ? 0 : catalog_asset_total}</p>
					<p class="price">${catalog_count_total == null ? 0 : catalog_count_total}</p>
					
					<c:forEach var="warehouse" items="${warehouse_report}">
						<p class="price">${warehouse.total}</p>
					</c:forEach>
				</div>
				<div class="clear"></div>
			</div>
			<div class="fl">
				<div class="title">LAPORAN JUAL BELI</div>
				<div class="fl">
					<p>Total Penjualan</p>
					<p>Belum Diproses</p>
					<p>Jumlah Unit Barang</p>
					<p>Peningkatan</p>
					<p>&nbsp;</p>
					<p>Total Pembelian</p>
					<p>Jumlah Unit Barang</p>
					<p>Peningkatan</p>
				</div>
				<div class="fr">
					<p class="price">${invoice_total_sales == null ? 0 : invoice_total_sales}</p>
					<p class="price">${invoice_total_pending_sales == null ? 0 : invoice_total_pending_sales}</p>
					<p class="price">${invoice_total_item == null ? invoice_total_item : invoice_total_item}</p>
					<p>${invoice_persentase == null ? 0 : invoice_persentase}</p>
					<p>&nbsp;</p>
					<p  class="price">${bill_total_sales == null ? 0 : bill_total_sales}</p>
					<p  class="price">${bill_total_item == null ? 0 : bill_total_item}</p>
					<p >${bill_persentase == null ? 0 : bill_persentase}</p>
				</div>
				<div class="clear"></div>
			</div>
			<div class="fl">
				<div class="title">HUTANG/PIUTANG</div>
				<div class="fl">
					<p>Total Hutang</p>
					<p>Bulan Ini</p>
					<p>30+</p>
					<p>60+</p>
					<p>&nbsp;</p>
					<p>Total Piutang</p>
					<p>Bulan Ini</p>
					<p>30+</p>
					<p>60+</p>
				</div>
				<div class="fr">
					<p class="price">${total_utang_all 	  == null ? 0 : total_utang_all}</p>
					<p class="price">${total_utang_at_month == null ? 0 : total_utang_at_month}</p>
					<p class="price">${total_utang_30_month == null ? 0 : total_utang_30_month}</p>
					<p class="price">${total_utang_60_month == null ? 0 : total_utang_60_month}</p>
					<p>&nbsp;</p>
					<p class="price">${total_piutang_all      == null ? 0 : total_piutang_all}</p>
					<p class="price">${total_piutang_at_month == null ? 0 : total_piutang_at_month}</p>
					<p class="price">${total_piutang_30_month == null ? 0 : total_piutang_30_month}</p>
					<p class="price">${total_piutang_60_month == null ? 0 : total_piutang_60_month}</p>
				</div>
				<div class="clear"></div>
			</div>
			<div class="fl">
				<div class="title">LAIN-LAIN</div>
				<div class="fl">
					<p>Jumlah Staff</p>
					<p>Jumlah Salesperson</p>
					<p>Penjualan per Orang</p>
					<p>Top Salesperson</p>
					<p>&nbsp;</p>
					<p>Jumlah Retur</p>
					<p>Jumlah Unit Retur</p>
				</div>
				<div class="fr">
					<p>${staff_total_count}</p>
					<p>${salesperson_total_count}</p>
					<p class="price-decimal">${salesperson_invoice_avg}</p>
					<p class="price">${top_salesperson}</p>
					<p>&nbsp;</p>
					<p class="price">${ret_total_sales}</p>
					<p class="price">${ret_total_item}</p>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
		</div>
		<table>
			<colgroup>
			<col style="width:140px"></col>
			<col style="width:auto"></col>
			<col style="width:90px;"></col>
			<col style="width:160px;"></col>
			<col style="width:90px"></col>
			<col style="width:100px;"></col>
			</colgroup>
			<thead>
				<tr>
					<td>NOTA JUAL</td>
					<td>PELANGGAN</td>
					<td align="right">ITEM</td>
					<td align="right">JUMLAH</td>
					<td>TGL</td>
					<td>STATUS</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="invoice" items="${invoice_list}">
				<tr>
					<td class="number"><a class="upfont" href="">${invoice.refNumber}</a></td>
					<td>
						<a href="">${invoice.customerName}</a>
						<br>
						<span>${invoice.customerCity}</span>
					</td>
					<td class="number" align="right">${invoice.totalItem}</td>
					<td class="number price" align="right">${invoice.totalAmount}</td>
					<td>12/12/12</td>
					<td>
						<c:choose>
							<c:when test="${invoice.status == 'Tahan'}">
								<div class="status hold">
									${invoice.status}
								</div>
							</c:when>
							<c:when test="${invoice.status == 'Aktif'}">
								<div class="status hold active">
									${invoice.status}
								</div>
							</c:when>
							<c:when test="${invoice.status == 'Lunas'}">
								<div class="status done completed">
									${invoice.status}
								</div>
							</c:when>
							<c:when test="${invoice.status == 'Batal'}">
								<div class="status cancel cancelled">
									${invoice.status}
								</div>
							</c:when>
							<c:otherwise>
								<div class="status hold">
									${invoice.status}
								</div>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<br/>
		<br/>
		<!-- bill section-->
		<table>
			<colgroup>
			<col style="width:140px"></col>
			<col style="width:auto"></col>
			<col style="width:90px;"></col>
			<col style="width:160px;"></col>
			<col style="width:90px"></col>
			<col style="width:100px;"></col>
			</colgroup>
			<thead>
				<tr>
					<td>NOTA BELI</td>
					<td>PELANGGAN</td>
					<td align="right">ITEM</td>
					<td align="right">JUMLAH</td>
					<td>TGL</td>
					<td>STATUS</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="bill" items="${bill_list}">
				<tr>
					<td class="number"><a class="upfont" href="">${bill.refNumber}</a></td>
					<td>
						<a href="">${bill.customerName}</a>
						<br>
						<span>${bill.customerCity}</span>
					</td>
					<td class="number" align="right">${bill.totalItem}</td>
					<td class="number price" align="right">${bill.totalAmount}</td>
					<td>12/12/12</td>
					<td>
						<c:choose>
							<c:when test="${bill.status == 'Tahan'}">
								<div class="status hold">
									${bill.status}
								</div>
							</c:when>
							<c:when test="${bill.status == 'Aktif'}">
								<div class="status hold active">
									${bill.status}
								</div>
							</c:when>
							<c:when test="${bill.status == 'Lunas'}">
								<div class="status done completed">
									${bill.status}
								</div>
							</c:when>
							<c:when test="${bill.status == 'Batal'}">
								<div class="status cancel cancelled">
									${bill.status}
								</div>
							</c:when>
							<c:otherwise>
								<div class="status hold">
									${bill.status}
								</div>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- endof content report -->
</div>