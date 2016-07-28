var URL = {
	HOST_NAME : 'http://'+window.location.hostname+':'+window.location.port,
	APPLICATION_NAME : '/InventoryManagement',
	USER_CRUD_URL : '/api/user',
	DEVICE_CRUD_URL : '/api/deviceIssue/saveDeviceInfo',
	ISSUE_DEVICE : '/api/deviceIssue',
	SUBMIT_DEVICE : '/api/deviceIssue/submitDevice',
	DEVICES_AVAILABILITY : '/api/deviceIssue/deviceReportByAvailability',
	DEVICE_DETAILS : '/api/deviceIssue/devicesIssueReportByStatus',
	SAVEUSER_URL : '/api/user/saveUserInfo',
	SAVEDEVICE_URL : '/api/deviceIssue/saveDeviceInfo',
	DEVICE_ISSUE_TIMELINE : '/api/deviceIssue/deviceIssueTimeLineTrendReport',
	TOP_DEVICES : '/api/deviceReport/topDevices',
	MASTER_UPDATE_URL : '/api/master',
	APPROVE_DEVICE_URL : '/api/deviceIssue/approveDevice',
	PENDING_DEVICE_URL : '/api/deviceIssue/pendingDevicesReport',
}

var alertBox = function(msg){
	if(!msg){
		return;
	}
	$("#alertMessage").text(msg);
	$("#alertModal").modal({                    
	    "backdrop"  : "static",
	    "show"      : true                     
	  });
}


var getUserList = function() {
	var userList;
	$.ajax({
		type : 'GET',
		url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.USER_CRUD_URL,
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			userList = response.result.userList;
			userList = JSON.parse(JSON.stringify(userList).split('"userName":').join('"label":'));
			userList = JSON.parse(JSON.stringify(userList).split('"userId":').join('"value":'));
		},
		error : function(xhr, status, error) {
			try {
				errorResponse = JSON.parse(xhr.responseText);
				alertBox(errorResponse.responseCode.message);
			} catch (e) {
				alertBox("some error occurred, please try later.");
			}finally{
				userList = null;
			}
		}

	});	
	return userList;
}