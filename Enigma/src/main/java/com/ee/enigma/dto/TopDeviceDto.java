package com.ee.enigma.dto;

public class TopDeviceDto implements Comparable<TopDeviceDto> {
	  private String deviceId;
	  private String deviceName;
	  private Long count;
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
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "TopDeviceDto [deviceId=" + deviceId + ", deviceName=" + deviceName + ", count=" + count + "]";
	}

	@Override
	public int compareTo(TopDeviceDto obj) {

		// ascending order
//		return this.count - compareQuantity;

		// descending order
		 return (int) (obj.getCount() - this.count);
	}
	
}
