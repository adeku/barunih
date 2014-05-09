<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="popup alert" id="popup-prices">
	
	<div class="popup-holder">
		<div class="history">
			<table>
				<thead>
					<tr>
						<td colspan="2">PRICE HISTORY</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="borderright"><span>Current Price</span></td>
						<td align="right" class="number"><a href="javascript:void(0)" class="price">${current_price}</a></td>
					</tr>
					<c:forEach items="${data}" var="item" >
						<tr>
							<td class="borderright"><span>${item.date}</span></td>
							<td align="right" class="number"><a href="javascript:void(0)" class="price">${item.prices}</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>