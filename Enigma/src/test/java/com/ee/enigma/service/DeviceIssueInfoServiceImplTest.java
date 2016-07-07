package com.ee.enigma.service;

import java.util.ArrayList;
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
    deviceInfo.setDeviceAvailability(Constants.DEVICE_INFO_ISSUED_TO_USER);
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
    deviceInfo.setDeviceAvailability(Constants.DEVICE_INFO_ISSUED_TO_USER);
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
    deviceInfo.setDeviceAvailability(Constants.DEVICE_INFO_ISSUED_TO_USER);
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