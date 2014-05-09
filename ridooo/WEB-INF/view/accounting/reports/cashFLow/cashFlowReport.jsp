<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/htmlCompresorTag.tld" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script>
	$(document).ready(function() {
		if(document.form.optionDate.value!=5)
			$('#date-range').hide(0);
	});
	
	function filterByDate() {
			document.form.submit();
	}
	
	function showCustomDate() {
		if (document.form.optionDate.value==5){
			$('#date-range').fadeIn(400);
		}else{
			$('#date-range').fadeOut(400);
			filterByDate() ;
		}
	}
</script>
<div class="cash content">
	<div class="grid-container">
		<div class="grid-full">
			<div class="grid-content">
			<!-- print start -->
				<div class="print-layout">
					<div class="print-head">
						<div class="print-title">
							<img src="../../img/ciputralogo.png">
							<h1>SD. Citra Berkat</h1>
							<p>Jl. Raya Bukit Palma 2 Citraland, Surabaya</p>
						</div>
						<div class="print-info">
							<h3>statement of cash flows</h3>
							<p>March 2013</p>
						</div>
						<div class="clear"></div>
					</div>	
				</div>
			<!-- print end -->
				<form id="form" name="form" method="POST">
					<div class="content-header no-border">
						<div class="helper">
							<a class="btn print fr" href="#">
								<i class="icon-print"></i>
								Print
							</a>
						</div>
						<h1>CASH FLOWS</h1>
						<div class="field fl">
							<div class="input-append">
								<select name="optionDate" class="profit-select" readonly="" onChange="showCustomDate();">
									<c:forEach items="${pdOption}" var="date">
										<c:choose>
										    <c:when test="${date[0].equalsIgnoreCase(dateOption)}">
												<option value="${date[0]}" selected>${date[1]}</option>
										    </c:when>
										    <c:otherwise>
												<option value="${date[0]}">${date[1]}</option>
										    </c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</div>
						<div id="date-range" style="margin-top:0px">
							<div class="field fl">
								<div class="input-append">
									<input type="text" name="dateFrom" class="input-very-small datepicker"
									readonly="" value="${dateFrom}" onChange="filterByDate();">
									<span class="add-on"><i class="icon-calendar"></i></span>
								</div>
							</div>
							<div class="field fl">
								<div class="input-append">
									<input type="text" name="dateTo" class="input-very-small datepicker"
									readonly="" value="${dateTo}" onChange="filterByDate();">
									<span class="add-on"><i class="icon-calendar"></i></span>
								</div>
							</div>
						</div>
					</div>
					<br>
					<div class="clear"></div>
					<div class="report">
						<div class="legend">OPERATING ACTIVITIES</div>
						<div class="control-group">
							<label class="control-label pad">Net Income</label>
				
							<div class="controls">
							<p class="data">${NetIncome}</p>
							</div>
							
						</div>
						<div class="clear"></div>
						<div class="control-group">
							<label class="control-label pad">Account Recevable</label>
							<div class="controls">
							<p class="data">${acoountReceivable}</p>
							</div>
						</div>
						<div class="clear"></div>
						<div class="control-group">
							<label class="control-label pad">Account Payable</label>
							<div class="controls">
							<p class="data">${accountPayable}</p>
							</div>
						</div>
						<div class="clear"></div>
						<div class="control-group">
							<label class="control-label pad">Other Current Asset</label>							
							<div class="controls">
							<p class="data">${otherCurrentAsset}</p>
							</div>							
						</div>
						<div class="clear"></div>
						<div class="control-group">
							<label class="control-label pad">Other Current Liability</label>							
							<div class="controls">
							<p class="data">${otherCurrentLiability}</p>
							</div>							
						</div>
						<div class="clear"></div>
						<div class="control-group bold no-m">
							<label class="control-label">Net Cashed Used in Operating Activities</label>
							<div class="controls">
							<p class="data">${operatingActivities}</p>
							</div>
						</div>
						<div class="clear"><br></div>
						<div class="legend">INVESTING ACTIVITIES</div>
						<div class="control-group">
							<label class="control-label pad">Other Assets</label>
							<div class="controls">
							<p class="data">${otherAsset}</p>
							</div>
						</div>
						<div class="clear"></div>
						<div class="control-group">
							<label class="control-label pad">Fixed Assets</label>
							<div class="controls">
							<p class="data">${fixedAsset}</p>
							</div>
						</div>
						<div class="clear"></div>
						<div class="control-group">
							<label class="control-label pad">Credit Card</label>
							<div class="controls">
							<p class="data">${creditCard}</p>
							</div>
						</div>
						<div class="clear"></div>
						<div class="control-group bold no-m">
							<label class="control-label">Net Cashed Used in Investing Activities</label>
							<div class="controls">
							<p class="data">${investingActivities}</p>
							</div>
						</div>
						<div class="clear"><br></div>
						<div class="legend">FINANCING ACTIVITIES</div>
						<div class="control-group"> 
							<label class="control-label pad">Bank</label>
							<div class="controls">
							<p class="data">${bank}</p>
							</div>
						</div>
						<div class="clear"></div>
						<div class="control-group"> 
							<label class="control-label pad">Long Term Liability</label>
							<div class="controls">
							<p class="data">${longTermLiability}</p>
							</div>
						</div>
						<div class="clear"></div>
						<div class="control-group"> 
							<label class="control-label pad">Equity</label>
							<div class="controls">
							<p class="data">${equity}</p>
							</div>
						</div>
						<div class="clear"></div>
						<div class="control-group bold no-m">
							<label class="control-label">Net Cashed Used in Financing Activities</label>
							<div class="controls">
							<p class="data">${financingActivities}</p>
							</div>
						</div>
						<div class="clear"><br></div>
						<div class="grandtotal no-b">
						<label class="control-label">NET INCREASE IN CASH</label>
							<div class="controls no-b">
							<p class="data">Rp ${netInCash }</p>
							</div>
						</div>
						<span class="clear"></span>
						<div class="grandtotal no-b">
						<label class="control-label">CASH AT BEGINNING OF THE MONTH</label>
							<div class="controls no-b">
							<p class="data">Rp ${CashAtBeginning }</p>
							</div>
						</div>
						<div class="grandtotal">
						<label class="control-label">CASH AT END OF THE MONTH</label>
							<div class="controls">
							<p class="data">Rp ${CashAtEnd }</p>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="grid1 last"></div>
	</div>
	<div class="clear"></div>
</div>