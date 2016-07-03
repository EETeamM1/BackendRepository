package com.ee.enigma.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.ee.enigma.model.DeviceInfo;

@RunWith(MockitoJUnitRunner.class)
public class DeviceInfoDaoImplTest
{

  @InjectMocks
  private DeviceInfoDaoImpl deviceInfoDaoImpl;
  @Mock
  private SessionFactory sessionFactory;
  @Mock
  private Session session;
  private final static Log LOGGER = LogFactory.getLog(DeviceInfoDaoImplTest.class);

  @Before
  public void setUp() throws Exception
  {
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(sessionFactory.openSession()).thenReturn(session);
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
