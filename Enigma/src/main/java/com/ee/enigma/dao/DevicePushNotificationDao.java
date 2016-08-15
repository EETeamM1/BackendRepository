package com.ee.enigma.dao;

import com.ee.enigma.model.DevicePushNotification;

public interface DevicePushNotificationDao {
    public DevicePushNotification getDeviceInfo(String deviceId);

    public void updateDevicePushNotification(DevicePushNotification deviceInfo);

    public void saveDevicePushNotification(DevicePushNotification devicePushNotification);
}
