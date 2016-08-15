package com.ee.enigma.service;

import org.json.simple.JSONObject;

import com.ee.enigma.common.EngimaException;
import com.ee.enigma.dto.DeviceIssueStatusDto;
import com.ee.enigma.dto.DeviceIssueTrendLineDto;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface DeviceIssueInfoService {
    public EnigmaResponse deviceIssueInfoService(Request deviceIssueInfo) throws EngimaException;

    public JSONObject getDeviceTimeLineReport(String beginDateString, String endDateString, String id,
            String reportType);

    public EnigmaResponse submitDevice(Request deviceIssueInfo) throws EngimaException;

    public String populateDeviceIssueInfo(String deviceId, String userId) throws EngimaException;

    public EnigmaResponse getDeviceReportAvailability() throws EngimaException;

    public DeviceIssueTrendLineDto getDeviceIssueTimeLineTrendReport(String beginDateString, String endDateString,
            String reportType) throws EngimaException;

    public DeviceIssueStatusDto getDeviceIssueStatusForDevice(String deviceId);

    public EnigmaResponse getDevicesIssueReportByStatus() throws EngimaException;

    public EnigmaResponse getPendingDevicesReport() throws EngimaException;
}
