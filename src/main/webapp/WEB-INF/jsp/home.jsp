<%@ include file="header.jsp" %>
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