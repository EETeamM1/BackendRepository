package com.ee.enigma.response;

import java.util.List;

import com.ee.enigma.dto.DeviceReportDto;
import com.ee.enigma.dto.TopDeviceDto;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.UserInfo;

public class ResponseResult {
    private String sessionToken;
    private int timeout;
    private String masterPassword;
    private UserInfo user;
    private List<UserInfo> userList;
    private List<TopDeviceDto> topDeviceDtoList;
    private List<DeviceInfo> deviceList;
    private List<DeviceReportDto> deviceReportDtoList;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo userInfo) {
        this.user = userInfo;
    }

    public List<UserInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<UserInfo> userInfoList) {
        this.userList = userInfoList;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public List<TopDeviceDto> getTopDeviceDtoList() {
        return topDeviceDtoList;
    }

    public void setTopDeviceDtoList(List<TopDeviceDto> topDeviceDtoList) {
        this.topDeviceDtoList = topDeviceDtoList;
    }

    public List<DeviceInfo> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<DeviceInfo> deviceList) {
        this.deviceList = deviceList;
    }

    public List<DeviceReportDto> getDeviceReportDtoList() {
        return deviceReportDtoList;
    }

    public void setDeviceReportDtoList(List<DeviceReportDto> deviceReportDtoList) {
        this.deviceReportDtoList = deviceReportDtoList;
    }

}
