package com.ee.enigma.service;

import com.ee.enigma.common.EnigmaException;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface UserService {
    public EnigmaResponse saveUserInfo(Request userInfo, String operation) throws EnigmaException;

    public EnigmaResponse deleteUserInfo(String userId) throws EnigmaException;

    public EnigmaResponse getAllUser() throws EnigmaException;

    public EnigmaResponse getUserInfo(String userId) throws EnigmaException;

    public EnigmaResponse searchUserResult(String searchQuery) throws EnigmaException;

    public EnigmaResponse updatePassword(Request updatePassword) throws EnigmaException;
}
