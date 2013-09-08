<%@ include file="header.jsp" %>
	<div class="error"><c:out value="${error}" /></div>
	<h1><c:out value="${user.username}"/></h1>
	<c:out value="${user.name}"> </c:out><c:out value="${user.surname}"></c:out>
	<h2><c:out value="${user.description}"></c:out></h2>

	<h3>Twatts</h3>
	<c:choose>
		<c:when  test="${empty twatts}">
			<p> No hay Twatts para mostrar </p>
		</c:when>
		<c:otherwise>
			<table>
				<c:forEach items="${twatts}" var="twatt">
					<tr>
						<td>
						<c:url value="${twatt.message}"/>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>	
	</c:choose>
<%@ include file="footer.jsp" %>