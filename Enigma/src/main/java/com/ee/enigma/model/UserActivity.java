package com.ee.enigma.model;



import java.util.Comparator;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	  @ManyToOne(fetch = FetchType.EAGER)
	  @JoinColumn(name = "issueId" ,insertable =false, updatable=false)
	  private DeviceIssueInfo deviceIssueInfo;
	 
	  @ManyToOne(fetch = FetchType.EAGER)
	  @JoinColumn(name = "deviceId" ,insertable =false, updatable=false)
	  private DeviceInfo deviceInfo;
	   
	  @ManyToOne(fetch = FetchType.EAGER)
	  @JoinColumn(name = "userId",insertable =false, updatable=false)
	  private UserInfo userInfo;
		  
	/*GETTERS AND SETTERS*/	
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
	
	public DeviceIssueInfo getDeviceIssueInfo()
  {
    return deviceIssueInfo;
  }

  public void setDeviceIssueInfo(DeviceIssueInfo deviceIssueInfo)
  {
    this.deviceIssueInfo = deviceIssueInfo;
  }

  public DeviceInfo getDeviceInfo()
  {
    return deviceInfo;
  }

  public void setDeviceInfo(DeviceInfo deviceInfo)
  {
    this.deviceInfo = deviceInfo;
  }

  public UserInfo getUserInfo()
  {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo)
  {
    this.userInfo = userInfo;
  }

  @Override
	public String toString() {
		return "activityId: "+this.getActivityId()+", userId: "+this.getUserId()+", deviceId: "+this.getDeviceId()+", issueId: "+this.getIssueId()+", location: "+this.getLocation()+", loginTime: "+this.getLoginTime()+", logoutTime: "+this.getLogoutTime();
	}
}
