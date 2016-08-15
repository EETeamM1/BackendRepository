package com.ee.enigma.dto;

public class DeviceStatusCountsInfo {
    int totalDevices;
    int availableDevices;
    int issuedDevices;

    public int getTotalDevices() {
        return totalDevices;
    }

    public void setTotalDevices(int totalDevices) {
        this.totalDevices = totalDevices;
    }

    public int getAvailableDevices() {
        return availableDevices;
    }

    public void setAvailableDevices(int availableDevices) {
        this.availableDevices = availableDevices;
    }

    public int getIssuedDevices() {
        return issuedDevices;
    }

    public void setIssuedDevices(int issuedDevices) {
        this.issuedDevices = issuedDevices;
    }

}
