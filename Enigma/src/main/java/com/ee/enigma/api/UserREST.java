package com.ee.enigma.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.model.UserInfo;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.service.UserService;

@Controller(value = "userREST")
@Consumes("application/json")
@Produces("application/json")
public class UserREST
{
  private Logger logger = Logger.getLogger(UserREST.class);

  private UserService userService;

  @Autowired(required = true)
  @Qualifier(value = "userService")
  public void setUserService(UserService userService)
  {
    this.userService = userService;
  }

  @POST
  @Path("/")
  public Response saveUserInfo(Request userInfo)
  {
    EnigmaResponse userResponse = userService.saveUserInfo(userInfo,"save");
    return Response.ok(userResponse, MediaType.APPLICATION_JSON)
      .status(userResponse.getResponseCode().getCode()).build();
  }
  
  @PUT
  @Path("/{id}")
  public Response updateUserInfo(@PathParam("id") String userId, Request userInfo)
  {
	 userInfo.getParameters().setUserId(userId);
	 EnigmaResponse userResponse = userService.saveUserInfo(userInfo,"update");
    return Response.ok(userResponse, MediaType.APPLICATION_JSON)
      .status(userResponse.getResponseCode().getCode()).build();
  }

  @DELETE
  @Path("/{id}")
  public Response deleteUserInfo(@PathParam("id") String userId)
  {
    EnigmaResponse userResponse = userService.deleteUserInfo(userId);
    return Response.ok(userResponse, MediaType.APPLICATION_JSON)
      .status(userResponse.getResponseCode().getCode()).build();
  }
  
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUserInfo(@PathParam("id") String userId)
  {
	EnigmaResponse userResponse =  userService.getUserInfo(userId);
	return Response.ok(userResponse, MediaType.APPLICATION_JSON)
		      .status(userResponse.getResponseCode().getCode()).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUserInfo()
  {
	EnigmaResponse userResponse=  userService.getAllUser();
	return Response.ok(userResponse, MediaType.APPLICATION_JSON)
		      .status(userResponse.getResponseCode().getCode()).build();
  }
}
