package com.ee.enigma.service.impl;

import java.sql.Time;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.common.Constants;
import com.ee.enigma.common.EnigmaException;
import com.ee.enigma.dao.DeviceInfoDao;
import com.ee.enigma.dao.DevicePushNotificationDao;
import com.ee.enigma.dao.LocationInfoDao;
import com.ee.enigma.dao.MasterDao;
import com.ee.enigma.dao.SessionDao;
import com.ee.enigma.dao.UserActivityDao;
import com.ee.enigma.dao.UserInfoDao;
import com.ee.enigma.dao.impl.UserActivityDaoImpl;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.DevicePushNotification;
import com.ee.enigma.model.LocationInfo;
import com.ee.enigma.model.Master;
import com.ee.enigma.model.Session;
import com.ee.enigma.model.UserActivity;
import com.ee.enigma.model.UserInfo;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.response.ResponseCode;
import com.ee.enigma.response.ResponseResult;
import com.ee.enigma.service.DeviceIssueInfoService;
import com.ee.enigma.service.UserLoginLogoutService;

@Service(value = "userLoginLogoutService")
@Transactional
public class UserLoginLogoutServiceImpl implements UserLoginLogoutService {

    private static final Logger LOGGER = Logger.getLogger(UserActivityDaoImpl.class);
    private UserActivityDao userActivityDao;
    private UserInfoDao userInfoDao;
    private LocationInfoDao locationInfoDao;
    private SessionDao sessionDao;
    private DeviceInfoDao deviceInfoDao;
    private MasterDao masterDao;
    private EnigmaResponse response;
    private ResponseCode responseCode;
    private ResponseResult result;
    private DeviceIssueInfoService deviceIssueInfoService;
    private DevicePushNotificationDao devicePushNotificationDao;

    @Autowired
    @Qualifier(value = "devicePushNotificationDao")
    public void setDevicePushNotificationDao(DevicePushNotificationDao devicePushNotificationDao) {
        this.devicePushNotificationDao = devicePushNotificationDao;
    }

    @Autowired
    @Qualifier(value = "deviceIssueInfoService")
    public void setDeviceIssueInfoService(DeviceIssueInfoService deviceIssueInfoService) {
        this.deviceIssueInfoService = deviceIssueInfoService;
    }

    @Autowired
    @Qualifier(value = "sessionDao")
    public void setSessionDao(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    @Autowired
    @Qualifier(value = "locationInfoDao")
    public void setLocationInfoDao(LocationInfoDao locationInfoDao) {
        this.locationInfoDao = locationInfoDao;
    }

    @Autowired
    @Qualifier(value = "userInfoDao")
    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    @Autowired
    @Qualifier(value = "masterDao")
    public void setMasterDao(MasterDao masterDao) {
        this.masterDao = masterDao;
    }

    @Autowired
    @Qualifier(value = "deviceInfoDao")
    public void setDeviceInfoDao(DeviceInfoDao deviceInfoDao) {
        this.deviceInfoDao = deviceInfoDao;
    }

    @Autowired
    @Qualifier(value = "userActivityDao")
    public void setUserActivityDao(UserActivityDao userActivityDao) {
        this.userActivityDao = userActivityDao;
    }

    @SuppressWarnings("deprecation")
    @Override
    public EnigmaResponse userLoginService(Request loginInfo) throws EnigmaException {

        response = new EnigmaResponse();
        responseCode = new ResponseCode();
        result = new ResponseResult();
        try {
            String userId;
            String password;
            String deviceId;
            float latitude;
            float longitude;
            String osVersion;
            String deviceToken;

			userId = loginInfo.getParameters().getUserId().trim();
			password = loginInfo.getParameters().getPassword();
			deviceId = loginInfo.getParameters().getDeviceId().trim();
			latitude = loginInfo.getParameters().getLatitude();
			longitude = loginInfo.getParameters().getLongitude();
			osVersion = loginInfo.getParameters().getOsVersion();
			deviceToken = loginInfo.getParameters().getDeviceToken();
            
    		// Checking device info
    		DeviceInfo deviceInfo = deviceInfoDao.getDeviceInfo(deviceId);

            EnigmaResponse checkRequest = checkRequest(userId, password, deviceInfo);
            if(null != checkRequest)
            {
            	return checkRequest;
            }
    		
            Time timeoutPeriod = deviceInfo.getTimeoutPeriod();

            // fetching Master passwrod
            Master master = masterDao.getMasterInfo();
            String masterPassword = master.getMasterPassword();


            // call "DeviceIssueInfoService" to get issueId
            String issueId = deviceIssueInfoService.populateDeviceIssueInfo(deviceId, userId);

            // call "GeoLocationService" to get location name from Geo
            // coordinates.
            String location = getGeoLocation(latitude, longitude);

            // Checking is previous user logout on device
            userActivityDao.logOutBYDeviceId(deviceId);
            String activityId = createNewActivity(userId, deviceId, deviceToken, timeoutPeriod, issueId, location, osVersion, deviceInfo);

            // Success response.
            responseCode.setCode(Constants.CODE_SUCCESS);
            responseCode.setMessage(Constants.MESSAGE_SUCCESS);
            result.setSessionToken(activityId);
            result.setMasterPassword(masterPassword);
            result.setTimeout(timeoutPeriod.getHours() * 60 + timeoutPeriod.getMinutes());
            response.setResponseCode(responseCode);
            response.setResult(result);
        } catch (NullPointerException npe) {
            LOGGER.error(npe);
            return badRequest();
        }catch (HibernateException e) {
            throw new EnigmaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e);
        } catch (Exception e) {
            throw new EnigmaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e, e);
        }
        return response;
    }

	/**
	 * @param userId
	 * @param password
	 * @param deviceInfo 
	 * @return
	 */
	private EnigmaResponse checkRequest(String userId, String password, DeviceInfo deviceInfo) {
		// Checking whether request contains all require fields or not.
		if (null == userId || null == password) {
		    return badRequest();
		}

		// Authenticating User.
		if (!isUserAuthenticated(userId, password)) {
		    return authenticationFailedResponse();
		}
		
		if (null == deviceInfo) {
		    return deviceNotRegisteredResponse();
		}
		return null;
	}

	/**
	 * @param userId
	 * @param deviceId
	 * @param deviceToken
	 * @param timeoutPeriod
	 * @param issueId
	 * @param location
	 * @return
	 */
	private String createNewActivity(String userId, String deviceId, String deviceToken, Time timeoutPeriod,
			String issueId, String location, String osVersion, DeviceInfo deviceInfo) {

        // check and update OS Version.
        if (osVersion != null && !osVersion.equals(deviceInfo.getOsVersion())) {
            deviceInfo.setOsVersion(osVersion);
            deviceInfoDao.updateDeviceInfo(deviceInfo);
        }
		// make UserActivity entry.
		Date loginTime = new Date();
		String activityId = activityIdGenerator(userId, loginTime.getTime());
		UserActivity userAct = new UserActivity();
		userAct.setUserId(userId);
		userAct.setDeviceId(deviceId);
		userAct.setIssueId(issueId);
		userAct.setLocation(location);
		userAct.setLoginTime(loginTime);
		userAct.setActivityId(activityId);
		userActivityDao.createNewActivity(userAct);

		// create session validity entry.
		createSessionEntry(activityId, loginTime, timeoutPeriod);
		updateDevicePushNotification(timeoutPeriod, deviceId, deviceToken, userId, activityId);
		return activityId;
	}

    private void createSessionEntry(String activityId, Date loginTime, Time timeoutPeriod) {
        Session session = new Session();
        session.setActivityId(activityId);
        Date notificationStartTime = getNotificationStartTime(loginTime, timeoutPeriod, 0);
        session.setNotificationStartTime(notificationStartTime);
        sessionDao.createSeesionEntry(session);
    }

    private void updateDevicePushNotification(Time timeoutPeriod, String deviceId, String deviceToken, String userId,
            String activityId) {
        DevicePushNotification devicePushNotification = devicePushNotificationDao.getDeviceInfo(deviceId);
        devicePushNotification.setPushNotificationStartTime(getNotificationStartTime(new Date(), timeoutPeriod, 0));
        devicePushNotification.setPushNotificationEndTime(getNotificationStartTime(new Date(), timeoutPeriod, 1));
        devicePushNotification.setDeviceToken(deviceToken);
        devicePushNotification.setUserId(userId);
        devicePushNotification.setActivityId(activityId);
        devicePushNotificationDao.updateDevicePushNotification(devicePushNotification);
    }

    private String getGeoLocation(float latitude, float longitude) {
        LocationInfo locationInfo;
        String location = null;
        if (0 < latitude && 0 < longitude) {
            locationInfo = locationInfoDao.getLocationName(latitude, longitude);
            if (null == locationInfo) {
                location = latitude + "," + longitude;
            } else {
                location = locationInfo.getLocation();
            }
        }
        return location;
    }

    private EnigmaResponse deviceNotRegisteredResponse() {
        responseCode.setCode(Constants.CODE_NOT_FOUND);
        responseCode.setMessage(Constants.MESSAGE_NOT_FOUND_DEVICE);
        response.setResponseCode(responseCode);
        return response;
    }

    private EnigmaResponse authenticationFailedResponse() {
        responseCode.setCode(Constants.CODE_AUTHENTICATION_FAILD);
        responseCode.setMessage(Constants.MESSAGE_AUTHENTICATION_FAILD);
        response.setResponseCode(responseCode);
        return response;
    }

    private EnigmaResponse badRequest() {
        responseCode.setCode(Constants.CODE_BAD_REQUEST);
        responseCode.setMessage(Constants.MESSAGE_BAD_REQUEST);
        response.setResponseCode(responseCode);
        return response;
    }

    @SuppressWarnings("deprecation")
    private Date getNotificationStartTime(Date loginTime, Time timeoutPeriod, int delay) {
        int hour = loginTime.getHours() + timeoutPeriod.getHours() + delay;
        int minutes = loginTime.getMinutes() + timeoutPeriod.getMinutes();
        Date sessionExpireTime = new Date(loginTime.getTime());
        sessionExpireTime.setHours(hour);
        sessionExpireTime.setMinutes(minutes);
        return sessionExpireTime;
    }

    private String activityIdGenerator(String userId, long time) {
        return userId + "_" + time;
    }

    private boolean isUserAuthenticated(String userId, String password) {
        UserInfo userInfo = userInfoDao.getUserInfo(userId, password);
        if (null != userInfo) {
            return true;
        }
        return false;
    }

    public EnigmaResponse userLogoutService(Request logoutInfo) throws EnigmaException {
        String sessionToken = null;

        response = new EnigmaResponse();
        responseCode = new ResponseCode();
        try {
            try {
                sessionToken = logoutInfo.getParameters().getSessionToken();
            } catch (Exception e) {
                LOGGER.error(e);
                return badRequest();
            }
            // Checking whether request contains require field sessionToken or
            // not.
            if (null == sessionToken) {
                return badRequest();
            }
            String status = userActivityDao.logOutBYActivityId(sessionToken);

            if (status.equals(Constants.MESSAGE_SUCCESS)) {
                // Success response.
                responseCode.setCode(Constants.CODE_SUCCESS);
                responseCode.setMessage(Constants.LOGOUT_SUCCESS);
            } else {
                // Failure response
                responseCode.setCode(Constants.CODE_BAD_REQUEST);
                responseCode.setMessage(status);

            }
            response.setResponseCode(responseCode);
            response.setResult(null);
        } catch (HibernateException e) {
            throw new EnigmaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e);
        } catch (Exception e) {
            throw new EnigmaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e, e);
        }
        return response;
    }

}
