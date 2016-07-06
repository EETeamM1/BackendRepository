package com.ee.enigma.dto;

import java.sql.Timestamp;
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
  String deviceName;
  private Timestamp issueTime;
  private Timestamp submitTime;
  private Boolean issueByAdmin;
  private Boolean submitByAdmin;
  private String deviceAvailability;
  private String manufacturer;
  private String OS;
  private String OSVersion;
  private String issuedBy;
  
    public String getIssuedBy()
  {
    return issuedBy;
  }

  public void setIssuedBy(String issuedBy)
  {
    this.issuedBy = issuedBy;
  }

  public String getManufacturer()
  {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer)
  {
    this.manufacturer = manufacturer;
  }

  public String getOS()
  {
    return OS;
  }

  public void setOS(String oS)
  {
    OS = oS;
  }

  public String getOSVersion()
  {
    return OSVersion;
  }

  public void setOSVersion(String oSVersion)
  {
    OSVersion = oSVersion;
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

  public Boolean getIssueByAdmin()
  {
    return issueByAdmin;
  }

  public void setIssueByAdmin(Boolean issueByAdmin)
  {
    this.issueByAdmin = issueByAdmin;
  }

  
  

  public String getDeviceName()
  {
    return deviceName;
  }

  public void setDeviceName(String deviceName)
  {
    this.deviceName = deviceName;
  }

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

  public String getDeviceAvailability()
  {
    return deviceAvailability;
  }

  public void setDeviceAvailability(String deviceAvailability)
  {
    this.deviceAvailability = deviceAvailability;
  }

  public Boolean getSubmitByAdmin()
  {
    return submitByAdmin;
  }

  public void setSubmitByAdmin(Boolean submitByAdmin)
  {
    this.submitByAdmin = submitByAdmin;
  }

}
