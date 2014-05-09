<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>


<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">

		<!-- START : CONTENT AREA -->
		<div class="grid2">
			<div class="content-header">
				<h1>Create Accounts</h1>
				<p class="subtitle">Create new system account for existing staffs. Need to add more employees?</p>
			</div>
			<br /> Message from singgular check = ${message}
			<c:url var="addRegisterPersonal" value="/reg/112" />
			<form:form modelAttribute="regPErsonal" method="POST"
				action="${addRegisterPersonal}" class="form-horizontal">
				<%-- <form:label path="firstname" value="${firstname}">first name:</form:label>
				<form:input path="firstname" />
				<br />
				<br />

				<form:label path="lastname" value="${lastname}">last name:</form:label>
				<form:input path="lastname" />
				<br />
				<br />
				<form:label path="password" value="${password}">password :</form:label>
				<form:input path="password" />
				<br />
				<br />
				<form:label path="email" value="${email}">email :</form:label>
				<form:input path="email" />
				<br />
				<br />
				<form:label path="shortcode" value="${shortcode}">short code:</form:label>
				<form:input path="shortcode" />
				<br />
				<br />
				<form:button value="${save}">Save</form:button>
				<br />
				<br /> --%>

				<cform:input label="Firstname" path="firstname" value="${firstname}" />
				<cform:input label="Lastname" path="lastname" value="${lastname}" />
				<cform:input label="Password" path="password" value="${password}" />
				<cform:input label="Email" path="email" value="${email}" />
				<cform:input label="Shortcode" path="shortcode" value="${shortcode}" />
				<div class="form-action">
					<form:button value="${save}" class="btn btn-positive">Save</form:button>
				</div>
			</form:form>

			<a href="../">Back Home</a>
			<div class="clear"></div>
		</div>
		<!-- 		<div class="grid1 last">&nbsp;</div> -->

		<!-- END : CONTENT AREA -->

	</div>

</div>

