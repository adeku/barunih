<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/>
<c:url var="dataAjax" value="/warehouses/ajax-data-warehouse"/>
<c:url var="dataAjaxRak" value="/warehouses/ajax-data-rak"/>
<c:url var="jsFile" value="/js/pagescroll1.js"/>

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/"/>
<c:set var="dataAjax" value="/warehouses/ajax-data-warehouse"/>
<c:set var="dataAjaxRak" value="/warehouses/ajax-data-rak"/>
<c:set var="jsFile" value="/js/pagescroll1.js"/>
</c:if>


<c:if test="${!empty limitDataPage}">
<script type="text/javascript">
var urlAjax = '${dataAjax}';
var nop = ${limitDataPage};
var urlLoadingImage = '${baseURL}';

var offsetRak  = 0;
var busyRak = false;
var urlAjaxRak = '${dataAjaxRak}';
var firstWarehouse = ${warehouse1};
var firstNameWarehouse = '${nameWarehouse1}';
function getDataRak() {
	
	if (!busyRak) {
		busyRak=true;
		$('#btnPAgeNextRak').html('<center><img src="${baseURL}img/ajax-loader.gif"></center>');
		$('#RakNameWarehouse').text(' Rak '+firstNameWarehouse);
					$.post(urlAjaxRak, {
						action        : 'scrollpagination',
					    number        : nop,
					    offset        : offsetRak,
					    warehouse1 		: firstWarehouse
						    
					}, function(data) {
						if (data == ""&&offsetRak==0) {
							$('#btnPAgeNextRak').html('<a class="btn disabled large" href="" disabled="disabled">TIDAK ADA DATA</a>');
							busy = true;
						}
						else if(data == ""&&offsetRak>0) { 
							$('#btnPAgeNextRak').html('<a class="btn disabled large" href="" disabled="disabled">TIDAK ADA LAGI</a>');
							busy = true;
						}
						else {							
						    offsetRak = offsetRak+nop; 
						    $('#tableValueRak').append(data);							
							$.post(urlAjaxRak, {
								action        : 'scrollpagination',
							    number        : nop,
							    offset        : offsetRak,
							    warehouse1 		: firstWarehouse
								    
							}, function(data) {
								if (data=="") {									
									if (offsetRak==nop) {
										$('#btnPAgeNextRak').html('<a class="btn disabled large" href="" disabled="disabled">TIDAK ADA LAGI</a>');
									} else {
										$('#btnPAgeNextRak').html('<a class="btn disabled large" href="" disabled="disabled">TIDAK ADA LAGI</a>');
									}
								} else {
									$('#btnPAgeNextRak').html('<a class="btn positive large" href="">SELANJUTNYA</a>');
								}
								busyRak = false;
							});
						}
					});
	}
}
</script>
<script src="${jsFile}" type="text/javascript"></script>
</c:if>	

<script type="text/javascript">
function changeStatus1(id,stat) {
	$('#frList').attr('action',"${baseURL}warehouses/delete1-warehouse");
	$('#frList [name=id]').val(id);
	$('#frList [name=status]').val(stat);
	$('#frList').submit();
}

function changeStatus1Rak(id,stat) {
	$('#frList').attr('action',"${baseURL}warehouses/delete1-rak");
	$('#frList [name=id]').val(id);
	$('#frList [name=status]').val(stat);
	$('#frList').submit();
}

function showOption(str) {
	var target_span = $('#selectType');
	var select = "<select class='select unit' name='type' id='type' target-tab='#btSave' target-type='button'>";
	$.each(str, function() {
	      select += "<option value='"+this['data1']+"'>"+this['data1']+"</option>";
	});
	select += "</select>";
	target_span.html(select);
	core.ui.dropdown();
}

$(document).ready(function() {
	$('#btnAddWarehouse').on('click',function(e){
		e.preventDefault();
		$(this).blur();			
			$.colorbox({
				inline : true,
				height:382,
				href : "#addWArehouse",
				onComplete: function() {
					$("select#type").parents("div.select-layout").css("margin-left", "-1%");
					$('#name').focus();
				}
			});
			$('#warehouseID').val("");
			$('#name').val("");
			$('#telephone').val("");
			$('#street').val("");
			$('#city').val("");
			$('#province').val("");
			$('#addressID').val("");
			$('#infoTitle').text('Gudang Baru');
			$.post('${baseURL}warehouses/type-option-warehouse', function(data) {
				var dataI = jQuery.parseJSON(data);
				showOption(dataI.thisData);
			});
	 });
	$('#btSave').on('click',function(e){
		e.preventDefault();
		var canSave = true;
		msg = '';
		if (canSave&&$('#name').val().length<1) {
			canSave = false;
			msg = 'Tulis nama warehouse';
		}
		if (canSave&&$('#street').val().length<1) {
			canSave = false;
			msg = 'Tulis alamat';
		}
		if (canSave&&$('#telephone').val().length<1) {
			canSave = false;
			msg = 'Tulis telpon';
		}
		if (canSave&&$('#city').val().length<1) {
			canSave = false;
			msg = 'Tulis kota';
		}
		if (canSave&&$('#province').val().length<1) {
			canSave = false;
			msg = 'Tulis provinsi';
		}
		if (canSave) {
			$('#frAddWarehouse').submit();
		} else {
			$('#msgWaregouseErr').css("visibility", "visible");
			$('#msgWaregouseErr').text(msg);
		}
	});	
	$('#btSaveRak').on('click',function(e){
		e.preventDefault();
		var canSave = true;
		msg = '';
		if (canSave&&$('#RAkname').val().length<1) {
			canSave = false;
			msg = 'Tulis nama rak';
		}
		if (canSave) {
		$('#frRakk').submit();
		}  else {
			$('#rakmsgWaregouseErr').text(msg);
		}
	});
	$('#btYesHapus1').on('click',function(e){
		e.preventDefault();			
		$.colorbox.close();
		changeStatus1($(this).attr('delID'),0);
	 });
	
	$('.btNotHapus').on('click',function(e){
		e.preventDefault();			
		$.colorbox.close();
	 });
	$('.btNotHapus1').on('click',function(e){
		e.preventDefault();			
		$.colorbox.close();
	 });
			
	$('#btYesHapus1Rak').on('click',function(e){
		e.preventDefault();			
		$.colorbox.close();
		changeStatus1Rak($(this).attr('delID'),0);
	 });
	$('#btnPAgeNextRak').bind('click', function(e) {
		e.preventDefault();
		getDataRak();
	});
	
	$('#tableValue').bind('click', function(event) {
		var elementHere = event.target;
		if (elementHere.getAttribute('class')!=null) {
		if (elementHere.getAttribute('class').indexOf("editData1")!=-1) {
			elementHere.blur();
			event.preventDefault();	
			$.colorbox({
				inline : true,
				height:378	,
				href : "#addWArehouse",
				onComplete : function(){
					$("select#type").parents("div.select-layout").css("margin-left", "-1%");
				}
			});
		$('#warehouseID').val(elementHere.getAttribute('id'));
		$('#name').val($('#name-'+elementHere.getAttribute('id')).text());
		$('#telephone').val($('#telp-'+elementHere.getAttribute('id')).text());
		$('#street').val($('#street-'+elementHere.getAttribute('id')).text());
		$('#city').val($("input[name='city-"+elementHere.getAttribute('id')+"']").val());
		$('#province').val($("input[name='province-"+elementHere.getAttribute('id')+"']").val());
		$('#addressID').val($("input[name='addressID-"+elementHere.getAttribute('id')+"']").val());
		
		$('#infoTitle').text('Gudang');
		
		$.post('${baseURL}warehouses/type-option-warehouse',{selectedComp:elementHere.getAttribute('id')}, function(data) {
			var dataI = jQuery.parseJSON(data);
			showOption(dataI.thisData);
			$('#type').selectVal($("input[name='type-"+elementHere.getAttribute('id')+"']").val());
		});
		
		} else if (elementHere.getAttribute('class').indexOf("tambahRak")!=-1) {
			elementHere.blur();
			event.preventDefault();	
			
			offsetRak  = 0;
			busyRak = false;
			firstWarehouse = elementHere.getAttribute('id');			
			$('#tableValueRak').html('');
			firstNameWarehouse =  elementHere.getAttribute('warehousName');
			getDataRak();
			
			$.colorbox({
				inline : true,
// 				height:210,
				href : "#addRakk",
				onComplete: function() {
					$('#RAkname').focus();
				}
			});
			$('#infoTitleRak').text('Tambah Rak');
			$('#wareHouseID').val(elementHere.getAttribute('compID'));	
			$('#idRakk').val("");
			$('#RAkname').val("");
		} else if (elementHere.getAttribute('class').indexOf("ralatRak")!=-1) {
			elementHere.blur();
			event.preventDefault();	
			
			offsetRak  = 0;
			busyRak = false;
			firstWarehouse = elementHere.getAttribute('id');			
			$('#tableValueRak').html('');
			firstNameWarehouse =  elementHere.getAttribute('warehousName');
			getDataRak();
			
		} else if (elementHere.getAttribute('class').indexOf("btnHapus1")!=-1) {
			elementHere.blur();
			event.preventDefault();	
			$.colorbox({
				inline : true,
// 				height:180,
				href : "#askDelete1"
			});
			$('#btYesHapus1').attr('delID',elementHere.getAttribute('id'));
		}
		}
	 });
	$('#tableValueRak').bind('click', function(event) {
		var elementHere = event.target;
		if (elementHere.getAttribute('class')!=null) {
		if (elementHere.getAttribute('class').indexOf("editDataRak1")!=-1) {
			elementHere.blur();
			event.preventDefault();	
			$.colorbox({
				inline : true,
// 				height:180,
				href : "#addRakk"
			});
			$('#infoTitleRak').text('Ralat Rak');
		$('#idRakk').val(elementHere.getAttribute('id'));
		$('#wareHouseID').val(elementHere.getAttribute('compid'));
		$('#RAkname').val($("#rakkIIDD-"+elementHere.getAttribute('id')).text());			
		} else if (elementHere.getAttribute('class').indexOf("btnHapusRak1")!=-1) {
			elementHere.blur();
			event.preventDefault();	
			$.colorbox({
				inline : true,
				height:161,
				href : "#askDelete1Rak"
			});
			$('#btYesHapus1Rak').attr('delID',elementHere.getAttribute('id'));
		}
		}
	 });
	$("body").keydown(function(e){
		if(e.keyCode == 116){
			e.preventDefault();				
		    $('#btnAddWarehouse').trigger('click');
		}
		else if(e.keyCode == 112){
			e.preventDefault();
			if ($('#addWArehouse').parent().is(':visible')){
				$('#btSave').click();
			}
			else if ($('#addRakk').parent().is(':visible')){
				$('#btSaveRak').click();
			}
		}
	});
	
	
	<c:if test="${!empty rak_warehouseSElected}">
	offsetRak  = 0;
	busyRak = false;
	firstWarehouse = ${rak_warehouseSElected};			
	$('#tableValueRak').html('');
	firstNameWarehouse = '${rak_warehouseSElectedName}';
	</c:if>
	getDataRak();
});
</script>