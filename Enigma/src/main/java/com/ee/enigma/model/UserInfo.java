package com.ee.enigma.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USER_INFO")
public class UserInfo {
	
	@Id
	private String userId;
	private String userName;
	private String password;
	
	@OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "deviceId",insertable =false, updatable=false)
  private Set<DeviceIssueInfo> deviceIssueInfos;
	
	public Set<DeviceIssueInfo> getDeviceIssueInfos()
  {
    return deviceIssueInfos;
  }
  public void setDeviceIssueInfos(Set<DeviceIssueInfo> deviceIssueInfos)
  {
    this.deviceIssueInfos = deviceIssueInfos;
  }
   
  /* GETTERS AND SETTERS */
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "userId: "+this.getUserId()+", userName: "+this.getUserName()+", password: "+this.getPassword();
	}
}
