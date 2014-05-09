<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/>
<c:url var="product_auto_complete" value="/sales/product/auto_complete"/>
<c:url var="customer_auto_complete" value="/sales/customer/auto_complete" />

<c:if test="${baseURL=='//'}">
<c:set var="product_auto_complete" value="/sales/product/auto_complete"/>
<c:set var="customer_auto_complete" value="/sales/customer/auto_complete" />
</c:if>

<script>
	var count = -1;
	var isFirst = false;
	
	function setSelectOption(select, boxOption){
		select.addClass("select");
		select.next().remove();
		select.find("option").remove();
		select.append('<option value="1">Biji</option><option value="'+ boxOption +'">Box ['+ boxOption +']</option>');
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
	
				if(data.pricing.length == 0){
					$(this).tokenInput("clear");
					alert("Barang belum memiliki daftar harga");
					return true;
				}
				
				var parent_tr = $(this).parents("tr");
				var id = parent_tr.attr('id');
				parent_tr.find("input#weight"+id).val(data.weight);
				parent_tr.find("div.sku").html(data.sku);
				parent_tr.find("input[name='transactionDetails["+id+"].sku']").val(data.sku);
// 				parent_tr.find("span.price").html(data.pricing[0].price);
// 				parent_tr.find("input[name='transactionDetails["+id+"].price']").val(data.pricing[0].price);
				
				console.log('unit'+id);
				setSelectOption($("select[name='transactionDetails["+id+"].unit']"), data.unit);
				
// 				core.ui.dropdown();
	
				//trigger click to item select
				var item_opt = parent_tr.find("td.item-container .select-content").click();
	
				//request focus to qty_input
				parent_tr.find("input.qty").attr("disabled",false);
				parent_tr.find("input.qty").val(1);
				parent_tr.find("input.qty").select();
				
				parent_tr.find("input.prices").attr("disabled",false);
// 				count ++;
		 		core.ui.dropdown();
			},
			onResult : null,
			onReady : null,
			minChars : 1,
			target_element : '.produk_name',
		    tokenFormatter: function(item) { return "<li><h6>" + item[this.propertyToSearch] + "</h6></li>" },
		    resultsFormatter: function(item){ return "<li><div style='width:100%'><span style='display:block' >" + item.name + "</span><span style='display: block;font-size: 10px;color: #6D6D6B;'>" +item.sku+ "</span></div></li>" },
			width : width
		}, element);
	}
	
	
	
	function customerAutoComplete(prepopulateData) {
		core.ui.autocomplete({
			data : "${customer_auto_complete}",
			prePopulate : prepopulateData, // required
			hintText : 'Silahkan ketikkan nama pelanggan....',
			onDelete : function(){
				$('textarea.address').val('');
			},
			onAdd : function(data){
				console.log("datam"+data.address);
				showTable(true);
// 				showTable(false);
				$('textarea.address').val(data.address);
				if($("#salesPersonId option[value='"+data.salesId+"']").length > 0){
					$('#salesPersonId').val(data.salesId);
				}
				$("#warehouseId").parent().click();
				$("#customerName").val(data.name);
				$('#customerPO').focus();
			},
			onResult : null,
			onReady : null,
			target_element : '#customer',
			minChars : 1,
			width : $('.inputholder').outerWidth(),
			left : $('.inputholder').offset().left,
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
		
		count++;
		$("input").blur();
		$('table.invoice_table tbody').append('<tr id="'+count+'">'+
				'<td align="right" class="borderright editable"><input type="text" name="transactionDetails['+count+'].quantity" class="qty short focus-tab text-right number-filter validation" data-validation="required"   target-type="select" target-tab=".unit" target-tr="true" disabled/></td>'+
				'<td class="item-container">'+
					'<input class="focus-tab unit unit-tab short validation" data-validation="required"  type="text" name="transactionDetails['+count+'].unit"  target-tab=".prices" target-tr="true" />'+
				'</td>'+
				'<td  class=" editable">'+
					'<div class="sku" id="sku'+count+'"></div>'+
					'<input type="hidden" name="transactionDetails['+count+'].sku"/>'+
				'</td>'+
				'<td class="editable">'+
					'<div class="autocomplete-block">'+
						'<input placeholder="Type product.." name="transactionDetails['+count+'].itemId" type="text" class="product_name autocomplete"/>'+
					'</div>'+
				'</td>'+
				'<td align="right" class="number">'+
					'<input type="text" name="transactionDetails['+count+'].priceHelper" class="text-right prices focus-tab number-filter price validation" data-validation="required"  target-action="create_new_row(false)" disabled autocomplete="off"/>'+
					'<input type="hidden" name="transactionDetails['+count+'].price" class="real-price"/>'+
				'</td>'+
				'<td align="right" class="number">'+
					'<div class="subtotal price" id="subtotal'+count+'"></div>'+
					'<input type="hidden" name="transactionDetails['+count+'].subtotal"/>'+
					'<input type="hidden" id="weight'+count+'" />'+
					'<input type="hidden" name="transactionDetails['+count+'].detailId" value="0" />'+
				'</td>'+
				'<td align="center"><a class="delete-row" href="#hapus"><i class="icon-close"></i></a></td>'+
			'</tr>');
		if(populate == false)
			$("input.product_name:last").focus();
		core.ui.dropdown();
	}
	
	$(document).ready(function(){
		
		$("input[name=refNumber]").focus();
		$("#inline").colorbox({
			inline : true,
			href : "#popup"
		});

		$("body").on("focus","input.product_name", function(e) {
			if ($("input#customer").val() == "") {
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

		$("body").on("blur", "input.customer",function(e) {
			if ($(this).val() == "") {
				$("input#product").parent().find('ul.token-input-list').remove();
				$("input#product").show();
			}
		});

		$("body").on("keyup", "input.qty, input.prices, input.unit",function(e){
			var qty = $(this).parents("tr").find("td input.qty").val();
			var price = $(this).parents("tr").find("td input.prices").val().split('.').join("");
			var item = $(this).parents("tr").find("input.unit").val();
			var subtotal_int = 0;
			if(isNaN(qty) || qty === "" || isNaN(price) || price === "")
				subtotal_int = 0;
			else
				subtotal_int = isNaN(qty) || qty === "" ? 0 : ( parseInt(qty) * parseInt(item) * parseInt(price) );
			console.log(parseInt(qty) +"|"+ parseInt(item) +"|"+ parseInt(price) )
			$(this).parents("tr").find("td div.subtotal").html(subtotal_int);
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
			create_new_row(false);
		});
		
// 		$("body").keydown(function(e){
// 			if(e.keyCode == 116){
// 				e.preventDefault();
// 				create_new_row();
// 			}
// 		});
		
		$("input.check_all").change(function(e){
			var is_checked = $(this).is(':checked');
			$(this).parents("table").find("tbody input[type=checkbox]").attr("checked", is_checked);
		});
		
		$("#customer-goto").click(function(e){
			$.colorbox.close();
			$("#token-input-customer").focus();
		});
		
		<c:choose>
			<c:when test="${!action.equals('edit')}">
				customerAutoComplete('');
			</c:when>
			<c:otherwise>
				customerAutoComplete([{id:${form.customerId}, name:"${form.customerName}"}]);
				$("textarea.address").text('${form.customerAddress}');
			</c:otherwise>
		</c:choose>
		
		loadCurrentData();
		
		//token input text
		$('table').on("focus", '.token-input-list input[type="text"], .qty, .prices, .unit-tab', function() {
			var add_btn = $("a.add_item");
			$(this).parents("td").addClass("yellowbg");
			if(add_btn.length > 0 && add_btn.is(":visible")){
				add_btn.parents("tfoot").hide();
			}
		});
		
		$('table').on("blur", '.token-input-list input[type="text"], .qty, .prices, .unit-tab', function() {
			var add_btn = $("a.add_item");
			$(this).parents("td").removeClass("yellowbg ");
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
				var token = $(this).parents("div.autocomplete-block").find("input.product_name");
				currentData = token.data("current-data");
				if(currentData != null && currentData != ""){
					//add current data to token with fake pricing data, to avoid error on prodct auto complete add callback
					var select_unit = $(this).parents("tr").find("td.item-container select");
					$(this).parents("tr").children("td.item-container").html("<select  target-tab='.prices' target-tr='true' class='"+select_unit.attr('class')+"' name='"+select_unit.attr('name')+"'></select>")
					token.tokenInput("add", {id: currentData.id, name: currentData.name, pricing : [ 0 , 1] });
					//remove td yellow hover statetoken
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

		$('table').on("keyup", 'input.prices', function(e) {
			var text = $(this).val();
			$(this).siblings("input.real-price").val(text.split('.').join(""));
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
			var token = $(this).parents("div.autocomplete-block").find("input.product_name");
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
		
		if( $("#customer").tokenInput("get").length > 0){
			$("table.invoice_table").fadeIn(function(e){
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
