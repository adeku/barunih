<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
<meta charset="utf-8">
<title>Bootstrap, for VERSE</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet" />

<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.ui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugins/ckeditor/adapters/jquery.js"></script>
<script src="js/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollbar.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.bxslider.min.js"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>

</head>

<body>

	<!-- HEADER -->
	<div class="navbar navbar-fixed-top">

		<div class="navbar-inner">
			<div class="container">

				<!-- CHECK THIS -->
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a>

				<div class="nav-collapse">

					<!-- LOGO -->
					<a class="brand" href="#"><img
						src="${pageContext.request.contextPath}/img/logo.png" /></a>

					<!-- SETTINGS -->
					<a href="#" class="settings"><i class="icon-cog icon-large"></i></a>

					<!-- NAVIGATION MENU -->
					<ul class="nav">
						<li class="active"><a href="#">People</a></li>
						<li><a href="#about">Academic</a></li>
						<li><a href="#contact">Finance</a></li>
						<li><a href="#report">Report</a></li>
						<li class="disabled"><a href="#">Disabled Link</a></li>
					</ul>

				</div>

				<!-- SEARCH -->
				<div class="search">
					<div>
						<i class="icon-search"></i> <input type="text" />
					</div>
				</div>
			</div>
		</div>
	</div>