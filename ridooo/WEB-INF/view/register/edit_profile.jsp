<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>


<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">

		<!-- START : CONTENT AREA -->
		<div class="grid2">
			<div class="content-header">
				<h1>Profile</h1>
			</div>
			<c:choose>
				<c:when test="${!empty sessionScope.u9988u}">${message}

			<c:url var="edit_profile" value="reg/312" />
					<form:form modelAttribute="edit_Profile" method="POST"
						action="${edit_profile}" class="form-horizontal">

						<cform:input label="Firstname" path="firstname"
							labelclass="firstname" size="small" />

						<cform:input label="Lastname" path="lastname"
							labelclass="lastname" size="small" />

						<cform:input label="Password" path="password"
							labelclass="password" size="small" minlength="10" />

						<cform:input label="Shortcode" path="shortcode"
							labelclass="shortcode" />

						<cform:input label="Date Birth" path="datebirth"
							labelclass="datebirth" datepicker="true" />

						<cform:select label="Gender" path="gender"
							options="${genderOption}" />

						<cform:input label="City" path="city" labelclass="city" />

						<cform:input label="Email" path="email" labelclass="email"
							email="true" /> ${MYemail}
						
						<cform:input label="Mobile Phone" path="mobilephone"
							labelclass="mobilephone" email="true" /> ${MYphone}

						<cform:input label="ID Card" path="IDCard" labelclass="IDCard" />

						<cform:input label="Driving License" path="DrivingLicense"
							labelclass="DrivingLicense" />

						<cform:input label="Passport" path="passport"
							labelclass="passport" />

						<cform:input label="Birth Certificate" path="BirthCertificate"
							labelclass="BirthCertificate" />

						<cform:input label="NIK" path="nik" labelclass="nik" />

						<cform:input label="NIP" path="nip" labelclass="nip" />

						<div class="form-actions">
							<form:button value="${save}" class="btn btn-positive">Save</form:button>
						</div>

					</form:form>
				</c:when>
			</c:choose>
			<div class="clear"></div>
		</div>
		<!-- 		<div class="grid1 last">&nbsp;</div> -->

		<!-- END : CONTENT AREA -->

	</div>

</div>

