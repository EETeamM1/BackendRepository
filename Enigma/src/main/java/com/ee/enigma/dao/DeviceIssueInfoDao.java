package com.ee.enigma.dao;

import java.util.List;

import com.ee.enigma.model.DeviceIssueInfo;

public interface DeviceIssueInfoDao {
	public DeviceIssueInfo getDeviceIssueInfo(long deviceId);
	public DeviceIssueInfo getDeviceIssueInfoByIssueID(String issueId);
	public String submitDeviceIssueInfo(long deviceId,String userId);
	public void createDeviceIssueInfo(DeviceIssueInfo deviceIssueInfo) ;
	public void updateDeviceIssueInfo(DeviceIssueInfo deviceIssueInfo) ;
	public List<DeviceIssueInfo> getDeviceIssueInfoList(long deviceId) ;
	
	
}
