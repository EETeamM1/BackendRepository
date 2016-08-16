package com.ee.enigma.service;

import org.json.simple.JSONObject;

import com.ee.enigma.common.EnigmaException;
import com.ee.enigma.dto.DeviceIssueStatusDto;
import com.ee.enigma.dto.DeviceIssueTrendLineDto;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface DeviceIssueInfoService {
    public EnigmaResponse deviceIssueInfoService(Request deviceIssueInfo) throws EnigmaException;

    public JSONObject getDeviceTimeLineReport(String beginDateString, String endDateString, String id,
            String reportType);

    public EnigmaResponse submitDevice(Request deviceIssueInfo) throws EnigmaException;

    public String populateDeviceIssueInfo(String deviceId, String userId) throws EnigmaException;

    public EnigmaResponse getDeviceReportAvailability() throws EnigmaException;

    public DeviceIssueTrendLineDto getDeviceIssueTimeLineTrendReport(String beginDateString, String endDateString,
            String reportType) throws EnigmaException;

    public DeviceIssueStatusDto getDeviceIssueStatusForDevice(String deviceId);

    public EnigmaResponse getDevicesIssueReportByStatus() throws EnigmaException;

    public EnigmaResponse getPendingDevicesReport() throws EnigmaException;
}
