package com.ee.enigma.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.service.DeviceIssueInfoService;


@Controller(value="deviceIssueInfoREST")
@Consumes("application/json")
@Produces("application/json")
public class DeviceIssueInfoREST {
	private Logger logger = Logger.getLogger(DeviceIssueInfoREST.class);

	DeviceIssueInfoService deviceIssueInfoService;
	@Autowired(required = true)
	@Qualifier(value = "deviceIssueInfoService")
	public void setDeviceIssueInfoService(DeviceIssueInfoService deviceIssueInfoService) {
		this.deviceIssueInfoService = deviceIssueInfoService;
	}
		
	@POST
	@Path("/deviceIssueInfo")
	public Response deviceIssueInfoService(Request deviceIssueInfo){
		EnigmaResponse deviceIssueResponse = deviceIssueInfoService.deviceIssueInfoService(deviceIssueInfo);
		Response response= Response.ok(deviceIssueResponse, MediaType.APPLICATION_JSON).entity(deviceIssueResponse.getResponseCode().getResultObject()).build();
		return response;
	}
	
	@POST
  @Path("/deviceIssueReport")
  public EnigmaResponse deviceIssueReportService(Request deviceIssueInfo){
    EnigmaResponse deviceIssueResponse = deviceIssueInfoService.getReportForDevice(deviceIssueInfo);
    return deviceIssueResponse;
  }
	
	@POST
  @Path("/submitDevice")
  public EnigmaResponse submitDevice(Request deviceIssueInfo){
    EnigmaResponse deviceIssueResponse = deviceIssueInfoService.submitDevice(deviceIssueInfo);
    return deviceIssueResponse;
  }
  
	
	
}
