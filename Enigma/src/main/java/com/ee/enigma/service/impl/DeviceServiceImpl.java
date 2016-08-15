package com.ee.enigma.service.impl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.Constants;
import com.ee.enigma.common.EngimaException;
import com.ee.enigma.dao.DeviceInfoDao;
import com.ee.enigma.dao.DeviceIssueInfoDao;
import com.ee.enigma.dao.DevicePushNotificationDao;
import com.ee.enigma.dao.impl.UserActivityDaoImpl;
import com.ee.enigma.dto.DeviceInfoDto;
import com.ee.enigma.dto.DeviceReportDto;
import com.ee.enigma.dto.TopDeviceDto;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.DeviceIssueInfo;
import com.ee.enigma.model.DevicePushNotification;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.response.ResponseCode;
import com.ee.enigma.response.ResponseResult;
import com.ee.enigma.service.DeviceService;

import edu.emory.mathcs.backport.java.util.Collections;

@Service(value = "deviceService")
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private static final Logger LOGGER = Logger.getLogger(UserActivityDaoImpl.class);
    private DeviceInfoDao deviceInfoDao;
    private DevicePushNotificationDao devicePushNotificationDao;
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
    @Qualifier(value = "devicePushNotificationDao")
    public void setDevicePushNotificationDao(DevicePushNotificationDao devicePushNotificationDao) {
        this.devicePushNotificationDao = devicePushNotificationDao;
    }

    @Autowired
    @Qualifier(value = "deviceIssueInfoDao")
    public void setDeviceIssueInfoDao(DeviceIssueInfoDao deviceIssueInfoDao) {
        this.deviceIssueInfoDao = deviceIssueInfoDao;
    }

    public List<DeviceInfoDto> getDevicesInfoByStatus(String deviceId, String deviceStatus) {

        List<DeviceInfoDto> deviceInfoDtos = new ArrayList<DeviceInfoDto>();
        List<DeviceInfo> deviceInfos = deviceInfoDao.getDevicesListByIDAndStatus(deviceId, deviceStatus);
        DeviceInfoDto deviceInfoDto = new DeviceInfoDto();
        DeviceInfo deviceInfo = null;
        if (deviceInfos != null && !deviceInfos.isEmpty()) {
            for (int i = 0; i < deviceInfos.size(); i++) {
                deviceInfo = deviceInfos.get(i);
                deviceInfoDtos.add(deviceInfoDto.getDeviceInfoDto(deviceInfo));
            }
        }
        return deviceInfoDtos;
    }

    public EnigmaResponse saveDeviceInfo(Request requestInfo) throws EngimaException {
        response = new EnigmaResponse();
        responseCode = new ResponseCode();
        result = new ResponseResult();
        String deviceId;
        String deviceName;
        String opration;
        String manufacturer;
        String oS;
        String osVersion;
        String yearOfManufacturing;
        Time timeOutPeriod;
        DeviceInfo deviceInfo = null;
        try {
            try {
                deviceId = requestInfo.getParameters().getDeviceId().trim();
                deviceName = requestInfo.getParameters().getDeviceName().trim();
                opration = requestInfo.getParameters().getOpration().trim();
                manufacturer = requestInfo.getParameters().getManufacturer().trim();
                oS = requestInfo.getParameters().getoS().trim();
                osVersion = requestInfo.getParameters().getOsVersion().trim();
                yearOfManufacturing = requestInfo.getParameters().getYearOfManufacturing().trim();
                timeOutPeriod = requestInfo.getParameters().getTimeoutPeriod();
            } catch (Exception e) {
                LOGGER.error(e);
                return CommonUtils.badRequest(response, responseCode);
            }

            // Checking whether request contains all require fields or not.
            if (deviceId.isEmpty() || opration.isEmpty() || manufacturer.isEmpty() || oS.isEmpty()){
                return CommonUtils.badRequest(response, responseCode);
            }
            if( osVersion.isEmpty() || yearOfManufacturing.isEmpty()) {
                return CommonUtils.badRequest(response, responseCode);
            }
            deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId(deviceId);
            deviceInfo.setDeviceName(deviceName);
            deviceInfo.setManufacturer(manufacturer);
            deviceInfo.setOs(oS);
            deviceInfo.setOsVersion(osVersion);
            deviceInfo.setYearOfManufacturing(yearOfManufacturing);
            deviceInfo.setTimeoutPeriod(timeOutPeriod);
            deviceInfo.setDeviceAvailability("Available");
            if ("save".equals(opration)) {
                if (isDeviceExists(deviceId)) {
                    return CommonUtils.duplicateRequest(response, responseCode, Constants.DEVICE_ALREADY_EXISTS);
                }
                deviceInfoDao.createDeviceInfo(deviceInfo);
                DevicePushNotification devicePushNotification = new DevicePushNotification();
                devicePushNotification.setDeviceId(deviceId);
                devicePushNotificationDao.saveDevicePushNotification(devicePushNotification);
                responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_SAVE);
            } else if ("update".equals(opration)) {
                deviceInfoDao.updateDeviceInfo(deviceInfo);
                responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_UPDATED);
            }
            // Success response.
            responseCode.setCode(Constants.CODE_SUCCESS);
            response.setResponseCode(responseCode);
            response.setResult(result);
        } catch (HibernateException e) {
            throw new EngimaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e);
        } catch (Exception e) {
            throw new EngimaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e, e);
        }
        return response;
    }

    public EnigmaResponse updateDeviceInfoStatus(Request requestInfo) throws EngimaException {

        response = new EnigmaResponse();
        responseCode = new ResponseCode();
        result = new ResponseResult();
        try {
            String deviceId = null;
            String deviceStatus = null;
            try {
                deviceId = requestInfo.getParameters().getDeviceId().trim();
                deviceStatus = requestInfo.getParameters().getDeviceStatus().trim();
            } catch (Exception e) {
                LOGGER.error(e);
                return CommonUtils.badRequest(response, responseCode);
            }

            // Checking whether request contains all require fields or not.
            if (null == deviceId || null == deviceStatus) {
                return CommonUtils.badRequest(response, responseCode);
            }
            DeviceInfo deviceInfo = deviceInfoDao.getDeviceInfo(deviceId);
            if (deviceInfo != null) {
                deviceInfo.setDeviceAvailability(deviceStatus);
                deviceInfoDao.updateDeviceInfo(deviceInfo);
                responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_UPDATED);
                // Success response.
                responseCode.setCode(Constants.CODE_SUCCESS);
                response.setResponseCode(responseCode);
                response.setResult(result);
                return response;
            }
            // Success response.
            responseCode.setMessage(Constants.MESSAGE_NOT_FOUND_DEVICE);
            responseCode.setCode(Constants.CODE_NOT_FOUND);
            response.setResponseCode(responseCode);
            response.setResult(result);
        } catch (HibernateException e) {
            throw new EngimaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e);
        } catch (Exception e) {
            throw new EngimaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e, e);
        }
        return response;

    }

    public EnigmaResponse deleteDeviceInfo(String deviceId) throws EngimaException {
        response = new EnigmaResponse();
        responseCode = new ResponseCode();
        result = new ResponseResult();
        try {
            // Checking whether request contains all require fields or not.
            if (null == deviceId) {
                return CommonUtils.badRequest(response, responseCode);
            }
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId(deviceId);
            deviceInfoDao.deleteDeviceInfo(deviceInfo);
            responseCode.setMessage(Constants.MESSAGE_SUCCESSFULLY_DELETED);

            // Success response.
            responseCode.setCode(Constants.CODE_SUCCESS);
            response.setResponseCode(responseCode);
            response.setResult(result);
        } catch (HibernateException e) {
            throw new EngimaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e);
        } catch (Exception e) {
            throw new EngimaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e, e);
        }
        return response;
    }

    public DeviceInfoDto getDeviceInfo(String deviceId) {
        DeviceInfoDto deviceInfoDto = new DeviceInfoDto();
        DeviceInfo deviceInfo = deviceInfoDao.getDeviceInfo(deviceId);
        return deviceInfoDto.getDeviceInfoDto(deviceInfo);
    }

    @Override
    public EnigmaResponse approveDevice(Request requestInfo) throws EngimaException {
        String deviceId = null;
        boolean isAdminApproved = false;
        response = new EnigmaResponse();
        responseCode = new ResponseCode();
        try {
            try {
                deviceId = requestInfo.getParameters().getDeviceId();
                isAdminApproved = requestInfo.getParameters().getIsAdminApproved();
            } catch (Exception e) {
                LOGGER.error(e);
                return CommonUtils.badRequest(response, responseCode);
            }
            // Checking whether request contains require field deviceId or not.
            if (null == deviceId) {
                return CommonUtils.badRequest(response, responseCode);
            }

            approveDeviceOperation(deviceId, isAdminApproved, responseCode);

            response.setResponseCode(responseCode);
            response.setResult(null);
        } catch (HibernateException e) {
            throw new EngimaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e);
        } catch (Exception e) {
            throw new EngimaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e, e);
        }
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
        if (null != deviceIssueInfo) {
            deviceIssueInfo.setSubmitTime(null);
            deviceIssueInfo.setSubmitBy(null);
            deviceIssueInfoDao.updateDeviceIssueInfo(deviceIssueInfo);
        }
    }

    @Override
    public List<TopDeviceDto> getTopDevices() {
        Date lastMonthDate = CommonUtils.getLastMonthAndDate();
        List<TopDeviceDto> topDeviceDtoList = deviceIssueInfoDao.getTopDevices(lastMonthDate);
        Collections.sort(topDeviceDtoList);
        return topDeviceDtoList;
    }

    @Override
    public EnigmaResponse searchDevice(String searchQuery) throws EngimaException {
        response = new EnigmaResponse();
        responseCode = new ResponseCode();
        result = new ResponseResult();
        try {
            if (null == searchQuery || searchQuery.trim().isEmpty()) {
                return CommonUtils.badRequest(response, responseCode);
            }

            List<DeviceInfo> deviceInfoList = deviceInfoDao.getDeviceFields(searchQuery);

            result.setDeviceList(deviceInfoList);
            responseCode.setCode(Constants.CODE_SUCCESS);
            response.setResponseCode(responseCode);
            response.setResult(result);
        } catch (HibernateException e) {
            throw new EngimaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e);
        } catch (Exception e) {
            throw new EngimaException("Excepton in " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "()  : " + e, e);
        }
        return response;
    }

    private boolean isDeviceExists(String deviceId) {
        if (null != deviceInfoDao.getDeviceInfo(deviceId)) {
            return true;
        }
        return false;
    }

    @Override
    public List<DeviceReportDto> getDeviceReport(String deviceId, Date startDate, Date endDate) {
        Date sDate = startDate;
        Date eDate = endDate;
        if (null == startDate) {
            sDate = CommonUtils.getDayBeginTime(new Date());
            eDate = CommonUtils.getDayEndTime(new Date());

        }
        return deviceIssueInfoDao.getDeviceReport(deviceId, sDate, eDate);
    }

}
