window.onload = function() {
	loadChart("day");
	$( "#startDate" ).datepicker();
	$( "#endDate" ).datepicker();
	
	$("#day").click(function(){
		loadChart("day");
	});
	$("#month").click(function(){
		loadChart("month");
	});
	$("#year").click(function(){
		loadChart("year");
	});
}

loadChart = function(dateType){
	var sDate = new Date( $("#startDate").datepicker("getDate") ).getTime();;
	var eDate = new Date( $("#endDate").datepicker("getDate")).getTime();
	console.log(sDate);
	$.getJSON("/bin/user/getreport?time="+ dateType+"&startDate="+sDate + "&endDate="+eDate, function(data){
		console.log(data.datagrams);
		var chart = new CanvasJS.Chart("chartContainer", {
			title : {
				text : "Tweets por fecha",
			},
			axisY : {
				title : "Cantidad"
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
				dataPoints : data.datagrams,
			} ]
		});

		chart.render();
	});
}