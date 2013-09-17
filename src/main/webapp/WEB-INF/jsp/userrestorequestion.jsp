<%@ include file="header.jsp" %>
<div class="container">
	<div class="panel panel-info">
		<div class="panel-heading">Recupero Contrasena de <c:out value="${restoreuser.username}"></c:out></div>
		<div class="panel-body">
			<form method="post" action="/restore/user/<c:out value='${restoreuser.username}'/>">
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Nombre de Usuario</label>
					</div>
					<div class="col-md-10">
						<p><c:out value="${restoreuser.username}"></c:out></p>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Pregunta Secreta</label>
					</div>
					<div class="col-md-10">
						<p><c:out value="${restoreuser.secretQuestion}"></c:out></p>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Respuesta Secreta</label>
					</div>
					<div class="col-md-10">
						<input type="text" class="input-long" name="secretanswer">
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Nueva Contraseña</label>
					</div>
					<div class="col-md-10">
						<input type="password" class="input-long" name="password">
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Confirmacion Contraseña</label>
					</div>
					<div class="col-md-10">
						<input type="password" class="input-long" name="confirmpassword">
					</div>
				</div>
				<input type="submit" class="pull-right btn btn-lg btn-primary"value="Restablecer">
				<div class="clearfix"></div>

			</form>
		</div>
	</div>
</div>

<%@ include file="footer.jsp" %>