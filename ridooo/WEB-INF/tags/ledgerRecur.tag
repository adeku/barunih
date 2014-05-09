<%@tag description="display the whole nodeTree" pageEncoding="UTF-8"%>
<%@attribute name="balances" type="java.util.Map" required="true" %>
<%@attribute name="account" type="verse.accounting.model.vac_chart_accounts" required="true" %>
<%@attribute name="symbol" type="java.lang.String" required="true" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tr >
	<td>
		<h4>
			<c:url var="viewAccountDetail" value="/finance/generalledger/${account.id}/view" />
			<a href="${viewAccountDetail}"
				id="list_<c:out value="${account.id}" />">
				<c:out value="${account.code}" /></a>
		</h4>
	</td>
	<td><c:out value="${account.category}"/></td>
	
	
	<td><h4>${symbol}<c:out value="${account.name}"/></h4>
		 <span><c:out value="${account.description}"/></span>
	</td>
	<td> 
		<c:choose>
			<c:when test="${account.status == 0}">
				<span class="label label-important">Disabled</span>
			</c:when>
			<c:when test="${account.status == 1}">
				
			</c:when>
		</c:choose>
	</td>
	
	<td><span class="amount"><c:out value="${balances.get(account.id)}" /></span></td>									
</tr>
<c:if test="${!empty account.children}">
    <c:forEach var="child" items="${account.children}">
<%--     	${child.name}ddd --%>
		
        <template:ledgerRecur account="${child}" symbol="${symbol}&mdash;" balances="${balances}" />
    </c:forEach>
</c:if>
