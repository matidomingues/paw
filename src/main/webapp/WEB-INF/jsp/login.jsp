<%@ include file="header.jsp" %>
<div class="error"><c:out value="${error}"></c:out></div>
<div class="container">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">Login</h3>
				</div>
				<div class="panel-body">
					<form method="post" action="/login">
						<fieldset class="loginsecuence">
							<input type="text" class="input-long input-border"name="username" placeholder="Usuario">
							<input type="password" class="input-long input-border" name="password" placeholder="password">
							<input type="submit" class="btn btn-primary" value="Login">
							<a href="/register" class="btn btn-primary">Register</a>
							<a data-toggle="modal" href="#restore-modal" class="btn btn-primary">Recupero Contrasena</a>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="restore-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <div class="modal-dialog">
		      	<div class="modal-content">
		        	<div class="modal-body">
		          		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<form method="post" action="/restore">
							<input type="text" class="twat-box" name="message" placeholder="Twatt" maxlength="140">
							<div class ="twat-button">
								<input type="submit" class="btn btn-primary" name="Twatt">
							</div>
							<div class="clearfix"></div>
						</form>
		        	</div>
		      	</div>
		    </div>
		 </div>
<%@ include file="footer.jsp" %>