package com.ee.enigma.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.Constants;
import com.ee.enigma.common.EngimaException;
import com.ee.enigma.dto.DeviceIssueStatusDto;
import com.ee.enigma.dto.DeviceIssueTrendLineDto;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.service.DeviceIssueInfoService;
import com.ee.enigma.service.DeviceService;

@Controller(value = "deviceIssueInfoREST")
@Consumes("application/json")
@Produces("application/json")
public class DeviceIssueInfoREST {
	 private Logger logger = Logger.getLogger(DeviceIssueInfoREST.class);

	DeviceIssueInfoService deviceIssueInfoService;
	private DeviceService deviceService;

	@Autowired(required = true)
	@Qualifier(value = "deviceIssueInfoService")
	public void setDeviceIssueInfoService(DeviceIssueInfoService deviceIssueInfoService) {
		this.deviceIssueInfoService = deviceIssueInfoService;
	}

	@Autowired(required = true)
	@Qualifier(value = "deviceService")
	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	@POST
	@Path("/")
	public Response deviceIssueInfoService(Request deviceIssueInfo) {
    String errorMessage;
    EnigmaResponse deviceIssueResponse;
    try
    {
      deviceIssueResponse = deviceIssueInfoService.deviceIssueInfoService(deviceIssueInfo);
      return Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON).status(deviceIssueResponse.getResponseCode().getCode()).build();
    }
    catch (EngimaException e)
    {
      errorMessage = e.getMessage();
      logger.error(e);
      logger.error(e.getMessage());
    }
    deviceIssueResponse = CommonUtils.internalSeverError(errorMessage);
    return Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON).status(deviceIssueResponse.getResponseCode().getCode()).build();

	}
  //TODO
	@GET
	@Path("/deviceTimeLineReport")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject deviceIssueReportService(@QueryParam("beginDate") String beginDate,@QueryParam("endDate") String endDate,
	  @QueryParam("deviceId") String deviceId) {
	  JSONObject deviceTimeLineReport = deviceIssueInfoService.getDeviceTimeLineReport(beginDate,endDate,deviceId,Constants.REPORT_DEVIVE_TIMELINE);
		return deviceTimeLineReport;
	}

//TODO
  @GET
  @Path("/userTimeLineReport")
  @Produces(MediaType.APPLICATION_JSON)
  public JSONObject userIssueReportService(@QueryParam("beginDate") String beginDate,@QueryParam("endDate") String endDate,
    @QueryParam("userId") String userId) {
    JSONObject deviceTimeLineReport = deviceIssueInfoService.getDeviceTimeLineReport(beginDate,endDate,userId,Constants.REPORT_USER_TIMELINE);
    return deviceTimeLineReport;
  }
  
	@POST
	@Path("/submitDevice")
	public EnigmaResponse submitDevice(Request deviceIssueInfo) {
    String errorMessage;
    EnigmaResponse deviceIssueResponse;
    try
    {
      deviceIssueResponse = deviceIssueInfoService.submitDevice(deviceIssueInfo);
      return deviceIssueResponse;
    }
    catch (EngimaException e)
    {
      errorMessage = e.getMessage();
      logger.error(e);
      logger.error(e.getMessage());
    }
    deviceIssueResponse = CommonUtils.internalSeverError(errorMessage);
    return deviceIssueResponse;
	}

	@GET
	@Path("/deviceReportByAvailability")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeviceReportAvailability() {
    String errorMessage;
    EnigmaResponse deviceIssueResponse;
    try
    {
      deviceIssueResponse = deviceIssueInfoService.getDeviceReportAvailability();
      Response response = Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON)
        .entity(deviceIssueResponse.getResponseCode().getResultObject()).build();
      return response;
    }
    catch (EngimaException e)
    {
      errorMessage = e.getMessage();
      logger.error(e);
      logger.error(e.getMessage());
    }
    deviceIssueResponse = CommonUtils.internalSeverError(errorMessage);
    return Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON).status(deviceIssueResponse.getResponseCode().getCode()).build();
	}

	
	@PUT
	@Path("/approveDevice")
  public Response approveDevice(Request requestInfo)
  {
    String errorMessage;
    EnigmaResponse userResponse;
    try
    {
      userResponse = deviceService.approveDevice(requestInfo);
      return Response.ok(userResponse, MediaType.APPLICATION_JSON).status(userResponse.getResponseCode().getCode()).build();
    }
    catch (EngimaException e)
    {
      errorMessage = e.getMessage();
      logger.error(e);
      logger.error(e.getMessage());
    }
    userResponse = CommonUtils.internalSeverError(errorMessage);
    return Response.ok(userResponse, MediaType.APPLICATION_JSON).status(userResponse.getResponseCode().getCode()).build();
  }

  
	@GET
  @Path("/deviceIssueTimeLineTrendReport")
	@Produces(MediaType.APPLICATION_JSON)
  public Response getDeviceIssueTimeLineTrendReport(@QueryParam("beginDate") String beginDate,@QueryParam("endDate") String endDate,
    @QueryParam("reportType") String reportType) {
	  Response response=null;
    DeviceIssueTrendLineDto deviceIssueTimeLineTrendReport = null;
    try
    {
      deviceIssueTimeLineTrendReport = deviceIssueInfoService.getDeviceIssueTimeLineTrendReport(beginDate, endDate, reportType);

      if (deviceIssueTimeLineTrendReport == null)
      {
        response = Response.ok(deviceIssueTimeLineTrendReport, MediaType.APPLICATION_JSON).entity("No data").build();
      }
      else
      {
        response = Response.ok(deviceIssueTimeLineTrendReport, MediaType.APPLICATION_JSON).entity(deviceIssueTimeLineTrendReport).build();
      }
    }
    catch (EngimaException e)
    {
      response = Response.ok(deviceIssueTimeLineTrendReport, MediaType.APPLICATION_JSON).entity(e.getMessage()).build();
      logger.error(e.getMessage());
      logger.error(e);
    }
    return response;
  }
	
  @GET
  @Path("/deviceIssueStatusForDevice")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDeviceIssueStatusForDevice(@QueryParam("deviceId") String deviceId)
  {
    DeviceIssueStatusDto deviceIssueStatusDto = deviceIssueInfoService
      .getDeviceIssueStatusForDevice(deviceId);
    Response response;
    if (deviceIssueStatusDto != null)
    {
      response = Response.ok(deviceIssueStatusDto, MediaType.APPLICATION_JSON)
        .entity(deviceIssueStatusDto).build();
    }
    else
    {
      return Response.ok(new DeviceIssueStatusDto(), MediaType.APPLICATION_JSON).status(400)
        .build();
    }
    return response;
  }
  @GET
  @Path("/devicesIssueReportByStatus")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDevicesIssueReportByStatus()
  {
    String errorMessage;
    EnigmaResponse deviceIssueResponse;
    try
    {
      deviceIssueResponse = deviceIssueInfoService.getDevicesIssueReportByStatus();
      Response response = Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON)
        .entity(deviceIssueResponse.getResponseCode().getResultObject()).build();
      return response;
    }
    catch (EngimaException e)
    {
      errorMessage = e.getMessage();
      logger.error(e);
      logger.error(e.getMessage());
    }
    deviceIssueResponse = CommonUtils.internalSeverError(errorMessage);
    return Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON).status(deviceIssueResponse.getResponseCode().getCode()).build();
  }
  
  @GET
  @Path("/pendingDevicesReport")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPendingDevicesReport()
  {
    String errorMessage;
    EnigmaResponse deviceIssueResponse;
    try
    {
      deviceIssueResponse = deviceIssueInfoService.getPendingDevicesReport();
      Response response = Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON)
        .entity(deviceIssueResponse.getResponseCode().getResultObject()).build();
      return response;
    }
    catch (EngimaException e)
    {
      errorMessage = e.getMessage();
      logger.error(e);
      logger.error(e.getMessage());
    }
    deviceIssueResponse = CommonUtils.internalSeverError(errorMessage);
    return Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON).status(deviceIssueResponse.getResponseCode().getCode()).build();
  }
  
	
}
