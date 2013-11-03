<nav class="navbar" role="navigation">
	<div class="navbar-header">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/bin/home">Twatter</a>
	</div>
	<div class="collapse navbar-collapse navbar-ex1-collapse">	
		<c:choose>
			<c:when  test="${empty local_user}">
				<ul class="nav navbar-nav navbar-right">
					<li> <a href="${pageContext.request.contextPath}/bin/user/login"> Ingresar </a> </li>
					<li> <a href="${pageContext.request.contextPath}/bin/user/register"> Registrarse </a> </li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul class="nav navbar-nav">
					<li><a data-toggle="modal" href="#twatt-modal">Twatt!</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li> <a href="${pageContext.request.contextPath}/bin/profile/<c:out value='${local_user.username}'/>">Perfil</a></li>
					<li> <a href="${pageContext.request.contextPath}/bin/find"> Busqueda</a></li>
					<li> <a href="${pageContext.request.contextPath}/bin/user/report"> Reporte</a></li>
					<li class="dropdown">
	    				<a href="#" class="dropdown-toggle" data-toggle="dropdown">Cuenta <b class="caret"></b></a>
		   				<ul class="dropdown-menu pull-right">
                            <li><a href="${pageContext.request.contextPath}/bin/user/notifications">Notificaciones</a></li>
                            <li><a href="${pageContext.request.contextPath}/bin/user/favourites">Favoritos</a></li>
			          		<li><a href="${pageContext.request.contextPath}/bin/user/settings">Configuracion</a></li>
			          		<li><a href="${pageContext.request.contextPath}/bin/user/logout">Salir</a></li>
			        	</ul>
			     	</li>
			     <ul>
			</c:otherwise>	
		</c:choose>
	</div>
</nav>
<c:choose>
	<c:when  test="${!empty local_user}">
		<div class="modal fade" id="twatt-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <div class="modal-dialog">
		      	<div class="modal-content">
		        	<div class="modal-body">
		          		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<form method="post" action="${pageContext.request.contextPath}/bin/twatt/add">
							<input type="text" class="twat-box" name="message" placeholder="Twatt" maxlength="140" required>
							<div class ="twat-button">
								<input type="submit" class="btn btn-primary" value="Twatt!">
							</div>
							<div class="clearfix"></div>
						</form>
		        	</div>
		      	</div>
		    </div>
		 </div>
	</c:when>
</c:choose>