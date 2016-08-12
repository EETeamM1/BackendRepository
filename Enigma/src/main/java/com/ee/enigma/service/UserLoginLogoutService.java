package com.ee.enigma.service;

import com.ee.enigma.common.EngimaException;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface UserLoginLogoutService {
	public EnigmaResponse userLoginService(Request loginInfo) throws EngimaException;
	public EnigmaResponse userLogoutService(Request logoutInfo) throws EngimaException;
}
