<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="base_url" value="/" />
<c:url var="invoice_create" value="/sales/deliveryorder/create" />
<c:url var="invoice_view_url" value="/sales/deliveryorder" />

<c:set var="dp_view_url" value="/sales/deliveryorder" />
	<c:choose>
	<c:when test="${action.equals('create')}">
		<c:url var="do_create" value="/sales/deliveryorder/create" />
		<c:set var="save_label" value="SIMPAN" />
	</c:when>
	<c:otherwise>
		<c:url var="do_create" value="/sales/deliveryorder/generate-fake-detail" />
		<c:set var="save_label" value="GENERATE" />
	</c:otherwise>
</c:choose>
<div class="content">
	<form class="invoice_detail" method="POST" action="${do_create}">
		<div class="main-content withright">
			<!-- heading tittle -->
			<h1>SURAT JALAN</h1>
			<!-- header info -->
			<input type="hidden" name="modified" value="1" />
			<table class="invoice_table"  style="${empty data.transactionDetails ? 'display:none' : '' }">
				<colgroup>
					<col style="width: 60px; text-align: right;">
					<col style="width: 60px">
					<col style="width: 120px">
					<col style="width: auto">
					<col style="width: 125px">
					<col style="width: 80px">
					<col style="width: 40px">
				</colgroup>
				<thead>
					<tr>
						<td align="right">QTY</td>
						<td>UNIT</td>
						<td>SKU</td>
						<td>ITEM</td>
						<td>NO DUS</td>
						<td>BERAT</td>
						<td>&nbsp;</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${inputold.quantity}" var="qty" varStatus="pointer">
						<tr id="first">
							<td align="right" class="borderright editable"><p class="number">
								<input value="${qty}"  type="text" name="quantity[]" class="short qty  focus-tab" target-type="select" target-tab=".unit" target-tr="true" >
								<input type="hidden" name="weight[]" class="weight"/>
								</p></td>
							<td class="item-container"><span> <select
									class="select unit focus-tab "  target-type="input" target-tab=".box-no" target-tr="true"  name="unit[]" >
										<option value="1">Biji</option>
										<option value="48">[48]</option>
										<option value="46">[46]</option>
										<option value="44">[44]</option>
								</select>
							</span></td>
							<td><div class="sku">${inputold.sku[pointer.index]}</div>
								<input type="hidden" name="sku[]" value="${inputold.sku[pointer.index]}" />
							</td>
							<td class="product-container editable" >
								<input id="product-${pointer.index}" placeholder="Type product.." name="itemId[]" type="text" 
								class="product_name autocomplete" value="[{id:${inputold.itemId[pointer.index]}, name:'<c:out value="${inputold.productname[pointer.index]}"/>'}]" />
								<input type="hidden" name="productname[]" id="productname" value="<c:out value="${inputold.productname[pointer.index]}"/>" />
							</td>
							<td align="right" class="editable">
								<input value="${boxNumber}"  type="text" name="boxNumber[]" class="medium box-number focus-tab" target-action="create_new_row()" />
							</td>
							<td class="weight-column"></td>
							<td align="center"><a class="delete-row" href="#hapus"><i class="icon-close" ></i></a></td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="7"><a href="javascript:void('Tambah Barang')"
							class="add_item">Tambah Barang [F5]</a></td>
					</tr>
				</tfoot>
			</table>
			<div class="empty-state" style="${empty form.transactionDetails ? '' : 'display:none' }">
				<i class="empty-icon entry"></i>
				<h4>Silakan mulai dengan mengisi data pelanggan di sisi kanan.</h4>
			</div>
		</div>
		<div class="rightheader">
		<div class="btn-area">
			<button class="btn positive" id="save-button"><i class="icon-file icon-white"></i>${save_label}</button>
			<a href="" class="btn netral"><i class="icon-grey icon-file"></i>Kembali</a>
		</div>
		<div class="totalbar ">
			<span class="total-weight">0</span><small>kg</small> <span class="total-item">0</span><small>items</small> <br>
		</div>
		<div class="form" >
			<input type="hidden" name="total-weight" value="0" class="validation" data-validation="number" >
			<input type="hidden" name="total-item" value="0"  class="validation" data-validation="number" >
			<input type="hidden" name="total-price" value="0"  class="validation" data-validation="number" >
			<div class="field">
				<label>Nota Pesan:</label> <input class="fr normal" type="text text-center" name="salesorderLabel" value="Kosong" disabled><input class="fr normal" type="hidden" name="salesorder" value="0">
			</div>
			<div class="clear"></div>
			<div class="inputholder">
				<label>Pelanggan:</label> 
				<input placeholder="Select customer..." name="customer" id="customer" type="text" class=" medium text-right border-bottom validation" data-validation="required" />
				<input name="customerName" type="text" id="customerName"  value="${inputold.courierName[0]}"/>
				<textarea class="long" name="cust-address" id="cust-address" readonly></textarea>
			</div>
			<div class="clear"></div>
			<div class="inputholder">
				<label>Ekspedisi:</label> 
				<input placeholder="Select customer..." name="courier" id="courier" type="text" class=" medium text-right border-bottom" />
				<input  name="courierName" type="text" id="courierName" value="${inputold.courierName[0]}" />
				<textarea class="long" name="eks-address" id="eks-address" readonly></textarea>
			</div>
			<div class="field">
				<label>Biaya Asuransi:</label>
				<input class="fr semi-medium focus-tab text-right number prices number-filter" type="text" name="assurance" id="assurance" target-tab="#senddate" value="${data.assurance}">
			</div>
			<div class="field">
				<label >Tgl Kirim:</label>
				<div class="field-content fr">
					<div class="input-date input-append">
						<div class="input-icon"><i class="icon-calendar"></i></div>
						<input type="text" value="${inputold.senddate}" class="focus-tab datepicker validation" data-validation="required"  target-action="showTable(true)" name="senddate" id="senddate"/>
					</div>
				</div>
			</div>
		</div>
	</form>

	<div class="clear"></div>

	<div style="display: none">
		<div class="popup alert" id="popup">
			<div class="head_title">
				PERHATIAN<a href="javascript:void(0);" class="fr close-popup"><i class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Anda harus memilih customer terlebih dahulu. Input customer
					berada pada sidebar sebelah kanan!!!</p>
				<br /> 
				<a href="javascript:void(0)" class="btn positive" id="customer-goto" 	>PILIH CUSTOMER</a>
				<div class="clear"></div>
			</div>
		</div>
	</div>
</div>