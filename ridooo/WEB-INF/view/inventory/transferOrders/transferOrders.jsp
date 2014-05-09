<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- CONTENT -->
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">

			<div class="grid-content vrs-invendelor vrs-inventrors">
				<form id="bpuForm" name="bpuForm" method="POST">
					<input type="hidden" name="idStatusSelected"
						value="${idStatusSelectedCheck}"> <input type="hidden"
						name="editIDTransferOrder"> <input type="hidden"
						name="editIDStatusChanged">
					<div class="content-header">
						<div class="helper">
							<c:url var="createTrasnferORder"
								value="/inventory/transfer-orders/create" />
								
							<a href="${createTrasnferORder}" class="btn fr  <c:if test="${!canCreate}">disabled </c:if>">Add Transfer
								Orders</a>
								
						</div>

						<h1>TRANSFER ORDERS</h1>
						<p class="subtitle">Manage items to be transfered between
							warehouses</p>
					</div>

					<div class="vrs-invendelor-sr">
						<div class="btn-group dropdown dropdown-large fr">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								<label id="vrs-do-mainlbl">${StatusSelectedCheck}</label><span
									class="vrs-invendelor-cr caret"></span>
							</button>
							<ul class="vrs-invendelor-ddr dropdown-menu">

								<c:forEach items="${statusOptionList}" var="statusOptionList1">
									<li><a href="javascript:void(0);"
										onClick="viewONlyThisStatus('${statusOptionList1.idStatus}')">${statusOptionList1.statusName}</a></li>
								</c:forEach>
							</ul>
						</div>
						<div class="field fl">
							<div class="input-append">
								<input type="text" name='dateFrom' class="datepicker"
									readonly="" onChange="filterByDateinDeliveryOrders();"
									value="${dateFrom}"><span class="add-on"><i
									class="icon-calendar"></i></span>
							</div>
						</div>
						<div class="field fl">
							<div class="input-append">
								<input type="text" name='dateTo' class="datepicker" readonly=""
									onChange="filterByDateinDeliveryOrders();" value="${dateTo}"><span
									class="add-on"><i class="icon-calendar"></i></span>
							</div>
						</div>
					</div>
					

					<table class="table table-striped">
						<colgroup>
							<col>
							<col class="w130">
							<col class="w130">
							<col class="w100">
							<col class="w100">
							<col class="w130">
						</colgroup>

						<thead>
							<tr>
								<th>Transaction</th>
								<th>Origin</th>
								<th>Destination</th>
								<th>Status</th>
								<th>Ship Date</th>
								<th></th>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${viewTransferOrders}"
								var="viewTransferOrders1">

								<tr>
									<td>
										<h4>
										<c:url  var="detailTransferORder" value="/inventory/transfer-orders/${viewTransferOrders1.transferORderView}/detail" />
											<a href="${detailTransferORder}">Transfer Order
												${viewTransferOrders1.trxNumber}</a>
										</h4>
									</td>
									<td class="vrs-invendelor-amts">
										<p class="vrs-font-thrteen">${viewTransferOrders1.origin}</p>
									</td>
									<td class="vrs-invendelor-amts">
										<p class="vrs-font-thrteen">${viewTransferOrders1.destination}</p>
									</td>
									<td class="vrs-invendelor-amts" id="status_${viewTransferOrders1.transferORderView}"><span
										class="label <c:choose>
												<c:when test="${viewTransferOrders1.status==-1}">label-important</c:when>
												<c:when test="${viewTransferOrders1.status==0}">label-info</c:when>
												<c:when test="${viewTransferOrders1.status==1}">label-process</c:when>
												<c:when test="${viewTransferOrders1.status==2}">label-process</c:when>
												<c:when test="${viewTransferOrders1.status==3}">label-warning</c:when>
												<c:when test="${viewTransferOrders1.status==4}">label-warning</c:when>
												<c:when test="${viewTransferOrders1.status==5}">label-warning</c:when>
												<c:when test="${viewTransferOrders1.status==6}">label-success</c:when>
											</c:choose>">
											<c:choose>
												<c:when test="${viewTransferOrders1.status==-1}">Cancelled</c:when>
												<c:when test="${viewTransferOrders1.status==0}">Pending</c:when>
												<c:when test="${viewTransferOrders1.status==1}">Waiting</c:when>
												<c:when test="${viewTransferOrders1.status==2}">In Process</c:when>
												<c:when test="${viewTransferOrders1.status==3}">Picked</c:when>
												<c:when test="${viewTransferOrders1.status==4}">Packed</c:when>
												<c:when test="${viewTransferOrders1.status==5}">Shipped</c:when>
												<c:when test="${viewTransferOrders1.status==6}">Delivered</c:when>
											</c:choose>
									</span></td>
									<td class="vrs-invendelor-amts">
										<p class="">${viewTransferOrders1.ship_date}</p>
									</td>
									<td class="vrs-invendelor-amts" id="btStatus_${viewTransferOrders1.transferORderView}"><c:if
											test="${viewTransferOrders1.status!=6&&viewTransferOrders1.status!=-1}">
											<div class="btn-group dropdown fr">
												<a href="javascript:void(0);" class="btn btn-65 align-left"
													<c:choose>
														<c:when test="${viewTransferOrders1.status==1}">
														onClick="changeStatusTransferORder(${viewTransferOrders1.transferORderView},6);" 
														</c:when>
													</c:choose>>
													<c:choose>
														<c:when test="${viewTransferOrders1.status==1}">Deliver</c:when>
													</c:choose>
												</a>
												<button class="btn dropdown-toggle" data-toggle="dropdown">
													<span class="caret"></span>
												</button>
												<ul class="vrs-invendelor-de dropdown-menu pull-left">
													<li><a href="javascript:void(0);"
														onClick="changeStatusTransferORder(${viewTransferOrders1.transferORderView},-1);">Cancel</a></li>
												</ul>
											</div>
										</c:if></td>
								</tr>
							</c:forEach>


						</tbody>
					</table>
				</form>
				${pagination}

			</div>

		</div>
		<div class="clear"></div>
	</div>
</div>
