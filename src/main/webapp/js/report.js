window.onload = function() {
	$( ".startDate" ).datepicker();
	$( ".endDate" ).datepicker();
	loadChart();

}

loadChart = function(){
		console.log(ajaxString);
		ajaxString = JSON.parse(ajaxString);
		var chart = new CanvasJS.Chart("chartContainer", {
			title : {
				text : "Tweets por fecha",
			},
			axisY : {
				title : "Cantidad",
				minimum: 0
			},
			axisX : {
				title : "Fecha"
			},
			legend : {
				verticalAlign : "bottom",
				horizontalAlign : "center"
			},
			theme : "theme2",
			data : [

			{
				dataPoints : ajaxString.datagrams,
			} ]
		});

		chart.render();
}