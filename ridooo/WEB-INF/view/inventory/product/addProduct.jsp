<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>

<c:url var="inventory" value="/inventory" />
<script>

function uploadThis(obj) {
	obj.trigger('click');
}

function uploadAccidental(obj,countITem) {
	checkeExtensionInAddProducts(obj.value,obj,countITem);
}

	$(document).ready(function(){
		
		var countLabel = 2;
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
		<form:form modelAttribute="productForm" action="${products}/create" method="POST" class="form-horizontal validate" enctype="multipart/form-data">
			<form:hidden path="type" value="Single" />
			<div class="grid-full">
				<div class="grid-content vrs-invenproduct">
					<div class="combined-grid">
						<div class="content-header">
							<h1>Add Products</h1>
							<p class="subtitle">Creating a new products</p>
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
								<form:textarea path="description" class="input-xxlarge custom_editor" value="${productData.description}" />
								
							</div>
						</div>

						<div class="fieldset">
							<div class="legend">Photos</div>
<input type="hidden" name="mainImage">
							<ul class="vrs-invenproduct-up image">
								<li class="vrs-invenproduct-up-main" id="liupImg1">
									<img class="image avatar" src="${images}/no-person.png">
									<a href="javascript:void(0)" class="btn btn-positive" id="uploadImage1" onClick="uploadThis($('#upImg1'))">Upload</a>
									<input id="upImg1" name="upImg1"  type="file" style="display:none" onChange="uploadAccidental(this,1)"/>
								</li>
							</ul>
							<div class="clear"></div>
						</div>

						<div class="fieldset vrs-invenproduct-addt">
							<div class="legend">Additional Information</div>
							<div class="control-group">
								<label class="control-label" for="additionalInformationValue0">Weight (kg)</label>
								<form:hidden path="additionalInformationLabel[0]" value="weight_(kg)" />
								<div class="controls">
									<form:input path="additionalInformationValue[0]" class="input-small"/>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="additionalInformationValue1">Color</label>
								<form:hidden path="additionalInformationLabel[1]" value="color" />
								<div class="controls">
									<form:input path="additionalInformationValue[1]" class="input-small"/>
								</div>
							</div>
							
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
											<input type="checkbox" id="categories${status.index}" name="categories[${status.index}]" value='${category.key}'>${category.value}
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
								<form:textarea path="tags" rows="5" />
								<p class="help-block">Enter a comma separated list of tags</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>