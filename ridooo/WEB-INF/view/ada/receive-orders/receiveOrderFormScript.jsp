<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url var="product_auto_complete" value="/sales/product/auto_complete"/>
<c:url var="customer_auto_complete" value="/sales/customer/auto_complete" />
<c:url var="supplier_auto_complete" value="/sales/supplier/auto_complete" />
<c:url var="roURL" value="/purchase/receive-orders" />
<c:url var="baseURL" value="/"/>

<c:if test="${baseURL=='//'}">
	<c:set var="product_auto_complete" value="/sales/product/auto_complete"/>
	<c:url var="customer_auto_complete" value="/sales/customer/auto_complete" />
	<c:set var="supplier_auto_complete" value="/sales/supplier/auto_complete" />
	<c:set var="roURL" value="/purchase/receive-orders" />
	<c:set var="baseURL" value="/"/>
</c:if>

<script>
	var count = 0;
	var isFirst = false;
	
	function setSelectOption(select, boxOption){
		select.next().remove();
		select.find("option").remove();
		select.append('<option value="1">Biji</option><option value="'+ boxOption +'">Box('+ boxOption +')</option>');
		select.show();
	}
	
	function productAutoComplete(element, prepopulateData) {
		var width = element.parents("td").width();
		isFirst = false;
		core.ui.autocomplete({
			data : "${product_auto_complete}",
			prePopulate : prepopulateData, // required
			hintText : 'Silahkan ketikkan nama dari product....',
			onDelete : null,
			onAdd : function(data) {
	
// 				if(data.pricing.length == 0){
// 					$(this).tokenInput("clear");
// 					alert("Barang belum memiliki daftar harga");
// 					return true;
// 				}
				
				var parent_tr = $(this).parents("tr");
				var id = parent_tr.attr('id');
				parent_tr.find("input#weight"+id).val(data.weight);
				parent_tr.find("span.sku").html(data.sku);
				parent_tr.find("input[name='transactionDetails["+id+"].sku']").val(data.sku);
				
				console.log('unit'+id);
				setSelectOption($("select[name='transactionDetails["+id+"].unit']"), data.unit);
				
				//refresh unit
				var target_span = $(this).parents("tr").find("td.item-container span");
				var select = "<select class='select unit focus-tab ' id='unitToDelete' target-action='create_new_row(false)' name='transactionDetails["+id+"].unit' >";
				select += "<option value='1'>Biji</option>";
				if(data.unit != 0 ){
					select += "<option value='" + data.unit + "'>Box[" + data.unit + "]</option>";
				}
				select += "</select>";
				target_span.html(select);
				core.ui.dropdown();
				$("select#unitToDelete").prop("name","unitToDelete");
				
				//trigger click to item select
				var item_opt = parent_tr.find("td.item-container .select-content").click();

				//request focus to qty_input
				parent_tr.find("input.qty").attr("disabled",false);
				parent_tr.find("input.qty").val(1);
				parent_tr.find("input.qty").focus();
				parent_tr.find("input.qty").select();
				parent_tr.find("input.qty").keyup();

				$("a.delete-row").css("visibility", "visible");
				
// 				count ++;
			},
			onResult : null,
			onReady : null,
			minChars : 1,
			 resultsFormatter: function(item){ return "<li><div style='width:100%'><span style='display:block' >" + item.name + "</span><span style='display: block;font-size: 10px;color: #6D6D6B;'>" +item.sku+ "</span></div></li>" },
			width : width
		}, element);
	}

	
	function customerAutoComplete(prepopulateData) {
		core.ui.autocomplete({
			data : "${customer_auto_complete}",
			prePopulate : prepopulateData, // required
			hintText : 'Silahkan ketikkan nama pelanggan....',
			onDelete : null,
			onAdd : function(data){
				showTable(true);
				console.log("datam"+data.address);
				$('textarea.address').val(data.address);
				
				// focus on product autocomplete
				$('#token-input-product').focus();
			},
			onResult : null,
			onReady : function(){
				$('#token-input-customer').focus();
			},
			target_element : '#customer',
			minChars : 1,
			width : $('.inputholder').outerWidth(),
			left : $('.inputholder').offset().left,
		});
	}
	
	function supplierAutoComplete(prepopulateData) {
		core.ui.autocomplete({
			data : "${supplier_auto_complete}",
			prePopulate : prepopulateData, // required
			hintText : 'Silahkan ketikkan nama dari product....',
			onDelete : null,
			onAdd : function(data){
				showTable(true);
				console.log("datam"+data.address);
				$('textarea.address').val(data.address);
				
				// focus on product autocomplete
				$('#token-input-product').focus();
			},
			onResult : null,
			onReady : function(){
				$('#token-input-supplier').focus();
			},
			target_element : '#supplier',
			minChars : 1,
			width : $('.inputholder').outerWidth(),
			left : $('.inputholder').offset().left,
		});
	}

	function refresh_total(){
		var total_price = 0;
		var total_item  = 0;
		var total_weight = 0;
	
		$("td input.qty").each(function() {
			var id = $(this).parents('tr').attr('id');
			if(typeof(id) !== undefined){
				var qty = $(this).val();
				var item = $('select[name="transactionDetails['+id+'].unit"]').val();
				var weight = $('input#weight'+id).val();
				console.log(id+"kitem:"+item);
				val = qty * item;
				total_weight += parseInt(val * weight);
				total_item += parseInt(val);
			}
		});
	
		//$("span.total-price").html(total_price);
		console.log("total item = "+total_item);
		$("span.total-item").html(total_item);
		$("span.total-weight").html(total_weight);
		$('input[name=total]').val(total_price);
		$('input[name=totalItem]').val(total_item);
		$('input[name=totalWeight]').val(total_weight);
	}

	
	function getShelves(element, warehouseId, selected){
		$.ajax({
			type : "POST",
			url : "${roURL}/"+warehouseId+"/get-shelves",
			beforeSend: function(){
				console.log("wid:"+warehouseId+"selected:"+selected);
			}
		})
		.done(
			function(resPonseText) {
				data = jQuery.parseJSON(resPonseText);
//					var data = obj.data;
				console.log(data);
				
				var select = element.parents('tr').find('select.shelf');
				console.log("select"+$(select));
				select.find("option").remove();
				$.each(data, function(key, value){
					console.log('asd');
					select.append('<option value="'+key+'">'+value+'</option>');
				});
				var tempSelect = select.clone();
				tempSelect.show();
				select.parents("div.field-content").html(tempSelect.wrap("<div>").parent().html());
				
				if(selected != null){
					select.selectVal(selected);
				}
				
				core.ui.dropdown();
				
				
			});
	}
	
	$(document).ready(function(){
		
		$("#inline").colorbox({
			inline : true,
			href : "#popup"
		});

		$("body").on("focus","input.product_name", function(e) {
			if ($("input#supplier").val() == "") {
				$(this).blur();
				$.colorbox({
					inline : true,
					href : "#popup",
					height : 199,
				});
			} else {
				productAutoComplete($(this), '');
				$(this).parents("td").find("li.token-input-input-token input").focus();
			}
		});

		$("body").on("blur", "input.supplier",function(e) {
			if ($(this).val() == "") {
				$("input#product").parent().find('ul.token-input-list').remove();
				$("input#product").show();
			}
		});

		$("body").on("keyup", "input.qty",function(e){
			var qty = $(this).val();
			var price = $(this).parents("tr").find("td span.price").text();
			var item = $(this).parents("tr").find("select.unit").val();
			var subtotal_int = isNaN(qty) || qty === "" ? 0 : ( parseInt(qty) * parseInt(item) * parseInt(price) );
			console.log(parseInt(qty) +"|"+ parseInt(item) +"|"+ parseInt(price) )
			$(this).parents("tr").find("td span.subtotal").html(subtotal_int);
			$(this).parents("tr").find("td input[name=subtotal]").html(subtotal_int);
			refresh_total();
		});

		$("body").on("click","a.delete-row", function(e){
			$(this).parents("tr").fadeOut(function(e){
				$(this).remove();
			});
		});
		
		$("input.check_all").change(function(e){
			var is_checked = $(this).is(':checked');
			$(this).parents("table").find("tbody input[type=checkbox]").attr("checked", is_checked);
		});
		
		$("#supplier-goto").click(function(e){
			$.colorbox.close();
			$("#token-input-supplier").focus();
		});
		
		<c:choose>
			<c:when test="${!action.equals('edit')}">
				<c:choose>
					<c:when test="${form.document == 'vin_return_orders'}">
						customerAutoComplete('');
					</c:when>
					<c:otherwise>
						supplierAutoComplete('');
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${form.document == 'vin_return_orders'}">
						customerAutoComplete([{id:"${form.supplierId}", name:"${form.supplierName}"}]);
						$("textarea.address").text('${form.supplierAddress}');
					</c:when>
					<c:otherwise>
						supplierAutoComplete([{id:"${form.supplierId}", name:"${form.supplierName}"}]);
						$("textarea.address").text('${form.supplierAddress}');
					</c:otherwise>
				</c:choose>
				
			</c:otherwise>
		</c:choose>
		

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
			/* $('table').on("focus", 'input:text', function(e) {
				$(this).parents("td").addClass("yellowbg");
			});

			$('table').on("click", 'input:text', function(e) {
				e.stopPropagation();
			});

			$('table').on("blur", 'input:text', function() {
				$(this).parents("td").removeClass("yellowbg");
			}); */

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
			
			$("body").on("click", "td div.select-content ul li", function(e){

				var warehouseId = $(this).parents("td").find("select.warehouse").val();
				getShelves($(this), warehouseId, null);
				$(this).parents("td").removeClass("yellowbg");
			});

	});
	
	function showTable(focusProduct){
		
		if( $("#supplier").tokenInput("get").length > 0){
			$("table.invoice_table").fadeIn(function(e){
				//reComputeWidthTable();
				if(focusProduct){
					$(".product_name").focus();
// 					create_new_row(false);
				}
			});
			$(".empty-state").hide();
		}else{
			$.colorbox({
				inline : true,
				href : "#popup",
				height : 199,
			});
		}
	}
	
</script>
