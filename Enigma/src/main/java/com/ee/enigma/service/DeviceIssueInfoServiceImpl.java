package com.ee.enigma.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.Constants;
import com.ee.enigma.dao.DeviceIssueInfoDao;
import com.ee.enigma.model.DeviceIssueInfo;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.Response;
import com.ee.enigma.response.ResponseCode;
import com.ee.enigma.response.ResponseResult;

@Service(value = "deviceIssueInfoService")
@Transactional
public class DeviceIssueInfoServiceImpl implements DeviceIssueInfoService
{

  private Logger logger = Logger.getLogger(DeviceIssueInfoServiceImpl.class);
  private Response response;
  private ResponseCode responseCode;
  private ResponseResult result;
  private DeviceIssueInfoDao deviceIssueInfoDao;

  @Autowired
  @Qualifier(value = "deviceIssueInfoDao")
  public void setDeviceInfoDao(DeviceIssueInfoDao deviceIssueInfoDao)
  {
    this.deviceIssueInfoDao = deviceIssueInfoDao;
  }

  public Response deviceIssueInfoService(Request deviceIssueInfo)
  {
    response = new Response();
    responseCode = new ResponseCode();
    result = new ResponseResult();
    String userId;
    long deviceId;
    boolean byAdmin;

    try
    {
      deviceId = deviceIssueInfo.getParameters().getDeviceId();
      userId = deviceIssueInfo.getParameters().getUserId();
      byAdmin = deviceIssueInfo.getParameters().isByAdmin();

    }
    catch (Exception e)
    {
      logger.error(e);
      return badRequest();
    }

    // Checking whether request contains all require fields or not.
    if (null == userId || "".equals(userId.trim()) || 0 == deviceId)
    {
      return badRequest();
    }
    // Checking device info
    // Assuming , deviceId and userId will be selected from UI
    List<DeviceIssueInfo> deviceIssueInfoList = deviceIssueInfoDao.getDeviceIssueInfoList(deviceId);
    boolean deviceAvailable = true;
    if (byAdmin)
    {
      if (deviceIssueInfoList != null && deviceIssueInfoList.size() > 0)
      {
        for (DeviceIssueInfo deviceIssue : deviceIssueInfoList)
        {
          if (deviceIssue.getSubmitTime() == null)
          {
            deviceAvailable = false;
            break;
          }
        }
      }
      // If Device is not issued to any one, issue it
      if (deviceAvailable)
      {
        createDeviceIssueInfo(deviceId, userId, byAdmin);
      }
      else
      {
        responseCode.setCode(Constants.CODE_SUCCESS);
        responseCode.setMessage(Constants.MESSAGE_DEVICE_ALREADY_ISSUED);
        response.setResponseCode(responseCode);
        response.setResult(result);
        return response;
      }
    }
    else
    {
      if (deviceIssueInfoList != null && deviceIssueInfoList.size() > 0)
      {
        for (DeviceIssueInfo deviceIssue : deviceIssueInfoList)
        {
          if (deviceIssue.getSubmitTime() == null)
          {
            // submit Device
            if(deviceIssue.getUserId().equals(userId))
            {
              //Not submit User Is same 
              responseCode.setCode(Constants.CODE_SUCCESS);
              responseCode.setMessage(deviceIssue.getIssueId());
              response.setResponseCode(responseCode);
              response.setResult(result);
              return response;
            }
            else
            {
            deviceIssue.setSubmitTime(CommonUtils.getCurrentDate());
            deviceIssue.setByAdmin(false);
            }
            break;
          }
        }
      }
      createDeviceIssueInfo(deviceId, userId, false);
    }
    // Success response.
    responseCode.setCode(Constants.CODE_SUCCESS);
    responseCode.setMessage(Constants.MESSAGE_DEVICE_SUCCESSFULLY_ISSUED);
    response.setResponseCode(responseCode);
    response.setResult(result);
    return response;
  }

  private String issueIdGenerator(long deviceId, String userId)
  {
    return deviceId + userId + CommonUtils.getTime();
  }

  private Response deviceNotRegisteredResponse()
  {
    responseCode.setCode(Constants.CODE_NOT_FOUND);
    responseCode.setMessage(Constants.MESSAGE_NOT_FOUND_DEVICE);
    response.setResponseCode(responseCode);
    return response;
  }

  private Response authenticationFailedResponse()
  {
    responseCode.setCode(Constants.CODE_AUTHENTICATION_FAILD);
    responseCode.setMessage(Constants.MESSAGE_AUTHENTICATION_FAILD);
    response.setResponseCode(responseCode);
    return response;
  }

  private Response badRequest()
  {
    responseCode.setCode(Constants.CODE_BAD_REQUEST);
    responseCode.setMessage(Constants.MESSAGE_BAD_REQUEST);
    response.setResponseCode(responseCode);
    return response;
  }

  private void createDeviceIssueInfo(long deviceId, String userId, boolean byAdmin)
  {
    DeviceIssueInfo newDeviceIssueInfo = new DeviceIssueInfo();
    newDeviceIssueInfo.setDeviceId(deviceId);
    newDeviceIssueInfo.setUserId(userId);
    newDeviceIssueInfo.setIssueTime(CommonUtils.getCurrentDate());
    newDeviceIssueInfo.setIssueId(issueIdGenerator(deviceId, userId));
    newDeviceIssueInfo.setByAdmin(byAdmin);
    deviceIssueInfoDao.createDeviceIssueInfo(newDeviceIssueInfo);
  }

}
