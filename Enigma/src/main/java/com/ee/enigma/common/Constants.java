package com.ee.enigma.common;

public interface Constants {
	int CODE_SUCCESS = 200;
	int CODE_BAD_REQUEST = 400;
	int CODE_AUTHENTICATION_FAILD = 401;
	int CODE_NOT_FOUND = 404;
	int CODE_INTERNAL_SERVER_ERROR = 500;

	String ISSUED_BY_ADMIN = "Issued By Admin";
	String ISSUED_BY_SYSTEM = "Issued By System";
	String SUBMITTED_BY_ADMIN = "Submitted By Admin";
	String SUBMITTED_BY_SYSTEM = "Submitted By System";
	String SUBMITTED_BY_USER = "Submitted By User";

	String MESSAGE_SUCCESS = "Success";
	String LOGOUT_SUCCESS = "Successfully LogOut";
	String MESSAGE_BAD_REQUEST = "Bad Requeset";
	String MESSAGE_AUTHENTICATION_FAILD = "UnAuthorized";
	String MESSAGE_NOT_FOUND_DEVICE = "Device not registered";
	String MESSAGE_WRONG_PASSWORD = "current Password is wrong";
	String MESSAGE_INTERNAL_SERVER_ERROR = "Internal Sever Error";

	String USER_ACTIVITY_ID_NOT_FOUND = "Provided Session Token is not exist";
	String USER_ALREADY_LOGOUT = "User already logout for provided Session Token";
	String USER_ALREADY_AUTOMATIC_LOGOUT = "User automatic logout for provided Session Token";
	String USER_NOT_EXISTING = "User does not exists.";
	String USER_ALREADY_EXISTS = "User already exists.";

	// Device Issue Info
	String MESSAGE_DEVICE_ALREADY_ISSUED = "Device is already issued.";
	String MESSAGE_DEVICE_SUCCESSFULLY_ISSUED = "Device successfully issued to user.";
	String MESSAGE_DEVICE_ISSUED_TO_OTHER_USER = "Device is issued to other User.";
	String MESSAGE_DEVICE_ASSIGNED_TO_SAME_USER = "Device is already assigned to you.";
	String MESSAGE_DEVICE_SUBMITTED_TO_ADMIN_FOR_APPROVAL = "Device is submitted to admin, approval is pending.";
	String MESSAGE_DEVICE_ALREADY_SUBMITTED = "Device is already submitted.";
	String MESSAGE_DEVICE_SUBMITTED = "Device is submitted.";
	 String DEVICE_ALREADY_EXISTS = "Device already exists.";
	 String MESSAGE_DEVICE_WAS_NOT_ISSUED = "Device was not issued.";
	 String MESSAGE_DEVICE_NOT_AUTHORIZE_TO_SUBMIT = "You are not authorize to submit the device.";
	
	String MESSAGE_DEVICE_SUBMISSION_IS_REJECTED = "Device submition is rejected. Again issue to same user";
	
	String DEVICE_STATUS_AVAILABLE = "Available";
	String DEVICE_STATUS_PENDING = "Pending";
	String DEVICE_STATUS_ISSUED = "Issued";
	
	 String OPRATION_SAVE = "save";
	  String OPRATION_UPDATE = "update";

	String MESSAGE_SUCCESSFULLY_SAVE = "Successfully saved.";
	String MESSAGE_SUCCESSFULLY_UPDATED = "Successfully updated.";
	String MESSAGE_SUCCESSFULLY_DELETED = "Successfully deleted";
	String MESSAGE_DEVICE_IS_NOT_PENDING = "Device is not found or Device is not in pending status";
	String MESSAGE_PASSWORD_NOT_MATCH = "Wrong Password";
	
   String DEVICE_ISSUE = "issue";
   String DEVICE_SUBMIT = "submit";
   String DEVICE_ISSUE_ALL = "all";
   
   String RETURN_REPONSE_UPDATED = "updated";
   String RETURN_REPONSE_SAME = "same";
   String RETURN_NA = "NA";
   
   String REPORT_DEVIVE_TIMELINE = "device";
   String REPORT_USER_TIMELINE = "user";
}
