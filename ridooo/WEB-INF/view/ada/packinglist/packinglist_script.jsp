\<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />
<c:url var="pack_url" value="/sales/sales-orders/packinglist" />

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/" />
<c:set var="pack_url" value="/sales/sales-orders/packinglist" />
</c:if>
<script type="text/javascript">
	var f = {};
	var c = {};
	var split_btn;
	
	$(document).ready(function(){
		computeItemLeft();
		initialDragAndDrop1();
		initialDragAndDrop2();
		initialDragAndDropPack();
		customerAutoComplete();

		$("#add-pack").click(function(){
			var pack_size = $("ul.packnumber li").length;

			$($("ul.packnumber li")[pack_size - 1]).before($('<li box_number="' +pack_size+ '"><a href="">' + pack_size + '</a><input type="hidden" name="packnumber[]" value="' + pack_size + '"></li>'));

			//$("ul.rg").append('<li ><a href="javascript:void();" class="transfer-item" box="' +pack_size+ '">Pindah ke Box ' +pack_size+ '</a></li>');

			initialDragAndDropPack();

		});
		
		$("table").on("click", ".transfer-item", function(){
			var current_box = ${box};
			var box_number = $(this).attr("box");
			var dropped = $(this).parents("tr");
			//rename input name to syncron with target box
			dropped.find("input[name*='detail']").prop("name", "detail-" +box_number+ "[]");
			dropped.find("input[name*='qty']").prop("name", "qty-" +box_number+ "[]");
			dropped.find("input[name*='unit']").prop("name", "unit-" +box_number+ "[]");
			dropped.find("input[name*='sku']").prop("name", "sku-" +box_number+ "[]");
			dropped.find("input[name*='item']").prop("name", "item-" +box_number+ "[]");

			var target_html = dropped.html();
			$("body").click();
			dropped.css({ position: "fixed", top : dropped.offset().top, left : dropped.offset().left});
			var target_animation = $(".box_number[box_number=" +box_number+ "]");
			dropped.animate({ "left": target_animation.offset().left, "top" : target_animation.offset().top , "width" : target_animation.width(),
				"height" : target_animation.height()}, 400 , function(e){
					$("table.helper-box").append('<tr>' + target_html + '</tr>');
					dropped.remove();
					computeItemLeft();
					initialDragAndDrop2();
			});
		});

		$("table").on("click", ".freeup-item", function(){
			var dropped = $(this).parents("tr");
			dropped.find('td:last').remove();
			//rename input name, renaming name with target box to free box
			dropped.find("input[name*='detail']").prop("name", "detail[]");
			dropped.find("input[name*='qty']").prop("name", "qty[]");
			dropped.find("input[name*='unit']").prop("name", "unit[]");
			dropped.find("input[name*='sku']").prop("name", "sku[]");
			dropped.find("input[name*='item']").prop("name", "item[]");
			
			var target_html = dropped.html();
			$("body").click();
			dropped.css({ position: "fixed", top : dropped.offset().top, left : dropped.offset().left});
			var target_animation = $(".box_number[box_number=" +0+ "]");
			dropped.animate({ "left": target_animation.offset().left, "top" : target_animation.offset().top , "width" : target_animation.width(),
				"height" : target_animation.height()}, 400 , function(e){

					$("table.helper-box").append('<tr>' + target_html + '</tr>');
					dropped.remove();
					computeItemLeft();
					initialDragAndDrop1();
			});
		});
		
		<c:if test="${action == 'view'}">
			$.colorbox({ closeButton : false, href:'${pack_url}', width:"700px", overlayClose : false,
				onComplete : function(){
					$("#button-back").prop("href" , "${pageContext.request.getHeader('referer')}");
				}});
		</c:if>
		
		$("table").on("click", "a.split-item", function(e){
			//clear dropdow
			$("body").click();
			
			split_btn = $(this);

			$.colorbox({
				inline : true,
				href : "#popup",
				height : 170,
				onComplete : function(){
					var val = split_btn.data("item");
					max_val = val;
					$("#split-item").val(val - 1);
					$("#split-item").focus();
				}
			});
		});
		
		$('#split-item').on('keyup', function() {
		    limitText(this, max_val - 1);
		});
		
		$('#change-item').click( function() {
			var table = split_btn.parents("table");
			var tr_clone = split_btn.parents("tr").clone();
			var val = $('#split-item').val();
			var old_Val = max_val - val;
			var td_obj = tr_clone.find("td").eq(0);
			
			split_btn.parents("tr").find("td").eq(0).find("p").text(old_Val);
			split_btn.parents("tr").find("td").eq(0).find("input[name*='qty']").val(old_Val);
			split_btn.data("item", old_Val);

			tr_clone.find("a.split-item").attr("data-item", val);
			td_obj.find("p").text(val);
			td_obj.find("input[name*='qty']").val(val);
			
			//notif that parent tr will be updated
			if(split_btn.parents("tr").find("input[name*='detail']").length > 0) {
				split_btn.parents("tr").find("input[name*='detail']").prop("name", "detail[]");
				split_btn.parents("tr").find("input[name*='qty']").prop("name", "qty[]");
				split_btn.parents("tr").find("input[name*='unit']").prop("name", "unit[]");
				split_btn.parents("tr").find("input[name*='sku']").prop("name", "sku[]");
				split_btn.parents("tr").find("input[name*='item']").prop("name", "item[]");
				

				td_obj.parents("tr").find("input[name*='detail']").prop("name", "detail[]");
				td_obj.parents("tr").find("input[name*='detail']").val("-1");
				td_obj.parents("tr").find("input[name*='qty']").prop("name", "qty[]");
				td_obj.parents("tr").find("input[name*='unit']").prop("name", "unit[]");
				td_obj.parents("tr").find("input[name*='sku']").prop("name", "sku[]");
				td_obj.parents("tr").find("input[name*='item']").prop("name", "item[]");
			}
			
			table.append(tr_clone.wrap("<div>").parent().html());

			initialDragAndDrop1();
			initialDragAndDrop2();
		    $.colorbox.close();
		});
	});

	function limitText(field, maxChar){
	    var ref = $(field),
	        val = ref.val();
	    if ( val >= maxChar ){
	        ref.val(function() {
	            return maxChar;       
	        });
	    }
	}
	
	function computeItemLeft(){
		var count = 0;
		if($("table.free-box").is(':visible')){
			count = $("table.free-box tbody tr:not(.drag-helper)").length;
			if(count == 0 && $(".drag-helper").length == 0 ){
				$('table.free-box tbody').append('<tr id="first"  class="drag-helper">'+
													'<td align="center" colspan="5"><p >Belum ada item di box ini</p></td>'+
												 '</tr>');
			}else if(count != 0 && $(".drag-helper").length > 0){
				$(".drag-helper").remove();
			}
			
		}else{
			count = $("table.current-box tbody tr:not(.drag-helper)").length;
			if(count == 0 && $(".drag-helper").length == 0 ){
				$('table.current-box tbody').append('<tr id="first"  class="drag-helper">'+
													'<td align="center" colspan="5"><p >Belum ada item di box ini</p></td>'+
												 '</tr>');
			}else if(count != 0 && $(".drag-helper").length > 0){
				$(".drag-helper").remove();
			}
		}
		$("span#item-left").html(count);
		return count;
	}

	function initialDragAndDropPack(){
		$('ul.packnumber li:not(:last-child), div.packingname').droppable({
			drop : function(ev, ui) {
				var current_box = ${box};
				var box_number = $(this).attr("box_number");
				var dropped = $(ui.draggable);
				
				$(this).css("border", "");
				$(this).find("a").css("color", "");
				//rename input name to syncron with target box
				if(box_number == 0){
					dropped.find("input[name*='detail']").prop("name", "detail[]");
					dropped.find("input[name*='qty']").prop("name", "qty[]");
					dropped.find("input[name*='unit']").prop("name", "unit[]");
					dropped.find("input[name*='sku']").prop("name", "sku[]");
					dropped.find("input[name*='item']").prop("name", "item[]");
				}else{
					dropped.find("input[name*='detail']").prop("name", "detail-" +box_number+ "[]");
					dropped.find("input[name*='qty']").prop("name", "qty-" +box_number+ "[]");
					dropped.find("input[name*='unit']").prop("name", "unit-" +box_number+ "[]");
					dropped.find("input[name*='sku']").prop("name", "sku-" +box_number+ "[]");
					dropped.find("input[name*='item']").prop("name", "item-" +box_number+ "[]");
				}
				dropped.find('td:last').remove();
				var target_html = dropped.html();
				if(current_box == box_number){
					$("table.current-box").append('<tr>' + target_html + '<td><a href="javascript:void();" class="freeup-item"><i class="icon-close"></i></a></td></tr>');
					//remove helper when there new item on tr
					var not_hlper = $("table.current-box tbody tr:not(.drag-helper)");
					if(not_hlper.length > 0){
						$(".drag-helper").remove();
					}
				}else{
					$("table.helper-box").append('<tr>' + target_html + '</tr>');
				}
				$(c.tr).remove();
				$(c.helper).remove();
				$(f.tr).remove();
				$(f.helper).remove();
				computeItemLeft();
				initialDragAndDrop2();
			},
			over: function(event, ui) {
				$(this).css("border", "2px solid #111111");
				$(this).find("a").css("color", "#111111");
			},
			out: function(event, ui) {
				$(this).css("border", "");
				$(this).find("a").css("color", "");
			}
		});
	}

	function initialDragAndDrop1(){

		$("table.free-box tbody tr, table.current-box tbody tr").css("cursor","move");
		$("table.free-box tbody tr").draggable({
			helper : function() {
		        //debugger;
		        //return $("<div></div>").append($(this).find('.name').clone());
		        var td_obj = $(this).find("td");
		        var clone_obj = $(this).clone();
		        $(clone_obj.find("td").get(0)).css( "width" , $(td_obj[0]).width());
		        $(clone_obj.find("td").get(1)).css( "width" , $(td_obj[1]).width());
		        $(clone_obj.find("td").get(2)).css( "width" , $(td_obj[2]).width());
		        $(clone_obj.find("td").get(3)).css( "width" , $(td_obj[3]).width());
		        $(clone_obj.find("td").get(4)).css( "width" , $(td_obj[4]).width());
		        return clone_obj;
		    },
			//containment : "body",
			revert : 'invalid',
			zIndex: 1,
			cursorAt: { left: $("table.free-box tbody").width() / 2, top :30},
			start : function(event, ui) {
				f.tr = this;
				f.helper = ui.helper;
			}
		});

		$('table.current-box').droppable({
			drop : function(ev, ui) {
				var dropped = $(ui.draggable);
				if(dropped.parents("table").hasClass( "current-box") == false){
					dropped.find('td:last').remove();
					//rename input name to syncron with target box
					dropped.find("input[name*='detail']").prop("name", "detail-${box}[]");
					dropped.find("input[name*='qty']").prop("name", "qty-${box}[]");
					dropped.find("input[name*='unit']").prop("name", "unit-${box}[]");
					dropped.find("input[name*='sku']").prop("name", "sku-${box}[]");
					dropped.find("input[name*='item']").prop("name", "item-${box}[]");
					
					var target_html = dropped.html()
					var droppedOn = $(this).find("tbody");
					droppedOn.append('<tr>' + target_html
							+ '<td><a href="javascript:void();" class="freeup-item"><i class="icon-close"></i></a></td></tr>');
					$(f.tr).remove();
					$(f.helper).remove();
					computeItemLeft();
					initialDragAndDrop2();
					
					//remove helper when there new item on tr
					var not_hlper = $("table.current-box tbody tr:not(.drag-helper)");
					if(not_hlper.length > 0){
						$(".drag-helper").remove();
					}
				}
			}
		});
	}
	
	function initialDragAndDrop2(){
		$("table.free-box tbody tr, table.current-box tbody tr").css("cursor","move");

		$("table.current-box tbody tr:not(.drag-helper)").draggable({
			helper : function() {
		        //debugger;
		        //return $("<div></div>").append($(this).find('.name').clone());
		        var td_obj = $(this).find("td");
		        var clone_obj = $(this).clone();
		        $(clone_obj.find("td").get(0)).css( "width" , $(td_obj[0]).width());
		        $(clone_obj.find("td").get(1)).css( "width" , $(td_obj[1]).width());
		        $(clone_obj.find("td").get(2)).css( "width" , $(td_obj[2]).width());
		        $(clone_obj.find("td").get(3)).css( "width" , $(td_obj[3]).width());
		        $(clone_obj.find("td").get(4)).css( "width" , $(td_obj[4]).width());
		        return clone_obj;
		    },
			//containment : "body",
			revert : 'invalid',
			zIndex: 1,
			cursorAt: { left: $("table.current-box tbody").width() / 2, top :30},
			start : function(event, ui) {
				c.tr = this;
				c.helper = ui.helper;
			}
		});

		$('table.free-box').droppable({
			drop : function(ev, ui) {
				var dropped = $(ui.draggable);
				if(dropped.parents("table").hasClass( "free-box") == false){
					dropped.find('td:last').remove();
					//rename input name, renaming name with target box to free box
					dropped.find("input[name*='detail']").prop("name", "detail[]");
					dropped.find("input[name*='qty']").prop("name", "qty[]");
					dropped.find("input[name*='unit']").prop("name", "unit[]");
					dropped.find("input[name*='sku']").prop("name", "sku[]");
					dropped.find("input[name*='item']").prop("name", "item[]");
					
					var target_html = dropped.html()
					var droppedOn = $(this).find("tbody");
					droppedOn.append('<tr>' + target_html + '<td>' + $(".btn-helper").html() + '</td></tr>');
					$(c.tr).remove();
					$(c.helper).remove();

					computeItemLeft();
					initialDragAndDrop1();

					//add helper when there's no item on tr
					var not_hlper = $("table.current-box tbody tr:not(.drag-helper)");
					if(not_hlper.length == 0){
						$('table.current-box tbody').append('<tr id="first"  class="drag-helper">'+
															'<td align="center" colspan="5"><p >Belum ada item di box ini</p></td>'+
														 '</tr>');
					}
				}
			}
		});
		 	

		$('table.free-box tr').droppable({
			drop : function(ev, ui) {
				var dropped = $(ui.draggable);
				if(dropped.parents("table").hasClass( "free-box") == true){
					var target_tr = $(this);
					var table = $("table.helper-deleted");
					
					var val = dropped.find("a.split-item").data("item");
					var new_val = target_tr.find("a.split-item").data("item") + val;
					
					target_tr.find("a.split-item").data("item", new_val);
					target_tr.find("td").eq(0).find("input[name*='qty']").val( new_val);
					target_tr.find("td").eq(0).find("p").text( new_val);
					target_tr.find("input[name*='detail']").prop("name", "detail[]");
					target_tr.find("input[name*='qty']").prop("name", "qty[]");
					target_tr.find("input[name*='unit']").prop("name", "unit[]");
					target_tr.find("input[name*='sku']").prop("name", "sku[]");
					target_tr.find("input[name*='item']").prop("name", "item[]");

					dropped.find("input[name*='detail']").prop("name", "detail-del[]");
					dropped.find("input[name*='qty']").prop("name", "qty-del[]");
					dropped.find("input[name*='unit']").prop("name", "unit-del[]");
					dropped.find("input[name*='sku']").prop("name", "sku-del[]");
					dropped.find("input[name*='item']").prop("name", "item-del[]");
					
					table.append(dropped.wrap("<div>").parent().html());
					
					$(f.tr).remove();
					$(f.helper).remove();
				}
			}
		});
	}
	
	function customerAutoComplete() {
		core.ui.autocomplete({
			data : "${customer_auto_complete}",
			<c:choose>
				<c:when test="${action.equals('create')}">
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
			width : "100%"
		});
	}
</script>