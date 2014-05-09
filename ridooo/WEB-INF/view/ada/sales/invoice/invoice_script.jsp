<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:url var="product_auto_complete" value="/sales/product/auto_complete" />
<c:url var="customer_auto_complete" value="/sales/customer/auto_complete" />
<c:url var="so_url" value="/sales/sales-orders/invoice" />
<c:url var="edit_status" value="/sales/invoice/edit/status/" />
<c:url var="dataAjax" value="/sales/invoice/ajax-data-notajual"/>
<c:url var="jsFile" value="/js/pagescroll.js"/>

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/" />
<c:set var="product_auto_complete" value="/sales/product/auto_complete" />
<c:set var="customer_auto_complete" value="/sales/customer/auto_complete" />
<c:set var="so_url" value="/sales/sales-orders/invoice" />
<c:set var="edit_status" value="/sales/invoice/edit/status/" />
<c:set var="dataAjax" value="/sales/invoice/ajax-data-notajual"/>
<c:set var="jsFile" value="/js/pagescroll.js"/>
</c:if>

<c:if test="${!empty limitDataPage}">
<script type="text/javascript">
	var urlAjax = '${dataAjax}';
	var nop = ${limitDataPage};
</script>
<script src="${jsFile}" type="text/javascript"></script>
</c:if>	

<script type="text/javascript">
	var sourcePrice = null;
	$(document).ready(
		function() {
			$("#inline").colorbox({
				inline : true,
				href : "#popup",
				height : 199,
			});

			$("body").on("focus","input.product_name", function(e) {

				if ($("input#customer").val() == "") {
					$(this).blur();
					$.colorbox({
						inline : true,
						height : 202,
						href : "#popup",
						onComplete : function(){
							$("#customer-goto").focus();
						}
					});
				} else {
					productAutoComplete($(this));
					$(this).parents("td").find("li.token-input-input-token input").focus();
				}

			});

			$("body").on("blur", "input.customer",function(e) {
				if ($(this).val() == "") {
					$("input#product").parent().find('ul.token-input-list').remove();
					$("input#product").show();
				}
			});

			$("body").on("keyup", "input.qty, #discount, #ppn", function(e){
				var qty = $(this).val();
				var price = $(this).parents("tr").find("td div.prices").text().split('.').join("");
				var item = $(this).parents("tr").find("td.item-container div.select-content input.select-choice").val();
				var subtotal_int = isNaN(qty) || qty === "" ? 0 : ( parseInt(qty) * parseInt(item) * parseInt(price) );
				console.log(parseInt(qty) +"|"+ parseInt(item) +"|"+ parseInt(price) )
				$(this).parents("tr").find("td div.subtotal").html(subtotal_int);
				$(this).parents("tr").find("td input[name='subtotal[]']").val(subtotal_int);
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
			});
			
			$("body").keydown(function(e){
// 				if(e.keyCode == 112){
// 					e.preventDefault();
// 					create_new_row();
// 				}
				if(e.ctrlKey && e.which == 80){
					$("#ui-datepicker-div").remove();
				}
				
				if(e.which == 116){		
					e.preventDefault();
					if ($('#popup').parent().is(':visible')){
						window.location = $('#create').attr('href');
					}
					else{
						$('#inline').click();
					}
					
					
				}
			});
			
			$("#customer-goto").click(function(e){
				$.colorbox.close();
				$('#colorbox').promise().done(function(){
					$("input#token-input-customer").focus();
				});
			});
			
			$("#inv_so").click(function(e){
				$.colorbox({href:'${so_url}', width:"700px"});
			});
			
			$("a.paid_all").click(function(e){
				$("form.invoice_action").attr("action", "${edit_status}1");
				$("form.invoice_action").submit();
			});

			$("a.hold_all").click(function(e){
				$("form.invoice_action").attr("action", "${edit_status}2");
				$("form.invoice_action").submit();
			});

			$("a.cancel_all").click(function(e){
				$.colorbox({
					inline : true,
					href : "#cancel-form-alert",
					onComplete : function(){
						$("form.invoice_action").attr("action", "${edit_status}-1");

						$("#cancel-form-alert a.cancel-form-button").click(function(e){
							e.preventDefault();
							$("form.invoice_action").submit();
						});
					}
				});
			});
			
			$("table").on("change", "input:checkbox", function(e){

				if($("table input:checkbox:checked").length > 0){
					$("#tools").children("a").html("Tools");
					$("#tools").find("div.disabled, a.disabled").removeClass("disabled").addClass("netral");
					$("#tools").find("i.icon-dropdown").removeClass("disabled").addClass("icon-grey");
					$("#tools").find("div.netral").addClass("dropdown");
					
					//set avalaible tool
					//first make tools all avalaible
					$("#tools ul li").show();
					
					if ( $("input.check[data-status='-1']:checkbox:checked").length > 0){
						$("#tools ul li:not(.print)").hide();
					} 
					
					if ( $("input.check[data-status='2']:checkbox:checked").length > 0){
						$("#tools ul li").show();
						$("#tools ul li.tunda").hide();
					} 
					
					if ( $("input.check[data-status='1']:checkbox:checked").length > 0){
						$("#tools ul li").show();
						$("#tools ul li:not(.batal, .print)").hide();
					} 
					

				}else{
					$("#tools").children("a").html("Select item(s)");
					$("#tools").find("div.netral, a.netral").removeClass("netral").addClass("disabled");
					$("#tools").find("i.icon-dropdown").addClass("disabled");
					$("#tools").find("div.disabled").removeClass("dropdown");
				}

			});
			
			<c:choose>
				<c:when test="${action.equals('edit')}">
					customerAutoComplete();
					$(".product_name").each( function() {
						productAutoComplete($(this));
					});
					$( ".datepicker" ).datepicker( "setDate", "${data.deliveryDate}" );
// 					refresh_total();
				</c:when>
				<c:when test="${!empty inputold.customer}">
					customerAutoComplete();
					$(".product_name").each( function() {
						productAutoComplete($(this));
					});
					$( ".datepicker" ).datepicker( "setDate", "${inputold.senddate[0]}" );
				</c:when>
				<c:when test="${action.equals('create') || action.equals('create-fake')}">

					customerAutoComplete();
					var today = new Date();
					var dd = today.getDate();
					var mm = today.getMonth()+1; //January is 0!
	
					var yyyy = today.getFullYear();
					if(dd<10){dd='0'+dd} if(mm<10){mm='0'+mm} today = dd+'/'+mm+'/'+yyyy;
					$( ".datepicker" ).datepicker( "setDate", today );
				</c:when>
				<c:otherwise>
					//productAutoComplete($(".product_name"));
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
					var data_token = $(this).parents("td");
					var token = $(this).parents("td").find("input.product_name");
					currentData = data_token.data("current-data");
					console.log(  currentData );
					if(currentData != null && currentData != ""){

						//add current data to token with fake pricing data, to avoid error on prodct auto complete add callback
						token.tokenInput("add", {id: currentData.id, name: currentData.name , pricing: [{price: currentData.pricing[0].price}], weight : currentData.weight });
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
// 				var token = $(this).parents("td").find("input.product_name");
				var token = $(this).parents("td").find("input.product_name");
				//get input 
				token.tokenInput("clear");
			});
			
			$("body").on("click", "td div.select-content ul li", function(e){
				refresh_total();
			});
			
			$("table").on("click", "a.change-price", function(e){
				var token = $(this).parents("tr").find("input.product_name");
				var currentPrice = $(this).parents("tr").find("input[name='price[]']").val();
				sourcePrice = $(this).parents("td").find("p.prices");
				//get input 
				currentData = token.tokenInput("get")[0];
				$.colorbox({href:"${baseURL}/sales/invoice/pricehistory/"+currentData.id+"/"+currentPrice,
							onComplete: function(e){
								core.ui.currency();
							}});
			});
			

			$("body").on("click", "div.history td a", function(e){
				sourcePrice.text($(this).text());
				sourcePrice.parents("tr").find("input[name='price[]']").val($(this).text().split('.').join(""));
				sourcePrice.parents("tr").find("input.qty").keyup();
				$.colorbox.close();
				sourcePrice = null;
			});

			$("body").on("click","a.cancel", function(e){
				if(!$(this).hasClass("cancel_all")){
					e.preventDefault();
					$.colorbox({
						inline : true,
						href : "#cancel-alert",
// 						height : 202,
					});
					$("#cancel-alert a.cancel-button").attr('href', $(this).attr("href"));
				}
			});
			
			$("#useppn").change(function(e){
				var is_checked = $(this).is(":checked");

				if(is_checked){
					$("#ppn-container").slideDown( function(e){
						$("#ppn").prop("disabled", false);
						$("#taxNumber").prop("disabled", false);
					});
				}else{
					$("#ppn-container").slideUp(function(e){
						$("#ppn").prop("disabled", true);
						$("#taxNumber").prop("disabled", true);
					});
				}
			});
			$("#useppn").keydown(function(e){
				if(e.keyCode == 9){
					$("#shopcode").select();
				}
			});
			
			$("input:checkbox").on("focus", function(e){
				$(this).attr("style", "-webkit-box-shadow : rgba(50, 50, 50, 0.74902) 0px 0px 4px 0px;");
			});
			
	});

	function productAutoComplete(element) {
		var width = element.parents("td").width();
		isFirst = false;

		core.ui.autocomplete({
			data : "${product_auto_complete}",
			prePopulate : eval(element.val()), // required
			hintText : 'Silahkan ketikkan nama dari product....',
			onDelete : null,
			onAdd : function(data) {
				
				console.log(data);
				$(this).parents("td").data("current-data", data);
// 				var request = $.ajax({
// 					  url: "${baseURL}/sales/invoice/checkpricehistory/"+data.id+"/"+data.pricing[0].price,
// 					  type: "get",
// 					  dataType: "text",
// 					  async: false
// 				});

// 				request.done(function( msg ) {
// 					if(msg == "true"){
// 						element.parents('tr').find("a.change-price").css("visibility", "visible");
// 					}
// 			  	});

				$("input#productname").val(data.name);

// 				if(data.pricing.length == 0){
// 					$(this).tokenInput("clear");
// 					alert("Barang belum memiliki daftar harga");
// 					return true;
// 				}
				var parent_tr = $(this).parents("tr");
				var selected_price = $("#priceList").val();
				parent_tr.find("div.sku").html(data.sku);
				parent_tr.find("input[name='sku[]']").val(data.sku);
				parent_tr.find("div.prices").text(data.pricing[selected_price].price);
				parent_tr.find("input[name='price[]']").val(data.pricing[selected_price].price);
				parent_tr.find("input.weight").val(data.weight);

				//refresh unit
				var target_span = $(this).parents("tr").find("td.item-container span");
				var select = "<select class='select unit focus-tab '  target-action='create_new_row()'  name='unit[]' >";
				select += "<option value='1'>Biji</option>";
				if(data.unit != 0 ){
					select += "<option value='" + data.unit + "'>Box [" + data.unit + "]</option>";
				}
				select += "</select>";
				target_span.html(select);
				core.ui.dropdown();

				//request focus to qty_input
				parent_tr.find("input.qty").attr("disabled", false);
				parent_tr.find("input.qty").val(1);
				parent_tr.find("input.qty").focus();
				parent_tr.find("input.qty").select();
				parent_tr.find("input.qty").keyup();

				$("a.delete-row").css("visibility", "visible");

				refresh_total();
			},
			onResult : null,
			onReady : function() {
				<c:if test="${action.equals('edit')}">
					var data = element.attr("value");
					data = eval(data);
					element.parents("td").data("current-data", eval(data[0]) );
				</c:if>
			},
			minChars : 1,
		    tokenFormatter: function(item) { return "<li><h6>" + item[this.propertyToSearch] + "</h6></li>" },
		    resultsFormatter: function(item){ return "<li><div style='width:100%'><span style='display:block' >" + item.name + "</span><span style='display: block;font-size: 10px;color: #6D6D6B;'>" +item.sku+ "</span></div></li>" },
			width : width
		}, element);

	}

	function customerAutoComplete() {
		core.ui.autocomplete({
			data : "${customer_auto_complete}",
			<c:choose>
				<c:when test="${action.equals('edit')}">
					prePopulate : [{id: ${data.customerId} , name: "${data.customerName}"}],
				</c:when>
				<c:when test="${!empty inputold.customer && inputold.customer[0] != ''}">
					prePopulate : [{id: ${inputold.customer[0] } , name: "${inputold.customerName[0]}"}],
				</c:when>
				<c:otherwise>
					prePopulate : "",
				</c:otherwise>
			</c:choose>
			hintText : 'Silahkan ketikkan nama dari product....',
			onDelete : null,
			onAdd : function(data){
				showTable(false);
				$("#customerPO").focus();
				$("#address").text(data.address);
				$("#customerName").val(data.name);
			},
			onResult : null,
			onReady : function(e){
				$("#token-input-customer").focus();
			},
			target_element : '#customer',
			minChars : 1,
			width : $('.inputholder').outerWidth(),
			left : $('.inputholder').offset().left,
		});
	}

	function refresh_total(){
		var total_price = 0;
		var total_item  = 0;
		var total_weight  = 0;
		$("td div.subtotal").each(function( i ) {
			var val = $(this).text().split('.').join("");
			val = val == "" ? 0 : val;
// 			console.log(val);
			total_price += parseInt(val);
		});

		$("td input.qty").each(function( i ) {
			var qty = $(this).val();
			var item = $($("td.item-container div.select-content input.select-choice")[i]).val();
			val = qty * item;
			total_item += parseInt(val);
		});

		$("td input.weight").each(function( i ) {
			var weight = $(this).val();
			var qty = $(this).parents("tr").find("td input.qty").val();
			var item = $($("td.item-container div.select-content input.select-choice")[i]).val();
			val = weight * item * qty;
			total_weight += Math.round(val * 10) / 10;
		});
		//set ppn and discount
		var discount = $("#discount").val();
		var ppn = $("#ppn").val();
		var percent = ppn.match(/%/);
		if(percent){
			ppn = ppn.replace("%","");
			ppn = isNaN(ppn) ? 0 : ppn;
			ppn = (total_price * parseInt(ppn) ) / 100;
		}
		ppn = isNaN(ppn) ? 0 : ppn;
		total_price = total_price + parseInt(ppn);
		
		percent = discount.match(/%/);
		if(percent){
			discount = discount.replace("%","");
			discount = isNaN(discount) ? 0 : discount;
			discount = (total_price * parseInt(discount) ) / 100;
		}
		discount = isNaN(discount) ? 0 : discount;
		total_price = total_price - parseInt(discount);
		
		$("span.total-price").html(total_price);
		$("input[name='total-price']").val(total_price);

		$("span.total-weight").html(total_weight);
		$("input[name='total-weight']").val(total_weight);
		$("span.total-item").html(total_item);
		$("input[name='total-item']").val(total_item);

		core.ui.currency();
	}

	function create_new_row(){
		if(checkValidate()){
			$('table.invoice_table tbody').append('<tr>'+
					'<td align="right" class="borderright editable">'+
					'<input type="text" name="quantity[]"  class="qty focus-tab short focus-tab text-right number-filter validation" data-validation="required" target-type="select" target-tab=".unit" target-tr="true"  disabled/>'+
					'<input type="hidden" name="weight[]"  class="weight"/></td>'+
					'<td class="item-container">'+
						'<span>'+
						'</span>'+
					'</td>'+
					'<td><div class="sku"></div><input type="hidden" name="sku[]"/>'+
					'<td class="editable"><input placeholder="Type product.." name="itemId[]" type="text" class="product_name autocomplete"/><input type="hidden" id="productname" name="productname[]" /></td>'+
					'<td align="right" ><a class="change-price" style="visibility:hidden"><i class="icon-dollar fr"></i></a>&nbsp;<div class="number price prices fr"></div><input type="hidden" name="price[]"/></td>'+
					'<td align="right" class="number"><div class="subtotal price"></div><input type="hidden" name="subtotal[]"/></td>'+
					'<td align="center"><a style="visibility:hidden" class="delete-row" href="#hapus"><i class="icon-close"></i></a></td>'+
				'</tr>');
			$("input.product_name:last").focus();
			core.ui.dropdown();
		}
	}

	function showTable(focusProduct){
		
		if( $("#customer").tokenInput("get").length > 0){
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
// 				height : 194,
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

</script>
