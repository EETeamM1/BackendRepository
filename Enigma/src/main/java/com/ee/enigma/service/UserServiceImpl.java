package com.ee.enigma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.Constants;
import com.ee.enigma.dao.UserInfoDao;
import com.ee.enigma.model.UserInfo;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.response.ResponseCode;
import com.ee.enigma.response.ResponseResult;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService {

	// private Logger logger = Logger.getLogger(UserActivityDaoImpl.class);
	private UserInfoDao userInfoDao;
	private EnigmaResponse response;
	private ResponseCode responseCode;
	private ResponseResult result;

	@Autowired
	@Qualifier(value = "userInfoDao")
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public EnigmaResponse getUserInfo(String userId) {
		response = new EnigmaResponse();
		responseCode = new ResponseCode();
		result = new ResponseResult();
    try
    {
		try {
			userId = userId.trim();
		} catch (Exception e) {
			return CommonUtils.badRequest();
		}

		UserInfo userInfo = userInfoDao.getUserInfo(userId);
		
		if(null == userInfo){
			return userNotFound();
		}
		// Success response.
		result.setUser(userInfo);
		responseCode.setCode(Constants.CODE_SUCCESS);
		response.setResponseCode(responseCode);
		response.setResult(result);		
    }
    catch(Exception e)
    {
      CommonUtils.internalSeverError(response, responseCode);
      return response;
    }
		return response;		
	}

	public EnigmaResponse saveUserInfo(Request requestInfo, String operation) {
		response = new EnigmaResponse();
		responseCode = new ResponseCode();
    try
    {
		String userId;
		String password;
		String userName;
		try {
			userId = requestInfo.getParameters().getUserId().trim().toLowerCase();
			password = requestInfo.getParameters().getPassword();
			userName = requestInfo.getParameters().getUserName().trim();

		} catch (Exception e) {
			return CommonUtils.badRequest();
		}

		// Checking whether request contains all require fields or not.
		if (null == userId || null == userName || null == password || null == operation) {
			return CommonUtils.badRequest();
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setPassword(password);
		userInfo.setUserId(userId);
		userInfo.setUserName(userName);
		if (operation.equals("save")) {
			if(isUserExists(userId) == true){
				return duplicateRequest();
			}
			userInfoDao.createUserInfo(userInfo);
			responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_SAVE);
		} else if (operation.equals("update")) {
			userInfoDao.updateUserInfo(userInfo);
			responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_UPDATED);
		}
		// Success response.
		responseCode.setCode(Constants.CODE_SUCCESS);
		response.setResponseCode(responseCode);
    }
    catch(Exception e)
    {
      CommonUtils.internalSeverError(response, responseCode);
      return response;
    }
		return response;
	}

	public EnigmaResponse deleteUserInfo(String userId) {

		responseCode = new ResponseCode();
		response = new EnigmaResponse();
		try
		{
		try {
			userId = userId.trim();
		} catch (Exception e) {
			// logger.error(e);
			return CommonUtils.badRequest();
		}

		// Checking whether request contains all require fields or not.
		if (null == userId) {
			return CommonUtils.badRequest();
		}
		if(isUserExists(userId) == false){
			return userNotFound();
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		userInfoDao.deleteUserInfo(userInfo);
		responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_DELETED);
		// Success response.
		responseCode.setCode(Constants.CODE_SUCCESS);
		response.setResponseCode(responseCode);
		}
    catch(Exception e)
    {
      CommonUtils.internalSeverError(response, responseCode);
      return response;
    }
		return response;
	}

	public EnigmaResponse getAllUser() {
		response = new EnigmaResponse();
		responseCode = new ResponseCode();
		result = new ResponseResult();
    try
    {
		List<UserInfo> userInfoList = userInfoDao.getAllUserInfo();
		if(null == userInfoList){
			return userNotFound();
		}

		// Success response.
		result.setUserList(userInfoList);
		responseCode.setCode(Constants.CODE_SUCCESS);
		responseCode.setMessage(Constants.MESSAGE_SUCCESS);
		response.setResponseCode(responseCode);
		response.setResult(result);
    }
    catch(Exception e)
    {
      CommonUtils.internalSeverError(response, responseCode);
      return response;
    }
		return response;

	}
	
	@Override
	public EnigmaResponse searchUserResult(String searchQuery) {
		response = new EnigmaResponse();
		responseCode = new ResponseCode();
		result = new ResponseResult();
		try
		{
		if(null == searchQuery ||searchQuery.trim().isEmpty()){
	    	return CommonUtils.badRequest();
	    }
		
		List<UserInfo> userInfoList = userInfoDao.getUsersByIdAndName(searchQuery);

		// Success response.
		result.setUserList(userInfoList);
		responseCode.setCode(Constants.CODE_SUCCESS);
		responseCode.setMessage(Constants.MESSAGE_SUCCESS);
		response.setResponseCode(responseCode);
		response.setResult(result);
		}
    catch(Exception e)
    {
      CommonUtils.internalSeverError(response, responseCode);
      return response;
    }
		return response;
	}
	
	private EnigmaResponse userNotFound(){
		response = new EnigmaResponse();
		responseCode = new ResponseCode();
		
		responseCode.setCode(Constants.CODE_BAD_REQUEST);
		responseCode.setMessage(Constants.USER_NOT_EXISTING);
		response.setResponseCode(responseCode);		
		return response;
	}
	
	private EnigmaResponse duplicateRequest(){
		response = new EnigmaResponse();
		responseCode = new ResponseCode();
				responseCode.setCode(Constants.CODE_BAD_REQUEST);
		responseCode.setMessage(Constants.USER_ALREADY_EXISTS);
		response.setResponseCode(responseCode);		
		return response;
	}
	
	private boolean isUserExists(String userId){
		if(null != userInfoDao.getUserInfo(userId)){
			return true;
		}
		return false;
	}

}
