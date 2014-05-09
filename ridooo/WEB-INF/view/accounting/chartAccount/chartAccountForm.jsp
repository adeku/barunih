<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="chartAccount" value="/finance/accounts/" />
<%-- <c:url var="apiChartAccount" value="/api/companies/1/finance/accounts" /> --%>
<script type="text/javascript">
<!--
	function getAccounts() {
		var parentOption = $('select#parent');
		$.ajax({
			type : "GET",
			url : "${chartAccount}"+$('select#category').val()+"/getaccounts",
			beforeSend : function() {
				parentOption.empty();
			}
		}).done(function(resPonseText) {
			var obj = jQuery.parseJSON(resPonseText);
			data = obj.data;
			console.log(data);
			console.log(parentOption);
			
			parentOption.append($('<option></option>').val("0").html("None (It's a parent account)"));
			if(data.length != 0){
				parentOption.prop('disabled', false);
				for (var i = 0; i < data.length; i++) {
					parentOption.append($('<option></option>').val(
							data[i].id)
							.html('('+data[i].code+') '+data[i].name));
				}
			}
			else{
				parentOption.prop('disabled', 'disabled');
			}
		});
	};
	function chackValidAddCOA (){ 
		var canSave = false;
		$('#messagesAlert').html('');
		if ($('#oForm').valid()) {
			canSave = true;
		} else {
			$('#messagesAlert').html('<div class="alert alert-error"><button type="button" class="close" data-dismiss="alert">&times;</button><strong>Error!</strong> Missing required fields.</div>');
		}
		return canSave;
	}
// 	function api(){
// 		$.ajax({
// 			type : "POST",
// 			url : "${apiChartAccount}/${account.id}/edit",
			
// 			data : $('form').serialize()
// 		}).done(function(resPonseText) {
// 			alert(resPonseText);
// 		});
// 	}
	window.onload = function() { 
	  var txts = document.getElementsByTagName('TEXTAREA');

	  for(var i = 0, l = txts.length; i < l; i++) {
	    if(/^[0-9]+$/.test(txts[i].getAttribute("maxlength"))) { 
	      var func = function() { 
	        var len = parseInt(this.getAttribute("maxlength"), 10); 

	        if(this.value.length > len) { 
	          this.value = this.value.substr(0, len); 
	          return false; 
	        } 
	      }

	      txts[i].onkeyup = func;
	    } 
	  } 
	}

	$(document).ready(function() {
		var length = 100 - $('#description').val().length;
		$('select#category').change(function(){
			getAccounts();
		});
		
		$('#description').keyup(function(){
			length = $(this).val().length;
			length = 100 - length;
			
			if(length >= 0)
				$('#desc_help').text(length);
		});
// 		$('form').submit(function(e){
// 			e.preventDefault();
// 			api();
// 		});
	});
//-->
</script>
<div class="content">
		<!-- GRID CONTAINER -->
		<div class="grid-container">
			
			<!-- START : CONTENT AREA -->
			<div class="grid-full">
				<div class="grid-content">
					<div class="content-header">
						<c:choose>
							<c:when test="${action == 'create'}">
								<h1>Create new chart of accounts</h1>
								<p class="subtitle">Create a new account</p>
							</c:when>
							<c:when test="${action == 'edit'}">
								<h1>Edit chart of accounts</h1>
								<c:url var="staffProfile" value="/academic/staffs/${account.modifiedBy}/details" />
								<p class="subtitle">Last edited ${date} by <a href="${staffProfile}">${name}</a></p>
							</c:when>
						</c:choose>
						
					</div>
					
					<div id="messagesAlert">
						
					</div>
					
					<c:choose>
						<c:when test="${action == 'create'}">
							<c:url var="postForm" value="$/finance/accounts/create" />
						</c:when>
						<c:when test="${action == 'edit'}">
							<c:url var="postForm" value="/finance/accounts/${account.id}/edit" />
						</c:when>
					</c:choose>
					<form:form modelAttribute="oForm" method="POST" action="${postForm }" class="form-horizontal validate" onSubmit="return chackValidAddCOA()">
<!-- 						<input type="hidden" name="roleId" value="1" /> -->
						<cform:select label="Type" path="category" options="${categoryOption}" selected="${account.category}" />	
						<cform:input label="Code*" path="code" size="small" value="${account.code}"/>
						<cform:input label="Account Name*" path="name" size="medium" value="${account.name}" />
						<cform:textarea label="Description" path="description" size="xlarge" value="${account.description}" maxlength="100" help="<span id='desc_help'>${100 - descriptionLength}</span> characters left"/>
						<c:choose>
							<c:when test="${parentOptionSize == 1}">
								<cform:select label="Sub Account of" path="parent" options="${parentOption}" size="large" selected="${account.parent}" disabled="true"/>
							</c:when>
							<c:otherwise>
								<cform:select label="Sub Account of" path="parent" options="${parentOption}" size="large" selected="${account.parent}"/>
							</c:otherwise>
						</c:choose>
						
						
						<c:choose>
							<c:when test="${action == 'create'}">
								<cform:input label="Starting Balance*" path="starting_balance" number="true" value="${account.starting_balance}"/>
							</c:when>
							<c:when test="${action == 'edit'}">
								<c:choose>
									<c:when test="${editableBalance == true}">
										<cform:input label="Starting Balance*" path="starting_balance" number="true" value="${account.starting_balance}" />
									</c:when>
									<c:otherwise>
										<cform:input label="Starting Balance*" path="starting_balance" number="true" value="${account.starting_balance}" disabled="true" />
									</c:otherwise>
								</c:choose>
							</c:when>
						</c:choose>
						<div class="form-actions vrs-nopadl">
							<c:choose>
								<c:when test="${action == 'create'}">
									<form:button name="save" value="add" class="btn btn-positive">Save</form:button>
								</c:when>
								<c:when test="${action == 'edit'}">
									<form:button name="save" value="edit" class="btn btn-positive">Save</form:button>
								</c:when>
							</c:choose>
							
							<c:url var="cancel" value="/finance/accounts" />
							<a href="${cancel}" class="btn cancel">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
			<!-- END : CONTENT AREA -->
			
		</div>
		<div class="clear"></div>
	</div>