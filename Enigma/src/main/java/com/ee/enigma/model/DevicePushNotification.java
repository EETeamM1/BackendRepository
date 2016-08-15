package com.ee.enigma.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEVICE_PUSH_NOTIFICATION")
public class DevicePushNotification {

    @Id
    private String deviceId;
    private String deviceToken;
    private Date push_notification_start_time;
    private Date push_notification_end_time;
    private String userId;
    private String activityId;

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

    public void setPush_notification_start_time(Date pushNotificationStartTime) {
        this.push_notification_start_time = pushNotificationStartTime;
    }

    public Date getPush_notification_end_time() {
        return push_notification_end_time;
    }

    public void setPush_notification_end_time(Date pushNotificationEndTime) {
        this.push_notification_end_time = pushNotificationEndTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return "DevicePushNotification [deviceId=" + deviceId + ", deviceToken=" + deviceToken
                + ", push_notification_start_time=" + push_notification_start_time + ", push_notification_end_time="
                + push_notification_end_time + ", userId=" + userId + "+ , activitId=" + activityId + "]";
    }

}
