package com.ee.enigma.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.ee.enigma.request.Request;
import com.ee.enigma.response.Response;
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
		Response loginResponse = userLoginLogoutService.userLoginService(loginInfo);
		return loginResponse;
		
	}
	
	@GET
	@Path("/login")
	public String loginAPITest(){
		return "Success";
	}
	
}
