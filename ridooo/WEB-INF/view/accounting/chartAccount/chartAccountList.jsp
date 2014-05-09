<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="financeAccounts" value="/finance/accounts" />
<%-- <c:url var="apiFinanceAccounts" value="/api/companies/1/finance/accounts" /> --%>
<script>
// 	function api(accountId){
// 		$.ajax({
// 			type : "POST",
// 			url : "${apiFinanceAccounts}/"+accountId+"/enable",
		
// 			data : $('.oForm').serialize()
// 		}).done(function(resPonseText) {
// 			alert(resPonseText);
// 		});
// 	}
	$(document).ready(function(){
		core.ui_with_parameter();

		$('.disable_popup').click(function() {
			var tmp = $(this);
			$(this).popup({
				title : 'Confirm to Disable',
				hideClose : true,
				html : '<form id="'+tmp.attr('id')+'" class="oForm" method="POST" action="${financeAccounts}/'+tmp.attr('id')+'/disable">'+
// 							'<input type="hidden" name="roleId" value="1" />'+
							'<p>Are you sure to disable this item?</p><div class="control-group">'+
							'<div class="controls">'+
								'<textarea class="input-full"  id="note" name="note" placeholder="Reasons" id="textarea" rows="3"></textarea>'+
							'</div></div>'+
							'<div class="form-actions">'+
								'<button type="submit" class="btn btn-negative">Disable</button><button type="reset" class="btn" onClick="$.colorbox.close();">Cancel</button>'+
							'</div></form>',
				height : '250px'
			});
		});

		$('.enable_popup').click(function(){
			var tmp = $(this);
			$(this).popup({
				title : 'Confirm to Enable',
				hideClose : true,
				html : '<form id="'+tmp.attr('id')+'" class="oForm" action="${financeAccounts}/'+tmp.attr('id')+'/enable">'+
// 							'<input type="hidden" name="roleId" value="1" />'+
							'<p>Are you sure to enable this item?</p><div class="control-group">'+
							'<div class="controls">'+
								'<textarea class="input-full"  id="note" name="note" placeholder="Reasons" id="textarea" rows="3"></textarea>'+
							'</div></div>'+
							'<div class="form-actions">'+
								'<button type="submit" class="btn btn-positive">Enable</button><button type="reset" class="btn" onClick="$.colorbox.close();">Cancel</button>'+
							'</div></form>',
				height : '250px'
			});
		});
		
// 		$('body').on('submit','.oForm', function(e){
// 			var id = $(this).attr("id");
// 			e.preventDefault();
// 			api(id);
// 		});
	});
	function filterByType() {
		if (document.form.type.value.length > 0) {
			document.form.submit();
		}
	}
</script>

<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">

		<div class="grid1 last extended">
			<div class="grid-content">


				<div class="content-header">
					<h3><span id="code"></span></h3>
					<p class="subtitle">
						<span id="chartname"></span>
					</p>

				</div>

				

				<div class="fieldset">
					<form>
						
						<div class="control-group">
							<label class="control-label" for="input01">Description</label>
							<div class="controls">
								<p>
									<span id="description"></span>
								</p>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="input01">Category</label>
							<div class="controls">
								<p>
									<span id="category"></span>
								</p>
							</div>
						</div>
						
						<!-- <div class="control-group">
							<label class="control-label" for="input01">Chart Account
								Name</label>
							<div class="controls">
								<p>
									<span id="chartname"></span>
								<p>
							</div>
						</div> -->

						<div class="control-group">
							<label class="control-label" for="input01">Starting
								Balance</label>
							<div class="controls">
								<p>
									<span id="starting_balance"></span>
								</p>
							</div>
						</div>
					</form>
				</div>



				<!--  <h3>
					<span id="schoolName"></span>
				</h3>-->


			</div>
		</div>

		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">

				<div class="content-header">
					<span class="fr"> <c:url var="addChartAccount"
							value="/finance/accounts/create" /> <c:if test="${canCreate}">
							<a href="${addChartAccount}" class="btn">Add Accounts</a>
						</c:if> <c:if test="${!canCreate}">
							<a href="javascript:void(0)" class="btn disabled">Add
								Accounts</a>
						</c:if>
					</span>

					<h1>Chart of Accounts</h1>
					<p class="subtitle">Chart of accounts are valid for all transactions in the company, including the sub companies.</p>

				</div>
				
				<form id="form" name="form" method="POST">
					<div class="field fl">
							<div class="input-append">
								<select name="type" class="input-very-small" readonly="" onChange="filterByType();">
									<c:forEach items="${typeOptions}" var="type">
										<c:choose>
										    <c:when test="${type.key.equalsIgnoreCase(typeSelected)}">
												<option value="${type.key}" selected>${type.value}</option>
										    </c:when>
										    <c:otherwise>
												<option value="${type.key}">${type.value}</option>
										    </c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
					</div>
				</form>
				
				${viewTimeLoad}

				<c:if test="${!empty action}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						${action}
					</div>
				</c:if>

				<c:if test="${!empty message}">
					<div class="alert alert-error">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						${message}
					</div>
				</c:if>
				
				<ul class="nav nav-pills fr">
					<c:url var="coaUrl" value="/finance/accounts"></c:url>
					<c:url var="disabledCoaUrl" value="/finance/accounts?status=disabled"></c:url>
					<c:if test="${status == 1 }">
						<li class="active"><a href="${coaUrl }">Active</a></li>
						<li><a href="${disabledCoaUrl }">Disabled</a></li>
					</c:if>
					<c:if test="${status == 0 }">
						<li><a href="${coaUrl }">Active</a></li>
						<li class="active"><a href="${disabledCoaUrl }">Disabled</a></li>
					</c:if>
				</ul>

				<c:choose>
					<c:when test="${empty accounts}">
						<div class="empty-state book">
							<h2>No chart of accounts available</h2>
						</div>
					</c:when>
					<c:otherwise>
						<table class="table table-striped table-list">
							<colgroup>
								<col class="w130"/>
								<col class="w200"/>
								<col />
								<col class="status"/>
								<col class="amount" />
								<col class="w100" />
							</colgroup>
							<thead>
								<tr>
									<th>CODE</th>
									<th>TYPE</th>
									<th>ACCOUNT</th>
									<th></th>
									<th class="align-right">Balance</th>
									<th></th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${accounts}" var="acc">
									<template:coaRecur canUpdate="${canUpdate}" account="${acc}" symbol="" />
								</c:forEach>
							</tbody>
						</table>
					${pagination}
					</c:otherwise>
				</c:choose>

				<!-- <c:if test="${!empty accounts}">
					
				</c:if> -->
			</div>
		</div>
		<div class="grid1 last">&nbsp;</div>
		<!-- END : CONTENT AREA -->

	</div>
	<div class="clear"></div>
</div>

