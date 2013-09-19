<%@ include file="header.jsp" %>
<div class="container">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">Login</h3>
				</div>
				<div class="panel-body">
					<form method="post" action="${pageContext.request.contextPath}/login">
						<fieldset class="loginsecuence">
							<input type="text" class="input-long input-border"name="username" placeholder="Usuario" required>
							<input type="password" class="input-long input-border" name="password" placeholder="password" required>
							<input type="submit" class="btn btn-primary" value="Login">
							<a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Register</a>
							<a href="${pageContext.request.contextPath}/restore" class="btn btn-primary">Recupero Contrasena</a>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="footer.jsp" %>