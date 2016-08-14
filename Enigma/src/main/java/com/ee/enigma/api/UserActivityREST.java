package com.ee.enigma.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.EngimaException;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
//import com.ee.enigma.response.Response;
import com.ee.enigma.service.UserLoginLogoutService;


@Controller(value="userActivityREST")
@Consumes("application/json")
@Produces("application/json")
public class UserActivityREST {
	private Logger logger = Logger.getLogger(UserActivityREST.class);

	private UserLoginLogoutService userLoginLogoutService;
	@Autowired(required = true)
	@Qualifier(value = "userLoginLogoutService")
	public void setUserActivityService(UserLoginLogoutService userActivityService) {
		this.userLoginLogoutService = userActivityService;
	}
	
	@POST
	@Path("/login")
	public Response login(Request loginInfo){
	  EnigmaResponse loginResponse;
    String errorMessage;
    try
    {
		loginResponse = userLoginLogoutService.userLoginService(loginInfo);
		return Response.ok(loginResponse, MediaType.APPLICATION_JSON).status(loginResponse.getResponseCode().getCode()).build();
    }
    catch (EngimaException e)
    {
      errorMessage = e.getMessage();
      logger.error(e);
      logger.error(e.getMessage());
    }
    loginResponse = CommonUtils.internalSeverError(errorMessage);
    return Response.ok(loginResponse, MediaType.APPLICATION_JSON).status(loginResponse.getResponseCode().getCode()).build();
	}
	
	@POST
	@Path("/logout")
	public Response logout(Request logoutInfo){
	  EnigmaResponse logoutResponse;
    String errorMessage;
    try
    {
		 logoutResponse = userLoginLogoutService.userLogoutService(logoutInfo);
		return Response.ok(logoutResponse, MediaType.APPLICATION_JSON).status(logoutResponse.getResponseCode().getCode()).build();
    }
    catch (EngimaException e)
    {
      errorMessage = e.getMessage();
      logger.error(e);
      logger.error(e.getMessage());
    }
    logoutResponse = CommonUtils.internalSeverError(errorMessage);
    return Response.ok(logoutResponse, MediaType.APPLICATION_JSON).status(logoutResponse.getResponseCode().getCode()).build();

	}
	
//	@POST
//	@Path("/logout")
//	public Response userReport(Request userReportInfo){
//		EnigmaResponse logoutResponse = userLoginLogoutService.userReportService(userReportInfo);
//		return Response.ok(logoutResponse, MediaType.APPLICATION_JSON).status(logoutResponse.getResponseCode().getCode()).build();
//		
//	}
	
	@GET
	@Path("/login")
	public String loginAPITest(){
		return "Success";
	}
	
}
