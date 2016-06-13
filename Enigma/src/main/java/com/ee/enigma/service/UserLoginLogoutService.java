package com.ee.enigma.service;

import com.ee.enigma.request.Request;
import com.ee.enigma.response.Response;

public interface UserLoginLogoutService {
	public Response userLoginService(Request loginInfo);
	public Response userLogoutService(Request logoutInfo);
}
