package com.ee.enigma.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEVICE_INFO")
public class DeviceInfo {

    @Id
    private String deviceId;
    private String deviceName;
    private String manufacturer;
    @Column(name = "OS")
    private String os;
    @Column(name = "OSVersion")
    private String osVersion;
    private String yearOfManufacturing;
    private boolean isMasterSet;
    private Time timeoutPeriod;
    private String deviceAvailability;

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

    public String getOs() {
        return os;
    }

    public void setOs(String oS) {
        os = oS;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
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

    public String getDeviceAvailability() {
        return deviceAvailability;
    }

    public void setDeviceAvailability(String deviceAvailability) {
        this.deviceAvailability = deviceAvailability;
    }

    @Override
    public String toString() {
        return "DeviceId : " + this.getDeviceId() + ", DeviceName: " + this.getDeviceName() + ", OS: " + this.getOs()
                + ", OSVersion: " + this.getOsVersion() + ", Manufacturer: " + this.getManufacturer() + ", YOM: "
                + this.getYearOfManufacturing() + ", TimeoutPeriod: " + this.getTimeoutPeriod() + ", isMasterSet: "
                + this.isMasterSet();
    }
}
