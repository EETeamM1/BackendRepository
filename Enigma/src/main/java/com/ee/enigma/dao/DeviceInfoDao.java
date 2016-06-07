package com.ee.enigma.dao;

import com.ee.enigma.model.DeviceInfo;

public interface DeviceInfoDao {
	public DeviceInfo getDeviceInfo(long deviceId);
	public void updateDeviceInfo(DeviceInfo deviceInfo);
}
