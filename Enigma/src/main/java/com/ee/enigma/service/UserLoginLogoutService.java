package com.ee.enigma.service;

import com.ee.enigma.model.Request;
import com.ee.enigma.model.Response;

public interface UserLoginLogoutService {
	public Response userLoginService(Request loginInfo);
	public void userLogoutService(Request logoutInfo);
}
