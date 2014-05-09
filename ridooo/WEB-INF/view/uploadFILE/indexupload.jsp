<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- CONTAINER -->
<div id="canvas" class="container">

	<!-- CONTENT -->
	<div class="content">

		<!-- GRID CONTAINER -->
		<div class="grid-container">

			<!-- START : CONTENT AREA -->
			<div class="grid1 last">&nbsp;</div>
			<div class="grid2">
				<h1>Form Upload</h1>
				<c:url var="doform" value="" />
				<form:form modelAttribute="modelUpload"
					enctype="multipart/form-data" method="POST" class="form-horizontal"
					action="${doform}">
					<cform:input path="fileData" label="File" type="file" />
					<div class="form-actions">
						<form:button name="action" value="save" class="btn btn-positive">Save</form:button>
					</div>
				</form:form>
			</div>
			<!-- END : CONTENT AREA -->

		</div>
		<div class="clear"></div>


	</div>
	<div class="clear"></div>
</div>