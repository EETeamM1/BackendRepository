package com.ee.enigma.dao;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.ee.enigma.common.Constants;
import com.ee.enigma.common.JunitConstants;
import com.ee.enigma.dao.impl.DeviceInfoDaoImpl;
import com.ee.enigma.model.DeviceInfo;

@RunWith(MockitoJUnitRunner.class)
public class DeviceInfoDaoImplTest
{
  @Spy
  @InjectMocks
  private DeviceInfoDaoImpl deviceInfoDaoImpl;
  @Mock
  private SessionFactory sessionFactory;
  @Mock
  private Session session;
  @Mock
  private Query query;
  private final static Log LOGGER = LogFactory.getLog(DeviceInfoDaoImplTest.class);

  @Before
  public void setUp() throws Exception
  {
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(sessionFactory.openSession()).thenReturn(session);
    Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);

  }

  @BeforeClass
  public static void init() throws Exception
  {

  }

  @Test
  public void testUpdateDeviceInfo()
  {
    DeviceInfo deviceInfo = new DeviceInfo();
    deviceInfoDaoImpl.updateDeviceInfo(deviceInfo);
    Mockito.verify(session).update(Mockito.any(DeviceInfo.class));
  }

  @Test
  public void testUpdateDeviceInfoNullUserInfo()
  {
    DeviceInfo deviceInfo = null;
    deviceInfoDaoImpl.updateDeviceInfo(deviceInfo);
    Mockito.verify(session).update(Mockito.any(DeviceInfo.class));
  }
  
  @Test
  public void testCreateDeviceInfo()
  {
    DeviceInfo deviceInfo = null;
    deviceInfoDaoImpl.createDeviceInfo(deviceInfo);
    Mockito.verify(session).persist(Mockito.any(DeviceInfo.class));
  }
  
  //@Test
  public void testGetDeviceInfo()
  {
    DeviceInfo deviceInfo = new DeviceInfo();
    Mockito.doNothing().when(session).load(Mockito.any(DeviceInfo.class),Mockito.anyString());
    deviceInfo=deviceInfoDaoImpl.getDeviceInfo(JunitConstants.DEVICE_ID);
    Assert.assertTrue(deviceInfo!=null);
  }
  
  @Test
  public void testDeleteDeviceInfo()
  {
    Mockito.when( query.executeUpdate()).thenReturn(1);
    int reuult=deviceInfoDaoImpl.deleteDeviceInfo(populateDeviceInfo());
    Assert.assertTrue(1==reuult);
  }
  
  @Test
  public void testGetDevicesList()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceInfoList());
    List<DeviceInfo> deviceInfos=deviceInfoDaoImpl.getDevicesList();
    Assert.assertTrue(deviceInfos.size()>1);
  }
  
  @Test
  public void testGetDevicesListWithNoDevice()
  {
    Mockito.when(query.list()).thenReturn(null);
    List<DeviceInfo> deviceInfos=deviceInfoDaoImpl.getDevicesList();
    Assert.assertTrue(deviceInfos==null);
  }
  
  @Test
  public void testGetDevicesListByIDAndStatus()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceInfoList());
    List<DeviceInfo> deviceInfos=deviceInfoDaoImpl.getDevicesListByIDAndStatus(JunitConstants.DEVICE_ID, Constants.DEVICE_ISSUE);
    Assert.assertTrue(deviceInfos.size()>1);
  }
  
  @Test
  public void testGetDevicesListByIDAndStatusWithNullDeviceId()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceInfoList());
    List<DeviceInfo> deviceInfos=deviceInfoDaoImpl.getDevicesListByIDAndStatus(null, Constants.DEVICE_ISSUE);
    Assert.assertTrue(deviceInfos.size()>1);
  }
  
  @Test
  public void testGetDevicesListByIDAndStatusWithNullIssueStatus()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceInfoList());
    List<DeviceInfo> deviceInfos=deviceInfoDaoImpl.getDevicesListByIDAndStatus(JunitConstants.DEVICE_ID,null);
    Assert.assertTrue(deviceInfos.size()>1);
  }
  
  @Test
  public void testGetDevicesListByIDAndStatusWithBothNull()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceInfoList());
    List<DeviceInfo> deviceInfos=deviceInfoDaoImpl.getDevicesListByIDAndStatus(null,null);
    Assert.assertTrue(deviceInfos.size()>1);
  }
  
  @Test
  public void testGetDevicesListByIDAndStatushenNoDeviceFound()
  {
    Mockito.when(query.list()).thenReturn(null);
    List<DeviceInfo> deviceInfos=deviceInfoDaoImpl.getDevicesListByIDAndStatus(JunitConstants.DEVICE_ID, Constants.DEVICE_ISSUE);
    Assert.assertTrue(null==deviceInfos);
  }
  
  @Test
  public void testGetDeviceFields()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceInfoList());
    List<DeviceInfo> deviceInfos=deviceInfoDaoImpl.getDeviceFields(JunitConstants.SEARCH_TEXT);
    Assert.assertTrue(deviceInfos.size()>1);
  }
  
  @Test
  public void testGetDeviceFieldshenNoDeviceFound()
  {
    Mockito.when(query.list()).thenReturn(null);
    List<DeviceInfo> deviceInfos=deviceInfoDaoImpl.getDeviceFields(JunitConstants.SEARCH_TEXT);
    Assert.assertTrue(null==deviceInfos);
  }
  

  
  public List<DeviceInfo> populateDeviceInfoList()
  {
    DeviceInfo deviceInfo = null;
    List<DeviceInfo> deviceIssueInfoList = new ArrayList<DeviceInfo>();
    deviceInfo = populateDeviceInfo();
    deviceIssueInfoList.add(deviceInfo);

    deviceInfo = populateDeviceInfo();
    deviceIssueInfoList.add(deviceInfo);
    return deviceIssueInfoList;
  }
  public DeviceInfo populateDeviceInfo()
  {
    DeviceInfo deviceInfo = new DeviceInfo();
    deviceInfo.setDeviceId(JunitConstants.DEVICE_ID);
    deviceInfo.setDeviceName(JunitConstants.DEVICE_NAME);
    deviceInfo.setManufacturer(JunitConstants.DEVICE_MANUFACTURER);
    deviceInfo.setYearOfManufacturing(JunitConstants.DEVICE_YR_OF_MANUFACTURING);
    deviceInfo.setTimeoutPeriod(new Time(new java.util.Date().getTime()));
    deviceInfo.setOsVersion(JunitConstants.DEVICE_OS_VERSION);
    deviceInfo.setOs(JunitConstants.DEVICE_OS);
    deviceInfo.setDeviceAvailability(Constants.DEVICE_ISSUE);
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
