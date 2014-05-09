<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="content">
			
			<!-- GRID CONTAINER -->
			<div class="grid-container">
				
				<!-- START : CONTENT AREA -->
				<div class="grid2">
					<h1>Posting Account</h1>
					<c:url var="postForm" value="../addPosting/1" />
					<form:form modelAttribute="oForm" method="POST" action="${postForm}" class="form-horizontal">
						<fieldset>
							<div class="control-group">
								<div class="controls">
									<form:radiobuttons path="optionAccount"
									items="${cekOptionAccount}" onClick="getTransaction(this.value)" />
								</div>
							</div>
							<div class="control-group">
								<form:label path="categoryTransaction" class="control-label">Transaction :</form:label>
<%-- 								<cform:select path="categoryTransaction" /> --%>
								<form:select path="categoryTransaction" multiple="false" size="1"></form:select>
							</div>
							<div class="control-group">
								<form:label path="assets" class="control-label">Asset :</form:label>
<%-- 								<cform:select path="assets" options="${assetsOption}" /> --%>
								<form:select path="assets" multiple="false" size="1">
										<form:options items="${assetsOption}" />
									</form:select>
							</div>
							<div class="control-group">
								<form:label path="refNumber" class="control-label">RefNumber :</form:label>
								<cform:input-small path="refNumber" />
							</div>
							<div class="control-group">
								<form:label path="amount" class="control-label">Amount :</form:label>
								<cform:input-medium path="amount" />
							</div>
							<div class="control-group">
								<form:label path="cashChooser" class="control-label">Cash :</form:label>
								<form:checkbox path="cashChooser" value="cash" />
							</div>
							<%-- <div class="control-group">
								<form:label path="note" class="control-label">note :</form:label> --%>
								<cform:textarea label="Note :" path="note" />
<!-- 							</div> -->
						</fieldset>
						<div class="form-actions">
							<form:button name="save" value="add" class="btn btn-primary">Save</form:button>
						</div>
					</form:form>
				</div>
				<div class="grid1 last">
					&nbsp;
				</div>
				<!-- END : CONTENT AREA -->
				
			</div>
			<div class="clear"></div>
		</div>
	
	</div>
	
	<script type="text/javascript">
		function getTransaction(str) {
			var selection = $("#categoryTransaction");
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/finance/accountOption/1/" + str,
				beforeSend : function() {
					selection.empty();
				}
			}).done(
					function(resPonseText) {
						var obj = jQuery.parseJSON(resPonseText);
						var dataThis = obj.data;
						for (i = 0; i < dataThis.length; i++) {
							selection.append($('<option></option>').val(
									dataThis[i].value).html(dataThis[i].key));
						}
					});
		}
		
		getTransaction($('input[name=optionAccount]:checked', '#oForm').val());
	</script>