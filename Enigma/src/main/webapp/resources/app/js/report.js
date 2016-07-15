google.charts.load('current', {	'packages' : [ 'line' ]});
google.charts.setOnLoadCallback(drawIssueTrendChart);
google.charts.setOnLoadCallback(drawSubmitTrendChart);

function drawIssueTrendChart() {

	var startDate;
	var endDate;
	var ReportData=[];
	$.ajax({
		type : 'GET',
		url : 'http://172.26.60.21:9000/InventoryManagement/api/deviceIssue/deviceIssueTimeLineTrendReport?reportType=issue',
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			startDate = response.startDate;
			endDate = response.endDate;
			$.each(response.issueTrendLineData,function(k,v){
				ReportData.push([new Date(v.date),v.issedByAdmin,v.issedBySystem]);
			});
		}
	});
	
	console.log(ReportData);
	
	var data = new google.visualization.DataTable();
	data.addColumn('date', 'Date');
	data.addColumn('number', 'Devices Issed by Admin');
	data.addColumn('number', 'Devices self/user Issued');

	data.addRows(ReportData);

	var options = {
		chart : {
			title : 'Devices Issue Trend in Transility',
			subtitle : 'from '+startDate+' to '+endDate
		},
		width : 900,
		height : 500
	};

	var chart = new google.charts.Line(document.getElementById('device_issue_trend'));
	chart.draw(data, options);
}

function drawSubmitTrendChart() {

	var startDate;
	var endDate;
	var ReportData=[];
	$.ajax({
		type : 'GET',
		url : 'http://172.26.60.21:9000/InventoryManagement/api/deviceIssue/deviceIssueTimeLineTrendReport?reportType=submit',
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			startDate = response.startDate;
			endDate = response.endDate;
			$.each(response.issueTrendLineData,function(k,v){
				ReportData.push([new Date(v.date),v.issedByAdmin,v.issedBySystem]);
			});
		}
	});
	
	console.log(ReportData);
	
	if(ReportData == []){
		alert("asd");
		return;
	}
	
	var data = new google.visualization.DataTable();
	data.addColumn('date', 'Date');
	data.addColumn('number', 'Submitted by Admin');
	data.addColumn('number', 'Submitted by User');

	data.addRows(ReportData);

	var options = {
		chart : {
			title : 'Devices Submit Trend in Transility',
			subtitle : 'from '+startDate+' to '+endDate
		},
		width : 900,
		height : 500
	};

	var chart = new google.charts.Line(document.getElementById('device_submit_trend'));
	chart.draw(data, options);
}