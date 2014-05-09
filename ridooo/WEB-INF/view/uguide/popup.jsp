<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="guide-container">
	<div class="guide-nav">
		<div class="guide-nav-header">
			<i class="guide-icon"></i>
			<h4>USER GUIDE</h4>
			
			<div class="search">
				<i class="icon-search"></i>
				<input type="text" placeholder="Search" />
			</div>
		</div>
	
		<ul class="guide-nav-sidebar">
			<li><a href="" guide-nav="general" name="general" class="guide-nav-link">General</a></li>
			<li><a href="" guide-nav="people" class="guide-nav-link">People</a></li>
			<li><a href="" guide-nav="finance" class="guide-nav-link">Finance</a></li>
			<li><a href="" guide-nav="academic" class="guide-nav-link">Academic</a></li>
			<li><a href="" guide-nav="inventory" class="guide-nav-link">Inventory</a></li>
		</ul>
	</div>
	<div class="guide-content">
<!-- 		<ul class="guide-content-list"> -->
<!-- 			<li> -->
				<h2 class="guide-title">${popuprespond.title}</h2>
				<div class="guide-popup-content">
					${popuprespond.prevId } - ${popuprespond.nextId }
					<c:if test="${not empty popuprespond.contentImage}">
						<img src="${popuprespond.contentImage}" />
					</c:if>
					<p class="editor">
						${popuprespond.content}
					</p>
				</div>
<!-- 			</li> -->
			
<%-- 			<c:forEach items="${userGuides}" var="userGuide"> --%>
<!-- 				<li> -->
<%-- 					<h2 class="guide-title">${userGuide.title}</h2> --%>
<!-- 					<div class="guide-popup-content"> -->
<%-- 						<c:if test="${userGuide.imageUrl != null}"> --%>
<%-- 							<img src="${userGuide.imageUrl}" /> --%>
<%-- 						</c:if> --%>
<!-- 						<p class="editor"> -->
<%-- 							${userGuide.content} --%>
<!-- 						</p> -->
<!-- 					</div> -->
<!-- 				</li> -->
<%-- 			</c:forEach> --%>
<!-- 		</ul> -->
		<div class="guide-pagination">
			<c:if test="${not empty popuprespond.prevId }">
				<div class="btn guide-prev fl nav-btn" id="${popuprespond.prevId}"><i class="icon-chevron-left"></i></div>
			</c:if>
			<c:if test="${not empty popuprespond.nextId }">
				<div class="btn guide-next fr nav-btn" id="${popuprespond.nextId}"><i class="icon-chevron-right"></i></div>
			</c:if>
		</div>
	</div>
	<div class="clear"></div>
</div>
<c:url var="xxx" value="/uguide/menu/"/>
<c:url var="uguide" value="/uguide"/>

<script>
	var slider;
	$(document).bind('cbox_complete', function(){
		$('.guide-content a').colorbox();
	});
	
	function changeGuideNav(guideNav){
		console.log("/uguide/menu/"+guideNav);
		$.ajax({
			type : "GET",
			url : "${xxx}" +guideNav,
			beforeSend: function(){
				sb_popup.remove();
			}
		}).done(function(resPonseText) {
			data = jQuery.parseJSON(resPonseText);
			console.log(data);
			if(!$.isEmptyObject(data)){
				console.log('not empty');
				
				var str = '<h2 class="guide-title">'+data.title+'</h2>';
				str += '<div class="guide-popup-content">';
					if(data.contentImage != null){
						str += '<img src="'+data.contentImage+'" />';
					}
					str += '<p class="editor">'+data.content+'</p>';
				str += '</div>';
				
				str += '<div class="guide-pagination">';
					if(data.nextId != null){
						str += '<div class="btn guide-next fr nav-btn" id="'+data.nextId+'"><i class="icon-chevron-right"></i></div>';
					}
				str += '</div>';
				
				$('.guide-content').html(str);
				
				$('.nav-btn').click(function(){
					changeGuideContent(guideNav, $(this).attr('id'));
				});
			}
			else{
				$('.guide-content').html('');
			}
			initPopupScrollbar();
			
// 				$.each(data, function(index,value){
// 					var str =
// 							'<h2 class="guide-title">'+value.title+'</h2>'+
// 							'<div class="guide-popup-content">';
// 					if(value.image != null){
// 						str += '<img src="'+ value.image +'" />';
// 					}
// 						str += '<p class="editor">'+
// 					 				value.content +
// 								'</p>'+
// 							'</div>';
// 					if(index != 0){
// 						$('.guide-content-list').append(str);
// 					}
// 					else{
// 						$('.guide-content-list').html(str);
// 					}
// 				});
// 				slider.remove();
// 				slider = $('.guide-content-list').bxSlider({
// 					slideMargin: 10,
// 					pager: false,
// 					nextText: '',
// 					prevText: '',
// 					mode: 'fade'
// 				});
// 				slider.reloadSlider();
// 				$('div.guide-pagination').show();
// 			}
// 			else{
// 				console.log('empty');
// 				$('.guide-content-list').html('');
// 				$('div.guide-pagination').hide();
// 			}
// 			$('.editor').html(resPonseText);
		});
	}
	
	function changeGuideContent(service, guideId){
		console.log("/uguide/menu/"+guideId);
		$.ajax({
			type : "GET",
			url : "${uguide}/" +service+ "/" +guideId+ "/getdetails",
			beforeSend: function(){
				sb_popup.remove();
			}
		}).done(function(resPonseText) {
			data = jQuery.parseJSON(resPonseText);
			console.log(data);
			
			var str = '<h2 class="guide-title">'+data.title+'</h2>';
			str += '<div class="guide-popup-content">';
				if(data.contentImage != null){
					str += '<img src="'+data.contentImage+'" />';
				}
				str += '<p class="editor">'+data.content+'</p>';
			str += '</div>';
			
			str += '<div class="guide-pagination">';
				if(data.prevId != null){
					str += '<div class="btn guide-prev fl nav-btn" id="'+data.prevId+'"><i class="icon-chevron-left"></i></div>';
				}
				if(data.nextId != null){
					str += '<div class="btn guide-next fr nav-btn" id="'+data.nextId+'"><i class="icon-chevron-right"></i></div>';
				}
			str += '</div>';
			
			$('.guide-content').html(str);
			
			$('.nav-btn').click(function(){
				changeGuideContent(service, $(this).attr('id'));
			});
			
			initPopupScrollbar();
		});
	}
	
	function initPopupScrollbar(){
		sb_popup = $('.guide-popup-content').niceScroll({
			cursoropacitymax: 0.5,
			railoffset: true,
			railpadding: { right: 15 }
		});
	}
	
	$(document).ready(function(){
		initPopupScrollbar();
		$('.guide-nav-sidebar li a[guide-nav="${service}"]').addClass('active');
		$('a.guide-nav-link').click(function(e){
			e.preventDefault();
			$('li a.active').removeClass('active');
			$(this).addClass('active');
			changeGuideNav($(this).attr('guide-nav'));
		});
// 		slider = $('.guide-content-list').bxSlider({
// 			slideMargin: 10,
// 			pager: false,
// 			nextText: '',
// 			prevText: '',
// 			mode: 'fade',
// 			onSlideAfter: function(){
// 				sb_popup.resize();
// 			}
// 		});
		
		$('.nav-btn').click(function(){
			changeGuideContent("${service}", $(this).attr('id'));
// 			slider.goToPrevSlide();
// 		    return false;
		});
// 		$('.guide-next').click(function(){
// 			slider.goToNextSlide();
// 		    return false;
// 		});
		
	});
</script>