package com.ee.enigma.common;

public interface Constants {
	int CODE_SUCCESS=200;
	int CODE_BAD_REQUEST=400;
	int CODE_AUTHENTICATION_FAILD=401;
	int CODE_NOT_FOUND=404;
	
	String MESSAGE_SUCCESS = "Success";
	String LOGOUT_SUCCESS = "Successfully LogOut";
	String MESSAGE_BAD_REQUEST = "Bad Requeset";
	String MESSAGE_AUTHENTICATION_FAILD = "UnAuthorized";
	String MESSAGE_NOT_FOUND_DEVICE = "Device not registered";
	
	String USER_ACTIVITY_ID_NOT_FOUND = "Provided Session Token is not exist";
	String USER_ALREADY_LOGOUT = "User already logout for provided Session Token";
	String USER_ALREADY_AUTOMATIC_LOGOUT = "User automatic logout for provided Session Token";
	String USER_NOT_EXISTING = "User does not exist, please use valid user.";
	
	//Device Issue Info
	String MESSAGE_DEVICE_ALREADY_ISSUED = "Device is already issued.";
	String MESSAGE_DEVICE_SUCCESSFULLY_ISSUED = "Device successfully issued to user.";
	String MESSAGE_DEVICE_ISSUED_TO_OTHER_USER = "Device Issue to Other User.";
	 String MESSAGE_DEVICE_ASSIGNED_TO_SAME_USER = "Device is already assigned to you.";
	
	String DEVICE_INFO_ADMIN_AVAILABLE = "AV";
	String DEVICE_INFO_PENDING_WITH_ADMIN = "PD";
	String DEVICE_INFO_ISSUED_TO_USER = "IS";
}
