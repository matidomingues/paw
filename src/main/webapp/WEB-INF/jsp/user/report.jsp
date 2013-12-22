<%@ include file="../header.jsp"%>
	<div class="container">
		<div class="row text-center">
			<div class="col-md-6">
				<div id="chartContainer" style="height: 300px; width: 100%;"></div>
			</div>
		</div>
	</div>

	<div class="container well">
		<div class="row input-padding">
			<div class="col-md-2">
				<label class="input-label">Fecha de Inicio</label>
			</div>
			<div class="col-md-10">
				<input type="text" id="startDate" class="input-long">
			</div>
		</div>
		<div class="row input-padding">
			<div class="col-md-2">
				<label class="input-label">Fecha de Finalizacion</label>
			</div>
			<div class="col-md-10">
				<input type="text" id="endDate" class="input-long">
			</div>
		</div>
		<div class="row input-padding">
			<div class="col-md-2">
				<label class="input-label">Filtrar por:</label>
			</div>
			<div class="col-md-3">
				<button class="btn btn-lg btn-primary" id="day">Dia</button>
			</div>
			<div class="col-md-3">
				<button class="btn btn-lg btn-primary" id="month">Mes</button>
			</div>
			<div class="col-md-3">
				<button class="btn btn-lg btn-primary" id="year">Año</button>
			</div>

		</div>
	</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/report.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/canvasjs.min.js"></script>
<script
	src="///cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>

<%@ include file="../footer.jsp"%>