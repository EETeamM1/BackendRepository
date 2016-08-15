package com.ee.enigma.dto;

import java.sql.Time;

import com.ee.enigma.model.DeviceInfo;

public class DeviceInfoDto {
    private String deviceId;
    private String deviceName;
    private String manufacturer;
    private String OS;
    private String OSVersion;
    private String yearOfManufacturing;
    private boolean isMasterSet;
    private Time timeoutPeriod;
    private String deviceAvailability;

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

    public Time getTimeoutPeriod() {
        return timeoutPeriod;
    }

    public void setTimeoutPeriod(Time timeoutPeriod) {
        this.timeoutPeriod = timeoutPeriod;
    }

    public String getDeviceAvailability() {
        return deviceAvailability;
    }

    public void setDeviceAvailability(String deviceAvailability) {
        this.deviceAvailability = deviceAvailability;
    }

    public DeviceInfoDto getDeviceInfoDto(DeviceInfo deviceInfo) {
        DeviceInfoDto deviceInfoDto = null;
        if (deviceInfo != null) {
            deviceInfoDto = new DeviceInfoDto();
            deviceInfoDto.setDeviceAvailability(deviceInfo.getDeviceAvailability());
            deviceInfoDto.setDeviceId(deviceInfo.getDeviceId());
            deviceInfoDto.setDeviceName(deviceInfo.getDeviceName());
            deviceInfoDto.setManufacturer(deviceInfo.getManufacturer());
            deviceInfoDto.setOS(deviceInfo.getOS());
            deviceInfoDto.setOSVersion(deviceInfo.getOSVersion());
            deviceInfoDto.setTimeoutPeriod(deviceInfo.getTimeoutPeriod());
            deviceInfoDto.setYearOfManufacturing(deviceInfo.getYearOfManufacturing());
        }
        return deviceInfoDto;
    }

}
