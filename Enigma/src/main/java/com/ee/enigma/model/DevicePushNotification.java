package com.ee.enigma.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEVICE_PUSH_NOTIFICATION")
public class DevicePushNotification {

    @Id
    private String deviceId;
    private String deviceToken;
    @Column(name = "push_notification_start_time")
    private Date pushNotificationStartTime;
    @Column(name = "push_notification_end_time")
    private Date pushNotificationEndTime;
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

    public Date getPushNotificationStartTime() {
        return pushNotificationStartTime;
    }

    public void setPushNotificationStartTime(Date pushNotificationStartTime) {
        this.pushNotificationStartTime = pushNotificationStartTime;
    }

    public Date getPushNotificationEndTime() {
        return pushNotificationEndTime;
    }

    public void setPushNotificationEndTime(Date pushNotificationEndTime) {
        this.pushNotificationEndTime = pushNotificationEndTime;
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
                + ", push_notification_start_time=" + pushNotificationStartTime + ", push_notification_end_time="
                + pushNotificationEndTime + ", userId=" + userId + "+ , activitId=" + activityId + "]";
    }

}
