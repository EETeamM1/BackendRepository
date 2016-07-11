package com.ee.enigma.dto;

public class TopDeviceDto {
	  private String deviceId;
	  private String deviceName;
	  private int count;
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getDeviceName() {
		return deviceName;
	}
	
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "TopDeviceDto [deviceId=" + deviceId + ", deviceName=" + deviceName + ", count=" + count + "]";
	}
	
}
