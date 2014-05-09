<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/customFormTag.tld" prefix="cform"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div class="content">

	<!-- GRID CONTAINER -->
	<div class="grid-container">

		<!-- START : CONTENT AREA -->
		<div class="grid-full">
			<div class="grid-content">
				<div class="content-header">
					<h1>Finance Settings</h1>
					<p class="subtitle">Settings for all finance transactions</p>
				</div>
				<c:if test="${!empty action}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Success!</strong> ${object} ${action}
					</div>
				</c:if>
				<c:if test="${!empty message}">
					<div class="alert alert-error">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						<strong>Error!</strong> ${message}
					</div>
				</c:if>
				
				<c:url var="fundTransfer" value="/finance/settings/apply" />
				<form:form modelAttribute="oForm" method="POST"
					action="${fundTransfer}" class="form-horizontal validate">
				
	<!-- 				<div class="fieldset"> -->
						<h3>Default Accounts</h3>
<!-- 						<div class="fieldset"> -->
<!-- 							<div class="legend">Default Accounts</div> -->
							<p class="subtitle">Determine default accounts for specific transactions</p>
<%-- 							<c:choose> --%>
<%-- 								<c:when test="${cashOption.size() == 1}"> --%>
									<cform:select label="Cash" path="cashAccount" size="medlarge" options="${cashOption}" selected="${oForm.cashAccount}" help="Type: Other Current Assets, Other Asset" disabled="true if only one" />
<%-- 								</c:when> --%>
<%-- 								<c:otherwise> --%>
<%-- 									<cform:select label="Cash" path="cashAccount" size="medium" options="${cashOption}" selected="${oForm.cashAccount}" /> --%>
<%-- 								</c:otherwise> --%>
<%-- 							</c:choose> --%>
							
							<cform:select label="Business Liabilities" path="businessLiabilitiesAccount" size="medlarge" options="${liabilitiesOption}" selected="${oForm.businessLiabilitiesAccount}" help="Type: Account Payable" disabled="true if only one" />
							<cform:select label="Cost Liabilities" path="costLiabilitiesAccount" size="medlarge" options="${liabilitiesOption}" selected="${oForm.costLiabilitiesAccount}" help="Type: Account Payable" disabled="true if only one" />
							<cform:select label="Credit" path="creditAccount" size="medlarge" options="${creditOption}" selected="${oForm.creditAccount}" help="Type: Account Receivable" disabled="true if only one" />
							<cform:select label="Service Expense" path="serviceExpenseAccount" size="medlarge" options="${expenseOption}" selected="${oForm.serviceExpenseAccount}" help="Type: Expense, Other Expense" disabled="true if only one" />
							<cform:select label="Operating Expense" path="operatingExpenseAccount" size="medlarge" options="${expenseOption}" selected="${oForm.operatingExpenseAccount}" help="Type: Expense, Other Expense" disabled="true if only one" />
							<cform:select label="SSP" path="SSPAccount" size="medlarge" options="${incomeOption}" selected="${oForm.SSPAccount}" help="Type: Income" disabled="true if only one" />
							<cform:select label="SPP" path="SPPAccount" size="medlarge" options="${incomeOption}" selected="${oForm.SPPAccount}" help="Type: Income" disabled="true if only one" />
							<cform:select label="Finance Charges" path="financeChargesAccount" size="medlarge" options="${chargeOption}" selected="${oForm.SSPFinanceChargesAccount}" help="Type: Other Income" disabled="true if only one" />
							<%-- <cform:select label="SSP Finance Charges" path="SSPFinanceChargesAccount" size="medium" options="${chargeOption}" selected="${oForm.SSPFinanceChargesAccount}"/>
							<cform:select label="SPP Finance Charges" path="SPPFinanceChargesAccount" size="medium" options="${chargeOption}" selected="${oForm.SPPFinanceChargesAccount}"/> --%>
							<cform:select label="Books And Supplies" path="booksSuppliesAccount" size="medlarge" options="${goodsOption}" selected="${oForm.booksSuppliesAccount}" help="Type: Cost of Goods Sold" disabled="true if only one" />
							<cform:select label="School Uniforms" path="schoolUniformsAccount" size="medlarge" options="${goodsOption}" selected="${oForm.schoolUniformsAccount}" help="Type: Cost of Goods Sold" disabled="true if only one" />
							<cform:select label="Registration Form Sales" path="registrationFormSalesAccount" size="medlarge" options="${incomeOption}" selected="${oForm.registrationFormSalesAccount}" help="Type: Income" disabled="true if only one" />
							<cform:select label="Re-registration" path="reRegistrationAccount" size="medlarge" options="${incomeOption}" selected="${oForm.reRegistrationAccount}" help="Type: Income" disabled="true if only one" />
<!-- 						</div> -->
							
						<h3 class="margin-top-40">Finance Charges</h3>
						<p class="subtitle">Set values for finance charges and late fees</p>
							<cform:input label="SSP Finance Charge" help="Daily" path="SSPFinanceCharge" size="mini" value="${oForm.SSPFinanceCharge}" prepend="IDR" />
							<cform:input label="SPP Finance Charge" help="Daily" path="SPPFinanceCharge" size="mini" value="${oForm.SPPFinanceCharge}" prepend="IDR" />
							<cform:input label="SSP Due Date" path="SSPDueDate" size="micro" value="${oForm.SSPDueDate}" append="days" />
							<cform:input label="SPP Due Date" path="SPPDueDate" size="micro" value="${oForm.SPPDueDate}" append="days" />
							
						<h3 class="margin-top-40">Budget Settings</h3>
						<p class="subtitle">Set lock out dates for budget setup</p>
							<div class='control-group'>
								<label class='control-label' for='budgetLockOutDay'>Lock Out Date</label>
								<div class='controls timepicker'>
									<select id='budgetLockOutDay' name='budgetLockOutDay' size='1' class='input-mini'>
										<c:forEach var="i" begin="1" end="31" step="1">
											<c:choose>
												<c:when test="${i == 1 || i == oForm.budgetLockOutDay}">
													<c:choose>
														<c:when test="${i < 10}">
															<option value='0${i}' selected='selected'>0${i}</option>
														</c:when>
														<c:otherwise>
															<option value='${i}' selected='selected'>${i}</option>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${i < 10}">
															<option value='0${i}'>0${i}</option>
														</c:when>
														<c:otherwise>
															<option value='${i}'>${i}</option>
														</c:otherwise>
													</c:choose>
													
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<span>&nbsp;</span>
									<select id='budgetLockOutMonth' name='budgetLockOutMonth' size='1' class='input-very-small'>
										<c:choose>
											<c:when test="${oForm.budgetLockOutMonth == 1}">
												<option value="01" selected='selected'>Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 2}">
												<option value="01">Januari</option>
												<option value="02" selected='selected'>Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 3}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03" selected='selected'>Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 4}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04" selected='selected'>April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 5}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05" selected='selected'>Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 6}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06" selected='selected'>Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 7}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07" selected='selected'>Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 8}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08" selected='selected'>Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 9}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09" selected='selected'>September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 10}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10" selected='selected'>Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 11}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11" selected='selected'>November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 12}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12" selected='selected'>Desember</option>
											</c:when>
											<c:otherwise>
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:otherwise>
										</c:choose>
										
										
									</select>
								</div>
							</div>
							
							<div class='control-group'>
								<label class='control-label' for='budgetEndDay'>End Date</label>
								<div class='controls timepicker'>
									
									<select id='budgetEndDay' name='budgetEndDay' size='1' class='input-mini'>
										<c:forEach var="i" begin="1" end="31" step="1">
											<c:choose>
												<c:when test="${i == 1 || i == oForm.budgetEndDay}">
													<c:choose>
														<c:when test="${i < 10}">
															<option value='0${i}' selected='selected'>0${i}</option>
														</c:when>
														<c:otherwise>
															<option value='${i}' selected='selected'>${i}</option>
														</c:otherwise>
													</c:choose>
													
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${i < 10}">
															<option value='0${i}'>0${i}</option>
														</c:when>
														<c:otherwise>
															<option value='${i}'>${i}</option>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<span>&nbsp;</span>
									<select id='budgetEndMonth' name='budgetEndMonth' size='1' class='input-very-small'>
										<c:choose>
											<c:when test="${oForm.budgetLockOutMonth == 1}">
												<option value="01" selected='selected'>Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 2}">
												<option value="01">Januari</option>
												<option value="02" selected='selected'>Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 3}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03" selected='selected'>Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 4}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04" selected='selected'>April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 5}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05" selected='selected'>Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 6}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06" selected='selected'>Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 7}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07" selected='selected'>Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 8}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08" selected='selected'>Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 9}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09" selected='selected'>September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 10}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10" selected='selected'>Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 11}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11" selected='selected'>November</option>
												<option value="12">Desember</option>
											</c:when>
											<c:when test="${oForm.budgetLockOutMonth == 12}">
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12" selected='selected'>Desember</option>
											</c:when>
											<c:otherwise>
												<option value="01">Januari</option>
												<option value="02">Februari</option>
												<option value="03">Maret</option>
												<option value="04">April</option>
												<option value="05">Mei</option>
												<option value="06">Juni</option>
												<option value="07">Juli</option>
												<option value="08">Agustus</option>
												<option value="09">September</option>
												<option value="10">Oktober</option>
												<option value="11">November</option>
												<option value="12">Desember</option>
											</c:otherwise>
										</c:choose>
									</select>
								</div>
							</div>
<%-- 							<cform:input label="Lock Out Date" path="budgetLockOutDate" datepicker="true" size="small" value="${oForm.budgetLockOutDate}" /> --%>
<%-- 							<cform:input label="End Date" path="budgetEndDate" datepicker="true" size="small" value="${oForm.budgetEndDate}" /> --%>
							
	<!-- 				</div> -->
		
				
					
					
	
					<%-- <cform:input label="Date*" path="trxDate" size="small" datepicker="true" />
					<cform:select label="Transfer From*" path="fromBankId" size="medium" options="${accounts}"/>
					<cform:select label="Transfer To*" path="toBankId" size="medium" options="${accounts}" />
					<cform:input label="Amount*" path="amount" prepend="Rp." size="small" />
					<cform:textarea label="Note" path="note" size="medium" required="false" /> --%>
					
					<c:if test="${canUpdate}">
					<div class="form-actions vrs-nopadl">
						<form:button name="action" value="${save}" class="btn btn-positive">Save</form:button>
						<c:url var="cancel" value="/finance/settings" />
						<a href="${cancel}" class="btn">Cancel</a>
					</div>
					</c:if>
					
				</form:form>
			</div>
		</div>
		<!-- END : CONTENT AREA -->

	</div>
	<div class="clear"></div>
</div>