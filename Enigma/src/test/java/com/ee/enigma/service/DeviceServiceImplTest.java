package com.ee.enigma.service;

import java.sql.Time;
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

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.Constants;
import com.ee.enigma.common.JunitConstants;
import com.ee.enigma.dao.DeviceInfoDaoImpl;
import com.ee.enigma.dao.DeviceIssueInfoDaoImpl;
import com.ee.enigma.dto.DeviceInfoDto;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.DeviceIssueInfo;
import com.ee.enigma.request.Request;
import com.ee.enigma.request.RequestParameters;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.response.ResponseCode;
import com.ee.enigma.response.ResponseResult;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceImplTest
{

  @InjectMocks
  private DeviceServiceImpl deviceServiceImpl;

  @Mock
  private DeviceInfoDaoImpl deviceInfoDaoImpl;
  @Mock
  private DeviceIssueInfoDaoImpl deviceIssueInfoDaoImpl;
  @Mock
  private SessionFactory sessionFactory;
  @Mock
  private Session session;

  private EnigmaResponse response;
  private ResponseCode responseCode;
  private ResponseResult result;
  private Request requestInfo ;
  private RequestParameters parameters;

  @Before
  public void setUp() throws Exception
  {
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(sessionFactory.openSession()).thenReturn(session);
    parameters = new RequestParameters();
    requestInfo = new Request();
  }

  @BeforeClass
  public static void init() throws Exception
  {
  }

  @Test
  public void testGetDevicesInfoByStatus() throws Exception
  {
    List<DeviceInfoDto> deviceInfoDtos = null;
    String deviceId = JunitConstants.DEVICE_ID;
    String deviceStatus = Constants.DEVICE_ISSUE;
    List<DeviceInfo> deviceInfos = populateDeviceInfoList();
    Mockito.doReturn(deviceInfos).when(deviceInfoDaoImpl)
      .getDevicesListByIDAndStatus(Matchers.anyString(), Matchers.anyString());
    deviceInfoDtos = deviceServiceImpl.getDevicesInfoByStatus(deviceId, deviceStatus);
    Assert.assertTrue(deviceInfoDtos.size() == 3);
  }

  @Test
  public void testSaveDeviceInfoSaveInfo() throws Exception
  {
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    setRequestParameters(parameters);
    requestInfo.setParameters(parameters);
    parameters.setDeviceId(JunitConstants.DEVICE_ID + 11);
    Mockito.doReturn(null).when(deviceInfoDaoImpl).getDeviceInfo(Matchers.anyString());
    Mockito.doNothing().when(deviceInfoDaoImpl).createDeviceInfo(Matchers.any(DeviceInfo.class));
    response = deviceServiceImpl.saveDeviceInfo(requestInfo);
    Assert.assertTrue(response.getResponseCode().getMessage()
      .equals(Constants.MESSAGE_SUCCESSFULLY_SAVE));
    Mockito.verify(deviceInfoDaoImpl, Mockito.times(1)).createDeviceInfo(
      Mockito.any(DeviceInfo.class));
  }

  @Test
  public void testSaveDeviceInfoSaveInfoDeviceInfoExisting() throws Exception
  {
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    setRequestParameters(parameters);
    requestInfo.setParameters(parameters);
    Mockito
      .doReturn(populateDeviceInfo(parameters.getDeviceId(), Constants.DEVICE_STATUS_AVAILABLE))
      .when(deviceInfoDaoImpl).getDeviceInfo(Matchers.anyString());
    Mockito.doNothing().when(deviceInfoDaoImpl).createDeviceInfo(Matchers.any(DeviceInfo.class));
    response = deviceServiceImpl.saveDeviceInfo(requestInfo);
    Assert.assertTrue(response.getResponseCode().getMessage()
      .equals(Constants.DEVICE_ALREADY_EXISTS));
  }

  @Test
  public void testSaveDeviceInfoUpdateInfo() throws Exception
  {
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    setRequestParameters(parameters);
    requestInfo.setParameters(parameters);
    parameters.setOpration(Constants.OPRATION_UPDATE);
    Mockito.doReturn(null).when(deviceInfoDaoImpl).getDeviceInfo(Matchers.anyString());
    Mockito.doNothing().when(deviceInfoDaoImpl).updateDeviceInfo(Matchers.any(DeviceInfo.class));
    response = deviceServiceImpl.saveDeviceInfo(requestInfo);
    Assert.assertTrue(response.getResponseCode().getMessage()
      .equals(Constants.MESSAGE_SUCCESSFULLY_UPDATED));
    Mockito.verify(deviceInfoDaoImpl, Mockito.times(1)).updateDeviceInfo(
      Mockito.any(DeviceInfo.class));
  }

  @Test
  public void testSaveDeviceInfoSaveInfoBadRequests() throws Exception
  {
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    setRequestParameters(parameters);
    requestInfo.setParameters(parameters);
    parameters.setDeviceId(null);
    Mockito.doNothing().when(deviceInfoDaoImpl).createDeviceInfo(Matchers.any(DeviceInfo.class));
    response = deviceServiceImpl.saveDeviceInfo(requestInfo);
    Assert
      .assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_BAD_REQUEST));
  }

  @Test
  public void testSaveDeviceInfoSaveInfoRequestParametersNull() throws Exception
  {
    response = new EnigmaResponse();
    parameters = new RequestParameters();
    requestInfo = null;
    Mockito.doNothing().when(deviceInfoDaoImpl).createDeviceInfo(Matchers.any(DeviceInfo.class));
    response = deviceServiceImpl.saveDeviceInfo(requestInfo);
    Assert
      .assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_BAD_REQUEST));
  }
  @Test
  public void testUpdateDeviceInfoStatus() throws Exception
  {
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    setRequestParameters(parameters);
    requestInfo.setParameters(parameters);
    Mockito
    .doReturn(populateDeviceInfo(parameters.getDeviceId(), Constants.DEVICE_STATUS_AVAILABLE))
    .when(deviceInfoDaoImpl).getDeviceInfo(Matchers.anyString());
    Mockito.doNothing().when(deviceInfoDaoImpl).updateDeviceInfo(Matchers.any(DeviceInfo.class));
    
    response = deviceServiceImpl.updateDeviceInfoStatus(requestInfo);
    Assert
      .assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_SUCCESSFULLY_UPDATED));
  }
  @Test
  public void testUpdateDeviceInfoStatusWhenDeviceInfoIsNull() throws Exception
  {
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    setRequestParameters(parameters);
    requestInfo.setParameters(parameters);
    Mockito
    .doReturn(null)
    .when(deviceInfoDaoImpl).getDeviceInfo(Matchers.anyString());
    Mockito.doNothing().when(deviceInfoDaoImpl).updateDeviceInfo(Matchers.any(DeviceInfo.class));
    
    response = deviceServiceImpl.updateDeviceInfoStatus(requestInfo);
   Assert
      .assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_NOT_FOUND_DEVICE));
  }
  @Test
  public void testUpdateDeviceInfoStatusWhenRequestInfoIsNull() throws Exception
  {
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    setRequestParameters(parameters);
    requestInfo=null;
    Mockito
    .doReturn(null)
    .when(deviceInfoDaoImpl).getDeviceInfo(Matchers.anyString());
    Mockito.doNothing().when(deviceInfoDaoImpl).updateDeviceInfo(Matchers.any(DeviceInfo.class));
    
    response = deviceServiceImpl.updateDeviceInfoStatus(requestInfo);
   Assert
      .assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_BAD_REQUEST));
  }
  @Test
  public void testUpdateDeviceInfoStatusWhenDEviceIdIsNull() throws Exception
  {
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    setRequestParameters(parameters);
    requestInfo.setParameters(parameters);
    parameters.setDeviceId(null);
    Mockito
    .doReturn(null)
    .when(deviceInfoDaoImpl).getDeviceInfo(Matchers.anyString());
    Mockito.doNothing().when(deviceInfoDaoImpl).updateDeviceInfo(Matchers.any(DeviceInfo.class));
    
    response = deviceServiceImpl.updateDeviceInfoStatus(requestInfo);
   Assert
      .assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_BAD_REQUEST));
  }
  
  @Test
  public void testDeleteDeviceInfo() throws Exception
  {
    String deviceId=JunitConstants.DEVICE_ID;
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    Mockito.doReturn(1).when(deviceInfoDaoImpl).deleteDeviceInfo(Matchers.any(DeviceInfo.class));
    response = deviceServiceImpl.deleteDeviceInfo(deviceId);
   Assert
      .assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_SUCCESSFULLY_DELETED));
  }
  @Test
  public void testDeleteDeviceInfoWhenDeviceIdIsNull() throws Exception
  {
    String deviceId=null;
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    Mockito.doReturn(0).when(deviceInfoDaoImpl).deleteDeviceInfo(Matchers.any(DeviceInfo.class));
    response = deviceServiceImpl.deleteDeviceInfo(deviceId);
   Assert
      .assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_BAD_REQUEST));
  }
  
  @Test
  public void testGetDeviceInfo() throws Exception
  {
    String deviceId=JunitConstants.DEVICE_ID;
    DeviceInfoDto deviceInfoDto = new DeviceInfoDto();
     DeviceInfo deviceInfo=populateDeviceInfo(deviceId, Constants.DEVICE_ISSUE);
    Mockito.doReturn(deviceInfo).when(deviceInfoDaoImpl).getDeviceInfo(Matchers.anyString());
    deviceInfoDto= deviceServiceImpl.getDeviceInfo(deviceId);
   Assert.assertTrue(deviceInfoDto!=null);
  }
  
  @Test
  public void testApproveDevice() throws Exception
  {
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    setRequestParameters(parameters);
    requestInfo.setParameters(parameters);
    parameters.setIsAdminApproved(true);
    DeviceInfo deviceInfo=populateDeviceInfo(parameters.getDeviceId(), Constants.DEVICE_STATUS_ISSUED);
    deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_PENDING);
    Mockito.doReturn(deviceInfo).when(deviceInfoDaoImpl).getDeviceInfo(Matchers.anyString());
    response = deviceServiceImpl.approveDevice(requestInfo);
    Assert.assertTrue(Constants.DEVICE_STATUS_AVAILABLE.equals(deviceInfo.getDeviceAvailability()));
       
    Mockito.doReturn(null).when(deviceInfoDaoImpl).getDeviceInfo(Matchers.anyString());
    response = deviceServiceImpl.approveDevice(requestInfo);
    Assert.assertTrue(Constants.MESSAGE_DEVICE_IS_NOT_PENDING.equals(response.getResponseCode().getMessage()));
  }
 @Test
  public void testApproveDeviceWhenCancelDevice() throws Exception
  {
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    setRequestParameters(parameters);
    requestInfo.setParameters(parameters);
    DeviceInfo deviceInfo=populateDeviceInfo(parameters.getDeviceId(), Constants.DEVICE_STATUS_ISSUED);
    parameters.setIsAdminApproved(false);
    deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_PENDING);
    Mockito.doReturn(deviceInfo).when(deviceInfoDaoImpl).getDeviceInfo(Matchers.anyString());
    
    DeviceIssueInfo deviceIssueInfo=new DeviceIssueInfo();
    deviceIssueInfo.setSubmitTime(CommonUtils.getCurrentDateTime());
    Mockito.doReturn(deviceIssueInfo).when(deviceIssueInfoDaoImpl).getDeviceIssueInfoByDeviceId(Matchers.anyString());
    response = deviceServiceImpl.approveDevice(requestInfo);
    Assert.assertTrue(Constants.MESSAGE_DEVICE_SUBMISSION_IS_REJECTED.equals(response.getResponseCode().getMessage()));
    Assert.assertTrue(Constants.DEVICE_STATUS_ISSUED.equals(deviceInfo.getDeviceAvailability()));
    Assert.assertTrue(null==deviceIssueInfo.getSubmitTime());
   }
  @Test
  public void testApproveDeviceForBadRequest() throws Exception
  {
    response = new EnigmaResponse();
    requestInfo = new Request();
    parameters = new RequestParameters();
    setRequestParameters(parameters);
    requestInfo.setParameters(parameters);
    parameters.setIsAdminApproved(true);
    parameters.setDeviceId(null);
    DeviceInfo deviceInfo=populateDeviceInfo(parameters.getDeviceId(), Constants.DEVICE_STATUS_ISSUED);
    deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_PENDING);
    Mockito.doReturn(deviceInfo).when(deviceInfoDaoImpl).getDeviceInfo(Matchers.anyString());
    response = deviceServiceImpl.approveDevice(requestInfo);
    Assert.assertTrue(Constants.MESSAGE_BAD_REQUEST.equals(response.getResponseCode().getMessage()));
    // when requestInfo is null
    parameters=null;
    response = deviceServiceImpl.approveDevice(requestInfo);
    Assert.assertTrue(Constants.MESSAGE_BAD_REQUEST.equals(response.getResponseCode().getMessage()));
  }
  
  @Test
  public void testSearchDevice() throws Exception
  {
    String searchQuery="";
    response = new EnigmaResponse();
    requestInfo = new Request();
   
    List<DeviceInfo> deviceInfos = populateDeviceInfoList();
    Mockito.doReturn(deviceInfos).when(deviceInfoDaoImpl).getDeviceFields(Matchers.anyString());
    searchQuery="Query";
    response = deviceServiceImpl.searchDevice(searchQuery);
    Assert.assertTrue(response.getResult().getDeviceList().size()>0);
    
    searchQuery="";
    response = deviceServiceImpl.searchDevice(searchQuery);
    Assert.assertTrue(Constants.MESSAGE_BAD_REQUEST.equals(response.getResponseCode().getMessage()));
    
    searchQuery=null;
    response = deviceServiceImpl.searchDevice(searchQuery);
    Assert.assertTrue(Constants.MESSAGE_BAD_REQUEST.equals(response.getResponseCode().getMessage()));
  }
  
  public void setRequestParameters(RequestParameters requestInfo)
  {
    parameters.setDeviceId(JunitConstants.DEVICE_ID);
    parameters.setDeviceName(JunitConstants.DEVICE_NAME);
    parameters.setDeviceStatus(Constants.DEVICE_STATUS_ISSUED);
    parameters.setManufacturer(JunitConstants.DEVICE_MANUFACTURER);
    parameters.setoS(JunitConstants.DEVICE_OS);
    parameters.setOsVersion(JunitConstants.DEVICE_OS_VERSION);
    parameters.setYearOfManufacturing(JunitConstants.DEVICE_YR_OF_MANUFACTURING);
    parameters.setOpration(Constants.OPRATION_SAVE);
  }

  public List<DeviceInfo> populateDeviceInfoList()
  {
    DeviceInfo deviceInfo = null;
    List<DeviceInfo> deviceIssueInfoList = new ArrayList<DeviceInfo>();
    String deviceId = JunitConstants.DEVICE_ID;
    deviceInfo = populateDeviceInfo(deviceId, Constants.DEVICE_ISSUE);
    deviceIssueInfoList.add(deviceInfo);

    deviceInfo = populateDeviceInfo(deviceId + 1, Constants.DEVICE_ISSUE);
    deviceIssueInfoList.add(deviceInfo);

    deviceInfo = populateDeviceInfo(deviceId + 2, Constants.DEVICE_ISSUE);
    deviceIssueInfoList.add(deviceInfo);

    return deviceIssueInfoList;
  }

  public DeviceInfo populateDeviceInfo(String deviceId, String deviceAvailability)
  {
    DeviceInfo deviceInfo = new DeviceInfo();
    deviceInfo.setDeviceId(deviceId);
    deviceInfo.setDeviceName(JunitConstants.DEVICE_NAME);
    deviceInfo.setManufacturer(JunitConstants.DEVICE_MANUFACTURER);
    deviceInfo.setYearOfManufacturing(JunitConstants.DEVICE_YR_OF_MANUFACTURING);
    deviceInfo.setTimeoutPeriod(new Time(new java.util.Date().getTime()));
    deviceInfo.setOSVersion(JunitConstants.DEVICE_OS_VERSION);
    deviceInfo.setOS(JunitConstants.DEVICE_OS);
    deviceInfo.setDeviceAvailability(deviceAvailability);
    return deviceInfo;
  }
  
  @AfterClass
  public static void destroy() throws Exception
  {
  }

  @After
  public void tearDown()
  {
    deviceInfoDaoImpl = null;
    sessionFactory = null;
    session = null;
  }
}
