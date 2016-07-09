package com.ee.enigma.dao;

import java.util.List;

import com.ee.enigma.model.UserInfo;

public interface UserInfoDao {

	public UserInfo getUserInfo(String userId, String password) ;
	public UserInfo getUserInfo(String userId) ;
	public List<UserInfo> getAllUserInfo();
	public void createUserInfo(UserInfo userInfo) ;
	public void updateUserInfo(UserInfo userInfo) ;
	public int deleteUserInfo(UserInfo userInfo) ;
	
}
