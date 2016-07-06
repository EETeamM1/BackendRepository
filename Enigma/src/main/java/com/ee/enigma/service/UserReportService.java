package com.ee.enigma.service;

import com.ee.enigma.request.Request;
import com.ee.enigma.response.EnigmaResponse;

public interface UserReportService {
	public EnigmaResponse getUserReportForDateRange(Request userReportInfo);
}
