<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- CONTENT -->
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content vrs-invendelor">
				<form id="bpuForm" name="bpuForm" method="POST">
				<input type="hidden" name="idStatusSelected" value="${idStatusSelectedCheck}">
					<div class="content-header">
						<h1>DELIVERY ORDER</h1>
						<p class="subtitle">Showing all delivery orders issued for
							customers</p>
					</div>

					<div class="vrs-invendelor-sr">
						<div class="btn-group dropdown dropdown-large fr">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								${StatusSelectedCheck}<span class="vrs-invendelor-cr caret"></span>
							</button>
							<ul class="vrs-invendelor-ddr dropdown-menu">
								<c:forEach items="${statusOptionList}" var="statusOptionList1">
									<li><a href="javascript:void(0);" onClick="viewONlyThisStatus('${statusOptionList1.idStatus}')">${statusOptionList1.statusName}</a></li>
								</c:forEach>
							</ul>
						</div>
						<div class="field fl">
							<div class="input-append">
								<input type="text" name='dateFrom'
									class="input-very-small datepicker" readonly=""
									onChange="makeSameDateToinDeliveryOrders(); filterByDateinDeliveryOrders();" value="${dateFrom}"><span
									class="add-on"><i class="icon-calendar"></i></span>
							</div>
						</div>
						<div class="field fl">
							<div class="input-append">
								<input type="text" name='dateTo'
									class="input-very-small datepicker" readonly=""
									onChange="filterByDateinDeliveryOrders();" value="${dateTo}"><span
									class="add-on"><i class="icon-calendar"></i></span>
							</div>
						</div>
					</div>


					<c:choose>
						<c:when test="${empty viewDeliveryOrders}">
						<div class="empty-state book">
								<h2>Delivery orders empty</h2>
							</div>
						</c:when>
						
						<c:otherwise>
					<table class="table table-striped">
						<colgroup>
							<col>
							<col class="w100">
							<col class="w100">
							<col class="w130">
						</colgroup>

						<thead>
							<tr>
								<th>Transaction</th>
								<th>Status</th>
								<th>Ship Date</th>
								<th></th>
							</tr>
						</thead>

						<tbody>

							<c:forEach items="${viewDeliveryOrders}"
								var="viewDeliveryOrders1">
								<tr>
									<td>
										<h4>
										<c:url var="deliveryOrderDetail" value="/inventory/delivery-orders/${viewDeliveryOrders1.orderID}/detail"/>
											<a href="${deliveryOrderDetail}">${viewDeliveryOrders1.orderView}</a>
										</h4> <span><a href="#" class="alt-link">${viewDeliveryOrders1.party_name}</a></span>
									</td>
									<td class="vrs-invendelor-amts" id="status_${viewDeliveryOrders1.orderID}"><span
										class="label <c:choose>
												<c:when test="${viewDeliveryOrders1.status==-1}">label-important</c:when>
												<c:when test="${viewDeliveryOrders1.status==0}">label-info</c:when>
												<c:when test="${viewDeliveryOrders1.status==1}">label-process</c:when>
												<c:when test="${viewDeliveryOrders1.status==2}">label-process</c:when>
												<c:when test="${viewDeliveryOrders1.status==3}">label-warning</c:when>
												<c:when test="${viewDeliveryOrders1.status==4}">label-warning</c:when>
												<c:when test="${viewDeliveryOrders1.status==5}">label-warning</c:when>
												<c:when test="${viewDeliveryOrders1.status==6}">label-success</c:when>
											</c:choose>">
											<c:choose>
												<c:when test="${viewDeliveryOrders1.status==-1}">Cancelled</c:when>
												<c:when test="${viewDeliveryOrders1.status==0}">Pending</c:when>
												<c:when test="${viewDeliveryOrders1.status==1}">Waiting</c:when>
												<c:when test="${viewDeliveryOrders1.status==2}">In Process</c:when>
												<c:when test="${viewDeliveryOrders1.status==3}">Picked</c:when>
												<c:when test="${viewDeliveryOrders1.status==4}">Packed</c:when>
												<c:when test="${viewDeliveryOrders1.status==5}">Shipped</c:when>
												<c:when test="${viewDeliveryOrders1.status==6}">Delivered</c:when>
											</c:choose>
									</span></td>
									<td class="vrs-invendelor-amts">
										<p class="font-sz14">${viewDeliveryOrders1.ship_date}</p>
									</td>
									<td class="vrs-invendelor-amts" id="btStatus_${viewDeliveryOrders1.orderID}"><c:if
											test="${viewDeliveryOrders1.status!=6 && viewDeliveryOrders1.status!=-1}">
											<div class="btn-group dropdown fr">
												<a href="javascript:void(0);" class="btn btn-65 align-left"
												<c:choose>
														<c:when test="${viewDeliveryOrders1.status==1}">
														onClick="changeStatusDeliveryORder(${viewDeliveryOrders1.orderID},6);" 
														</c:when>
													</c:choose>>
												<c:choose>
														<c:when test="${viewDeliveryOrders1.status==1}">Deliver</c:when>
													</c:choose></a>
												<button class="btn dropdown-toggle" data-toggle="dropdown">
													<span class="caret"></span>
												</button>
												<ul class="vrs-invendelor-de dropdown-menu pull-left">
													<li><a href="javascript:void(0);" onclick="changeStatusDeliveryORder(${viewDeliveryOrders1.orderID},-1)">Cancel</a></li>
												</ul>
											</div>
										</c:if>
									</td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</form>
				${pagination}
</c:otherwise>
</c:choose>
			</div>
			<!-- END : CONTENT AREA -->
		</div>
		<div class="clear"></div>
	</div>
</div>
