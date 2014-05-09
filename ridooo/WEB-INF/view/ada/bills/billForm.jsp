<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/" />
<c:set var="onServer" value="0" />

<c:if test="${baseURL=='//'}">
	<c:set var="onServer" value="1" />
	<c:set var="baseURL" value="/" />
</c:if>

<c:choose>
	<c:when test="${action == 'edit' }">
		<c:url var="formURL" value="/purchase/bills/${id}/edit" />
		<c:if test="${onServer=='1'}">
			<c:set var="formURL" value="/purchase/bills/${id}/edit" />
		</c:if>
	</c:when>
	<c:otherwise>
		<c:url var="formURL" value="/purchase/bills/create" />
		<c:if test="${onServer=='1'}">
			<c:set var="formURL" value="/purchase/bills/create" />
		</c:if>
	</c:otherwise>
</c:choose>

<div class="content">
	<form:form modelAttribute="form" method="POST" action="${formURL}">
		<div class="main-content withright">
			<!-- heading tittle -->
			<div class="header-info">
				<c:choose>
					<c:when test="${action == 'edit'}">
						<h1>nota beli (BILL-${id})</h1>
					</c:when>
					<c:otherwise>
						<h1>nota beli baru</h1>
					</c:otherwise>
				</c:choose>
			</div>

			<!-- header info -->

			<!-- table -->
			<table class="invoice_table"
				style="${empty form.transactionDetails ? 'display:none' : '' }">
				<colgroup>
					<col style="width: 60px; text-align: right;"></col>
					<col style="width: 60px"></col>
					<col style="width: 70px"></col>
					<col style="width: 120px"></col>
					<col style="width: auto"></col>
					<col style="width: 140px"></col>
					<col style="width: 140px"></col>
					<col style="width: 40px"></col>
				</colgroup>
				<thead>
					<tr>
						<td align="right">QTY</td>
						<td>UNIT</td>
						<td>IN BOX</td>
						<td>SKU</td>
						<td>ITEM</td>
						<td align="right">HARGA</td>
						<td align="right">SUBTOTAL</td>
						<td>&nbsp;</td>
					</tr>
				</thead>
				<tbody>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="7"><a href="#" class="add_item">Tambah Barang</a></td>
					</tr>
				</tfoot>
			</table>

			<div class="empty-state"
				style="${empty form.transactionDetails ? '' : 'display:none' }">
				<i class="empty-icon entry"></i>
				<h4>Silakan mulai dengan mengisi data supplier di sisi kanan.</h4>
			</div>
		</div>
		<div class="rightheader">
			<div class="btn-area">
				<button id="save-button" class="btn positive">
					<i class="icon-file icon-white"></i>SIMPAN
				</button>
				<a href="${baseURL}purchase/bills" class="btn netral close"><i
					class="icon-cancel icon-grey"></i>KEMBALI</a>
			</div>
			<div class="totalbar ">
				<span class="total-weight"> <c:choose>
						<c:when test="${action.equals('edit')}">
							${form.totalWeight}
						</c:when>
						<c:otherwise> 0 </c:otherwise>
					</c:choose>
				</span> <small>kg</small>
				<form:hidden path="totalWeight" />
				<span class="total-item"> <c:choose>
						<c:when test="${action.equals('edit')}">
							${form.totalItem}
						</c:when>
						<c:otherwise> 0 </c:otherwise>
					</c:choose>
				</span>
				<c:choose>
					<c:when test="${action.equals('edit')}">
						<form:hidden path="totalItem" value="${form.totalItem}" />
					</c:when>
					<c:otherwise>
						<form:hidden path="totalItem" value="0" />
					</c:otherwise>
				</c:choose>

				<small>items</small> <br />
				<div class="fl">
					TOTAL<br />
					<span>IDR</span>
				</div>
				<div class="fr">
					<span class="total-price price"> <c:choose>
							<c:when test="${action.equals('edit')}">
							${form.total}
						</c:when>
							<c:otherwise>
							0
						</c:otherwise>
						</c:choose>
					</span>
					<form:hidden path="total" />
					<!-- 					<input type="hidden" name="total"> -->
				</div>
				<div class="clear"></div>
			</div>
			<div class="form">

				<div class="field">
					<label>Kode Bongkar:</label>
					<form:input path="refNumber" cssClass="fr input-mini text-right validation" data-validation="required"  />
				</div>

				<div class="inputholder">
					<label>Vendor:</label>

					<form:input path="supplierId" id="supplier"
						cssClass="medium text-right border-bottom focus-tab validate validation" data-validation="required" 
						placeholder="Select supplier..." />
					<form:hidden path="supplierName" />
					<form:textarea path="supplierAddress" class="long address"
						disabled="true" />
				</div>
				
				<div class="field">
					<label>Gudang:</label>
					<div class="field-content fr">
						<form:select path="warehouseId" cssClass="select focus-tab validation" items="${warehouseOption}"   data-validation="required"  target-action="showTable(true)" />
					</div>
				</div>

			</div>
		</div>
		<div class="clear"></div>
	</form:form>

	<div style="display: none">
		<div class="popup alert" id="popup">
			<div class="head_title">
				PERHATIAN<a href="javascript:void(0)" class="fr close-popup"><i class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
				<p>Anda harus memilih supplier terlebih dahulu. Input supplier
					berada pada sidebar sebelah kanan!</p>
				<div class="clear"></div>
				<br />
				<a href="javascript:void(0)" class="btn positive" id="supplier-goto"  >PILIH SUPPLIER</a>
			</div>
		</div>
	</div>
</div>