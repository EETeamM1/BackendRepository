package com.ee.enigma.service;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.Constants;
import com.ee.enigma.dao.DeviceInfoDao;
import com.ee.enigma.dao.DeviceIssueInfoDao;
import com.ee.enigma.dao.UserInfoDao;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.DeviceIssueInfo;
import com.ee.enigma.model.UserInfo;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.response.ResponseCode;
import com.ee.enigma.response.ResponseResult;

@Service(value = "deviceIssueInfoService")
@Transactional
public class DeviceIssueInfoServiceImpl implements DeviceIssueInfoService
{

  private Logger logger = Logger.getLogger(DeviceIssueInfoServiceImpl.class);
  private EnigmaResponse response;
  private ResponseCode responseCode;
  private ResponseResult result;
  private DeviceIssueInfoDao deviceIssueInfoDao;
  private DeviceInfoDao deviceInfoDao;
  private UserInfoDao userInfoDao;
  
  @Autowired
  @Qualifier(value = "deviceIssueInfoDao")
  public void setDeviceInfoDao(DeviceIssueInfoDao deviceIssueInfoDao)
  {
    this.deviceIssueInfoDao = deviceIssueInfoDao;
  }
  
  @Autowired
  @Qualifier(value = "deviceInfoDao")
  public void setDeviceInfoDao(DeviceInfoDao deviceInfoDao) {
    this.deviceInfoDao = deviceInfoDao;
  }

  @Autowired
  @Qualifier(value = "userInfoDao")
  public void setUserInfoDao(UserInfoDao userInfoDao) {
    this.userInfoDao = userInfoDao;
  }
  
  public EnigmaResponse getReportForDevice(Request deviceIssueInfo){
    EnigmaResponse  response=null;
 
    long deviceId;
    String beginDateString;
    String endDateString;
    Date beginDate;
    Date endDate;
    try
    {
      deviceId = deviceIssueInfo.getParameters().getDeviceId();
      beginDateString = deviceIssueInfo.getParameters().getBeginDate();
      endDateString = deviceIssueInfo.getParameters().getEndDate();
      beginDate= CommonUtils.getSqlDateByString(beginDateString);
      endDate= CommonUtils.getSqlDateByString(endDateString);
      if(beginDate==null)
        endDate=null;
    }
    catch (Exception e)
    {
      logger.error(e);
      return badRequest();
    }
    JSONObject jsonObject=null;
    jsonObject=getDeviceInfoReportJson(deviceId, beginDate, endDate);
    return response;
  }
  
  public JSONObject getDeviceInfoReportJson(long deviceId,Date beginDate,Date endDate)
  {
    JSONObject jsonObject = new JSONObject();
     List<DeviceIssueInfo> jsonObjectList= deviceIssueInfoDao.getDeviceIssueInfoListByDate(deviceId, beginDate, endDate);
    jsonObject.put("jsonList", jsonObjectList);
    logger.error(jsonObjectList);  
    return jsonObject;
  }
   
  public EnigmaResponse deviceIssueInfoService(Request deviceIssueInfo)
  {
    response = new EnigmaResponse();
    responseCode = new ResponseCode();
    result = new ResponseResult();
    String userId;
    long deviceId;
    Boolean byAdmin;

    try
    {
      deviceId = deviceIssueInfo.getParameters().getDeviceId();
      userId = deviceIssueInfo.getParameters().getUserId();
      byAdmin = deviceIssueInfo.getParameters().getByAdmin();

    }
    catch (Exception e)
    {
      logger.error(e);
      return badRequest();
    }

    // Checking whether request contains all require fields or not.
    if (null == userId || "".equals(userId.trim()) || 0 == deviceId || null==byAdmin)
    {
      return badRequest();
    }
    
    // Checking whether request Parameters are existing or not.
    // Checking device info
    DeviceInfo deviceInfo = deviceInfoDao.getDeviceInfo(deviceId);
    if (null == deviceInfo) {
      return deviceNotRegisteredResponse();
    }
    
    if (!isUserExisting(userId)) {
      return userNotExistingResponse();
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
              responseCode.setMessage(Constants.MESSAGE_DEVICE_ASSIGNED_TO_SAME_USER);
              response.setResponseCode(responseCode);
              response.setResult(result);
              return response;
            }
            else
            {
            deviceIssue.setSubmitTime(CommonUtils.getCurrentDate());
            deviceIssue.setSubmitByAdmin(false);
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

  
  @Override
  public EnigmaResponse submitDevice(long deviceId, String userId,boolean byAdmin,String approvedByAdmin)
  {
    response = new EnigmaResponse();
    responseCode = new ResponseCode();
    result = new ResponseResult();
    
    //approvedByAdmin -->> PD device is submmited but entry pending with admin , AV--> available
    //ByAdmin --> 
    String issueId=null;
    DeviceIssueInfo tempDeviceIssueInfo=null;
    DeviceIssueInfo newDeviceIssueInfo=null;
    List<DeviceIssueInfo> deviceIssueInfoList = deviceIssueInfoDao.getDeviceIssueInfoList(deviceId);
    boolean deviveInfoFound=false;
    DeviceInfo deviceInfo=null;
    // Checking whether request contains all require fields or not.
    if (null == userId || "".equals(userId.trim()) || 0 == deviceId)
    {
      return badRequest();
    }
    
    if(byAdmin)
    {
      for(DeviceIssueInfo deviceIssueInfo : deviceIssueInfoList)
      {
        if(deviceIssueInfo.getIssueId().equals(userId) && deviceIssueInfo.getSubmitTime()==null)
        {
          deviveInfoFound=true;
          tempDeviceIssueInfo=deviceIssueInfo;
          break;
        }
      }
      if(deviveInfoFound)
      {
        tempDeviceIssueInfo.setSubmitTime(CommonUtils.getCurrentDate());
        tempDeviceIssueInfo.setSubmitByAdmin(byAdmin);
        deviceIssueInfoDao.updateDeviceIssueInfo(tempDeviceIssueInfo);
     
        deviceInfo= deviceInfoDao.getDeviceInfo(deviceId);
        deviceInfo.setIsAdminApproved(Constants.DEVICE_INFO_ADMIN_AVAILABLE);
        deviceInfoDao.updateDeviceInfo(deviceInfo);
      }
      else
      {
        responseCode.setCode(Constants.CODE_SUCCESS);
        responseCode.setMessage(Constants.MESSAGE_DEVICE_ISSUED_TO_OTHER_USER);
        response.setResponseCode(responseCode);
        response.setResult(result);
        return response;
      }
    }
    else
    {
      //
      boolean flag=false;
      for(DeviceIssueInfo deviceIssueInfo : deviceIssueInfoList)
      {
        if(deviceIssueInfo.getIssueId().equals(userId) && deviceIssueInfo.getSubmitTime()==null)
        {
          flag=true;
          tempDeviceIssueInfo=deviceIssueInfo;
          break;
        }
      }
      
      if(flag)
      {
        responseCode.setCode(Constants.CODE_SUCCESS);
        responseCode.setMessage(tempDeviceIssueInfo.getIssueId());
        response.setResponseCode(responseCode);
        response.setResult(result);
        return response; 
      }
      else
      {
        
        tempDeviceIssueInfo.setSubmitTime(CommonUtils.getCurrentDate());
        tempDeviceIssueInfo.setSubmitByAdmin(false);
        deviceIssueInfoDao.updateDeviceIssueInfo(tempDeviceIssueInfo);
        
        createDeviceIssueInfo(deviceId, userId, false);
        flag=true;
      }
      
    }
    
    for(DeviceIssueInfo deviceIssueInfo : deviceIssueInfoList)
    {
      if(deviceIssueInfo.getIssueId().equals(userId))
      {
        deviveInfoFound=true;
      }
    }
    if(!deviveInfoFound)
    {
      newDeviceIssueInfo=new DeviceIssueInfo();
      newDeviceIssueInfo.setDeviceId(deviceId);
      newDeviceIssueInfo.setUserId(userId);
      newDeviceIssueInfo.setIssueTime(CommonUtils.getCurrentDate());
      newDeviceIssueInfo.setIssueId(issueIdGenerator(deviceId,userId));
      deviceIssueInfoDao.createDeviceIssueInfo(newDeviceIssueInfo);
    }
    else
    {
      boolean flag=false;
      for(DeviceIssueInfo deviceIssueInfo : deviceIssueInfoList)
      {
        if(deviceIssueInfo.getUserId().equals(userId) && deviceIssueInfo.getSubmitTime()==null)
        {
          deviceIssueInfo.setSubmitTime(CommonUtils.getCurrentDate());
          deviceIssueInfoDao.updateDeviceIssueInfo(deviceIssueInfo);
          flag=true;
          break;
        }
      }
      if(!flag)
      {
        //Need to implement , where submit time is not null
      }
    }
    return response;
  }
  
  private String issueIdGenerator(long deviceId, String userId)
  {
    return deviceId+"_"+CommonUtils.getTime();
  }
 

  private void createDeviceIssueInfo(long deviceId, String userId, boolean byAdmin)
  {
    DeviceIssueInfo newDeviceIssueInfo = new DeviceIssueInfo();
    newDeviceIssueInfo.setDeviceId(deviceId);
    newDeviceIssueInfo.setUserId(userId);
    newDeviceIssueInfo.setIssueTime(CommonUtils.getCurrentDate());
    newDeviceIssueInfo.setIssueId(issueIdGenerator(deviceId, userId));
    newDeviceIssueInfo.setIssueByAdmin(byAdmin);
    deviceIssueInfoDao.createDeviceIssueInfo(newDeviceIssueInfo);
    //Update Device Status
    DeviceInfo deviceInfo=deviceInfoDao.getDeviceInfo(deviceId);
    deviceInfo.setIsAdminApproved(Constants.DEVICE_INFO_ISSUED_TO_USER);
    deviceInfoDao.updateDeviceInfo(deviceInfo);
   }

  
  private EnigmaResponse deviceNotRegisteredResponse()
  {
    responseCode.setCode(Constants.CODE_NOT_FOUND);
    responseCode.setMessage(Constants.MESSAGE_NOT_FOUND_DEVICE);
    response.setResponseCode(responseCode);
    return response;
  }

  private EnigmaResponse userNotExistingResponse()
  {
    responseCode.setCode(Constants.CODE_AUTHENTICATION_FAILD);
    responseCode.setMessage(Constants.USER_NOT_EXISTING);
    response.setResponseCode(responseCode);
    return response;
  }

  private EnigmaResponse badRequest()
  {
    responseCode.setCode(Constants.CODE_BAD_REQUEST);
    responseCode.setMessage(Constants.MESSAGE_BAD_REQUEST);
    response.setResponseCode(responseCode);
    return response;
  }
  
  private boolean isUserExisting(String userId) {
    UserInfo userInfo = userInfoDao.getUserInfo(userId);
    if (null != userInfo) {
      return true;
    }
    return false;
  }

}
