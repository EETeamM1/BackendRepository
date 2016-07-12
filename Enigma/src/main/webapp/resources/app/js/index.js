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
			async : false,
			contentType : 'application/json; charset=utf-8',
			success : function(response_status) {
				var responseData = [];
				$.each(response_status, function(key, value) {
					var osType = 'mobile';
					switch (value.os) {
					case 'android':
						osType = 'android';
						break;
					case 'iOS':
						osType = 'apple';
						break;
					default:
						osType = 'mobile';
					}

					responseData.push({
						'img' : '' + osType + '',
						'searchKeywords' : '' + value.os.toLowerCase() + ' '+ value.deviceName.toLowerCase(),
						'device_name' : value.deviceName,
						'status' : 'Available',
						'button' : 'issue'
					});
				});
				$("#deviceStatusTemplate").tmpl(responseData).appendTo(
						"#device_list");
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
		$(".device_block").hide();
		$("div.device_block:contains("+searchText+")").show();
	}else{
		$(".device_block").show();
	}
});

$(".apple_icon").click(function(){
	$( this ).toggleClass("active");
	$("#search_box").trigger("keyup");
});
$(".android_icon").click(function(){
	$( this ).toggleClass("active");
	$("#search_box").trigger("keyup");
});
$(".windows_icon").click(function(){
	$( this ).toggleClass("active");
	$("#search_box").trigger("keyup");
});
