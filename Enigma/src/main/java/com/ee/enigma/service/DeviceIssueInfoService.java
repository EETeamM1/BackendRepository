package com.ee.enigma.service;

import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface DeviceIssueInfoService {
	public EnigmaResponse deviceIssueInfoService(Request deviceIssueInfo);
	public EnigmaResponse getDeviceTimeLineReport(Request deviceIssueInfo);
	public EnigmaResponse submitDevice(Request deviceIssueInfo);
	public String submitDeviceIssueInfo(String deviceId, String userId);
	public EnigmaResponse getDeviceIssueReportByStatus();
	public EnigmaResponse getDeviceReportAvailability();
	public EnigmaResponse getDeviceIssueReport(Request deviceIssueInfo);
	public EnigmaResponse getDeviceSubmitReport(Request deviceIssueInfo) throws Exception;
	
}
