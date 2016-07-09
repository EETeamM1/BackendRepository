package com.ee.enigma.service;

import java.util.List;

import com.ee.enigma.dto.DeviceInfoDto;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface DeviceService {
	public EnigmaResponse saveDeviceInfo(Request userInfo);

	public EnigmaResponse deleteDeviceInfo(Request requestInfo);

	public DeviceInfoDto getDeviceInfo(Request requestInfo);

	public List<DeviceInfoDto> getDevicesInfoByStatus(Request requestInfo);

	public EnigmaResponse updateDeviceInfoStatus(Request requestInfo);

	public EnigmaResponse approveDevice(Request requestInfo);

}
