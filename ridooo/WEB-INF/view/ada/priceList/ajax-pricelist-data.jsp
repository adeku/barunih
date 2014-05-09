<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><c:forEach items="${catalogItemList}" var="catalogItem">
						<tr>
							<td class="borderright"></td>
							<td><a href="" id="${catalogItem.catalogItemID}" class="showDetailCatalog">${catalogItem.sku}</a></td>
							<td class="borderright">${catalogItem.item}</td>
							<c:if test="${isAdmin==1}">
								<td class="borderright number" align="right"><input type="text"  <c:if test="${!(canCreate||canUpdate)}">readonly="true"</c:if> name="msrp-${catalogItem.catalogItemID}-${catalogItem.idVinPricing}" value="${catalogItem.msrp}" class="qty text-right number price prices number-filter msrp"></td>
							</c:if>
							<c:if test="${priceListCount>0}">							
							<td class="number" align="right"><input type="text" <c:if test="${!(canCreate||canUpdate)}">readonly="true"</c:if>  name="price-${catalogItem.catalogItemID}-${catalogItem.idVinPricing}" value="${catalogItem.priceHere}" class="qty text-right number price prices number-filter thisMoney"></td>
							</c:if>
						</tr>
						</c:forEach>