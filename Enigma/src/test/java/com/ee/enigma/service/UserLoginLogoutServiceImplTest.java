package com.ee.enigma.service;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import com.ee.enigma.common.Constants;
import com.ee.enigma.dao.impl.UserActivityDaoImpl;
import com.ee.enigma.model.UserActivity;
import com.ee.enigma.request.Request;
import com.ee.enigma.request.RequestParameters;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.service.impl.UserLoginLogoutServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class UserLoginLogoutServiceImplTest {
	
	  @InjectMocks
	  private UserLoginLogoutServiceImpl userLoginLogoutServiceImpl;
	  
	  @InjectMocks
	  private UserActivityDaoImpl userActivityDaoImpl;
	  
	  @Mock
	  private SessionFactory sessionFactory;
	  @Mock
	  private Session session;
	  
	  private Request requestInfo=null;
	  private RequestParameters parameters;


	@Before
	  public void setUp() throws Exception
	  {
	    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
	    Mockito.when(sessionFactory.openSession()).thenReturn(session);
	    parameters=new RequestParameters();
	    requestInfo=new Request();
	    userLoginLogoutServiceImpl.setUserActivityDao(userActivityDaoImpl);
	  }
	
	  @Test
	  public void testUserLogoutService() throws Exception
	  {
		  parameters.setSessionToken("NotExist");
		  requestInfo.setParameters(parameters);
		  
		  Mockito.when(userActivityDaoImpl.getUserActivityByActivityId(requestInfo.getParameters().getSessionToken()))
		  .thenReturn(null);
		  EnigmaResponse enigma = userLoginLogoutServiceImpl.userLogoutService(requestInfo);
		  Assert.assertEquals(Constants.CODE_BAD_REQUEST, enigma.getResponseCode().getCode());
		  Assert.assertEquals(Constants.USER_ACTIVITY_ID_NOT_FOUND, enigma.getResponseCode().getMessage());
		  
		  parameters.setSessionToken("AlreadyLogout");
		  requestInfo.setParameters(parameters);
		  UserActivity userActivity = new UserActivity();
		  userActivity.setLogoutTime(new Date());
		  Mockito.when(userActivityDaoImpl.getUserActivityByActivityId(requestInfo.getParameters().getSessionToken()))
		  .thenReturn(userActivity);
		  enigma = userLoginLogoutServiceImpl.userLogoutService(requestInfo);
		  Assert.assertEquals(Constants.CODE_BAD_REQUEST, enigma.getResponseCode().getCode());
		  Assert.assertEquals(Constants.USER_ALREADY_LOGOUT, enigma.getResponseCode().getMessage());
		  

		  parameters.setSessionToken("UserLogout");
		  requestInfo.setParameters(parameters);
		  userActivity.setLogoutTime(null);
		  Mockito.when(userActivityDaoImpl.getUserActivityByActivityId(requestInfo.getParameters().getSessionToken()))
		  .thenReturn(userActivity);
		  enigma = userLoginLogoutServiceImpl.userLogoutService(requestInfo);
		  Assert.assertEquals(Constants.CODE_SUCCESS, enigma.getResponseCode().getCode());
		  Assert.assertEquals(Constants.LOGOUT_SUCCESS, enigma.getResponseCode().getMessage());
	  }
	
}
