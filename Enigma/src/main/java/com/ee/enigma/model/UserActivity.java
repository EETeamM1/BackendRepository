package com.ee.enigma.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ACTIVITY")
public class UserActivity {

    @Id
    String activityId;
    Date loginTime;
    Date logoutTime;
    String location;
    String userId;
    String issueId;
    String deviceId;

    /* GETTERS AND SETTERS */
    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "activityId: " + this.getActivityId() + ", userId: " + this.getUserId() + ", deviceId: "
                + this.getDeviceId() + ", issueId: " + this.getIssueId() + ", location: " + this.getLocation()
                + ", loginTime: " + this.getLoginTime() + ", logoutTime: " + this.getLogoutTime();
    }
}
