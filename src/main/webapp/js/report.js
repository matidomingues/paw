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
	var context = "";
	if(window.location.pathname.split("/")[1] != "bin"){
		context = window.location.pathname.split("/")[1];
	}
	$.getJSON("/"+ context + "bin/user/getreport?time="+ dateType+"&startDate="+sDate + "&endDate="+eDate, function(data){
		console.log(data.datagrams);
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
				dataPoints : data.datagrams,
			} ]
		});

		chart.render();
	});
}