<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/sales/sales-orders"/>
<c:url var="URL" value="/"/>
<c:set var="onServer" value="0"/>

<c:if test="${URL=='//'}">
	<c:set var="baseURL" value="/sales/sales-orders"/>
	<c:set var="onServer" value="1"/>
</c:if>

<c:choose>
	<c:when test="${action == 'edit' }">
		<c:url var="formURL" value="/sales/sales-orders/${id}/edit" />
		<c:if test="${onServer=='1'}">
		<c:set var="formURL" value="/sales/sales-orders/${id}/edit" />
		</c:if>
	</c:when>
	<c:otherwise>
		<c:url var="formURL" value="/sales/sales-orders/create" />
		<c:if test="${onServer=='1'}">
		<c:set var="formURL" value="/sales/sales-orders/create" />
		</c:if>
	</c:otherwise>
</c:choose>

<div class="content">
	<form:form modelAttribute="form" method="POST" action="${formURL}">
		<form:hidden path="refNumber" value="${form.refNumber}" />
		<div class="main-content withright">
			<!-- heading tittle -->
			<div class="header-info">
				<c:choose>
				<c:when test="${action == 'edit'}">
					<h1>nota pesan (${form.refNumber})</h1>
				</c:when>
				<c:otherwise>
					<h1>nota pesan baru</h1>
				</c:otherwise>
			</c:choose>
		</div>
			<!-- header info -->
			
			<!-- table -->
			<table class="invoice_table" style="${empty form.transactionDetails || form.customerId == '' ? 'display:none' : '' }">
				<colgroup>
					<col style="width:60px;text-align:right;"></col>
					<col style="width:65px;text-align:right;"></col>
					<col style="width:120px"></col>
					<col style="width:auto"></col>
					<col style="width:140px"></col>
					<col style="width:140px"></col>
					<col style="width:40px"></col>
				</colgroup>
				<thead>
					<tr>
						<td align="right">QTY</td>
						<td>UNIT</td>
						<td>SKU</td>
						<td>ITEM</td>
						<td align="right">HARGA</td>
						<td align="right">SUBTOTAL</td>
						<td>&nbsp;</td>
					</tr>
				</thead>
				<tbody></tbody>
				<tfoot>
					<tr>
						<td colspan="7"><a href="#" class="add_item">Tambah Barang</a></td>
					</tr>
				</tfoot>
			</table>
			
			<div class="empty-state" style="${empty form.transactionDetails || form.customerId == ''  ? '' : 'display:none' }">
				<i class="empty-icon entry"></i>
				<h4>Silakan mulai dengan mengisi data pelanggan di sisi kanan.</h4>
			</div>
		</div>
		<div class="rightheader">
			<div class="btn-area">
				<button id="save-button" class="btn positive"><i class="icon-file icon-white"></i>SIMPAN</button>
				<a href="${baseURL}" class="btn netral close"><i class="icon-cancel icon-grey"></i>KEMBALI</a>
			</div>
			<div class="totalbar ">
				<span class="total-weight">
					<c:choose>
						<c:when test="${action.equals('edit')}">
							${form.totalWeight}
						</c:when>
						<c:otherwise>
							0
						</c:otherwise>
					</c:choose>
				</span>
				<small>kg</small>
				<form:hidden path="totalWeight" />
				<span class="total-item">
					<c:choose>
						<c:when test="${action.equals('edit')}">
							${form.totalItem}
						</c:when>
						<c:otherwise>
							0
						</c:otherwise>
					</c:choose>
				</span>
				<c:choose>
					<c:when test="${action.equals('edit')}">
						<form:hidden path="totalItem" value="${form.totalItem}" class="validation" data-validation="required,number"/>
						<form:hidden path="totalWeight" value="${form.totalWeight}" class="validation" data-validation="required"/>
					</c:when>
					<c:otherwise>
						<form:hidden path="totalItem" value="0" class="validation" data-validation="required,number"/>
						<form:hidden path="totalWeight" value="0" class="validation" data-validation="required"/>
					</c:otherwise>
				</c:choose>
				
				<small>items</small>
				<br/>
				<div class="fl">TOTAL<br/><span>IDR</span></div>
				<div class="fr">
					<span class="total-price price">
					<c:choose>
						<c:when test="${action.equals('edit')}">
							${form.total}
						</c:when>
						<c:otherwise>
							0
						</c:otherwise>
					</c:choose>
					</span>
					<form:hidden path="total" class="validation" data-validation="number"/>
<!-- 					<input type="hidden" name="total"> -->
				</div>
				<div class="clear"></div>
			</div>
			<div class="form">
				<div class="field">
					<div class="checkbox">
						<div class="checkbox-field">
							<form:checkbox path="retail" class="focus-tab" target-type="autocomplete" target-tab="#customer"/>
	<!-- 						<input type="checkbox" name="retail"> -->
							<label>Retail Order</label>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<div class="inputholder">
					<label>Pelanggan:</label> 
					
					<form:input path="customerId" id="customer" data-validation="required" cssClass="medium text-right border-bottom focus-tab validation" target-tab="#customerPO" placeholder="Select customer..." />
					<form:hidden path="customerName"/>
	<!-- 				<input placeholder="Select customer..." -->
	<!-- 					name="customerId" id="customer" type="text" -->
	<!-- 					class=" medium text-right border-bottom" /> -->
					<!-- <textarea class="long address" disabled></textarea> -->
					<form:textarea path="customerAddress" class="long address" disabled="true"/>
				</div>

				<div class="field">
					<label>PO Pelanggan:</label>
					<form:input path="customerPO"  cssClass="fr text-right semi-medium"/>
	<!-- 				<input class="fr normal" type="text" name="customerPO"/> -->
				</div>

				<div class="field">
					<label >Tgl Kirim:</label>
					<div class="field-content fr">
						<div class="input-date input-append">
							<div class="input-icon"><i class="icon-calendar"></i></div>
							<form:input  data-validation="required"  path="deliveryDate" cssClass="datepicker focus-tab text-right validation" target-type="select" target-tab="#salesPersonId"/>
	<!-- 						<input type="text" class="datepicker" name="deliveryDate" value="01/01/2014"/> -->
						</div>
					</div>
				</div>

				<div class="field">
					<label>Sales person:</label>
					<div class="field-content fr">
						<form:select path="salesPersonId" data-validation="required"  cssClass="select focus-tab validation"  target-type="select" items="${salesList}" target-tab="#priceList" />
					</div>
				</div>
				
				<div class="field">
					<label>Daftar Harga:}</label>
					<div class="field-content fr">
						<c:choose>
							<c:when test="${action.equals('edit')}">
								<input id="priceList"  type="text" value="${form.priceList}" class="semi-medium text-right" readonly> 
							</c:when>
							
							<c:otherwise>
								<select id="priceList" class="select focus-tab" target-action="showTable(true)"  name="priceList" }>
									<c:forEach items="${priceList}" var="priceList" varStatus="pointer">
										<option value="${priceList}" ${priceList.equals(form.priceList) ? 'Selected' : ''} >${priceList}</option>
									</c:forEach>
								</select>
							</c:otherwise>
						</c:choose>
						<div class="clear"></div>
					</div>
				</div>

			</div>
			
		</div>
		<div class="clear"></div>
	</form:form>
	<div style="display: none">
		<div class="popup alert" id="popup">
			<div class="head_title">PERHATIAN
				<a href="javscript:void();)" class="fr close-popup">
					<i class="icon-blue icon-close"></i>
				</a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Anda harus memilih customer terlebih dahulu. Input customer
					berada pada sidebar sebelah kanan!</p>
				<br />
				<a href="javascript:void(0)" class="btn positive" id="customer-goto" >PILIH CUSTOMER</a>
				<div class="clear"></div>
			</div>
		</div>
	</div>
</div>