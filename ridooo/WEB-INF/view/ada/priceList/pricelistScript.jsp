<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/>
<c:url var="ajaxData" value="/inventory/pricelist/ajax-data-pricelist"/>
<c:url var="exportURL" value="/inventory/pricelist/export-to-csv"/>
<c:url var="importURL" value="/inventory/pricelist/import-from-csv"/>

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/"/>
<c:set var="ajaxData" value="/inventory/pricelist/ajax-data-pricelist"/>
<c:set var="exportURL" value="/inventory/pricelist/export-to-csv"/>
<c:set var="importURL" value="/inventory/pricelist/import-from-csv"/>
</c:if>


<script type="text/javascript">
<c:if test="${!empty limitDataPage}">
var urlAjax = '${ajaxData}';
var nop = ${limitDataPage};
var offset  = 0;
var busy = false;
function getData() {
	if (!busy) {
		busy=true;
		$('#btnPAgeNext').html('<center><img src="${baseURL}img/ajax-loader.gif"></center>');
		$.post(urlAjax, {
			action        : 'scrollpagination',
		    number        : nop,
		    offset        : offset,
		    optselectedprice:'${selectedOne}'
		}, function(data) {					
			
			if (data == ""&&offset==0) {
				$('#btnPAgeNext').html('<a class="btn disabled large" href="" disabled="disabled">TIDAK ADA DATA</a>');
				busy = true;
			}
			else if(data == ""&&offset>0) { 
				$('#btnPAgeNext').html('<a class="btn disabled large" href="" disabled="disabled">TIDAK ADA LAGI</a>');
				busy = true;
			}
			else {
			    offset = offset+nop; 
			    $('#tableValue').append(data);
			    $('#tableValuePrint').append(data);
				busy = false;
				$.post(urlAjax, {
					action        : 'scrollpagination',
				    number        : nop,
				    offset        : offset,
				    optselectedprice:'${selectedOne}'	
					    
				}, function(data) {	
					if (data=="") {
						if (offset==nop) {
							$('#btnPAgeNext').html('');
						} else {
							$('#btnPAgeNext').html('<a class="btn disabled large" href="" disabled="disabled">TIDAK ADA LAGI</a>');
							busy = true;
						}
					} else {
						$('#btnPAgeNext').html('<a class="btn positive large" href="">SELANJUTNYA</a>');
					}
				});
			}
			
			$.each($(".price"), function(index, data){
				if($(data).prop("tagName").toLowerCase() == "input"){
					var text = $(data).val();
					$(data).val(text.split('.').join(""));
				}else{
					var text = $(data).text();
					$(data).text(text.split('.').join(""));
				}
			});
			$('.price').currency();
		});
	}
	
	$("#export-btn").click(function(e){
		/* $.get("${exportURL}", { name: "John", time: "2pm" }, function(data){
			alert(data);
		}); */
		var queryString = $("div.export-form select").serialize();
		window.location = "${exportURL}?"+queryString;
	});
	
/*	$("#import-btn").click(function(e){
		e.preventDefault();
		$('#import-file').click();
	});*/
	
	
}
</c:if>	
function viewAddPrice() {
	$.colorbox({
		inline : true,
		height:194,
		href : "#winDowsaddPrice"
	});
	
}

$(document).ready(function() {
	$('#newPrice1').css( 'cursor', 'pointer' );
	$('#newPrice').on('click',function(e){
		e.preventDefault();
		viewAddPrice();
	 });
	
	$("#index_search").keydown(function(e){
		if(e.keyCode == 13&&$(this).val().length>0){
			$('#search_index_form').submit();
		}
	});
	
	$('#newPrice1').on('click',function(e){
		viewAddPrice();
	 });
	$('#btSavePrice').on('click',function(e){
		e.preventDefault();
		var canSave = true;
		msg = '';
		if (canSave&&$('#namePrice').val().length<1) {
			canSave = false;
			msg = 'Tulis jenis harganya';
		}
		if (canSave) {
			$('#frAddPrice').submit();
		} else {
			$('#msgWaregouseErr').text(msg);
		}
	 });
	$('#btnSavePriceHere').on('click',function(e){
		e.preventDefault();
		$('#frPriceSet').submit();
	 });
	$('.selectPriceType').on('click',function(e){
		e.preventDefault();
		var str = "${baseURL}inventory/pricelist?optselectedprice="+$(this).attr('pricetype');
		var searching = '${index_search}';
		if (searching.length>0) {
			str+="&index_search="+searching;
		}
		window.location = str;
	 });
	$('.btNotHapus').on('click',function(e){
		e.preventDefault();
		$.colorbox.close();
	 });
	
	
	$('#btnHapusSelected').on('click',function(e){
		e.preventDefault();
		$.colorbox({
			inline : true,
// 			height:200,
			href : "#infoDeleted"
		});
	});
	
	$('#btYesHapus').on('click',function(e){
		e.preventDefault();
		var urlHere = '${baseURL}inventory/pricelist/delete-price/${selectedOne}';
		$.post(urlHere,function(data) { window.location.href='${baseURL}inventory/pricelist'; });
	});
	
	$('#tableValue').bind('click', function(event) {
		var elementHere = event.target;
		if (elementHere.getAttribute('class')!=null) {
		if (elementHere.getAttribute('class').indexOf("showDetailCatalog")!=-1) {
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
		}
		}
	 });
	
	$('body').on('blur','.msrp', function(event) {
		$.post("${baseURL}inventory/pricelist/msrp-save",{"cekPayment":$(this).attr('name'),"val":$(this).val()});	
	});
	
	$('body').on('blur','.thisMoney', function(event) {
		$.post("${baseURL}inventory/pricelist/msrp-save",{"cekPayment":$(this).attr('name'),"val":$(this).val(),"priceSelected":"${selectedOne}"});	
	});
	
	$('#btnExport').bind('click', function(e) {
		e.preventDefault();
		$.colorbox({
			inline : true,
			height:300,
			href : "#layoutExportData"
		});	
	});
	$('#btnImport').bind('click', function(e) {
		e.preventDefault();
		$.colorbox({
			inline : true,
			height:220,
			href : "#layoutUpdateData"
		});	
	});
	
	$("div.upload-file").click(function(e){
		$("#import-file").click();
	});
	
	$('div.upload-file').on(
            {'dragover': function(e) {
  				e.preventDefault();
            },'drop': function(e) {
               var file = e.originalEvent.dataTransfer.files; 
               fileUpload("${importURL}", file[0] );
                e.preventDefault();
     		},'dragenter': function(event, ui) {
                e.preventDefault();
		    }
    });
	
	<c:if test="${!empty limitDataPage}">
	$('#btnPAgeNext').bind('click', function(e) {
		e.preventDefault();
		getData();
	});
	getData();
	</c:if>	
	
	$("body").on("keyup", "input.prices", function(e){
		core.ui.currency();
	});
});

function fileUpload( urlSet, fileUpload){
	var formData = new FormData($("form")[0]);
	formData.append("file", fileUpload);
    $.ajax({
        url: urlSet,  //server script to process data
        type: 'POST',
//         dataType : 'json', 
        xhr: function() {  // custom xhr
            var myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){ // check if upload property exists
                myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // for handling the progress of the upload
            }
            return myXhr;
        },
        success: function(data, status){
        	alert("res = " + data);
        },
        error: function(xhr, ajaxOptions, thrownError) {
                alert("There is some error when importing current data \n"+ 
                xhr.status+"\n"+
                thrownError);
        },
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
    });
 }

 function progressHandlingFunction(data){
 	function progressHandlingFunction(e){
	    if(e.lengthComputable){
	        console.log("value:"+e.loaded+" - "+"max:"+e.total);
	    }
	}
 }
</script>
