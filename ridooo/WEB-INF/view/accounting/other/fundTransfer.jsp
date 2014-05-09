<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">

		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
				<div class="content-header">
					<h1>Fund Transfer ${transactionId}</h1>
					<p class="subtitle">Transfer funds between bank accounts</p>
				</div>
				<c:if test="${!empty action}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Well done!</strong> You have successfully ${action} ${object}
					</div>
				</c:if>
				<c:if test="${!empty message}">
					<div class="alert alert-error">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Error!</strong> ${message}
					</div>
				</c:if>
		
				<c:choose>
					<c:when test="${empty transactionId}">
						<c:url var="fundTransfer" value="/finance/fundtransfers/create" />
					</c:when>
					<c:otherwise>
						<c:url var="fundTransfer" value="/finance/fundtransfers/${transactionId}/edit" />
					</c:otherwise>
				</c:choose>
				
				<form:form modelAttribute="transferForm" method="POST"
					action="${fundTransfer}" class="form-horizontal validate">
	
	<!-- 				<div class='control-group'> -->
	<!-- 					<label class='control-label' for='trxDate'>Date*</label> -->
	<!-- 					<div class='controls'> -->
	<!-- 						<div class='input-append'> -->
	<%-- 							<form:input path="trxDate" class="input-small datepicker" readonly="true" /> --%>
	<!-- 							<span class='add-on'><i class='icon-calendar'></i></span> -->
	<!-- 							<span id='trxDate.errors' class='error'></span> -->
	<!-- 						</div> -->
	<!-- 					</div> -->
	<!-- 				</div> -->
	
					<cform:input label="Date*" path="trxDate" size="small" datepicker="true" value="${transferForm.trxDate}"/>
					<cform:select label="Transfer From*" path="fromBankId" size="medium" options="${accounts}" selected="${transferForm.fromBankId}"/>
					<cform:select label="Transfer To*" path="toBankId" size="medium" options="${accounts}" selected="${transferForm.toBankId}" />
					<cform:input label="Amount*" path="amount" prepend="Rp." size="small" value="${transferForm.amount}"/>
					<cform:textarea label="Note" path="note" size="medium" required="false" value="${transferForm.note}" />
					<div class="form-actions vrs-nopadl">
						<form:button name="action" value="${save}" class="btn btn-positive">Save</form:button>
						<c:url var="cancel" value="/finance/fundtransfers" />
						<a href="${cancel}" class="btn">Cancel</a>
					</div>
				</form:form>
			</div>
		</div>
		<!-- END : CONTENT AREA -->

	</div>
	<div class="clear"></div>
</div>