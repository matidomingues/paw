<%@ include file="header.jsp" %>
	<div class="error"><c:out value="${error}" /></div>
	<h2>Login</h2>
	<form method="post" action="/register">
		<input type="text" name="username" placeholder="Usuario">
		<input type="password" name="password" placeholder="password">
		<input type="password" name="extrapassword" placeholder="password">
		<input type="text" name="name" placeholder="Nombre">
		<input type="text" name="surname" placeholder="Apellido">
		<input type="text" name="description" placeholder="Descripcion">
		<input type="submit" value="Registrar">
	</form>
<%@ include file="footer.jsp" %>