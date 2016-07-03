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
import com.ee.enigma.service.DeviceService;

@Controller(value = "devicInfoREST")
@Consumes("application/json")
@Produces("application/json")
public class DeviceREST
{
  private Logger logger = Logger.getLogger(DeviceREST.class);

  private DeviceService deviceService;

  @Autowired(required = true)
  @Qualifier(value = "deviceService")
  public void setDeviceService(DeviceService deviceService)
  {
    this.deviceService = deviceService;
  }

  @POST
  @Path("/saveDeviceInfo")
  public Response saveUserInfo(Request requestInfo)
  {
    EnigmaResponse userResponse = deviceService.saveDeviceInfo(requestInfo);
    return Response.ok(userResponse, MediaType.APPLICATION_JSON)
      .status(userResponse.getResponseCode().getCode()).build();
  }

  @POST
  @Path("/deleteDeviceInfo")
  public Response deleteUserInfo(Request requestInfo)
  {
    EnigmaResponse userResponse = deviceService.deleteDeviceInfo(requestInfo);
    return Response.ok(userResponse, MediaType.APPLICATION_JSON)
      .status(userResponse.getResponseCode().getCode()).build();
  }
  
  @POST
  @Path("/getDeviceInfo")
  public Response getUserInfo(Request requestInfo)
  {
    EnigmaResponse userResponse = deviceService.getDeviceInfo(requestInfo);
    Response response= Response.ok(userResponse, MediaType.APPLICATION_JSON)
      .entity(userResponse.getResponseCode().getResultObject()).build();
    return response;
  }

}
