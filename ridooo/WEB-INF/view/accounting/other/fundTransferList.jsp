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
	//						html: '<form id="oForm" method="POST" action="${issuePayment}'+tmp.attr('id')+'/cancel"><label id="note">Notes:</label><textarea id="note" name="note" value="note" /><br /><br /><button type="submit" class="btn btn-positive">Cancel Payment</button><button type="reset" class="btn">Cancel</button></div></form>',
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
</script>
<div class="content">
	
	<!-- GRID CONTAINER -->
	<div class="grid-container">
		
		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
				<div class="content-header">
					<div class="btn-group dropdown-large fr">
						<c:url var="createFundTransfer" value="/finance/fundtransfers/create" />
						<a class="btn" href="${createFundTransfer}">Create Fund Transfers</a>	
					</div>
					
					<h1>Fund Transfers</h1>
					<p class="subtitle">Showing all fund transfers</p>
				</div>
				
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
						<div class="empty-state book">
							<h2>No fund transfers available</h2>
						</div>
					</c:when>
					
					<c:otherwise>
						<table class="table table-striped table-list">
							<colgroup>
								<col/>
								<col class="w250"/>
								<col class="w250"/>
								<col class="date"/>
								<col class="w100"/>
								<col class="actions"/>
							</colgroup>
					
							<thead>
								<tr>
								
									<th>TRANSACTION</th>
									<th>FROM</th>
									<th>TO</th>
									<th>TRX DATE</th>
									<th class="align-right">AMOUNT (RP.)</th>
									<th></th>
								
								</tr>
							</thead>
							
							<tbody>
								<c:forEach items="${transactions}" var="transaction" varStatus="status">
									
									<tr id="${transaction.id}">
										<td>
											<h4>

												<a href="#">FUND TRANSFER <c:out value="${transaction.id}"/></a>
											</h4>
										</td>
										<td>a<c:out value="${fromAccountNames.get(status.index)}"/></td>
										<td><c:out value="${toAccountNames.get(status.index)}"/></td>
										<td><c:out value="${trxDates.get(status.index)}"/></td>
										<td>
											<span class="amount"><c:out value="${transaction.amount}"/></span>
										</td>
										
										<td>
											<div class="btn-group dropdown fr">
																<c:url var="editFundTransfer" value="/finance/fundtransfers/${transaction.id}/edit" />
																<a href="${editFundTransfer}" class="btn btn-mini">Edit</a>


											</div>
										</td>
									</tr>
									
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
				
				
				
				
				
				
			</div>
		</div>
		<!-- END : CONTENT AREA -->
		
	</div>
	<div class="clear"></div>
</div>
</div>
	
