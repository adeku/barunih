<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:url var="packinglist_create" value="/sales/packing-list/create/" />
<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/"/>
<c:set var="packinglist_create" value="/sales/packing-list/create/" />
</c:if>

<div class="content">
	<form method="POST" action="${packinglist_create}${id_so}">
		<div class="main-content withright">
			<!-- heading tittle -->
			<h1>create packing list</h1>
			<!-- header info -->
			<div class="header_info">
				<div class="fl" >
					<div class="packingname">SO-A25</div>
					<div class="line"></div>
					<ul class="packnumber" style="position:relative;z-index:9">
						<li><a href="">1</a><input type="hidden" name="packnumber[]" value="1"></li>
						<li><a href="javascript:void(0);" id="add-pack">+</a></li>
					</ul>
				</div>
				<div class="fr">
					<span>Masuk ke box <span id="box">1</span>. <span id="item-left">12</span> item tersisa.</span>
				</div>
				<div class="clear"></div>
			</div>
			<!-- table -->
			<table class="free-box">
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
						<td>&nbsp;</td>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${data.exist == true}">
							<c:forEach items="${data.freeItem.details}" var="salesOrder">
								<tr id="first"	style="background-color: #F6F6F6; z-index: 1">
									<td align="right" class="borderright">
										<p class="number">${salesOrder.quantity}</p>
										<input type="hidden" name="qty[]" value="${salesOrder.quantity}">
									</td>
									<td>
										<span>[${salesOrder.unit}]</span>
										<input type="hidden" name="unit[]" value="${salesOrder.unit}">
									</td>
									<td>${salesOrder.sku}
										<input type="hidden" name="sku[]" value="${salesOrder.sku}"></td>
									<td>${salesOrder.item.vinCatalogs.title}
										<input type="hidden" name="item[]" value="${salesOrder.item.id}"></td></td>
									<td><a href="#"><i class="icon-justify"></i></a></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach items="${data.itemResult}" var="salesOrder">
								<tr id="first"	style="background-color: #F6F6F6; z-index: 1">
									<td align="right" class="borderright">
										<p class="number">${salesOrder.quantity}</p>
										<input type="hidden" name="qty[]" value="${salesOrder.quantity}">
									</td>
									<td>
										<span>[${salesOrder.unit}]</span>
										<input type="hidden" name="unit[]" value="${salesOrder.unit}">
									</td>
									<td>${salesOrder.sku}
										<input type="hidden" name="sku[]" value="${salesOrder.sku}"></td>
									<td>${salesOrder.itemName}
										<input type="hidden" name="item[]" value="${salesOrder.itemId}"></td></td>
									<td><a href="#"><i class="icon-justify"></i></a></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<br/>
			<br/>
			<table class="current-box">
				<colgroup>
					<col style="width: 60px; text-align: right;"></col>
					<col style="width: 60px"></col>
					<col style="width: 90px"></col>
					<col style="width: auto"></col>
					<col style="width: 40px"></col>
				</colgroup>
				<thead>
					<tr >
						<td align="right">QTY</td>
						<td>UNIT</td>
						<td>SKU</td>
						<td>ITEM</td>
						<td>&nbsp;</td>
					</tr>
				</thead>
				<tbody>
					<!-- <tr id="first">
						<td align="right" class="borderright"><p class="number">48</p></td>
						<td><span>Biji</span></td>
						<td>PHX-1029</td>
						<td>PROHEX Phillips Screwdriver 8mm</td>
						<td><i class="icon-close"></i></td>
					</tr> -->
					<c:choose>
						<c:when test="${data.exist == true}">
							<c:forEach items="${data.currentItem.details}" var="salesOrder">
								<tr id="first"	style="background-color: #F6F6F6; z-index: 1">
									<td align="right" class="borderright">
										<p class="number">${salesOrder.quantity}</p>
										<input type="hidden" name="qty[]" value="${salesOrder.quantity}">
									</td>
									<td>
										<span>[${salesOrder.unit}]</span>
										<input type="hidden" name="unit[]" value="${salesOrder.unit}">
									</td>
									<td>${salesOrder.sku}
										<input type="hidden" name="sku[]" value="${salesOrder.sku}"></td>
									<td>${salesOrder.item.vinCatalogs.title}
										<input type="hidden" name="item[]" value="${salesOrder.item.id}"></td></td>
									<td><a href="#"><i class="icon-close"></i></a></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
								<tr id="first"  class="drag-helper">
									<td align="center" colspan="5"><p >Drag Item Yang Tersedia Kesini</p></td>
								</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<div class="rightheader">
			<div class="btn-area">
				<button class="btn positive"><i class="icon-file"></i>SIMPAN</button>
				<a href="" class="btn negative close"><i class="icon-close" style="margin-right:-3px !important;"></i>&nbsp;</a>
			</div>
			<div class="totalbar ">
				235<small>kg</small> 132<small>items</small>
			</div>
			<div class="form">
				<label>Nota Pesan:</label>
				<div class="inputholder fr">
					<input type="text" class="short" value="SO-A25" /><i
						class="icon-close"></i>
				</div>
				<div class="clear"></div>
				<div class="inputholder">
					<label>Pelanggan:</label> <input type="text" id="customer"	name="customer" class=" text-right border-bottom" value="Customer ABC" />
					<textarea class="long">Pandegiling 209 Surabaya, Indonesia</textarea>
				</div>
			</div>
		</div>
	</form>
	<div class="clear"></div>
</div>