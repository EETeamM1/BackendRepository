package com.ee.enigma.model;

import java.sql.Time;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="DEVICE_INFO")
public class DeviceInfo {
	
	@Id
	private String deviceId;
	private String deviceName;
	private String manufacturer;
	private String OS;
	private String OSVersion;
	private String yearOfManufacturing;
	private boolean isMasterSet;
	private Time timeoutPeriod;
	private String deviceAvailability;
	
	/*@OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "deviceId",insertable =false, updatable=false)
  private Set<DeviceIssueInfo> deviceIssueInfos;*/
	
	/* GETTERS AND SETTERS */
	public Time getTimeoutPeriod() {
		return timeoutPeriod;
	}
	public void setTimeoutPeriod(Time timeoutPeriod) {
		this.timeoutPeriod = timeoutPeriod;
	}
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
	public void setOSVersion(String osVersion) {
		OSVersion = osVersion;
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
	
  public String getDeviceAvailability()
  {
    return deviceAvailability;
  }
  public void setDeviceAvailability(String deviceAvailability)
  {
    this.deviceAvailability = deviceAvailability;
  }
  @Override
	public String toString() {
		return "DeviceId : "+this.getDeviceId()+", DeviceName: "+this.getDeviceName()+", OS: "+this.getOS()+", OSVersion: "+this.getOSVersion()+", Manufacturer: "+this.getManufacturer()+", YOM: "+this.getYearOfManufacturing()+", TimeoutPeriod: "+this.getTimeoutPeriod()+", isMasterSet: "+this.isMasterSet();
	}
}
