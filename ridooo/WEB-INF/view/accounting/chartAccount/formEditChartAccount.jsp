<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">

		<!-- START : CONTENT AREA -->
		<div class="grid2">

			<div class="content-header">
				<h1>Add Accounts</h1>
				<p class="subtitle">Edit account details - Last edited </p>
			</div>
			<c:url var="addChartAccount" value="/finance/accounts/edit" />
			<form:form modelAttribute="oForm" method="POST"
				action="${addChartAccount}" class="form-horizontal validate">
				

				<cform:select label="Company" path="companyID"
					options="${companyOption}" />
				<cform:input label="Code *" path="code" size="small" required="true"/>
				<cform:select label="Category" path="category"
					options="${categoryOption}" />
				<cform:input label="Chart Account Name *" path="name" size="medium" required="true"/>
				<cform:textarea label="Description" path="description" size="xlarge"  required="false"/>
				<cform:input label="Starting Balance *" path="starting_balance"
					number="true" required="true"/>
				<cform:select label="Choose Parent Chart" path="parent"
					options="${parentOption}" size="large"/>
				<div class="form-actions">
					<form:button name="save" value="Edit" class="btn btn-positive">Save	</form:button>
					<!-- 					<input type="reset" value="Reset"> -->
					<a href="../accounts" class="btn"
						style="color: #000000">Cancel</a>
				</div>

			</form:form>
			<div class="clear"></div>
			<!-- END : CONTENT AREA -->
		</div>
		
	</div>
</div>
<script type="text/javascript">
	function checkedParent(str) {
		var pre = "#parent";
		var ulang = 1;
		var founded = false;
		while ($(pre + ulang).val() != null && !founded) {
			if ($(pre + ulang).val() == str) {
				founded = true;
				$(pre + ulang).prop("checked", true);
			} else
				ulang++;
		}
	}
	function clearParent() {
		var pre = "#parent";
		var ulang = 1;
		while ($(pre + ulang).val() != null) {
			$(pre + ulang).prop("checked", false);
			ulang++;
		}
	}
	function getDetail(str) {
		clearParent();
		$.ajax({
			type : "POST",
			url : "../chartAccINFO/1/" + str
		}).done(function(resPonseText) {
			var obj = jQuery.parseJSON(resPonseText);
			$("#companyID").val(obj.companyId);
			$("#code").val(obj.code);
			$("#category").val(obj.category);
			$("#name").val(obj.name);
			$("#description").val(obj.description);
			$("#starting_balance").val(obj.starting_balance);
			checkedParent(obj.parent);
		});
	}
	getDetail($("#chartID").val());
</script>
<!-- </body>
</html> -->