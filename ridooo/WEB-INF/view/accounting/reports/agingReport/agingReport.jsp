<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="versehtmlcompressor" prefix="html"%>
<c:url var="financeAccounts" value="/finance/accounts" />
<script>

	function generateReport() {
		getAgingList($('#typeAging').val(),1);
	}
	function generateCSV() {
		if($('#report2').find(".table-striped").length){
			if($('#typeAging').val()==1)
				window.open(site_url+"finance/agingtable/exporttocsv?output=excel&data=agingapreport","_self");
			else
				window.open(site_url+"finance/agingtable/exporttocsv?output=excel&data=agingarreport","_self");
		}else
			alert("Please Generate Report First");			
			
			
	}
	$(document).ready(function(){	
   	
	   	$('#typeAging').change(function(){
			if($(this).val()==0)
				$("#agingPrint").html("aging report (a/r)");
			else
				$("#agingPrint").html("aging report (a/p)");
		});
   	});
   	
   	function printReport(){
   		if($("#report2").find(".table-striped").length)
   			getAllAgingList($('#typeAging').val());
   		else
   			alert("Please Gnerate Report First");
   	}
   	
</script>

<div class="content aging">
	<div class="grid-container">
		<div class="grid1 last extended">
			<div class="grid-content">
			</div>
		</div>
		<div class="grid-full">
			<div class="grid-content">
				<div class="print-layout">
					<div class="print-head">
						<div class="print-title">
							<img src="${imageCompany}" />
							<h1>${companyName}</h1>
							<p>${companyAddress}</p>
						</div>
						<div class="print-info">
							<h3 id="agingPrint">aging report (a/p)</h3>
							<p>${date}</p>
						</div>
						<div class="clear"></div>
					</div>	
				</div>
				
				<div class="content-header no-border">
						<div class="btn-group dropdown dropdown-large fr">
							<a href="#" onClick="printReport();" class="btn"><i class="icon-print"></i> Print</a>
							<button class="btn btn-large dropdown-toggle" data-toggle="dropdown">
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="javascript:generateCSV();">Export to CSV</a></li>
							</ul>
						</div>
					<h1>Aging Report</h1>
					<p class="subtitle">Showing outstanding invoices and bills amount</p>
					<div class="control-group">				
						<form onsubmit="return false">
								<div class="field fl">
										<div class="input-append">
											<form:select path="typeAging" id="typeAging" multiple="false" size="1"  class="input-mini aging-select">
												<form:options items="${typeAging}"/>
											</form:select>
										</div>
								</div>
								<div class="field fl">
										<button type="submit" onClick="generateReport();" class="btn btn-positive">Generate</button>
								</div>
						</form>	
					</div><br><div class="clear"></div>
					
					<div id="report2">
							<div class="clear"></div>
							<div class="empty-state book defaault-aging-report">
								<h2>Start with A/R or A/P</h2>
							</div>
							<div class="clear"></div>
					</div>
				</div>
				<div id="report" >
				</div>
				<div class="inner-grid3">
				</div>
			</div>
		</div>
		<div class="grid1 last"></div>
	</div>
	<div class="clear"></div>
</div>

