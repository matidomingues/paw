<%@ include file="../header.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-md-6">
			<div class="panel panel-info">
				<div class="panel-heading">Seguido</div>
				<c:choose>
					<c:when test="${empty localUser.followings}">
						<div class="panel-body">
							<p>No hay Followings para mostrar</p>
						</div>
					</c:when>

					<c:otherwise>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Usuario</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${localUser.followings}" var="following">
									<tr>
										<td><a
											href="${pageContext.request.contextPath}/bin/profile/<c:out value='${following.username}'/>"><c:out
													value="${following.username}"></c:out></a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-info">
				<div class="panel-heading">Seguidores</div>
				<c:choose>
					<c:when test="${empty localUser.followers}">
						<div class="panel-body">
							<p>No hay Followers para mostrar</p>
						</div>
					</c:when>

					<c:otherwise>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Usuario</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${localUser.followers}" var="follower">
									<tr>
										<td><a
											href="${pageContext.request.contextPath}/bin/profile/<c:out value='${follower.username}'/>"><c:out
													value="${follower.username}"></c:out></a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>
<%@ include file="../footer.jsp"%>
