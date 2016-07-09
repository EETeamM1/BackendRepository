package com.ee.enigma.service;

import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface UserService
{
  public EnigmaResponse saveUserInfo(Request userInfo, String operation);

  public EnigmaResponse deleteUserInfo(String userId);

  public EnigmaResponse getAllUser();
  
  public EnigmaResponse getUserInfo(String userId);
}
