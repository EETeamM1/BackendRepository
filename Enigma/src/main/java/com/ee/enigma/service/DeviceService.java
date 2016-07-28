package com.ee.enigma.service;

import java.util.Date;
import java.util.List;

import com.ee.enigma.dto.DeviceInfoDto;
import com.ee.enigma.dto.DeviceReportDto;
import com.ee.enigma.dto.TopDeviceDto;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface DeviceService {
	public EnigmaResponse saveDeviceInfo(Request userInfo);

	public EnigmaResponse deleteDeviceInfo(String deviceId);

	public DeviceInfoDto getDeviceInfo(String deviceId);

	public List<DeviceInfoDto> getDevicesInfoByStatus(String deviceId,String deviceStatus);

	public EnigmaResponse updateDeviceInfoStatus(Request requestInfo);

	public EnigmaResponse approveDevice(Request requestInfo);
	
	public List<TopDeviceDto> getTopDevices();
	
    public EnigmaResponse searchDevice(String searchQuery);

	List<DeviceReportDto> getDeviceReport(String deviceId, Date startDate, Date endDate);
}