package com.ee.enigma.dto;

import java.util.Date;

public class DeviceReportDto {

	Date loginTIme;
	Date logOutTime;
	String userId;
	String userName;
	public Date getLoginTIme() {
		return loginTIme;
	}
	
	public void setLoginTIme(Date loginTIme) {
		this.loginTIme = loginTIme;
	}
	
	public Date getLogOutTime() {
		return logOutTime;
	}
	
	public void setLogOutTime(Date logOutTime) {
		this.logOutTime = logOutTime;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "DeviceReportDto [loginTIme=" + loginTIme + ", logOutTime=" + logOutTime + ", userId=" + userId
				+ ", userName=" + userName + "]";
	}
	
}
