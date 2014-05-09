<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="inventory" value="/inventory" />
<script>

function uploadThis(obj) {
	obj.trigger('click');
}

function uploadAccidental(obj,countITem) {
	checkeExtensionInAddProducts(obj.value,obj,countITem);
}

	$(document).ready(function(){
		var countLabel = ${additionalInfoLabelSize};
		var countCategories = ${categoriesSize};
		var data = "";
		$('#add_other').click(function(e){
			e.preventDefault();
			$('#vrs-prod-addlblbox').show();
			$(this).hide();
		});
		
		$('#vrs-prod-lbladd').click(function(e){
			e.preventDefault();
			var newLabel = $('#newLabel').val();
			$('#vrs-prod-addlblbox').hide();
			$(this).closest(".control-group").before('<div class="control-group">'+
					'<label class="control-label" for="additionalInformationValue'+countLabel+'">'+
						newLabel+
					'</label>'+
					'<input type="hidden" name="additionalInformationLabel['+countLabel+']" value="'+newLabel.toLowerCase().replace(/ /g,"_")+'"/>'+
					'<div class="controls">'+
						'<input type="text" id="additionalInformationValue'+countLabel+'" name="additionalInformationValue['+countLabel+']" class="input-small"/>'+
					'</div>'+
				'</div>');
			countLabel++;
			$('#newLabel').val('');
			$('#add_other').show();
		});
		
		$('#vrs-prod-lblback').click(function(e){
			e.preventDefault();
			$('#add_other').show();
			$(this).closest(".control-group").hide();
		});
		
		
		$('#addOthercat').click(function(e){
			e.preventDefault();
			$('#vrs-prod-catadd').show();
			$(this).hide();
		});
		
		$('#vrs-prod-cataddbtn').click(function(e){
			e.preventDefault();
			$('#vrs-prod-catadd').hide();
			var title = $('#newCategory').val();
			$.ajax({
				type : "POST",
				url : "${inventory}/addcategory",
				data:{
					title: $('#newCategory').val()
				}
			})
			.done(
				function(resPonseText) {
					data = jQuery.parseJSON(resPonseText);
					
					if(data.message == ""){
						$('#vrs-prod-catadd').closest(".control-group").before('<div class="control-group vrs-invenproduct-cbox">'+
							'<label>'+
								'<input type="checkbox" id="categories'+countCategories+'" name="categories['+countCategories+']" value="'+data.id+'">'+
								title+
							'</label>'+
						'</div>');
						countCategories++;
					}
					
			});
			$('#newCategory').val('');
			$('#addOthercat').show();
		});
		
		$('#vrs-prod-catback').click(function(e){
			e.preventDefault();
			$('#addOthercat').show();
			$(this).closest(".control-group").hide();
		});
		$('#other_brand')
		.click(
				function(e) {
					e.preventDefault();
					$('.brand select').remove();
					$('.brand')
							.prepend(
									'<input type="hidden" name="brand" value="-1"><input type="text" id="brand1" name="brand1" class="input-medium">');
					$('#other_brand').remove();
				});
	});
</script>

<c:url var="images" value="/img" />
<!-- CONTENT -->
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<c:url var="products" value="/inventory/products" />
		<form:form modelAttribute="productForm" action="${products}/${itemId}/edit" method="POST" class="form-horizontal">
			<form:hidden path="type" value="Single" />
			<div class="grid-full">
				<div class="grid-content vrs-invenproduct">
					<div class="combined-grid">
						<div class="content-header">
							<h1>Edit Products</h1>
							<p class="subtitle">Edit a product</p>
						</div>
						<div class="fieldset">
							<div class="legend">Basic Information</div>
							<cform:input label="Title" path="title" clazz="input-medium" value="${productData.title}"/>

							<cform:input label="SKU" path="sku" clazz="input-very-small" value="${productData.sku}"/>
							<cform:select label="Brand" controlclass="brand" path="brand" options="${brands}" selected="${productData.brand}" appendhtml="<a href='javascript:void(0);' class='alt-link add-other' id='other_brand'>Add Other</a>"/>

							<cform:input label="Model No." path="modelNo" clazz="input-very-small" value="${productData.modelNo}" />
							
							<cform:select label="Account" path="coaId" options="${accounts}" selected="${productData.coaId}"/>

							<div class="control-group">
								<label>Description</label>
								<textarea class="input-xxlarge custom_editor"
									id="textarea_ckeditor" name="description">${productData.description}</textarea>
							</div>
						</div>
						
						<div class="fieldset">
							<div class="legend">Photos</div>
							<input type="hidden" name="mainImage" value="${defaultImage}">
							<form:hidden path="ProductListforDeleted"/>
							<ul class="vrs-invenproduct-up image">
							<c:forEach items="${imageList}" var="imageList1">
							${imageList1}
							</c:forEach>
								<li class="vrs-invenproduct-up-main" id="liupImg${liNext}">
									<img class="image avatar" src="${images}/no-person.png">
									<a href="javascript:void(0)" class="btn btn-positive" id="uploadImage1" onClick="uploadThis($('#upImg1'))">Upload</a>
									<input id="upImg1" name="upImg1"  type="file" style="display:none" onChange="uploadAccidental(this,${liNext})"/>
								</li>
							</ul>
							<div class="clear"></div>
						</div>


						<div class="fieldset vrs-invenproduct-addt">
							<div class="legend">Additional Information</div>
							<c:forEach items="${productData.additionalInformationLabel}" var="label" varStatus="status">
								<div class="control-group">
									<label class="control-label" for="additionalInformationValue${status.index}" style="text-transform: capitalize">${fn:replace(label, "_", " ")}</label>
									<form:hidden path="additionalInformationLabel[${status.index}]" value="${label}" />
									<div class="controls">
										<form:input path="additionalInformationValue[${status.index}]" class="input-small" value="${productData.additionalInformationValue[status.index]}"/>

									</div>
								</div>
							</c:forEach>

							<div id="vrs-prod-addlblbox" class="control-group">
								<label class="control-label" for="input01">Label</label>
								<div class="controls vrs-prod-lbladd">
									<input type="text" class="input-small" id="newLabel">
									<a id="vrs-prod-lbladd" href="#" class="btn btn-positive">Add</a>
									<a id="vrs-prod-lblback" href="#" class="btn">Cancel</a>
								</div>
							</div>

							<div class="control-group">
								<div class="controls">
									<a href="javascript:void(0);" class="btn alt-link add-other"
										id="add_other">Add More</a>
								</div>
							</div>
						</div>

						<div class="form-actions">
							<form:button class="btn btn-positive input-mini">Save</form:button>
							<c:url var="products" value="/inventory/products"></c:url>
							<a href="${products}" class="btn">Cancel</a>
						</div>
					</div>
					<div class="grid1 alt2">
						<div class="fieldset fieldset-cat">
							<div class="vrs-invenproduct-lbl legend">Categories</div>
							<c:if test="${!empty categories}">
								<c:forEach items="${categories}" var="category" varStatus="status">
									<div class="control-group vrs-invenproduct-cbox">
										<label>
											<c:set var="checked" value="false" />
											<c:forEach items="${productData.categories}" var="categoryData">
												<c:if test="${categoryData == category.key}">
													<c:set var="checked" value="true" />
												</c:if>
											</c:forEach>
											<c:choose>
												<c:when test="${checked == 'true'}">
													<input type="checkbox" id="categories${status.index}" name="categories[${status.index}]" value="${category.key}" checked="checked">
												</c:when>
												<c:otherwise>
													<input type="checkbox" id="categories${status.index}" name="categories[${status.index}]" value="${category.key}">
												</c:otherwise>
											</c:choose>
											${category.value}
										</label>
									</div>
								</c:forEach>
							</c:if>

							<div id="vrs-prod-catadd" class="control-group">
								<input type="text" class="input-very-small" id="newCategory">
								<a id="vrs-prod-cataddbtn" href="" class="btn btn-positive">Add</a>
								<a id="vrs-prod-catback" href="#" class="">x</a>
							</div>

							<div id="addOthercat" class="control-group vrs-invenproduct-cbox">
								<a href="#"><i class="vrs-invenproduct-bluplus"></i>New
									Category...</a>
							</div>
						</div>
						<div class="fieldset">
							<div class="vrs-invenproduct-lbl legend">Tags</div>
							<div class="control-group vrs-invenproduct-cbox">
								<textarea name="tags" rows="5">${productData.tags}</textarea>
								<p class="help-block">Enter a comma separated list of tags</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>