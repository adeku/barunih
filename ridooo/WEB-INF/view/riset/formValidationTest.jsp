<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

		<!-- CONTENT -->
		<div class="content">
			
			<!-- GRID CONTAINER -->
			<div class="grid-container">
				
				<div class="grid1 last extended">
					<div class="grid-content">
						<h3>Right Side Bar </h3>
						
					</div>
				</div>
				
				<!-- START : CONTENT AREA -->
				<div class="grid-full">
					<div class="grid-content">
					
					<h1>Fom Example</h1>
					${messages}
					<c:url var="linkPost" value="" />
				<form:form modelAttribute="formPost" method="POST"
					action="${linkPost}"  class="form-horizontal" >

					<spring:bind path="textOnly">
					<cform:input label="Texxt Field" path="textOnly"
						value="${textOnly}" />
					</spring:bind>
						
					

					<cform:input label="Text With minimum and maximum length" path="textOnlyMinMaxCharacter" value="${textOnlyMinMaxCharacter}" />
					<form:errors path="textOnlyMinMaxCharacter" cssClass="error" />

					<cform:input label="Number Only" path="number" value="${number}" />
					<form:errors path="number" cssClass="error" />

					<cform:input label="Number Only With MAx and Min Value"
						path="numberMinMax" value="${numberMinMax}" />
					<form:errors path="numberMinMax" cssClass="error" />

					<cform:input label="Decimal min max"
						path="numbeerDecimal" value="${numbeerDecimal}" />
					<form:errors path="numbeerDecimal" cssClass="error" />
					
					<cform:input label="Email"
						path="emailInput" value="${emailInput}" />
					<form:errors path="emailInput" cssClass="error" />
					
					
					<cform:input label="Date"
						path="dateData" value="${dateData}" />
					<form:errors path="dateData" cssClass="error" />
					
					<cform:input label="Number Can be Null"
						path="numberCanBeNull" value="${numberCanBeNull}" />
					<form:errors path="numberCanBeNull" cssClass="error" />
					

					<div class="form-actions">
						<form:button path="action" value="save"  class="btn btn-positive">Save</form:button>
					</div>
				</form:form>
			
					</div>
					<!-- END : CONTENT AREA -->
				</div>
			</div>
			<div class="clear"></div>
			
		</div>
	
	</div>


