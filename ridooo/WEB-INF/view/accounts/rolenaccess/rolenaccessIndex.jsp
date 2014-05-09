<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="baseURL" value="/"/>
<c:if test="${baseURL=='//'}">
	<c:set var="baseURL" value="/"/>
</c:if>

<c:url var="positionCount" value="0"/>
<div class="print-layout">
	<c:if test="${empty positionList}">
	<div class="print-header">
		<h1>Peran & Akses</h1>
	</div>
	<table class="table">
		<colgroup>
			<col width="80%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
			<col width="5%"></col>
		</colgroup>
		<thead>
			<tr>
				<td>ITEM</td>
				<td align="center">VIEW</td>
				<td align="center">CREATE</td>
				<td align="center">UPDATE</td>
				<td align="center">DELETE</td>
			</tr>
		</thead>
		<tbody >
			<c:forEach items="${urlMenu}" var="urlMenu1">
				<tr style="page-break-after: auto">
					<td>${urlMenu1.name}<br/><span>${urlMenu1.description}</span></td>
					<td align="center"><input name="viewed-${urlMenu1.id}" class="role" type="checkbox"${urlMenu1.viewed}></td>
					<td align="center"><input name="created-${urlMenu1.id}" class="role" type="checkbox"${urlMenu1.created}></td>
					<td align="center"><input name="updated-${urlMenu1.id}" class="role" type="checkbox"${urlMenu1.updated}></td>
					<td align="center"><input name="deleted-${urlMenu1.id}" class="role" type="checkbox"${urlMenu1.deleted}></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:if>
	<c:if test="${!empty positionList}">
		<div class="print-header">
			<h1>Peran & Akses (${positionSelected})</h1>
		</div>
		<table class="table-helper table">
				<colgroup>
					<col style="width:auto"></col>
					<col style="width:auto"></col>
				</colgroup>
				<thead>
					<tr>						
						<td>Nama</td>
						<td>Email</td>
					</tr>
				</thead>
				<tbody  id="tableValuePrint"></tbody>
		</table>
	</c:if>
</div>
<div class="content notsw">
	<div class="main-content<c:if test="${!empty positionList}"> withright</c:if>">
		<form id="formIN" method="POST">
			<!-- heading tittle -->
			<div class="header-info">
				<div class="header-attr">
					
				</div>
				<h1>Peran & Akses</h1>
			</div>
			<c:if test="${empty positionList}">
			<div class="btn_loadmore_area" id="btnPAgeNext"><a class="btn disabled large" href="" disabled="disabled">TIDAK ADA DATA</a></div>
			</c:if>
			<c:if test="${!empty positionList}">
			<div id="container-tabs">
				<div class="tabs-content" id="tab1" style="display:block;">
					<table>
						<colgroup>
							<col style="width:auto"></col>
							<col style="width:auto"></col>
						</colgroup>
						<thead>
							<tr>
								<td>Nama</td>
								<td>Email</td>
							</tr>
						</thead>
						<tbody id="tableValue"></tbody>
					</table>
				<div class="btn_loadmore_area" id="btnPAgeNext"></div>
				</div>
			</div>
		</form>
	</div>
	<div class="rightheader">
	<div class="btn-area">
		<a href="${baseURL}accounts/rolenaccess/${positionSelected}/edit" class="btn netral" id="btnSave"><i class="icon-pencil icon-grey"></i>Ralat</a>
		<a href="javascript:void(0);" class="btn netral print skip-check-dirty"><i class="icon-grey icon-print"></i>CETAK</a>
	</div>
		<div class="form">
		<h3 id="RakNameWarehouse">PERAN</h3>
		<ul id="tabs">
				<c:forEach items="${positionList}" var="position1">
				<c:set var="selectPosition" value="${baseURL}accounts/rolenaccess/${position1.position_name}/selected" />
				<li<c:if test="${positionSelected==position1.position_name}"> class="active"<c:url var="positionCount" value="${position1.qtyRole}"/> </c:if>><a href="${selectPosition}">${position1.position_name} (${position1.qtyRole}) </a></li>
				</c:forEach>
			</ul>
		</div>	
	</div>	
	</c:if>
</div>

<div style="display: none">
	<div class="popup alert" id="addRole" style="width: 300px !important">
		<div class="head_title">
			<span id="infoTitleRak">Tambah Peran</span>
				<a href="javascript:void(0)" class="fr close-popup"><i class="icon-blue icon-close"></i></a>
			<div class="clear"></div>
		</div>
		<div class="popup-holder">
				<c:set var="addurl" value="${baseURL}accounts/rolenaccess/create" />
			<form:form action="${addurl}" modelAttribute="fr">
				<form:hidden path="id" />
				<div class="form">
					<div class="field" style="background-color: red; color: white;"
						id="errorMessage"></div>
					<div class="field">
						<label>Nama Peran</label>
						<form:input path="roleName" class="fr focus-tab"
							target-tab="#btSavePosition" target-type="button" />
					</div>
				</div>
			</form:form>
			<a href="javascript:void(0)" class="btn positive focus-tab skip-check-dirty" id="btSavePosition"
				target-tab="#btCancelRak" target-type="button">Simpan</a> <a href=""
				class="btn netral btNotHapus focus-tab" id="btCancelRak"
				target-tab="#btDeletePeran">Batal</a>
			<div class="clear"></div>
		</div>
	</div>
</div>
<div style="display: none">
		<div class="popup alert" id="askDelete"
			style="width: 400px !important">
			<div class="head_title">
				INFORMASI<a href="javascript:void(0)" class="fr close-popup"><i
					class="icon-blue icon-close"></i></a>
				<div class="clear"></div>
			</div>
			<div class="popup-holder">
			<form method="post" id="formDelete"></form>
			<c:if test="${positionCount<1}">
				<p>Apakah peran ${positionSelected} akan dihapus?</p>
				<br /> <button class="btn positive" id="btYesHapus1" delID="">Hapus</button>
				<a href="" class="btn netral btNotHapus1">Batal</a>
			</c:if>	
			<c:if test="${positionCount>0}">
				<p>Peran ${positionSelected} tidak bisa dihapus karena telah di pakai staff lain</p>
			</c:if>
			</div>
		</div>
	</div>