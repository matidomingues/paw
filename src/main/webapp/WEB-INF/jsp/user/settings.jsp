<%@ include file="../header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="container">
	<div class="panel panel-info">
		<div class="panel-heading">Twatts</div>
		<div class="panel-body">
			<form:form action="settings" method="post" commandName="userForm" enctype="multipart/form-data">
			<div class="error"><form:errors path="*" /></div>
	
				<form:hidden path="twattUser" />
				
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Nombre</label>
					</div>
					<div class="col-md-10">
						<form:input type="text" path="name" class="input-long"
							required="true" />
						<div class="error">
							<form:errors path="name" />
						</div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Apellido</label>
					</div>
					<div class="col-md-10">
						<form:input type="text" path="surname" class="input-long"
							required="true" />
						<div class="error">
							<form:errors path="surname" />
						</div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Contrasena</label>
					</div>
					<div class="col-md-10">
						<form:input type="password" path="password" class="input-long"
							required="true" />
						<div class="error">
							<form:errors path="password" />
						</div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Contrasena 2</label>
					</div>
					<div class="col-md-10">
						<form:input type="password" path="extrapassword"
							class="input-long" required="true" />
						<div class="error">
							<form:errors path="extrapassword" />
						</div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Descripcion</label>
					</div>
					<div class="col-md-10">
						<form:input type="text" path="description" class="input-long"
							required="true" />
						<div class="error">
							<form:errors path="description" />
						</div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Privacidad del Perfil</label>
					</div>
					<div class="col-md-10">
						<form:checkbox path="privacy"/> Privado
						<div class="error">
							<form:errors path="privacy" />
						</div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Foto</label>
					</div>
					<div class="col-md-6">
						<form:input type="file" path="photo" />
						<div class="error">
							<form:errors path="photo" />
						</div>
					</div>
					<div class="col-md-4">
						<div>
							<img
								src="${pageContext.request.contextPath}/bin/image/<c:out value='${local_user.username}'/>" />
						</div>
					</div>
				</div>
				<input type="submit" class="pull-right btn btn-lg btn-primary"
					value="Guardar">
				<div class="clearfix"></div>
			</form:form>
		</div>
	</div>
</div>

<%@ include file="../footer.jsp"%>