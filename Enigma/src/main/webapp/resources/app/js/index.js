var devicesList;
var userList;
var deviceIssueObj;
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

$.ajax({
			type : 'GET',
			url : 'http://172.26.60.21:9000/InventoryManagement/api/deviceIssue/deviceReportByStatus',
			dataType : 'json',
			async : false,
			contentType : 'application/json; charset=utf-8',
			success : function(response_status) {
				var responseData = [];
				devicesList = response_status;
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
					if(value.deviceAvailability == "Available"){
						hideAvailable = "";
					}
					if(value.deviceAvailability == "Issued"){
						userId = value.userId;
						userName = value.userName;
						searchUser = value.userName.toLowerCase();
						hideIssued = "";
					}

					responseData.push({
						'img' : '' + osType + '',
						'searchKeywords' : '' + value.os.toLowerCase() + ' '+ value.deviceName.toLowerCase()+' '+value.deviceAvailability.toLowerCase()+' '+searchUser,
						'deviceId' : value.deviceId,
						'device_name' : value.deviceName,
						'status' : value.deviceAvailability,
						'user_id': userId,
						'user_name' : userName,
						'hideAvailable' : hideAvailable,
						'hideIssued' : hideIssued						
					});
				});
				var temp = $('#deviceStatusTemplate').text().replace(/@/g, '$');
				var template = $('#deviceStatusTemplate').text(temp);
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

$(".list_device_name").click(function(e) {
	e.preventDefault();
	var id = this.id;
	console.log(devicesList);
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
});

$(".issue_device_modal").click(function(e) {
	e.preventDefault();
	deviceIssueObj = this;
	if (!userList) {
		getUserList();
		setAutocompleteList();
	}
	var id = $(this).attr("device-id");
	var deviceName = $(this).attr("device-name");
	$("#issue_modal_name").html(deviceName);
	$("#issue_modal_device_id").html(id);
	$("#deviceIssueModal").modal('show');
});

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

$("#issue_btn").click(function(e) {
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
			$(deviceIssueObj).addClass("hide");
			$(deviceIssueObj).siblings().removeClass("hide");
			$(deviceIssueObj).siblings().attr("user-id",userId);
			alert(response.responseCode.message);
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
});

$(".submit_device").click(function(e){
	e.preventDefault();
	var self = this;
	var userId =  $(this).attr("user-id");
	var deviceId =  $(this).attr("device-id");
	
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
            	$.ajax({
            		type : 'POST',
            		url : 'http://172.26.60.21:9000/InventoryManagement/api/deviceIssue/submitDevice',
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
            			$(self).addClass("hide");
            			$(self).siblings().removeClass("hide");            			
            			alert(response.responseCode.message);
            			
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
	
});