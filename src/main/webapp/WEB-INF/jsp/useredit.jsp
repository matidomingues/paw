<%@ include file="header.jsp" %>
	<div class="error"><c:out value="${error}" ></c:out></div>
	<div class="container">
		<div class="panel panel-info">
			<div class="panel-heading">Twatts</div>
			<div class="panel-body">
				<form method="post" action="/settings">
					<div class="row input-padding">
						<div class="col-md-2">
							<label class="input-label">Nombre</label>
						</div>
						<div class="col-md-10">
							<input type="text" class="input-long" name="name" value="<c:out value="${user.name}"/>">
						</div>
					</div>
					<div class="row input-padding">
						<div class="col-md-2">
							<label class="input-label">Apellido</label>
						</div>
						<div class="col-md-10">
							<input type="text" class="input-long" name="surname" value="<c:out value="${user.surname}"/>">
						</div>
					</div>
					<div class="row input-padding">
						<div class="col-md-2">
							<label class="input-label">Contrasena</label>
						</div>
						<div class="col-md-10">
							<input type="password" class="input-long" name="password" value="<c:out value="${user.password}"/>">
						</div>
					</div>
					<div class="row input-padding">
						<div class="col-md-2">
							<label class="input-label">Contrasena 2</label>
						</div>
						<div class="col-md-10">
							<input type="password" class="input-long" name="password2" value="<c:out value="${user.password}"/>">
						</div>
					</div>
					<div class="row input-padding">
						<div class="col-md-2">
							<label class="input-label">Descripcion</label>
						</div>
						<div class="col-md-10">
							<input type="text" class="input-long" name="description" value="<c:out value="${user.description}"/>">
						</div>
					</div>
					<input type="submit" class="pull-right btn btn-lg btn-primary"value="Guardar">
					<div class="clearfix"></div>
				</form>
			</div>
		</div>
	</div>

<%@ include file="footer.jsp" %>