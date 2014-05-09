<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- CONTENT -->
		<div class="content">
			
			<!-- GRID CONTAINER -->
			<div class="grid-container">
				
				<!-- START : CONTENT AREA -->
				<div class="grid-full">
					<div class="grid-content vrs-invenitem vrs-inventrorv">
							<div class="content-header">
    							<div class="helper">
    								<a href="" class="btn print fr"><i class="icon-print"></i> Print</a>
    							</div>
    						
    							<h1>Transfer Order ${ref_number}</h1>
								<p class="subtitle">${transferOrderDate}</p>
    						</div>
							
							<div class="list list-horizontal span6 vrs-no-ml">
    							<ul>
    								<li>
    									<p class="title">Origin</p>
										<strong>${originName}</strong>
    									<p class="title">${originAddress}</p>
    								</li>
    								<li>
    									<p class="title">Destination</p>
										<strong>${destinationName}</strong>
    									<p class="title">${destinationAddress}</p>
    								</li>
    							</ul>
    						</div>
    						<div class="list list-horizontal span3 vrs-invenitemv-fr">
    							<ul>
    								<li>
    									<p class="title">Ship Date</p>
    									<p class="title">${shipDate}</p>
										<p class="title">&nbsp; </p>
    								</li>
    							</ul>
    						</div>
							
						
						<table class="table table-striped">
							
							<colgroup>
								<col>
								<col class="w100">
							</colgroup>
							
							<thead>
								<tr>
									<th>Item</th>
									<th class="align-right">QTY</th>
								</tr>
							</thead>
							
							<tbody>
							<c:forEach items="${itemList}" var="itemList1">
								<tr class="vrs-invenitem-tradd">
        							<td>
										<h4>${itemList1.title}</h4>
										<span>${itemList1.brandName} &mdash; ${itemList1.sku}</span>
									</td>
        							<td class="align-right"><span class="score">${itemList1.quantity}</span></td>
        						</tr>
        						</c:forEach>
        						
							</tbody>
						</table>
						
					</div>
					<!-- END : CONTENT AREA -->
				</div>
			</div>
			<div class="clear"></div>
			
		</div>