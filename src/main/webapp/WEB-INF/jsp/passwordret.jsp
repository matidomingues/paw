<%@ include file="header.jsp" %>
<div class="error"><c:out value="${error}" ></c:out></div>
<div class="container">
	<div class="panel panel-info">
		<div class="panel-heading">Recupero Contrasena de <c:out value="${user}"></c:out></div>
		<div class="panel-body">
			<form method="post" action="/settings">
				<div class="row input-padding">
					<div class="col-md-2">
						<label class="input-label">Nombre</label>
					</div>
					<div class="col-md-10">
						<input type="text" class="input-long" name="name" value="<c:out value="${user.name}"/>">
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<%@ include file="footer.jsp" %>