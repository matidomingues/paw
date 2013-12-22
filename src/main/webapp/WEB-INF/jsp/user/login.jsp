<%@ include file="../header.jsp" %>
<div class="container">
	<div class="row text-center">
		<div class="col-md-offset-3 col-md-6 text-center">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">Login</h3>
				</div>
				<div class="panel-body">
					<form method="post" action="login">
						<fieldset class="loginsecuence">
							<input type="text" class="input-long input-border"name="username" placeholder="Usuario" value="<c:out value="${username}"/>" required>
							<input type="password" class="input-long input-border" name="password" placeholder="password" required>
							<button type="submit" class="btn btn-primary" value="Login"><span class="glyphicon glyphicon-log-in"></span> Ingresar</button>
							<a href="register" class="btn btn-primary">Registrarse</a>
							<a href="restore" class="btn btn-primary">Recuperar Contrase&ntilde;a</a>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="../footer.jsp" %>