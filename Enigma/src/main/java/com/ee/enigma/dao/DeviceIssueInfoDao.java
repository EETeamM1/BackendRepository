package com.ee.enigma.dao;

import java.sql.Date;
import java.util.List;

import com.ee.enigma.model.DeviceIssueInfo;

public interface DeviceIssueInfoDao {
	public DeviceIssueInfo getDeviceIssueInfo(String deviceId);
	public DeviceIssueInfo getDeviceIssueInfoByIssueID(String issueId);
	//public String submitDeviceIssueInfo(long deviceId,String userId);
	public void createDeviceIssueInfo(DeviceIssueInfo deviceIssueInfo) ;
	public void updateDeviceIssueInfo(DeviceIssueInfo deviceIssueInfo) ;
	public List<DeviceIssueInfo> getDeviceIssueInfoList(String deviceId) ;
	public List<DeviceIssueInfo> getDeviceIssueInfoList(String deviceId,String userId) ;
	public List<DeviceIssueInfo> getDeviceIssueInfoListByDate(String deviceId,Date beginDate,Date endDate) ;
	
}
