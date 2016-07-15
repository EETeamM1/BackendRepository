package com.ee.enigma.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.Constants;
import com.ee.enigma.dao.DeviceInfoDao;
import com.ee.enigma.dao.DeviceIssueHelper;
import com.ee.enigma.dao.DeviceIssueInfoDao;
import com.ee.enigma.dao.UserInfoDao;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.DeviceIssueInfo;
import com.ee.enigma.dto.DeviceIssueStatusDto;
import com.ee.enigma.dto.DeviceIssueTrendLineDto;
import com.ee.enigma.dto.DeviceStatusCountsInfo;
import com.ee.enigma.dto.IssueTrendLineData;
import com.ee.enigma.dto.ReportInfo;
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
  String reportType=null;
  String beginDateString=null;
  String endDateString=null;
  Date beginDate=null;
  Date endDate=null;
  
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
  
  public DeviceIssueTrendLineDto getDeviceIssueTimeLineTrendReport(
    String beginDateString,
    String endDateString,
    String reportType)
  {
    DeviceIssueTrendLineDto deviceIssueTrendLineDto = null;
    try
    {
      if (reportType == null) reportType = Constants.DEVICE_ISSUE_ALL;
      beginDate = CommonUtils.getSqlDateByString(beginDateString);
      endDate = CommonUtils.getSqlDateByString(endDateString);
      if (beginDate == null) endDate = null;
    }
    catch (Exception e)
    {
      // logger.error(e);
    }
    if (beginDate == null)
    {
      endDate = CommonUtils.getCurrentDate();
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.MONTH, -1);
      beginDate = new java.sql.Date(cal.getTimeInMillis());
    }
    DeviceIssueHelper deviceIssueHelper = new DeviceIssueHelper();
    List<DeviceIssueInfo> deviceIssueInfoList = null;
    deviceIssueInfoList = deviceIssueInfoDao.getDeviceIssueList(beginDate, endDate, reportType);
    if (Constants.DEVICE_ISSUE.equalsIgnoreCase(reportType))
    {
      deviceIssueTrendLineDto = deviceIssueHelper
        .buildDeviceIssueTimeLineTrendReport(deviceIssueInfoList);
    }
    else if (Constants.DEVICE_SUBMIT.equalsIgnoreCase(reportType))
    {
      deviceIssueTrendLineDto = deviceIssueHelper
        .buildDeviceSubmitTimeLineTrendReport(deviceIssueInfoList);

    }
    if (deviceIssueTrendLineDto != null)
    {
      deviceIssueTrendLineDto.setStartDate(beginDate);
      deviceIssueTrendLineDto.setEndDate(endDate);
    }
    return deviceIssueTrendLineDto;
  }
  
    public JSONObject getDeviceTimeLineReport(
    String beginDateString,
    String endDateString,
    String deviceId){
    try
    {
      beginDate= CommonUtils.getSqlDateByString(beginDateString);
      endDate= CommonUtils.getSqlDateByString(endDateString);
      if(beginDate==null)
        endDate=null;
    }
    catch (Exception e)
    {
     // logger.error(e);
    }
    if(beginDate==null)
    {
      endDate=CommonUtils.getCurrentDate();
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.MONTH, -1);
      beginDate=new java.sql.Date(cal.getTimeInMillis()); 
    }
    if(endDate==null)
      endDate=beginDate;
    JSONObject jsonObject=null;
    DeviceIssueHelper deviceIssueHelper=new DeviceIssueHelper();
    List<DeviceIssueInfo> jsonObjectList= deviceIssueInfoDao.getDeviceIssueList(deviceId, beginDate, endDate);
    jsonObject=deviceIssueHelper.buildDeviceTimeLineReport(jsonObjectList);
    return jsonObject;
  }
  

   
  public EnigmaResponse deviceIssueInfoService(Request deviceIssueInfoRequest)
  {
    response = new EnigmaResponse();
    responseCode = new ResponseCode();
    result = new ResponseResult();
    String userId;
    String deviceId;
    Boolean byAdmin;

    try
    {
      deviceId = deviceIssueInfoRequest.getParameters().getDeviceId();
      userId = deviceIssueInfoRequest.getParameters().getUserId();
      byAdmin = deviceIssueInfoRequest.getParameters().getByAdmin();

    }
    catch (Exception e)
    {
     // logger.error(e);
      return badRequest();
    }

    // Checking whether request contains all require fields or not.
    if (null == userId || "".equals(userId.trim()) || null == deviceId || null==byAdmin)
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
            deviceIssue.setSubmitTime(CommonUtils.getCurrentDateTime());
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

  
 
  public EnigmaResponse submitDevice(Request deviceIssueInfoRequest)
  {
    response = new EnigmaResponse();
    responseCode = new ResponseCode();
    result = new ResponseResult();
    String userId;
    String deviceId;
    Boolean byAdmin;

    try
    {
      deviceId = deviceIssueInfoRequest.getParameters().getDeviceId();
      userId = deviceIssueInfoRequest.getParameters().getUserId();
      byAdmin = deviceIssueInfoRequest.getParameters().getByAdmin();
    }
    catch (Exception e)
    {
      logger.error(e);
      return badRequest();
    }
    
    //submitted by user  -->> PD device is submmited but entry pending with admin ,
    //AV--> available
    //IS -->already issed
    DeviceIssueInfo tempDeviceIssueInfo=null;
    List<DeviceIssueInfo> deviceIssueInfoList = deviceIssueInfoDao.getDeviceIssueInfoList(deviceId);
    boolean deviveInfoFound=false;
   // Checking whether request contains all require fields or not.
    if (null == userId || "".equals(userId.trim()) || null == deviceId || null==byAdmin)
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
    
    if(byAdmin)
    {
      for(DeviceIssueInfo deviceIssueInfo : deviceIssueInfoList)
      {
        if(deviceIssueInfo.getUserId().equals(userId) && deviceIssueInfo.getSubmitTime()==null)
        {
          deviveInfoFound=true;
          tempDeviceIssueInfo=deviceIssueInfo;
          break;
        }
      }
      if(deviveInfoFound && Constants.DEVICE_STATUS_ISSUED.equals(deviceInfo.getDeviceAvailability()))
      {
        tempDeviceIssueInfo.setSubmitTime(CommonUtils.getCurrentDateTime());
        tempDeviceIssueInfo.setSubmitByAdmin(byAdmin);
        deviceIssueInfoDao.updateDeviceIssueInfo(tempDeviceIssueInfo);
     
        deviceInfo= deviceInfoDao.getDeviceInfo(deviceId);
        deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_AVAILABLE);
        deviceInfoDao.updateDeviceInfo(deviceInfo);
      }
      else if(!Constants.DEVICE_STATUS_ISSUED.equals(deviceInfo.getDeviceAvailability()))
      {
        responseCode.setCode(Constants.CODE_SUCCESS);
        responseCode.setMessage(Constants.MESSAGE_DEVICE_ALREADY_SUBMITTED);
        response.setResponseCode(responseCode);
        response.setResult(result);
        return response;
      }
    }
    else
    {
      boolean flag=false;
      for(DeviceIssueInfo deviceIssueInfo : deviceIssueInfoList)
      {
        if(deviceIssueInfo.getUserId().equals(userId) && deviceIssueInfo.getSubmitTime()==null)
        {
          flag=true;
          tempDeviceIssueInfo=deviceIssueInfo;
          break;
        }
      }
      //If same user is submitting device ,it will goes to pending status
      if(flag)
      {
        tempDeviceIssueInfo.setSubmitTime(CommonUtils.getCurrentDateTime());
        tempDeviceIssueInfo.setSubmitByAdmin(false);
        deviceIssueInfoDao.updateDeviceIssueInfo(tempDeviceIssueInfo);
        
        deviceInfo= deviceInfoDao.getDeviceInfo(deviceId);
        deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_PENDING);
        deviceInfoDao.updateDeviceInfo(deviceInfo);
        
        responseCode.setCode(Constants.CODE_SUCCESS);
        responseCode.setMessage(Constants.MESSAGE_DEVICE_SUBMITTED_TO_ADMIN_FOR_APPROVAL);
        response.setResponseCode(responseCode);
        response.setResult(result);
        return response; 
      }
      else
      {
        responseCode.setCode(Constants.CODE_SUCCESS);
        responseCode.setMessage(Constants.MESSAGE_DEVICE_ALREADY_SUBMITTED);
        response.setResponseCode(responseCode);
        response.setResult(result);
        return response;  
      }
     
    }
    responseCode.setCode(Constants.CODE_SUCCESS);
    responseCode.setMessage(Constants.MESSAGE_DEVICE_SUBMITTED);
    response.setResponseCode(responseCode);
    response.setResult(result);
    return response;
  }
  
 
  
 @Override
 public String populateDeviceIssueInfo(String deviceId, String userId)
 {
   String issueId = null;
   DeviceIssueInfo newDeviceIssueInfo = null;
   List<DeviceIssueInfo> deviceIssueInfoList = deviceIssueInfoDao.getDeviceIssueInfoList(deviceId);
   DeviceIssueInfo deviceIssueInfoTemp = null;
   if (deviceIssueInfoList != null && deviceIssueInfoList.size() > 0)
   {
     deviceIssueInfoTemp = deviceIssueInfoList.get(0);
   }
   else
   {
     newDeviceIssueInfo = new DeviceIssueInfo();
     newDeviceIssueInfo.setDeviceId(deviceId);
     newDeviceIssueInfo.setUserId(userId);
     newDeviceIssueInfo.setIssueTime(CommonUtils.getCurrentDateTime());
     newDeviceIssueInfo.setIssueId(issueIdGenerator(deviceId, userId));
     newDeviceIssueInfo.setIssueByAdmin(false);
     deviceIssueInfoDao.createDeviceIssueInfo(newDeviceIssueInfo); 
     updateDeviceInfo(deviceId, Constants.DEVICE_STATUS_ISSUED);
     deviceIssueInfoTemp=newDeviceIssueInfo;
   }
   // If userId is same means same user is logged
   if (userId.equals(deviceIssueInfoTemp.getUserId()))
   {
     if (deviceIssueInfoTemp.getSubmitTime() == null)
     {
     //issued to current user
     }
     else
     {
       newDeviceIssueInfo = new DeviceIssueInfo();
       newDeviceIssueInfo.setDeviceId(deviceId);
       newDeviceIssueInfo.setUserId(userId);
       newDeviceIssueInfo.setIssueTime(CommonUtils.getCurrentDateTime());
       newDeviceIssueInfo.setIssueId(issueIdGenerator(deviceId, userId));
       newDeviceIssueInfo.setIssueByAdmin(false);
       deviceIssueInfoDao.createDeviceIssueInfo(newDeviceIssueInfo);
       // Update Device status
       updateDeviceInfo(deviceId, Constants.DEVICE_STATUS_ISSUED);
     }
   }
   else
   {
     // Other user logged in
     // 1. submit device (update submit time)
     // 2. issue device to new user
     if (deviceIssueInfoTemp.getSubmitTime() == null)
     {
       deviceIssueInfoTemp.setSubmitTime(CommonUtils.getCurrentDateTime());
       deviceIssueInfoTemp.setSubmitByAdmin(false);
       deviceIssueInfoDao.updateDeviceIssueInfo(deviceIssueInfoTemp);
     }
     else
     {
       // Do Nothing
     }
     // Issue device to other user
     newDeviceIssueInfo = new DeviceIssueInfo();
     newDeviceIssueInfo.setDeviceId(deviceId);
     newDeviceIssueInfo.setUserId(userId);
     newDeviceIssueInfo.setIssueTime(CommonUtils.getCurrentDateTime());
     newDeviceIssueInfo.setIssueId(issueIdGenerator(deviceId, userId));
     newDeviceIssueInfo.setIssueByAdmin(false);
     deviceIssueInfoDao.createDeviceIssueInfo(newDeviceIssueInfo);
     // Update Device status
     updateDeviceInfo(deviceId, Constants.DEVICE_STATUS_ISSUED);
   }
   deviceIssueInfoList = deviceIssueInfoDao.getDeviceIssueInfoList(deviceId);
   if (deviceIssueInfoList != null && deviceIssueInfoList.size() > 0)
   {
     deviceIssueInfoTemp = deviceIssueInfoList.get(0);
     if (deviceIssueInfoTemp != null && deviceIssueInfoTemp.getIssueId() != null)
     {
       issueId = deviceIssueInfoTemp.getIssueId();
     }
   }
   return issueId;
 } 

 protected void updateDeviceInfo(String deviceId, String deviceAvailabilityStatus)
 {
   DeviceInfo deviceInfo = deviceInfoDao.getDeviceInfo(deviceId);
   deviceInfo.setDeviceAvailability(deviceAvailabilityStatus);
   deviceInfoDao.updateDeviceInfo(deviceInfo);
 }

 public EnigmaResponse getDeviceReportAvailability(){
   response = new EnigmaResponse();
   responseCode = new ResponseCode();
   result = new ResponseResult();
   
   DeviceIssueHelper deviceIssueHelper=new DeviceIssueHelper();
   
   List<DeviceInfo> deviceInfoList= deviceInfoDao.getDevicesList();
   DeviceStatusCountsInfo info=deviceIssueHelper.buildDeviceReportAvailability(deviceInfoList);
   responseCode.setResultObject(info);
   responseCode.setCode(Constants.CODE_SUCCESS);
   responseCode.setMessage(Constants.MESSAGE_SUCCESS);
   response.setResponseCode(responseCode);
   response.setResult(result);
   return response;
 }
 
  public DeviceIssueStatusDto getDeviceIssueStatusForDevice(String deviceId)
  {
    DeviceIssueStatusDto deviceIssueStatusDto = null;
    List<DeviceIssueInfo> deviceIssueInfoList = deviceIssueInfoDao.getDeviceIssueInfoList(deviceId);
    DeviceIssueInfo deviceIssueInfo = null;
    UserInfo userInfo = null;
    DeviceInfo deviceInfo = null;
    if (deviceIssueInfoList != null && deviceIssueInfoList.size() > 0)
    {
      deviceIssueInfo = deviceIssueInfoList.get(0);
    }
    deviceInfo = deviceInfoDao.getDeviceInfo(deviceId);
    if (deviceIssueInfo != null && deviceIssueInfo.getUserId() != null)
    {
      userInfo = userInfoDao.getUserInfo(deviceIssueInfo.getUserId());
    }
    if (deviceIssueInfo != null)
    {
      deviceIssueStatusDto = new DeviceIssueStatusDto();
      deviceIssueStatusDto.setDeviceId(deviceId);
      deviceIssueStatusDto.setAvailablityStatus(deviceInfo.getDeviceAvailability());
      if (userInfo != null)
      {
        deviceIssueStatusDto.setUserId(userInfo.getUserId());
        deviceIssueStatusDto.setUserName(userInfo.getPassword());
      }
    }
    return deviceIssueStatusDto;
  }
 public EnigmaResponse getDevicesIssueReportByStatus(){
   response = new EnigmaResponse();
   responseCode = new ResponseCode();
   result = new ResponseResult();
   DeviceIssueHelper deviceIssueHelper=new DeviceIssueHelper();
   List<DeviceInfo> deviceInfoList= deviceInfoDao.getDevicesList();
   List<DeviceIssueInfo> deviceIssueInfoList = deviceIssueInfoDao.getAllDeviceIssueInfoList();
   List<ReportInfo> jsonObjectList= deviceIssueHelper.buildDevicesListByStatus(deviceIssueInfoList,deviceInfoList);
   responseCode.setResultObject(jsonObjectList);
   CommonUtils.updateResponse(response, responseCode, Constants.MESSAGE_SUCCESS, Constants.CODE_SUCCESS);
   response.setResult(result);
   return response;
 }
 
  private String issueIdGenerator(String deviceId, String userId)
  {
    return deviceId+"_"+CommonUtils.getTime();
  }
 
  protected void createDeviceIssueInfo(String deviceId, String userId, boolean byAdmin)
  {
    DeviceIssueInfo newDeviceIssueInfo = new DeviceIssueInfo();
    newDeviceIssueInfo.setDeviceId(deviceId);
    newDeviceIssueInfo.setUserId(userId);
    newDeviceIssueInfo.setIssueTime(CommonUtils.getCurrentDateTime());
    newDeviceIssueInfo.setIssueId(issueIdGenerator(deviceId, userId));
    newDeviceIssueInfo.setIssueByAdmin(byAdmin);
    deviceIssueInfoDao.createDeviceIssueInfo(newDeviceIssueInfo);
    //Update Device Status
    DeviceInfo deviceInfo=deviceInfoDao.getDeviceInfo(deviceId);
    deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_ISSUED);
    deviceInfoDao.updateDeviceInfo(deviceInfo);
    
    responseCode.setCode(Constants.CODE_SUCCESS);
    responseCode.setMessage(Constants.MESSAGE_DEVICE_SUCCESSFULLY_ISSUED);
    response.setResponseCode(responseCode); 
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
