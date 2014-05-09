<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>


<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">

		<!-- START : CONTENT AREA -->
		<div class="grid2">
			<div class="grid-content">

				<div class="content-header">
					<h2>Add Accounts</h2>
					<p class="subtitle">Create a new account</p>
				</div>
				<c:url var="addChartAccount" value="/finance/accounts/create" />
				<form:form modelAttribute="oForm" method="POST"
					action="${addChartAccount}" class="form-horizontal validate">
					<cform:select label="Company" path="companyID"
						options="${companyOption}" />
					<cform:input label="Code *" path="code" size="small" required="true"/>
					<cform:select label="Category" path="category"
						options="${categoryOption}" />
					<cform:input label="Chart Account Name *" path="name" size="medium" required="true" />
					<cform:textarea label="Description" path="description" size="xlarge"  required="false"/>
					<cform:input label="Starting Balance *" path="starting_balance"
						number="true" required="true" />
					<cform:select label="Choose Parent Chart" path="parent"
						options="${parentOption}" size="large"/>
	
					<div class="form-actions">
						<form:button name="save" value="Add" class="btn btn-positive">Submit</form:button>
						<a href="../accounts" class="btn" style="color: #000000">Cancel</a>
					</div>
				</form:form>
			</div>
			<!-- END : CONTENT AREA -->
		</div>
		<div class="clear"></div>
	</div>
</div>