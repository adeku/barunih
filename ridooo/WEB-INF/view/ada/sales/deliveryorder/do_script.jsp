<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="product_auto_complete" value="/sales/product/auto_complete" />
<c:url var="customer_auto_complete" value="/sales/customer/auto_complete" />
<c:url var="courier_auto_complete" value="/sales/courier/auto_complete" />
<c:url var="so_url" value="/sales/invoice/deliveryorder" />
<c:url var="edit_status" value="/sales/deliveryorder/edit/status/" />
<c:url var="base_url" value="/" />
<c:url var="ajaxData" value="/sales/deliveryorder/ajax-data-deliveryorder"/>
<c:url var="jsFile" value="/js/pagescroll.js"/>


<c:if test="${base_url=='//'}">
<c:set var="product_auto_complete" value="/sales/product/auto_complete" />
<c:set var="customer_auto_complete" value="/sales/customer/auto_complete" />
<c:set var="courier_auto_complete" value="/sales/courier/auto_complete" />
<c:set var="so_url" value="/sales/invoice/deliveryorder" />
<c:set var="edit_status" value="/sales/deliveryorder/edit/status/" />
<c:set var="base_url" value="/" />
<c:set var="ajaxData" value="/sales/deliveryorder/ajax-data-deliveryorder"/>
<c:set var="jsFile" value="/js/pagescroll.js"/>
</c:if>


<c:if test="${!empty limitDataPage}">
<script type="text/javascript">
var urlAjax = '${ajaxData}';
var nop = ${limitDataPage};
</script>

<script src="${jsFile}" type="text/javascript"></script>
</c:if>	

<script type="text/javascript">
	var isFirst = null;
	$(document).ready(
		function() {
			$("#inline").colorbox({
				inline : true,
				href : "#popup",
				height : 160,
			});

			$("body").on("click","a.cancel", function(e){
				if(!$(this).hasClass("cancel_all")){
					e.preventDefault();
					$.colorbox({
						inline : true,
						href : "#cancel-alert",
					});
					$("#cancel-alert a.cancel-button").attr('href', $(this).attr("href"));
				}else{
					e.preventDefault();
					$.colorbox({
						inline : true,
						href : "#cancel-form-alert",
						onComplete : function(){
							$("form.deliveryorder_action").attr("action", "${edit_status}-1");

							$("#cancel-form-alert a.cancel-form-button").click(function(e){
								e.preventDefault();
								$("form.deliveryorder_action").submit();
							});
						}
					});
				}
			});

			$("body").on("click","a.paid_all", function(e){
				$("form.deliveryorder_action").attr("action", "${edit_status}1");
				$("form.deliveryorder_action").submit();
			});

			$("body").on("focus","input.product_name", function(e) {
				if ($("input#customer").val() == "") {
					$(this).blur();
					$.colorbox({
						inline : true,
						href : "#popup",
						height : 207,
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

			$("body").on("keyup", "input.qty",function(e){
				var qty = $(this).val();
				var price = $(this).parents("tr").find("td span.price").text();
				var item = $(this).parents("tr").find("td.item-container div.select-content input.select-choice").val();
				var subtotal_int = isNaN(qty) || qty === "" ? 0 : ( parseInt(qty) * parseInt(item) * parseInt(price) );
				console.log(parseInt(qty) +"|"+ parseInt(item) +"|"+ parseInt(price) )
				$(this).parents("tr").find("td span.subtotal").html(subtotal_int);
				$(this).parents("tr").find("td input[name='subtotal[]']").val(subtotal_int);
				refresh_total();
			});

			$("body").on("click","a.delete-row", function(e){
				$(this).parents("tr").fadeOut(function(e){
					$(this).remove();
				});
			});

			$("a.add_item").click(function(e){
				create_new_row();
			});
			
			$("body").keydown(function(e){
// 				if(e.keyCode == 116){
// 					e.preventDefault();
// 					create_new_row();
// 				}
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
			
			$("input.check_all").change(function(e){
				var is_checked = $(this).is(':checked');
				$(this).parents("table").find("tbody input[type=checkbox]").attr("checked", is_checked);
			});
			
			$("#customer-goto").click(function(e){
				$.colorbox.close();
				$("#token-input-customer").focus();
			});
			
			$("#inv_so").click(function(e){
				$.colorbox({href:'${so_url}', width:"700px"});
			});
			
			$("input.check_all").change(function(e){
				var is_checked = $(this).is(':checked');
				$(this).parents("table").find("tbody input[type=checkbox]").attr("checked", is_checked);
			});
			
			$("a.paid_all").click(function(e){
				$("form.invoice_action").attr("action", "${edit_status}3");
				$("form.invoice_action").submit();
			});
			
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
			
			customerAutoComplete();
			courierAutoComplete();

			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth()+1; //January is 0!

			var yyyy = today.getFullYear();
			if(dd<10){dd='0'+dd} if(mm<10){mm='0'+mm} today = dd+'/'+mm+'/'+yyyy;
			$( ".datepicker" ).datepicker( "setDate", today );
			
			<c:if test="${action.equals('edit')}">
				productAutoComplete($(".product_name"));
				$( ".datepicker" ).datepicker( "setDate", "${data.deliveryDate}" );
// 				refresh_total();
			</c:if>
			

			<c:if test="${!empty inputold.customer}">
					$(".product_name").each( function() {
						productAutoComplete($(this));
					});
					$( ".datepicker" ).datepicker( "setDate", "${inputold.senddate[0]}" );
			</c:if>
			
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
					var token = $(this).parents("td").find("input.product_name");
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
				var token = $(this).parents("td").find("input.product_name");
				//get input 
				console.log("token = " + token.prop("tagName"));
				currentData = token.tokenInput("get")[0];
				token.tokenInput("clear");
				token.data("current-data", currentData);
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
			
			refresh_total();

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

				if(data.pricing.length == 0){
					$(this).tokenInput("clear");
					alert("Barang belum memiliki daftar harga");
					return true;
				}
				
				var parent_tr = $(this).parents("tr");
				parent_tr.find("span.sku").html(data.sku);
				parent_tr.find("input[name='sku[]']").val(data.sku);
// 				parent_tr.find("span.price").html(data.pricing[0].price);
// 				parent_tr.find("input[name='price[]']").val(data.pricing[0].price);
				parent_tr.find("input.weight").val(data.weight);
				parent_tr.find("td.weight-column").text(data.weight);
				
				//refresh unit
				var target_span = $(this).parents("tr").find("td.item-container span");
				var select = "<select class='select unit focus-tab ' target-tab='.box-no' target-tr='true' name='unit[]' >";
				select += "<option value='1'>Biji</option>";
				if(data.unit != 0 ){
					select += "<option value='" + data.unit + "'>Box [" + data.unit + "]</option>";
				}

				select += "</select>";
				target_span.html(select);
				core.ui.dropdown();

				//request focus to qty_input
				parent_tr.find("input.qty").attr("disabled",false);
				parent_tr.find("input.qty").val("1");
				parent_tr.find("input.qty").select();
				parent_tr.find("input.qty").focus();

			},
			onResult : null,
			onReady : null,
		    tokenFormatter: function(item) { return "<li><h6>" + item[this.propertyToSearch] + "</h6></li>" },
		    resultsFormatter: function(item){ return "<li><div style='width:100%'><span style='display:block' >" + item.name + "</span><span style='display: block;font-size: 10px;color: #6D6D6B;'>" +item.sku+ "</span></div></li>" },
		    width : width
		}, element);
	}

	function customerAutoComplete() {
		core.ui.autocomplete({
			data : "${customer_auto_complete}",
			/* <c:choose>
				<c:when test="${action.equals('edit')}">
					prePopulate : [{id: ${data.customerId} , name: "${data.customerName}"}],
				</c:when>
				<c:when test="${!empty inputold.customer && inputold.customer[0] != '' }">
					prePopulate : [{id: ${inputold.customer[0]} , name: "${inputold.customerName[0]}"}],
				</c:when>
				<c:otherwise>
					prePopulate : "",
				</c:otherwise>
			</c:choose> */
			hintText : 'Silahkan ketikkan nama dari costomer....',
			onDelete : null,
			onAdd : function(data){
				$("#cust-address").text(data.address);
				$("#customerName").val(data.name);
				$("#token-input-courier").focus();
			},
			onResult : null,
			onReady : function(data){
				$("#token-input-customer").focus();
			},
			target_element : '#customer',
			minChars : 1,
			width : $('.inputholder').outerWidth(),
			left : $('.inputholder').offset().left,
		});
	}
	
	function courierAutoComplete() {
		core.ui.autocomplete({
			data : "${courier_auto_complete}",
			<c:choose>
				<c:when test="${action.equals('edit') && data.courierID!=null}">
					prePopulate : [{id: ${data.courierID} , name: "${data.courierName}"}],
				</c:when>
				<c:when test="${!empty inputold.courier && inputold.courierName[0] != '' }">
					prePopulate : [{id: ${inputold.courier[0]} , name: "${inputold.courierName[0]}"}],
				</c:when>
				<c:otherwise>
					prePopulate : "",
				</c:otherwise>
			</c:choose>
			hintText : 'Silahkan ketikkan nama dari courier....',
			onDelete : null,
			onAdd : function(data){
				$("#eks-address").text(data.address);
				$("#courierName").val(data.name);
				$("#assurance").focus();
			},
			onResult : null,
			onReady : null,
			target_element : '#courier',
			minChars : 1,
			width : $('.inputholder').outerWidth(),
			left : $('.inputholder').offset().left,
		});
	}

	function refresh_total(){
		var total_price = 0;
		var total_item  = 0;
		var total_weight  = 0;
		$("td span.subtotal").each(function( i ) {
			var val = $(this).text();
			val = val == "" ? 0 : val;
			total_price += parseInt(val);
		});

		$("td input.qty").each(function( i ) {
			var qty = $(this).val();
			var item = $("input[name='unit[]']").eq(i).val();
			val = qty * item;
			total_item += parseInt(val);
		});

		$("td input.weight").each(function( i ) {
			console.log(i);
			var weight = $(this).val();
			var qty = $(this).parents("tr").find("td input.qty").val();
			var item = $("input[name='unit[]']").eq(i).val().trim();
			val = weight * item * qty;
			total_weight += Math.round(val * 10) / 10;
		});
		
		$("span.total-price").html(total_price);
		$("input[name='total-price']").val(total_price);

		$("span.total-weight").html(total_weight);
		$("input[name='total-weight']").val(total_weight);

		$("span.total-item").html(total_item);
		$("input[name='total-item']").val(total_item);
	}

	function create_new_row(){
		$('table.invoice_table tbody').append('<tr>'+
				'<td align="right" class="borderright editable">'+
				'<input type="text" name="quantity[]"  class="short qty focus-tab text-right number-filter validation" data-validation="required"  target-type="select" target-tab=".unit" target-tr="true"  disabled/>'+
				'<input type="hidden" name="weight[]"  class="weight"/></td>'+
				'<td class="item-container">'+
					'<span>'+'</span>'+
				'</td>'+
				'<td><span class="sku"></span><input type="hidden" name="sku[]"/></td>'+
				'<td><input placeholder="Type product.." name="itemId[]" type="text" class="product_name autocomplete"/></td>'+
				'<td class="editable"><input type="text" name="boxNumber[]" class="medium box-no focus-tab" target-action="create_new_row()" /></td>'+
				'<td class="weight-column"></td>'+
				'<td align="right"><a class="delete-row" href="#hapus"><i class="icon-close"></i></a></td>'+
			'</tr>');
		$("input.product_name:last").focus();
		core.ui.dropdown();
	}
	
	/* function submit_form(){

		$.each ( $('form.invoice_detail input, form.invoice_detail select, form.invoice_detail textarea').serializeArray(), function ( i, obj ) {
			  $('<input type="hidden">').prop( obj ).appendTo( $('form.invoice') );
		});
		
		$('form.invoice').submit();

	} */
	
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
			});
		}
	}
</script>
