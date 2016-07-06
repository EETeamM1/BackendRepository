package com.ee.enigma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.common.Constants;
import com.ee.enigma.dao.UserInfoDao;
import com.ee.enigma.model.UserInfo;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.response.ResponseCode;
import com.ee.enigma.response.ResponseResult;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService
{

  //private Logger logger = Logger.getLogger(UserActivityDaoImpl.class);
  private UserInfoDao userInfoDao;
  private EnigmaResponse response;
  private ResponseCode responseCode;
  private ResponseResult result;

  @Autowired
  @Qualifier(value = "userInfoDao")
  public void setUserInfoDao(UserInfoDao userInfoDao)
  {
    this.userInfoDao = userInfoDao;
  }
  public UserInfo getUserInfo(String userId){
    try
    {
      userId = userId.trim();
       }
    catch (Exception e)
    {
     // logger.error(e);
     
    }
    return  userInfoDao.getUserInfo(userId);
  }
  

  public EnigmaResponse saveUserInfo(Request requestInfo)
  {
    response = new EnigmaResponse();
    responseCode = new ResponseCode();
    result = new ResponseResult();

    String userId;
    String password;
    String userName;
    String opration;
    try
    {
      userId = requestInfo.getParameters().getUserId().trim();
      password = requestInfo.getParameters().getPassword().trim();
      userName = requestInfo.getParameters().getUserName().trim();
      opration = requestInfo.getParameters().getOpration().trim();

    }
    catch (Exception e)
    {
     // logger.error(e);
      return badRequest();
    }

    // Checking whether request contains all require fields or not.
    if (null == userId || null == userName || null == password || null == opration)
    {
      return badRequest();
    }
    UserInfo userInfo = new UserInfo();
    userInfo.setPassword(password);
    userInfo.setUserId(userId);
    userInfo.setUserName(userName);
    if (opration.equals("save"))
    {
      userInfoDao.createUserInfo(userInfo);
      responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_SAVE);
    }
    else if (opration.equals("update"))
    {
      userInfoDao.updateUserInfo(userInfo);
      responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_UPDATED);
    }

    // Success response.
    responseCode.setCode(Constants.CODE_SUCCESS);
    // result.setSessionToken(activityId);
    response.setResponseCode(responseCode);
    response.setResult(result);
    return response;
  }

  public EnigmaResponse deleteUserInfo(String userId)
  {
   
	responseCode = new ResponseCode();
	response = new EnigmaResponse();
    result = new ResponseResult();
    try
    {
      userId = userId.trim();
    }
    catch (Exception e)
    {
     // logger.error(e);
      return badRequest();
    }

    // Checking whether request contains all require fields or not.
    if (null == userId)
    {
      return badRequest();
    }
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(userId);
    userInfoDao.deleteUserInfo(userInfo);
    responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_DELETED);

    // Success response.
    responseCode.setCode(Constants.CODE_SUCCESS);
    response.setResponseCode(responseCode);
    return response;
  }

  /*public EnigmaResponse getUserInfo(Request requestInfo)
  {
    response = new EnigmaResponse();
    responseCode = new ResponseCode();
    result = new ResponseResult();
    String userId;

    try
    {
      userId = requestInfo.getParameters().getUserId().trim();
    }
    catch (Exception e)
    {
     // logger.error(e);
      return badRequest();
    }

    // Checking whether request contains all require fields or not.
    if (null == userId)
    {
      return badRequest();
    }
    UserInfo userInfo=  userInfoDao.getUserInfo(userId);
    responseCode.setResultObject(userInfo);
    responseCode.setMessage(Constants.MESSAGE_SUCCESS);

    // Success response.
    responseCode.setCode(Constants.CODE_SUCCESS);
    response.setResponseCode(responseCode);
    response.setResult(result);
    return response;

  }
*/
  private EnigmaResponse badRequest()
  {
    responseCode.setCode(Constants.CODE_BAD_REQUEST);
    responseCode.setMessage(Constants.MESSAGE_BAD_REQUEST);
    response.setResponseCode(responseCode);
    return response;
  }

}
