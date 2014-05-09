<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/>
<c:url var="ajaxData" value="/inventory/categories/ajax-data-categories/"/>
<c:url var="jsFile" value="/js/pagescroll.js"/>
<c:if test="${actionview=='detail'}">
	<c:url var="ajaxData" value="/inventory/catalogs/ajax-data-catalogs-incategories/${id}"/>
	<c:url var="jsFile" value="/js/pagescroll1.js"/>
</c:if>


<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/"/>
<c:set var="ajaxData" value="/inventory/categories/ajax-data-categories"/>
<c:set var="jsFile" value="/js/pagescroll.js"/>
<c:if test="${actionview=='detail'}">
	<c:set var="ajaxData" value="/inventory/catalogs/ajax-data-catalogs-incategories/${id}"/>
	<c:set var="jsFile" value="/js/pagescroll1.js"/>
</c:if>
</c:if>

<c:if test="${!empty limitDataPage}">
<script type="text/javascript">
<c:if test="${actionview=='detail'}">
var urlLoadingImage = '${baseURL}';
</c:if>
var urlAjax = '${ajaxData}';
var nop = ${limitDataPage};
</script>
<script src="${jsFile}" type="text/javascript"></script>
</c:if>	

<script type="text/javascript">
	$(document).ready(function() {
		$('#btnSimpan').on('click',function(e){
			e.preventDefault();
			$('#formIN').submit();
		  });
		
		$('#tableValue').bind('click', function(event) {
			var elementHere = event.target;
			if (elementHere.getAttribute('class')!=null) {
			if (elementHere.getAttribute('class').indexOf("btHapus1")!=-1) {
				elementHere.blur();
				event.preventDefault();	
				$.colorbox({
					inline : true,
					height:161,
					href : "#popup"
				});
				$('#btYesHapus1').attr('delID',elementHere.getAttribute('id'));
				
				<c:if test="${actionview=='detail'}">
			} else if (elementHere.getAttribute('class').indexOf("showDetailCatalog")!=-1) {
				event.preventDefault();	
				$.post("${baseURL}inventory/catalogs/detail1data/"+elementHere.getAttribute('id'),
						function(result) {
					$.colorbox({
						inline : true,
						height:300,
						
						href : "#datalogDetailHere"
					});	
					var inJson = jQuery.parseJSON(result);
					$('#dDATaProduk').text(inJson.name);
					$('#pSKU').text(inJson.sku);
					$('#pBrandName').text(inJson.brandName);
					$('#pcategoryID').text(inJson.taxonomyName);
					$('#pdescriptionID').text(inJson.description);
					$('#ponhandID').text(inJson.onhandqty);
					$('#pSiapPesan').text(inJson.onsiappesanqty);
					$('#pmsRP').text(inJson.msrp);
					if (inJson.imgNAme.length>0)
						$('#detailImage').html("<img src=\"${baseURL}catalog/"+inJson.imgNAme+"\"/>");
					else
						$('#detailImage').html("<img src=\"${baseURL}img/companies.png\"/>");
					
					str = inJson.priceList;
					htmlInside = "";
					$('#pPriceList').html(htmlInside);
					$.each(str, function() {
						htmlInside+="<div class=\"field\"><label>"+this['pricename']+"</label><label class=\"fr\">"+this['priceNumber']+"</label></div>";
					});
					$('#pPriceList').html(htmlInside);
					
					str = inJson.warehouseList;
					htmlInside = "";
					$('#pWarehouseList').html(htmlInside);
					$.each(str, function() {
						htmlInside+="<div class=\"field\"><label>"+this['companyname']+"</label><label class=\"fr\">"+this['countItem']+"</label></div>";
					});
					$('#pWarehouseList').html(htmlInside);
				});
				</c:if>
				
			}
		}
		 });	
		$('.btNotHapus').on('click',function(e){
			e.preventDefault();
			$.colorbox.close();
		 });
		$('#btYesHapus1').on('click',function(e){
			e.preventDefault();
			$.colorbox.close();
			$('#frList').attr('action',"${baseURL}inventory/categories/"+$('#btYesHapus1').attr('delID')+"/delete");
			$('#frList').submit();
		 });
		<c:if test="${!empty limitDataPage&&canCreate&&empty actionview}">
		$("body").keydown(function(e){
			if(e.keyCode == 116){
				e.preventDefault();				
			     window.location.href = $('#newData').attr('href');
			}
		});
		</c:if>
		
		<c:if test="${!empty action}">
		$("body").keydown(function(e){
			if(e.keyCode == 112){
				e.preventDefault();				
			    $('#btnSimpan').trigger('click');
			}
		});
		</c:if>
		$('#name').focus();
	});
</script>