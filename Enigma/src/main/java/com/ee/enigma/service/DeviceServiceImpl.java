package com.ee.enigma.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.Constants;
import com.ee.enigma.dao.DeviceInfoDao;
import com.ee.enigma.dao.DeviceIssueInfoDao;
import com.ee.enigma.dao.UserActivityDaoImpl;
import com.ee.enigma.dto.DeviceInfoDto;
import com.ee.enigma.dto.TopDeviceDto;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.DeviceIssueInfo;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.response.ResponseCode;
import com.ee.enigma.response.ResponseResult;

@Service(value = "deviceService")
@Transactional
public class DeviceServiceImpl implements DeviceService {

	private Logger logger = Logger.getLogger(UserActivityDaoImpl.class);
	private DeviceInfoDao deviceInfoDao;
	private DeviceIssueInfoDao deviceIssueInfoDao;
	private EnigmaResponse response;
	private ResponseCode responseCode;
	private ResponseResult result;

	@Autowired
	@Qualifier(value = "deviceInfoDao")
	public void setDeviceInfoDao(DeviceInfoDao deviceInfoDao) {
		this.deviceInfoDao = deviceInfoDao;
	}
	
	@Autowired
	@Qualifier(value = "deviceIssueInfoDao")
	public void setDeviceIssueInfoDao(DeviceIssueInfoDao deviceIssueInfoDao) {
		this.deviceIssueInfoDao = deviceIssueInfoDao;
	}

	//public List<DeviceInfoDto> getDevicesInfoByStatus(Request requestInfo) {
	public List<DeviceInfoDto> getDevicesInfoByStatus(String deviceId,String deviceStatus) {
	
		List<DeviceInfoDto> deviceInfoDtos = new ArrayList<DeviceInfoDto>();
		/*try {
			deviceId = requestInfo.getParameters().getDeviceId().trim();
			deviceStatus = requestInfo.getParameters().getDeviceStatus().trim();
		} catch (Exception e) {
			logger.error(e);
		}*/
		List<DeviceInfo> deviceInfos = deviceInfoDao.getDevicesListByIDAndStatus(deviceId, deviceStatus);
		DeviceInfoDto deviceInfoDto = new DeviceInfoDto();
		DeviceInfo deviceInfo = null;
		if (deviceInfos != null && deviceInfos.size() > 0) {
			for (int i = 0; i < deviceInfos.size(); i++) {
				deviceInfo = deviceInfos.get(i);
				deviceInfoDtos.add(deviceInfoDto.getDeviceInfoDto(deviceInfo));
			}
		}
		return deviceInfoDtos;
	}

	public EnigmaResponse saveDeviceInfo(Request requestInfo) {
		response = new EnigmaResponse();
		responseCode = new ResponseCode();
		result = new ResponseResult();

		String deviceId;
		String deviceName;
		// String isAdminApproved;
		String opration;
		String manufacturer;
		String oS;
		String osVersion;
		String yearOfManufacturing;
		DeviceInfo deviceInfo = null;
		try {
			deviceId = requestInfo.getParameters().getDeviceId().trim();
			deviceName = requestInfo.getParameters().getDeviceName().trim();
			opration = requestInfo.getParameters().getOpration().trim();
			manufacturer = requestInfo.getParameters().getManufacturer().trim();
			oS = requestInfo.getParameters().getoS().trim();
			osVersion = requestInfo.getParameters().getOsVersion().trim();
			// timeoutPeriod =
			// requestInfo.getParameters().getTimeoutPeriod().trim();
			yearOfManufacturing = requestInfo.getParameters().getYearOfManufacturing().trim();
		} catch (Exception e) {
			logger.error(e);
			return badRequest();
		}

		// Checking whether request contains all require fields or not.
		if (null == deviceId || null == deviceName || null == opration || null == manufacturer || null == oS
				|| null == osVersion || null == yearOfManufacturing) {
			return badRequest();
		}
		deviceInfo = new DeviceInfo();
		deviceInfo.setDeviceId(deviceId);
		deviceInfo.setDeviceName(deviceName);
		// deviceInfo.setIsAdminApproved(isAdminApproved);
		deviceInfo.setManufacturer(manufacturer);
		deviceInfo.setOS(oS);
		deviceInfo.setOSVersion(osVersion);
		// deviceInfo.setTimeoutPeriod(timeoutPeriod);
		deviceInfo.setYearOfManufacturing(yearOfManufacturing);
		if (opration.equals("save")) {
			deviceInfoDao.createDeviceInfo(deviceInfo);
			responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_SAVE);
		} else if (opration.equals("update")) {
			deviceInfoDao.updateDeviceInfo(deviceInfo);
			responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_UPDATED);
		}
		// Success response.
		responseCode.setCode(Constants.CODE_SUCCESS);
		// result.setSessionToken(activityId);
		response.setResponseCode(responseCode);
		response.setResult(result);
		return response;
	}

	public EnigmaResponse updateDeviceInfoStatus(Request requestInfo) {

		response = new EnigmaResponse();
		responseCode = new ResponseCode();
		result = new ResponseResult();

		String deviceId = null;
		String deviceStatus = null;
		try {
			deviceId = requestInfo.getParameters().getDeviceId().trim();
			deviceStatus = requestInfo.getParameters().getDeviceStatus().trim();
		} catch (Exception e) {
			logger.error(e);
			return badRequest();
		}

		// Checking whether request contains all require fields or not.
		if (null == deviceId || null == deviceStatus) {
			return badRequest();
		}
		DeviceInfo deviceInfo = deviceInfoDao.getDeviceInfo(deviceId);
		if (deviceInfo != null) {
			deviceInfo.setDeviceAvailability(deviceStatus);
			deviceInfoDao.updateDeviceInfo(deviceInfo);
			responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_UPDATED);
			// Success response.
			responseCode.setCode(Constants.CODE_SUCCESS);
			// result.setSessionToken(activityId);
			response.setResponseCode(responseCode);
			response.setResult(result);
			return response;
		}
		// Success response.
		responseCode.setMessage(Constants.MESSAGE_NOT_FOUND_DEVICE);
		responseCode.setCode(Constants.CODE_NOT_FOUND);
		// result.setSessionToken(activityId);
		response.setResponseCode(responseCode);
		response.setResult(result);
		return response;

	}

	public EnigmaResponse deleteDeviceInfo(String deviceId) {
		response = new EnigmaResponse();
		responseCode = new ResponseCode();
		result = new ResponseResult();
		/*String deviceId;

		try {
			deviceId = requestInfo.getParameters().getDeviceId().trim();
		} catch (Exception e) {
			logger.error(e);
			return badRequest();
		}*/

		// Checking whether request contains all require fields or not.
		if (null == deviceId) {
			return badRequest();
		}
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setDeviceId(deviceId);
		deviceInfoDao.deleteDeviceInfo(deviceInfo);
		responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_DELETED);

		// Success response.
		responseCode.setCode(Constants.CODE_SUCCESS);
		response.setResponseCode(responseCode);
		response.setResult(result);
		return response;
	}

	public DeviceInfoDto getDeviceInfo(String deviceId) {
		//String deviceId = "";
		DeviceInfoDto deviceInfoDto = new DeviceInfoDto();
		/*try {
			deviceId = requestInfo.getParameters().getDeviceId().trim();
		} catch (Exception e) {
			logger.error(e);
		}*/
		
		DeviceInfo deviceInfo = deviceInfoDao.getDeviceInfo(deviceId);
		return deviceInfoDto.getDeviceInfoDto(deviceInfo);
	}

	private EnigmaResponse badRequest() {
		responseCode.setCode(Constants.CODE_BAD_REQUEST);
		responseCode.setMessage(Constants.MESSAGE_BAD_REQUEST);
		response.setResponseCode(responseCode);
		return response;
	}

	@Override
	public EnigmaResponse approveDevice(Request requestInfo) {
		String deviceId = null;
		boolean isAdminApproved = false;
		response = new EnigmaResponse();
		responseCode = new ResponseCode();
		try {
			deviceId = requestInfo.getParameters().getDeviceId();
			isAdminApproved = requestInfo.getParameters().getIsAdminApproved();
		} catch (Exception e) {
			logger.error(e);
			return badRequest();
		}
		// Checking whether request contains require field deviceId or not.
		if (null == deviceId) {
			return badRequest();
		}
		
		approveDeviceOperation(deviceId, isAdminApproved, responseCode);

		response.setResponseCode(responseCode);
		response.setResult(null);

		return response;
	}
	
	private void approveDeviceOperation(String deviceId, boolean isAdminApproved, ResponseCode approveResponseCode) {
		DeviceInfo deviceInfo = deviceInfoDao.getDeviceInfo(deviceId);
		if (null != deviceInfo && deviceInfo.getDeviceAvailability().equals(Constants.DEVICE_STATUS_PENDING)) {
			if (isAdminApproved) {
				deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_AVAILABLE);
				approveResponseCode.setMessage(Constants.MESSAGE_DEVICE_SUBMITTED);
			} else {
				deviceInfo.setDeviceAvailability(Constants.DEVICE_STATUS_ISSUED);
				approveResponseCode.setMessage(Constants.MESSAGE_DEVICE_SUBMISSION_IS_REJECTED);
				cancelSubmitDevice(deviceId);
			}
			deviceInfoDao.updateDeviceInfo(deviceInfo);
			// Success response.
			approveResponseCode.setCode(Constants.CODE_SUCCESS);
		} else {
			// Failure response
			approveResponseCode.setCode(Constants.CODE_NOT_FOUND);
			approveResponseCode.setMessage(Constants.MESSAGE_DEVICE_IS_NOT_PENDING);
		}
	}

	private void cancelSubmitDevice(String deviceId) {
		DeviceIssueInfo deviceIssueInfo = deviceIssueInfoDao.getDeviceIssueInfoByDeviceId(deviceId);
		if(null != deviceIssueInfo) {
			deviceIssueInfo.setSubmitTime(null);
			deviceIssueInfo.setSubmitByAdmin(null);
			deviceIssueInfoDao.updateDeviceIssueInfo(deviceIssueInfo);
		}
	}

	@Override
	public List<TopDeviceDto> getTopDevices() {
		Date lastMonthDate = CommonUtils.getLastMonthAndDate();
		List<TopDeviceDto> topDeviceDtoList = deviceIssueInfoDao.getTopDevices(lastMonthDate);
		return topDeviceDtoList;
	}

@Override
public EnigmaResponse searchDevice(String searchQuery) {	
	response = new EnigmaResponse();
    responseCode = new ResponseCode();
    result = new ResponseResult();
    
    if(null == searchQuery ||searchQuery.trim().isEmpty()){
    	return badRequest();
    }
    
	List<DeviceInfo> deviceInfoList = deviceInfoDao.getDeviceFields(searchQuery);
    
	result.setDeviceList(deviceInfoList);
    responseCode.setCode(Constants.CODE_SUCCESS);
    response.setResponseCode(responseCode);
    response.setResult(result);
    return response;
}

}
