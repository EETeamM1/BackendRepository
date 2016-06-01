package com.ee.enigma.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_INFO")
public class UserInfo {
	
	@Id
	private String userId;
	private String userName;
	private String password;
	
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
