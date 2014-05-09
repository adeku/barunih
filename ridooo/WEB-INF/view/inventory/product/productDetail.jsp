<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="productsUrl" value="/inventory/products" />
<c:url var="imgUrl" value="/img" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container vrs-invenproduxt">		
		<div id="list" class="grid1 last">
			<div class="vrs-invenproduxt-boxr">
				<span class="vrs-invenproduxt-boxr-head">
					<label>MSRP</label>
					<label class="fr"><strong>${msrp}</strong></label>
				</span>
				<c:forEach items="${itemPricing}" var="pricing">
					<span class="vrs-invenproduxt-boxr-body">
						<label>${pricing.key}</label>
						<label class="fr amount"><strong>${pricing.value}</strong></label>
					</span>
				</c:forEach>
				
				
			</div>
			<c:choose>
				<c:when test="${!empty totalStock && totalStock != 0}">
					<div class="vrs-invenproduxt-boxr">
						<span class="vrs-invenproduxt-boxr-head">
							<label>In Stock</label>
							<label class="fr"><strong>${totalStock}</strong></label>
						</span>
						<c:forEach items="${stock}" var="st">
							<span class="vrs-invenproduxt-boxr-body">
								<label>${st.key}</label>
								<label class="fr"><strong>${st.value}</strong></label>
							</span>
						</c:forEach>
						
					</div>
				</c:when>
				<c:otherwise>
					<div class="vrs-invenproduxt-boxr">
						<span class="vrs-invenproduxt-boxr-head">
							<label><span class="label label-important">Out of Stock</span></label>
						</span>
						<c:forEach items="${stock.key}" var="key">
							<span class="vrs-invenproduxt-boxr-body">
								<label>${key}</label>
								<label class="fr"><strong>0</strong></label>
							</span>
						</c:forEach>
						
					</div>
				</c:otherwise>
			</c:choose>
			
			
		</div>
		
		<div class="grid2">
			<div class="grid1">
				<div class="vrs-invenproduxt-mainimg image align-center">
					<img src="${mainImage}" alt="dummy" id="imgView" />
				</div>
				<ul class="vrs-invenproduxt-dislist">
					<c:forEach items="${imageList}" var="imageList1"> 
						<li>
							<img class="image avatar avatar-lmini" src="${imageList1}" onmouseover="viewLArgeImageProduct(this.src)">
						</li>
					</c:forEach>
				</ul>
				
				<h4>Categories</h4>
				<ul>
					<c:if test="${!empty categories}">
						<c:forEach items="${categories}" var="category">
	  						<li>${category.title}</li>
	  					</c:forEach>
  					</c:if>
  				</ul>
				<h4>Tags</h4>
				<ul>
					<c:if test="${!empty tags}">
						<c:forEach items="${tags}" var="tag">
	  						<li>${tag.title}</li>
	  					</c:forEach>
  					</c:if>
				</ul>
			</div>
			<div class="grid-content">
      			<div class="content-header">						
      				<div class="btn-group dropdown dropdown-large fr">
      				<c:url var="editItemProducts" value="/inventory/products/${itemId}/edit" />
  						<a href="${editItemProducts}" class="btn">Edit</a>
  						<button class="btn btn-large dropdown-toggle" data-toggle="dropdown">
  							<span class="caret"></span>
  						</button>
  						<ul class="vrs-invenproduxt-ddr dropdown-menu pull-left">
  							<li><a href="#">Tambah variasi</a></li>
							<li><a href="#">Print</a></li>
							<li><a href="#">Disable</a></li>
  							<li><a href="#">Hapus</a></li>
  						</ul>
  					</div>
					<h1>${title}</h1>
      				<p class="subtitle"><a href="#">${brandName}</a> &mdash; ${sku}</p>
      			</div>
				<p>${description} <!-- <a href="#">More</a> --></p>
				
				<div class="form-horizontal">
					<c:forEach items="${additionalInfoLabel}" var="label" varStatus="status">
						<div class="control-group">
	     					<label class="control-label" for="input01">${label}</label>
	     					<div class="controls">
	     						<p class="data">${additionalInfoValue[status.index]}</p>
	     					</div>
	     				</div>
					</c:forEach>
					
					<h4 class="vrs-invenproduxt-optbl">Options</h4>
					<ul class="course nav nav-pills fr">
  							<li><a id="sets" href="#">Sets</a></li>
  							<li class="active">
							<a id="variants" href="#">Variants</a>
						</li>
  							<li><a id="bundles" href="#">Bundles</a></li>
  							<li><a id="upgrades" href="#">Upgrades</a></li>
  						</ul>
  					<c:choose>
  						<c:when test="${!empty variantsLabel}">
  							<table class="table table-striped">
	  							<colgroup>
	  								<col>
	  								<col class="w150">
	  								<col class="w150">
	  								<col class="w150">
	  							</colgroup>
		  						<thead>
	  								<tr>
	  									<th>SKU</th>
	  									<th>Color</th>
	  									<th>Material</th>
	  									<th>Weight (kg)</th>
	  								</tr>
	  							</thead>
	  							<tbody>
	  								<tr>
	  									<td>
	  										<h4>1234</h4>
	  									</td>
	  									<td>
	  										<p class="course homework text">Marble White</p>
	  									</td>
	  									<td>
	  										<p class="course homework">Alumunium</p>
	  									</td>
	  									<td>
	  										<p class="course homework">1.2</p>
	  									</td>
	  								</tr>
	  							</tbody>
	  						</table>
  						</c:when>
  						<c:otherwise>
  							<div class="empty-state no-cart no-instructor"><h2>No Item Found</h2></div>
  						</c:otherwise>
  					</c:choose>
				</div>
			</div>
			<!-- END : CONTENT AREA -->
		</div>
	</div>
	<div class="clear"></div>
</div>