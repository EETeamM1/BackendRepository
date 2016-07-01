package com.ee.enigma.dao;

import java.util.Date;
import java.util.List;

import com.ee.enigma.model.DeviceIssueInfo;
import com.ee.enigma.model.UserActivity;

public interface UserActivityDao {
	public void createNewActivity(UserActivity userActivity);
	public void updateUserActivity(UserActivity userActivity);
	public UserActivity getUserActivityByActivityId(String activityId);
	public String logOutActivity(String activityId);
	public List<UserActivity> getUserActivityByDates(String deviceId,Date fromDate,Date  toDate);
}
