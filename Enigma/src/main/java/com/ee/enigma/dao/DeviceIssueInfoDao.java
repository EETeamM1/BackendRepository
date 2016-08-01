package com.ee.enigma.dao;

import java.util.Date;
import java.util.List;

import com.ee.enigma.dto.DeviceReportDto;
import com.ee.enigma.dto.TopDeviceDto;
import com.ee.enigma.model.DeviceIssueInfo;

public interface DeviceIssueInfoDao {
	public DeviceIssueInfo getDeviceIssueInfoByIssueID(String issueId);

	public void createDeviceIssueInfo(DeviceIssueInfo deviceIssueInfo);

	public void updateDeviceIssueInfo(DeviceIssueInfo deviceIssueInfo);

	public List<DeviceIssueInfo> getDeviceIssueInfoList(String deviceId);
	
	public DeviceIssueInfo getDeviceIssueInfoByDeviceId(String deviceId);

	public List<DeviceIssueInfo> getDeviceIssueInfoList(String deviceId, String userId);

	public List<DeviceIssueInfo> getDeviceIssueList(String deviceId,java.util.Date beginDate, java.util.Date endDate);
	
	 public List<DeviceIssueInfo> getDeviceIssueListByUserId(String userId, java.util.Date beginDate, java.util.Date endDate);

	public List<DeviceIssueInfo> getDeviceIssueList(java.util.Date beginDate, java.util.Date endDate, String issueType);

	public List<TopDeviceDto> getTopDevices(java.util.Date lastMonthDate);
	
	 public List<DeviceIssueInfo> getAllDeviceIssueInfoList() ;

	public List<DeviceReportDto> getDeviceReport(String deviceId, Date startDate, Date endDate);
}
