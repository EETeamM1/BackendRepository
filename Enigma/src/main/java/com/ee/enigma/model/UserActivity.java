package com.ee.enigma.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ACTIVITY")
public class UserActivity {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	long activityId;
	Date loginTime;
	Date logoutTime;
	String location;
	String userId;
	String issueId;
	long deviceId;
	
	/*GETTERS AND SETTERS*/	
	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	
	@Override
	public String toString() {
		return "activityId: "+this.getActivityId()+", userId: "+this.getUserId()+", deviceId: "+this.getDeviceId()+", issueId: "+this.getIssueId()+", location: "+this.getLocation()+", loginTime: "+this.getLoginTime()+", logoutTime: "+this.getLogoutTime();
	}
}
