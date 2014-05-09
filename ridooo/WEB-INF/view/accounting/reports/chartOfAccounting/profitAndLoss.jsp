<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="rupiahFunctions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="financeAccounts" value="/finance/accounts" />
<%-- <html:html enabled="true" compressJavaScript="true" compressCss="true"> --%>
<script>
	$(document).ready(function() {
		if(document.form.optionDate.value!=5)
			$('#date-range').hide(0);
		
		
		$(".control-label").live("click", function(){
		  	setTimeout(updateHeightOfContent( $(this).attr("data-target") ), 500);
		});
	
	});
	
	function updateHeightOfContent(target) {
		var collapse = $(target).css("height");
		if( collapse == "0px"){
			sb.resize();
		  	setTimeout(updateHeightOfContent( $(this).attr("data-target") ), 100); 
		  	console.log("refreshhhhhhh");
		}
			  	
		
	}
	
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
<div class="profit content">
	<div class="grid-container">
		<div class="grid-full">
			<div class="grid-content" >
				<div class="print-layout">
					<div class="print-head">
						<div class="print-title">
							<img src="img/ciputralogo.png">
							<h1>SD. Citra Berkat</h1>
							<p>Jl. Raya Bukit Palma 2 Citraland, Surabaya</p>
						</div>
						<div class="print-info">
							<h3>profit and loss</h3>
							<p>28/08/2012 - 30/08/2012</p>
						</div>
						<div class="clear"></div>
					</div>	
				</div>
				
				<form id="form" name="form" method="POST">
				<div class="content-header no-border">
					<div class="helper">
						<a class="btn print fr" href="#">
							<i class="icon-print"></i>
							Print
						</a>
					</div>
					<h1>Profit And Loss</h1>
					<div class="control-group">				
					<div class="field fl">
							<div class="input-append">
								<select name="optionDate" class="input-very-small datepicker" readonly="" onChange="showCustomDate();">
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
					</div><br><br>
		
				</div>
				
				<div class="grid-content" >
					<div class="clear"></div>
					<div class="legend">GROSS PROFIT</div>
					<div class="control-group">
						<label class="control-label" data-toggle="collapse" data-target=".income"><i class="arrows black rotate"></i> Income</label>			
						<div class="controls">
							<p class="data">${Income }</p>
						</div>
						<ul class="income" >						
							<c:forEach items="${IncomeList}" var="account">
								<c:choose>
								    <c:when test="${!empty account.name}">
										<li>
											<label class="ins-label">${account.name}</label>
											<div class="ins-controls"><p class="data">${f:rupiahCurrency(account.lastBalance)}</p></div>
										</li>
								    </c:when>
								</c:choose>
							</c:forEach>					
						</ul>
					</div>	
					<div class="clear"></div>
			 		<div class="control-group">
						<label class="control-label" data-toggle="collapse" data-target=".goods_sold"><i class="arrows black rotate"></i> Cost of Goods Sold</label>			
						<div class="controls">
							<p class="data">${CostOfGoods }</p>
						</div>
						<ul class="goods_sold" >						
							<c:forEach items="${GoodsList}" var="account">
								<c:choose>
								    <c:when test="${!empty account.name}">
										<li>
											<label class="ins-label">${account.name}</label>
											<div class="ins-controls"><p class="data">${f:rupiahCurrency(account.lastBalance)}</p></div>
										</li>
								    </c:when>
								</c:choose>
							</c:forEach>					
						</ul>
					</div>	 
					<div class="clear"></div>
					<div class="control-group bold">
							<label class="control-label">Gross Profit</label>
							<div class="controls">
								<c:choose>
									<c:when test='${GrossProfit.matches(".*-.*")}'>
										<p class="data fr" style="color:red">${GrossProfit }</p>
									</c:when>
									<c:otherwise>
										<p class="data fr">${GrossProfit }</p>
									</c:otherwise>
								</c:choose>	
							</div>
					</div>
					<div class="clear"><br></div>
					
					<div class="legend">OPERATING EXPENSES</div>
					<div class="control-group">
						<label class="control-label" data-toggle="collapse" data-target=".operating"><i class="arrows black rotate"></i> Total Operating Expenses</label>			
						<div class="controls">
							<p class="data">${Expenses }</p>
						</div>
						<ul class="operating" >						
							<c:forEach items="${ExpensesList}" var="account">
								<c:choose>
								    <c:when test="${!empty account.name}">
										<li>
											<label class="ins-label">${account.name}</label>
											<div class="ins-controls"><p class="data">${f:rupiahCurrency(account.lastBalance)}</p></div>
										</li>
								    </c:when>
								</c:choose>
							</c:forEach>					
						</ul>
					</div>	 
					<div class="clear"></div>
					<div class="control-group bold">
							<label class="control-label">Operating Income</label>
							<div class="controls">
								<c:choose>
									<c:when test='${OperatingIncome.matches(".*-.*")}'>
										<p class="data fr" style="color:red">${OperatingIncome }</p>
									</c:when>
									<c:otherwise>
										<p class="data fr">${OperatingIncome }</p>
									</c:otherwise>
								</c:choose>	
							</div>
					</div>
					<div class="clear"><br></div>
					
					<div class="legend">NON-OPERATING OR OTHER</div>
					<div class="control-group">
						<label class="control-label" data-toggle="collapse" data-target=".other1"><i class="arrows black rotate"></i> Other Income</label>
						
									<div class="controls">
									<p class="data">${OtherIncome}</p>
									</div>
						<ul class="other1" >
							<c:forEach items="${OtherIncomeList}" var="account">
								<c:choose>
								    <c:when test="${!empty account.name}">
								    
										<li><label class="ins-label">${account.name}</label><div class="ins-controls"><p class="data">${f:rupiahCurrency(account.lastBalance)}</p></div></li>
								    </c:when>
								</c:choose>
							</c:forEach>
							
						</ul>
					</div>
					<div class="clear"></div>
					
					
					<div class="control-group">
						<label class="control-label" data-toggle="collapse" data-target=".other2"><i class="arrows black rotate"></i> Other Expenses</label>
						
							<div class="controls">
								<p class="data">${Otherexpense}</p>
							</div>
						<ul class="other2" >
							<c:forEach items="${OtherexpenseList}" var="account">
								<c:choose>
								    <c:when test="${!empty account.name}">
								    
										<li><label class="ins-label">${account.name}</label><div class="ins-controls"><p class="data">${f:rupiahCurrency(account.lastBalance)}</p></div></li>
								    </c:when>
								</c:choose>
							</c:forEach>
							
						</ul>
					</div>
					<div class="clear"></div>
					
					<div class="control-group bold">
						<label class="control-label">Total non-operating</label>
						<div class="controls">
							<c:choose>
								<c:when test='${NonOperating.matches(".*-.*")}'>
									<p class="data" style="color:red">${NonOperating }</p>
								</c:when>
								<c:otherwise>
									<p class="data">${NonOperating }</p>
								</c:otherwise>
							</c:choose>	
						</div>
					</div>
					<div class="clear"></div>
					
					<div class="grandtotal">
						<label class="control-label">NET-INCOME</label>
						<div class="controls">
						<c:choose>
								<c:when test='${NetIncome.matches(".*-.*")}'>
									<p class="data " style="color:red">${NetIncome }</p>
								</c:when>
								<c:otherwise>
									<p class="data ">${NetIncome }</p>
								</c:otherwise>
						</c:choose>		
					</div></div>
					
				</div>
					
			 </form>	
			</div>
		</div>
		<div class="grid1 last"></div>
	</div>
	<div class="clear"></div>
</div>
<%-- </html:html> --%>
