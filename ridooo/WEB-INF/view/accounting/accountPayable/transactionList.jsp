<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="bill" value="/finance/bills/" />
<c:url var="issuePayment" value="/finance/payments-issued/" />
<c:url var="apiIssuePayment" value="/api/companies/1/finance/payments-issued/" />

<script>
	$(document).ready(function(){
		core.ui_with_parameter();
		
		<c:choose>
			<c:when test="${transactionType == 'Bills'}">
				$('.delete_popup').click(function(){
					var tmp = $(this);
					$(this).popup({
						title:'Delete Form',
						html: '<form id="oForm" method="POST" action="${bill}'+tmp.attr('id')+'/delete"><label id="note">Notes:</label><textarea id="note" name="note" value="note" /><br /><br /><button type="submit" class="btn btn-positive">Hapus</button><button type="reset" class="btn">Batal</button></div></form>',
					});
				});
			</c:when>
			<c:when test="${transactionType == 'Payments'}">
				$('.delete_popup').click(function(){
					var tmp = $(this);
					$(this).popup({
						
						title : 'Confirm to Cancel Payment',
						hideClose : true,
						html : '<form id="deleteModel" method="POST" action="${issuePayment}'+tmp.attr('id')+'/cancel">'+
								'<input type="hidden" name="roleId" value="1" />'+
									'<p>Are you sure to cancel this payment?</p><div class="control-group">'+
									'<div class="controls">'+
										'<textarea class="input-full"  id="reason" name="reason" placeholder="Reasons" id="textarea" rows="3"></textarea>'+
									'</div></div>'+
									'<div class="form-actions">'+
										'<button type="submit" class="btn btn-negative">Batalkan pembayaran</button>'+
										'<button type="reset" class="btn" onClick="$.colorbox.close();">Batal</button>'+
									'</div></form>',
						height : '250px'
// 						html: '<form id="oForm" method="POST" action="${issuePayment}'+tmp.attr('id')+'/cancel"><label id="note">Notes:</label><textarea id="note" name="note" value="note" /><br /><br /><button type="submit" class="btn btn-positive">Cancel Payment</button><button type="reset" class="btn">Cancel</button></div></form>',
					});
				});
				
				$('body').on('submit','#deleteModel',function(e){
					e.preventDefault();
					$.ajax({
						type:'POST',
						url: $(this).attr('action'),
						data : $(this).serialize()
					}).done(function(resPonseText){
						console.log(resPonseText);
						var data = jQuery.parseJSON(resPonseText);
						if(data.message == "cannot cancel"){
							$('.content-header').after('<div class="alert alert-error">'+
															'<button type="button" class="close" data-dismiss="alert">&times;</button>'+
															'<strong>Error!</strong> Your account cannot cancel payment issued.'+
														'</div>');
							$.colorbox.close();
							sb.doScrollTo(top);
						}
						else if(data.message == 'success'){
							$.colorbox.close();	
							$('.popup-close').trigger('click');
							var id = data.id;
							$('tr#'+id).addClass('disabled');
							$('tr#'+id).attr('data-attr',"The payment transaction is canceled");
							$('tr#'+id).attr('data-title', "Transaction "+id);
							console.log(data.id);
							core.init();
						}
					});
				});
			</c:when>
		</c:choose>
	});
	$(document).ready(function() {
		if(document.form.optionDate.value!=5)
			$('#date-range').hide(0);
	});
	
	function filterByDate() {
		if (document.form.dateFrom.value.length > 0
				&& document.form.dateTo.value.length>0) {
			document.form.submit();
		}
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
<div class="content">
	
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
							
				<div class="content-header">
					<c:choose>
						<c:when test="${transactionType == 'Bills'}">
						<span class="fr">
							<c:choose>
								<c:when test="${canCreate == true}">
									<c:url var="addBill" value="/finance/bills/create" />
									<a class="btn" href="${addBill}">Create Bills</a>
								</c:when>
								<c:otherwise>
									<a class="btn disabled" href="#">Create Bills</a>
								</c:otherwise>
							</c:choose>
						</span>
							<h1>Bills</h1>
							<p class="subtitle">Showing all bills from all vendors</p>
						</c:when>
						
						<c:when test="${transactionType == 'Payments'}">
						<span class="fr">
							<c:choose>
								<c:when test="${canCreate == true}">
									<c:url var="makePayment" value="/finance/payments-issued/create" />
									<a class="btn" href="${makePayment}">Make Payment</a>
								</c:when>
								<c:otherwise>
									<a class="btn disabled" href="#">Make Payment</a>
								</c:otherwise>
							</c:choose>
								
						</span>
							<h1>Payments</h1>
							<p class="subtitle">Showing all payments received from all customers</p>
						</c:when>
					</c:choose>
				</div>
				<form id="form" name="form" method="POST">
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
					<div id="date-range">
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
				</form>			
				<c:if test="${!empty action}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Well done!</strong> You have successfully ${action} ${object}
					</div>
				</c:if>
				
				<c:if test="${!empty message}">
					<div class="alert alert-error">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Error!</strong> ${message}
					</div>
				</c:if>
<%-- 				<h3>${message}</h3> --%>
				
				<c:choose>
					<c:when test="${empty transactions}">
						<c:if test="${transactionType == 'Bills'}">
						<div class="empty-state person">
							<h2>No bills available</h2>
						</div>	
						</c:if>
						
						
						<c:if test="${transactionType == 'Payments'}">
						<div class="empty-state person">
							<h2>No payments available</h2>
						</div>
						</c:if>
					</c:when>
					<c:otherwise>
						<table class="table table-striped table-list">
					<colgroup>
						<col/>
						<col class="date"/>
						<c:if test="${transactionType == 'Bills'}">
							<col class="date"/>
							<col class="date"/>
						</c:if>
						
						<col class="actions"/>
					</colgroup>
					
						<thead>
							<tr>
							
								<th>TRANSACTION</th>
								<c:if test="${transactionType == 'Bills'}">
									<th>TRX DATE</th>
									<th>DUE DATE</th>
								</c:if>
								<th>AMOUNT(Rp.)</th>
								
								<th></th>
							</tr>
						</thead>
						
						<tbody>
						<c:forEach items="${transactions}" var="transaction" varStatus="status">
<%-- 							<c:if test="${transactionStatus[status.index] != 0}"> --%>
								<tr id="${transaction.id}">
									<td>
									<h4>
										<c:choose>	
											<c:when test="${transactionType == 'Bills'}">
												<c:url var="viewBill" value="/finance/bills/${transaction.id}/view" />
												<a href="${viewBill}">BILL <c:out value="${transaction.id}"/></a>
											</c:when>
											<c:when test="${transactionType == 'Payments'}">
												<c:url var="viewPayment" value="/finance/payments-issued/${transaction.id}/view" />
												<a href="${viewPayment}">PAYMENT <c:out value="${transaction.id}"/></a>
											</c:when>
										</c:choose>
									</h4>
									<span>${vendorNames[status.index]}</span>
									</td>
									<!--  <td><a class="alt-link" href="#"><c:out value="${transaction.refNumber}"/></a></td> -->
									
									<c:if test="${transactionType == 'Bills'}">
										<td><c:out value="${trxDates.get(transaction.id)}"/></td>
										
										<td><c:out value="${dueDates.get(transaction.id)}"/></td>
<%-- 										<td><c:out value="${transaction.trxDate}"/></td> --%>
									</c:if>
									
									<td>
										<span class="amount"><c:out value="${transaction.amount}"/></span>
									</td>
									<c:if test="${transactionType == 'Bills'}">
										<c:url var="makePayment" value="/finance/payable-bills/${transaction.id}/pay" />
									</c:if>
									
									
									<td>
										<div class="btn-group dropdown fr">
											<c:choose>
												<c:when test="${transactionType == 'Bills'}">
													<c:choose>
														<c:when test="${canUpdate == true}">
															<c:choose>
																<c:when test="${transactionStatus[status.index] != 2}">
																	<c:url var="editBill" value="/finance/bills/${transaction.id}/edit" />
																	<a href="${editBill}" class="btn btn-mini">Edit</a>
																	<%-- <button class="btn dropdown-toggle" data-toggle="dropdown">
																		<span class="caret"></span>
																	</button>
																	<ul class="dropdown-menu pull-right">
																		<!-- dropdown menu links -->
																		<li><a tabindex="-1" href ="${editBill}">Edit</a></li>
																		<li><a href="#" class="delete_popup" id="${transaction.id}" tabindex="-1">Cancel</a></li>
																	</ul> --%>
																</c:when>
																<c:otherwise>
																	<a href="#" class="btn btn-mini disabled">Edit</a>
																</c:otherwise>
															</c:choose>
														</c:when>
														<%-- <c:when test="${canUpdate == true && canDelete == false}">
															<c:choose>
																<c:when test="${transactionStatus[status.index] != 2}">
																	<c:url var="editBill" value="/finance/bills/${transaction.id}/edit" />
																	<a href="${editBill}" class="btn btn-mini">Edit</a>
																</c:when>
																<c:otherwise>
																	<a href="#" class="btn btn-mini disabled">Edit</a>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${canUpdate == false && canDelete == true}">
															<a href="#" class="btn btn-mini delete_popup" id="${transaction.id}">Cancel</a>
														</c:when> --%>
														<c:otherwise>
															<a href="#" class="btn btn-mini disabled">Edit</a>
														</c:otherwise>
													</c:choose>
												</c:when>
															
												<c:when test="${transactionType == 'Payments'}">
													<c:choose>
														<c:when test="${canDelete == true}">
<%-- 														<c:url var="editPayment" value="/finance/payments-issued/${transaction.id}/edit" /> --%>
															<a href="#" id="${transaction.id}" class="btn btn-mini delete_popup">Cancel</a>
															<%-- <button class="btn dropdown-toggle" data-toggle="dropdown">
																<span class="caret"></span>
															</button>
															<ul class="dropdown-menu pull-right">
																<!-- dropdown menu links -->
																<li><a href="#" class="delete_popup" id="${transaction.id}" tabindex="-1">Delete</a></li>
															</ul> --%>
														</c:when>
														<c:otherwise>
															<a href="#" class="btn btn-mini disabled">Cancel</a>
														</c:otherwise>
													</c:choose>
												
												</c:when>
											</c:choose>
										</div>
										<!-- <div class="btn-group dropdown fr">
											<button class="btn btn-mini">Edit</button>
											<button class="btn dropdown-toggle" data-toggle="dropdown">
												<span class="caret"></span>
											</button>
											<ul class="dropdown-menu pull right">
												
											
											</ul>
										</div> -->
									</td>
									
									
									<!-- <c:choose>
										<c:when test="${transaction.status == 1}">
											<td><a class="btn" href="${makePayment}">Pay</a></td>
										</c:when>
										<c:otherwise>
											<td>Paid</td>
										</c:otherwise>
									</c:choose>-->
								</tr>
								
<%-- 							</c:if> --%>
						</c:forEach>
						</tbody>
					</table>
					</c:otherwise>
				</c:choose>
				
				<!-- <c:if test="${!empty transactions}">
					
					
				</c:if> -->
				
				
			</div>
		</div>
		<!-- END : CONTENT AREA -->
		
	</div>
	<div class="clear"></div>
</div>
<!-- </div> -->
	
