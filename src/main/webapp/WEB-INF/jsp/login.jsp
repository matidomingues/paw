<%@ include file="header.jsp" %>
	<div class="error"><c:out value="${error}"></c:out></div>
	<div class="container">
	<div class="panel panel-info">
		<div class="panel-heading">
   	 		<h3 class="panel-title">Login</h3>
  		</div>
  		<div class="panel-body">
			<form method="post" action="/login">
				<fieldset>
					<input type="text" name="username" placeholder="Usuario">
					<input type="password" name="password" placeholder="password">
					<input type="submit" class="btn btn-primary" value="Login">
					<a href="/register" class="btn btn-primary">Register</a>
				</fieldset>
			</form>
		</div>
	</div>
	</div>
	
<%@ include file="footer.jsp" %>