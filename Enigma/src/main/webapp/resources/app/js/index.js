
$.ajax({
	type : 'GET',
	url : 'http://172.26.60.21:9000/InventoryManagement/api/deviceIssue/deviceReportByAvailability',
	dataType : 'json',
	contentType : 'application/json; charset=utf-8',
	success : function(response) {	
		$("#totalDevices").text(response.totalDevices);
		$("#availableDevices").text(response.availableDevices);
		$("#issuedDevices").text(response.issuedDevices);
	},
	error : function(xhr, status, error) {
		try {
			errorResponse = JSON.parse(xhr.responseText);
			alert(errorResponse.responseCode.message);
		} catch (e) {
			alert("some error occurred, please try later.");
		}
	}

});



$.ajax({
	type : 'GET',
	url : 'http://172.26.60.21:9000/InventoryManagement/api/deviceIssue/deviceReportByStatus',
	dataType : 'json',
	contentType : 'application/json; charset=utf-8',
	success : function(response){
		var responseData = [];
		$.each(response, function(key,value) {
			responseData.push({
				'img' : value.os,
				'device_name' : value.deviceName,
				'status' : 'Available',
				'button'  : 'issue'
			});
		}); 
		$("#deviceStatusTemplate").tmpl(responseData).appendTo("#device_list");
	},
	error : function(xhr, status, error) {
		try {
			errorResponse = JSON.parse(xhr.responseText);
			alert(errorResponse.responseCode.message);
		} catch (e) {
			alert("some error occurred, please try later.");
		}
	}

});
