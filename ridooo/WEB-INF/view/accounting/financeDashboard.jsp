<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">

	// Create and populate the data table.
/* 	var data = google.visualization.arrayToDataTable([
		['Month', 'Revenge',     'Expense',],
		['Older',  50000000,    250000000],
		['May',  	 180000000,   130000000],
		['Jun',  	 200000000,   270000000],
		['Jul',    350000000,   250000000],
		
		
		['Aug',    70000000,    100000000],
		['Sep',    300000000,   250000000],
		['Future', 0,           0]
	]); */
	$(document).ready(function(){
		var chart = new CanvasJS.Chart("chartContainer", {
			colorSet:"verseColumnChart",
			culture: "id",
			legend: {
				fontFamily: "trebuchet-ms",
				fontSize: 13,
				fontColor: "#959595",
			},

		    data: [  //array of dataSeries     
				{ //dataSeries - first quarter
		  		/*** Change type "column" to "bar", "area", "line" or "pie"***/        
				type: "column",
				name: "Revenue",
				showInLegend: true,
				dataPoints: ${financeModel.revenueChart}
			},
			{ //dataSeries - second quarter
				type: "column",
				name: "Expense",  
				showInLegend: true,
				dataPoints: ${financeModel.expenseChart}
		    }
		    ],
			axisY:{
				gridThickness: 0.1,
				labelFontFamily: "arial",
				labelFontSize: 12,
				labelFontColor: "#959595",
			},
			axisX:{
				labelFontFamily: "arial",
				labelFontSize: 12,
				labelFontColor: "#959595",
			}
		});
		chart.render();
		    
	});
	
</script>

<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">	
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">					
				<div class="combined-grid">
<%-- 				${financeModel.revenueChart} --%>
				<h1>Finance Dashboard</h1>
					<p class="subtitle">Showing latest financial summary</p>
<!-- 					<h4 class="font-dasboardfinance color-font income-expense">INCOMES & EXPENSES</h4>	 -->
<!-- 					<div id="visualization" class="charts column"></div> -->
					
					<div id="chartContainer" style="height: 300px; width: 100%;"></div>
					
				
					<div class="inner-grid1 empty-statebook">
						<div>
							<c:url var="invoices" value="/finance/invoices"/>
							<a href="${invoices}"  class="font-dasboardfinance">INVOICES</a>
							<c:url var="addInvoice" value="/finance/invoices/create"/>
							<span class="fr"><a href="${addInvoice}" class="alt-link font-dasboardfinance2">Add</a></span>
						</div>
						<div class="font-dasboardfinance2  margin-top7">
							Balance <span class="amount fr font-dfinanceamount">${financeModel.invoiceBalance}</span>
						</div>
						<div class="font-dasboardfinance2  margin-top7">
							Overdue <span class="amount fr font-dfinanceamount2">${financeModel.overdueInvoice}</span>
						</div>					
					
						<c:choose>
							<c:when test="${empty financeModel.invoices}">
								<table class="inner-grid">
									<colgroup>
										<col/>
										<col class="amount"/>
									</colgroup>
									<thead>
										<tr>
											<th  class="font-dasboardfinance3">OVERDUE INVOICES</th>
											<th  class="font-dasboardfinance3 align-text">AMOUNT (Rp.)</th>
										</tr>
									</thead>
								</table>
								<div class="empty-state book empty-statebook">
									<div class="clear"></div>
									<h2>No Invoices Available</h2>
								</div>
							</c:when>
							<c:otherwise>
								<table class="inner-grid">
									<colgroup>
										<col/>
										<col class="amount"/>
									</colgroup>
									<thead>
										<tr>
											<th  class="font-dasboardfinance3">OVERDUE INVOICES</th>
											<th  class="font-dasboardfinance3 align-text">AMOUNT (Rp.)</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${financeModel.invoices}" var="invoice" varStatus="status">
											<tr>
												<td>
													<c:url var="image" value="/img/no-person.png"/>
													<span class="avatar fl">
														<img src="${financeModel.customerPhotos[status.index] }" width="50" height="50" alt="Avatar">
													</span>
													<span class="avatar-pull">
														<c:url var="viewInvoice" value="/finance/invoices/${invoice.id}/view"/>
														<c:url var="viewCustomer" value="/people/customers/${invoice.roleName.id}/profile"/>
														<h4><a href="${viewInvoice}">Invoice ${invoice.id}</a></h4>
														<span><a href="${viewCustomer}" class="alt-link">${invoice.roleName.name} - ${invoice.transaction_metas[0].value}</a></span>
													</span>
												</td>
												<td>
													<span class="amount">${invoice.amount}</span>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:otherwise>
						</c:choose>
					</div>
				
					<div class="inner-grid1 last empty-statebook">
						<div>
							<c:url var="bills" value="/finance/bills"/>
							<a href="${bills}" class="font-dasboardfinance">BILLS</a>
							<c:url var="addBill" value="/finance/bills/create"/>
							<span class="fr"><a href="${addBill}" class="alt-link font-dasboardfinance2">Add</a></span>
						</div>
						
						<div class="font-dasboardfinance2  margin-top7">
							Balance <span class="amount fr font-dfinanceamount">${financeModel.billBalance}</span>
						</div>
						
						<div class="font-dasboardfinance2  margin-top7">
							Overdue <span class="amount fr font-dfinanceamount2">${financeModel.overdueBill}</span>
						</div>
						
						<c:choose>
							<c:when test="${empty financeModel.bills}">
								<table class="inner-grid">
									<colgroup>
										<col/>
										<col class="amount"/>
									</colgroup>
									<thead>
										<tr>
											<th class="font-dasboardfinance3">OVERDUE BILLS</th>
											<th class="font-dasboardfinance3 align-text">AMOUNT (Rp.)</th>
										</tr>
									</thead>
								</table>
								<div class="empty-state book empty-statebook">
									<div class="clear"></div>
									<h2>No Bills Available</h2>
								</div>
							</c:when>
							<c:otherwise>
								<table class="inner-grid">
									<colgroup>
										<col/>
										<col class="amount"/>
									</colgroup>
									<thead>
										<tr>
											<th class="font-dasboardfinance3">OVERDUE BILLS</th>
											<th class="font-dasboardfinance3 align-text">AMOUNT (Rp.)</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${financeModel.bills}" var="bill" varStatus="status">
											<tr>
												<td>
													<c:url var="image" value="/img/no-person.png"/>
													<span class="avatar fl">
														<img src="${image }" width="50" height="50" alt="Avatar">
													</span>
													<span class="avatar-pull">
														<c:url var="viewBill" value="/finance/bills/${bill.id}/view"/>
														<h4><a href="${viewBill}">Bill ${bill.id}</a></h4>
														<span><a href="#" class="alt-link">${bill.roleName.name} - ${invoice.transaction_metas[0].value}</a></span>
													</span>
												</td>
												<td>
													<span class="amount">${bill.amount}</span>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>									
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				
				<div class="grid1 alt2">
					<c:url var="budgets" value="/finance/budgets"/>
					<h4 class="margin-budget">BUDGETS</h4>
					<c:choose>
						<c:when test="${empty financeModel.categories}">
							<div class="empty-state book margin-topbudget">
								<h2>No Budgets Available</h2>
							</div>
						</c:when>
						<c:otherwise>
							<c:forEach items="${financeModel.categories}" var="category" varStatus="status">
									${category}
									<c:choose>
										<c:when test="${financeModel.budgetsPercentage[status.index] == 100}">
											<div class="progress progress-danger margin-budget">
												<div class="bar" style="width: ${financeModel.budgetsPercentage[status.index]}%"></div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="progress progress-success margin-budget">
												<div class="bar" style="width: ${financeModel.budgetsPercentage[status.index]}%"></div>
											</div>
										</c:otherwise>
									</c:choose>
							</c:forEach>
						</c:otherwise>
					</c:choose>			
				</div>
			</div>
		</div>						
	</div>
	<!-- END : CONTENT AREA -->
	<div class="clear"></div>
</div>