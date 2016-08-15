/**
 *  All URLs for ajax calls.
 */
var URL = {
	HOST_NAME : 'http://'+window.location.hostname+':'+window.location.port,
	APPLICATION_NAME : '/InventoryManagement',
	USER_CRUD_URL : '/api/user',
	DEVICE_CRUD_URL : '/api/deviceIssue/saveDeviceInfo',
	ISSUE_DEVICE : '/api/deviceIssue',
	SUBMIT_DEVICE : '/api/deviceIssue/submitDevice',
	DEVICES_AVAILABILITY : '/api/deviceIssue/deviceReportByAvailability',
	DEVICE_DETAILS : '/api/deviceIssue/devicesIssueReportByStatus',
	SAVEUSER_URL : '/api/user',
	SAVEDEVICE_URL : '/api/device',
	DEVICE_ISSUE_TIMELINE : '/api/deviceIssue/deviceIssueTimeLineTrendReport',
	TOP_DEVICES : '/api/deviceReport/topDevices',
	MASTER_UPDATE_URL : '/api/master',
	APPROVE_DEVICE_URL : '/api/deviceIssue/approveDevice',
	PENDING_DEVICE_URL : '/api/deviceIssue/pendingDevicesReport',
	DEVICE_TMELINE_URL : '/api/deviceIssue/deviceTimeLineReport',
	USER_TIMELINE_URL : '/api/deviceIssue/userTimeLineReport',
	USER_DETAILS : '/api/user',
	UPDATE_USER_PASSWORD : '/api/user/updatePassword'
}

/**
 *  Logic for alert box.
 */
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

/**
 *  fetching user list from server.
 */
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