<%@ include file="../header.jsp" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div class="container">
	<div class="panel panel-info">
		<div class="panel-heading">Registrarse</div>
		<div class="panel-body">
			<form:form action="register" method="post" commandName="userForm" enctype="multipart/form-data">
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Nombre de Usuario</label>
					</div>
					<div class="col-md-10">
						<form:input type="text" path="username" class="input-long" required="true"/>
						<div class="error"><form:errors path="username" /></div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Nombre</label>
					</div>
					<div class="col-md-10">
					<form:input type="text" path="name" class="input-long" required="true"/>
						<div class="error"><form:errors path="name" /></div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Apellido</label>
					</div>
					<div class="col-md-10">
					<form:input type="text" path="surname" class="input-long" required="true"/>
						<div class="error"><form:errors path="surname" /></div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Contrasena</label>
					</div>
					<div class="col-md-10">
					<form:input type="password" path="password" class="input-long" required="true"/>
						<div class="error"><form:errors path="password" /></div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Contrasena 2</label>
					</div>
					<div class="col-md-10">
					<form:input type="password" path="extrapassword" class="input-long" required="true"/>
						<div class="error"><form:errors path="extrapassword" /></div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Descripcion</label>
					</div>
					<div class="col-md-10">
					<form:input type="text" path="description" class="input-long" required="true"/>
						<div class="error"><form:errors path="description" /></div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Pregunta Secreta</label>
					</div>
					<div class="col-md-10">
					<form:input type="text" path="secretquestion" class="input-long" required="true"/>
						<div class="error"><form:errors path="secretquestion" /></div>
					</div>
				</div>
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Respuesta Secreta</label>
					</div>
					<div class="col-md-10">
					<form:input type="text" path="secretanswer" class="input-long" required="true"/>
						<div class="error"><form:errors path="secretanswer" /></div>
					</div>
				</div>
                <div class="row input-padding">
                    <div class="col-md-2">
                        <label class="input-label">Foto</label>
                    </div>
                    <div class="col-md-10">
                    <form:input type="file" path="photo" class="input-long"/>
						<div class="error"><form:errors path="photo" /></div>
                    </div>
                </div>
				<input type="submit" class="pull-right btn btn-lg btn-primary" value="Registrar">
				<div class="clearfix"></div>
			</form:form>
		</div>
	</div>
</div>
<%@ include file="../footer.jsp" %>