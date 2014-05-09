<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- CONTENT -->
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">


				<div class="content-header">
				<div class="helper">
				<c:url var="createData" value="/inventory/brands/create" />
						</div>
					<h1>Brands</h1>
					<p class="subtitle">Brands Data</p>
				</div>
				<div>
					<c:choose>
						<c:when test="${empty viewDataList}">
							<div class="empty-state book">
								<h2>Brands empty</h2>
							</div>

						</c:when>
						<c:otherwise>
							<table class="table table-striped">
								<colgroup>
									<col>
									<col class="w300">
									<col class="w300">
									<col class="w300">
									<col class="w130">
								</colgroup>

								<thead>
									<tr>
										<th>Brands</th>
										<th>Number Item</th>
										<th>Products</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${viewDataList}" var="viewDataList1">
										<tr>
											<td>${viewDataList1.brand_name}</td>
											<td>${viewDataList1.number_item}</td>
											<td>${viewDataList1.product_title}</td>											
											<td>
												<div class="btn-group dropdown fr">
													<a href="#" class="btn show-extend">Edit</a>
													<button class="btn btn-65 dropdown-toggle"
														data-toggle="dropdown">
														<span class="caret"></span>
													</button>
													<ul class="dropdown-menu pull-left">
														<li><a href="javascript:void(0)">Disable</a></li>
														<li><a href="javascript:void(0)">Remove</a></li>
													</ul>
												</div>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
				</div>


			</div>

		</div>
		<div class="clear"></div>
	</div>
</div>
