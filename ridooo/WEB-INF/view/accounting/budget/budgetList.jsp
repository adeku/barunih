<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url var="budgetsUrl" value="/finance/budgets" />
<c:url var="apibud" value="/api/companies/1/finance/budgets"></c:url>
<script type="text/javascript">
	// 	var allFields = $([]).add(notes);

	function addBudget() {
		console.log("asd");
		$.ajax({
			type : "POST",
			url : "${budgetsUrl}/create",
			beforeSend : function() {
				core.loading('sidebar');
			},
			data : $('#addBudget').serialize()
		})
		.done(function(resPonseText) {
				
				data = jQuery.parseJSON(resPonseText);
// 					var data = obj.data;
				console.log(data);
				core.extended.close($('.grid1'));
				var date = new Date();
				var currentMonth = date.getMonth();
				console.log('currentMonth:'+currentMonth);
				if(data.message == 'success'){
					$('.content-header').after('<div class="alert alert-success">'+
							'<button type="button" class="close" data-dismiss="alert">&times;</button>'+
							'<strong>Well done!</strong> You have successfully create budget.'+
						'</div>');
					if(data.companyId == $('#schoolId').val()){
						var button = '';
						if(data.canUpdate == true && data.canDelete == true){
							button = '<a href="#" class="btn btn-mini show-extend edit" id="'+data.id+'">Edit</a>'+
										'<button class="btn dropdown-toggle" data-toggle="dropdown">'+
										'<span class="caret"></span>'+
										'</button>'+
										'<ul class="dropdown-menu">'+
											'<li><a tabindex="-1" href="#" class="delete_popup" id="'+data.id+'">Hapus</a></li>'+
										'</ul>';
						}
						else if(data.canUpdate == true && data.canDelete == false){
							button = '<a href="#" class="btn btn-mini show-extend edit" id="'+data.id+'">Ralat</a>';
						}
						else if(data.canUpdate == false && data.canDelete == true){
							button = '<a href="#" class="btn btn-mini delete_popup" id="'+data.id+'">Hapus</a>';
						}
						else{
							button = '<a href="#" class="btn btn-mini disabled">Ralat</a>';
						}
						
						if($('table').length == 0){
							$('div.empty-state.person').remove();
							$('div.grid-content').append('<table class="table table-striped">'+
								'<colgroup>'+
									'<col>'+
									'<col class="w150">'+
									'<col class="w30">'+
									'<col class="w100">'+
									'<col class="w100">'+
								'</colgroup>'+
								'<thead>'+
									'<tr>'+
										'<th>ACCOUNT</th>'+
										'<th class="align-right">ALLOCATED (Rp.)</th>'+
										'<th></th>'+
										'<th class="align-right">REMAINING (Rp.)</th>'+
										'<th></th>'+
									'</tr>'+
								'</thead>'+
								'<tbody></tbody>'+
							'</table>');
						}
						$('#monthlyAmount'+currentMonth).currency();
						$('#amount').currency();
						$('table tbody').prepend('<tr>'+
								'<td>'+
									'<h4>'+$('#accountId option:selected').text()+'</h4>'+
									'<span>'+data.description+'</span>'+
								'</td>'+
								'<td class="allocated">'+
									'<span class="amount" id="amount-'+data.id+'">'+$('#amount').val()+'</span>'+
								'</td>'+
								'<td></td>'+
								'<td class="align-right remaining">'+							
									'<div class="progress progress-success margin-bottom-zero">'+
										'<div class="bar" style="width:100%"></div>'+
									'</div>'+
									'<span class="remaining-amount" id="remaining-amount-'+data.id+'">'+$('#monthlyAmount'+currentMonth).val()+'</span>'+
								'</td>'+
								
								'<td>'+
									'<div class="btn-group dropdown fr">'+
										button+
									'</div>'+
								'</td>'+
							'</tr>');
					}
					
					$('#schoolId').tokenInput("clear");
					$('#addBudget')[0].reset();
					
					
				}
				else{
					$('.content-header').after('<div class="alert alert-error">'+
							'<button type="button" class="close" data-dismiss="alert">&times;</button>'+
							data.message +
						'</div>');
				}
				core.loading.destroy();
			});
	}
	
	function updateBudget(budgetId) {
		$.ajax({
			type : "POST",
			url : "${budgetsUrl}/edit",
			beforeSend : function() {
				core.loading('sidebar');
			},
			data : $('#editBudget').serialize()
		})
		.done(function(resPonseText) {
				core.loading.destroy();
				data = jQuery.parseJSON(resPonseText);
// 					var data = obj.data;
				console.log(data);
				core.extended.close($('.grid1'));
				var date = new Date();
				var currentMonth = date.getMonth();
				console.log('currentMonth:'+currentMonth);
				if(data.message == 'success'){
					$('.content-header').after('<div class="alert alert-success">'+
							'<button type="button" class="close" data-dismiss="alert">&times;</button>'+
							'<strong>Well done!</strong> You have successfully edit budget.'+
						'</div>');
					if(data.companyId == $('#schoolId').val()){
						$('tr#'+data.id+' .allocated .amount').text($('#amount').val());
						$('tr#'+data.id+' .remaining').html('<div class="progress progress-success margin-bottom-zero">'+
									'<div class="bar" style="width:'+data.remainingPercentage+'%"></div>'+
								'</div>'+
								'<span class="remaining-amount" id="remaining-amount-'+data.id+'">'+data.remaining+'</span>');
						$('#remaining-amount-'+data.id).currency();
						$('#amount-'+data.id).currency();
					}
					else{
						$('tr#'+data.id).addClass('disabled');
						$('tr#'+data.id).attr('data-attr',"The budget has been moved");
						$('tr#'+data.id).attr('data-title', $('tr#'+data.id+' td.name h4').text());
						core.init();
					}
					$('#schoolId').tokenInput("clear");
					$('#editBudget')[0].reset();
				}
				else{
					$('.content-header').after('<div class="alert alert-error">'+
							'<button type="button" class="close" data-dismiss="alert">&times;</button>'+
							data.message +
						'</div>');
				}
			});
	}
	
// 	function getAccountsOption(companyId) {
// 		$.ajax({
// 			type : "GET",
// 			url : "${budgetsUrl}/getaccountsoption",
// 			beforeSend : function() {
// 				core.loading('sidebar');
// 			},
// 			data: {
// 				id : companyId
// 			},
// 		})
// 		.done(function(resPonseText) {
// 			data = jQuery.parseJSON(resPonseText);
// 			console.log(data);
// 			core.loading.destroy();
// 		});
// 	}

	function getBudget(budgetId) {
		$.ajax({
			type : "GET",
			url : "${budgetsUrl}/"+budgetId+"/getbudget",
			beforeSend : function() {
				core.loading('sidebar');
			},
		})
		.done(function(resPonseText) {
			data = jQuery.parseJSON(resPonseText);
			console.log(data);
			
// 			$('#schoolId').val(data.schoolId);
			$('#accountId').val(data.accountId);
			$('#amount').val(data.amount);
			
			$('#schoolId').tokenInput("add", {id: data.schoolId, name: data.schoolName});
			
			$.each(data.monthlyAmount, function(i, obj) {
				$('#monthlyAmount'+i).val(obj);
			});
			
			core.loading.destroy();
		});
	}
	
	
// 	$("#dialog-form").dialog({
// 		autoOpen : false,
// 		height : 300,
// 		width : 350,
// 		modal : true,
// 		buttons : {
// 			"Delete" : function() {
// 				$(this).submit();
// 			},
// 			Cancel : function() {
// 				$(this).dialog("close");
// 			}
// 		},
// 		close : function() {
// 			allFields.val("").removeClass("ui-state-error");
// 		}
// 	});

	$(document).ready(function(){
				$('.remaining-amount').currency();
				$('body').on('submit','#addBudget',function(e){
					e.preventDefault();
					addBudget();
				});
				
				$('body').on('submit','#editBudget',function(e){
					e.preventDefault();
					var budgetId = $('#budgetId').val();
					updateBudget(budgetId);
				});

				function deleteBudget(url) {
					var isOK = confirm("Are you sure to delete?");
					if (isOK) {
						go(url);
					}
				} 
				
				$('body').on('click', '.delete_popup',function(){
					var tmp = $(this);
					$(this).popup({
						title : 'Confirm to Delete',
						hideClose : true,
						html : '<form id="deleteModel" method="POST" action="${budgetsUrl}/'+tmp.attr('id')+'/delete">'+
									'<p>Are you sure to delete this budget?</p><div class="control-group">'+
									'<div class="controls">'+
										'<textarea class="input-full"  id="reason" name="reason" placeholder="Reasons" id="textarea" rows="3"></textarea>'+
									'</div></div>'+
									'<div class="form-actions">'+
										'<button type="submit" class="btn btn-negative">Hapus</button>'+
										'<button type="reset" class="btn" onClick="$.colorbox.close();">Batal</button>'+
									'</div></form>',
						height : '250px'
					});
				});
				
				var amount = $('#amount').val();
				var total = 0;

				$("#budget").validate({
					onfocusout : false,
					onkeyup : false,
				});

				jQuery.validator.addMethod("greaterThanZero",
						function(value, element, params) {
							total = 0;
							for (i = 0; i < 12; i++) {
								total = total
										+ parseInt($('#monthlyAmount' + i)
												.val());
							}

							console.log(total);

							return this.optional(element)
									|| (parseFloat(value) == total);
						}, "Amount is not equal with total of monthly amount");

				$('#amount').rules('add', {
					greaterThanZero : true
				});

				$('.monthlyAmount').keyup(
						function() {
							// 			alert(amount);
							total = 0;
							for (i = 0; i < 12; i++) {
								total = total
										+ parseInt($('#monthlyAmount' + i)
												.val());
								// 				console.log(total);
							}
							$('#amount').val(total);
						});
				$('.divide').click(function() {
					amount = $('#amount').val();
					var value = Math.floor(amount / 12);
					var totalCount=0;
					for (i = 0; i < 12; i++) {
						$('#monthlyAmount' + i).val(value);
						totalCount+=value;
					}
					 $('#amount').val(totalCount);
				});
				
				core.pills_autocomplete({
					data: '${budgetsUrl}/getschools',
					hintText: "School Name",
					target_element: '#schoolId',
// 					onAdd: function(){
// 						var companyId = $('#schoolId').val();
// 						getAccountsOption(companyId);
// 					},
				});
				
				$('.create').click(function(){
					$('form#editBudget').attr('id', 'addBudget');
					$('form#addBudget #id').val('0');
					$('#schoolId').tokenInput("clear");
					$('#addBudget')[0].reset();
					$('.grid1 h3').text('Add Budget');
					$('.grid1 p.subtitle').text('Create new budgets to be allocated');
					getBudget(budgetId);
				});
				
				$('body').on('click', '.edit', function(){
					var budgetId = $(this).attr('id');
					$('form#addBudget').attr('id', 'editBudget');
					$('form#editBudget #id').val(budgetId);
					$('#schoolId').tokenInput("clear");
					$('#editBudget')[0].reset();
					$('.grid1 h3').text('Edit Budget');
					$('.grid1 p.subtitle').text('Change budget to be allocated');
					getBudget(budgetId);
				});

// 				$(".delete").click(
// 						function() {
// 							$("#dialog-form").dialog({
// 								buttons : {
// 									"Delete" : function() {
// 										$("#delete_budget").submit();
// 									},
// 									Cancel : function() {
// 										$(this).dialog("close");
// 									}
// 								}
// 							});
// 							$("#delete_budget").attr(
// 									'action',
// 									'/verse/finance/budgets/'
// 											+ $(this).attr('id') + '/delete');
// 							return false;
// 						});
// 				$(".cancel").click(function(e) {
// 					e.preventDefault();
// 					$("#dialog-form").dialog("close");
// 				});

			});
</script>

<%-- <div id="dialog-form" title="Delete Budget" style="display: none">
	<form:form modelAttribute="delete_budget" method="POST">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Are you sure?
		</p>
		<br />
		<form:button name="action" value="delete" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Delete</form:button>
		<form:button name="action" value="cancel" class="cancel ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Cancel</form:button>

	</form:form>
</div> --%>
<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">
	
		<div class="grid1 last extended">
			<div class="grid-content">
				<h3>Add Budgets</h3>
				<p class="subtitle">Create new budgets to be allocated</p>
				
				<form:form modelAttribute="addBudget" method="POST" class="validate">
					<input type="hidden" id="id" name="id" value="0">
						<div class="control-group">
							<label class="control-label">School</label>
							<div class="input-append pills-autocomplete-field">
								<form:input path="schoolId" class="pills-autocomplete" /><span class="add-on"><i class="icon-pencil"></i></span>
							</div>
							
						</div>
					
						<div class="control-group">
							<label class="control-label">Account</label>
							<form:select path="accountId" items="${accounts}" />
						</div>
					
						<div class="control-group">
							<label class="control-label">Amount</label>
							<div class="input-prepend">
								<span class="add-on">Rp.</span><form:input path="amount" class="input-full"/>
							</div>
						</div>
						
						<label><a href='#' class='divide'>Distribute Equally</a></label>
						<br />
						<label>Monthly Breakdown</label>
						<hr />
						
						<div class="form-horizontal">
							<c:forEach items="${months}" var="month" varStatus="stat">
								<div class="control-group">
									<label class="control-label">${month}</label>
									<div class="controls">
										<div class="input-prepend">
											<span class="add-on">Rp.</span><form:input path="monthlyAmount[${stat.index}]" value="${status.value}" class="input-full monthlyAmount" />
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
						<div class="form-actions">
							<button type="submit" class="btn btn-positive add" name="save">Save</button>
							<button type="reset" class="btn">Cancel</button>
						</div>
						
					
				</form:form>
			</div>
		</div>

		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">

				<!-- 				<p align="right"> -->
				
				<div class="content-header">
					<span class="fr">
						<c:choose>
							<c:when test="${canCreate == true}">
								<c:url var="createBudget" value="/finance/budgets/create" />
								<a href="#" class="btn show-extend create">Create Budgets</a>
							</c:when>
							<c:otherwise>
								<a href="#" class="btn disabled">Create Budgets</a>
							</c:otherwise>
						</c:choose>
						
					</span>
					<h1>Manage Budgets</h1>
					<p class="subtitle">Display all budgets allocated</p>
				</div>
				
				<c:if test="${!empty warningMessages}">
					<c:choose>
						<c:when test="${warningMessagesSize == 1}">
							<div class="alert alert-block alert-block-pad">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								<strong>Warning!</strong> ${warningMessages[0]}
							</div>
						</c:when>
						<c:otherwise>
							<div class="alert alert-block">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								<h4 class="alert-heading">Warning!</h4>
								<p>
									<ul>
										<c:forEach items="${warningMessages}" var="warningMessage">
											<li>${warningMessage}</li>
										</c:forEach>
									</ul>
								</p>
							</div>
						</c:otherwise>
					</c:choose>
					
				</c:if>

				<c:choose>
					<c:when test="${empty budgets}">
						<div class="empty-state person">
							<h2>No Budgets Available</h2>
						</div>
					</c:when>
					<c:otherwise>
						
					
					<table class="table table-striped">
					<colgroup>
						<col/>
<%-- 						<col style="width:150px;"/> --%>
<%-- 						<col style="width:150px;"/> --%>
<%-- 						<col style="width:150px;"/> --%>
						<%-- <col class="amount"/>
						<col class="amount"/> --%>
						<col class="w150"/>
						<col class="w30"/>
						<col class="w100"/>
						<col class="w100"/>
					</colgroup>
					
					<thead>
						<tr>
							<th>ACCOUNT</th>
							<!-- <th style="text-align: center;">PREV MONTHS (Rp.)</th>
							<th style="text-align: center;">CURRENT MONTHS (Rp.)</th>
							<th  style="text-align: center;">TOTAL AMOUNT (RP.)</th> -->
							<th class="align-right">ALLOCATED (Rp.)</th>
							<th></th>
							<th class="align-right">REMAINING (Rp.)</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					
					
					
					<c:forEach items="${budgets}" var="budgets1" varStatus="status">
						<tr id="${budgets1.budgetId}">
							<td class="name">
							<h4>${budgets1.coaName}</h4>
							<span>${budgets1.coaDescription}</span>	
							</td>
							<%-- <td>
								<span class="amount">${budgets1.countPreviousMonth}</span>
								<!-- <div class="progress progress-danger">
								<div class="bar" style="width:80%"></div>
								</div> -->
							</td>
							
							<td>
								<span class="amount">${budgets1.countNowMonth}</span>
								<!-- <div class="progress progress-success">
								<div class="bar" style="width:40%"></div>
								</div> -->
							</td> --%>
							
							<td class="allocated">
								<span class="amount" id="amount-${budgets1.budgetId}">${budgets1.totalBudget}</span>
								<!-- <div class="progress progress-success">
								<div class="bar" style="width:50%"></div>
								</div> -->
							</td>
							<td></td>
							
							<td class="align-right remaining">								
								<div class="progress progress-success margin-bottom-zero">
									<div class="bar" style="width:${budgets1.remainingPercentage}%"></div>
								</div>
								<span class="remaining-amount" id="remaining-amount-${budgets1.budgetId}">${budgets1.remainingBudget}</span>
							</td>
							
							<td>
								<div class="btn-group dropdown fr">
									<c:choose>
										<c:when test="${canUpdate == true && canDelete == true}">
											<c:url var="editBudget" value="/finance/budgets/${budgets1.budgetId}/edit" />
											<a href="#" class="btn btn-mini show-extend edit" id="${budgets1.budgetId}">Edit</a>
											<button class="btn dropdown-toggle" data-toggle="dropdown">
												<span class="caret"></span>
											</button>
											<ul class="dropdown-menu">
												<li><a tabindex="-1" href="#" class="delete_popup" id="${budgets1.budgetId}">Hapus</a></li>
											</ul>
										</c:when>
										<c:when test="${canUpdate == true && canDelete == false}">
											<c:url var="editBudget" value="/finance/budgets/${budgets1.budgetId}/edit" />
											<a href="#" class="btn btn-mini show-extend edit" id="${budgets1.budgetId}">Ralat</a>
										</c:when>
										<c:when test="${canUpdate == false && canDelete == true}">
											<a href="#" class="btn btn-mini delete_popup" id="${budgets1.budgetId}">Hapus</a>
										</c:when>
										<c:otherwise>
											<a href="#" class="btn btn-mini disabled">Ralat</a>
										</c:otherwise>
									</c:choose>
								</div>
							</td>
						</tr>
						</c:forEach>
						
					</tbody>
					
					</table>
					
					</c:otherwise>
				</c:choose>
				<h3>${message}</h3>
				<!-- <c:if test="${!empty budgets}">
					
				</c:if> -->
			</div>
		</div>
		<!-- END : CONTENT AREA -->

	</div>
	<div class="clear"></div>
</div>

<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Budget List</title>
</head>
<body>
	<c:if test="${!empty budgets}">
		<table>
			<tr>
				<th>Account ID</th>
				<th>Amount</th>
				<th>Monthly Amount</th>
				<th>Control</th>
			</tr>
	
			<c:forEach items="${budgets}" var="budget">
				<tr>
					<td><c:out value="${budget.accountName}"/></td>
					<td><c:out value="${budget.amount}"/></td>
					<td>
						<c:forEach items="${budget.monthlyAmount}" var="amount" varStatus="status">
						<c:out value="${status.index + 1}"/>:<c:out value="${amount}"/><br />
						</c:forEach>
					</td>
					<td><a href="budgets/${budget.id}/edit">Edit</a> <a href="budgets/${budget.id}/delete">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html> --%>