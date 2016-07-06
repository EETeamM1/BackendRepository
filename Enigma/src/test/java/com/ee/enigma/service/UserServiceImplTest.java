package com.ee.enigma.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.ee.enigma.common.Constants;
import com.ee.enigma.common.JunitConstants;
import com.ee.enigma.dao.UserInfoDaoImpl;
import com.ee.enigma.model.UserInfo;
import com.ee.enigma.request.Request;
import com.ee.enigma.request.RequestParameters;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.response.ResponseCode;
import com.ee.enigma.response.ResponseResult;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest  {

  @InjectMocks
  private UserServiceImpl userServiceImpl;
  
  @Mock
  private UserInfoDaoImpl userInfoDaoImpl;
  @Mock
  private SessionFactory sessionFactory;
  @Mock
  private Session session;
  
  private EnigmaResponse response;
  private ResponseCode responseCode;
  private ResponseResult result;
  private Request requestInfo=null;
  private RequestParameters parameters;

  @Before
  public void setUp() throws Exception
  {
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(sessionFactory.openSession()).thenReturn(session);
    parameters=new RequestParameters();
    requestInfo=new Request();
  }

  @BeforeClass
  public static void init() throws Exception
  {
   }
  @Test
  public void testSaveUserInfoForSaveOpration() throws Exception
  {
    response = new EnigmaResponse();
    parameters = new RequestParameters();
    requestInfo = new Request();
    requestInfo.setParameters(parameters);
    String userId =JunitConstants.USER_ID;
    String password = JunitConstants.PASSWORD;
    String userName = JunitConstants.USER_NAME;
    String opration =JunitConstants.OPRATION_SAVE;
    parameters.setUserId(userId);
    parameters.setPassword(password);
    parameters.setUserName(userName);
    parameters.setOpration(opration);
    Mockito.doNothing().when(userInfoDaoImpl).updateUserInfo(Matchers.any(UserInfo.class));
    response=userServiceImpl.saveUserInfo(requestInfo);
    Mockito.verify(userInfoDaoImpl,Mockito.times(1)).createUserInfo(Mockito.any(UserInfo.class));
    Assert.assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_SUCCESSFULLY_SAVE));
   }
  
  @Test
  public void testSaveUserInfoForUpdateOpration() throws Exception
  {
    response = new EnigmaResponse();
    parameters = new RequestParameters();
    requestInfo = new Request();
    requestInfo.setParameters(parameters);
    parameters.setUserId(JunitConstants.USER_ID);
    parameters.setPassword(JunitConstants.PASSWORD);
    parameters.setUserName(JunitConstants.USER_NAME);
    parameters.setOpration(JunitConstants.OPRATION_UPDATE);
    Mockito.doNothing().when(userInfoDaoImpl).updateUserInfo(Matchers.any(UserInfo.class));
    response=userServiceImpl.saveUserInfo(requestInfo);
    Mockito.verify(userInfoDaoImpl,Mockito.times(1)).updateUserInfo(Mockito.any(UserInfo.class));
    Assert.assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_SUCCESSFULLY_UPDATED));
  }
  
  @Test
  public void testSaveUserInfoForUserIdNull() throws Exception
  {
    response = new EnigmaResponse();
    parameters = new RequestParameters();
    requestInfo = new Request();
    requestInfo.setParameters(parameters);
    parameters.setUserId(null);
    parameters.setPassword(JunitConstants.PASSWORD);
    parameters.setUserName(JunitConstants.USER_NAME);
    parameters.setOpration(JunitConstants.OPRATION_UPDATE);
    response=userServiceImpl.saveUserInfo(requestInfo);
    Assert.assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_BAD_REQUEST));
  }
  
  @Test
  public void testDeleteUserInfoForUserId() throws Exception
  {
    response = new EnigmaResponse();
    parameters = new RequestParameters();
    requestInfo = new Request();
    requestInfo.setParameters(parameters);
    parameters.setUserId(JunitConstants.USER_ID);
    Mockito.doReturn(JunitConstants.COUNT_ONE).when(userInfoDaoImpl)
    .deleteUserInfo(Matchers.any(UserInfo.class));
    response=userServiceImpl.deleteUserInfo(JunitConstants.USER_ID);
    Assert.assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_SUCCESSFULLY_DELETED));
  }
  
  @Test
  public void testDeleteUserInfoForNullUserId() throws Exception
  {
    response = new EnigmaResponse();
    parameters = new RequestParameters();
    requestInfo = new Request();
    requestInfo.setParameters(parameters);
    parameters.setUserId(null);
    Mockito.doReturn(JunitConstants.COUNT_ONE).when(userInfoDaoImpl)
    .deleteUserInfo(Matchers.any(UserInfo.class));
    response=userServiceImpl.deleteUserInfo(JunitConstants.USER_ID);
    Assert.assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_BAD_REQUEST));
  }
  
  @Test
  public void testGetUserInfo() throws Exception
  {
    response = new EnigmaResponse();
    parameters = new RequestParameters();
    requestInfo = new Request();
    requestInfo.setParameters(parameters);
    parameters.setUserId(JunitConstants.USER_ID);
    UserInfo userInfo=new  UserInfo();
    userInfo.setUserId(JunitConstants.USER_ID);
    userInfo.setPassword(JunitConstants.PASSWORD);
    userInfo.setUserName(JunitConstants.USER_NAME);
    Mockito.doReturn(userInfo).when(userInfoDaoImpl)
    .getUserInfo(Matchers.anyString());
    UserInfo userInfo2=null;
    userInfo2=userServiceImpl.getUserInfo(JunitConstants.USER_ID);
    Assert.assertTrue(userInfo2.getUserId().equals(JunitConstants.USER_ID));
  }
  
  @Ignore
  @Test
  public void testGetUserInfoForNullUserId() throws Exception
  {
    response = new EnigmaResponse();
    parameters = new RequestParameters();
    requestInfo = new Request();
    parameters.setUserId(null);
    requestInfo.setParameters(parameters);
    parameters.setUserId(null);
    UserInfo userInfo=new  UserInfo();
    userInfo.setUserId(JunitConstants.USER_ID);
    userInfo.setPassword(JunitConstants.PASSWORD);
    userInfo.setUserName(JunitConstants.USER_NAME);
    Mockito.doReturn(userInfo).when(userInfoDaoImpl)
    .getUserInfo(Matchers.anyString());
    UserInfo userInfo2=null;
    userInfo2=userServiceImpl.getUserInfo(JunitConstants.USER_ID);
   // response=userServiceImpl.getUserInfo(requestInfo);
    Assert.assertTrue(response.getResponseCode().getMessage().equals(Constants.MESSAGE_BAD_REQUEST));
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
