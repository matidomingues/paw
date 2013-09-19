<%@ include file="header.jsp" %>
<div class="container">
	<div class="panel panel-info">
		<div class="panel-heading">Registrarse</div>
		<div class="panel-body">
			<form method="post" action="${pageContext.request.contextPath}/register">
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Nombre de Usuario</label>
					</div>
					<div class="col-md-10">
						<input type="text" class="input-long" name="username" required>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Nombre</label>
					</div>
					<div class="col-md-10">
						<input type="text" class="input-long" name="name" required>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Apellido</label>
					</div>
					<div class="col-md-10">
						<input type="text" class="input-long" name="surname" required>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Contrasena</label>
					</div>
					<div class="col-md-10">
						<input type="password" class="input-long" name="password" required>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Contrasena 2</label>
					</div>
					<div class="col-md-10">
						<input type="password" class="input-long" name="extrapassword" required>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Descripcion</label>
					</div>
					<div class="col-md-10">
						<input type="text" class="input-long" name="description" required>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Pregunta Secreta</label>
					</div>
					<div class="col-md-10">
						<input type="text" class="input-long" name="secretquestion" required>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Respuesta Secreta</label>
					</div>
					<div class="col-md-10">
						<input type="text" class="input-long" name="secretanswer" required>
					</div>
				</div>
				<input type="submit" class="pull-right btn btn-lg btn-primary"value="Registrar">
				<div class="clearfix"></div>
			</form>
		</div>
	</div>
</div>
<%@ include file="footer.jsp" %>