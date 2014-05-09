<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page session="false"%>
<c:url var="baseURL" value="/"/><c:if test="${baseURL=='//'}"><c:set var="baseURL" value="/"/></c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>ADA</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="${baseURL}css/core.css">
<body>
    <div class="wrapper">
        <div class="message error">${msgSuccess}</div>
        <div class="login-page">
            <div class="login-area">
                <div class="fl">
                    <img src="${baseURL}img/logo.png" />
                    <div class="tagline">INVENTORY MANAGEMENT SYSTEM</div>
                </div>
                <div class="fr">
                    <label>Please log in</label>
                    <form:form action="${baseURL}login/2" commandName="loginForm">
	                    <form:hidden path="urlView" value="${urlView}" />
	                    <form:input path="email" placeholder="E-mail Address" />
	                    <form:password path="password" placeholder="Password" />
	                    <input type="submit" value="LOG IN" class="btn btn-positive" />
                    </form:form>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <!-- put javascript on end body for fast loader -->
    <script src="${baseURL}js/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="${baseURL}js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script>
    <script src="${baseURL}js/jquery.validate.js" type="text/javascript"></script>
    <script src="${baseURL}js/jquery.cleditor.min.js" type="text/javascript"></script>
    <script src="${baseURL}js/jquery.mousewheel.js" type="text/javascript"></script>
    <script src="${baseURL}js/jScrollPane.js" type="text/javascript"></script>
    <script src="${baseURL}js/jquery.slug.js" type="text/javascript"></script>
    <script src="${baseURL}js/jquery.tokeninput.js" type="text/javascript"></script>
    <script src="${baseURL}js/jquery.colorbox-min.js" type="text/javascript"></script>
    <script src="${baseURL}js/jquery.imagesloaded.min.js" type="text/javascript"></script>
    <script src="${baseURL}js/core.js" type="text/javascript"></script>
    <script src="${baseURL}js/custom.js" type="text/javascript"></script>
</head>
</body>
</html>