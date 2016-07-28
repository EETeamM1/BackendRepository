var devicesList;
var userList;
var deviceIssueObj;

//show loading indicator before populating data.
$("#totalDevices").html('<i class="fa fa-refresh fa-spin" style="font-size:24px"></i>');
$("#availableDevices").html('<i class="fa fa-refresh fa-spin" style="font-size:24px"></i>');
$("#issuedDevices").html('<i class="fa fa-refresh fa-spin" style="font-size:24px"></i>');
$("#device_list").html('<div style="font-size:32px;text-align:center;">Loading Device List...<i class="fa fa-refresh fa-spin"></i></div>');

var deviceStatusviseCount = function(){
		
	$.ajax({
		type : 'GET',
		url : 'http://172.26.60.21:9000/InventoryManagement/api/deviceIssue/deviceReportByAvailability',
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			$("#totalDevices").text(response.totalDevices);
			$("#availableDevices").text(response.availableDevices);
			$("#issuedDevices").text(response.issuedDevices);
		},
		error : function(xhr, status, error) {
			try {
				var errorResponse = JSON.parse(xhr.responseText);
				alert(errorResponse.responseCode.message);
			} catch (e) {
				alert("some error occurred, please try later.");
			}
		}

	});
}
deviceStatusviseCount();

function compare(a,b) {
  if (a.status < b.status)
    return -1;
  if (a.status > b.status)
    return 1;
  return compareByName(a,b);
}

function compareByName(a,b){
	if (a.device_name.toLowerCase() < b.device_name.toLowerCase())
	    return -1;
	  if (a.device_name.toLowerCase() > b.device_name.toLowerCase())
	    return 1;
	  return 0;
}
function compareForUser(a,b){
	if(a.hideIssued=="" && b.hideIssued!=""){
		return -1
	}else if(a.hideIssued!="" && b.hideIssued==""){
		return 1;
	}else{
		return compare(a,b);
	}
}


var DeviceRenderingData = [];
var viewRenderer = function(){
	var isAdmin = $("#isAdmin").text();
	var temp = $('#deviceStatusTemplate').text().replace(/@/g, '$');
	$('#deviceStatusTemplate').text(temp);
	$("#device_list").html('');
	DeviceRenderingData.sort(compareByName);
	if(isAdmin){
		DeviceRenderingData.sort(compare);
	}else{
		DeviceRenderingData.sort(compareForUser);
	}
	$("#deviceStatusTemplate").tmpl(DeviceRenderingData).appendTo("#device_list");
	$(".issue_device_modal").click(function(e) {
		e.preventDefault();
		issueDeviceModel(this);
	});
	$(".submit_device").on('click',function(e){
		e.preventDefault();
		submitDevice(this);
	});
	$(".list_device_name").click(function(e) {
		e.preventDefault();
		deviceDetails(this);
	});
}

$.ajax({
			type : 'GET',
			url : 'http://172.26.60.21:9000/InventoryManagement/api/deviceIssue/devicesIssueReportByStatus',
			dataType : 'json',
			async : false,
			contentType : 'application/json; charset=utf-8',
			success : function(response_status) {
				devicesList = response_status;
				var isAdmin = $("#isAdmin").text();
				var loggedUserId = $("#loggedInUserId").text();
				$.each(response_status, function(key, value) {
					var osType = 'mobile';
					switch (value.os.toLowerCase()) {
					case 'android':
						osType = 'android';
						break;
					case 'ios':
						osType = 'apple';
						break;
					default:
						osType = 'mobile';
					}
					var hideAvailable = "hide";
					var userId = "";
					var userName = "";
					var searchUser = "";
					var hideIssued = "hide";
					var hideIssuedDetails = "hide";
					if(value.deviceAvailability == "Available" && isAdmin){
						hideAvailable = "";
					}
					if(value.deviceAvailability == "Issued"){
							userId = value.userId;
							userName = value.userName;
							searchUser = value.userName.toLowerCase();
							hideIssuedDetails=""
						if(isAdmin || (loggedUserId == value.userId)){
							hideIssued="";
						}
					}

					DeviceRenderingData.push({
						'img' : '' + osType + '',
						'searchKeywords' : '' + value.os.toLowerCase() + ' '+ value.deviceName.toLowerCase()+' '+value.deviceAvailability.toLowerCase()+' '+searchUser,
						'deviceId' : value.deviceId,
						'device_name' : value.deviceName,
						'status' : value.deviceAvailability,
						'OSVersion' : value.osversion,
						'user_id': userId,
						'user_name' : userName,
						'hideAvailable' : hideAvailable,
						'hideIssuedDetails' : hideIssuedDetails,
						'hideIssued' : hideIssued						
					});
				});
				
				viewRenderer();
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


$("#search_box").keyup(function() {
	var searchText ="";
	if($(".apple_icon").hasClass( "active" )){
		searchText = searchText.concat(" iOS ");
	}
	if($(".android_icon").hasClass( "active" )){
		searchText = searchText.concat(" android ");
	}
	if($(".windows_icon").hasClass( "active" )){
		searchText = searchText.concat(" windows ");
	}
	searchText = searchText.concat($("#search_box").val().trim());
	searchText = searchText.toLowerCase();
	
	if (searchText.length > 0) { 	
		var divToShow = "div.device_block";
		// tokenize searchText on space
		var searchArray = searchText.split(" ");
		searchArray.forEach(function(key) {
			if (key) {
				divToShow = divToShow.concat(":contains(" + key + ")");
			}
		});
		$(".device_block").hide();
		$(divToShow).show();
	}else{
		$(".device_block").show();
	}
});

$(".apple_icon").click(function(){
	$( this ).toggleClass("active");
	if ($(".windows_icon").hasClass("active")) {
		$(".windows_icon").toggleClass("active")
	}
	if ($(".android_icon").hasClass("active")) {
		$(".android_icon").toggleClass("active")
	}
	$("#search_box").trigger("keyup");
});

$(".android_icon").click(function(){
	$( this ).toggleClass("active");
	if ($(".windows_icon").hasClass("active")) {
		$(".windows_icon").toggleClass("active")
	}
	if ($(".apple_icon").hasClass("active")) {
		$(".apple_icon").toggleClass("active")
	}
	$("#search_box").trigger("keyup");
});
$(".windows_icon").click(function(){
	$( this ).toggleClass("active");
	if ($(".apple_icon").hasClass("active")) {
		$(".apple_icon").toggleClass("active")
	}
	if ($(".android_icon").hasClass("active")) {
		$(".android_icon").toggleClass("active")
	}
	$("#search_box").trigger("keyup");
});


var deviceDetails = function(self){
	var id = self.id;
	$.each(devicesList, function(i, v) {
		if (v.deviceId == id) {
			$("#device_detail_modal_name").html(v.deviceName);
			$("#device_detail_modal_id").html(v.deviceId);
			$("#device_detail_modal_manufacturer").html(v.manufacturer);
			$("#device_detail_modal_os").html(v.os);
			$("#device_detail_modal_os_version").html(v.osversion);
			$("#deviceDetailModal").modal('show');
		}
	});
}

var issueDeviceModel = function(self){
	deviceIssueObj = self;
	if (!userList) {
		getUserList();
		setAutocompleteList();
	}
	var id = $(self).attr("device-id");
	var deviceName = $(self).attr("device-name");
	$("#issue_modal_name").html(deviceName);
	$("#issue_modal_device_id").html(id);
	$("#deviceIssueModal").modal('show');
}

var getUserList = function() {
	$.ajax({
		type : 'GET',
		url : 'http://172.26.60.21:9000/InventoryManagement/api/user',
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			userList = response.result.userList;
			userList = JSON.parse(JSON.stringify(userList).split('"userName":').join('"label":'));
			userList = JSON.parse(JSON.stringify(userList).split('"userId":').join('"value":'));
			console.log(userList);
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
			$("#userAutocomplete").val("");
			$("#issue_modal_issued_to").html(ui.item.label);
			$("#issue_modal_user_id").html(ui.item.value);
			$("#issue_btn").removeClass("hide");
			return false;
		}
	});
};

$('#deviceIssueModal').on('hidden.bs.modal', function () {
	if(!$("#issue_btn").hasClass("hide")){
		$("#issue_btn").addClass("hide");
		$("#issue_modal_issued_to").html("");
		$("#issue_modal_user_id").html("");
		$("#issue_modal_device_id").html("");
	}
})

var issueDevice = function(e){
	e.preventDefault();
	var userId = $("#issue_modal_user_id").html();
	var deviceId = $("#issue_modal_device_id").html();
	
	$.ajax({
		type : 'POST',
		url : 'http://172.26.60.21:9000/InventoryManagement/api/deviceIssue/',
		data : JSON.stringify({
			  "parameters": {
			      "userId": userId,
			      "deviceId": deviceId,
			      "byAdmin": true
			  }
		}),
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {			
			$('#deviceIssueModal').modal('hide');
			$.each(DeviceRenderingData, function(i, v) {
				if (v.deviceId == deviceId) {
					v.status = "Issued";
					v.user_id =  userId;
					v.user_name = $("#issue_modal_issued_to").html();
					v.hideAvailable = "hide";
					v.hideIssuedDetails = "";
					v.hideIssued = "";				
				}
			});
			alert(response.responseCode.message);
			deviceStatusviseCount();
			viewRenderer();
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
}
$("#issue_btn").click(function(e) {
	issueDevice(e);
});
var submitDevice = function(self){
	var userId =  $(self).attr("user-id");
	var deviceId =  $(self).attr("device-id");
	
	$('<div></div>').appendTo('body')
    .html('<div><h6>Are you sure?</h6></div>')
    .dialog({
        modal: true,
        title: 'Delete message',
        zIndex: 10000,
        autoOpen: true,
        width: 'auto',
        resizable: false,
        buttons: {
            Yes: function () {
                $(this).dialog("close");
                var byAdmin=false;
                if($("#isAdmin").text()){
                	byAdmin = true;
                }
            	$.ajax({
            		type : 'POST',
            		url : 'http://172.26.60.21:9000/InventoryManagement/api/deviceIssue/submitDevice',
            		data : JSON.stringify({
            			  "parameters": {
            			      "userId": userId,
            			      "deviceId": deviceId,
            			      "byAdmin": byAdmin
            			  }
            		}),
            		dataType : 'json',
            		async : false,
            		contentType : 'application/json; charset=utf-8',
            		success : function(response) {		
            			$.each(DeviceRenderingData, function(i, v) {
            				if (v.deviceId == deviceId) {
            					v.status = "Available";
            					v.user_id =  "";
            					v.user_name = "";
            					if($("#isAdmin").text()){
            						v.hideAvailable = "";
            					}
            					v.hideIssuedDetails = "hide";
            					v.hideIssued = "hide";				
            				}
            			});
            			alert(response.responseCode.message);
            			deviceStatusviseCount();
            			viewRenderer();
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
            },
            No: function () {
                $(this).dialog("close");
            }
        },
        close: function (event, ui) {
            $(this).remove();
        }
    });
	
}
