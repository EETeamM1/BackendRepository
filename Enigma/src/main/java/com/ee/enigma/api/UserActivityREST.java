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

import com.ee.enigma.model.Request;
import com.ee.enigma.model.Response;
import com.ee.enigma.service.UserActivityService;


@Controller(value="userActivityREST")
@Consumes("application/json")
@Produces("application/json")
public class UserActivityREST {
	private Logger logger = Logger.getLogger(UserActivityREST.class);

	private UserActivityService userActivityService;
	@Autowired(required = true)
	@Qualifier(value = "userActivityService")
	public void setUserActivityService(UserActivityService userActivityService) {
		this.userActivityService = userActivityService;
	}
	
	@POST
	@Path("/login")
	public Response login(Request loginInfo){
		Response loginResponse = userActivityService.userLoginService(loginInfo);
		return loginResponse;
		
	}
	
	@GET
	@Path("/login")
	public String loginAPITest(){
		return "Success";
	}
	
}
