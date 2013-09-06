<%@ include file="header.jsp" %>
<div class="well">
	<form method="post" action="/find">
		<input name="username" placeholder="Usuario" type="text">
		<input type="submit" name="Buscar">
	</form> 
</div>
<c:choose>
	<c:when  test="${empty users}">
		<p> La busqueda no devolvio resultados </p>
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
						<td><c:out value="${user.username}"/></td>
						<td><c:out value="${user.name}"/> <c:out value="${user.surname}"/></td>
						<td><c:out value="${user.date}"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>	
</c:choose>
<%@ include file="footer.jsp" %>