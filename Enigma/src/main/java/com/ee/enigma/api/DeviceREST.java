package com.ee.enigma.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.EngimaException;
import com.ee.enigma.dto.DeviceInfoDto;
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
  @Path("/")
  public Response saveDeviceInfo(Request requestInfo)
  {
    String errorMessage;
    EnigmaResponse userResponse;
    try
    {
    userResponse = deviceService.saveDeviceInfo(requestInfo);
    return Response.ok(userResponse, MediaType.APPLICATION_JSON)
      .status(userResponse.getResponseCode().getCode()).build();
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


  @DELETE
  @Path("/{id}")
  public Response deleteUserInfo(@PathParam("id") String deviceId)
  {
    String errorMessage;
    EnigmaResponse userResponse;
    try
    {
      userResponse = deviceService.deleteDeviceInfo(deviceId);
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
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public DeviceInfoDto getDeviceInfo(@PathParam("id") String deviceId)
  {
    DeviceInfoDto deviceInfoDto=deviceService.getDeviceInfo(deviceId);
    return deviceInfoDto;
  }
  

  @GET
  @Path("/getDevicesInfoByStatus")
  @Produces(MediaType.APPLICATION_JSON)
  public List<DeviceInfoDto> getDevicesInfoByStatus(@QueryParam("deviceId") String deviceId, @QueryParam("deviceStatus") String deviceStatus)
  {
    List<DeviceInfoDto> deviceInfoDtos=deviceService.getDevicesInfoByStatus(deviceId,deviceStatus);
    return deviceInfoDtos;
  }
  
  @PUT
  @Path("/{id}")
  public Response updateDeviceInfoStatus(@PathParam("id") String deviceId, Request requestInfo)
  {
    String errorMessage;
    EnigmaResponse userResponse;
    try
    {
      requestInfo.getParameters().setDeviceId(deviceId);
      userResponse = deviceService.updateDeviceInfoStatus(requestInfo);
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
  
}
