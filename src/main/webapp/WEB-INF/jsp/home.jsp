<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="header.jsp" %>
<div class="container">
	<div class="main-content-wrapper">
		<div class="main-content">
			<div class="panel panel-info">
				<div class="panel-heading">Hashtags</div>
				<c:choose>
					<c:when  test="${empty hashtags}">
						<div class="panel-body"><p> No hay #Hashtags para mostrar </p></div>
					</c:when>

					<c:otherwise>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>#</th>
									<th># menciones</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${hashtags}" var="hashtag">
									<tr>
										<td><a href="/hastag/<c:out value='${hashtag.tagName}'/>"><c:out value="${hashtag.tagName}"></c:out></a></td>
										<td><c:out value="${fn:length(hashtag.invokers)}"></c:out></td>
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
				<form method="post" action="/home">
					<input type="radio" name="dayfilter" value="day"> Dia<br>
					<input type="radio" name="dayfilter" value="week"> Mes<br>
					<input type="radio" name="dayfilter" value="month"> Ano<br>
					<div class ="twat-button">
						<input type="submit" class="btn btn-large" name="Buscar">
					</div>
					<div class="clearfix"></div>
				</form>
			</div>
		</div> 
	</div>
</div>
<%@ include file="footer.jsp" %>