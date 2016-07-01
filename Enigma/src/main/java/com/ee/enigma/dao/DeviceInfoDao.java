package com.ee.enigma.dao;

import com.ee.enigma.model.DeviceInfo;

public interface DeviceInfoDao {
	public DeviceInfo getDeviceInfo(String deviceId);
	public void updateDeviceInfo(DeviceInfo deviceInfo);
	public void createDeviceInfo(DeviceInfo deviceInfo) ;
  public int deleteDeviceInfo(DeviceInfo deviceInfo) ;
}
