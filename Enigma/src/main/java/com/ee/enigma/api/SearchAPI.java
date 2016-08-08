package com.ee.enigma.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.service.DeviceService;
import com.ee.enigma.service.UserService;

@Controller(value = "searchAPI")
@Consumes("application/json")
@Produces("application/json")
public class SearchAPI {
	
	private UserService userService;
	private DeviceService deviceService;
	 private Logger logger = Logger.getLogger(SearchAPI.class);
	 
	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired(required = true)
	@Qualifier(value = "deviceService")
	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	@GET
	@Path("/")
	public Response searchUser(@QueryParam("user") String userSearchQuery, @QueryParam("device") String deviceSearchQuery) {
		EnigmaResponse response = null;
		String errorMessage = "";
    try
    {
		if(null != userSearchQuery && !userSearchQuery.trim().isEmpty()){
			response = userService.searchUserResult(userSearchQuery.trim());
		}else if(null != deviceSearchQuery && !deviceSearchQuery.trim().isEmpty()){
			response = deviceService.searchDevice(deviceSearchQuery.trim());
		}else{
			return Response.ok(CommonUtils.badRequest(), MediaType.APPLICATION_JSON).status(CommonUtils.badRequest().getResponseCode().getCode())
					.build();
		}
		return Response.ok(response, MediaType.APPLICATION_JSON).status(response.getResponseCode().getCode())
				.build();
    }
    catch (EngimaException e)
    {
      errorMessage = e.getMessage();
      logger.error(e.getMessage());
    }
    response = CommonUtils.internalSeverError(errorMessage);
    return Response.ok(response, MediaType.APPLICATION_JSON).status(response.getResponseCode().getCode()).build();

	}

}
