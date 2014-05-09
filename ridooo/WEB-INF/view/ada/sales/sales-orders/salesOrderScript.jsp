<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="dataAjax" value="/sales/sales-orders/ajax-data-sales-orders"/>
<c:url var="jsFile" value="/js/pagescroll.js"/>

<c:url var="URL" value="/"/>

<c:url var="packinglist_url" value="/sales/packing-list/"/>

<c:if test="${URL=='//'}">
<c:set var="dataAjax" value="/sales/sales-orders/ajax-data-sales-orders"/>
<c:set var="jsFile" value="/js/pagescroll.js"/>
<c:set var="URL" value="/"/>
<c:set var="packinglist_url" value="/sales/packing-list/"/>
</c:if>

<c:if test="${!empty limitDataPage}">
<script type="text/javascript">
	var urlAjax = '${dataAjax}';
	var nop = ${limitDataPage};
</script>

<script src="${jsFile}" type="text/javascript"></script>
</c:if>

<script>
	$(document).ready(function(){
		$("button#make_process").click(function(e){
			e.preventDefault();
			$.ajax({
				type : "POST",
				url : "${URL}sales/sales-orders/check-retail",
				data : $('form').serialize()
			})
			.done(
				function(resPonseText) {
					data = jQuery.parseJSON(resPonseText);
// 					var data = obj.data;
					console.log("3"+data);
					var result, checked, createTransferOrder;
					$.each(data, function(key,value){
// 						alert("key:"+key+" val:"+value);
						if(key == "checked")
							checked = value;
						else if(key == "retail")
							result = value;
						else if(key == "createTransferOrder")
							createTransferOrder = value;
					});
// 					alert(createTransferOrder);
					if(result == ""){
						$.colorbox({
							inline : true,
							href : "#error",
// 							height : 207,
						});
					}
					else if(result == "true"){
						$('#checkedRetail').text(checked);
						if(createTransferOrder == "true"){
							$.colorbox({
								inline : true,
								href : "#retail-process",
								height : 207,
							});
						}
						else
							alert('in stock');
					}
					else if(result == "false"){
						$('#checkedWholesale').text(checked);
						$.colorbox({
							inline : true,
							href : "#wholesale-process",
							height : 207,
						});
					}
					
// 					$('.extended .content-header').html('<img class="image avatar avatar-mini" src="${img}/no-person.png"><h3>'+$(".token-input-token p").text()+'</h3><p class="subtitle"></p><div class="clear"></div>');
// 					if(data.city !== undefined && data.province !== undefined){
// 						$('.extended .content-header p.subtitle').html(data.city +", "+ data.province);
// 					}
// 					else if(data.city !== undefined){
// 						$('.extended .content-header p.subtitle').html(data.city);
// 					}
// 					else if(data.province !== undefined){
// 						$('.extended .content-header p.subtitle').html(data.province);
// 					}
					
				});
			
		});
		
		$("body").on("click", "a.processTO", function(e){
			e.preventDefault();
			console.log($(this).parents("tr").find("input:checkbox"));
			$("input:checkbox").prop("checked", false);
			$(this).parents("tr").find("input:checkbox").prop('checked',true);
			var id = $(this).data('id');
			$.ajax({
				type : "GET",
				url : "${URL}sales/sales-orders/"+id+"/check-retail",
// 				data : $('form').serialize()
			})
			.done(
				function(resPonseText) {
					data = jQuery.parseJSON(resPonseText);
					console.log("3"+data);
					var result, checked, createTransferOrder, warning, empty, emptyRetail;
					$.each(data, function(key,value){
						if(key == "checked")
							checked = value;
						if(key == "retail")
							result = value;
						if(key == "createTransferOrder" && value == "true")
							createTransferOrder = value;
						if(key == "warning" && value == "true")
							warning = value;
						if(key == "empty" && value == "true")
							empty = value;
							warning = value;
						if(key == "empty_retail" && value == "true")
							emptyRetail = value;
					});
					if(result == ""){
						$.colorbox({
							inline : true,
							href : "#error",
							//height : 180,
						});
					}
					else if(result == "true"){
						$('#checkedRetail').text(checked);
						if(emptyRetail == "true"){
// 							alert(createTransferOrder)
							if(createTransferOrder == "true"){
								$.ajax({
									type : "POST",
									url : "${URL}sales/sales-orders/create-transfer-order",
					 				data : $('form').serialize()
								})
							}
							$('p#error-message').text('Transfer order telah dibuat, tetapi transaksi tidak bisa diproses karena seluruh stock item digudang retail habis.');
							
							$.colorbox({
								inline : true,
								href : "#error",
								height : 197,
								onClosed: function(){
									location.reload();
								}
							});
						}else if(empty == "true"){
							$('p#error-message').text('Transaksi tidak bisa diproses karena stok habis.');
							$.colorbox({
								inline : true,
								href : "#error",
								//height : 180,
							});
						}
						else if(warning == "true" && (createTransferOrder == "true")){
							$.colorbox({
								inline : true,
								href : "#retail-process",
								//height : 180,
							});
						}
						else if(createTransferOrder == "true"){
							$('a#createTO').click();
						}
						else{
							window.location.replace("${packinglist_url}"+id);
						}
					}
					else if(result == "false"){
						$('#checkedWholesale').text(checked);
						$.colorbox({
							inline : true,
							href : "#wholesale-process",
							//height : 180,
						});
					}
				});
		});
		
		$("a#make_invoices").click(function(e){
			e.preventDefault();
			$('form').attr('action','${URL}sales/invoice/create-form/from-sales-orders');
			$('form').submit();
		});
		$("a#createTO").click(function(e){
			e.preventDefault();
			$('form').attr('action','${URL}sales/sales-orders/create-transfer-order');
			$('form').submit();
		});
		
		$("body").on("click","a.cancel", function(e){
			e.preventDefault();
			var id = $(this).data('id');
			$.colorbox({
				inline : true,
				href : "#cancel-alert",
			});
			$("#cancel-alert a.cancel-button").attr('href', '${URL}sales/sales-orders/'+id+'/change-status/cancel');
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
			
			var batal = 0;
			var tunda = 0;
			var selesai = 0;
			var proses = 0;
			
			$.each($("table input:checkbox:checked"), function(index, obj){
// 				console.log("index"+index+" "+data);
				var status = $(obj).data("status");
				console.log("index"+index+" "+status);
				
				if(status != 'Batal'){
					batal = 1;
				}
				
				if((status == 'Batal' || status == 'Selesai') && tunda != 1){
					tunda = 0;
				}
				else if(status != 'Tunda'){
					tunda = 1;
				}
				
				if(status == 'Aktif' || status == 'Kemas'){
					selesai = 1;
					proses = 1;
				}
				
				
			});
			
			console.log("b:"+batal);
			console.log("t:"+tunda);
			console.log("s:"+selesai);
			
			if(batal == 0){
				$("#tools ul li").eq(3).hide();
			}
			else{
				$("#tools ul li").eq(3).show();
			}
			if(tunda == 0){
				$("#tools ul li").eq(1).hide();
			}
			else{
				$("#tools ul li").eq(1).show();
			}
			if(selesai == 0){
				$("#tools ul li").eq(2).hide();
			}
			else{
				$("#tools ul li").eq(2).show();
			}
			
			if(proses == 0){
				$("#tools ul li").eq(0).hide();
			}
			else{
				$("#tools ul li").eq(0).show();
			}
			
			if(batal == 0 && tunda == 0 && selesai == 0 && proses == 0){
				$("#tools ul li").eq(4).show();
			}
			else{
				$("#tools ul li").eq(4).hide();
			}

		});
		
	});
</script>
