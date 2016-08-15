var userId = $("#userIdA").text();

/**
 * fetching user name from server using userId.
 */
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

/**
 *  Validating and submitting update password request.
 */
$( "#form_update_user" ).submit(function( event ) {
	  event.preventDefault();

	  var password = $("#ud_password").val();
	  var newPassword = $("#ud_new_password").val();
	  var confirmPassword = $("#ud_confirm_password").val();
	  if(newPassword != confirmPassword){
		  document.getElementById("ud_confirm_password")
			.setCustomValidity("Confirm password must match new password.");
			return;
	  }
	  if(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,10}$/.test(newPassword) == false) {
			document.getElementById("ud_new_password")
			.setCustomValidity("Password must have 6 to 10 character with small, capital letter and a numric number");
		return;
	}
	
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

$("#update_password_btn").click(function(e) {
	var $myForm = $('#form_update_user');
	$myForm.find(':submit').click();
});