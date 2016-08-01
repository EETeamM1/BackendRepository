package com.ee.enigma.service;

import org.json.simple.JSONObject;

import com.ee.enigma.dto.DeviceIssueStatusDto;
import com.ee.enigma.dto.DeviceIssueTrendLineDto;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface DeviceIssueInfoService {
	public EnigmaResponse deviceIssueInfoService(Request deviceIssueInfo);
	public JSONObject getDeviceTimeLineReport(String beginDateString,String endDateString,String id,String reportType);
	public EnigmaResponse submitDevice(Request deviceIssueInfo);
	public String populateDeviceIssueInfo(String deviceId, String userId);
	public EnigmaResponse getDeviceReportAvailability();
	public DeviceIssueTrendLineDto getDeviceIssueTimeLineTrendReport(String beginDateString,String endDateString,String reportType);
	public DeviceIssueStatusDto getDeviceIssueStatusForDevice(String deviceId);
	public EnigmaResponse getDevicesIssueReportByStatus();
	public EnigmaResponse getPendingDevicesReport();
}
