<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:forEach items="${dataRAk}" var="dataRAk1"><tr>
			<td id="rakkIIDD-${dataRAk1.id}">${dataRAk1.rakName}</td>
			<td>${dataRAk1.sumRak}</td>
			<td><div class="btn-group icon fr">
				<div class="btn">
					<i class="icon-dropdown"></i>
				</div>
				<ul class="rg">
					<li>
						<a href="" class="editDataRak1" id="${dataRAk1.id}" compid="${dataRAk1.wareHouseID}">Ralat</a>
					</li>
					<c:if test="${dataRAk1.isDefault!=1}">
					<li>
					<a href="" class="cancel btnHapusRak1" id="${dataRAk1.id}">Hapus</a>
					</li>
					</c:if>
				</ul>
			</div></td>
		</tr>
</c:forEach>