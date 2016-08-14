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
				url : 'URL.HOST_NAME+URL.APPLICATION_NAME+URL.MASTER_UPDATE_URL',
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

					if (userId.length < 4 || userId.length > 20) {
						document.getElementById("userId").setCustomValidity(
								"User Id Must Have 4 to 20 character long length");
						return;
					}
					if (userName.length < 4 || userName.length > 50) {
						document.getElementById("userName").setCustomValidity(
								"User Name Must Have 4 to 50 character long legth");
						return;
					}
					if(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,10}$/.test(password) == false) {
						document.getElementById("user_password")
						.setCustomValidity("Password must have 6 to 10 character with small, capital letter and a numric number");
						return;
					}

					$
							.ajax({
								type : 'POST',
								url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.SAVEUSER_URL,
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
					

					if (deviceId.length < 10 || deviceId.length > 20) {
						document.getElementById("deviceName").setCustomValidity(
								"deviceId Name Must Have 10 to 20 character long lenght");
						return;
					}
					if (/^[a-zA-Z0-9]*$/.test(deviceId) == false) {
						document.getElementById("deviceId").setCustomValidity(
								"Device Id Must Have only 0-9 digit and a-z character");
						return;
					}
					if (deviceName.length < 6 || deviceName.length > 30) {
						document.getElementById("deviceName").setCustomValidity(
								"Device Name Must Have 4 to 30 character long lenght");
						return;
					}
					if (manufacturer.length < 4 || manufacturer.length > 20) {
						document.getElementById("manufacturer").setCustomValidity(
								"Manufacturer Name Must Have 4 to 20 character long lenght");
						return;
					}
					if (OSVersion.length < 1 || OSVersion.length > 30) {
						document.getElementById("OSVersion").setCustomValidity(
								"OSVersion Must Have 1 to 10 character long lenght");
						return;
					}
					if (yearOfManufacturing.length != 4) {
						document.getElementById("yearOfManufacturing").setCustomValidity(
								"yearOfManufacturing Must Have 4 digit numeric year");
						return;
					}
					if (/^[0-9]*$/.test(yearOfManufacturing) == false) {
						document.getElementById("yearOfManufacturing").setCustomValidity(
								"yearOfManufacturing must have 4 digit numeric value");
						return;
					}

					$
							.ajax({
								type : 'POST',
								url : URL.HOST_NAME+URL.APPLICATION_NAME+URL.SAVEDEVICE_URL,
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