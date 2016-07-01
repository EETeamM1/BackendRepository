package com.ee.enigma.service;

import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface DeviceService
{
  public EnigmaResponse saveDeviceInfo(Request userInfo);

  public EnigmaResponse deleteDeviceInfo(Request requestInfo);

  public EnigmaResponse getDeviceInfo(Request requestInfo);
}
