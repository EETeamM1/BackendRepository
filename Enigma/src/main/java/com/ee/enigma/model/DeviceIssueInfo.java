package com.ee.enigma.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DEVICE_ISSUE_INFO")
public class DeviceIssueInfo {
	
	@Id
	private String issueId;
	private String deviceId;
	private String userId;
	private Timestamp issueTime;
	private Timestamp submitTime;
	private Boolean issueByAdmin;
  private Boolean submitByAdmin;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "deviceId" ,insertable =false, updatable=false)
  private DeviceInfo deviceInfo;
  
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "issueId" ,insertable =false, updatable=false)
  @javax.persistence.OrderBy("loginTime")
  private Set<UserActivity> userActivity;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "userId",insertable =false, updatable=false)
  private UserInfo userInfo;
  
  public UserInfo getUserInfo()
  {
    return userInfo;
  }
  public void setUserInfo(UserInfo userInfo)
  {
    this.userInfo = userInfo;
  }
  public Boolean getIssueByAdmin()
  {
    return issueByAdmin;
  }
  public void setIssueByAdmin(Boolean issueByAdmin)
  {
    this.issueByAdmin = issueByAdmin;
  }
  public Boolean getSubmitByAdmin()
  {
    return submitByAdmin;
  }
  public void setSubmitByAdmin(Boolean submitByAdmin)
  {
    this.submitByAdmin = submitByAdmin;
  }
  /* GETTERS AND SETTERS */
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
  public Timestamp getIssueTime()
  {
    return issueTime;
  }
  public void setIssueTime(Timestamp issueTime)
  {
    this.issueTime = issueTime;
  }
  public Timestamp getSubmitTime()
  {
    return submitTime;
  }
  public void setSubmitTime(Timestamp submitTime)
  {
    this.submitTime = submitTime;
  }
  
  public DeviceInfo getDeviceInfo()
  {
    return deviceInfo;
  }
  public void setDeviceInfo(DeviceInfo deviceInfo)
  {
    this.deviceInfo = deviceInfo;
  }
  
  public Set<UserActivity> getUserActivity()
  {
    return userActivity;
  }
  public void setUserActivity(Set<UserActivity> userActivity)
  {
    this.userActivity = userActivity;
  }
	
}
