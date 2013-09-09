<nav class="navbar" role="navigation">
	<div class="navbar-header">
		<a class="navbar-brand" href="/home">Twatter</a>
	</div>
	<div class="collapse navbar-collapse navbar-ex1-collapse">	
		<c:choose>
			<c:when  test="${empty user}">
				<ul class="nav navbar-nav navbar-right">
					<li> <a href="/login"> Login </a> </li>
					<li> <a href="/register"> Register </a> </li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul class="nav navbar-nav">
					<li><a data-toggle="modal" href="#twatt-modal">Twatt!</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li> <a href="/user/<c:out value='${user.username}'/>">Perfil</a></li>
					<li> <a href="/find"> Busqueda</a></li>
					<li class="dropdown">
	    				<a href="#" class="dropdown-toggle" data-toggle="dropdown">Cuenta <b class="caret"></b></a>
		   				<ul class="dropdown-menu pull-right">
			          		<li><a href="/settings">Configuracion</a></li>
			          		<li><a href="/logout">Log Out</a></li>
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
						<form method="post" action="/twatt/add">
							<input type="text" class="twat-box" name="message" placeholder="Twatt">
							<div class ="twat-button">
								<input type="submit" class="btn btn-primary" name="Twatt">
							</div>
							<div class="clearfix"></div>
						</form>
		        	</div>
		      	</div>
		    </div>
		 </div>
	</c:when>
</c:choose>