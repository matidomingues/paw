<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../header.jsp"%>
<div class="container">
	<div class="main-content-wrapper">
		<div class="main-content">
			<div class="panel panel-info">
				<div class="panel-heading">Twatts</div>
				<c:choose>
					<c:when test="${empty twatts}">
						<div class="panel-body">
							<p>No hay Twatts para mostrar</p>
						</div>
					</c:when>
					<c:otherwise>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Foto</th>
									<th>User</th>
									<th>Tweet</th>
									<th>Fecha</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${twatts}" var="twatt">
									<%@include file="../twatt.jsp"%>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="sidebar panel panel-info">
		<div class="panel-heading">Informacion del Usuario</div>
		<table class="table">
			<tbody>
				<tr>
					<th>Usuario</th>
					<td><c:out value="${searchuser.username}"></c:out></td>
				</tr>
				<tr>
					<th>Nombre Completo</th>
					<td><c:out value="${searchuser.name}">
						</c:out> <c:out value="${searchuser.surname}"></c:out></td>
				</tr>
				<tr>
					<th>Descripcion</th>
					<td><c:out value="${searchuser.description}"></c:out></td>
				</tr>
				<tr>
					<th># de Followers</th>
					<td><c:out value="${fn:length(searchuser.followers)}"></c:out></td>
				</tr>
				<tr>
					<th># de Followings</th>
					<td><c:out value="${fn:length(searchuser.followings)}"></c:out></td>
				</tr>
				<c:choose>
					<c:when test="${!empty follow}">
						<c:choose>
							<c:when test="${follow}">
								<tr>
									<th>Seguir</th>
									<td><a class="btn"
										href="/bin/follow/<c:out value='${searchuser.username}'/>">Follow!</a>
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<th>Dejar de Seguir</th>
									<td><a class="btn"
										href="/bin/unfollow/<c:out value='${searchuser.username}'/>">UnFollow!</a>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:when>
				</c:choose>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="../footer.jsp"%>