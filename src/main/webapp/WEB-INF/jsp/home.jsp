<%@ include file="header.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-md-9">
			<div class="panel panel-info">
				<div class="panel-heading">Hashtags</div>
				<c:choose>
					<c:when test="${empty hashtags}">
						<div class="panel-body">
							<p>No hay #Hashtags para mostrar</p>
						</div>
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
								<c:forEach items="${hashtags}" var="hashtagBundle">
									<tr>
										<td><a
											href="${pageContext.request.contextPath}/bin/hashtag/<c:out value='${hashtagBundle.hashtag.tagName}'/>">#<c:out
													value="${hashtagBundle.hashtag.tagName}"></c:out></a></td>
										<td><c:out value="${hashtagBundle.mentions}"></c:out></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
            <c:if test="${!empty userTwatts}">
                <div class="panel panel-info">
                    <div class="panel-heading">Mis Twatts</div>
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
                        <c:forEach items="${userTwatts}" var="twatt">
                            <%@include file="twatt.jsp"%>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
			<c:choose>
				<c:when test="${!empty followingsTwatts}">
					<div class="panel panel-info">
						<div class="panel-heading">Twatts de los que sigo</div>
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
								<c:forEach items="${followingsTwatts}" var="twatt">
									<%@include file="twatt.jsp"%>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:when>
			</c:choose>
		</div>
		<div class="col-md-3">
			<div class="panel panel-info">
				<div class="panel-heading">Busqueda</div>
				<div class="formsidebar">
					<form method="get" action="${pageContext.request.contextPath}/home">
						<input type="radio" name="dayfilter" value="1"> Dia<br>
						<input type="radio" name="dayfilter" value="7"> Semana<br>
						<input type="radio" name="dayfilter" value="30"> Mes<br>
						<div class="twat-button">
							<input type="submit" class="btn btn-large" value="Buscar">
						</div>
						<div class="clearfix"></div>
					</form>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-heading">Recomendaciones</div>
				<table class="table">
					<tbody>
						<c:forEach items="${candidates}" var="candidate">
							<tr>
								<th><c:out value='${candidate.username}'/></th>
								<td><a
									href="${pageContext.request.contextPath}/bin/follow/<c:out value='${candidate.username}'/>">Follow!</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</div>

<%@ include file="footer.jsp"%>