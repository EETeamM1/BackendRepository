package com.ee.enigma.dao;

import java.sql.Time;
import java.sql.Timestamp;
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
import com.ee.enigma.dao.impl.DeviceIssueInfoDaoImpl;
import com.ee.enigma.dao.impl.UserInfoDaoImpl;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.DeviceIssueInfo;
import com.ee.enigma.model.UserInfo;

@RunWith(MockitoJUnitRunner.class)
public class DeviceIssueInfoDaoImplTest
{
  @Spy
  @InjectMocks
  private DeviceIssueInfoDaoImpl deviceIssueInfoDaoImpl;
  @Mock
  private UserInfoDaoImpl userInfoDaoImpl;
  @Mock
  private DeviceInfoDaoImpl deviceInfoDaoImpl;
  @Mock
  private SessionFactory sessionFactory;
  @Mock
  private Session session;
  @Mock
  private Query query;
  private final static Log LOGGER = LogFactory.getLog(DeviceIssueInfoDaoImplTest.class);

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
  public void testGetDeviceIssueListByIssueType()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceIssueInfoList(new Timestamp(1112121), JunitConstants.USER_ID));
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getDeviceIssueList(JunitConstants.CURRENT_DATE,JunitConstants.CURRENT_DATE,Constants.DEVICE_ISSUE);
    Assert.assertTrue(deviceIssueInfos.size()>1);
  }
  
  @Test
  public void testGetDeviceIssueListByIssueTypeForNoDeviviceIssue()
  {
    Mockito.when(query.list()).thenReturn(null);
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getDeviceIssueList(JunitConstants.CURRENT_DATE,JunitConstants.CURRENT_DATE,Constants.DEVICE_ISSUE);
    Assert.assertTrue(deviceIssueInfos.size() == 0);
  }
  
  
  @Test
  public void testGetDeviceIssueListByIssueTypeForDeviceSubmit()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceIssueInfoList(new Timestamp(1112121), JunitConstants.USER_ID));
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getDeviceIssueList(JunitConstants.CURRENT_DATE,JunitConstants.CURRENT_DATE,Constants.DEVICE_SUBMIT);
    Assert.assertTrue(deviceIssueInfos.size()>1);
  }
  
  @Test
  public void testGetDeviceIssueListByIssueTypeForDeviceAll()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceIssueInfoList(new Timestamp(1112121), JunitConstants.USER_ID));
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getDeviceIssueList(JunitConstants.CURRENT_DATE,JunitConstants.CURRENT_DATE,Constants.DEVICE_ISSUE_ALL);
    Assert.assertTrue(deviceIssueInfos.size()>1);
  }
  
  @Test
  public void testGetDeviceIssueListByIssueTypeForNullBeginDate()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceIssueInfoList(new Timestamp(1112121), JunitConstants.USER_ID));
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getDeviceIssueList(null,JunitConstants.CURRENT_DATE,Constants.DEVICE_ISSUE_ALL);
    Assert.assertTrue(deviceIssueInfos.size()>1);
  }
  
  
  
  
  @Test
  public void testGetDeviceIssueList()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceIssueInfoList(new Timestamp(1112121), JunitConstants.USER_ID));
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getDeviceIssueList(JunitConstants.DEVICE_ID,JunitConstants.CURRENT_DATE,JunitConstants.CURRENT_DATE);
    Assert.assertTrue(deviceIssueInfos.size()>1);
  }
  
  @Test
  public void testGetDeviceIssueListWithNoInfos()
  {
    Mockito.when(query.list()).thenReturn(null);
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getDeviceIssueList(JunitConstants.DEVICE_ID,JunitConstants.CURRENT_DATE,JunitConstants.CURRENT_DATE);
    Assert.assertTrue(deviceIssueInfos.size() == 0);
  }
  
  @Test
  public void testGetDeviceIssueListithBeginDateIsNull()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceIssueInfoList(new Timestamp(1112121), JunitConstants.USER_ID));
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getDeviceIssueList(JunitConstants.DEVICE_ID,null,JunitConstants.CURRENT_DATE);
    Assert.assertTrue(deviceIssueInfos.size()>1);
  }
 
  @Test
  public void testUpdateDeviceIssueInfo()
  {
    deviceIssueInfoDaoImpl.updateDeviceIssueInfo(populateDeviceIssueInfo(JunitConstants.USER_ID, JunitConstants.DEVICE_ID, JunitConstants.ISSUE_ID, true, Constants.SUBMITTED_BY_ADMIN));
    Mockito.verify(session).update(Mockito.any(UserInfo.class));
  }
  
  @Test
  public void testCreateDeviceIssueInfo()
  {
    deviceIssueInfoDaoImpl.createDeviceIssueInfo(populateDeviceIssueInfo(JunitConstants.USER_ID, JunitConstants.DEVICE_ID, JunitConstants.ISSUE_ID, true, Constants.SUBMITTED_BY_ADMIN));
    Mockito.verify(session).persist(Mockito.any(UserInfo.class));
  }
  
  @Test
  public void testGetDeviceIssueInfoList()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceIssueInfoList(new Timestamp(1112121), JunitConstants.USER_ID));
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getDeviceIssueInfoList(JunitConstants.DEVICE_ID);
    Assert.assertTrue(deviceIssueInfos.size()>0);
  }
  @Test
  public void testGetDeviceIssueInfoListForNoDeviceIssue()
  {
    Mockito.when(query.list()).thenReturn(null);
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getDeviceIssueInfoList(JunitConstants.DEVICE_ID);
    Assert.assertTrue(deviceIssueInfos.size() == 0);
  }
  
  @Test
  public void testGetDeviceIssueInfoListForDeviceIDAndUserId()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceIssueInfoList(new Timestamp(1112121), JunitConstants.USER_ID));
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getDeviceIssueInfoList(JunitConstants.DEVICE_ID,JunitConstants.USER_ID);
    Assert.assertTrue(deviceIssueInfos.size()>0);
  }
  
  @Test
  public void testGetDeviceIssueInfoListForDeviceIDAndUserIdWithNullRecord()
  {
    Mockito.when(query.list()).thenReturn(null);
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getDeviceIssueInfoList(JunitConstants.DEVICE_ID,JunitConstants.USER_ID);
    Assert.assertTrue(deviceIssueInfos.size() == 0);
  }
  
  @Test
  public void testGetAllDeviceIssueInfoList()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceIssueInfoList(new Timestamp(1112121), JunitConstants.USER_ID));
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getAllDeviceIssueInfoList();
    Assert.assertTrue(deviceIssueInfos.size()>0);
  }
  
  @Test
  public void testGetAllDeviceIssueInfoListWithNullRecord()
  {
    Mockito.when(query.list()).thenReturn(null);
    List<DeviceIssueInfo> deviceIssueInfos=deviceIssueInfoDaoImpl.getAllDeviceIssueInfoList();
    Assert.assertTrue(deviceIssueInfos.size() == 0);
  }
  
  @Test
  public void testGetDeviceIssueInfoByDeviceId()
  {
    Mockito.when(query.list()).thenReturn(populateDeviceIssueInfoList(new Timestamp(1112121), JunitConstants.USER_ID));
    DeviceIssueInfo deviceIssueInfo=deviceIssueInfoDaoImpl.getDeviceIssueInfoByDeviceId(JunitConstants.DEVICE_ID);
    Assert.assertTrue(deviceIssueInfo!=null);
  }
  
  @Test
  public void testGetDeviceIssueInfoByDeviceIdWithNullRecord()
  {
    Mockito.when(query.list()).thenReturn(null);
    DeviceIssueInfo deviceIssueInfo=deviceIssueInfoDaoImpl.getDeviceIssueInfoByDeviceId(JunitConstants.DEVICE_ID);
    Assert.assertTrue(deviceIssueInfo==null);
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
  
  public List<DeviceInfo> populateDeviceInfoList()
  {
    DeviceInfo deviceInfo = null;
    List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
    deviceInfo = populateDeviceInfo();
    deviceInfoList.add(deviceInfo);

    deviceInfo = populateDeviceInfo();
    deviceInfoList.add(deviceInfo);
    return deviceInfoList;
  }
  public DeviceInfo populateDeviceInfo(String deviceId,String deviceAvailability)
  {
    DeviceInfo deviceInfo=new DeviceInfo();
    deviceInfo.setDeviceId(deviceId);
    deviceInfo.setDeviceName("Android");
    deviceInfo.setManufacturer("HTC");
    deviceInfo.setDeviceAvailability(deviceAvailability);
    return deviceInfo;
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
