package com.ee.enigma.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.Constants;
import com.ee.enigma.dto.DeviceIssueTrendLineDto;
import com.ee.enigma.dto.DeviceStatusCountsInfo;
import com.ee.enigma.dto.IssueTrendLineData;
import com.ee.enigma.dto.ReportInfo;
import com.ee.enigma.dto.ReportResultInfo;
import com.ee.enigma.model.DeviceInfo;
import com.ee.enigma.model.DeviceIssueInfo;
import com.ee.enigma.model.UserActivity;

public class DeviceIssueHelper
{
  public DeviceIssueTrendLineDto buildDeviceIssueTimeLineTrendReport(List<DeviceIssueInfo> deviceIssueInfos)
  {
    DeviceIssueInfo deviceIssueInfo = null;
    DeviceIssueTrendLineDto deviceIssueTrendLineDto = new DeviceIssueTrendLineDto();
    List<String> deviceIssueDateList = new ArrayList<String>();
    String date = "";
    if (deviceIssueInfos != null && deviceIssueInfos.size() > 0)
    {
      for (int i = 0; i < deviceIssueInfos.size(); i++)
      {
        deviceIssueInfo = deviceIssueInfos.get(i);
        date = CommonUtils.getDateFromTemeStamp(deviceIssueInfo.getIssueTime());
        if (date != null && !deviceIssueDateList.contains(date))
        {
          deviceIssueDateList.add(date);
        }
      }
    }
    IssueTrendLineData issueTrendLineData = null;
    List<IssueTrendLineData> issueTrendLineList = new ArrayList<IssueTrendLineData>();
    if (deviceIssueInfos != null && deviceIssueInfos.size() > 0)
    {
      for (String issueDate : deviceIssueDateList)
      {
        int issuedByAdmin = 0;
        int issuedBySystem = 0;
        issueTrendLineData = new IssueTrendLineData();
        for (int i = 0; i < deviceIssueInfos.size(); i++)
        {
          deviceIssueInfo = deviceIssueInfos.get(i);
          if (deviceIssueInfo.getIssueByAdmin() != null
            && issueDate.equals(CommonUtils.getDateFromTemeStamp(deviceIssueInfo.getIssueTime())))
          {
            if (deviceIssueInfo.getIssueByAdmin())
            {
              issuedByAdmin++;
            }
            if (!deviceIssueInfo.getIssueByAdmin())
            {
              issuedBySystem++;
            }
          }
        }
        issueTrendLineData.setDate(issueDate);
        issueTrendLineData.setByAdmin(issuedByAdmin);
        issueTrendLineData.setBySystem(issuedBySystem);
        issueTrendLineList.add(issueTrendLineData);
      }
    }
    deviceIssueTrendLineDto.setIssueTrendLineData(issueTrendLineList);
    return deviceIssueTrendLineDto;
  }  
  

  public DeviceIssueTrendLineDto buildDeviceSubmitTimeLineTrendReport(List<DeviceIssueInfo> deviceIssueInfos)
  {
    DeviceIssueInfo deviceIssueInfo = null;
    DeviceIssueTrendLineDto deviceIssueTrendLineDto = new DeviceIssueTrendLineDto();
    List<String> deviceIssueDateList = new ArrayList<String>();
    String date = "";
    if (deviceIssueInfos != null && deviceIssueInfos.size() > 0)
    {
      for (int i = 0; i < deviceIssueInfos.size(); i++)
      {
        deviceIssueInfo = deviceIssueInfos.get(i);
        date = CommonUtils.getDateFromTemeStamp(deviceIssueInfo.getSubmitTime());
        if(date==null)
        {
          date="Not Submitted";
        }
        if (!deviceIssueDateList.contains(date))
        {
          deviceIssueDateList.add(date);
        }
      }
    }
    IssueTrendLineData issueTrendLineData = null;
    List<IssueTrendLineData> issueTrendLineList = new ArrayList<IssueTrendLineData>();
    if (deviceIssueInfos != null && deviceIssueInfos.size() > 0)
    {
      for (String issueDate : deviceIssueDateList)
      {
        int submitByAdmin = 0;
        int submitBySystem = 0;
        int submitByUser = 0;
        issueTrendLineData = new IssueTrendLineData();
        for (int i = 0; i < deviceIssueInfos.size(); i++)
        {
          deviceIssueInfo = deviceIssueInfos.get(i);
          if (deviceIssueInfo.getSubmitBy() != null
            && issueDate.equals(CommonUtils.getDateFromTemeStamp(deviceIssueInfo.getSubmitTime())))
          {
            if (Constants.SUBMITTED_BY_ADMIN.equals(deviceIssueInfo.getSubmitBy()))
            {
              submitByAdmin++;
            }
            if (Constants.SUBMITTED_BY_USER.equals(deviceIssueInfo.getSubmitBy()))
            {
              submitByUser++;
            }
            if (Constants.SUBMITTED_BY_SYSTEM.equals(deviceIssueInfo.getSubmitBy()))
            {
              submitBySystem++;
            }
          }
        }
        issueTrendLineData.setDate(issueDate);
        issueTrendLineData.setByAdmin(submitByAdmin);
        issueTrendLineData.setBySystem(submitBySystem);
        issueTrendLineData.setByUser(submitByUser);
        issueTrendLineList.add(issueTrendLineData);
      }
    }
    deviceIssueTrendLineDto.setIssueTrendLineData(issueTrendLineList);
    return deviceIssueTrendLineDto;
  }  
  
 /* public List<com.ee.enigma.dto.ReportInfo> buildDeviceIssueTrendList(List<DeviceIssueInfo> deviceIssueInfos)
  {
    DeviceIssueInfo deviceIssueInfo = null;
    List<ReportInfo> reportInfoList = new ArrayList<ReportInfo>();
    ReportInfo reportInfo = null;
    if (deviceIssueInfos != null && deviceIssueInfos.size() > 0)
    {
      for (int i = 0; i < deviceIssueInfos.size(); i++)
      {
         deviceIssueInfo = deviceIssueInfos.get(i);
          reportInfo = new ReportInfo();
          reportInfo.setDeviceId(deviceIssueInfo.getDeviceInfo().getDeviceId());
          reportInfo.setDeviceName(deviceIssueInfo.getDeviceInfo().getDeviceName());
          reportInfo.setDeviceAvailability(deviceIssueInfo.getDeviceInfo().getDeviceAvailability());
          reportInfo.setManufacturer(deviceIssueInfo.getDeviceInfo().getManufacturer());
          reportInfo.setOS(deviceIssueInfo.getDeviceInfo().getOS());
          reportInfo.setOSVersion(deviceIssueInfo.getDeviceInfo().getOSVersion());
          reportInfo.setIssueTime(deviceIssueInfo.getIssueTime());
          if(reportInfo.getIssueByAdmin()!=null && reportInfo.getIssueByAdmin())
          {
            reportInfo.setIssuedBy(Constants.DEVICE_STATUS_ISSUED);
          }
          else
          {
            reportInfo.setIssuedBy(Constants.DEVICE_STATUS_ISSUED);
          }
          reportInfo.setSubmitTime(deviceIssueInfo.getSubmitTime());
          reportInfoList.add(reportInfo);
      }
    }
    return reportInfoList;
  }  */
  
 /* public List<ReportInfo> buildDeviceSubmitTrendList(List<DeviceIssueInfo> deviceIssueInfos)
  {
    DeviceIssueInfo deviceIssueInfo = null;
    List<ReportInfo> reportInfoList = new ArrayList<ReportInfo>();
    ReportInfo reportInfo = null;
    if (deviceIssueInfos != null && deviceIssueInfos.size() > 0)
    {
      for (int i = 0; i < deviceIssueInfos.size(); i++)
      {
         deviceIssueInfo = deviceIssueInfos.get(i);
          reportInfo = new ReportInfo();
          reportInfo.setDeviceId(deviceIssueInfo.getDeviceInfo().getDeviceId());
          reportInfo.setDeviceName(deviceIssueInfo.getDeviceInfo().getDeviceName());
          reportInfo.setDeviceAvailability(deviceIssueInfo.getDeviceInfo().getDeviceAvailability());
          reportInfo.setManufacturer(deviceIssueInfo.getDeviceInfo().getManufacturer());
          reportInfo.setOS(deviceIssueInfo.getDeviceInfo().getOS());
          reportInfo.setOSVersion(deviceIssueInfo.getDeviceInfo().getOSVersion());
          reportInfo.setIssueTime(deviceIssueInfo.getIssueTime());
          if(reportInfo.getSubmitByAdmin()!=null && reportInfo.getSubmitByAdmin())
          {
            reportInfo.setIssuedBy(Constants.SUBMITTED_BY_ADMIN);
          }
          else
          {
            reportInfo.setIssuedBy(Constants.SUBMITTED_BY_SYSTEM);
          }
          reportInfo.setSubmitTime(deviceIssueInfo.getSubmitTime());
          reportInfoList.add(reportInfo);
      }
    }
    return reportInfoList;
  }  */
  
 
 public DeviceStatusCountsInfo buildDeviceReportAvailability(List<DeviceInfo> deviceInfoList)
  {
    DeviceInfo deviceInfo = null;
     int totalDevicesCount=0;
    int availableDevices=0;
    int issuedDevices=0;
    DeviceStatusCountsInfo info = null;
    for (int i = 0; i < deviceInfoList.size(); i++)
    {
        deviceInfo = deviceInfoList.get(i);
        totalDevicesCount++;
        if(Constants.DEVICE_STATUS_AVAILABLE.equals(deviceInfo.getDeviceAvailability()))
        {
          availableDevices++;
        }
        if(Constants.DEVICE_STATUS_ISSUED.equals(deviceInfo.getDeviceAvailability()))
        {
          issuedDevices++;
        }
     }
    info=new DeviceStatusCountsInfo();
    info.setTotalDevices(totalDevicesCount);
    info.setAvailableDevices(availableDevices);
    info.setIssuedDevices(issuedDevices);
    return info;
  }  
  
 /*public List<ReportInfo> buildDeviceIssueReportListByStatus(List<DeviceInfo> deviceInfoList)
 {
   DeviceInfo deviceInfo = null;
   List<ReportInfo> reportInfoList = new ArrayList<ReportInfo>();
   ReportInfo reportInfo = null;
   for (int i = 0; i < deviceInfoList.size(); i++)
   {
     deviceInfo = deviceInfoList.get(i);
       reportInfo = new ReportInfo();
       reportInfo.setDeviceId(deviceInfo.getDeviceId());
       reportInfo.setDeviceName(deviceInfo.getDeviceName());
       reportInfo.setDeviceAvailability(deviceInfo.getDeviceAvailability());
        reportInfo.setManufacturer(deviceInfo.getManufacturer());
       reportInfo.setOS(deviceInfo.getOS());
       reportInfo.setOSVersion(deviceInfo.getOSVersion());
       reportInfo.setYearOfManufacturing(deviceInfo.getYearOfManufacturing());
       reportInfoList.add(reportInfo);
   }
   return reportInfoList;
 }  */
 
  public JSONObject buildDeviceTimeLineReport(List<DeviceIssueInfo> deviceIssueInfos)
  {
    JSONObject deviceJson = new JSONObject();
    ReportResultInfo reportResultInfo = null;
    List<ReportResultInfo> reportResultInfoList = null;
    List<ReportInfo> reportInfoList = null;
    ReportInfo reportInfo = null;
    JSONArray userActityJsonArray = null;
    JSONArray issueIdJsonArray = null;
    String deviceId = "";
    JSONObject issueIdJson = null;
    JSONObject userActivityJson = null;
    DeviceIssueInfo deviceIssueInfo = null;
    if (deviceIssueInfos != null && deviceIssueInfos.size() > 0)
    {
      UserActivity userActivity = null;
      issueIdJsonArray = new JSONArray();
      for (int i = 0; i < deviceIssueInfos.size(); i++)
      {
        deviceIssueInfo = deviceIssueInfos.get(i);
        deviceId = deviceIssueInfo.getDeviceId();
        reportResultInfoList = new ArrayList<ReportResultInfo>();
        issueIdJson = new JSONObject();
        issueIdJson.put("issueId", deviceIssueInfo.getIssueId());
        issueIdJson.put("issueTime", CommonUtils.getTimeStampFormatedString(deviceIssueInfo.getIssueTime()));
        issueIdJson.put("submitTime",CommonUtils.getTimeStampFormatedString(deviceIssueInfo.getSubmitTime()));
        userActityJsonArray = null;
        if (deviceIssueInfo.getUserActivity() != null)
        {
          Iterator<UserActivity> iterator = deviceIssueInfo.getUserActivity().iterator();
          userActityJsonArray = new JSONArray();
          reportInfoList = new ArrayList<ReportInfo>();
          while (iterator.hasNext())
          {
            userActivity = iterator.next();
            reportInfo = new ReportInfo();
            reportInfo.setLoginTime(CommonUtils.getTimeStampFormatedString(userActivity.getLoginTime()));
            reportInfo.setLogoutTime(CommonUtils.getTimeStampFormatedString(userActivity.getLogoutTime()));
            reportInfo.setInDate(userActivity.getLoginTime());
            reportInfo.setOutDate(userActivity.getLogoutTime());
            reportInfo.setUserName(deviceIssueInfo.getUserInfo().getUserName());
            reportInfoList.add(reportInfo);
          }
          if (reportInfoList != null && reportInfoList.size() > 0)
          {
            reportResultInfo = null;
            ReportInfo reportInfoTemp=null;
            for (int j = 0; j < reportInfoList.size(); j++)
            {
              reportInfoTemp=null;
              reportResultInfo=null;
              reportInfoTemp = reportInfoList.get(j);
              if (j == 0)
              {
                reportResultInfo = new ReportResultInfo();
                reportResultInfo.setReportInfoNext(reportInfoTemp);
                reportInfo = new ReportInfo();
                reportInfo.setLoginTime(CommonUtils.getTimeStampFormatedString(deviceIssueInfo.getIssueTime()));
                reportInfo.setLogoutTime(CommonUtils.getTimeStampFormatedString(userActivity.getLogoutTime()));
                reportInfo.setUserName(deviceIssueInfo.getUserInfo().getUserName());
                reportInfo.setFromTable("DIINFO");
                reportResultInfo.setReportInfo(reportInfo);
              }
              else if (j == (reportInfoList.size() - 1))
              {
                reportResultInfo = new ReportResultInfo();
                reportResultInfo.setReportInfo(reportInfoList.get(j));
                reportInfo = new ReportInfo();
                reportInfo.setLoginTime(null);
                reportInfo.setLogoutTime(CommonUtils.getTimeStampFormatedString(deviceIssueInfo.getSubmitTime()));
                reportInfo.setUserName(deviceIssueInfo.getUserInfo().getUserName());
                reportInfo.setFromTable("DIINFO");
                reportResultInfo.setReportInfoNext(reportInfo);
              }
              else
              {
                reportResultInfo = new ReportResultInfo();
                reportResultInfo.setReportInfo(reportInfoList.get(j - 1));
                reportResultInfo.setReportInfoNext(reportInfoList.get(j));
              }
              reportResultInfoList.add(reportResultInfo);
            }
            reportResultInfo=null; 
          }
          for(int k=0;k<reportResultInfoList.size();k++)
          {
            reportResultInfo=reportResultInfoList.get(k);
            userActivityJson=null;
            if(k==0)
            {
              userActivityJson=new JSONObject();
              userActivityJson.put("userName", deviceIssueInfo.getUserInfo().getUserName());
              userActivityJson.put("inTime", reportResultInfo.getReportInfo().getLoginTime());
              userActivityJson.put("outTime","NA");
              userActivityJson.put("useStatus", "Issued");
              userActivityJson.put("duration", "NA");
              userActityJsonArray.add(userActivityJson);
              
              //Next
              if(CommonUtils.getTimeDiffernce(reportResultInfo.getReportInfo().getInDate(), reportResultInfo.getReportInfoNext().getInDate())>0)
              {
              userActivityJson=new JSONObject();
              userActivityJson.put("userName", deviceIssueInfo.getUserInfo().getUserName());
              userActivityJson.put("inTime", reportResultInfo.getReportInfo().getLoginTime());
              userActivityJson.put("outTime", reportResultInfo.getReportInfoNext().getLoginTime());
              userActivityJson.put("duration", CommonUtils.displayTimeString(CommonUtils.getTimeDiffernce(reportResultInfo.getReportInfo().getInDate(), reportResultInfo.getReportInfoNext().getInDate())));
              userActivityJson.put("useStatus", "Idle");
              userActityJsonArray.add(userActivityJson);
              }
              
            }
            else if(k==(reportResultInfoList.size()-1))
            {
              
              userActivityJson=new JSONObject();
              userActivityJson.put("userName", deviceIssueInfo.getUserInfo().getUserName());
              userActivityJson.put("inTime", reportResultInfo.getReportInfo().getLoginTime());
              userActivityJson.put("outTime", reportResultInfo.getReportInfo().getLogoutTime());
              userActivityJson.put("duration", CommonUtils.displayTimeString(CommonUtils.getTimeDiffernce(reportResultInfo.getReportInfo().getInDate(), reportResultInfo.getReportInfo().getOutDate())));
              userActivityJson.put("useStatus", "Active");
              userActityJsonArray.add(userActivityJson);
              
              //Next
              if(CommonUtils.getTimeDiffernce(reportResultInfo.getReportInfo().getOutDate(), reportResultInfo.getReportInfoNext().getInDate())>0)
              {
              userActivityJson=new JSONObject();
              userActivityJson.put("userName", deviceIssueInfo.getUserInfo().getUserName());
              userActivityJson.put("inTime", reportResultInfo.getReportInfo().getLogoutTime());
              userActivityJson.put("outTime", reportResultInfo.getReportInfoNext().getLoginTime());
              userActivityJson.put("duration", CommonUtils.getTimeDiffernce(reportResultInfo.getReportInfo().getOutDate(), reportResultInfo.getReportInfoNext().getInDate()));
              userActivityJson.put("useStatus", "Idle");
              userActityJsonArray.add(userActivityJson);
              }
              //Last
              userActivityJson=new JSONObject();
              userActivityJson.put("userName", deviceIssueInfo.getUserInfo().getUserName());
              userActivityJson.put("inTime", "NA");
              userActivityJson.put("outTime", reportResultInfo.getReportInfoNext().getLogoutTime());
              userActivityJson.put("duration", "NA");
              userActivityJson.put("useStatus", "Submitted");
              userActityJsonArray.add(userActivityJson);
            }
            else
            {
              userActivityJson=new JSONObject();
              userActivityJson.put("userName", deviceIssueInfo.getUserInfo().getUserName());
              userActivityJson.put("inTime", reportResultInfo.getReportInfo().getLoginTime());
              userActivityJson.put("outTime", reportResultInfo.getReportInfo().getLogoutTime());
              userActivityJson.put("duration", CommonUtils.displayTimeString(CommonUtils.getTimeDiffernce(reportResultInfo.getReportInfo().getInDate(), reportResultInfo.getReportInfo().getOutDate())));
              userActivityJson.put("useStatus", "Active");
              userActityJsonArray.add(userActivityJson);
              
              //Next
              if(CommonUtils.getTimeDiffernce(reportResultInfo.getReportInfo().getOutDate(), reportResultInfo.getReportInfoNext().getInDate())>0)
              {
              userActivityJson=new JSONObject();
              userActivityJson.put("userName", deviceIssueInfo.getUserInfo().getUserName());
              userActivityJson.put("inTime", reportResultInfo.getReportInfo().getLogoutTime());
              userActivityJson.put("outTime", reportResultInfo.getReportInfoNext().getLoginTime());
              userActivityJson.put("duration",CommonUtils.displayTimeString(CommonUtils.getTimeDiffernce(reportResultInfo.getReportInfo().getOutDate(), reportResultInfo.getReportInfoNext().getInDate())));
              userActivityJson.put("useStatus", "Idle");
              userActityJsonArray.add(userActivityJson);
              }
            }
          }
          issueIdJson.put("userActivities", userActityJsonArray);
        }
        
        issueIdJsonArray.add(issueIdJson);
      }
      deviceJson.put("deviceId", deviceId);
      deviceJson.put("deviceIssueDetails", issueIdJsonArray);
      System.out.println("jsonObject: " + deviceJson);
    }
    return deviceJson;
  }
  
  
  
  public List<ReportInfo> buildDevicesListByStatus(List<DeviceIssueInfo> deviceIssueInfos,List<DeviceInfo> deviceInfoList)
  {
    DeviceIssueInfo deviceIssueInfo = null;
    DeviceInfo deviceInfo = null;
    List<String> deviceInfoListByDeviceId = new ArrayList<String>();
    List<ReportInfo> reportInfoList = new ArrayList<ReportInfo>();
    ReportInfo reportInfo = null;
    if (deviceIssueInfos != null && deviceIssueInfos.size() > 0)
    {
      for (int i = 0; i < deviceIssueInfos.size(); i++)
      {
        deviceIssueInfo = deviceIssueInfos.get(i);
        if (!deviceInfoListByDeviceId.contains(deviceIssueInfo.getDeviceId()))
        {
          deviceInfoListByDeviceId.add(deviceIssueInfo.getDeviceId());
          reportInfo = new ReportInfo();
          reportInfo.setDeviceId(deviceIssueInfo.getDeviceInfo().getDeviceId());
          reportInfo.setDeviceName(deviceIssueInfo.getDeviceInfo().getDeviceName());
          reportInfo.setDeviceAvailability(deviceIssueInfo.getDeviceInfo().getDeviceAvailability());
          reportInfo.setManufacturer(deviceIssueInfo.getDeviceInfo().getManufacturer());
          reportInfo.setOS(deviceIssueInfo.getDeviceInfo().getOS());
          reportInfo.setOSVersion(deviceIssueInfo.getDeviceInfo().getOSVersion());
          reportInfo.setUserId(deviceIssueInfo.getUserId());
          reportInfo.setUserName(deviceIssueInfo.getUserInfo().getUserName());
          reportInfoList.add(reportInfo);
        }
      }
    }
    for (int i = 0; i < deviceInfoList.size(); i++)
    {
      deviceInfo = deviceInfoList.get(i);
      if (!deviceInfoListByDeviceId.contains(deviceInfo.getDeviceId()))
      {
        reportInfo = new ReportInfo();
        reportInfo.setDeviceId(deviceInfo.getDeviceId());
        reportInfo.setDeviceName(deviceInfo.getDeviceName());
        reportInfo.setDeviceAvailability(deviceInfo.getDeviceAvailability());
         reportInfo.setManufacturer(deviceInfo.getManufacturer());
        reportInfo.setOS(deviceInfo.getOS());
        reportInfo.setOSVersion(deviceInfo.getOSVersion());
        reportInfoList.add(reportInfo);
      }
    }
    return reportInfoList;
  }  
  
}


