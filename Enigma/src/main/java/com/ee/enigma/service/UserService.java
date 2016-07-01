package com.ee.enigma.service;

import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface UserService
{
  public EnigmaResponse saveUserInfo(Request userInfo);

  public EnigmaResponse deleteUserInfo(Request requestInfo);

  public EnigmaResponse getUserInfo(Request requestInfo);
}
