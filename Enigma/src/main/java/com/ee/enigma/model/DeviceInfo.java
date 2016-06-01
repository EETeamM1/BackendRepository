package com.ee.enigma.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DEVICE_INFO")
public class DeviceInfo {
	
	@Id
	private long deviceId;
	private String deviceName;
	private String manufacturer;
	private String OS;
	private String OSVersion;
	private String yearOfManufacturing;
	private boolean isMasterSet;
	private int timeoutPeriod;
	
	/* GETTERS AND SETTERS */
	
	
	public int getTimeoutPeriod() {
		return timeoutPeriod;
	}
	public void setTimeoutPeriod(int timeoutPeriod) {
		this.timeoutPeriod = timeoutPeriod;
	}
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getOS() {
		return OS;
	}
	public void setOS(String oS) {
		OS = oS;
	}
	public String getOSVersion() {
		return OSVersion;
	}
	public void setOSVersion(String oSVersion) {
		OSVersion = oSVersion;
	}
	public String getYearOfManufacturing() {
		return yearOfManufacturing;
	}
	public void setYearOfManufacturing(String yearOfManufacturing) {
		this.yearOfManufacturing = yearOfManufacturing;
	}
	public boolean isMasterSet() {
		return isMasterSet;
	}
	public void setMasterSet(boolean isMasterSet) {
		this.isMasterSet = isMasterSet;
	}
}
