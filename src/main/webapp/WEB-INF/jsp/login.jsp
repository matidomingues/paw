<%@ include file="header.jsp" %>
	<div class="error"><c:out value="${error}" /></div>
	<h2>Login</h2>
	<form method="post" action="/login">
		<input type="text" name="username" placeholder="Usuario">
		<input type="password" name="password" placeholder="password">
		<input type="submit" value="Login">
	</form>
	<a href="/register">Register</a>
<%@ include file="footer.jsp" %>