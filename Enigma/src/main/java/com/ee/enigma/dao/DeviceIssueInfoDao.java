package com.ee.enigma.dao;

import java.sql.Date;
import java.util.List;

import com.ee.enigma.dto.TopDeviceDto;
import com.ee.enigma.model.DeviceIssueInfo;

public interface DeviceIssueInfoDao {
	public DeviceIssueInfo getDeviceIssueInfoByIssueID(String issueId);

	public void createDeviceIssueInfo(DeviceIssueInfo deviceIssueInfo);

	public void updateDeviceIssueInfo(DeviceIssueInfo deviceIssueInfo);

	public List<DeviceIssueInfo> getDeviceIssueInfoList(String deviceId);
	
	public DeviceIssueInfo getDeviceIssueInfoByDeviceId(String deviceId);

	public List<DeviceIssueInfo> getDeviceIssueInfoList(String deviceId, String userId);

	public List<DeviceIssueInfo> getDeviceIssueList(String deviceId, Date beginDate, Date endDate);

	public List<DeviceIssueInfo> getDeviceIssueList(Date beginDate, Date endDate, String issueType);

	public List<TopDeviceDto> getTopDevices(java.util.Date lastMonthDate);
}
