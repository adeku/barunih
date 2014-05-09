<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/htmlCompresorTag.tld" prefix="html"%>
<%@ taglib uri="rupiahFunctions" prefix="f" %>

<c:choose>
	<c:when test="${!empty reports}">
		<table class="table table-striped customer">
		<colgroup>
			<col>
			<col class="w200">
			<col class="w200">
			<col class="w200">
		</colgroup>
		<thead>
			<tr>
				<c:choose>
					<c:when test="${type.equals('AP')}">
						<th>vendor</th>
					</c:when>
					<c:otherwise>
						<th>customer</th>
					</c:otherwise>
				</c:choose>
				<th>phone no.</th>
				<th class="align-right">current (Rp.)</th>
				<th class="align-right">31-60 (Rp.)</th>
				<th class="align-right">61-90(Rp.)</th>
				<th class="align-right">over 90 (Rp.)
				</th><th class="align-right">Total (Rp.)
			</th></tr>
		</thead>
		<tbody>
			<c:forEach items="${reports}" var="report">
						<tr>
							<td>
								<h4><a href="#">${report.firstName} ${report.lastName}</a></h4>
								<span>${report.address} ${report.city}</span>
							</td>
							<td><span class="number">${report.phone}</span></td>
							<td><span class="number big fr">${f:rupiahCurrency(report.amountCurrent)}</span></td>
							<td><span class="number big fr">${f:rupiahCurrency(report.amountLastMonth)}</span></td>
							<td><span class="number big fr">${f:rupiahCurrency(report.amountLast2Month)}</span></td>
							<td><span class="number big fr">${f:rupiahCurrency(report.amountOver2Month)}</span></td>
							<td><span class="number big fr">${f:rupiahCurrency(report.amountTotal)}</span></td>
						</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td></td>
				<td><h4>Total (Rp.)</h4></td>
				<td><span class="number big fr">${f:rupiahCurrency(TCurent)}</span></td>
				<td><span class="number big fr">${f:rupiahCurrency(T1M)}</span></td>
				<td><span class="number big fr">${f:rupiahCurrency(T2M)}</span></td>
				<td><span class="number big fr">${f:rupiahCurrency(TOM)}</span></td>
				<td><span class="number big fr">${f:rupiahCurrency(TTotal)}</span></td>	
				</tr>
			</tfoot>
		</table>
		${pagination}
	</c:when>
	<c:otherwise>
		<div class="clear"></div>
		<div class="empty-state book defaault-aging-report">
			<div class="feat"></div>
			<h2>Start with A/R or A/P</h2>
		</div>
		<div class="clear"></div>
	</c:otherwise>	
</c:choose>