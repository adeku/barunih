<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>

<c:url var="customers" value="/people/customers"/>
<c:url var="vendors" value="/people/vendors"/>
<c:url var="employee" value="/people/employees"/>
<c:url var="customersin" value="/people/customers-inactive"/>
<c:url var="vendorsin" value="/people/vendors-inactive"/>
<c:url var="employeein" value="/people/employees-inactive"/>

<script type="text/javascript">
	
	$(document).ready(function(){
	var chart = new CanvasJS.Chart("chartContainer",
			{
				legend: {
					verticalAlign: "bottom",
					horizontalAlign: "center",
					fontFamily: "trebuchet-ms",
					fontSize: 12,
					fontColor: "#555",
				},
				toolTip:{
					enabled: true,
				},
				colorSet: "versePieChart",
				data: [
				{        
					type: "doughnut",
					indexLabelFontFamily: "trebuchet-ms",       
					indexLabelFontSize: 14,
					startAngle:0,
					indexLabelFontColor: "#555",       
					indexLabelLineColor: "darkgrey", 
					indexLabelPlacement: "inside", 
					toolTipContent: "{name}: {y} people",
					showInLegend: true,
					dataPoints: ${peopleModel.chartData}
				}
				]
			});

			chart.render();
	});
</script>

<!-- CONTENT -->
<div class="content">
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
			<div class="combined-grid">
				<h1>People Dashboard</h1>
				<p class="subtitle">Showing number of customers, vendors, and employees</p>
					<c:choose>
						<c:when test="${empty userLogin}">
							<table class="table table-striped empty-statebook">							
								<colgroup>
									<col>
									<col class="w200">
								</colgroup>
								<thead>
									<tr>
										<th>Last Login</th>
										<th>Position</th>
									</tr>
								</thead>
							</table>
							<div class="empty-state person">
								<h2>No People Available</h2>
							</div>
						</c:when>
						<c:otherwise>
							<table class="table table-striped empty-statebook">							
								<colgroup>
									<col>
									<col class="w200">
								</colgroup><colgroup>
								
								</colgroup>
								<thead>
									<tr>
										<th>Employee</th>
										<th class="align-right">Last Login</th>
										<th class="align-right">Position</th>
										<th class="align-right"></th>
									</tr>
								</thead>
										
								<tbody>
									<c:forEach items="${userLogin}" var="vpeople">	
										<%-- <c:url var="detailPeople" value="/academic/staffs/${vpeople.user_id }/details"/>			 --%>				
										<tr>
											<td>
												<span class="avatar fl"><img src="${pageContext.request.contextPath}/img/no-person.png" width="50" height="50" alt="Avatar"></span>
												<span class="avatar-pull">
													<h4>
														<a href="${vpeople.user_id}"> ${vpeople.name} </a>
													</h4>
													<span>${vpeople.gender }</span>
													<span>${vpeople.age}</span> 
												</span>
											</td>
											<td class="align-right">
												${vpeople.lastLogin}
											</td>
											<td class="align-right">${vpeople.role}</br>
												<%-- <span><c:out value="${vpeople.schoolName}" /> <c:out value="${vpeople.schoolCity}" /></span> --%>
											</td>
											<td></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>				
				</div>	
				<div class="grid1 alt2">
				<form>
				 	 
					 <c:forEach items="${vpeopleDashboard}" var="vdash">
					 	 <c:set var="totalCashier" value="${totalCashier + vdash.cashierTotal }"/>
					 	 <c:set var="totalTeacher" value="${totalTeacher + vdash.teacherTotal }"/>
					 	 <c:set var="totalHeadmaster" value="${totalHeadmaster + vdash.headmasterTotal }"/>
					 	 <c:set var="totalAdmin" value="${vdash.adminTotal }"/>
					 	 <c:set var="totalRole" value="${vdash.allRoles }"/>
					 </c:forEach> 
					 
					<div class="fieldset">
						<div class="item">
							<h4 class="margin-budget"><a href="${customers }">CUSTOMERS</a>							
							<span class="rounded-number-label no-border fr align-text">${totalCustomersActive+totalCustomersDisable}</span></h4>
						</div>
						<div class="clear"></div>
						<a href="${customers }" class="alt-link">Active</a>
						<span class="rounded-number-label no-border fr align-text">${totalCustomersActive}</span>
						<div class="clear"></div>
						<a href="${customersin }" class="alt-link">Disable</a>
						<span class="rounded-number-label no-border fr align-text">${totalCustomersDisable}</span>
						<div class="clear"></div>					
					</div>
					
					<div class="fieldset">
						<div class="item">
							<p><h4 class="margin-budget"><a href="${vendors }">VENDORS</a>								
							<span class="rounded-number-label no-border fr align-text">${totalVendorsActive+totalVendorsDisable}</span>
							</h4>
						</div>
						<div class="clear"></div>
						<a href="${vendors }" class="alt-link">Active</a>
						<span class="rounded-number-label no-border fr align-text">${totalVendorsActive}</span>
						<div class="clear"></div>
						<a href="${vendorsin }" class="alt-link">Disabled</a>
						<span class="rounded-number-label no-border fr align-text">${totalVendorsDisable}</span>
						<div class="clear"></div>
					</div>
							
					<div class="fieldset">		
						<div class="item">
							<p><h4 class="margin-budget"><a href="${employee}">EMPLOYEES</a>
							<span class="rounded-number-label no-border fr align-text">${totalEmployeesActive+totalEmployeesDisable}</span></h4>
						</div>
						<div class="clear"></div>
						<a href="${employee }" class="alt-link ">Active</a>
						<span class="rounded-number-label no-border fr align-text">${totalEmployeesActive}</span>
						<div class="clear"></div>
						<a href="${employeein }" class="alt-link ">Disabled</a>
						<span class="rounded-number-label no-border fr align-text">${totalEmployeesDisable}</span>
						<div class="clear"></div>
					</div>
					
					<div class="fieldset">	
						<div class="item">
							<p><h4 class="margin-budget second-linkcashier">Employee By Position</h4>
						</div>
						<div class="clear"></div>
						<span >Headmaster</span>
						<span class="rounded-number-label no-border fr align-text">${totalHeadmaster }</span>
						<div class="clear"></div>
						<span >Teacher</span>
						<span class="rounded-number-label no-border fr align-text">${totalTeacher }</span>
						<div class="clear"></div>
						<span >Cashier</span>
						<span class="rounded-number-label no-border fr align-text">${totalCashier }</span>
						<div class="clear"></div>
						<span >Admin</span>
						<span class="rounded-number-label no-border fr align-text">${totalAdmin}</span>
						<div class="clear"></div>
						<span >All Roles</span>
						<span class="rounded-number-label no-border fr align-text">${totalRole}</span>
						<div class="clear"></div>
					</div>
					<div class="fieldset">	
						<div class="item">
							<p><h4 class="margin-budget second-linkcashier">Age Group</h4>
						</div>
					</div>
					
<!-- 					<div id="visualization_pie2" class="charts pie"></div> -->
					<div id="chartContainer" style="height: 300px; width: 100%;"></div>
				</form>
							
			</div>
					
			</div>
			
		</div>
		<div class="clear"></div>
	</div>	
</div>

