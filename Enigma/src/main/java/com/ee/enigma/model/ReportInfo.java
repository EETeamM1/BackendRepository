package com.ee.enigma.model;

import java.util.Date;

public class ReportInfo
{
  String activityId;
  Date loginTime;
  Date logoutTime;
  String location;
  String userId;
  String issueId;
  String deviceId;
  String fromTable;
  String userName;

  public String getActivityId()
  {
    return activityId;
  }

  public void setActivityId(String activityId)
  {
    this.activityId = activityId;
  }

  public Date getLoginTime()
  {
    return loginTime;
  }

  public void setLoginTime(Date loginTime)
  {
    this.loginTime = loginTime;
  }

  public Date getLogoutTime()
  {
    return logoutTime;
  }

  public void setLogoutTime(Date logoutTime)
  {
    this.logoutTime = logoutTime;
  }

  public String getLocation()
  {
    return location;
  }

  public void setLocation(String location)
  {
    this.location = location;
  }

  public String getUserId()
  {
    return userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public String getIssueId()
  {
    return issueId;
  }

  public void setIssueId(String issueId)
  {
    this.issueId = issueId;
  }

  public String getDeviceId()
  {
    return deviceId;
  }

  public void setDeviceId(String deviceId)
  {
    this.deviceId = deviceId;
  }

  public String getFromTable()
  {
    return fromTable;
  }

  public void setFromTable(String fromTable)
  {
    this.fromTable = fromTable;
  }

  public String getUserName()
  {
    return userName;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

}
