<%@ include file="header.jsp" %>
<div class="error"><c:out value="${error}" /></div>
<div class="logincontainer">
	<div class="panel panel-info">
		<div class="panel-heading">
			<h3 class="panel-title">Registro</h3>
		</div>
		<div class="panel-body">
			<form method="post" action="/register">
				<fieldset class="loginsecuence">
					<input type="text" name="username" placeholder="Usuario">
					<input type="password" name="password" placeholder="password">
					<input type="password" name="extrapassword" placeholder="password">
					<input type="text" name="name" placeholder="Nombre">
					<input type="text" name="surname" placeholder="Apellido">
					<input type="text" name="description" placeholder="Descripcion">
					<input type="submit" value="Registrar">
				</fieldset>
			</form>
		</div>
	</div>
</div>
<%@ include file="footer.jsp" %>