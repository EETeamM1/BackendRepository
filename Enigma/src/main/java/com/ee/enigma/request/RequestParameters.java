package com.ee.enigma.request;

import java.sql.Time;

public class RequestParameters {
	private String userId;
	private String password;
	private String deviceId;
	private String osVersion;
	private float latitude;
	private float longitude;
	private String sessionToken;
	private Boolean byAdmin;
	private String beginDate;
	private String endDate;
	private String userName;
	private String opration;

	private String deviceName;
	private boolean isAdminApproved;
	private String manufacturer;
	private String oS;
	private Time timeoutPeriod;
	private String yearOfManufacturing;
	private String currentMasterPassword;
	private String newMasterPassword;
	private String deviceStatus;
	private String issueType;
	
	private String deviceToken;

	public String getDeviceToken() {
		return deviceToken;
	}
	

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	

	public void setAdminApproved(boolean isAdminApproved) {
		this.isAdminApproved = isAdminApproved;
	}
	

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getCurrentMasterPassword() {
		return currentMasterPassword;
	}

	public void setCurrentMasterPassword(String currentMasterPassword) {
		this.currentMasterPassword = currentMasterPassword;
	}

	public String getNewMasterPassword() {
		return newMasterPassword;
	}

	public void setNewMasterPassword(String newMasterPassword) {
		this.newMasterPassword = newMasterPassword;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public Boolean getByAdmin() {
		return byAdmin;
	}

	public void setByAdmin(Boolean byAdmin) {
		this.byAdmin = byAdmin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOpration() {
		return opration;
	}

	public void setOpration(String opration) {
		this.opration = opration;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public boolean getIsAdminApproved() {
		return isAdminApproved;
	}

	public void setIsAdminApproved(boolean isAdminApproved) {
		this.isAdminApproved = isAdminApproved;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getoS() {
		return oS;
	}

	public void setoS(String oS) {
		this.oS = oS;
	}

	public Time getTimeoutPeriod() {
		return timeoutPeriod;
	}

	public void setTimeoutPeriod(Time timeoutPeriod) {
		this.timeoutPeriod = timeoutPeriod;
	}

	public String getYearOfManufacturing() {
		return yearOfManufacturing;
	}

	public void setYearOfManufacturing(String yearOfManufacturing) {
		this.yearOfManufacturing = yearOfManufacturing;
	}

}
