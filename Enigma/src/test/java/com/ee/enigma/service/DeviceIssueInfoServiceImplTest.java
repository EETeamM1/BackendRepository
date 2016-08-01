package com.ee.enigma.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.ee.enigma.common.Constants;
import com.ee.enigma.common.JunitConstants;
import com.ee.enigma.dao.DeviceInfoDao;
import com.ee.enigma.dao.DeviceIssueInfoDaoImpl;
import com.ee.enigma.dao.UserInfoDaoImpl;
import com.ee.enigma.dto.DeviceIssueStatusDto;
import com.ee.enigma.dto.DeviceIssueTrendLineDto;
import com.ee.enigma.dto.DeviceStatusCountsInfo;
import com.ee.enigma.dto.ReportInfo;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.DeviceIssueInfo;
import com.ee.enigma.model.UserActivity;
import com.ee.enigma.model.UserInfo;
import com.ee.enigma.request.Request;
import com.ee.enigma.request.RequestParameters;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.response.ResponseCode;
import com.ee.enigma.response.ResponseResult;

@RunWith(MockitoJUnitRunner.class)
public class DeviceIssueInfoServiceImplTest
{

  @InjectMocks
  private DeviceIssueInfoServiceImpl deviceIssueInfoServiceImpl;

  @Mock
  private UserInfoDaoImpl userInfoDaoImpl;
  @Mock
  private SessionFactory sessionFactory;
  @Mock
  private Session session;
  @Mock
  private DeviceInfoDao deviceInfoDao;
  @Mock
  private DeviceIssueInfoDaoImpl deviceIssueInfoDao;

  private EnigmaResponse response;
  private ResponseCode responseCode;
  private ResponseResult result;
  private Request requestInfo = null;
  private RequestParameters parameters;
  private UserInfo userInfo;
  private DeviceInfo deviceInfo;
  EnigmaResponse enigmaResponse =null;
  @Before
  public void setUp() throws Exception
  {
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(sessionFactory.openSession()).thenReturn(session);

  }

  public void initializeDeviceIssueInfoService()
  {
    parameters = new RequestParameters();
    requestInfo = new Request();
    response = new EnigmaResponse();

    requestInfo.setParameters(parameters);
    parameters.setUserId(JunitConstants.USER_ID);
    parameters.setDeviceId(JunitConstants.DEVICE_ID);
    parameters.setByAdmin(JunitConstants.BY_ADMIN_FALSE);

    userInfo = new UserInfo();
    userInfo.setUserId(JunitConstants.USER_ID);
    userInfo.setPassword(JunitConstants.PASSWORD);
    userInfo.setUserName(JunitConstants.USER_NAME);

    deviceInfo = new DeviceInfo();
    deviceInfo.setDeviceId(JunitConstants.DEVICE_ID);
    deviceInfo.setDeviceAvailability(Constants.DEVICE_ISSUE);
    Mockito.doReturn(userInfo).when(userInfoDaoImpl).getUserInfo(Matchers.anyString());
    Mockito.doNothing().when(deviceIssueInfoDao)
      .createDeviceIssueInfo(Matchers.any(DeviceIssueInfo.class));
    Mockito.doNothing().when(deviceInfoDao).updateDeviceInfo(Matchers.any(DeviceInfo.class));

    Mockito.doReturn(deviceInfo).when(deviceInfoDao).getDeviceInfo(Matchers.anyString());
  }

  @BeforeClass
  public static void init() throws Exception
  {
  }
  
  @Test
  public void testBuildDeviceReportAvailability() throws Exception
  {
    List<DeviceInfo> deviceInfoList=new ArrayList<DeviceInfo>();
    String deviceId=JunitConstants.DEVICE_ID;
    DeviceInfo deviceInfo=null;
    deviceInfo= populateDeviceInfo(deviceId, Constants.DEVICE_STATUS_AVAILABLE);
    deviceInfoList.add(deviceInfo);
    deviceInfo= populateDeviceInfo(deviceId, Constants.DEVICE_STATUS_ISSUED);
    deviceInfoList.add(deviceInfo);
    deviceInfo= populateDeviceInfo(deviceId, Constants.DEVICE_STATUS_PENDING);
    deviceInfoList.add(deviceInfo);
    Mockito.doReturn(deviceInfoList).when(deviceInfoDao).getDevicesList();
    EnigmaResponse enigmaResponse= deviceIssueInfoServiceImpl.getDeviceReportAvailability();
    DeviceStatusCountsInfo info=(DeviceStatusCountsInfo) enigmaResponse.getResponseCode().getResultObject();
    Assert.assertTrue(info.getAvailableDevices()==1);
    Assert.assertTrue(info.getTotalDevices()==3);
  }
  
  @Test
  public void testPopulateDeviceIssueInfo() throws Exception
  {
    Mockito.doReturn(populateDeviceIssueInfoList(new Timestamp(11111),JunitConstants.USER_ID)).when(deviceIssueInfoDao).getDeviceIssueInfoList(Matchers.anyString());
    String userId=JunitConstants.USER_ID;
    String deviceId=JunitConstants.DEVICE_ID;
    Mockito.doReturn(new DeviceInfo()).when(deviceInfoDao).getDeviceInfo(Matchers.anyString());
    String issueId = deviceIssueInfoServiceImpl.populateDeviceIssueInfo(deviceId,userId);
    Assert.assertTrue(issueId.equals(JunitConstants.ISSUE_ID));
  }
  
  @Test
  public void testPopulateDeviceIssueInfoSubmitTimeNull() throws Exception
  {
    String userId=JunitConstants.USER_ID;
    String deviceId=JunitConstants.DEVICE_ID;
    Mockito.doReturn(populateDeviceIssueInfoList(null,JunitConstants.USER_ID)).when(deviceIssueInfoDao).getDeviceIssueInfoList(Matchers.anyString());
    String issueId = deviceIssueInfoServiceImpl.populateDeviceIssueInfo(deviceId,userId);
    Assert.assertTrue(issueId.equals(JunitConstants.ISSUE_ID));
  }
  
  @Test
  public void testPopulateDeviceIssueInfoWithNoDeviceIssueInfo() throws Exception
  {
    String userId=JunitConstants.USER_ID;
    String deviceId=JunitConstants.DEVICE_ID;
    Mockito.doReturn(new ArrayList<DeviceIssueInfo>()).when(deviceIssueInfoDao).getDeviceIssueInfoList(Matchers.anyString());
    Mockito.doNothing().when(deviceIssueInfoDao)
    .createDeviceIssueInfo(Matchers.any(DeviceIssueInfo.class));
    Mockito.doNothing().when(deviceInfoDao).updateDeviceInfo(Matchers.any(DeviceInfo.class));
    Mockito.doReturn(new DeviceInfo()).when(deviceInfoDao).getDeviceInfo(Matchers.anyString());
    deviceIssueInfoServiceImpl.populateDeviceIssueInfo(deviceId,userId);
     Mockito.verify(deviceIssueInfoDao, Mockito.times(1)).createDeviceIssueInfo(
       Matchers.any(DeviceIssueInfo.class));
    }
  @Test
  public void testPopulateDeviceIssueInfoWithDifferentUserId() throws Exception
  {
    String userId=JunitConstants.USER_ID+1;
    String deviceId=JunitConstants.DEVICE_ID;
    Mockito.doReturn(populateDeviceIssueInfoList(new Timestamp(2222),JunitConstants.USER_ID)).when(deviceIssueInfoDao).getDeviceIssueInfoList(Matchers.anyString());
    Mockito.doNothing().when(deviceIssueInfoDao)
    .createDeviceIssueInfo(Matchers.any(DeviceIssueInfo.class));
    Mockito.doNothing().when(deviceInfoDao).updateDeviceInfo(Matchers.any(DeviceInfo.class));
    Mockito.doReturn(new DeviceInfo()).when(deviceInfoDao).getDeviceInfo(Matchers.anyString());
    deviceIssueInfoServiceImpl.populateDeviceIssueInfo(deviceId,userId);
    Mockito.verify(deviceIssueInfoDao, Mockito.atLeast(1)).createDeviceIssueInfo(
      Matchers.any(DeviceIssueInfo.class));
  
  }
  @Test
  public void testPopulateDeviceIssueInfoWithDifferentUserIdAndNullSubmitTime() throws Exception
  {
    String userId=JunitConstants.USER_ID+1;
    String deviceId=JunitConstants.DEVICE_ID;
    Mockito.doReturn(populateDeviceIssueInfoList(null,JunitConstants.USER_ID)).when(deviceIssueInfoDao).getDeviceIssueInfoList(Matchers.anyString());
    Mockito.doNothing().when(deviceIssueInfoDao)
    .createDeviceIssueInfo(Matchers.any(DeviceIssueInfo.class));
    Mockito.doNothing().when(deviceInfoDao).updateDeviceInfo(Matchers.any(DeviceInfo.class));
    Mockito.doReturn(new DeviceInfo()).when(deviceInfoDao).getDeviceInfo(Matchers.anyString());
     String issueId = deviceIssueInfoServiceImpl.populateDeviceIssueInfo(deviceId,userId);
     Mockito.verify(deviceIssueInfoDao, Mockito.atLeast(1)).updateDeviceIssueInfo(
       Matchers.any(DeviceIssueInfo.class));
     Mockito.verify(deviceIssueInfoDao, Mockito.atLeast(1)).createDeviceIssueInfo(
       Matchers.any(DeviceIssueInfo.class));
  }
  
  @Test
  public void testGetDeviceIssueTimeLineTrendReportForSubmitDevice() throws Exception
  {
    initializeDeviceIssueInfoService();
    String beginDateString="";
    String endDateString="";
    String reportType= Constants.DEVICE_SUBMIT;
     List<DeviceIssueInfo> deviceIssueInfoList = null;
    DeviceIssueInfo deviceIssueInfo = null;
    
    deviceIssueInfoList = new ArrayList<DeviceIssueInfo>();
    DeviceInfo deviceInfo=null;
    deviceInfo=new DeviceInfo();
    deviceIssueInfo = new DeviceIssueInfo();
    deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_ISSUED);
    deviceIssueInfo.setDeviceInfo(deviceInfo);
    deviceIssueInfo.setUserId(JunitConstants.USER_ID);
    deviceIssueInfo.setSubmitTime(new Timestamp(1111));
    //deviceIssueInfo.setSubmitByAdmin(new Boolean(true));
    deviceIssueInfoList.add(deviceIssueInfo);
    
    deviceIssueInfo = new DeviceIssueInfo();
    deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_ISSUED);
    deviceIssueInfo.setDeviceInfo(deviceInfo);
    deviceIssueInfo.setIssueTime(new Timestamp(1111));
       deviceIssueInfo.setUserId(JunitConstants.USER_ID);
    deviceIssueInfo.setSubmitTime(new Timestamp(1111));
   // deviceIssueInfo.setSubmitByAdmin(new Boolean(false));
    deviceIssueInfoList.add(deviceIssueInfo);
    
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao)
      .getDeviceIssueList(Mockito.any(java.sql.Date.class),Mockito.any(java.sql.Date.class),Matchers.anyString());
    
    DeviceIssueTrendLineDto deviceIssueTrendLineDto = deviceIssueInfoServiceImpl.getDeviceIssueTimeLineTrendReport(beginDateString,endDateString,reportType);
    Assert.assertTrue(deviceIssueTrendLineDto.getEndDate()!=null);
    Assert.assertTrue(deviceIssueTrendLineDto.getStartDate()!=null);
    Assert.assertTrue(deviceIssueTrendLineDto.getIssueTrendLineData()!=null && deviceIssueTrendLineDto.getIssueTrendLineData().size()>0);
  }
  @Test
  public void testGetDeviceIssueTimeLineTrendReportForIssueDevice() throws Exception
  {
    initializeDeviceIssueInfoService();
    String beginDateString="";
    String endDateString="";
    String reportType= Constants.DEVICE_ISSUE;
     List<DeviceIssueInfo> deviceIssueInfoList = null;
    DeviceIssueInfo deviceIssueInfo = null;
    
    deviceIssueInfoList = new ArrayList<DeviceIssueInfo>();
    DeviceInfo deviceInfo=null;
    deviceInfo=new DeviceInfo();
    deviceIssueInfo = new DeviceIssueInfo();
    deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_ISSUED);
    deviceIssueInfo.setDeviceInfo(deviceInfo);
    deviceIssueInfo.setUserId(JunitConstants.USER_ID);
    deviceIssueInfo.setIssueTime(new Timestamp(1111));
    deviceIssueInfo.setIssueByAdmin(new Boolean(true));
    deviceIssueInfo.setSubmitTime(null);
    deviceIssueInfoList.add(deviceIssueInfo);
    
    deviceIssueInfo = new DeviceIssueInfo();
    deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_ISSUED);
    deviceIssueInfo.setDeviceInfo(deviceInfo);
    deviceIssueInfo.setIssueTime(new Timestamp(1111));
    deviceIssueInfo.setIssueByAdmin(new Boolean(false));
    deviceIssueInfo.setUserId(JunitConstants.USER_ID);
    deviceIssueInfo.setSubmitTime(null);
    deviceIssueInfoList.add(deviceIssueInfo);
    
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao)
      .getDeviceIssueList(Mockito.any(java.sql.Date.class),Mockito.any(java.sql.Date.class),Matchers.anyString());
    
    DeviceIssueTrendLineDto deviceIssueTrendLineDto = deviceIssueInfoServiceImpl.getDeviceIssueTimeLineTrendReport(beginDateString,endDateString,reportType);
    Assert.assertTrue(deviceIssueTrendLineDto.getEndDate()!=null);
    Assert.assertTrue(deviceIssueTrendLineDto.getStartDate()!=null);
    Assert.assertTrue(deviceIssueTrendLineDto.getIssueTrendLineData()!=null && deviceIssueTrendLineDto.getIssueTrendLineData().size()>0);
  }
  
  
  @Test
  public void testSubmitDeviceNotFountDevice() throws Exception
  {
    initializeDeviceIssueInfoService();
    List<DeviceIssueInfo> deviceIssueInfoList = null;
    
    DeviceIssueInfo deviceIssueInfo = null;
    parameters.setByAdmin(JunitConstants.BY_ADMIN_TRUE);
    enigmaResponse = deviceIssueInfoServiceImpl.submitDevice(requestInfo);
    //Assert.assertTrue(Constants.MESSAGE_DEVICE_WAS_NOT_ISSUED.equals(enigmaResponse.getResponseCode().getMessage() ));
    
    deviceIssueInfoList = new ArrayList<DeviceIssueInfo>();
    deviceIssueInfo = new DeviceIssueInfo();
    deviceIssueInfo.setUserId(JunitConstants.USER_ID);
    deviceIssueInfoList.add(deviceIssueInfo);
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao)
      .getDeviceIssueInfoList(Matchers.anyString());
    enigmaResponse = deviceIssueInfoServiceImpl.submitDevice(requestInfo);
    Assert.assertTrue(Constants.MESSAGE_DEVICE_SUBMITTED.equals(enigmaResponse.getResponseCode().getMessage()));
    
    deviceIssueInfoList = new ArrayList<DeviceIssueInfo>();
    DeviceInfo deviceInfo=null;
    deviceInfo=new DeviceInfo();
    deviceIssueInfo = new DeviceIssueInfo();
    deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_ISSUED);
    deviceIssueInfo.setDeviceInfo(deviceInfo);
    deviceIssueInfo.setUserId(JunitConstants.USER_ID);
    deviceIssueInfo.setSubmitTime(null);
    deviceIssueInfoList.add(deviceIssueInfo);
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao)
      .getDeviceIssueInfoList(Matchers.anyString());
    
    Mockito.doReturn(deviceInfo).when(deviceInfoDao).getDeviceInfo(Matchers.anyString());
    enigmaResponse = deviceIssueInfoServiceImpl.submitDevice(requestInfo);
    Mockito.verify(deviceIssueInfoDao, Mockito.atLeast(1)).updateDeviceIssueInfo(
      Matchers.any(DeviceIssueInfo.class));
  }
  
   @Test
  public void testSubmitDevice() throws Exception
  {
    initializeDeviceIssueInfoService();
    EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.submitDevice(requestInfo);
    Assert.assertTrue(Constants.MESSAGE_DEVICE_WAS_NOT_ISSUED.equals(enigmaResponse.getResponseCode().getMessage()));
    
    List<DeviceIssueInfo> deviceIssueInfoList = new ArrayList<DeviceIssueInfo>();
    DeviceIssueInfo deviceIssueInfo = null;
    deviceIssueInfo = new DeviceIssueInfo();
    deviceIssueInfo.setUserId(JunitConstants.USER_ID);
    deviceIssueInfoList.add(deviceIssueInfo);
    deviceIssueInfo = new DeviceIssueInfo();
    deviceIssueInfo.setUserId(JunitConstants.USER_ID);
    deviceIssueInfoList.add(deviceIssueInfo);
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao)
      .getDeviceIssueInfoList(Matchers.anyString());
    enigmaResponse = deviceIssueInfoServiceImpl.submitDevice(requestInfo);
    Assert.assertTrue(enigmaResponse.getResponseCode().getMessage() == Constants.MESSAGE_DEVICE_SUBMITTED_TO_ADMIN_FOR_APPROVAL); 
    
    Mockito.doReturn(null).when(deviceInfoDao).getDeviceInfo(Matchers.anyString());
    enigmaResponse = deviceIssueInfoServiceImpl.submitDevice(requestInfo);
    Assert.assertTrue(enigmaResponse.getResponseCode().getCode() == Constants.CODE_NOT_FOUND);
    
    requestInfo.getParameters().setDeviceId(null);
    enigmaResponse = deviceIssueInfoServiceImpl.submitDevice(requestInfo);
    Assert.assertTrue(enigmaResponse.getResponseCode().getMessage() == Constants.MESSAGE_BAD_REQUEST);
  
    Mockito.doReturn(deviceInfo).when(deviceInfoDao).getDeviceInfo(Matchers.anyString());
    requestInfo.getParameters().setDeviceId(JunitConstants.DEVICE_ID);
    Mockito.doReturn(null).when(userInfoDaoImpl).getUserInfo(Matchers.anyString());
    enigmaResponse = deviceIssueInfoServiceImpl.submitDevice(requestInfo);
    Assert
      .assertTrue(enigmaResponse.getResponseCode().getCode() == Constants.CODE_AUTHENTICATION_FAILD);
  }
  
  @Test
  public void testDeviceIssueInfoService() throws Exception
  {
    initializeDeviceIssueInfoService();
    EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.deviceIssueInfoService(requestInfo);
    Assert.assertTrue(enigmaResponse.getResponseCode().getCode() == Constants.CODE_SUCCESS);
  }

  @Test
  public void testDeviceIssueInfoServiceUserIdNull() throws Exception
  {
    initializeDeviceIssueInfoService();
    parameters.setUserId(null);
    EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.deviceIssueInfoService(requestInfo);
    Assert.assertTrue(enigmaResponse.getResponseCode().getCode() == Constants.CODE_BAD_REQUEST);
  }

  @Test
  public void testDeviceIssueInfoServiceDeviceInfoIsNull() throws Exception
  {
    initializeDeviceIssueInfoService ();
    Mockito.doReturn(null).when(deviceInfoDao).getDeviceInfo(Matchers.anyString());
    EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.deviceIssueInfoService(requestInfo);
    Assert.assertTrue(enigmaResponse.getResponseCode().getCode() == Constants.CODE_NOT_FOUND);
  }

  @Test
  public void testDeviceIssueInfoServiceUserInfoIsNotNull() throws Exception
  {
    initializeDeviceIssueInfoService();
    EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.deviceIssueInfoService(requestInfo);
    Assert.assertTrue(enigmaResponse.getResponseCode().getMessage()
      .equals(Constants.MESSAGE_DEVICE_SUCCESSFULLY_ISSUED));
  }

  @Test
  public void testDeviceIssueInfoServiceUserInfoIsNull() throws Exception
  {
    initializeDeviceIssueInfoService ();
     Mockito.doReturn(null).when(userInfoDaoImpl).getUserInfo(Matchers.anyString());
    EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.deviceIssueInfoService(requestInfo);
    Assert
      .assertTrue(enigmaResponse.getResponseCode().getCode() == Constants.CODE_AUTHENTICATION_FAILD);
  }

  @Test
  public void testDeviceIssueInfoServiceDeviceIssueInfoList() throws Exception
  {
    initializeDeviceIssueInfoService();
    List<DeviceIssueInfo> deviceIssueInfoList = new ArrayList<DeviceIssueInfo>();
    DeviceIssueInfo deviceIssueInfo = null;
    deviceIssueInfo = new DeviceIssueInfo();
    deviceIssueInfo.setUserId(JunitConstants.USER_ID);
    deviceIssueInfoList.add(deviceIssueInfo);
    deviceIssueInfo = new DeviceIssueInfo();
    deviceIssueInfo.setUserId(JunitConstants.USER_ID);
    deviceIssueInfoList.add(deviceIssueInfo);
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao)
      .getDeviceIssueInfoList(Matchers.anyString());
    EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.deviceIssueInfoService(requestInfo);
    Assert
      .assertTrue(enigmaResponse.getResponseCode().getMessage() == Constants.MESSAGE_DEVICE_ASSIGNED_TO_SAME_USER);
  }

  @Test
  public void testDeviceIssueInfoServiceDeviceIssueInfoListWithDifferentUserId() throws Exception
  {
    initializeDeviceIssueInfoService();
    parameters.setByAdmin(JunitConstants.BY_ADMIN_FALSE);
    userInfo.setUserId(JunitConstants.USER_ID + "1");
    deviceInfo.setDeviceId(JunitConstants.DEVICE_ID);
    deviceInfo.setDeviceAvailability(Constants.DEVICE_ISSUE);
    List<DeviceIssueInfo> deviceIssueInfoList = new ArrayList<DeviceIssueInfo>();
    DeviceIssueInfo deviceIssueInfo = null;
    deviceIssueInfo = new DeviceIssueInfo();
    deviceIssueInfo.setUserId(JunitConstants.USER_ID + "2");
    deviceIssueInfoList.add(deviceIssueInfo);
    deviceIssueInfo = new DeviceIssueInfo();
    deviceIssueInfo.setUserId(JunitConstants.USER_ID + "2");
    deviceIssueInfoList.add(deviceIssueInfo);
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao)
      .getDeviceIssueInfoList(Matchers.anyString());
    EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.deviceIssueInfoService(requestInfo);
    Assert.assertTrue(deviceIssueInfoList.get(0).getSubmitTime() != null);
  }

  @Test
  public void testDeviceIssueInfoServiceDeviceIssueInfoListWithDifferentUserIdByAdinnIsTrue()
    throws Exception
  {
    initializeDeviceIssueInfoService();
    parameters.setByAdmin(JunitConstants.BY_ADMIN_TRUE);
    userInfo.setUserId(JunitConstants.USER_ID + "1");
    List<DeviceIssueInfo> deviceIssueInfoList = new ArrayList<DeviceIssueInfo>();
    DeviceIssueInfo deviceIssueInfo = null;
    deviceIssueInfo = new DeviceIssueInfo();
    deviceIssueInfo.setUserId(JunitConstants.USER_ID + "2");
    deviceIssueInfoList.add(deviceIssueInfo);
    deviceIssueInfo = new DeviceIssueInfo();
    deviceIssueInfo.setUserId(JunitConstants.USER_ID + "2");
    deviceIssueInfoList.add(deviceIssueInfo);
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao)
      .getDeviceIssueInfoList(Matchers.anyString());
    EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.deviceIssueInfoService(requestInfo);
    Assert.assertTrue(enigmaResponse.getResponseCode().getMessage()
      .equals(Constants.MESSAGE_DEVICE_ALREADY_ISSUED));
  }

  @Test
  public void testDeviceIssueInfoServiceDeviceIssueInfoListWithDifferentUserIdByAdinnIsTrueDeviceInfoListIsNull()
    throws Exception 
  {
    initializeDeviceIssueInfoService();
    userInfo.setUserId(JunitConstants.USER_ID + "1");
    deviceInfo.setDeviceAvailability(Constants.DEVICE_ISSUE);
    List<DeviceIssueInfo> deviceIssueInfoList = new ArrayList<DeviceIssueInfo>();
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao)
      .getDeviceIssueInfoList(Matchers.anyString());
    EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.deviceIssueInfoService(requestInfo);
    Mockito.verify(deviceIssueInfoDao, Mockito.atLeast(1)).createDeviceIssueInfo(
      Matchers.any(DeviceIssueInfo.class));
  }
  
  public List<DeviceIssueInfo> populateDeviceIssueInfoList(Timestamp submitTime,String userId)
  {
    List<DeviceIssueInfo> deviceIssueInfoList =new ArrayList<DeviceIssueInfo>();
    DeviceIssueInfo deviceIssueInfo = null;
    DeviceInfo deviceInfo=null;
    
    deviceIssueInfo=populateDeviceIssueInfo(userId, JunitConstants.DEVICE_ID, JunitConstants.ISSUE_ID, true, Constants.SUBMITTED_BY_ADMIN);
    deviceIssueInfo.setIssueTime(new Timestamp(1111));
    deviceIssueInfo.setSubmitTime(submitTime);
    deviceInfo=new DeviceInfo();
    deviceIssueInfo.setDeviceInfo(deviceInfo);
    deviceIssueInfo.setIssueId(JunitConstants.ISSUE_ID);
    deviceIssueInfoList.add(deviceIssueInfo);
    
    deviceIssueInfo=populateDeviceIssueInfo(userId, JunitConstants.DEVICE_ID, JunitConstants.ISSUE_ID, true, Constants.SUBMITTED_BY_ADMIN);
    deviceIssueInfo.setIssueTime(submitTime);
    deviceInfo=new DeviceInfo();
    deviceIssueInfo.setDeviceInfo(deviceInfo);
    deviceIssueInfoList.add(deviceIssueInfo);
    
    return deviceIssueInfoList;
  }
  
  public DeviceIssueInfo populateDeviceIssueInfo(String userId,String deviceId,String issueId,Boolean issuedByAdmin,String submitBy)
  {
    DeviceIssueInfo deviceIssueInfo = new DeviceIssueInfo();
    deviceIssueInfo.setUserId(userId);
    deviceIssueInfo.setDeviceId(deviceId);
    deviceIssueInfo.setIssueByAdmin(issuedByAdmin);
    deviceIssueInfo.setSubmitBy(submitBy);
    return deviceIssueInfo;
  }
  
  public DeviceInfo populateDeviceInfo(String deviceId,String deviceAvailability)
  {
    DeviceInfo deviceInfo=new DeviceInfo();
    deviceInfo.setDeviceId(deviceId);
    deviceInfo.setDeviceName("Android");
    deviceInfo.setManufacturer("HTC");
    //deviceInfo.setTimeoutPeriod(timeoutPeriod);
    deviceInfo.setDeviceAvailability(deviceAvailability);
    return deviceInfo;
  }
  
  public UserInfo populateUserInfo(String userId,String password)
  {
    UserInfo userInfo=new UserInfo();
    userInfo.setUserId(userId);
    userInfo.setPassword(password);
    return userInfo;
  }
  
  @Test
  public void testGetDevicesIssueReportByStatus() throws Exception
  {
    List<DeviceIssueInfo> deviceIssueInfoList=new ArrayList<DeviceIssueInfo>();
    List<DeviceInfo> deviceInfoList=new ArrayList<DeviceInfo>();
    DeviceInfo deviceInfo=null;
    DeviceIssueInfo  deviceIssueInfo=null;
    
    deviceIssueInfo=populateDeviceIssueInfo(JunitConstants.USER_ID, JunitConstants.DEVICE_ID,JunitConstants.ISSUE_ID,  true, Constants.SUBMITTED_BY_ADMIN);
    deviceInfo=populateDeviceInfo(JunitConstants.DEVICE_ID, Constants.DEVICE_STATUS_ISSUED);
    deviceIssueInfo.setDeviceInfo(deviceInfo);
    deviceIssueInfo.setUserInfo(new UserInfo());
    deviceIssueInfoList.add(deviceIssueInfo);
    deviceInfoList.add(deviceInfo);
    
    deviceInfo=populateDeviceInfo(JunitConstants.DEVICE_ID+1, Constants.DEVICE_STATUS_ISSUED);
    deviceInfoList.add(deviceInfo);
    
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao).getAllDeviceIssueInfoList();
    Mockito.doReturn(deviceInfoList).when(deviceInfoDao).getDevicesList();
    
    EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.getDevicesIssueReportByStatus();
    
    Assert.assertTrue(((List<ReportInfo>)(enigmaResponse.getResponseCode().getResultObject())).get(0).getDeviceId().equals(JunitConstants.DEVICE_ID));
  }
  
  @Test
  public void testGetDeviceReportAvailability() throws Exception
  {
    List<DeviceInfo> deviceInfoList=new ArrayList<DeviceInfo>();
    DeviceInfo deviceInfo=null;
    deviceInfo=populateDeviceInfo(JunitConstants.DEVICE_ID, Constants.DEVICE_STATUS_ISSUED);
    deviceInfoList.add(deviceInfo);
    deviceInfo=populateDeviceInfo(JunitConstants.DEVICE_ID+1, Constants.DEVICE_STATUS_AVAILABLE);
    deviceInfoList.add(deviceInfo);
    deviceInfoList.add(deviceInfo);
    Mockito.doReturn(deviceInfoList).when(deviceInfoDao).getDevicesList();
    EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.getDeviceReportAvailability();
    Assert.assertTrue((((DeviceStatusCountsInfo)(enigmaResponse.getResponseCode().getResultObject())).getAvailableDevices())==2);
    Assert.assertTrue((((DeviceStatusCountsInfo)(enigmaResponse.getResponseCode().getResultObject())).getIssuedDevices())==1);
    Assert.assertTrue((((DeviceStatusCountsInfo)(enigmaResponse.getResponseCode().getResultObject())).getTotalDevices())==3);
  }
  
  @Test
  public void testGetDeviceTimeLineReport() throws Exception
  {
    List<DeviceIssueInfo> deviceIssueInfoList =populateDeviceIssueInfoListForTimeLineReport(new Timestamp(11111),JunitConstants.USER_ID,1);
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao).getDeviceIssueList(Matchers.anyString(),Mockito.any(java.util.Date.class),Mockito.any(java.util.Date.class));
    String deviceId=JunitConstants.DEVICE_ID;
    Mockito.doReturn(new DeviceInfo()).when(deviceInfoDao).getDeviceInfo(Matchers.anyString());
    JSONObject jsonObject=deviceIssueInfoServiceImpl.getDeviceTimeLineReport(null,null,deviceId,"device");
    //Assert.assertTrue(issueId.equals(JunitConstants.ISSUE_ID));
  }
  
  
  @Test
  public void testGetDeviceTimeLineReportForZeroIdleTime() throws Exception
  {
    List<DeviceIssueInfo> deviceIssueInfoList =populateDeviceIssueInfoListForTimeLineReport(new Timestamp(11111),JunitConstants.USER_ID,0);
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao).getDeviceIssueList(Matchers.anyString(),Mockito.any(java.util.Date.class),Mockito.any(java.util.Date.class));
    String deviceId=JunitConstants.DEVICE_ID;
    Mockito.doReturn(new DeviceInfo()).when(deviceInfoDao).getDeviceInfo(Matchers.anyString());
    JSONObject jsonObject=deviceIssueInfoServiceImpl.getDeviceTimeLineReport(null,null,deviceId,"device");
    //Assert.assertTrue(issueId.equals(JunitConstants.ISSUE_ID));
  }
  
  public List<DeviceIssueInfo> populateDeviceIssueInfoListForTimeLineReport(Timestamp submitTime,String userId,int idleTimeInMinutes)
  {
    List<DeviceIssueInfo> deviceIssueInfoList =new ArrayList<DeviceIssueInfo>();
    DeviceIssueInfo deviceIssueInfo = null;
    deviceIssueInfo=populateDeviceIssueInfo(userId, JunitConstants.DEVICE_ID, JunitConstants.ISSUE_ID, true, Constants.SUBMITTED_BY_ADMIN);
    deviceIssueInfo.setIssueTime(new Timestamp(1111));
    deviceIssueInfo.setSubmitTime(getUpdatedTimeStamp(new Timestamp((new java.util.Date()).getTime()), 100, 30));
    deviceInfo=new DeviceInfo();
    deviceIssueInfo.setDeviceInfo(populateDeviceInfo(JunitConstants.DEVICE_ID, JunitConstants.ISSUE_ID));
    deviceIssueInfo.setIssueId(JunitConstants.ISSUE_ID);
    deviceIssueInfo.setUserActivity(populateUserActivities(idleTimeInMinutes));
    deviceIssueInfo.setUserInfo(populateUserInfo(JunitConstants.USER_ID, JunitConstants.PASSWORD));
    
    deviceIssueInfoList.add(deviceIssueInfo);
     
    return deviceIssueInfoList;
  }
  
 
  
  public Set<UserActivity> populateUserActivities(int idleTimeInMinutes)
  {
    Set<UserActivity> userActivities=new HashSet<UserActivity>(); 
    UserActivity userActivity=null;
    Date loginTime=null;;
    Date logoutTime=null;
    loginTime=new Date();
    userActivity=populateUserActivity(loginTime, logoutTime);
    userActivities.add(userActivity);
    if(idleTimeInMinutes>=0)
    {
      logoutTime=loginTime;
    }
    else{
     logoutTime=getUpdatedTime(loginTime, 2, 20);
    }
    userActivity=populateUserActivity(loginTime, logoutTime);
    userActivities.add(userActivity);
    
    userActivity=populateUserActivity(loginTime, logoutTime);
    userActivities.add(userActivity);
    logoutTime=null;
    if(idleTimeInMinutes>=0)
    {
      logoutTime=loginTime;
    }
    else{
    logoutTime=getUpdatedTime(loginTime, 4, 25);
    }
    userActivity=populateUserActivity(loginTime, logoutTime);
    userActivities.add(userActivity);
    logoutTime=null;
    if(idleTimeInMinutes<=0)
    {
      logoutTime=loginTime;
    }
    else{
    logoutTime=getUpdatedTime(loginTime, 5, 25);}
    userActivity=populateUserActivity(loginTime, logoutTime);
    userActivities.add(userActivity);
   
    userActivity=populateUserActivity(loginTime, logoutTime);
    userActivities.add(userActivity);
    if(idleTimeInMinutes<=0)
    {
      logoutTime=loginTime;
    }
    else{
     logoutTime=getUpdatedTime(loginTime, 2, 20);
    }
    userActivity=populateUserActivity(loginTime, logoutTime);
    userActivities.add(userActivity);
    
    userActivity=populateUserActivity(loginTime, logoutTime);
    userActivities.add(userActivity);
    logoutTime=null;
    if(idleTimeInMinutes<=0)
    {
      logoutTime=loginTime;
    }
    else{
    logoutTime=getUpdatedTime(loginTime, 4, 25);
    }
    userActivity=populateUserActivity(loginTime, logoutTime);
    userActivities.add(userActivity);
    logoutTime=null;
    if(idleTimeInMinutes<=0)
    {
      logoutTime=loginTime;
    }
    else{
    logoutTime=getUpdatedTime(loginTime, 5, 25);}
    userActivity=populateUserActivity(loginTime, logoutTime);
    userActivities.add(userActivity);
    
    logoutTime=getUpdatedTime(loginTime, 6, 25);
    userActivity=populateUserActivity(loginTime, logoutTime);
    userActivities.add(userActivity);
    
    return userActivities;
  }
  
  public UserActivity populateUserActivity(java.util.Date loginTime,java.util.Date logoutTime)
  {
    UserActivity userActivity=new UserActivity();
    userActivity.setActivityId(JunitConstants.ACTIVITY_ID);
    userActivity.setDeviceId(JunitConstants.DEVICE_ID);
    userActivity.setIssueId(JunitConstants.ISSUE_ID);
    userActivity.setLocation(JunitConstants.LOCATION);
    userActivity.setLoginTime(loginTime);
    userActivity.setLogoutTime(logoutTime);
    userActivity.setUserId(JunitConstants.USER_ID);
    return userActivity;
  }
  
  public static java.util.Date getUpdatedTime(java.util.Date currentTime,int hours, int minutre) {
    java.util.Date  updateTime=null;
    try {
      if (currentTime != null) {
        updateTime=new Date(currentTime.getTime());
        updateTime.setHours(hours);
        updateTime.setMinutes(minutre);
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return updateTime;
  }
  
  public static java.sql.Timestamp getUpdatedTimeStamp(java.util.Date currentTime,int hours, int minutre) {
    java.sql.Timestamp  updateTime=null;
    try {
      if (currentTime != null) {
        updateTime=new java.sql.Timestamp(currentTime.getTime());
        updateTime.setHours(hours);
        updateTime.setMinutes(minutre);
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return updateTime;
  }

  @AfterClass
  public static void destroy() throws Exception
  {
  }

  @After
  public void tearDown()
  {
    userInfoDaoImpl = null;
    sessionFactory = null;
    session = null;
  }
  
  @Test
  public void testGetDeviceIssueStatusForDevice() throws Exception
  {
    DeviceInfo deviceInfo=null;
    DeviceIssueInfo  deviceIssueInfo=null;
    deviceIssueInfo=populateDeviceIssueInfo(JunitConstants.USER_ID, JunitConstants.DEVICE_ID, JunitConstants.ISSUE_ID, true, Constants.SUBMITTED_BY_ADMIN);
    List<DeviceIssueInfo> deviceIssueInfoList=new ArrayList<DeviceIssueInfo>();
    deviceIssueInfoList.add(deviceIssueInfo);
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao).getDeviceIssueInfoList(Matchers.anyString());
    
    userInfo=populateUserInfo(JunitConstants.USER_ID, JunitConstants.PASSWORD);
    Mockito.doReturn(userInfo).when(userInfoDaoImpl).getUserInfo(Matchers.anyString());
    Mockito.doNothing().when(deviceInfoDao).updateDeviceInfo(Matchers.any(DeviceInfo.class));
   
    deviceInfo= populateDeviceInfo(JunitConstants.DEVICE_ID, Constants.ISSUED_BY_ADMIN);
    Mockito.doReturn(deviceInfo).when(deviceInfoDao).getDeviceInfo(Matchers.anyString());
    DeviceIssueStatusDto deviceIssueStatusDto = deviceIssueInfoServiceImpl.getDeviceIssueStatusForDevice(JunitConstants.DEVICE_ID);
    Assert.assertTrue(Constants.ISSUED_BY_ADMIN.equals(deviceIssueStatusDto.getAvailablityStatus()));
    Assert.assertTrue(JunitConstants.DEVICE_ID.equals(deviceIssueStatusDto.getDeviceId()));
  }
  
  @Test
  public void testGetPendingDevicesReport() throws Exception
  {
    List<DeviceIssueInfo> deviceIssueInfoList = new ArrayList<DeviceIssueInfo>();
    List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
    DeviceInfo deviceInfo = null;
    DeviceIssueInfo deviceIssueInfo = null;

    deviceIssueInfo = populateDeviceIssueInfo(JunitConstants.USER_ID, JunitConstants.DEVICE_ID,
      JunitConstants.ISSUE_ID, true, Constants.SUBMITTED_BY_ADMIN);
    deviceInfo = populateDeviceInfo(JunitConstants.DEVICE_ID, Constants.DEVICE_STATUS_PENDING);
    deviceIssueInfo.setDeviceInfo(deviceInfo);
    deviceIssueInfo.setUserInfo(new UserInfo());
    deviceIssueInfoList.add(deviceIssueInfo);
    deviceInfoList.add(deviceInfo);

    deviceInfo = populateDeviceInfo(JunitConstants.DEVICE_ID + 1, Constants.DEVICE_STATUS_PENDING);
    deviceInfoList.add(deviceInfo);

    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao).getAllDeviceIssueInfoList();
    Mockito.doReturn(deviceInfoList).when(deviceInfoDao).getDevicesList();
    
 EnigmaResponse enigmaResponse = deviceIssueInfoServiceImpl.getPendingDevicesReport();
    
    //Assert.assertTrue(((List<ReportInfo>)(enigmaResponse.getResponseCode().getResultObject())).get(0).getDeviceId().equals(JunitConstants.DEVICE_ID));
    
    }
 }