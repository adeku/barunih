<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/"/><c:if test="${baseURL=='//'}"><c:set var="baseURL" value="/"/></c:if>
<!DOCTYPE html>
<html class="no-js">
	<head>
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	    <title>${title != null ? title : "ADA"} </title>
	    <meta name="description" content="">
	    <meta name="viewport" content="width=device-width">
	    <link rel="stylesheet" type="text/css" href="${baseURL}css/core.css">
	    <link rel="stylesheet" type="text/css" href="${baseURL}css/print.css" media="print">
	    <style type="text/css">
	       .main-nav a.active{
	           background: #173c54; color: @white !important; border-top: 1px solid #335871;border-bottom: 1px solid #335871;padding-top:6px;padding-bottom: 6px;
	       }
	    </style>
	    
	     <!-- put javascript on end body for fast loader -->
	    <script src="${baseURL}js/jquery-1.8.3.min.js" type="text/javascript"></script>
	    <script src="${baseURL}js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script>
	    <script src="${baseURL}js/jquery.validate.js" type="text/javascript"></script>
	    <script src="${baseURL}js/jquery.mousewheel.js" type="text/javascript"></script>
	    <script src="${baseURL}js/jScrollPane.js" type="text/javascript"></script>
	    <script src="${baseURL}js/jquery.slug.js" type="text/javascript"></script>
	    <script src="${baseURL}js/jquery.tokeninput.js" type="text/javascript"></script>
	    <script src="${baseURL}js/jquery.colorbox-min.js" type="text/javascript"></script>
	    <script src="${baseURL}js/jquery.imagesloaded.min.js" type="text/javascript"></script>
	    <script src="${baseURL}js/jquery.formatCurrency-1.4.0.min.js" type="text/javascript"></script>
	    <script src="${baseURL}js/core.js" type="text/javascript"></script>
	    <script src="${baseURL}js/icheck.js" type="text/javascript"></script>
	    <script src="${baseURL}js/custom.js" type="text/javascript"></script>
	    <script type="text/javascript">
			$(document).bind('cbox_complete', function(){ 
				$("#cboxClose").remove(); });
		</script>
	</head>
	<body>
	    <div class="wrapper">
	        <c:if test="${not empty messages && messages != null }">
	            <!-- messages notification -->
				<c:set var="message" value="${messages.split(':')}" />
                <div class="message ${message[0]}">${message[1]}<a href="#" class="fr message-close"><i class="icon-close" style="margin-top:3px !important;"></i></a></div>
            </c:if>
	        <c:if test="${not empty second_messages && second_messages != null }">
	            <!-- messages notification -->
				<c:set var="second_messages" value="${second_messages.split(':')}" />
                <div class="message ${second_messages[0]}">${second_messages[1]}<a href="" class="fr"><i class="icon-close" style="margin-top:3px !important;"></i></a></div>
            </c:if>
            <tiles:insertAttribute name="sidebar" />
            <tiles:insertAttribute name="content" />
            <div class="clear"></div>
        </div>
	   
        <tiles:insertAttribute name="option_javascript" />
	</body>
</html>