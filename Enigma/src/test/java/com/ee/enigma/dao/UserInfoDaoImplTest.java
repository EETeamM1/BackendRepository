package com.ee.enigma.dao;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
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
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.UserInfo;

@RunWith(MockitoJUnitRunner.class)
public class UserInfoDaoImplTest
{
  @Spy
  @InjectMocks
  private UserInfoDaoImpl userInfoDaoImpl;
  @Mock
  private SessionFactory sessionFactory;
  @Mock
  private Session session;
  @Mock
  private Query query;
  private final static Log LOGGER = LogFactory.getLog(UserInfoDaoImplTest.class);

  @Before
  public void setUp() throws Exception
  {
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(sessionFactory.openSession()).thenReturn(session);
    Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
   // Mockito.when(session.createCriteria(Mockito.anyString())).thenReturn(query);
  }

  @BeforeClass
  public static void init() throws Exception
  {

  }

  @Test
  public void testUpdateUserInfo()
  {
    UserInfo userInfo = new UserInfo();
    userInfoDaoImpl.updateUserInfo(userInfo);
    Mockito.verify(session).update(Mockito.any(UserInfo.class));
  }
  
  @Test
  public void testCreateUserInfo()
  {
    UserInfo userInfo = new UserInfo();
    userInfoDaoImpl.createUserInfo(userInfo);
    Mockito.verify(session).persist(Mockito.any(UserInfo.class));
  }
  
  @Test
  public void testDeleteUserInfo()
  {
    UserInfo userInfo = populateUserInfo(JunitConstants.USER_ID);
    userInfoDaoImpl.deleteUserInfo(userInfo);
    Mockito.verify(query).executeUpdate();
  }
  
  //@Test
  public void testGetUserInfo()
  {
    UserInfo userInfo=userInfoDaoImpl.getUserInfo(JunitConstants.USER_ID,JunitConstants.PASSWORD);
    Mockito.verify(query).executeUpdate();
  }
  
  @Test
  public void testGetUserInfoByUserId()
  {
    Mockito.when(query.list()).thenReturn(populateUserInfoList());
    UserInfo userInfo=userInfoDaoImpl.getUserInfo(JunitConstants.USER_ID);
    Assert.assertTrue(JunitConstants.USER_ID.equals(userInfo.getUserId()));
  }
  
  @Test
  public void testGetUserInfoByUserIdWithDefferentUserId()
  {
    Mockito.when(query.list()).thenReturn(null);
    UserInfo userInfo=userInfoDaoImpl.getUserInfo(JunitConstants.USER_ID);
    Assert.assertTrue(null==userInfo);
  }
  
  @Test
  public void testGetAllUserInfo()
  {
    List<UserInfo> userInfoList=null;
    Mockito.when(query.list()).thenReturn(populateUserObjectInfoList());
    userInfoList=userInfoDaoImpl.getAllUserInfo();
    Assert.assertTrue(userInfoList.size()>0);
  }
  
  @Test
  public void testGetAllUserInfoithNoUser()
  {
    List<UserInfo> userInfoList=null;
    Mockito.when(query.list()).thenReturn(null);
    userInfoList=userInfoDaoImpl.getAllUserInfo();
    Assert.assertTrue(userInfoList==null);
  }
  
  public List<Object[]> populateUserObjectInfoList()
  {
    String[] user=new String[2];
    user[0]=JunitConstants.USER_ID;
    user[1]=JunitConstants.USER_NAME;
    List<Object[]> objs=new ArrayList<Object[]>();
    objs.add(user);
    objs.add(user);
    return objs;
  }
  public List<UserInfo> populateUserInfoList()
  {
    UserInfo userInfo = null;
    List<UserInfo> userInfoList = new ArrayList<UserInfo>();
    userInfo = populateUserInfo(JunitConstants.USER_ID);
    userInfoList.add(userInfo);
    userInfo = populateUserInfo(JunitConstants.USER_ID+1);
    userInfoList.add(userInfo);
    return userInfoList;
  }
  public UserInfo populateUserInfo(String userId)
  {
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(userId);
    userInfo.setUserName(JunitConstants.USER_NAME);
    userInfo.setPassword(JunitConstants.PASSWORD);
    return userInfo;
  }
  
  @Test
  public void testGetUsersByIdAndName()
  {
    Mockito.when(query.list()).thenReturn(populateUserInfoList());
    List<UserInfo> userInfoList=userInfoDaoImpl.getUsersByIdAndName(JunitConstants.USER_ID);
    Assert.assertTrue(userInfoList.size()>0);
  }
  
  @Test
  public void testGetUsersByIdAndNameWithNoList()
  {
    Mockito.when(query.list()).thenReturn(null);
    List<UserInfo> userInfoList=userInfoDaoImpl.getUsersByIdAndName(JunitConstants.USER_ID);
    Assert.assertTrue(userInfoList==null);
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
