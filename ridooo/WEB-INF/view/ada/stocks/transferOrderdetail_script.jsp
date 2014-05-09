<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/>
<c:url var="product_auto_complete" value="/inventory/stock-movement/auto-complete-stock-in-warehouse" />
<c:url var="warehouse_auto_complete" value="/warehouses/warehouse-autocomplete" />

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/"/>
<c:set var="product_auto_complete" value="/inventory/stock-movement/auto-complete-stock-in-warehouse" />
<c:set var="warehouse_auto_complete" value="/warehouses/warehouse-autocomplete" />
</c:if>

<script type="text/javascript">
	$(document).ready(
		function() {			
			$("input.check_all").change(function(e){
				var is_checked = $(this).is(':checked');
				$(this).parents("table").find("tbody input[type=checkbox]").attr("checked", is_checked);
			});
			
			
			<c:if test="${dataBefore.isBefore}">
				<c:if test="${!empty dataBefore.sourceWarehouseID}">
				 	$("#sourceWarehouse1").val('${dataBefore.sourceWarehouseName}');
				 	$('#addressAsal').val("${dataBefore.sourceWarehouseDescriptionStreet}\n${dataBefore.sourceWarehouseDescriptionCity}, ${dataBefore.sourceWarehouseDescriptionProvince}");
			 	</c:if>
			 	
			 	<c:if test="${!empty dataBefore.destinationeWarehouseID}">
			 	$("#destinationeWarehouse1").val('${dataBefore.destinationeWarehouseName}');
			 	$('#addressDestination').val("${dataBefore.destinationeWarehouseDescriptionStreet}\n${dataBefore.destinationeWarehouseDescriptionCity}, ${dataBefore.destinationeWarehouseDescriptionProvince}");
			 	</c:if>
			 	
			 	
			 	<c:if test="${!empty dataBefore.catalogs}">
			 	elementTable = $('table.invoice_table tbody');
			 	ii=0
			 	var total_item=0;
			 	var totalWeight=0;
			 	<c:forEach items="${dataBefore.catalogs}" var="dataCAtalog">			 	
			 	elementTable.append('<tr id="first">'
			 			+'<td align="right" class="borderright text-right">${dataCAtalog.qty}</td>'
			 			+'<td height="20">${dataCAtalog.unit == "1" ? "Biji" : "Box <span class=\"number\">[".concat("1").trim().concat("]</span>")}</td>'
			 			+'<td>${dataCAtalog.sku}</td>'
			 			+'<td>${dataCAtalog.catalogName}</td>'
			 			+'<td>${dataCAtalog.sourceRak}</td>'
			 			+'<td>${dataCAtalog.destinationRak}</td>'
			 			+'</tr>');
			 	<c:if test="${!empty dataCAtalog.qty}">
	 			<c:if test="${!empty dataCAtalog.weight}">
	 			total_item += ${dataCAtalog.qty};
	 			totalWeight +=${dataCAtalog.weight}*${dataCAtalog.qty};
	 			</c:if>
	 			</c:if>		
			 	</c:forEach>			 	
			 	$("span.total-item").html(total_item);
				$("span.total-weight").html(totalWeight);
			 	</c:if>
				
				
			</c:if>
	});

	function productAutoComplete(element) {
		core.ui.autocomplete({
			data : "${product_auto_complete}?sourceWarehouse="+$('#sourceWarehouse').val(),
			prePopulate : '', // required
			hintText : 'Silahkan ketikkan nama dari product....',
			onDelete : null,
			onAdd : function(data) {
				var parent_tr = $(this).parents("tr");
				parent_tr.find("span.sku").html(data.sku);
				parent_tr.find("input[name^=sku]").val(data.sku);
				parent_tr.find("span.unit").html(data.unit);
				parent_tr.find("input[name^=unit]").val(data.unit);
				parent_tr.find("input.qty").val(0);

				//trigger click to item select
				var item_opt = parent_tr.find("td.item-container .select-content").click();

				//request focus to qty_input
				parent_tr.find("input.qty").attr("disabled",false);
				parent_tr.find("input.unit").attr("disabled",false);				
				parent_tr.find("input.qty").focus();
				
			},
			onResult : null,
			onReady : null,
			minChars : 1
		}, element);
	}

	function warehouseAutoComplete() {
		core.ui.autocomplete({
			data : "${warehouse_auto_complete}",
			prePopulate : '', // required
			hintText : 'Silahkan ketikkan nama dari product....',
			onDelete : null,
			onAdd :  function(data) {
// 				$('#addressAsal').val(data.city+' '+data.province);
				warehouseDestinaionAutoComplete();
			},
			onResult : null,
			onReady : null,
			target_element : '#sourceWarehouse',
			minChars : 1,
			width : "100%"
		});
	}
	
	function warehouseDestinaionAutoComplete() {
		try {
		 $("#destinationeWarehouse").parent().find(".token-input-list").remove();
		} catch (err) {}
		
		core.ui.autocomplete({			
			data : "${warehouse_auto_complete}?sourcei="+$('#sourceWarehouse').val(),
			prePopulate : '', // required
			hintText : 'Silahkan ketikkan nama dari product....',
			onDelete : null,
			onAdd :  function(data) {
// 				$('#addressDestination').val(data.city+' '+data.province);
			},
			onResult : null,
			onReady : null,
			target_element : '#destinationeWarehouse',
			minChars : 1,
			width : "100%"
		},
		$('#destinationeWarehouse'));
	}
	
	function refresh_total(){
		var total_price = 0;
		var total_item  = 0;
		$("td span.subtotal").each(function( i ) {
			var val = $(this).text();
			val = val == "" ? 0 : val;
			total_price += parseInt(val);
		});

		$("td input.qty").each(function( i ) {
			var qty = $(this).val();
			var item = $($("td.item-container div.select-content input.select-choice")[i]).val();
			val = qty * item;
			total_item += parseInt(val);
		});

		$("span.total-price").html(total_price);
		$("span.total-item").html(total_item);
	}
</script>
