package com.ee.enigma.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.common.Constants;
import com.ee.enigma.dao.DeviceInfoDao;
import com.ee.enigma.dao.MasterDao;
import com.ee.enigma.dao.UserActivityDao;
import com.ee.enigma.dao.UserActivityDaoImpl;
import com.ee.enigma.dao.UserInfoDao;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.Master;
import com.ee.enigma.model.Request;
import com.ee.enigma.model.Response;
import com.ee.enigma.model.ResponseCode;
import com.ee.enigma.model.ResponseResult;
import com.ee.enigma.model.UserActivity;
import com.ee.enigma.model.UserInfo;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;

@Service(value = "userActivityService")
@Transactional
public class UserActivityServiceImpl implements UserActivityService {

	private Logger logger = Logger.getLogger(UserActivityDaoImpl.class);
	private UserActivityDao userActivityDao;
	private UserInfoDao userInfoDao;
	private DeviceInfoDao deviceInfoDao;
	private MasterDao masterDao;
	private Response response;
	private ResponseCode responseCode;
	private ResponseResult result;
	
	@Autowired
	@Qualifier(value="userInfoDao")
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
	@Autowired
	@Qualifier(value="masterDao")
	public void setMasterDao(MasterDao masterDao) {
		this.masterDao = masterDao;
	}
	@Autowired
	@Qualifier(value="deviceInfoDao")
	public void setDeviceInfoDao(DeviceInfoDao deviceInfoDao) {
		this.deviceInfoDao = deviceInfoDao;
	}
	@Autowired
	@Qualifier(value = "userActivityDao")
	public void setUserActivityDao(UserActivityDao userActivityDao) {
		this.userActivityDao = userActivityDao;
	}

	public Response userLoginService(Request loginInfo) {

		response = new Response();
		responseCode = new ResponseCode();
		result = new ResponseResult();

		String userId = loginInfo.getParameters().getUserId();
		String password = loginInfo.getParameters().getPassword();
		long deviceId = loginInfo.getParameters().getDeviceId();
		float latitude = loginInfo.getParameters().getLatitude();
		float longitude = loginInfo.getParameters().getLongitude();
		String osVersion = loginInfo.getParameters().getOsVersion();

		// Checking whether request contains all require fields or not.
		if (null == userId || null == password || 0 == deviceId || 0 == latitude || 0 == longitude) {
			responseCode.setCode(Constants.CODE_BAD_REQUEST);
			responseCode.setMessage(Constants.MESSAGE_BAD_REQUEST);
			response.setResponseCode(responseCode);
			return response;
		}

		// Authenticating User.
		if(!IsUserAuthenticated(userId, password)){
			responseCode.setCode(Constants.CODE_AUTHENTICATION_FAILD);
			responseCode.setMessage(Constants.MESSAGE_AUTHENTICATION_FAILD);
			response.setResponseCode(responseCode);
			return response;
		}		
		
		// Checking device info
		DeviceInfo deviceInfo = deviceInfoDao.getDeviceInfo(deviceId);
		if(null == deviceInfo){
			responseCode.setCode(Constants.CODE_NOT_FOUND);
			responseCode.setMessage(Constants.MESSAGE_NOT_FOUND_DEVICE);
			response.setResponseCode(responseCode);
			return response;
		}		
		int timeoutPeriod = deviceInfo.getTimeoutPeriod();
		String masterPassword = null;
		if (!deviceInfo.isMasterSet()) {
			//Get Master Password.
			Master master = masterDao.getMasterInfo();
			masterPassword = master.getMasterPassword();
		}

		// call "DeviceIssueInfoService" to get issueId
		String issueId = "yyyymmdd1234";

		// call "GeoLocationService" to get location name from Geo coordinates.
		String location = "location";

		// create sessionToken
		String sessionToken = "skdfeo12334";

		//make UserActivity entry. 
		UserActivity userAct = new UserActivity();
		userAct.setDeviceId(deviceId);
		userAct.setIssueId(issueId);
		userAct.setLocation(location);
		Date loginTime = new Date();
		userAct.setLoginTime(new java.sql.Date(loginTime.getTime()));
		userAct.setUserId(userId);
		userActivityDao.createNewActivity(userAct);

		//Success response.
		responseCode.setCode(Constants.CODE_SUCCESS);
		responseCode.setMessage(Constants.MESSAGE_SUCCESS);
		result.setSessionToken(sessionToken);
		result.setTimeout(timeoutPeriod);
		response.setResponseCode(responseCode);
		response.setResult(result);

		return response;
	}

	private boolean IsUserAuthenticated(String userId, String password) {
		UserInfo userInfo = userInfoDao.getUserInfo(userId);
		if(null == userInfo || password == userInfo.getPassword()){
			return true;
		}
		return false;				
	}

	public void userLogoutService(Request logoutInfo) {
		// TODO Auto-generated method stub

	}

}
