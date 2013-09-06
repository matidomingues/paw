<%@ include file="header.jsp" %>
<h2>Twattealo!</h2>
<form method="post" action="/addtwatt">
	<input type="text" name="text" placeholder="Twatt">
	<input type="submit" name="Enviar">
</form>

<c:choose>
		<c:when  test="${empty hashtags}">
			<p> No hay Hashtags para mostrar </p>
		</c:when>
		<c:otherwise>
			<table>
				<c:forEach items="${hashtags}" var="hashtag">
					<tr>
						<td><c:out value="${hashtag.tagName}"/></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>	
	</c:choose>

<%@ include file="footer.jsp" %>