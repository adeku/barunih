<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Accepted Merger</title>
</head>
<body>
${actionTo}

	<c:choose>
		<c:when test="${!empty sessionScope.u9988u}">
	Accepted Merger
	<br />
			<c:url var="actionTo" value="/requestMerge/1" />
			<form:form modelAttribute="acceptedMerger" method="POST"
				action="${actionTo}">
				
				<form:label path="passport">Paspor:</form:label>
				<form:select path="passport" multiple="false" size="1">
					<form:options items="${pasporOption}" />
				</form:select>
				<br />
				<br />

				<form:label path="IDCard">ID Card:</form:label>
				<form:select path="IDCard" multiple="false" size="1">
					<form:options items="${idcardOption}" />
				</form:select>
				<br />
				<br />
				
				<form:label path="email">E - mail:</form:label>
				<form:checkboxes items="${emailcheck}" path="email" /><br/>
				<br />
				<br />
				<form:button  name="save" value="Merger">Merger</form:button> <form:button  name="save" value="Cancel">Cancel</form:button>

			</form:form>
			<br/><br/><br/>${message}<br/><br/>
		</c:when>
		<c:otherwise>
			<c:redirect url="/login/1" />
		</c:otherwise>
	</c:choose>
</body>
</html>