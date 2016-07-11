package com.ee.enigma.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import com.ee.enigma.dto.DeviceIssueTrendLineDto;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.DeviceIssueInfo;
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
    deviceIssueInfo.setSubmitByAdmin(new Boolean(true));
    deviceIssueInfoList.add(deviceIssueInfo);
    
    deviceIssueInfo = new DeviceIssueInfo();
    deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_ISSUED);
    deviceIssueInfo.setDeviceInfo(deviceInfo);
    deviceIssueInfo.setIssueTime(new Timestamp(1111));
       deviceIssueInfo.setUserId(JunitConstants.USER_ID);
    deviceIssueInfo.setSubmitTime(new Timestamp(1111));
    deviceIssueInfo.setSubmitByAdmin(new Boolean(false));
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
    Assert.assertTrue(enigmaResponse.getResponseCode().getMessage() == Constants.MESSAGE_DEVICE_ALREADY_SUBMITTED);
    
    deviceIssueInfoList = new ArrayList<DeviceIssueInfo>();
    deviceIssueInfo = new DeviceIssueInfo();
    deviceIssueInfo.setUserId(JunitConstants.USER_ID);
    deviceIssueInfoList.add(deviceIssueInfo);
    Mockito.doReturn(deviceIssueInfoList).when(deviceIssueInfoDao)
      .getDeviceIssueInfoList(Matchers.anyString());
    enigmaResponse = deviceIssueInfoServiceImpl.submitDevice(requestInfo);
    Assert.assertTrue(enigmaResponse.getResponseCode().getMessage() == Constants.MESSAGE_DEVICE_ALREADY_SUBMITTED);
    
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
    Assert.assertTrue(enigmaResponse.getResponseCode().getCode() == Constants.CODE_SUCCESS);
    
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
}