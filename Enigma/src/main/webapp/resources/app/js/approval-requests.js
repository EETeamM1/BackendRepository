var responseData;
var requestCount="";

/**
 * Feching request data from server;
 */
function getApproveRequests(){
	$.ajax({
		type : 'GET',
		url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.PENDING_DEVICE_URL,
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			responseData = response 
			
			requestCount=responseData.length;
		}
	});
}
getApproveRequests();
$("#requestCount").text(requestCount || 0);
$("#pendingDevices").text(requestCount || 0);

var popOverSettings = {
	placement : 'bottom',
	container : 'body',
	html : true,
	content : function() {
		return abc();
	}
};
var abc = function(){
	content = "";
	$.each(responseData, function(k, v) {
		if(v.deviceAvailability == 'Pending'){
		content = content.concat('<div style="border: 1px solid #cdcdcd; padding: 5px; overflow: auto;">'+
				
		'<div class="col-sm-9 t2 n3">'+
			v.deviceName+'<br/>'+v.deviceId+'<br />'+v.userName+
		'</div>'+
		'<div class="col-sm-3">'+
			'<div class="col-sm-12 device_approve_btn" id="device_approve_accept" device-id="'+v.deviceId+'" user-id="'+v.userId+'">'+
				'<span class="glyphicon glyphicon-ok-circle" style="color: #41be47; font-size: 28px; margin: 2px;"></span>'+
			'</div>'+
			'<div class="col-sm-12 device_approve_btn" id="device_approve_reject" device-id="'+v.deviceId+'" user-id="'+v.userId+'">'+
				'<span class="glyphicon glyphicon-remove-circle" style="color: red; font-size: 28px; margin: 2px;"></span>'+
			'</div>'+
		'</div>'+
	'</div>');
		}
	});
	return '<div>'+content+'</div>';
}
if(requestCount){
	$("#ApprovalRequests").popover(popOverSettings);
}


$('body').on("click", ".device_approve_btn", function() {
	var deviceId = $(this).attr("device-id");
	var userId = $(this).attr("user-id");
	var isAdminApproved = false;
	var status;
	
	if(this.id=="device_approve_accept"){
		status = 'accept';
		isAdminApproved = true;
	}else if (this.id=="device_approve_reject"){
		status = 'reject';
		isAdminApproved = false;
	}
	

	$.ajax({
		type : 'PUT',
		url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.APPROVE_DEVICE_URL,
		data : JSON.stringify({
			"parameters" : {
				"deviceId" : deviceId,
				"isAdminApproved" : isAdminApproved
			}
		}),
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			$("#ApprovalRequests").popover('hide');
			if(alertBox(response.responseCode.message))
			window.location.reload();
		},
		error : function(xhr, status, error) {
			try {
				errorResponse = JSON.parse(xhr.responseText);
				alertBox(errorResponse.responseCode.message);
			} catch (e) {
				alertBox("some error occurred, please try later.");
			}
		}

	});
	
	//post approve/reject data to server 
	//delete this data from array.
	//hide data from popover
	//if popover gets empty then hide popover.
});