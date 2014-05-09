<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">

		<div class="grid1 last extended">
			<div class="grid-content">
			
			
			<div class="content-header">
				<H4>Details Chart of Accounts</H4>
				<p class="subtitle">
					<span id="schoolAddress"></span> <span id="schoolCity"></span>
				</p>
				
			</div>
			
			</div>
		</div>

		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">

				<div class="content-header">
					<h1>General Ledger</h1>
					<p class="subtitle">Showing all accounts used in the company, including the sub companies</p>
				</div>
				
				${viewTimeLoad}

				<c:if test="${!empty action}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Well done!</strong> You successfully ${action} ${object}
					</div>
				</c:if>
				
				<c:choose>
					<c:when test="${empty accounts}">
						<div class="empty-state person">
							<h2>No chart of accounts available</h2>
						</div>
					</c:when>
					<c:otherwise>
							<table class="table table-striped">
								<colgroup>
									<col class="w130"/>
									<col class="w200"/>
									<col />
									<col class="status"/>
									<col class="w130" />
								</colgroup>
								<thead>
									<tr>
										<th>CODE</th>
										<th>TYPE</th>
										<th>ACCOUNT</th>
										<th></th>
										<th class="align-right">Balance</th>
									</tr>
								</thead>
		
								<tbody>
									<c:forEach items="${accounts}" var="account" varStatus="status">
										<template:ledgerRecur symbol="" account="${account}" balances="${balances}" />
									</c:forEach>
								</tbody>
							</table>
							${pagination}
					</c:otherwise>	
				</c:choose>
			</div>
		</div>
		<div class="grid1 last">&nbsp;</div>
		<!-- END : CONTENT AREA -->

	</div>
	<div class="clear"></div>
</div>


