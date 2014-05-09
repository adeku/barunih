<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url var="product_auto_complete" value="/sales/product/auto_complete"/>
<c:url var="supplier_auto_complete" value="/sales/supplier/auto_complete" />
<c:url var="baseURL" value="/"/>

<c:if test="${baseURL=='//'}">
<c:set var="product_auto_complete" value="/sales/product/auto_complete"/>
<c:set var="supplier_auto_complete" value="/sales/supplier/auto_complete" />
<c:set var="baseURL" value="/"/>
</c:if>


<script>
	var count = -1;
	
	function setSelectOption(select, boxOption){
		select.next().remove();
		select.find("option").remove();
		select.append('<option value="1">Biji</option>');
		if(boxOption != 0 && typeof boxOption != undefined){
			select.append('<option value="'+ null +'">Box('+ boxOption +')</option>');
		}
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

			/* 	if(data.pricing.length == 0){
					$(this).tokenInput("clear");
					alert("Barang belum memiliki daftar harga");
					return true;
				} */

				var parent_tr = $(this).parents("tr");
				var id = parent_tr.attr('id');
				parent_tr.find("input#weight"+id).val(data.weight);
				parent_tr.find("div.sku").html(data.sku);
				parent_tr.find("input[name='transactionDetails["+id+"].sku']").val(data.sku);
// 				parent_tr.find("div.item-price").html(data.pricing[0].price);
// 				parent_tr.find("input[name='transactionDetails["+id+"].price']").val(data.pricing[0].price);

				parent_tr.find("input.itemName").val(data.name);

				//add even tto select
				$(this).parents("tr").find("select.unit").selectOnClick(function(e){
					parent_tr.find("input.qty").keyup();
				});
				
				//request focus to qty_input
				parent_tr.find("input.unit").val(1);
				parent_tr.find("input.qty").attr("disabled",false);
				parent_tr.find("input[name='transactionDetails["+id+"].priceHelper']").attr("disabled",false);
				parent_tr.find("input.qty").val(1);
				parent_tr.find("input.qty").focus();
				parent_tr.find("input.qty").select();
				parent_tr.find("input.qty").keyup();
				refresh_total();
				
				$("a.delete-row").css("visibility", "visible");
// 				count ++;
			},
			onResult : null,
			onReady : null,
			minChars : 1,
			tokenFormatter: function(item) { return "<li><h6>" + item[this.propertyToSearch] + "</h6></li>" },
			resultsFormatter: function(item){ return "<li><div style='width:100%'><span style='display:block' >" + item.name + "</span><span style='display: block;font-size: 10px;color: #6D6D6B;'>" +item.sku+ "</span></div></li>" },
			width : width
			
		}, element);
	}
	
	
	
	function supplierAutoComplete(prepopulateData) {
		core.ui.autocomplete({
			data : "${supplier_auto_complete}",
			prePopulate : prepopulateData, // required
			hintText : 'Silahkan ketikkan nama supplier....',
			onDelete : function(){
				$('textarea.address').val('');
			},
			onAdd : function(data){
				showTable(false);
				$('textarea.address').val(data.address);
				if($("#salesPersonId option[value='"+data.salesId+"']").length > 0){
					$('#salesPersonId').val(data.salesId);
				}
				
				$("#supplierName").val(data.name);
				$('#warehouseId').parents('div.select-layout').click();
				
			},
			onResult : null,
			left : $(".inputholder").offset().left,
			width : $(".inputholder").width(),
			onReady : null,
			target_element : '#supplier',
			minChars : 1,
		});
	}
	
	function refresh_total(){

		var total_price = 0;
		var total_item  = 0;
		var total_weight = 0;
		$("td div.subtotal").each(function( i ) {
			var val = $(this).text().split('.').join("");
			val = val == "" ? 0 : val;
			total_price += parseInt(val);
		});
	
		$("td input.qty").each(function() {
			var id = $(this).parents('tr').attr('id');
			var qty = $(this).val();
			if(typeof(id) !== undefined && qty != ""){
				var item = $('input[name="transactionDetails['+id+'].unit"]').val();
				var weight = $('input#weight'+id).val();
				val = qty * item;
				total_weight += Math.round(val * weight * 10) / 10;
				total_item += parseInt(val);
			}
		});
		$("span.total-price").html(total_price);
		$("span.total-item").html(total_item);
		$("span.total-weight").html(total_weight);
		$('input[name=total]').val(total_price);
		$('input[name=totalItem]').val(total_item);
		$('input[name=totalWeight]').val(total_weight);
		core.ui.currency();
		
	}

	function create_new_row(populate){
		//when form is valid
		if(checkValidate()){
			count++;
			//trigger event keyup to calculate subtotal
			$("input.qty").keyup();
//	 		alert('count '+count);
			$('table.invoice_table tbody').append('<tr id="'+count+'">'+
					'<td align="right" class="borderright editable"><input type="text" id="qty" name="transactionDetails['+count+'].quantity" class="number-filter short qty  focus-tab validation" data-validation="required"  target-tab=".unit" target-tr="true" disabled/></td>'+
					'<td class="item-container itemDetail'+count+'">'+
						'<input class="short focus-tab unit validation" data-validation="required"  type="text" name="transactionDetails['+count+'].unit"  target-tab=".inbpx" target-tr="true" />'+
					'</td>'+
					'<td>'+
						'<input class="short focus-tab validation inbpx" data-validation="required,number"  target-tab=".prices"  value="1" type="text" name="transactionDetails['+count+'].inBox" target-tr="true" />'+
					'</td>'+
					'<td>'+
						'<div class="sku" id="sku'+count+'"></div>'+
						'<input type="hidden" name="transactionDetails['+count+'].sku"/>'+
					'</td>'+
					'<td class="editable">'+
						'<div class="autocomplete-block"">'+
						'<input placeholder="Type product.." id="product" name="transactionDetails['+count+'].itemId" type="text" class="product_name autocomplete"/>'+
						'</div>'+
					'</td>'+
					'<td align="right" class="number">'+
						'<input type="text" name="transactionDetails['+count+'].priceHelper" class="text-right prices focus-tab number-filter price validation" data-validation="required"  target-action="create_new_row(false)" disabled autocomplete="off"/>'+
						'<input type="hidden" name="transactionDetails['+count+'].price" class="real-price item-price"/>'+
// 						'<input type="text" class="text-right item-price focus-tab" name="transactionDetails['+count+'].price" target-action="create_new_row(true)" />'+
						'<input type="hidden" name="transactionDetails['+count+'].detailId" value="0" />'+
						'<input class="itemName" type="hidden" name="transactionDetails['+count+'].itemName" value="0" />'+
						'<input type="hidden" id="weight'+count+'"  name="transactionDetails['+count+'].weight" />'+
					'</td>'+
					'<td align="right" class="number">'+
						'<div class="subtotal price" id="subtotal'+count+'"></div>'+
						'<input type="hidden" name="transactionDetails['+count+'].subtotal"/>'+
					'</td>'+
					'<td align="center"><a style="visibility:hidden" id="delete'+count+'" class="delete-row" href="#hapus"><i class="icon-close"></i></a></td>'+
				'</tr>');
			if(populate == false){
				$("input.product_name:last").focus();
			}
	 		core.ui.dropdown();
		}
		
	}
	
	$(document).ready(function(){
		
		$("#refNumber").focus();
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

		$("body").on("keyup", "input.qty, input.unit, input.item-price, input.prices",function(e){
			var qty = $(this).parents("tr").find("input.qty").val();
			var price = $(this).parents("tr").find("td input.prices").val().split('.').join("");
			 $(this).parents("tr").find("td input.real-price").val(price);
			var item =  $(this).parents("tr").find('input.unit').val();
			var subtotal_int = isNaN(qty) || qty === "" ? 0 : ( parseInt(qty) * parseInt(item) * parseInt(price) );
			$(this).parents("tr").find("td div.subtotal").html(subtotal_int);
			$(this).parents("tr").find("td input[name=subtotal]").html(subtotal_int);
			refresh_total();
		});

		$("body").on("click","a.delete-row", function(e){
			$(this).parents("tr").fadeOut(function(e){
				$(this).remove();
				refresh_total();
				$("a.add_item").focus();
			});
		});

		$("a.add_item").click(function(e){
			create_new_row(false);
		});
				
		$("input.check_all").change(function(e){
			var is_checked = $(this).is(':checked');
			$(this).parents("table").find("tbody input[type=checkbox]").attr("checked", is_checked);
		});
		
		$("#supplier-goto").click(function(e){
			$.colorbox.close();
			
			$('#colorbox').promise().done(function() {
				$("#token-input-supplier").focus();
			});
		});

		<c:choose>
			<c:when test="${action.equals('edit') || form.supplierId != null}">
				supplierAutoComplete([{id:${form.supplierId}, name:"${form.supplierName}"}]);
			</c:when>
			<c:otherwise>
				supplierAutoComplete('');
			</c:otherwise>			
		</c:choose>
		
		
		loadCurrentData();
		
		$('body').on('change', 'select.unit', function(){
			refresh_total();
		});
		
		$('body').on('keydown', 'a.delete-row', function(event){
			if(event.which == 9){
				event.preventDefault();
				create_new_row(false);
				$("input.token-input-product:last").focus();
			}
			
		});
		
		$('#retail1').focus();
		$('#retail1').css('-webkit-box-shadow', 'rgba(50, 50, 50, 0.74902) 0px 0px 4px 0px');
		
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
		
	});
	
	function showTable(focusProduct){
		
		if( $("#supplier").tokenInput("get").length > 0){
			$("table.invoice_table").fadeIn(function(e){
				//reComputeWidthTable();
				if(focusProduct){
					$(".product_name").focus();
					create_new_row(false);
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
	
	function checkValidate(){
		var valid = true;
		var products = $(".product_name").prev().find("input:visible");
		if(products.length > 0){
			valid =  false;
			products[0].focus();
		}

		
		var qtys = $('.qty').filter(function(){return this.value=='0' || this.value==''});
		if(qtys.length > 0){
			valid =  false;
			qtys[0].focus();
			qtys.select();
		}
		
		return valid;
	}
	
	function loadCurrentData(){
		<c:forEach items="${form.transactionDetails}" var="detail">

			var itemId = "${detail.itemId}";
			if(itemId != ""){
				create_new_row(true);
				productAutoComplete($('input[name="transactionDetails['+count+'].itemId"]'), [{id:itemId, name:"<c:out value='${detail.itemName}' />"}]);

				$('input[name="transactionDetails['+count+'].itemName"]').val('${detail.itemName}');
				$('input[name="transactionDetails['+count+'].detailId"]').val('${detail.detailId}');
				$('input[name="transactionDetails['+count+'].inBox"]').val('${detail.inBox}');
	// 			$('input[name="transactionDetails['+count+'].itemId"]').val(${detail.itemId});
				$('input#weight'+count).val('${detail.weight}');
				$('input[name="transactionDetails['+count+'].quantity"]').val('${detail.quantity}');
				$('input[name="transactionDetails['+count+'].quantity"]').removeAttr('disabled');
				
				$('input[name="transactionDetails['+count+'].sku"]').val('${detail.sku}');
				$('input[name="transactionDetails['+count+'].priceHelper"]').val('${detail.price}');
				$('input[name="transactionDetails['+count+'].priceHelper"]').removeAttr('disabled');
				$('input[name="transactionDetails['+count+'].price"]').val('${detail.price}');
				$('input[name="transactionDetails['+count+'].subtotal"]').val('${detail.subtotal}');
				$('input[name="transactionDetails['+count+'].weight"]').val('${detail.weight}');
				$('input[name="transactionDetails['+count+'].unit"]').val('${detail.unit}');
				
				$('div#sku'+count).text('${detail.sku}');
				$('div#price'+count).text('${detail.price}');

				$('div#subtotal'+count).text( eval('${detail.price}') * eval('${detail.unit}') * eval('${detail.quantity}'));

				$('a.delete-row').css("visibility", "visible");
			}
		</c:forEach>
		
		refresh_total();

	}
</script>
