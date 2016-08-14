var userId = $("#userIdA").text();

function getUserDetails(){
	$.ajax({
		type : 'GET',
		url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.USER_DETAILS+"/"+userId,
		dataType : 'json',
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			$("#userId").text(userId);
			$("#userName").text(response.result.user.userName);		
		},
		error : function(xhr, status, error) {
			try {
				var errorResponse = JSON.parse(xhr.responseText);
				alertBox(errorResponse.responseCode.message);
			} catch (e) {
				alertBox("some error occurred, please try later.");
			}
		}

	});
}
getUserDetails();

$('#password').on('hidden.bs.modal', function () {
	$("#ud_password").val("");
	$("#ud_new_password").val("");
	$("#ud_confirm_password").val("");
});
$( "#form_update_user" ).submit(function( event ) {
	  event.preventDefault();
});

$("#update_password_btn").click(function(e) {
	var $myForm = $('#form_update_user');
	if (!$myForm[0].checkValidity()) {
		$myForm.find(':submit').click();
		return;
	}
	
	var password = $("#ud_password").val();	
	var newPassword = $("#ud_new_password").val();
	$('#password').modal('hide');
	$.ajax({
		type : 'POST',
		url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.UPDATE_USER_PASSWORD,
		dataType : 'json',
		data : JSON.stringify({
			  "parameters": {
			      "userId": userId,
			      "password": password,
			      "newPassword": newPassword
			  }
		}),
		async : false,
		contentType : 'application/json; charset=utf-8',
		success : function(response) {
			alertBox(response.responseCode.message);
		},
		error : function(xhr, status, error) {
			try {
				var errorResponse = JSON.parse(xhr.responseText);
				alertBox(errorResponse.responseCode.message);
			} catch (e) {
				alertBox("some error occurred, please try later.");
			}
		}

	});
});