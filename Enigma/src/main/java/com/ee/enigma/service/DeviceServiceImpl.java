package com.ee.enigma.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.common.Constants;
import com.ee.enigma.dao.DeviceInfoDao;
import com.ee.enigma.dao.UserActivityDaoImpl;
import com.ee.enigma.dto.DeviceInfoDto;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.response.ResponseCode;
import com.ee.enigma.response.ResponseResult;

@Service(value = "deviceService")
@Transactional
public class DeviceServiceImpl implements DeviceService
{

  private Logger logger = Logger.getLogger(UserActivityDaoImpl.class);
  private DeviceInfoDao deviceInfoDao;
  private EnigmaResponse response;
  private ResponseCode responseCode;
  private ResponseResult result;

  @Autowired
  @Qualifier(value = "deviceInfoDao")
  public void setDeviceInfoDao(DeviceInfoDao deviceInfoDao)
  {
    this.deviceInfoDao = deviceInfoDao;
  }

  public  List<DeviceInfoDto> getDevicesInfoByStatus(Request requestInfo)
  {
    List<DeviceInfoDto> deviceInfoDtos = new ArrayList<DeviceInfoDto>();
    String deviceId = null;
    String deviceStatus = null;
    try
    {
      deviceId = requestInfo.getParameters().getDeviceId().trim();
      deviceStatus = requestInfo.getParameters().getDeviceStatus().trim();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
    List<DeviceInfo> deviceInfos = deviceInfoDao
      .getDevicesListByIDAndStatus(deviceId, deviceStatus);
    DeviceInfoDto deviceInfoDto = new DeviceInfoDto();
    DeviceInfo deviceInfo = null;
    if (deviceInfos != null && deviceInfos.size() > 0)
    {
      for (int i = 0; i < deviceInfos.size(); i++)
      {
        deviceInfo = deviceInfos.get(i);
        deviceInfoDtos.add(deviceInfoDto.getDeviceInfoDto(deviceInfo));
      }
    }
    return deviceInfoDtos;
  }
  
  public EnigmaResponse saveDeviceInfo(Request requestInfo)
  {
    response = new EnigmaResponse();
    responseCode = new ResponseCode();
    result = new ResponseResult();

    String deviceId;
    String deviceName;
    // String isAdminApproved;
    String opration;
    String manufacturer;
    String oS;
    String osVersion;
    String yearOfManufacturing;
    DeviceInfo deviceInfo = null;
    try
    {
      deviceId = requestInfo.getParameters().getDeviceId().trim();
      deviceName = requestInfo.getParameters().getDeviceName().trim();
      opration = requestInfo.getParameters().getOpration().trim();
      manufacturer = requestInfo.getParameters().getManufacturer().trim();
      oS = requestInfo.getParameters().getoS().trim();
      osVersion = requestInfo.getParameters().getOsVersion().trim();
      // timeoutPeriod = requestInfo.getParameters().getTimeoutPeriod().trim();
      yearOfManufacturing = requestInfo.getParameters().getYearOfManufacturing().trim();
    }
    catch (Exception e)
    {
      logger.error(e);
      return badRequest();
    }

    // Checking whether request contains all require fields or not.
    if (null == deviceId || null == deviceName || null == opration || null == manufacturer
      || null == oS || null == osVersion || null == yearOfManufacturing)
    {
      return badRequest();
    }
    deviceInfo = new DeviceInfo();
    deviceInfo.setDeviceId(deviceId);
    deviceInfo.setDeviceName(deviceName);
    // deviceInfo.setIsAdminApproved(isAdminApproved);
    deviceInfo.setManufacturer(manufacturer);
    deviceInfo.setOS(oS);
    deviceInfo.setOSVersion(osVersion);
    // deviceInfo.setTimeoutPeriod(timeoutPeriod);
    deviceInfo.setYearOfManufacturing(yearOfManufacturing);
    if (opration.equals("save"))
    {
      deviceInfoDao.createDeviceInfo(deviceInfo);
      responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_SAVE);
    }
    else if (opration.equals("update"))
    {
      deviceInfoDao.updateDeviceInfo(deviceInfo);
      responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_UPDATED);
    }
    // Success response.
    responseCode.setCode(Constants.CODE_SUCCESS);
    // result.setSessionToken(activityId);
    response.setResponseCode(responseCode);
    response.setResult(result);
    return response;
  }

  public EnigmaResponse updateDeviceInfoStatus(Request requestInfo){

    response = new EnigmaResponse();
    responseCode = new ResponseCode();
    result = new ResponseResult();

    String deviceId = null;
    String deviceStatus = null;
    try
    {
      deviceId = requestInfo.getParameters().getDeviceId().trim();
      deviceStatus = requestInfo.getParameters().getDeviceStatus().trim();
    }
    catch (Exception e)
    {
      logger.error(e);
      return badRequest();
    }

    // Checking whether request contains all require fields or not.
    if (null == deviceId || null == deviceStatus)
    {
      return badRequest();
    }
     DeviceInfo deviceInfo=deviceInfoDao.getDeviceInfo(deviceId);
     if(deviceInfo!=null)
     {
     deviceInfo.setDeviceAvailability(deviceStatus);
     deviceInfoDao.updateDeviceInfo(deviceInfo);
     responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_UPDATED);
     // Success response.
     responseCode.setCode(Constants.CODE_SUCCESS);
     // result.setSessionToken(activityId);
     response.setResponseCode(responseCode);
     response.setResult(result);
     return response;
     }
     // Success response.
     responseCode.setMessage(Constants.MESSAGE_NOT_FOUND_DEVICE);
     responseCode.setCode(Constants.CODE_NOT_FOUND);
     // result.setSessionToken(activityId);
     response.setResponseCode(responseCode);
     response.setResult(result);
    return response;
  
  }
  public EnigmaResponse deleteDeviceInfo(Request requestInfo)
  {
    response = new EnigmaResponse();
    responseCode = new ResponseCode();
    result = new ResponseResult();
    String deviceId;

    try
    {
      deviceId = requestInfo.getParameters().getDeviceId().trim();
    }
    catch (Exception e)
    {
      logger.error(e);
      return badRequest();
    }

    // Checking whether request contains all require fields or not.
    if (null == deviceId)
    {
      return badRequest();
    }
    DeviceInfo deviceInfo = new DeviceInfo();
    deviceInfo.setDeviceId(deviceId);
    deviceInfoDao.deleteDeviceInfo(deviceInfo);
    responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_DELETED);

    // Success response.
    responseCode.setCode(Constants.CODE_SUCCESS);
    response.setResponseCode(responseCode);
    response.setResult(result);
    return response;
  }

  /*public EnigmaResponse getDeviceInfo(Request requestInfo)
  {
    response = new EnigmaResponse();
    responseCode = new ResponseCode();
    result = new ResponseResult();
    String deviceId;

    try
    {
      deviceId = requestInfo.getParameters().getDeviceId().trim();
    }
    catch (Exception e)
    {
      logger.error(e);
      return badRequest();
    }

    // Checking whether request contains all require fields or not.
    if (null == deviceId)
    {
      return badRequest();
    }
     DeviceInfo deviceInfo= deviceInfoDao.getDeviceInfo(deviceId);
     
    responseCode.setResultObject(deviceInfo);
    responseCode.setMessage(Constants.MESSAGE_SUCCESS);
    // Success response.
    responseCode.setCode(Constants.CODE_SUCCESS);
    response.setResponseCode(responseCode);
    response.setResult(result);
    return response;

  }*/
  
  public DeviceInfoDto getDeviceInfo(Request requestInfo)
  {
     String deviceId="";
     DeviceInfoDto deviceInfoDto=new DeviceInfoDto();
    try
    {
      deviceId = requestInfo.getParameters().getDeviceId().trim();
    }
    catch (Exception e)
    {
      logger.error(e);
    }
     DeviceInfo  deviceInfo= deviceInfoDao.getDeviceInfo(deviceId);
     return deviceInfoDto.getDeviceInfoDto(deviceInfo);
  }

  private EnigmaResponse badRequest()
  {
    responseCode.setCode(Constants.CODE_BAD_REQUEST);
    responseCode.setMessage(Constants.MESSAGE_BAD_REQUEST);
    response.setResponseCode(responseCode);
    return response;
  }

}
