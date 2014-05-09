<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="deliveryorder_edit" value="/sales/deliveryorder/edit" />
<c:url var="invoice_view_url" value="/sales/deliveryorder" />
<c:url var="base_url" value="/" />

<c:if test="${base_url=='//'}">
<c:set var="deliveryorder_edit" value="/sales/deliveryorder/edit" />
<c:set var="invoice_view_url" value="/sales/deliveryorder" />
</c:if>

<div class="content">

	<form class="deliveryorder" method="POST" action="${deliveryorder_edit}/${data.id}">
		<div class="main-content withright">
			<!-- heading tittle -->
			<h1>SURAT JALAN (${data.refNumber})</h1>
			<!-- header info -->
			<!-- table -->

			<input type="hidden" name="modified" value="1" />
			<br />
			<br />
			<table class="invoice_table">
				<colgroup>
					<col style="width: 60px; text-align: right;">
					<col style="width: 60px">
					<col style="width: 190px">
					<col style="width: auto">
					<col style="width: 125px">
					<col style="width: 80px">
				</colgroup>
				<thead>
					<tr>
						<td align="center">QTY</td>
						<td align="center">UNIT</td>
						<td>SKU</td>
						<td>ITEM</td>
						<td>NO DUS</td>
						<td>BERAT</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${data.deliveryorderDetails}" var="detail"
						varStatus="status">
						<tr id="first">
							<td align="right" class="borderright">
									<p class="number text-center">${detail.qty}</p>		
									<input type="hidden" name="do_detail_id[]"
										class="do_detail_id" id="qty-" value="${detail.id}">
									<input type="hidden" name="quantity[]" class="qty short" id="qty-" value="${detail.qty}" >
									<input type="hidden" name="itemId[]"  id="item-" value="${detail.item.id}" >
									<input type="hidden" name="weight[]" class="weight" value="${detail.item.weight}"/>
									<input type="hidden" name="unit[]" value="${detail.unit}"/>
							</td>
							<td class="item-container text-center">
								<p class="number text-center">${detail.unit}</p>
							</td>
							<td><div class="sku">${detail.sku}</div> <input type="hidden" name="sku[]" id="sku-${status.index}" value="${detail.sku}" /></td>
							<td class="product-container">${detail.item.vinCatalogs.title}</td>
							<td class="editable"><input type="text" name="boxNumber[]" class="medium box-no focus-tab" value="${detail.boxNumber}" target-action="create_new_row()" /></td>
							<td class="weight-column">${detail.item.weight}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="rightheader">
			<div class="btn-area">
				<button type="submit" class="btn positive">
					<i class="icon-file icon-white"></i>SIMPAN
				</button>
				<a href="${invoice_view_url}" class="btn netral"><i class="icon-grey icon-file" ></i>KEMBALI</a>
			</div>
			<div class="totalbar ">
				<span class="total-weight">${data.totalWeight}</span><small>kg</small> <span class="total-item">${data.totalItem}</span><small>items</small>
				<br>
				<div class="clear"></div>
			</div>
			<input type="hidden" name="total-weight" value="${data.totalWeight}">
			<input type="hidden" name="total-item" value="${data.totalItem}">
			<div class="form">
				<div class="field">
					<label>Nota Jual:</label>
					<input class="fr normal  text-right" type="text" name="salesorderLabel" value="${data.refNumber }" disabled>
					<input 	class="fr normal" type="hidden" name="salesorder" value="0">
				</div>
				<div class="field">
					<label>Kode Toko:</label>
					<input class="fr normal  text-right" type="text" name="salesorderLabel" value="${data.shopcode }" disabled>
					<input class="fr normal" type="hidden" name="salesorder" value="0">
				</div>
				<div class="clear"></div>
				<div class="inputholder">
				 <!-- name="customer" id="customer" -->
					<label>Pelanggan:</label> <input placeholder="Select customer..."  type="text"
						class="medium text-right border-bottom focus-tab validate validation" data-validation="required"  value="${data.customerName}" readonly/>
					<textarea class="long" name="cust-address" id="cust-address" readonly>${data.customerAddress}</textarea>
				</div>
				<div class="clear"></div>
				<div class="inputholder">
					<label>Ekspedisi:</label> 
					<input placeholder="Select customer..." name="courier" id="courier" type="text" class=" medium text-right border-bottom" />
					<textarea class="long"name="eks-address" id="eks-address" readonly>${data.courierAddress}</textarea>
				</div>
				<div class="field">
					<label>Biaya Asuransi:</label>
					<input class="fr semi-medium focus-tab text-right number prices number-filter" type="text" name="assurance" id="assurance" target-tab="#senddate" value="${data.assurance}">
				</div>
				<div class="field">
					<label>Tgl Kirim:</label>
					<div class="field-content fr">
						<div class="input-date input-append">
							<div class="input-icon">
								<i class="icon-calendar"></i>
							</div>
							<input type="text"  style="width: 67px" name="senddate" value="${data.deliveryDate}" class="validation" readonly/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>

	<div class="clear"></div>

	<div style="display: none">
		<div class="popup alert" id="popup">
			<div class="head_title">
				PERHATIAN<a class="fr"><i class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Anda harus memilih customer terlebih dahulu. Input customer
					berada pada sidebar sebelah kanan!!!</p>
				<br /> <a href="javascript:void(0)" class="btn positive"
					id="customer-goto" style="margin-left: 51px;">PILIH CUSTOMER</a>
			</div>
		</div>
	</div>
</div>