<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="baseURL" value="/" />

<c:if test="${baseURL=='//'}">
<c:set var="baseURL" value="/" />
</c:if>
<div class="content">
	<div class="main-content">
		<div class="error-container">
			<img src="${baseURL}img/error404.png" alt="">
			<h1>ERROR 404</h1>
			<p>
				Kami tidak menemukan halaman yang anda cari.
			</p>
		</div>
	</div>
</div>