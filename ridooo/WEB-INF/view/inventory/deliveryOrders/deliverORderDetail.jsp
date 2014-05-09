<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:url var="imageCiputra" value="/img/ciputralogo.png"></c:url>

<!-- CONTENT -->
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content vrs-invendelorv">
				<!-- PRINT START -->
				<div class="print-layout">
					<div class="print-head">
						<div class="print-title">
							<img alt="Ciputra Logo" src="${imageCiputra }" />
							<h1>${schoolName }</h1>
							<p>${schoolAddr }</p>
						</div>
						<div class="print-info">
							<h3>Delivery Order No. ${orderID}</h3>
							<p>${dateCreated}</p>
						</div>
						<div class="clear"></div>
					</div>

					<div class="print-content">
						<div class="customer-info">
							<h4>Customer:</h4>
							<p>
								${customerName}  (ID: ${customerID})
								<br />
								${streetAddress}
								<br />
								${city}, ${province}
							</p>
						</div>
						<%-- <div class="price-info">
							<div class="price-field">
								<label>Total Amount</label>
								<span class="price">${oForm.totalAmount}</span>
							</div>
							<div class="price-field">
								<label>Cashier</label>
								<span>${oForm.createdByName}</span>
							</div>
						</div> --%>
						<div class="clear"></div>
					</div>
				</div>
				<!-- PRINT END -->
					
				<div class="vrs-invenitemv-ch content-header">			
					<div class="helper">
						<a class="btn fr print"><i class="icon-print"></i>&nbsp; Print</a>
					</div>
					<h1>Delivery Order ${orderID}</h1>
					<p class="subtitle">${dateCreated}</p>
				</div>
				
				<div class="list list-horizontal span4 vrs-no-ml">
					<ul>
						<li>
							<p class="title">Customer</p>
							<c:url var="customerDetail" value="/people/customers/${customerID}/profile"/>
							<a href="${customerDetail}">${customerName}</a>
						</li>
						<li>
							<p class="title">Shipping Address</p>
							<p>${streetAddress}</p>
							<c:if test="${!empty streetAddress2}">${streetAddress2}</c:if>
							<p>${city}, ${province}</p>
						</li>
					</ul>
				</div>
				<div class="list list-horizontal span5 vrs-invendelorv-fr">
					<ul>
						<li>
							<p class="title">Warehouse</p>
							<p>${warehouseName}</p>
						</li>
						<li>
							<p class="title">Ship Date</p>
							<p>${shipDate}</p>
						</li>
						<li>
							<p class="title">Invoice No.</p>
							<a href="#">${invoiceID}</a>
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
					<c:forEach items="${itemList}" var="itemList1">
						<tr>
							<td>
								<h4>${itemList1.title}</h4>
								<span>${itemList1.brandName} &mdash; ${itemList1.sku}</span>
							</td>
      							<td class="align-right"><span class="score">${itemList1.quantity}</span></td>
      						</tr>
      					</c:forEach>
					</tbody>
				</table>
			</div>
		
			<!-- END : CONTENT AREA -->
		</div>
		<div class="clear"></div>
	</div>
</div>
