package com.ee.enigma.service;

import com.ee.enigma.common.EngimaException;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface UserService {
    public EnigmaResponse saveUserInfo(Request userInfo, String operation) throws EngimaException;

    public EnigmaResponse deleteUserInfo(String userId) throws EngimaException;

    public EnigmaResponse getAllUser() throws EngimaException;

    public EnigmaResponse getUserInfo(String userId) throws EngimaException;

    public EnigmaResponse searchUserResult(String searchQuery) throws EngimaException;

    public EnigmaResponse updatePassword(Request updatePassword) throws EngimaException;
}
