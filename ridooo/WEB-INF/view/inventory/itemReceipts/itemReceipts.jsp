<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- CONTENT -->
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
				<div class="content-header">
					<span class="fr">
						<c:url var="addBill" value="/inventory/item-receipts/create" />
						<a class="btn" href="${addBill}">Create Item Receipts</a>
					</span>
					<h1>Item Receipts</h1>
					<p class="subtitle">Item Receipts</p>
				</div>
				
				<c:if test="${!empty action}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Well done!</strong> You have successfully ${action} ${object}
					</div>
				</c:if>
				
				<c:if test="${!empty itemOrders}">
					<table class="table table-striped">
							
						<colgroup>
							<col>
							<col class="w130">
							<col class="w100">
						</colgroup>
						
						<thead>
							<tr>
								<th>Item Order</th>
								<th>DO No.</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach items="${itemOrders}" var="order">
								<tr>
									<td>
										<c:url var="itemReceipts" value="/inventory/item-receipts" />
										<h4><a href="${itemReceipts}/${order.id}/detail">Item Receipts ${order.id}</a></h4>
									</td>
									<td>
									<c:url var="toORderDetail" value="/inventory/delivery-orders/${order.id}/detail"/>
									<a href="${toORderDetail}">${order.ref_number}</a>
									</td>
									<td>
										<div class="btn-group dropdown fr">
										<c:url var="toEdit" value="/inventory/item-receipts/${order.id}/edit"/>
											<a href="${toEdit}" class="btn btn-mini">Edit</a>
											<button class="btn dropdown-toggle" data-toggle="dropdown">
												<span class="caret"></span>
											</button>
											<ul class="vrs-invenprodux-de dropdown-menu pull-left">
												<li>
													<a class="delete_popup" href="#" style="color: #333" id="4">Hapus</a>
												</li>
											</ul>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					${pagination}
				</c:if>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>
