<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:url var="ajaxData" value="/inventory/catalogs/ajax-data-catalogs" />
<c:url var="jsFile" value="/js/pagescroll.js" />

<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/" />
	<c:set var="ajaxData" value="/inventory/catalogs/ajax-data-catalogs" />
	<c:set var="jsFile" value="/js/pagescroll.js" />
</c:if>

<c:if test="${!empty limitDataPage}">
	<script type="text/javascript">
var urlAjax = '${ajaxData}';
var nop = ${limitDataPage};
</script>
	<script src="${jsFile}" type="text/javascript"></script>
</c:if>

<script type="text/javascript">
function changeStatusAll(stat) {
	$('#dataList').attr('action',"${baseURL}inventory/catalogs/delete?status="+stat);
	$('#dataList').submit();
}

function changeStatus1(id,stat) {
	$('#frList').attr('action',"${baseURL}inventory/catalogs/status-change");
	$('#frList [name=id]').val(id);
	$('#frList [name=status]').val(stat);
	$('#frList').submit();
}

	$(document).ready(function() {
		
		$("#inline").colorbox({
			inline : true,
			href : "#popup"
		});				
		$('#ShowAddBrand').on('click',function(e){
			e.preventDefault();			
			$('#brandSElect').hide();
			$('#newBrand').val('true');
			$('#brandAdd').fadeIn('slow');			
		});
		$('#ShowselectBrand').on('click',function(e){
			e.preventDefault();
			$('#brand').val('');
			$('#brandAdd').hide();
			$('#newBrand').val('false');
			$('#brandSElect').fadeIn('slow');			
		});		
		$('#ShowAddKategori').on('click',function(e){
			e.preventDefault();
			$('#SelectKAtegori').hide();	
			$('#newKategori').val('true');
			$('#addKAtegori').fadeIn('slow');			
		});
		$('#ShowselectKAtegori').on('click',function(e){
			e.preventDefault();
			$('#kategori').val('');
			$('#addKAtegori').hide();	
			$('#newKategori').val('false');
			$('#SelectKAtegori').fadeIn('slow');			
		});
		$('#btnSimpan').on('click',function(e){
			e.preventDefault();
			$('#formIN').submit();
		  });		
		$('#btnHapusSelected').on('click',function(e){
			e.preventDefault();
			$(this).blur();
			if ($('input[name="idCatalogItem[]"]:checked').length>0) {
				$.colorbox({
					inline : true,
					height:161,
					href : "#popup"
				});
			} else {
				$.colorbox({
					inline : true,
					height:161,
					href : "#infoCannotDelete"
				});
			}
		 });
		$('.btNotHapus').on('click',function(e){
			e.preventDefault();
			$.colorbox.close();
		 });
		$('#btYesHapus').on('click',function(e){
			e.preventDefault();			
			$.colorbox.close();
			changeStatusAll(-1);
		 });
		
		$('#tableValue').bind('click', function(event) {
			var elementHere = event.target;
			if (elementHere.getAttribute('class')!=null) {
			if (elementHere.getAttribute('class').indexOf("btnHapus1")!=-1) {
				elementHere.blur();
				event.preventDefault();	
				$.colorbox({
					inline : true,
					height:161,
					href : "#askDelete1"
				});
				$('#btYesHapus1').attr('delID',elementHere.getAttribute('id'));
			}
		}
		 });
		
		$('.btNotHapus1').on('click',function(e){
			e.preventDefault();
			$.colorbox.close();
		 });
		$('#btYesHapus1').on('click',function(e){
			e.preventDefault();			
			$.colorbox.close();
			changeStatus1($(this).attr('delID'),-1);
		 });
		
		
		$('#tableValue').bind('click', function(event) {
			var elementHere = event.target;
			if (elementHere.getAttribute('class')!=null) {
			if (elementHere.getAttribute('class').indexOf("btnTahan1")!=-1) {
				event.preventDefault();	
				changeStatus1(elementHere.getAttribute('id'),0);
			} else if (elementHere.getAttribute('class').indexOf("btnAktif1")!=-1) {
				event.preventDefault();	
				changeStatus1(elementHere.getAttribute('id'),1);			
			} else if (elementHere.getAttribute('class').indexOf("showDetailCatalog")!=-1) {
				event.preventDefault();	
				
				$.post("${baseURL}inventory/catalogs/detail1data/"+elementHere.getAttribute('id'),
						function(result) {
					var inJson = jQuery.parseJSON(result);
					var heightPop = 300;
					
					$.colorbox({
						inline : true,
						height:heightPop,
						
						href : "#datalogDetailHere"
					});	
					
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
			}
			}
		 });
		
		$('#btnTahanSelected').on('click',function(e){
			e.preventDefault();
			$(this).blur();
			if ($('input[name="idCatalogItem[]"]:checked').length>0) {
				changeStatusAll(0);
			} else {
				$.colorbox({
					inline : true,
					height:180,
					href : "#popupTAhan"
				});
			}
		 });
		$("input.check_all").change(function(e){
			var is_checked = $(this).is(':checked');
			$(this).parents("table").find("tbody input[type=checkbox]").attr("checked", is_checked);
		});
		<c:if test="${!empty limitDataPage&&canCreate}">
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
		$('#title').focus();
		
		$("table").on("change", "input:checkbox", function(e){
			if($("table input:checkbox:checked").length > 0){
				$("#tools").children("a").html("Tools");
				$("#tools").find("div.disabled, a.disabled").removeClass("disabled").addClass("netral");
				$("#tools").find("i.icon-dropdown").removeClass("disabled").addClass("icon-grey");
				$("#tools").find("div.netral").addClass("dropdown");
			}else{
				$("#tools").children("a").html("Select item(s)");
				$("#tools").find("div.netral, a.netral").removeClass("netral").addClass("disabled");
				$("#tools").find("i.icon-dropdown").addClass("disabled");
				$("#tools").find("div.disabled").removeClass("dropdown");
			}
		});
	});
</script>