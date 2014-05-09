<%@tag description="display the whole nodeTree" pageEncoding="UTF-8"%>
<%@attribute name="canUpdate" type="java.lang.Boolean" required="true" %>
<%@attribute name="account" type="verse.accounting.model.vac_chart_accounts" required="true" %>
<%@attribute name="symbol" type="java.lang.String" required="true" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- <c:url var="api" value="/api/companies/1/finance/accounts/" /> --%>
<tr>
	<td>
		<h4>
			<a href="javascript:void(0)"
				id="list_<c:out value="${account.id}" />"
				onClick="viewDetailsCOA('<c:out value="${account.id}" />')"
<%-- 				onClick="viewDetailsCOA('<c:out value="${account.id}" />', '${api}')" --%>
				class="show-extend"><c:out value="${account.code}" /></a>
		</h4>
	</td>
	<%-- <td><c:out value="${account.code}" /></td>--%>
	<td><c:out value="${account.category}" /></td>


	<td><h4>
			${symbol}<c:out value="${account.name}" />
		</h4> <span><c:out value="${account.description}" /></span></td>
	<td><c:choose>
			<c:when test="${account.status == 0}">
				<span class="label label-important">Disabled</span>
			</c:when>
			<c:when test="${account.status == 1}">

			</c:when>
		</c:choose></td>

	<td>
		<span class="amount">
			<c:out value="${account.lastBalance}" />
		</span>
	</td>
	<td>

		<div class="btn-group dropdown fr">
			<c:if test="${canUpdate}">
				<c:url var="editAccount"
					value="/finance/accounts/${account.id}/edit" />
				<a href="${editAccount}" class="btn btn-mini">Edit</a>
				<button class="btn dropdown-toggle" data-toggle="dropdown">
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu pull right">
					<c:choose>
						<c:when test="${account.status == 1}">
							<li><a class="disable_popup" id="${account.id}"
								tabindex="-1" href="#">Disable</a></li>
						</c:when>
						<c:otherwise>
							<li><a class="enable_popup" id="${account.id}"
								tabindex="-1" href="#">Enable</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</c:if>
			<c:if test="${!canUpdate}">
				<a href="javascript:void(0)" class="btn btn-mini disabled">Edit</a>
			</c:if>
		</div>
	</td>
</tr>
<c:if test="${!empty account.children}">
    <c:forEach var="child" items="${account.children}">
<%--     	${child.name}ddd --%>
		
        <template:coaRecur account="${child}" symbol="${symbol}&mdash;" canUpdate="${canUpdate}" />
    </c:forEach>
</c:if>
