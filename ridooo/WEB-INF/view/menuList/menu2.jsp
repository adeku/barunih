<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<script type="text/javascript">
	// Create and populate the data table.
// 	var data = google.visualization.arrayToDataTable();
	
	
// 	var options = {
// 			type: 'column',
// 			target_id: 'visualization', // required
// 			data: data, // required
// 			width: 900,
// 			height: 400
// 		};
		
// 	core.charts(options);

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

<c:url var="schools" value="/academic/schools"/>
<c:url var="students" value="/academic/students"/>
<c:url var="studentsDisable" value="/academic/students/inactive"/>
<c:url var="candidates" value="/academic/candidates"/>
<c:url var="pending" value="/academic/candidates/pending"/>
<c:url var="inprocess" value="/academic/candidates/inprocess"/>
<c:url var="approved" value="/academic/candidates/approved"/>
<c:url var="customers" value="/people/customers"/>
<c:url var="vendors" value="/people/vendors"/>
<c:url var="employee" value="/people/employees"/>
<c:url var="finacee" value="/finance/"/>
<c:url var="peoplee" value="/people/"/>
<!-- CONTENT -->
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">	
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
				
			<div class="grid-content">
				<h1 class="margin-dashboard">DASHBOARD</h1>

				<div class="combined-grid">
					${viewTimeLoad}
					<div id="chartContainer" style="height: 300px; width: 100%;"></div>
					<c:choose>
						<c:when test="${empty vdasboard}">
							<table class="table table-striped empty-statebook">							
										<colgroup>
											<col>
											<col class="w100">
											<col class="w100">
											<col class="amount">
										</colgroup><colgroup>
										
										</colgroup>
										<thead>
											<tr>
												<th>School</th>
												<th>Headmaster</th>
												<th>Students</th>
												<th>Staffs</th>
												<th>Candidates</th>
											</tr>
										</thead>
								</table>
								<div class="empty-state book">
									<h2>No Schools Available</h2>
								</div>
						</c:when>
						<c:otherwise>
							<table class="table table-striped empty-statebook">							
										<colgroup>
											<col>
											<col class="w200">
											<col class="w100">
											<col class="date">
											<col class="date">
											<col class="date">
										</colgroup>
										<thead>
											<tr>
												<th>School</th>
												<th>Address</th>
												<th>Headmaster</th>
												<th>Students</th>
												<th>Staffs</th>
												<th>Candidates</th>
											</tr>
										</thead>
										
										<tbody>
										<c:forEach items="${vdasboard}" var="vdash">
											<c:url var="headmaster" value="/academic/staffs/${vdash.roleId }/details"/>
											<c:url var="school" value="/academic/schools/${vdash.companyId }/details"/>
											<c:url var="schools" value="/academic/schools"/>
											
											<tr>
												<td>
													<span class="avatar fl"><img src="${vdash.extra_photo}" width="50" height="50" alt="Avatar"></span>
													<span class="avatar-pull">
														<h4>
														<a href="${school }"><c:out value="${vdash.name}" /></a>
<%-- 														<c:out value="${vdash.name}" /> --%>
														</h4>
														<c:choose>
															<c:when test="${! empty vdash.city && ! empty vdash.province}">
																<span>${vdash.city }</span> <span>${vdash.province }</span>
															</c:when>
															<c:when test="${! empty vdash.cityCompany && ! empty vdash.provinceCompany}">
																<span>${vdash.cityCompany }</span> <span>${vdash.provinceCompany }</span>
															</c:when>
														</c:choose>
													</span>
													
												</td>
												<td>${vdash.address}</td>
												<td><a class="vrs-allcaps" href="${headmaster }"><c:out value="${vdash.headmaster}" /></a></td>
<%-- 												<td><a href="${headmaster }">Selvie Rida Pangaribuan</a></td> --%>
												<td><span class="score"><c:out value="${vdash.student}" /></span></td>
												<td><span class="score"><c:out value="${vdash.staff}" /></span></td>
												<td><span class="score"><c:out value="${vdash.candidates}" /></span></td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
						</c:otherwise>
					</c:choose>
				</div>				
			
			<!-- END : CONTENT AREA -->
			<div class="grid1 alt2" style="padding-top: 0px !important;">
				
				<form>
					<c:set var="totalSchool" value="${0 }"/>
					 <c:set var="totalTK" value="${0 }"/>
					 <c:set var="totalSD" value="${0 }"/>
					 <c:set var="totalSMP" value="${0 }"/>
					 <c:set var="totalSMA" value="${0 }"/>
					 <c:set var="totalStudent" value="${0 }"/>
					 <c:set var="totalFemale" value="${0 }"/>
					 <c:set var="totalMale" value="${0 }"/>
					 <c:set var="totalDisabled" value="${0 }"/>
					 <c:set var="totalCandidates" value="${0 }"/>
					 <c:set var="totalPending" value="${0 }"/>
					 <c:set var="totalInprocess" value="${0 }"/>
					 <c:set var="totalApproved" value="${0 }"/>
					 <c:set var="totalPeople" value="${0 }"/>
					 <c:set var="totalCustomers" value="${0 }"/>
					 <c:set var="totalVendors" value="${0 }"/>
					 <c:set var="totalEmployees" value="${0 }"/>
					  <c:set var="totalCustomers2" value="${0 }"/>
					 
					 <c:forEach items="${vdasboard}" var="vdash">
					 	 <c:set var="totalSchool" value="${totalSchool + vdash.school }"/>
					 	 <c:set var="totalTK" value="${totalTK + vdash.tk }"/>
					 	 <c:set var="totalSD" value="${totalSD + vdash.sd }"/>
					 	 <c:set var="totalSMP" value="${totalSMP + vdash.smp }"/>
					 	 <c:set var="totalSMA" value="${totalSMA + vdash.sma }"/>
					 	 <c:set var="totalStudent" value="${totalStudent + vdash.student }"/>
					 	 <c:set var="totalFemale" value="${totalFemale + vdash.female }"/>
					 	 <c:set var="totalMale" value="${totalMale + vdash.male }"/>
					 	 <c:set var="totalDisabled" value="${totalDisabled + vdash.disabled }"/>
					 	 <c:set var="totalCandidates" value="${totalCandidates + vdash.candidates }"/>
					 	 <c:set var="totalPending" value="${totalPending + vdash.pending }"/>
					 	 <c:set var="totalInprocess" value="${totalInprocess + vdash.inprocess }"/>
					 	 <c:set var="totalApproved" value="${totalApproved + vdash.approved }"/>
					 	 <c:set var="totalPeople" value="${totalPeople + vdash.people }"/>
					 	 <c:set var="totalCustomers" value="${totalCustomers + vdash.customer }"/>
					 	 <c:set var="totalCustomers2" value="${vdash.customer }"/>
					 	 <c:set var="totalVendors" value="${totalVendors + vdash.vendors }"/>
					 	 <c:set var="totalEmployees" value="${totalEmployees + vdash.employees }"/> 				 	 
					 </c:forEach> 
					 
				<div class="fieldset">
					<div class="item">
						<h4 class="margin-budget"><a href="${schools }">SCHOOLS</a> <span class="rounded-number-label no-border fr align-text">${totalSchool }</span></h4>
					</div>
					<div class="clear"></div>
					<a class="alt-link">TK</a>
					<span class="rounded-number-label no-border fr align-text">${totalTK}</span>
					<div class="clear"></div>
					<a class="alt-link">SD</a>
					<span class="rounded-number-label no-border fr align-text">${totalSD}</span>
					<div class="clear"></div>
					<a class="alt-link">SMP</a>
					<span class="rounded-number-label no-border fr align-text">${totalSMP}</span>
					<div class="clear"></div>
					<a class="alt-link">SMA</a>
					<span class="rounded-number-label no-border fr align-text">${totalSMA}</span>
					<div class="clear"></div>
				</div>
				<div class="fieldset">
					<div class="item">
						<p><h4 class="margin-budget"><a href="${students }">STUDENTS</a> <span class="rounded-number-label no-border fr align-text">${totalStudent}</span></h4>
					</div>
					<div class="clear"></div>
					<a class="alt-link">Female</a>
					<span class="rounded-number-label no-border fr align-text">${totalFemale}</span>
					<div class="clear"></div>
					<a class="alt-link">Male</a>
					<span class="rounded-number-label no-border fr align-text">${totalMale }</span>
					<div class="clear"></div>
					<a href="${studentsDisable }" class="alt-link">Disabled</a>
					<span class="rounded-number-label no-border fr align-text">${totalDisabled }</span>
					<div class="clear"></div>
				</div>		
				<div class="fieldset">		
					<div class="item">
						<p><h4 class="margin-budget"><a href="${candidates }">CANDIDATES</a> <span class="rounded-number-label no-border fr align-text">${totalCandidates}</span></h4>
					</div>
					<div class="clear"></div>
					<a href="${pending }" class="alt-link">Pending</a>
					<span class="rounded-number-label no-border fr align-text">${totalPending}</span>
					<div class="clear"></div>
					<a href="${inprocess }" class="alt-link">In Process</a>
					<span class="rounded-number-label no-border fr align-text">${totalInprocess}</span>
					<div class="clear"></div>
					<a href="${approved }" class="alt-link">Approved</a>
					<span class="rounded-number-label no-border fr align-text">${totalApproved}</span>
					<div class="clear"></div>
				</div>
				<div class="fieldset">	
					<div class="item">
						<p><h4 class="margin-budget"><a href="${peoplee }">PEOPLE</a> <span class="rounded-number-label no-border fr align-text">${totalPeople}</span></h4>
					</div>
					<div class="clear"></div>
					<a href="${customers }" class="alt-link">Customers</a>
					<span class="rounded-number-label no-border fr align-text">${totalCustomers}</span>
					<div class="clear"></div>
					<a href="${vendors }" class="alt-link">Vendors</a>
					<span class="rounded-number-label no-border fr align-text">${totalVendors}</span>
					<div class="clear"></div>
					<a href="${employee }" class="alt-link">Employees</a>
					<span class="rounded-number-label no-border fr align-text">${totalEmployees}</span>
					<div class="clear"></div>
				</div>
				
				
				<div class="vrs-maindash-bdg">
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
											<div class="progress progress-danger">
												<div class="bar" style="width: ${financeModel.budgetsPercentage[status.index]}%"></div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="progress progress-success">
												<div class="bar" style="width: ${financeModel.budgetsPercentage[status.index]}%"></div>
											</div>
										</c:otherwise>
									</c:choose>
							</c:forEach>
						</c:otherwise>
					</c:choose>	
				</div>
			</form>
		</div>
		</div>
		</div>
		</div>
	<div class="clear"></div>
	
</div>
	
