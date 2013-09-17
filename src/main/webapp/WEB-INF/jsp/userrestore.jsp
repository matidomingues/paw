<%@ include file="header.jsp" %>
<div class="container">
	<div class="panel panel-info">
		<div class="panel-heading">Recupero Contrasena</div>
		<div class="panel-body">
			<form method="post" action="/restore">
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Nombre de usuario</label>
					</div>
					<div class="col-md-10">
						<input type="text" class="input-long" name="username">
					</div>
				</div>
				<input type="submit" class="pull-right btn btn-lg btn-primary"value="Seleccionar Usuario">
				<div class="clearfix"></div>

			</form>
		</div>
	</div>
</div>

<%@ include file="footer.jsp" %>