package com.ee.enigma.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.ee.enigma.common.Constants;
import com.ee.enigma.dto.DeviceIssueStatusDto;
import com.ee.enigma.dto.DeviceIssueTrendLineDto;
import com.ee.enigma.dto.IssueTrendLineData;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.service.DeviceIssueInfoService;
import com.ee.enigma.service.DeviceService;

@Controller(value = "deviceIssueInfoREST")
@Consumes("application/json")
@Produces("application/json")
public class DeviceIssueInfoREST {
	// private Logger logger = Logger.getLogger(DeviceIssueInfoREST.class);

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
		EnigmaResponse deviceIssueResponse = deviceIssueInfoService.deviceIssueInfoService(deviceIssueInfo);
	  return Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON)
      .status(deviceIssueResponse.getResponseCode().getCode()).build();
	
	}
  //TODO
	@GET
	@Path("/deviceTimeLineReport")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject deviceIssueReportService(@QueryParam("beginDate") String beginDate,@QueryParam("endDate") String endDate,
	  @QueryParam("deviceId") String deviceId) {
	  JSONObject deviceTimeLineReport = deviceIssueInfoService.getDeviceTimeLineReport(beginDate,endDate,deviceId);
		return deviceTimeLineReport;
	}

	@POST
	@Path("/submitDevice")
	public EnigmaResponse submitDevice(Request deviceIssueInfo) {
		EnigmaResponse deviceIssueResponse = deviceIssueInfoService.submitDevice(deviceIssueInfo);
		return deviceIssueResponse;
	}

	@GET
	@Path("/deviceReportByAvailability")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeviceReportAvailability() {
		EnigmaResponse deviceIssueResponse = deviceIssueInfoService.getDeviceReportAvailability();
		Response response = Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON)
				.entity(deviceIssueResponse.getResponseCode().getResultObject()).build();
		return response;
	}

	/*@GET
	@Path("/deviceReportByStatus")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeviceIssueReportByStatus() {
		EnigmaResponse deviceIssueResponse = deviceIssueInfoService.getDeviceIssueReportByStatus();
		Response response = Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON)
				.entity(deviceIssueResponse.getResponseCode().getResultObject()).build();
		return response;
	}*/

	@PUT
	@Path("/approveDevice")
	public Response approveDevice(Request requestInfo) {
		EnigmaResponse userResponse = deviceService.approveDevice(requestInfo);
		return Response.ok(userResponse, MediaType.APPLICATION_JSON).status(userResponse.getResponseCode().getCode())
				.build();
	}

  
	@GET
  @Path("/deviceIssueTimeLineTrendReport")
	@Produces(MediaType.APPLICATION_JSON)
  public Response getDeviceIssueTimeLineTrendReport(@QueryParam("beginDate") String beginDate,@QueryParam("endDate") String endDate,
    @QueryParam("reportType") String reportType) throws Exception{
    DeviceIssueTrendLineDto deviceIssueTimeLineTrendReport= deviceIssueInfoService.getDeviceIssueTimeLineTrendReport(beginDate,endDate,reportType);
    Response response=null;
    if(deviceIssueTimeLineTrendReport==null)
    {
      response=Response.ok(deviceIssueTimeLineTrendReport, MediaType.APPLICATION_JSON).entity("No data").build();
    }
    else
    {
      response= Response.ok(deviceIssueTimeLineTrendReport, MediaType.APPLICATION_JSON).entity(deviceIssueTimeLineTrendReport).build();
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
    Response response = null;
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
  public Response getDevicesIssueReportByStatus(){
    EnigmaResponse deviceIssueResponse = deviceIssueInfoService.getDevicesIssueReportByStatus();
    Response response= Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON).entity(deviceIssueResponse.getResponseCode().getResultObject()).build();
    return response;
  }
  
	
}
