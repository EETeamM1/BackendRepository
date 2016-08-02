package com.ee.enigma.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="device_push_notification")
public class DevicePushNotification {

	@Id
	private String deviceId;
	private String deviceToken;
	private Date push_notification_start_time;
	private Date push_notification_end_time;
	private String userId;
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getDeviceToken() {
		return deviceToken;
	}
	
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	
	public Date getPush_notification_start_time() {
		return push_notification_start_time;
	}
	
	public void setPush_notification_start_time(Date push_notification_start_time) {
		this.push_notification_start_time = push_notification_start_time;
	}
	
	public Date getPush_notification_end_time() {
		return push_notification_end_time;
	}
	
	public void setPush_notification_end_time(Date push_notification_end_time) {
		this.push_notification_end_time = push_notification_end_time;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "DevicePushNotification [deviceId=" + deviceId + ", deviceToken=" + deviceToken
				+ ", push_notification_start_time=" + push_notification_start_time + ", push_notification_end_time="
				+ push_notification_end_time + ", userId=" + userId + "]";
	}
	
	
	
}

