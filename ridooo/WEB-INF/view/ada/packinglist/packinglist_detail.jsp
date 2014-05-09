<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:url var="packinglist_create_url" value="/sales/packing-list/create/" />
<c:url var="packinglist_url" value="/sales/packing-list/" />
<c:url var="salesorder_url" value="/sales/sales-orders" />

<c:url var="baseURL" value="/sales/sales-orders"/>
<c:url var="URL" value="/"/>
<c:set var="onServer" value="0"/>

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/"/>
<c:set var="packinglist_create_url" value="/sales/packing-list/create/" />
<c:set var="packinglist_url" value="/sales/packing-list/" />
<c:set var="salesorder_url" value="/sales/sales-orders" />
</c:if>

<c:set var="totalItem" value="0"/>
<c:set var="totalWeight" value="0"/>
<div class="content">
	<form method="POST" action="${packinglist_create_url}${id_so}/${box}">
		<div class="main-content withright">
			<!-- heading tittle -->
			<h1>detail packing list (&nbsp;${data.refNumber}&nbsp;)</h1>
			<!-- header info -->
			<div class="header_info">
			</div>
			<!-- table -->
			<c:forEach items="${data.result.boxItem}" var="box_item" varStatus="pointer">
			
			<c:if test="${pointer.index > 1}">
				<br>
			</c:if>
			<c:if test="${pointer.index > 0}">
				<h3>BOX KE ${box_item.box_number}</h3>
				<table class="free-box"  >
					<colgroup>
						<col style="width: 60px; text-align: right;"></col>
						<col style="width: 60px"></col>
						<col style="width: 90px"></col>
						<col style="width: auto"></col>
						<col style="width: 40px"></col>
					</colgroup>
					<thead>
						<tr>
							<td align="right">QTY</td>
							<td>UNIT</td>
							<td>SKU</td>
							<td>ITEM</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${box_item.details}" var="salesOrder" >
							<tr id="first"	style="background-color: #F6F6F6; z-index: 1">
								<td align="right" class="borderright">
									<p class="number">${salesOrder.quantity}</p>
									<input type="hidden" name="qty" value="${salesOrder.quantity}">
									<input type="hidden" name="detail" value="${salesOrder.id}">
									
									<c:set var="totalItem" value="${totalItem+salesOrder.quantity}"/>
									<c:set var="totalWeight" value="${totalWeight+salesOrder.item.weight}"/>
								</td>
								<td>
									${salesOrder.unit == 1 ? "Biji" : salesOrder.unit}
									<input type="hidden" name="unit" value="${salesOrder.unit}">
								</td>
								<td>${salesOrder.sku}
									<input type="hidden" name="sku" value="${salesOrder.sku}"></td>
								<td>${salesOrder.item.vinCatalogs.title}
									<input type="hidden" name="item" value="${salesOrder.item.id}"></td></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</c:if>
			</c:forEach>
			
		</div>
		<div class="rightheader">
			<div class="btn-area">
<!-- 				<button class="btn positive"><i class="icon-file  icon-white"></i>SIMPAN</button> -->
				<a href="${salesorder_url}" class="btn netral close"><i class="icon-cancel icon-grey"></i>KEMBALI</a>
			</div>
			<div class="totalbar ">
				${totalWeight} <small>kg</small> ${totalItem} <small>items</small>
			</div>
			<div class="form">
				<div class="field">
					<label>Nota Pesan:</label>
					<input type="text" class="fr normal " value="${data.refNumber}" readonly/>
				</div>
				<div class="clear"></div>
				<div class="inputholder">
					<label>Pelanggan:</label> <input type="text" class="medium text-right border-bottom" name="customerName" value="${data.customerName}" readonly><input type="hidden"
					 name="none" value="">
					<textarea class="long" readonly>${data.customerAddress}</textarea>
				</div>
			</div>
		</div>
	</form>
	<div class="clear"></div>
	<div style="display: none">
		<div class="popup alert" id="popup"  style="width: 225px !important">
			<div class="head_title">
				MASUKKAN QUANTITY <a href="javascript:void(0);" class="fr close-popup"><i
					class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
			
				<div class="field-content">
					<center>
						<input id="split-item" placeholder="Masukkan qty" type="text" class="input-small required text-center" minlength="5">
						<!-- <p class="help-block">Masukkan banyak item yang ining di split</p> -->
					</center>
				</div>
				<br />
				<center>
					<a href="javascript:void(0)" class="btn positive" id="change-item"  >Split Item</a>
				</center>
			</div>
		</div>
	</div>
</div>