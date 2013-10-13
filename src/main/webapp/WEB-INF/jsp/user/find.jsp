<%@ include file="../header.jsp" %>
<div class="container">
	<div class="main-content-wrapper">
		<div class="main-content">
			<div class="panel panel-info">
				<div class="panel-heading">Usuarios</div>
				<c:choose>
					<c:when  test="${empty users}">
						<div class="panel-body"><p> La busqueda no devolvio resultados </p></div>
					</c:when>

					<c:otherwise>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Nombre de Usuario</th>
									<th>Nombre Completo</th>
									<th>Fecha de Registro</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${users}" var="user">
									<tr>
										<td><a href="${pageContext.request.contextPath}/bin/user/<c:out value='${user.username}'/>"><c:out value="${user.username}"></c:out></a></td>
										<td><c:out value="${user.name}"></c:out> <c:out value="${user.surname}"></c:out></td>
										<td><c:out value="${user.date}"></c:out></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>	
				</c:choose>
			</div>
		</div>
	</div>
	<div class="sidebar panel panel-info">
		<div class="panel-heading">Busqueda</div>
			<div class="formsidebar">
				<form method="post" action="find">
					<div class="filter-form">
						<label class="sidebar-label">Usuario</label>
						<input type="text" name="username">
					</div>
					<input type="submit" class="btn btn-large" name="Buscar">
				</form>
			</div>
		</div> 
	</div>
</div>

<%@ include file="../footer.jsp" %>