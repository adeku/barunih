<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="product_auto_complete" value="/sales/product/auto_complete" />
<c:url var="customer_auto_complete" value="/sales/customer/auto_complete" />
<c:url var="so_url" value="/sales/sales-orders/invoice" />
<c:url var="edit_status" value="/sales/invoice/edit/status/" />
<c:url var="URL" value="/"/>
<c:url var="ajaxData" value="/purchase/receive-orders/ajax-data-receiveorder"/>
<c:url var="jsFile" value="/js/pagescroll.js"/>

<c:if test="${URL=='//'}">
<c:set var="product_auto_complete" value="/sales/product/auto_complete" />
<c:set var="customer_auto_complete" value="/sales/customer/auto_complete" />
<c:set var="so_url" value="/sales/sales-orders/invoice" />
<c:set var="edit_status" value="/sales/invoice/edit/status/" />
<c:set var="URL" value="/"/>
<c:set var="ajaxData" value="/purchase/receive-orders/ajax-data-receiveorder"/>
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
	$(document).ready(
		function() {
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
				if(e.keyCode == 116){
					e.preventDefault();
					create_new_row();
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
			
// 			customerAutoComplete();
// 			productAutoComplete($(".product_name"));
			
/* 			<c:if test="${action.equals('edit')}">
				$( ".datepicker" ).datepicker( "setDate", "${data.deliveryDate}" );
				refresh_total();
			</c:if> */

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
			
			$("body").on("click","button.cancel", function(e){
				e.preventDefault();
				$.colorbox({
					inline : true,
					href : "#cancel-form-alert",
				});
				$("#cancel-form-alert a.cancel-form-button").attr('id', 'cancel-form');
			});

			$("body").on("click", "a#cancel-form", function(e){
				e.preventDefault();
				$('form').append('<input type="hidden" name="button" value="cancel" />');
				$('form').submit();
			});
	});
/* 
	function productAutoComplete(element) {
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
				parent_tr.find("span.price").html(data.pricing[0].price);
				parent_tr.find("input[name='price[]']").val(data.pricing[0].price);

				//trigger click to item select
				var item_opt = parent_tr.find("td.item-container .select-content").click();

				//request focus to qty_input
				parent_tr.find("input.qty").attr("disabled",false);
				parent_tr.find("input.qty").focus();
			},
			onResult : null,
			onReady : null,
			minChars : 1
		}, element);
	}

	function customerAutoComplete() {
		
		core.ui.autocomplete({
			data : "${customer_auto_complete}",
			<c:choose>
				<c:when test="${action.equals('edit')}">
					prePopulate : [{id: ${data.customerId} , name: "${data.customerName}"}],
				</c:when>
				<c:otherwise>
					prePopulate : "",
				</c:otherwise>
			</c:choose>
			hintText : 'Silahkan ketikkan nama dari product....',
			onDelete : null,
			onAdd : null,
			onResult : null,
			onReady : null,
			target_element : '#customer',
			minChars : 1,
			width : $('.inputholder').outerWidth(),
			left : $('.inputholder').offset().left,
		});
	} */
	/* 
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
		$("input[name='total-price']").val(total_price);

		$("span.total-item").html(total_item);
		$("input[name='total-item']").val(total_item);
	} */
/* 
	function create_new_row(){
		$('table.invoice_table tbody').append('<tr>'+
				'<td align="right" class="borderright"><input type="text" name="quantity[]"  class="qty" disabled/></td>'+
				'<td class="item-container">'+
					'<span>'+
						'<select class="select" name="unit[]">'+
						'<option value="48">[48]</option>'+
						'<option value="46">[46]</option>'+
						'<option value="44">[44]</option>'+
						'</select>'+
					'</span>'+
				'</td>'+
				'<td><span class="sku"></span><input type="hidden" name="sku[]"/></td>'+
				'<td><input placeholder="Type product.." name="itemId[]" type="text" class="product_name autocomplete"/></td>'+
				'<td align="right" class="number"><span class="price"></span><input type="hidden" name="price[]"/></td>'+
				'<td align="right" class="number"><span class="subtotal"></span><input type="hidden" name="subtotal[]"/></td>'+
				'<td align="center"><a class="delete-row" href="#hapus"><i class="icon-close"></i></a></td>'+
			'</tr>');
		$("input.product_name:last").focus();
		core.ui.dropdown();
	}
	
	function submit_form(){

		$.each ( $('form.invoice_detail input, form.invoice_detail select, form.invoice_detail textarea').serializeArray(), function ( i, obj ) {
			  $('<input type="hidden">').prop( obj ).appendTo( $('form.invoice') );
		});
		
		$('form.invoice').submit();

	} */
</script>
