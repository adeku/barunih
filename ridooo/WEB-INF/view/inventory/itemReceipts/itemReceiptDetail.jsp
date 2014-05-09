<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url var="bills" value="/finance/bills" />
<c:url var="itemReceipt" value="/inventory/item-receipts" />
<div class="content">
			
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content vrs-invenitem">
				<div class="vrs-invenitemv-ch content-header">
					<div class="helper">
						<a href="${itemReceipt}/${id}/createbill" class="btn fr">Create Bill</a>
					</div>
				
					<h1>Item Receipts ${id}</h1>
					<p class="subtitle">${date}</p>
				</div>
				
				<div class="list list-horizontal span5 vrs-no-ml">
					<ul>
						<li>
							<p class="title">Vendor</p>
							<a href="#">${vendorName}</a>
						</li>
						<li>
							<p class="title">DO No.</p>
							<strong>${deliveryOrderNo}</strong>
						</li>
						<li>
							<p class="title">Bill No.</p>
							<a href="${bills}/${billId}/view">${billId}</a>
						</li>
					</ul>
				</div>
				<div class="list list-horizontal span3 vrs-invenitemv-fr">
					<ul>
						<li>
							<p class="title">Warehouse</p>
							<strong>${warehouseName}</strong>
						</li>
					</ul>
				</div>	
					
					
				 
				<table class="table table-striped">
					
					<colgroup>
						<col>
						<col class="score">
					</colgroup>
					
					<thead>
						<tr>
							<th>Item</th>
							<th class="align-right">QTY</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${items}" var="item">
							<tr class="vrs-invenitemv-tr">
								<td>
									<h4>${item.title}</h4>
									<span>${item.brand} &mdash; ${item.sku}</span>
								</td>
	      						<td class="align-right"><span class="score">${item.quantity}</span></td>
	      					</tr>
      					</c:forEach>
						<!-- <tr class="vrs-invenitemv-tr">
							<td>
								<h4>Laptop</h4>
								<span>Samsung &mdash; 1234</span>
							</td>
      							<td class="align-right"><span class="score">2</span></td>
      						</tr> -->
					</tbody>
				</table>
				
			</div>
			<!-- END : CONTENT AREA -->
		</div>
	</div>
	<div class="clear"></div>
	
</div>
