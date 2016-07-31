google.charts.load('current', {	'packages' : [ 'line','corechart', 'timeline' ]});

google.charts.setOnLoadCallback(drawIssueTrendChart);
google.charts.setOnLoadCallback(drawSubmitTrendChart);
google.charts.setOnLoadCallback(drawTopDevicesChart);

function drawIssueTrendChart() {

	var startDate;
	var endDate;
	var ReportData=[];
	$.ajax({
		type : 'GET',
		url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.DEVICE_ISSUE_TIMELINE+'?reportType=issue',
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			startDate = response.startDate;
			endDate = response.endDate;
			$.each(response.issueTrendLineData,function(k,v){
				ReportData.push([new Date(v.date),v.byAdmin,v.bySystem]);
			});
		}
	});
	
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
		legend: { position: 'bottom' },
		width : 930,
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
		url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.DEVICE_ISSUE_TIMELINE+'?reportType=submit',
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			startDate = response.startDate;
			endDate = response.endDate;
			$.each(response.issueTrendLineData,function(k,v){
				ReportData.push([new Date(v.date),v.byAdmin,v.bySystem,v.byUser]);
			});
		}
	});
	
	if(ReportData === []){
		return;
	}
	
	var data = new google.visualization.DataTable();
	data.addColumn('date', 'Date');
	data.addColumn('number', 'Submitted by Admin');
	data.addColumn('number', 'Submitted by System');
	data.addColumn('number', 'Submitted by User');

	data.addRows(ReportData);

	var options = {
		chart : {
			title : 'Devices Submit Trend in Transility',
			subtitle : 'from '+startDate+' to '+endDate
		},
		legend: { position: 'bottom' },
		width : 930,
		height : 600
	};

	var chart = new google.charts.Line(document.getElementById('device_submit_trend'));
	chart.draw(data, options);
}

function compareByCount(a,b){
	if (a.count < b.count)
	    return 1;
	  if (a.count > b.count)
	    return -1;
	  return 0;
}

function drawTopDevicesChart(){
	var dataArray=[];
	var ReportData=[["Device Name", "Count"]];
	$.ajax({
		type : 'GET',
		url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.TOP_DEVICES,
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			dataArray = response.result.topDeviceDtoList;
			if(dataArray){
				dataArray.sort(compareByCount);
			}
			$.each(dataArray,function(k,v){
				ReportData.push([v.deviceName,v.count]);
			});
		}
	});
	
	var data = google.visualization.arrayToDataTable(ReportData);	
	var view = new google.visualization.DataView(data);
    view.setColumns([0, 1,
                     { calc: "stringify",
                       sourceColumn: 1,
                       type: "string",
                       role: "annotation" }]);

    var options = {
      title: "Most Popular devices",
      width: 900,
      height: 400,
      bar: {groupWidth: "95%"},
      legend: { position: "none" },
    };
    var chart = new google.visualization.BarChart(document.getElementById("barchart_top5"));
    chart.draw(view, options);

}

var userList;
$("#tabUserReport").on("click",function(e){
	if(!userList){
		userList = getUserList();
		setAutocompleteList();
	}
	
});

var deviceList;
$("#tabDeviceReport").on("click",function(){
	if(!deviceList){
		deviceList = getDeviceList();
		setDeviceAutocompleteList();
	}
});

var getDeviceList = function(){
	var deviceList=[];
	$.ajax({
		type : 'GET',
		url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.DEVICE_DETAILS,
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response_status) {
			$.each(response_status, function(k, v) {
				deviceList.push({'label': v.deviceName, 'value':v.deviceId});
			});
		},
		error : function(xhr, status, error) {
			try {
				errorResponse = JSON.parse(xhr.responseText);
				alertBox(errorResponse.responseCode.message);
			} catch (e) {
				alertBox("some error occurred, please try later.");
			}finally{
				deviceList = null;
			}
		}
	});
	return deviceList;
}

var setAutocompleteList = function() {
	$("#userAutocomplete").autocomplete({
		minLength : 0,
		source : userList,
		focus : function(event, ui) {
			$("#project").val(ui.item.label);
			return false;
		},
		select : function(event, ui) {
			$("#userAutocomplete").attr("user-id",ui.item.value);
			$("#userAutocomplete").val(ui.item.label);
			return false;
		}
	});
};

var setDeviceAutocompleteList = function() {
	$("#deviceAutocomplete").autocomplete({
		minLength : 0,
		source : deviceList,
		focus : function(event, ui) {
			$("#project").val(ui.item.label);
			return false;
		},
		select : function(event, ui) {
			$("#deviceAutocomplete").attr("device-id",ui.item.value);
			$("#deviceAutocomplete").val(ui.item.label);
			return false;
		}
	});
};

$( function() {
    var dateFormat = "mm/dd/yy",
      from = $( "#from" )
        .datepicker({
          defaultDate: "+1w",
          changeMonth: true,
          numberOfMonths: 1,
          maxDate: new Date 
        })
        .on( "change", function() {
          to.datepicker( "option", "minDate", getDate( this ) );
        }),
      to = $( "#to" ).datepicker({
        defaultDate: "+1w",
        changeMonth: true,
        numberOfMonths: 1,
        maxDate: new Date 
      })
      .on( "change", function() {
        from.datepicker( "option", "maxDate", getDate( this ) );
      });
 
    function getDate( element ) {
      var date;
      try {
        date = $.datepicker.parseDate( dateFormat, element.value );
      } catch( error ) {
        date = null;
      }
 
      return date;
    }
  } );

$("#fetchUserReport").on("click",function(e){
	var userId = $("#userAutocomplete").attr("user-id");
	if(!userId){
		alertBox("Please select user from drop-down");
		return;
	}
});

$("#fetchDeviceReport").on("click",function(e){
	var deviceId = $("#deviceAutocomplete").attr("device-id");
	if(!deviceId){
		alertBox("Please select user from drop-down");
		return;
	}	
	google.charts.setOnLoadCallback(deviceTimelineReport);
	
});
	
function deviceTimelineReport(){
	var ReportData = [];
	var deviceId = $("#deviceAutocomplete").attr("device-id");
	var container = document.getElementById('device_timeline');
	var dateString = '&beginDate=19/07/2016&endDate=19/07/2016';
	$.ajax({
		type : 'GET',
		url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.DEVICE_TMELINE_URL+'?deviceId='+deviceId,
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
//			startDate = response.startDate;
//			endDate = response.endDate;
			$.each(response.deviceIssueDetails,function(k,v){
				$.each(v.userActivities, function(k1, v1){
					if(v1.inTime != "NA" && v1.outTime != "NA" && v1.inTime && v1.outTime){
						var start = new Date(v1.inTime);
						var adate = (start.getMonth()+1)+'/'+start.getDate();  
						ReportData.push([v1.userName+" - "+ adate, v1.useStatus, new Date(v1.inTime), new Date(v1.outTime)]);
					}
				});
	
			});
		}
	});
	
    var chart = new google.visualization.Timeline(container);
    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn({ type: 'string', id: 'Name' });
    dataTable.addColumn({ type: 'string', id: 'Status' });
    dataTable.addColumn({ type: 'date', id: 'Start' });
    dataTable.addColumn({ type: 'date', id: 'End' });
    dataTable.addRows(ReportData);

    var options = {
      timeline: { showRowLabels: true }
    };
    chart.draw(dataTable, options);
}

