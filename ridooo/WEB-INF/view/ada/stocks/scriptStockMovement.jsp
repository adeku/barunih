<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/>
<c:url var="ajaxData" value="/inventory/stock-movement/ajax-data-stockmovement"/>
<c:url var="jsFile" value="/js/pagescroll.js"/>
<c:url var="urlHere" value="/inventory/stock-movement/get-maxvalue-stock-inwarehouse"/>
<c:url var="addAction" value="/inventory/stock-movement/create"/>

<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/"/>
	<c:set var="ajaxData" value="/inventory/stock-movement/ajax-data-stockmovement"/>
	<c:set var="jsFile" value="/js/pagescroll.js"/>
	<c:set var="urlHere" value="/inventory/stock-movement/get-maxvalue-stock-inwarehouse"/>
	<c:set var="addAction" value="/inventory/stock-movement/create"/>
</c:if>


<c:if test="${!empty limitDataPage}">
<script type="text/javascript">
var urlAjax = '${ajaxData}';
var nop = ${limitDataPage};
</script>
<script src="${jsFile}" type="text/javascript"></script>
</c:if>	

<script type="text/javascript">
function delTransferOrder(id) {
	$.post("${baseURL}inventory/stock-movement/delete",{ id: id },function(result) { location.reload(); });
}

function itemAutoComplete() {
	core.ui.autocomplete({
		data : "${baseURL}inventory/catalogs/auto-complete-stock",
		prePopulate : '',
		hintText : 'Silahkan ketikkan nama dari product....',
		onDelete : null,
		onAdd :  function() {
			$.post("${baseURL}inventory/stock-movement/warehouse-options/"+$(this).val(), function(result) {
				jsonLop = $.parseJSON(result);
				
								
				var target_span = $('#warehouse').parent().parent();
				var select = "<select class=\"select focus-tab\"  target-tab=\"#rak\" name='warehouse' id='warehouse'>";
				select += "<option>Pilih gudang</option>";
				try {
					$.each(jsonLop.warehouse, function(idx, obj) {
						 select += "<option value='"+obj.id+"'>"+obj.company+"</option>";
					});
				} catch (err) {}
				select += '</select>';
				target_span.find("div.select-layout").remove();
				target_span.append(select);
				
				target_span = $('#group').parent().parent();
				var select = "<select class=\"select focus-tab\"  target-tab=\"#qty\" name='group' id='group'>";
				try {
					$.each(jsonLop.dataUnit, function(idx, obj) {
						 select += "<option value='"+obj.unit+"'>"+obj.unit+"</option>";
					});
			} catch (err) {}
				select += '</select>';
				target_span.find("div.select-layout").remove();
				target_span.append(select);
				
				core.ui.dropdown();
				addListenerWarehouse();
				target_span = $('#warehouse').parent().parent();
				target_span.find("div.select-layout").click();
			});
			},
		onResult : null,
		onReady : null,
		target_element : '#catalog_item_id',
		minChars : 1,
		width : "30%"
	});
}

function setMaxValueItem() {
	$('#maxQty').val(0);
	var warehouseId= $('#warehouse').val();
	var groupID= $('#group').val();
	var catalog_item_id1 = $('#catalog_item_id').val();
	var rakID =  $('#rak').val();
	if (groupID.length>0
	&&warehouseId.length>0
	&&catalog_item_id1.length>0
	) {		
		$.post('${urlHere}',{group:groupID
			,warehouse:warehouseId, 
			catalog_item_id:catalog_item_id1,
			rakid:rakID
		}
		,function(result) { $('#maxQty').val(result); });
	}
	
}

	$(document).ready(function() {
		$('#btYesHapus1').on('click',function(e){
			e.preventDefault();			
			$.colorbox.close();
			delTransferOrder($(this).attr('delID'));
		 });
		
		$('#btnAdd').on('click',function(e){
			e.preventDefault();			
			$(this).blur();			
			$.colorbox({
				inline : true,
				height:429,
				href : "#formAdd",
				onComplete : function(){
					$('#token-input-catalog_item_id').focus();
				}
			});
			$('#titleForm').html('Penyesuaian Stok Baru');
			
			$('#catalog_item_id').tokenInput("clear");
			$('#warehouse').selectVal('');
			$('#group').selectVal(0);
			$('#qty').val(0);
			$('#maxQty').val(0);
			$('#notes').val('');
			
			
			var toAction="${addAction}";
			$('#formIN').attr('action', toAction);
		 });
		itemAutoComplete();
		
		
		
		$('#btSAve').on('click',function(e){
			e.preventDefault();
			var msg = "";
			var canSubmit=true;
			if (canSubmit&&$('#catalog_item_id').val().length<1) {
				canSubmit = false;
				msg ="Tulis nama barang";
			}

			if (canSubmit&& $('#warehouse').val().length<1) {
				canSubmit = false;
				msg ="Pilih warehouse";
			}
			
			if (canSubmit&&$('#qty').val().length<1) {
				canSubmit = false;
				msg ="Tulis quantity";
			}
			
			if (canSubmit&&$('#qty').val()==0) {
				canSubmit = false;
				msg ="Tulis quantity";
			}
			
			if (canSubmit&& $('#qty').val()<0 && ( ( $('#qty').val()*-1)>$('#maxQty').val())) {
				canSubmit = false;
				msg ="Quantity lebih besar dari jumlah maximal";
			}
				
			if (canSubmit)  
				$('#formIN').submit();
			else{
				$('#alertError').css("visibility","visible");
				$('#alertError').text(msg);
			}
		 });
		
		$('#btCancel').on('click',function(e){
			e.preventDefault();
			$.colorbox.close();
		 });
		
		$('#btNotHapus1').on('click',function(e){
			e.preventDefault();
			$.colorbox.close();
		 });
		
		$('#tableValue').bind('click', function(event) {
			var elementHere = event.target;
			if (elementHere.getAttribute('class')!=null) {
			if (elementHere.getAttribute('class').indexOf("btnHapus1")!=-1) {
				event.preventDefault();
				elementHere.blur();
				$.colorbox({
					inline : true,
					height:180,
					href : "#askDelete1"
				});
				$('#btYesHapus1').attr('delID',elementHere.getAttribute('id'));
			} else if (elementHere.getAttribute('class').indexOf("ralat")!=-1) {
				event.preventDefault();
				elementHere.blur();
				$.colorbox({
					inline : true,
					height:460,
					href : "#formAdd"
				});
				$('#titleForm').html('Ralat stok');
				$('#catalog_item_id').tokenInput("add", {id: elementHere.getAttribute('idCAtalogItem') , name: elementHere.getAttribute('catalogTitle') });
				$('#warehouse').selectVal(elementHere.getAttribute('warehouseid'));
				$('#group').selectVal(elementHere.getAttribute('group'));
				$('#qty').val(elementHere.getAttribute('qtyy'));
				$('#maxQty').val(elementHere.getAttribute('maxQTYy'));
				$('#notes').val(elementHere.getAttribute('notess'));
				<c:url var="addAction" value="/inventory/stock-movement/"/>
				var toAction="${addAction}"+elementHere.getAttribute('idStockMovement')+"/edit";
				$('#formIN').attr('action', toAction);
			}
		}
		 });
		<c:if test="${canCreate}">
		$("body").keydown(function(e){
			if(e.keyCode == 116){
				e.preventDefault();				
				$('#btnAdd').trigger('click');
			} else if(e.keyCode == 112){
				e.preventDefault();		
				if ($('#formAdd').parent().is(':visible')){
					$('#btSAve').trigger('click');
				}
			}
		});
		</c:if>
		

		addListenerWarehouse();
		addListenerRak();
	});
	
	function addListenerWarehouse(){
		$('#warehouse').selectOnClick(function() {
			$.post("${baseURL}inventory/stock-movement/rakoptions",{ id:$('#warehouse').val() },function(result) {
				if (result.length>0) {
					var dataI = jQuery.parseJSON(result);
					var target_span = $('#rak').parent().parent();
					var select = "<select class=\"select focus-tab\"  target-tab=\"#qty\" name='rak' id='rak'>";
					try {
					var inihData = dataI.dataoptions;
					countDAta =0;
					$.each(inihData, function() {
						countDAta++;
					      select += "<option value='"+this['val']+"'>"+this['text']+"</option>";
					});
					} catch (err) {}
					select += '</select>';
					target_span.find("div.select-layout").remove();
					target_span.append(select);
					core.ui.dropdown();
					if (countDAta>1) {
					target_span.find("div.select-layout").click();
					}
				}
				addListenerRak();
				setMaxValueItem();
			});			
		});
		
		$('#group').selectOnClick(function(e) {
			setMaxValueItem();
		});
	}
	
	function addListenerRak() {
		$('#rak').parent().find("ul li").on("click",function(e) {
			if ($('#group option').size()>1) {
				var target_span = $('#group').parent().parent();
				target_span.find("div.select-layout").click();				
			}
			setMaxValueItem();
		});
	}
	
	
</script>