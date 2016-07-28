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
	if (a[2] < b[2])
	    return -1;
	  if (a[2] > b[2])
	    return 1;
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
			dataArray = response.result.topDeviceDto;
			if(dataArray){
				dataArray.sort(compareByCount);
			}
			$.each(dataArray,function(k,v){
				ReportData.push([v[1],v[2]]);
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

var setAutocompleteList = function() {
	$("#userAutocomplete").autocomplete({
		minLength : 0,
		source : userList,
		focus : function(event, ui) {
			$("#project").val(ui.item.label);
			return false;
		},
		select : function(event, ui) {
//			$("#issue_modal_issued_to").html(ui.item.label);
//			$("#issue_modal_user_id").html(ui.item.value);
			$("#userAutocomplete").val(ui.item.label);
			alertBox(ui.item.value);
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