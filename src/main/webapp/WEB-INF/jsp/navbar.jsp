<nav class="navbar" role="navigation">
	<div class="navbar-header">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/home">Twatter</a>
	</div>
	<div class="collapse navbar-collapse navbar-ex1-collapse">	
		<c:choose>
			<c:when  test="${empty user}">
				<ul class="nav navbar-nav navbar-right">
					<li> <a href="${pageContext.request.contextPath}/login"> Login </a> </li>
					<li> <a href="${pageContext.request.contextPath}/register"> Register </a> </li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul class="nav navbar-nav">
					<li><a data-toggle="modal" href="#twatt-modal">Twatt!</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li> <a href="${pageContext.request.contextPath}/user/<c:out value='${user.username}'/>">Perfil</a></li>
					<li> <a href="${pageContext.request.contextPath}/find"> Busqueda</a></li>
					<li class="dropdown">
	    				<a href="#" class="dropdown-toggle" data-toggle="dropdown">Cuenta <b class="caret"></b></a>
		   				<ul class="dropdown-menu pull-right">
			          		<li><a href="${pageContext.request.contextPath}/settings">Configuracion</a></li>
			          		<li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
			        	</ul>
			     	</li>
			     <ul>
			</c:otherwise>	
		</c:choose>
	</div>
</nav>
<c:choose>
	<c:when  test="${!empty user}">
		<div class="modal fade" id="twatt-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <div class="modal-dialog">
		      	<div class="modal-content">
		        	<div class="modal-body">
		          		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<form method="post" action="${pageContext.request.contextPath}/twatt/add">
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