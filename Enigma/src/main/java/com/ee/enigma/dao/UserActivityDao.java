package com.ee.enigma.dao;

import com.ee.enigma.model.UserActivity;

public interface UserActivityDao {
	public void createNewActivity(UserActivity userActivity);
	public void updateUserActivity(UserActivity userActivity);
	public UserActivity getUserActivityByActivityId(String activityId);
	public String logOutActivity(String activityId);
}
