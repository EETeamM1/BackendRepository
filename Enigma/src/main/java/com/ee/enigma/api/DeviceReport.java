package com.ee.enigma.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.ee.enigma.common.CommonUtils;
import com.ee.enigma.common.Constants;
import com.ee.enigma.dto.TopDeviceDto;
import com.ee.enigma.model.UserInfo;
import com.ee.enigma.response.EnigmaResponse;
import com.ee.enigma.response.ResponseCode;
import com.ee.enigma.response.ResponseResult;
import com.ee.enigma.service.DeviceService;

@Controller(value = "deviceReport")
@Consumes("application/json")
@Produces("application/json")
public class DeviceReport {

	private DeviceService deviceService;
	private EnigmaResponse response;
	private ResponseCode responseCode;
	private ResponseResult result;

	@Autowired(required = true)
	@Qualifier(value = "deviceService")
	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	@GET
	@Path("/topDevices")
	public EnigmaResponse getUserInfo() {
		response = new EnigmaResponse();
		responseCode = new ResponseCode();
		result = new ResponseResult();

		List<TopDeviceDto> topDeviceDtoList = deviceService.getTopDevices();

		if (null == topDeviceDtoList || topDeviceDtoList.size() == 0) {
			return CommonUtils.badRequest();
		}

		// Success response.
		result.setTopDeviceDto(topDeviceDtoList);
		responseCode.setCode(Constants.CODE_SUCCESS);
		response.setResponseCode(responseCode);
		response.setResult(result);
		return response;
	}
}
