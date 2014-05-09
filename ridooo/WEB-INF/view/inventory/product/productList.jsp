<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="productsUrl" value="/inventory/products" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content vrs-invenprodux">
				<div class="content-header">
					<div class="btn-group dropdown-large fr">
						<c:url var="addProduct" value="/inventory/products/create" />
						<a class="btn" href="${addProduct}">Add Product</a>
						<button class="btn btn-large dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
						<ul class="dropdown-menu pull-right">
							<!-- dropdown menu links -->
							<c:url var="addBundle" value="/products/createbundle" />
							<li class="align-left"><a href="${addBundle}">Add Bundle</a></li>
						</ul>
					</div>

					<h1>Products</h1>
					<p class="subtitle">Count the number of all existing products</p>
				</div>
				
				<c:choose>
						<c:when test="${empty products}">
							<div class="empty-state book">
								<h2>Products empty</h2>
							</div>
						</c:when>
						<c:otherwise>
					<div class="vrs-invenprodux-sr">
						<div class="btn-group dropdown dropdown-large fr">
							<button class="btn dropdown-toggle" data-toggle="dropdown">All Brands<span class="vrs-invenprodux-cr caret"></span></button>
							<ul class="dropdown-menu">
								<c:forEach items="${brands}" var="brand">
									<li><a href="#">${brand.brandName}</a></li>
								</c:forEach>
							</ul>
						</div>
						<div class="btn-group dropdown dropdown-large fr">
							<button class="btn dropdown-toggle" data-toggle="dropdown">All Items<span class="vrs-invenprodux-cr caret"></span></button>
							<ul class="dropdown-menu">
								<li><a href="#">In Stock</a></li>
								<li><a href="#">Low Stock</a></li>
								<li><a href="#">Out of Stock</a></li>
							</ul>
						</div>
					</div>				
					<table class="table table-striped">
							
						<colgroup>
							<col>
							<col class="w130">
							<col class="w100">
						</colgroup>
						
						<thead>
							<tr>
								<th>Item</th>
								<th>Stock</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach items="${products}" var="product">
								<tr>
									<td>
									<c:url var="logoProduct" value="/img/avatar.jpeg" />
						
										<span class="avatar fl"><img src="<c:choose>
					<c:when test="${empty product.imageProduct}">
					${logoProduct}
					</c:when>
					<c:otherwise >
					 ${product.imageProduct}
					</c:otherwise>
					</c:choose>" width="50" height="50" alt="Avatar"></span>
										<span class="avatar-pull">
											<h4><a href="${productsUrl}/${product.id}/detail">${product.title }</a></h4>
											<span><a href="#" class="alt-link">${product.brand}</a> - ${product.sku}</span>
										</span>
										
									</td>
									<td>
										<c:choose>
											<c:when test="${!empty product.stock}">
												<span class="fl amount">
													${product.stock}
												</span>
											</c:when>
											<c:otherwise>
												<span class="label label-important">Out of Stock</span>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<div class="btn-group dropdown fr">
											<a href="${productsUrl}/${product.id}/edit" class="btn btn-mini">Edit</a>
											<button class="btn dropdown-toggle" data-toggle="dropdown">
												<span class="caret"></span>
											</button>
											<ul class="vrs-invenprodux-de dropdown-menu pull-left">
												<li><a href="#">Add Variants</a></li>
												<li><a href="#">Disable</a></li>
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
				</c:otherwise>
			</c:choose>
			</div>
		</div>
	</div>
</div>