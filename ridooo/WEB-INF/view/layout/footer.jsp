<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<!-- FOOTER -->
		<div class="footer">
			<div class="container">
				<ul class="tips">
					<%-- 
						This if condition use for determine which hint suit with URL ,
						and the other is for next or previous item. -danz-
					--%>
					<%-- <c:forEach items="${footer }" var="footers">
						<c:if test="${footers.title == footer_state }">
							<li>
								<span class="label label-info">Info</span>${footers.excerpt}<a href="${pageContext.request.contextPath}/uguide/${footers.id}" class="more">More</a>
							</li>
						</c:if>
						<c:if test="${footers.title != footer_state }">
							<li>
								<span class="label label-info">Info</span>${footers.excerpt}<a href="${pageContext.request.contextPath}/uguide/${footers.id}" class="more">More</a>
							</li>
						</c:if>
						<c:if test="${footers.title == random}">
							<li>
								<span class="label label-info">Info</span>${footers.excerpt}<a href="${pageContext.request.contextPath}/uguide/${footers.id}" class="more">More</a>
							</li>
						</c:if>
					</c:forEach> --%>
				</ul>
			</div>
		</div>
	</body>
</html>