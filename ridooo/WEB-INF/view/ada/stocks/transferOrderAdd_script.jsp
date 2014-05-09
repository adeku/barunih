<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/>
<c:url var="product_auto_complete" value="/inventory/stock-movement/auto-complete-stock-in-warehouse" />
<c:url var="warehouse_auto_complete" value="/warehouses/warehouse-autocomplete" />
<c:url var="roURL" value="/purchase/receive-orders" />

<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/"/>
	<c:set var="roURL" value="/purchase/receive-orders" />
	<c:set var="product_auto_complete" value="/inventory/stock-movement/auto-complete-stock-in-warehouse" />
	<c:set var="warehouse_auto_complete" value="/warehouses/warehouse-autocomplete" />
</c:if>

<script type="text/javascript">
	var isFirst = false;
	$(document).ready(
		function() {
			$("#inline").colorbox({
				inline : true,
				href : "#popup"
			});
			
			$('#btSave').on("click", function(e){
				e.preventDefault();
				$('#formIN').submit();
			});

			$("body").on("focus","input.product_name", function(e) {
				if ($("#sourceWarehouse").val() == "") {
					$(this).blur();
					$.colorbox({
						inline : true,
						href : "#noWArehouseSource",
						height : 207,
					});
				} else {
					productAutoComplete($(this), '');
					$(this).parents("td").find("li.token-input-input-token input").focus();
				}
			});

			$("body").on("blur", "input.customer",function(e) {
				if ($(this).val() == "") {
					$("input#product").parent().find('ul.token-input-list').remove();
					$("input#product").show();
				}
			});

			$("body").on("keyup", "input.qty",function(e){
				var qty = $(this).val();
				var price = $(this).parents("tr").find("td span.price").text();
				var item = $(this).parents("tr").find("td.item-container div.select-content input.select-choice").val();
				var subtotal_int = isNaN(qty) || qty === "" ? 0 : ( parseInt(qty) * parseInt(item) * parseInt(price) );
				console.log(parseInt(qty) +"|"+ parseInt(item) +"|"+ parseInt(price) )
				$(this).parents("tr").find("td span.subtotal").html(subtotal_int);
				$(this).parents("tr").find("td input[name=subtotal]").html(subtotal_int);
				refresh_total();
			});
			
			$("body").on("click","a.delete-row", function(e){
				$(this).parents("tr").fadeOut(function(e){
					$(this).remove();
					refresh_total();
				});
			});

			$("a.add_item").click(function(e){
				create_new_row();
				refresh_total();
			});
			
			$("body").keydown(function(e){
				if(e.keyCode == 116){
					e.preventDefault();
					create_new_row();
					refresh_total();
				}
			});
			
			$('#btnOK').on('click',function(e){
				e.preventDefault();
				$.colorbox.close();
			 });
			
			$("input.check_all").change(function(e){
				var is_checked = $(this).is(':checked');
				$(this).parents("table").find("tbody input[type=checkbox]").attr("checked", is_checked);
			});
			
			warehouseAutoComplete();
			warehouseDestinaionAutoComplete();
			
			
			<c:if test="${dataBefore.isBefore}">
				<c:if test="${!empty dataBefore.sourceWarehouseID}">
				 	$("#sourceWarehouse").tokenInput("add", {id: ${dataBefore.sourceWarehouseID}, name: '${dataBefore.sourceWarehouseName}'});
				 	$('#addressAsal').val("${dataBefore.sourceWarehouseDescriptionStreet}\n${dataBefore.sourceWarehouseDescriptionCity}, ${dataBefore.sourceWarehouseDescriptionProvince}");
			 	</c:if>
			 	
			 	<c:if test="${!empty dataBefore.destinationeWarehouseID}">
			 	$("#destinationeWarehouse").tokenInput("add", {id: ${dataBefore.destinationeWarehouseID}, name: '${dataBefore.destinationeWarehouseName}'});
			 	$('#addressDestination').val("${dataBefore.destinationeWarehouseDescriptionStreet}\n${dataBefore.destinationeWarehouseDescriptionCity}, ${dataBefore.destinationeWarehouseDescriptionProvince}");
			 	</c:if>
			 	
			 	
			 	<c:if test="${!empty dataBefore.catalogs}">
			 	elementTable = $('table.invoice_table tbody');
			 	ii=0
			 	var total_item=0;
			 	var totalWeight=0;
			 	<c:forEach items="${dataBefore.catalogs}" var="dataCAtalog">	
				 	elementTable.append('<tr ">'+
							'<td align="right" class="borderright editable">'+
							'<input type="text" name="qty[]"  class="short qty focus-tab validation" data-validation="required"  target-type="select" target-tab=".fromRakSelect" target-tr="true" disabled/>'+
							'<input type="hidden" name="weight[]"/></td>'+
							'<td align="center"><div class="unit text-center"></div><input type="hidden" name="unit[]"/></td>'+
							'<td><div class="sku"></div><input type="hidden" name="sku[]"/></td>'+
							'<td class="product-container"><div class="autocomplete-block"><input placeholder="Type product.." id="product" name="product[]" type="text" class="product_name autocomplete"/></div></td>'+
							'<td ><div class="field-content fr fromRak"><select class="fromRakSelect select focus-tab" name="fromRakId[]" target-action="create_new_row(false)"  target-tr="true"></select></div></td>'+
 							'<td ><div class="field-content fr toRak"><select  class="toRakSelect select focus-tab" name="toRakId[]"  target-action="create_new_row(false)" ></select></div></td>'+
							'<td align="center"><a class="delete-row" href="#hapus"><i class="icon-close"></i></a></td>'+
					'</tr>');
		 			elementProduct = elementTable.find('tr').eq(ii).find("input[name^=product]");
		 			productAutoComplete(elementProduct, [ {id: ${dataCAtalog.catalogID}, name:'${dataCAtalog.catalogName}'} ] );
		 			elementTable.find('tr').eq(ii).find("input[name^=qty]").val('${dataCAtalog.qty}').prop("disabled", false);
		 			
		 			elementTable.find('tr').eq(ii).find("input[name^=unit]").val('${dataCAtalog.unit}');
		 			elementTable.find('tr').eq(ii).find("div.unit").html('${dataCAtalog.unit}');
		 			
		 			elementTable.find('tr').eq(ii).find("input[name^=sku]").val('${dataCAtalog.sku}');
		 			elementTable.find('tr').eq(ii).find("div.sku").html('${dataCAtalog.sku}');
		 			
		 			elementTable.find('tr').eq(ii).find("input[name^=weight]").val('${dataCAtalog.weight}');
		 			elementTable.find('tr').eq(ii).find("input[name^=fromRakId]").val('${dataCAtalog.fromRakId}');
		 			elementTable.find('tr').eq(ii).find("input[name^=toRakId]").val('${dataCAtalog.toRakId}');

					var rakContainer = elementTable.find('tr').eq(ii).find("div.fromRak select");
					var selectedRak= null;
					<c:if test="${!empty dataCAtalog.shelfId}">
						selectedRak = ${dataCAtalog.shelfId};
					</c:if>
		 			getShelves(rakContainer, ${dataBefore.sourceWarehouseID}, selectedRak,false);
		 			
 					selectedRak= null;
 					rakContainer = elementTable.find('tr').eq(ii).find("div.toRak");
 					<c:if test="${!empty dataCAtalog.destinationId}">s
 						selectedRak = ${dataCAtalog.destinationId};
 					</c:if>
 		 			getShelves(rakContainer, ${dataBefore.destinationeWarehouseID}, selectedRak,true);
		 			
		 			<c:if test="${!empty dataCAtalog.qty}">
			 			<c:if test="${!empty dataCAtalog.weight}">
				 			total_item += ${dataCAtalog.qty};
				 			totalWeight +=${dataCAtalog.weight}*${dataCAtalog.qty};
			 			</c:if>
		 			</c:if>
		 			ii++;
			 	</c:forEach>
			 	$("span.total-item").html(total_item);
				$("span.total-weight").html(totalWeight);
			 	</c:if>
			</c:if>
			
			$("body").keydown(function(e){
				if(e.keyCode == 112){
					e.preventDefault();
				    $('#btSave').trigger('click');
				}
			});
			
			//token input text
			$('table').on("focus", '.token-input-list input[type="text"], .qty', function() {
				var add_btn = $("a.add_item");
				if(add_btn.length > 0 && add_btn.is(":visible")){
					add_btn.parents("tfoot").hide();
				}
			});
			
			$('table').on("blur", '.token-input-list input[type="text"], .qty', function() {
				var add_btn = $("a.add_item");
				if(add_btn.length > 0 && add_btn.is(":visible") == false){
					add_btn.parents("tfoot").show();
				}
			});

			//for remove row when auto complete empty
			$('table').on("focus", '.token-input-input-token input:text', function() {
				isFirst = true;
			});

			//active state for token inpute
			$('table').on("blur", '.token-input-input-token input:text', function() {
				if($(this).parents("ul").find("li.token-input-token h6").length == 0 && isFirst ){
					var token = $(this).parents("div.autocomplete-block").find("input#product");
					currentData = token.data("current-data");
					if(currentData != null && currentData != ""){
						//add current data to token with fake pricing data, to avoid error on prodct auto complete add callback
						token.tokenInput("add", {id: currentData.id, name: currentData.name, pricing : [ 0 , 1] });
						//remove td yellow hover state
						$(this).parents("td").removeClass("yellowbg ");
					}else{
						$(this).parents("tr").remove();
						$("div.token-input-dropdown:visible").remove();
					}
				}
			});

			$('table').on("keyup", '.token-input-input-token input:text', function(e) {
				if(e.which == 27){
					$(this).blur();
				};
			});

			//table input style
			$('table').on("focus", 'input:text', function(e) {
				$(this).parents("td").addClass("yellowbg");
			});

			$('table').on("click", 'input:text', function(e) {
				e.stopPropagation();
			});

			$('table').on("blur", 'input:text', function() {
				$(this).parents("td").removeClass("yellowbg");
			});

			$('table').on("mouseenter", 'input:text, ul.token-input-list', function() {
				
				$(this).parents("td").addClass("yellowbg");
			});

			$('table').on("mouseleave", 'input:text, ul.token-input-list', function() {
				if($(this).parents("td").find("input:text:focus").length == 0){
					$(this).parents("td").removeClass("yellowbg");
				}
			});

			$("#salesPersonId").selectOnClick(function(data){
				showTable(true);
			});
			
			//event when token input has clicked to visible click
			$("body").on("click", "li.token-input-token h6", function(e){
				e.stopPropagation();
				var token = $(this).parents("div.autocomplete-block").find("input#product");
				//get input 
				currentData = token.tokenInput("get")[0];
				token.tokenInput("clear");
				token.data("current-data", currentData);
			});
			
			$("body").on("click", "td div.select-content ul li", function(e){
				refresh_total();
			});
			
			<c:set var="dateCreated1" value="${dataBefore.dateModified}" />
			<c:if test="${empty dateCreated1}">
				<c:set var="dateCreated1" value="${dateCreated}" />
			</c:if>
			$( "#prosesDate" ).datepicker("setDate", "${dateCreated1}");
	});

	function productAutoComplete(element, preData) {
		console.log(preData);
		var width = element.parents("td").width();
		isFirst = false;
		core.ui.autocomplete({
			data : "${product_auto_complete}?sourceWarehouse="+$('#sourceWarehouse').val(),
			prePopulate : preData, // required
			hintText : 'Silahkan ketikkan nama dari product....',
			onDelete : null,
			onAdd : function(data) {
				var parent_tr = $(this).parents("tr");
				parent_tr.find("div.sku").html(data.sku);
				parent_tr.find("input[name^=sku]").val(data.sku);
				parent_tr.find("div.unit").html(data.unit);
				parent_tr.find("input[name^=unit]").val(data.unit);
				parent_tr.find("input[name^=weight]").val(data.weight);
				parent_tr.find("input.qty").val(1);
				
				var targetWarehouse = $("#sourceWarehouse").tokenInput("get")[0];
				var element = $(this).parents('tr').find("select.fromRakSelect").addClass("validation").data("validation","required");
				getShelves(element, targetWarehouse.id, null,false);
				
 				targetWarehouse = $("#destinationeWarehouse").tokenInput("get")[0];
 				element = $(this).parents('tr').find("select.toRakSelect");
 				getShelves(element, targetWarehouse.id, null,true);

				//trigger click to item select
				var item_opt = parent_tr.find("td.item-container .select-content").click();

				//request focus to qty_input
				parent_tr.find("input.qty").attr("disabled",false);
				parent_tr.find("input.qty").focus();
				parent_tr.find("input.qty").select();
				parent_tr.find("input.qty").keyup();
				
			},
			onResult : null,
			onReady : null,
			minChars : 1,
			width 	: width,
		    tokenFormatter: function(item) { return "<li><h6>" + item[this.propertyToSearch] + "</h6></li>" },
		    resultsFormatter: function(item){ return "<li><div style='width:100%'><span style='display:block' >" + item.name + "</span><span style='display: block;font-size: 10px;color: #6D6D6B;'>" +item.sku+ "</span></div></li>" },
		}, element);
	}

	function warehouseAutoComplete() {
		core.ui.autocomplete({
			data : "${warehouse_auto_complete}",
			prePopulate : '', // required
			hintText : 'Silahkan ketikkan nama dari product....',
			onDelete : null,
			onAdd :  function(data) {
				$('#addressAsal').val(data.street+"\n"+data.city+', '+data.province);
				warehouseDestinaionAutoComplete();
				$('#token-input-destinationeWarehouse').focus();
			},
			onResult : null,
			onReady : function () {$('#token-input-sourceWarehouse').focus();},
			target_element : '#sourceWarehouse',
			minChars : 1,
			width : $('.inputholder').outerWidth(),
			left : $('.inputholder').offset().left,

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
				$('#addressDestination').val(data.street+"\n"+data.city+', '+data.province);
				$('#token-input-product').focus();
				<c:if test="${!dataBefore.isBefore}">
					showTable(true);
				</c:if>
				$('#product').focus();				
			},
			onResult : null,
			onReady : null,
			target_element : '#destinationeWarehouse',
			minChars : 1,
			width : $('.inputholder').outerWidth(),
			left : $('.inputholder').offset().left,
		},
		$('#destinationeWarehouse'));
	}
	
	function refresh_total(){
		var total_item  = 0;
		var totalWeight = 0;
		var weight = 0;
		var qty = 0;
		$("td input.qty").each(function( i ) {
			qty = $(this).val();
			if (qty.length>0) {
				if ($.isNumeric(qty)) {
					total_item += parseInt(qty);
					weight = $('td input[name^="weight"]').val();
					if (qty.length>0) {
						if ($.isNumeric(qty)) {
							totalWeight+=total_item* parseInt(weight);
						}}
				}
			}
		});
		$("span.total-item").html(total_item);
		$("span.total-weight").html(totalWeight);		
	}

	function showTable(focusProduct){
		
		if( $("#sourceWarehouse").tokenInput("get").length > 0 && $("#destinationeWarehouse").tokenInput("get").length > 0){
			$("table.invoice_table").fadeIn(function(e){
				if(focusProduct){
					$(".product_name").focus();
					create_new_row();
				}
			});
			$(".empty-state").hide();
		}else{
			$.colorbox({
				inline : true,
				href : "#popup",
				height : 194,
			});
		}
	}

	function create_new_row(){
		$('table.invoice_table tbody').append('<tr ">'+
				'<td align="right" class="borderright editable">'+
				'<input type="text" name="qty[]"  class="short qty  focus-tab"  target-type="select" target-tab=".fromRakSelect" target-tr="true" disabled/>'+
				'<input type="hidden" name="weight[]"/></td>'+
				'<td align="center"><div class="unit text-center"></div><input type="hidden" name="unit[]"/></td>'+
				'<td><div class="sku"></div><input type="hidden" name="sku[]"/></td>'+
				'<td class="product-container"><div class="autocomplete-block"><input placeholder="Type product.." id="product" name="product[]" type="text" class="product_name autocomplete"/></div></td>'+
				'<td ><div class="field-content fr fromRak"><select class="fromRakSelect  select focus-tab" name="fromRakId[]" target-action="create_new_row(false)"  target-tr="true" style="display:none"></select></div></td>'+
 				'<td ><div class="field-content fr toRak"><select  class="toRakSelect select focus-tab" name="toRakId[]"  target-action="create_new_row(false)" style="display:none"></select></div></td>'+
				'<td align="center"><a class="delete-row" href="#hapus"><i class="icon-close"></i></a></td>'+
			'</tr>');
		$("input.product_name:last").focus();
	}

	function getShelves(element, warehouseId, selected, isDefaultShelfOnly){
		var urlTo = "${roURL}/"+warehouseId+"/get-shelves";
		if (isDefaultShelfOnly) {
			urlTo = "${roURL}/"+warehouseId+"/get-shelves?defaultshelves=1";
		}
		$.ajax({
			type : "POST",
			url : urlTo,
			beforeSend: function(){
				console.log("wid:"+warehouseId+"selected:"+selected);
			}
		})
		.done(
			function(resPonseText) {
				data = jQuery.parseJSON(resPonseText);
				console.log(data);
				var select = element;
				select.find("option").remove();

				$.each(data, function(key, value){
					console.log('asd');
					select.append('<option value="'+key+'">'+value+'</option>');
				});

				if(data.length == 0){
					select.append('<option >Tidak ada rak</option>');
				}

				var tempSelect = select.clone();
				tempSelect.show();
				select.parents("div.field-content").html(tempSelect.wrap("<div>").parent().html());

				if(selected != null){
					select.selectVal(selected);
				}
				
				core.ui.dropdown();

				select.show();
				
				
			});
	}
</script>
