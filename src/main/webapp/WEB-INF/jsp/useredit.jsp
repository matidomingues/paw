<%@ include file="header.jsp" %>
	<div class="container">
		<div class="panel panel-info">
			<div class="panel-heading">Twatts</div>
			<div class="panel-body">
				<form method="post" action="/settings" enctype="multipart/form-data">
					<div class="row input-padding">
						<div class="col-md-2">
							<label class="input-label">Nombre</label>
						</div>
						<div class="col-md-10">
							<input type="text" class="input-long" name="name" value="<c:out value="${user_name}"/>">
						</div>
					</div>
					<div class="row input-padding">
						<div class="col-md-2">
							<label class="input-label">Apellido</label>
						</div>
						<div class="col-md-10">
							<input type="text" class="input-long" name="surname" value="<c:out value="${user_surname}"/>">
						</div>
					</div>
					<div class="row input-padding">
						<div class="col-md-2">
							<label class="input-label">Contrasena</label>
						</div>
						<div class="col-md-10">
							<input type="password" class="input-long" name="password" value="<c:out value="${user_password}"/>">
						</div>
					</div>
					<div class="row input-padding">
						<div class="col-md-2">
							<label class="input-label">Contrasena 2</label>
						</div>
						<div class="col-md-10">
							<input type="password" class="input-long" name="password2" value="<c:out value="${user_password}"/>">
						</div>
					</div>
					<div class="row input-padding">
						<div class="col-md-2">
							<label class="input-label">Descripcion</label>
						</div>
						<div class="col-md-10">
							<input type="text" class="input-long" name="description" value="<c:out value="${user_description}"/>">
						</div>
					</div>
                    <div class="row input-padding">
                        <div class="col-md-2">
                            <label class="input-label">Foto</label>
                        </div>
                        <div class="col-md-6">
                            <input type="file" name="photo"/>
                        </div>
                        <div class="col-md-4">
                            <div>
                                <img src="/image?id=<c:out value='${user_id}'/>"/>
                            </div>
                        </div>
                    </div>
					<input type="submit" class="pull-right btn btn-lg btn-primary"value="Guardar">
					<div class="clearfix"></div>
				</form>
			</div>
		</div>
	</div>

<%@ include file="footer.jsp" %>