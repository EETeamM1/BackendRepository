package com.ee.enigma.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.EngimaException;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.service.MasterService;

@Controller(value = "masterAPI")
@Consumes("application/json")
@Produces("application/json")
public class MasterAPI {

    private static final Logger LOGGER = Logger.getLogger(MasterAPI.class);
    private MasterService masterService;

    @Autowired(required = true)
    @Qualifier(value = "masterService")
    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }

    @PUT
    @Path("/")
    public Response saveUserInfo(Request userInfo) {
        LOGGER.debug(userInfo);
        EnigmaResponse userResponse;
        String errorMessage;
        try {
            userResponse = masterService.updateMasterPassword(userInfo);
            return Response.ok(userResponse, MediaType.APPLICATION_JSON)
                    .status(userResponse.getResponseCode().getCode()).build();
        } catch (EngimaException e) {
            errorMessage = e.getMessage();
            LOGGER.error(e);
            LOGGER.error(e.getMessage());
        }
        userResponse = CommonUtils.internalSeverError(errorMessage);
        return Response.ok(userResponse, MediaType.APPLICATION_JSON).status(userResponse.getResponseCode().getCode())
                .build();
    }

}
