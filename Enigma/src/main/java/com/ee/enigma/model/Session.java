package com.ee.enigma.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SESSION")
public class Session {
    @Id
    private String activityId;
    private Date notificationStartTime;
    private int retryCount;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Date getNotificationStartTime() {
        return notificationStartTime;
    }

    public void setNotificationStartTime(Date notificationStartTime) {
        this.notificationStartTime = notificationStartTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retry) {
        this.retryCount = retry;
    }
}
