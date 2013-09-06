<%@ include file="header.jsp" %>
	<div class="error"><c:out value="${error}" /></div>
	<form method="post" action="/settings">
		Nombre
		<input type="text" name="name" value="<c:out value="${user.name}"/>">
		<input type="text" name="surname" value='<c:out value="${user.surname}"/>'>
		<input type="text" name="description" value='<c:out value="${user.description}"/>'>
		<input type="password" name="password" value='<c:out value="${user.password}"/>'>
		<input type="submit" value="Guardar">
	</form>
<%@ include file="footer.jsp" %>