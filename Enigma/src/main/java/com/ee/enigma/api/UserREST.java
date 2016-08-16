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
import com.ee.enigma.common.EnigmaException;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.service.UserService;

@Controller(value = "userREST")
@Consumes("application/json")
@Produces("application/json")
public class UserREST {
    private static final Logger LOGGER = Logger.getLogger(UserREST.class);

    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/")
    public Response saveUserInfo(Request userInfo) {
        EnigmaResponse userResponse;
        String errorMessage;
        try {
            userResponse = userService.saveUserInfo(userInfo, "save");
            return Response.ok(userResponse, MediaType.APPLICATION_JSON)
                    .status(userResponse.getResponseCode().getCode()).build();
        } catch (EnigmaException e) {
            errorMessage = e.getMessage();
            LOGGER.error(e);
            LOGGER.error(e.getMessage());
        }
        userResponse = CommonUtils.internalSeverError(errorMessage);
        return Response.ok(userResponse, MediaType.APPLICATION_JSON).status(userResponse.getResponseCode().getCode())
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUserInfo(@PathParam("id") String userId, Request userInfo) {
        EnigmaResponse userResponse;
        String errorMessage;
        try {
            userInfo.getParameters().setUserId(userId);
            userResponse = userService.saveUserInfo(userInfo, "update");
            return Response.ok(userResponse, MediaType.APPLICATION_JSON)
                    .status(userResponse.getResponseCode().getCode()).build();
        } catch (EnigmaException e) {
            errorMessage = e.getMessage();
            LOGGER.error(e);
            LOGGER.error(e.getMessage());
        }
        userResponse = CommonUtils.internalSeverError(errorMessage);
        return Response.ok(userResponse, MediaType.APPLICATION_JSON).status(userResponse.getResponseCode().getCode())
                .build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteUserInfo(@PathParam("id") String userId) {
        EnigmaResponse userResponse;
        String errorMessage;
        try {
            userResponse = userService.deleteUserInfo(userId);
            return Response.ok(userResponse, MediaType.APPLICATION_JSON)
                    .status(userResponse.getResponseCode().getCode()).build();
        } catch (EnigmaException e) {
            errorMessage = e.getMessage();
            LOGGER.error(e);
            LOGGER.error(e.getMessage());
        }
        userResponse = CommonUtils.internalSeverError(errorMessage);
        return Response.ok(userResponse, MediaType.APPLICATION_JSON).status(userResponse.getResponseCode().getCode())
                .build();

    }

    @POST
    @Path("/updatePassword")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(Request updatePasswordInfo) {
        EnigmaResponse userResponse = null;
        String errorMessage;
        try {
            userResponse = userService.updatePassword(updatePasswordInfo);
            return Response.ok(userResponse, MediaType.APPLICATION_JSON)
                    .status(userResponse.getResponseCode().getCode()).build();
        } catch (EnigmaException e) {
            errorMessage = e.getMessage();
            LOGGER.error(e);
        }
        userResponse = CommonUtils.internalSeverError(errorMessage);
        return Response.ok(userResponse, MediaType.APPLICATION_JSON).status(userResponse.getResponseCode().getCode())
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@PathParam("id") String userId) {
        EnigmaResponse userResponse;
        String errorMessage;
        try {
            userResponse = userService.getUserInfo(userId);
            return Response.ok(userResponse, MediaType.APPLICATION_JSON)
                    .status(userResponse.getResponseCode().getCode()).build();
        } catch (EnigmaException e) {
            errorMessage = e.getMessage();
            LOGGER.error(e);
            LOGGER.error(e.getMessage());
        }
        userResponse = CommonUtils.internalSeverError(errorMessage);
        return Response.ok(userResponse, MediaType.APPLICATION_JSON).status(userResponse.getResponseCode().getCode())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo() {
        EnigmaResponse userResponse;
        String errorMessage;
        try {
            userResponse = userService.getAllUser();
            return Response.ok(userResponse, MediaType.APPLICATION_JSON)
                    .status(userResponse.getResponseCode().getCode()).build();
        } catch (EnigmaException e) {
            errorMessage = e.getMessage();
            LOGGER.error(e);
            LOGGER.error(e.getMessage());
        }
        userResponse = CommonUtils.internalSeverError(errorMessage);
        return Response.ok(userResponse, MediaType.APPLICATION_JSON).status(userResponse.getResponseCode().getCode())
                .build();
    }
}
