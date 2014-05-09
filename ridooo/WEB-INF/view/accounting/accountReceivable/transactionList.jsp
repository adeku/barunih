<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="receivePayment" value="/finance/payments-received/" />
<script>
	$(document).ready(function(){
		core.ui_with_parameter();
		<c:choose>
			<c:when test="${transactionType == 'Invoices'}">
				$('.delete_popup').click(function(){
					var tmp = $(this);
					$(this).popup({	
						title: 'Delete Form',
						html: '<form id="oForm" method="POST" action="/verse/finance/invoices/'+tmp.attr('id')+'/delete"><label id="note">Notes:</label><textarea id="note" name="note" value="note" /><br /><br /><button type="submit" class="btn btn-positive">Hapus</button><button type="reset" class="btn">Batal</button></div></form>',
					});
				});
			</c:when>
			<c:when test="${transactionType == 'Payments'}">
				$('.delete_popup').click(function(){
					var tmp = $(this);
					$(this).popup({
						title : 'Confirm to Cancel Payment',
						hideClose : true,
						html : '<form id="deleteModel" method="POST" action="${receivePayment}/'+tmp.attr('id')+'/cancel">'+
									'<p>Are you sure to cancel this payment?</p><div class="control-group">'+
									'<div class="controls">'+
										'<textarea class="input-full"  id="reason" name="reason" placeholder="Reasons" id="textarea" rows="3"></textarea>'+
									'</div></div>'+
									'<div class="form-actions">'+
										'<button type="submit" class="btn btn-negative">Batalkan pembayaran</button>'+
										'<button type="reset" class="btn" onClick="$.colorbox.close();">Batal</button>'+
									'</div></form>',
						height : '250px'
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
						
						if(data.message == 'success'){
							$.colorbox.close();	
							$('.popup-close').trigger('click');
							var id = data.id;
							$('tr#'+id).addClass('disabled');
							$('tr#'+id).attr('data-attr',"The payment transaction is canceled");
							$('tr#'+id).attr('data-title', "Transaction "+id);
							console.log(data.id);
							core.init();
						}
						else if(data.message == "cannot cancel"){
							$('.content-header').after('<div class="alert alert-error">'+
														'<button type="button" class="close" data-dismiss="alert">&times;</button>'+
														'<strong>Error!</strong> Your account cannot cancel payment received.'+
													'</div>');
							$.colorbox.close();
							sb.doScrollTo(top);
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
						<c:when test="${transactionType == 'Invoices'}">
							<div class="btn-group dropdown dropdown-large vrs-btndrop-special fr">
								<button class="btn dropdown-toggle" data-toggle="dropdown">Create Invoices<span class="caret"></span></button>
								<ul class="dropdown-menu">
									<c:url var="addInvoice" value="/finance/invoices/create" />
									<li><a href="${addInvoice }">Service</a></li>
									<c:url var="addInventoryInvoice" value="/finance/invoices/create/inventory" />
									<li><a href="${addInventoryInvoice}">Inventory</a></li>
								</ul>
							</div>
								<h1>Invoices</h1>
								<p class="subtitle">Showing all invoices issued for customers</p>
							
						</c:when>
						
						
						<c:when test="${transactionType == 'Payments'}">
							<span class="fr">
								<c:url var="receivePayment" value="/finance/payments-received/create" />
								<a class="btn" href="${receivePayment}">Receive Payments</a>	
							</span>
							<h1>Payments</h1>
							<p class="subtitle">Showing all payments made for all vendors</p>
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
						<strong>Success!</strong> ${object} has been ${action}
					</div>
				</c:if>
				<c:if test="${!empty message}">
					<div class="alert alert-error">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Error!</strong> ${message}
					</div>
				</c:if>
				<c:choose>
				<c:when test="${empty transactions}">
				
					<c:if test="${transactionType == 'Payments'}">
						<div class="empty-state book">
							<h2>No payments available</h2>
						</div>
					</c:if>
					
					<c:if test="${transactionType == 'Invoices'}">
						<div class="empty-state book">
							<h2>No invoices available</h2>
						</div>
					</c:if>
					</c:when>
					
					<c:otherwise>
						<table class="table table-striped table-list">
							<colgroup>
								<col/>
								<c:if test="${transactionType == 'Payments'}">
									<col class="w250"/>
								</c:if>
								<c:if test="${transactionType == 'Invoices'}">
									<col class="date"/>
									<col class="date"/>
								</c:if>
								<col class="w100"/>
								<col class="actions"/>
							</colgroup>
					
							<thead>
								<tr>
								
									<th>TRANSACTION</th>
									<c:if test="${transactionType == 'Payments'}">
										<th>Bill Number</th>
									</c:if>
									<c:if test="${transactionType == 'Invoices'}">
										<th>TRX DATE</th>
										<th>DUE DATE</th>
									</c:if>
									<th>AMOUNT (RP.)</th>
									<th></th>
								</tr>
							</thead>
							
							<tbody>
								<c:forEach items="${transactions}" var="transaction" varStatus="status">
									
									<tr id="${transaction.id}">
										<td>
											<h4>
												<c:choose>	
													<c:when test="${transactionType == 'Invoices'}">
														<c:url var="viewInvoice" value="/finance/invoices/${transaction.id}/view" />
														<c:choose>
															<c:when test="${transaction.type == 'Invoice'}">
																<a href="${viewInvoice}">INVOICE <c:out value="${transaction.id}"/></a>
															</c:when>
															<c:otherwise>
																<a href="${viewInvoice}">CASH SALES <c:out value="${transaction.id}"/></a>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:when test="${transactionType == 'Payments'}">
														<c:url var="viewPayment" value="/finance/payments-received/${transaction.id}/view" />
														<a href="${viewPayment}">PAYMENT <c:out value="${transaction.id}"/></a>
													</c:when>
												</c:choose>
											</h4>
											<span>${customerNames[status.index]}</span>
										</td>
										
										<c:if test="${transactionType == 'Payments'}">
											<td>
												<span>
												<c:forEach items="${invoiceIds[status.index]}" var="invoiceId" varStatus="invoiceStatus">
													${invoiceId}<c:if test="${!empty invoiceId && invoiceStatus.index != invoiceIds[status.index].size() - 1}">,</c:if>
												</c:forEach>
												
												</span>
											</td>
										</c:if>
										
										<c:if test="${transactionType == 'Invoices'}">
											<td><c:out value="${trxDates.get(transaction.id)}"/></td>
											<td><c:out value="${dueDates.get(transaction.id)}"/></td>
										</c:if>
										
										
										<td>
											<span class="amount"><c:out value="${transaction.amount}"/></span>
										</td>
										
										
										<c:if test="${transactionType == 'Invoices'}">
											<c:url var="makePayment" value="/finance/bills/${transaction.id}/pay" />
										</c:if>
										
										
										<td>
											<div class="btn-group dropdown fr">
												<c:choose>
													<c:when test="${transactionType == 'Invoices'}">
														<c:choose>
															<c:when test="${canUpdate == true}">
																<c:choose>
																	<c:when test="${transactionStatus[status.index] != 2}">
																		<c:url var="editInvoice" value="/finance/invoices/${transaction.id}/edit" />
																		<a href="${editInvoice}" class="btn btn-mini">Edit</a>
																	</c:when>
																	<c:otherwise>
																		<a href="#" class="btn btn-mini disabled">Edit</a>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<a href="#" class="btn btn-mini disabled">Edit</a>
															</c:otherwise>
														</c:choose>
													</c:when>
																
													<c:when test="${transactionType == 'Payments'}">
														<c:choose>
															<c:when test="${canDelete == true}">
																<a href="#" id="${transaction.id}" class="btn btn-mini delete_popup">Cancel</a>
															</c:when>
															<c:otherwise>
																<a href="#" class="btn btn-mini disabled">Cancel</a>
															</c:otherwise>
														</c:choose>
													</c:when>
												</c:choose>
											</div>
										</td>
										
										
									</tr>
									
								</c:forEach>
							</tbody>
						</table>
						${pagination}
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>
		<!-- END : CONTENT AREA -->
		
	</div>
	<div class="clear"></div>
</div>
</div>
	
