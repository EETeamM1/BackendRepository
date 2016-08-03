google.charts.load('current', {	'packages' : [ 'line','corechart', 'timeline' ]});

google.charts.setOnLoadCallback(drawIssueTrendChart);
google.charts.setOnLoadCallback(drawSubmitTrendChart);
google.charts.setOnLoadCallback(drawTopDevicesChart);

$("#barchart_top5").html('<div style="font-size:32px;text-align:center; height:400px;"><i class="fa fa-refresh fa-spin"></i> Loading...</div>');
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
}

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
	google.charts.setOnLoadCallback(userTimelineReport);
});

$("#fetchDeviceReport").on("click",function(e){
	var deviceId = $("#deviceAutocomplete").attr("device-id");
	if(!deviceId){
		alertBox("Please select device from drop-down");
		return;
	}	
	google.charts.setOnLoadCallback(deviceTimelineReport);
	
});
	
function deviceTimelineReport(){
	var ReportData = [];
	var deviceId = $("#deviceAutocomplete").attr("device-id");
	var container = document.getElementById('device_timeline');
	var dateString = getDateRange('device');
	$.ajax({
		type : 'GET',
		url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.DEVICE_TMELINE_URL+'?deviceId='+deviceId+dateString,
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
						var end = new Date(v1.outTime);
						var dateArray = getDates(start, end);
						var arrayLength = dateArray.length;
						for(var i=0; i<arrayLength;i++){
							var adate = (new Date(dateArray[i]).getMonth()+1)+'/'+new Date(dateArray[i]).getDate();  
							var startLimit = new Date(0,0,0,0,0,0);
							var endLimit = new Date(0,0,0,23,59,59);
							if(i==0){
								startLimit = new Date(0,0,0,start.getHours(),start.getMinutes(),start.getSeconds());
							}
							if(i==(arrayLength-1)){
								endLimit = new Date(0,0,0,end.getHours(),end.getMinutes(),end.getSeconds());
							}
							ReportData.push([v1.userName+" - "+ adate, v1.useStatus, startLimit, endLimit]);
						}
						
					}
				});
	
			});
		}
	});
	
	if(ReportData.length == 0){
		$("#device_timeline").html("<center><span class='t1'>No Data Found</span></center>");
		return;
	}
	
	var rowHeight = 41;
    var chartHeight = (ReportData.length) * rowHeight;

    var chart = new google.visualization.Timeline(container);
    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn({ type: 'string', id: 'Name' });
    dataTable.addColumn({ type: 'string', id: 'Status' });
    dataTable.addColumn({ type: 'date', id: 'Start' });
    dataTable.addColumn({ type: 'date', id: 'End' });
    dataTable.addRows(ReportData);

    var options = {
      timeline: { showRowLabels: true },
      height: chartHeight
    };
    chart.draw(dataTable, options);
}

function userTimelineReport(){
	var ReportData = [];
	var userId = $("#userAutocomplete").attr("user-id");
	var container = document.getElementById('user_timeline');
	var dateString = getDateRange('user');
	$.ajax({
		type : 'GET',
		url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.USER_TIMELINE_URL+'?userId='+userId+dateString,
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
						var end = new Date(v1.outTime);
						var dateArray = getDates(start, end);
						var arrayLength = dateArray.length;
						for(var i=0; i<arrayLength;i++){
							var adate = (new Date(dateArray[i]).getMonth()+1)+'/'+new Date(dateArray[i]).getDate();  
							var startLimit = new Date(0,0,0,0,0,0);
							var endLimit = new Date(0,0,0,23,59,59);
							if(i==0){
								startLimit = new Date(0,0,0,start.getHours(),start.getMinutes(),start.getSeconds());
							}
							if(i==(arrayLength-1)){
								endLimit = new Date(0,0,0,end.getHours(),end.getMinutes(),end.getSeconds());
							}
							ReportData.push([v1.deviceName+" - "+ adate, v1.useStatus, startLimit, endLimit]);
						}
						
					}
				});
				
			});
		}
	});

	if(ReportData.length == 0){
		$("#user_timeline").html("<center><span class='t1'>No Data Found</span></center>");
		return;
	}
	
	var rowHeight = 41;
    var chartHeight = (ReportData.length) * rowHeight;

    var chart = new google.visualization.Timeline(container);
    var dataTable = new google.visualization.DataTable();
    dataTable.addColumn({ type: 'string', id: 'Name' });
    dataTable.addColumn({ type: 'string', id: 'Status' });
    dataTable.addColumn({ type: 'date', id: 'Start' });
    dataTable.addColumn({ type: 'date', id: 'End' });
    dataTable.addRows(ReportData);

    var options = {
      timeline: { showRowLabels: true },
      height: chartHeight
    };
    chart.draw(dataTable, options);
}

Date.prototype.addDays = function(days) {
    var dat = new Date(this.valueOf())
    dat.setDate(dat.getDate() + days);
    return dat;
}

function getDates(startDate, stopDate) {
   var dateArray = new Array();
   startDate = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate(), 0, 0, 0);
   stopDate = new Date(stopDate.getFullYear(), stopDate.getMonth(), stopDate.getDate(), 0, 0, 0);
   var currentDate = startDate;
   while (currentDate <= stopDate) {
     dateArray.push(currentDate);
     currentDate = currentDate.addDays(1);
   }   
   return dateArray;
 }

$(".calendar-range").on("click",function(e){
	$(".calendar-range").removeClass("active");
	$(this).addClass("active");
	if($("#deviceAutocomplete").attr("device-id")){
		$("#fetchDeviceReport").trigger("click");
	}	
});

$("#deviceAutocomplete").on("keyup",function(e){
	$("#deviceAutocomplete").attr("device-id","");
});

$(".calendar-range-user").on("click",function(e){
	$(".calendar-range-user").removeClass("active");
	$(this).addClass("active");
	if($("#userAutocomplete").attr("user-id")){
		$("#fetchUserReport").trigger("click");
	}	
});

$("#userAutocomplete").on("keyup",function(e){
	$("#userAutocomplete").attr("user-id","");
});

$(".calendar-icon-device").on("click",function(e){
	$("#device_timeline_range").toggleClass("hide");
	if($("#device_timeline_range").hasClass("hide")){
		$(".calendar-range").removeClass("active");
	}
});
$(".calendar-icon-user").on("click",function(e){
	$("#user_timeline_range").toggleClass("hide");
	if($("#user_timeline_range").hasClass("hide")){
		$(".calendar-range-user").removeClass("active");
	}
});

function getDateRange(reportType){
	var elements ;
	var dateRange = "";
	if(reportType=='device'){
		elements = $(".calendar-range")
	}else if(reportType=='user'){
		elements = $(".calendar-range-user")
	}
	if($("#device_timeline_range").hasClass("hide") && $("#user_timeline_range").hasClass("hide")){
		return dateRange;
	}
	for(i=0;i<elements.length;i++){
		if((elements[i].className).includes("active")){
			var currentDate = new Date();
			var endDate = new Date();
			switch(elements[i].innerHTML){
			case '1d':
				break;
			case '1w':
				currentDate.setDate(currentDate.getDate() - 7);
				break;
			case '1m':
				currentDate.setMonth(currentDate.getMonth() - 1);
				break;
			case '3m':
				currentDate.setMonth(currentDate.getMonth() - 3);
				break;
			default :
				currentDate = null;
				endDate = null;
				break;
			}
			if(currentDate && endDate){
				dateRange = '&beginDate='+currentDate.getDate()+"/"+(currentDate.getMonth()+1)+"/"+currentDate.getFullYear()+"&endDate="+endDate.getDate()+"/"+(endDate.getMonth()+1)+"/"+endDate.getFullYear()
			}
		}
	}
	return dateRange;
}
