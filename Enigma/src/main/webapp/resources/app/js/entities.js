$('#form_master').submit(
		function(e) {

			e.preventDefault();

			var $form = $(this);

			// check if the input is valid
			var password = $("#md_password").val();
			var new_password = $("#md_new_password").val();
			var confirm_password = $("#md_confirm_password").val();

			if (new_password != confirm_password) {
				document.getElementById("md_confirm_password")
						.setCustomValidity("Passwords Don't Match");
				return;
			}

			$.ajax({
				type : 'PUT',
				url : 'http://localhost:8080/InventoryManagement/api/master',
				data : JSON.stringify({
					"parameters" : {
						"currentMasterPassword" : password,
						"newMasterPassword" : new_password
					}
				}),
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				success : function(response) {
					alert(response.responseCode.message);
					$("#md_password").val("");
					$("#md_new_password").val("");
					$("#md_confirm_password").val("");
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

$('#form_user_save')
		.submit(
				function(e) {
					e.preventDefault();

					var $form = $(this);

					// check if the input is valid
					var userId = $("#userId").val().trim();
					var userName = $("#userName").val().trim();
					var password = $("#user_password").val();

					if (userId.length < 4) {
						document.getElementById("userId").setCustomValidity(
								"User Id Must Have 4 character long");
						return;
					}
					if (userName.length < 6) {
						document.getElementById("userName").setCustomValidity(
								"User Name Must Have 6 character long");
						return;
					}
					if (password.length < 6) {
						document.getElementById("user_password")
								.setCustomValidity(
										"Passwords Must Have 6 character long");
						return;
					}

					$
							.ajax({
								type : 'POST',
								url : 'http://localhost:8080/InventoryManagement/api/user/saveUserInfo',
								data : JSON.stringify({
									"parameters" : {
										"userId" : userId,
										"password" : password,
										"userName" : userName,
										"opration" : "save"
									}
								}),
								dataType : 'json',
								contentType : 'application/json; charset=utf-8',
								success : function(response) {
									alert(response.responseCode.message);
									$("#userId").val("");
									$("#userName").val("");
									$("#user_password").val("");
								},
								error : function(xhr, status, error) {
									try {
										errorResponse = JSON
												.parse(xhr.responseText);
										alert(errorResponse.responseCode.message);
									} catch (e) {
										alert("some error occurred, please try later.");
									}
								}

							});
				});

$('#form_device_submit')
		.submit(
				function(e) {
					e.preventDefault();

					var $form = $(this);

					// check if the input is valid
					var deviceId = $("#deviceId").val().trim();
					var deviceName = $("#deviceName").val().trim();
					var manufacturer = $("#manufacturer").val();
					var OS = $("#OS").val();
					var OSVersion = $("#OSVersion").val();
					var yearOfManufacturing = $("#yearOfManufacturing").val();
					var timeoutPeriod = $("#timeoutPeriod").val();

					$
							.ajax({
								type : 'POST',
								url : 'http://localhost:8080/InventoryManagement/api/deviceIssue/saveDeviceInfo',
								data : JSON
										.stringify({
											"parameters" : {
												"deviceId" : deviceId,
												"deviceName" : deviceName,
												"manufacturer" : manufacturer,
												"oS" : OS,
												"osVersion" : OSVersion,
												"yearOfManufacturing" : yearOfManufacturing,
												 "timeoutPeriod" : timeoutPeriod,
												"opration" : "save"
											}
										}),
								dataType : 'json',
								contentType : 'application/json; charset=utf-8',
								success : function(response) {
									alert(response.responseCode.message);
									$("#deviceId").val("");
									$("#deviceName").val("")
									$("#manufacturer").val("");
									$("#OS").val("");
									$("#OSVersion").val("");
									$("#yearOfManufacturing").val("");
									$("#timeoutPeriod").val("");
								},
								error : function(xhr, status, error) {
									try {
										errorResponse = JSON
												.parse(xhr.responseText);
										alert(errorResponse.responseCode.message);
									} catch (e) {
										alert("some error occurred, please try later.");
									}
								}

							});
				});