<%@ include file="header.jsp" %>
	<form method="post" action="/find">
		<input name="username" placeholder="Usuario" type="text">
		<input type="submit" name="Buscar">
	</form> 
	<c:choose>
		<c:when  test="${empty users}">
			<p> La busqueda no devolvio resultados </p>
		</c:when>
		<c:otherwise>
			<table>
				<c:forEach items="${users}" var="user">
					<tr>
						<td><c:out value="${user.name}"/></td>
						<td><c:out value="${user.surname}"/></td>
						<td><c:out value="${user.description}"/></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>	
	</c:choose>
<%@ include file="footer.jsp" %>