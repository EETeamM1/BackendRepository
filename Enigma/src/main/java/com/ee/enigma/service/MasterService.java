package com.ee.enigma.service;

import com.ee.enigma.common.EnigmaException;
import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface MasterService {
    public EnigmaResponse updateMasterPassword(Request masterInfo) throws EnigmaException;
}
