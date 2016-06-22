package com.ee.enigma.service;

import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface DeviceIssueInfoService {
	public EnigmaResponse deviceIssueInfoService(Request deviceIssueInfo);
	public EnigmaResponse getReportForDevice(Request deviceIssueInfo);
  public EnigmaResponse submitDevice(long deviceId, String userId,boolean byAdmin,String approvedByAdmin);
}
